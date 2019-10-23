package de.oliverpabst.jdp.database.postgresql.jdp_schema;

import de.oliverpabst.jdp.DiscogsParser;
import de.oliverpabst.jdp.ImportStatistics;
import de.oliverpabst.jdp.database.SchemaDoesNotExistException;
import de.oliverpabst.jdp.database.postgresql.PostgreSQLConnection;
import de.oliverpabst.jdp.model.Image;
import de.oliverpabst.jdp.model.jdp_schema.label.LabelEntity;
import de.oliverpabst.jdp.model.jdp_schema.label.LabelSublabel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LabelWriter {

    private static LabelWriter instance = null;

    private Connection con;

    private Integer objectCounter = 0;

    // LabelEntity
    private PreparedStatement insLabel;
    private PreparedStatement insLabelUrls;
    private PreparedStatement insSublabel;
    private PreparedStatement insSublabelOf;
    private PreparedStatement insLabelImages;
    private PreparedStatement insImageOfLabel;


    private Integer insertTrigger = 1000;

    private Integer labelCnt = 0;
    private Integer labelUrlsCnt = 0;
    private Integer sublabelCnt = 0;
    private Integer sublabelOfCnt = 0;
    private Integer labelImageCnt = 0;
    private Integer imageOfLabelCnt = 0;

    private LabelWriter() {
        try {
            con = new PostgreSQLConnection(DiscogsParser.getConnectionParameters()).getConnection();
        } catch (SchemaDoesNotExistException e) {
            e.printStackTrace();
        }
        try {
            setupPreparedStatements();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static LabelWriter getInstance() {
        if(instance == null) {
            instance = new LabelWriter();
        }
        return instance;
    }

    public void executeLabelBatchs() throws SQLException {
        insLabel.executeBatch();
        insLabelUrls.executeBatch();
        insSublabel.executeBatch();
        insSublabelOf.executeBatch();
        insLabelImages.executeBatch();
        insImageOfLabel.executeBatch();
    }

    public void finalBatchExecute() throws SQLException {
        executeLabelBatchs();
        con.commit();

        ImportStatistics.getInstance().setValue("Label", labelCnt);
        ImportStatistics.getInstance().setValue("LabelUrls", labelUrlsCnt);
        ImportStatistics.getInstance().setValue("LabelSublabel", sublabelCnt);
        ImportStatistics.getInstance().setValue("LabelSublabelOf", sublabelOfCnt);
        ImportStatistics.getInstance().setValue("LabelImage", labelImageCnt);
        ImportStatistics.getInstance().setValue("LabelImageOf", imageOfLabelCnt);

        con.setAutoCommit(true);
    }

    public void setupPreparedStatements() throws SQLException {
        con.setAutoCommit(false);
        insLabel = con.prepareStatement("INSERT INTO discogs.label_import (id, name, contactinfo, profile, data_quality) VALUES (?, ?, ?, ?, ?)");
        insLabelUrls = con.prepareStatement("INSERT INTO discogs.label_urls_import (id, url) VALUES (?, ?)");
        insSublabel = con.prepareStatement("INSERT INTO discogs.sublabel_import (id, name) VALUES (?, ?)");
        insSublabelOf = con.prepareStatement("INSERT INTO discogs.sublabel_of_import (label_id, sublabel_id) VALUES (?, ?)");
        insLabelImages = con.prepareStatement("INSERT INTO discogs.label_images_import (id, uri, uri150, type, width, height) VALUES (?, ?, ?, ?, ?, ?)");
        insImageOfLabel = con.prepareStatement("INSERT INTO discogs.image_of_label_import (image_id, label_id) VALUES (?, ?)");
    }

    public void insertLabel(LabelEntity _le) throws SQLException{
        insLabel.setInt(1, Integer.parseInt(_le.getId()));
        insLabel.setString(2, _le.getName());
        insLabel.setString(3, _le.getContactinfo());
        insLabel.setString(4, _le.getProfile());
        insLabel.setString(5, _le.getDataQuality().toString());
        insLabel.addBatch();
        labelCnt++;

        for(String url: _le.getUrls()) {
            insLabelUrls.setInt(1, Integer.parseInt(_le.getId()));
            insLabelUrls.setString(2, url);
            insLabelUrls.addBatch();
            labelUrlsCnt++;
        }

        for(LabelSublabel ls: _le.getSublabels()) {
            insSublabel.setInt(1, Integer.parseInt(ls.getSublabelID()));
            insSublabel.setString(2, ls.getSublabelName());
            insSublabel.addBatch();
            sublabelCnt++;

            insSublabelOf.setInt(1, Integer.parseInt(_le.getId()));
            insSublabelOf.setInt(2, Integer.parseInt(ls.getSublabelID()));
            insSublabelOf.addBatch();
            sublabelOfCnt++;
        }

        for(Image i: _le.getImages()) {
            insLabelImages.setInt(1, labelImageCnt+1);
            insLabelImages.setString(2, i.getUri());
            insLabelImages.setString(3, i.getUri150());
            insLabelImages.setString(4, i.getType().toString());
            insLabelImages.setInt(5, i.getWidth());
            insLabelImages.setInt(6, i.getHeight());
            insLabelImages.addBatch();
            labelImageCnt++;

            insImageOfLabel.setInt(1, imageOfLabelCnt+1);
            insImageOfLabel.setInt(2, Integer.parseInt(_le.getId()));
            insImageOfLabel.addBatch();
            imageOfLabelCnt++;
        }

        objectCounter++;
        if(objectCounter % insertTrigger == 0) {
            executeLabelBatchs();
        }
    }

    public void disconnect() {
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
