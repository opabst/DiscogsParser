package de.oliverpabst.jdp.database.postgresql;

import de.oliverpabst.jdp.DiscogsParser;
import de.oliverpabst.jdp.ImportStatistics;
import de.oliverpabst.jdp.database.SchemaDoesNotExistException;
import de.oliverpabst.jdp.model.Image;
import de.oliverpabst.jdp.model.label.LabelEntity;
import de.oliverpabst.jdp.model.label.LabelSublabel;

import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LabelWriter {

    private static LabelWriter instance = null;

    private Connection con;

    private Integer labelCounter = 0;

    // LabelEntity
    private PreparedStatement insLabel;
    private PreparedStatement insLabelUrls;
    private PreparedStatement insSublabel;
    private PreparedStatement insSublabelOf;
    private PreparedStatement insLabelImages;
    private PreparedStatement insImageOfLabel;


    private Integer insertTrigger = 50000;

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
        con.setAutoCommit(true);
    }

    public void setupPreparedStatements() throws SQLException {
        con.setAutoCommit(false);
        insLabel = con.prepareStatement("INSERT INTO discogs.label_import (id, name, contactinfo, profile, data_quality) VALUES (?, ?, ?, ?, ?)");
        insLabelUrls = con.prepareStatement("INSERT INTO discogs.label_urls_import (id, url) VALUES (?, ?)");
        insSublabel = con.prepareStatement("INSERT INTO discogs.sublabel_import (id, name) VALUES (?, ?)");
        insSublabelOf = con.prepareStatement("INSERT INTO discogs.sublabel_of_import (label_id, sublabel_id) VALUES (?, ?)");
        insLabelImages = con.prepareStatement("INSERT INTO discogs.label_images_import (uri, uri150, type, width, height) VALUES (?, ?, ?, ?, ?)");
        insImageOfLabel = con.prepareStatement("INSERT INTO discogs.image_of_label_import (uri, label_id) VALUES (?, ?)");
    }

    public void insertLabel(LabelEntity _le) throws SQLException{
        insLabel.setInt(1, Integer.parseInt(_le.getId()));
        insLabel.setString(2, _le.getName());
        insLabel.setString(3, _le.getContactinfo());
        insLabel.setString(4, _le.getProfile());
        insLabel.setString(5, _le.getDataQuality().toString());
        insLabel.addBatch();
        ImportStatistics.getInstance().increase("Label");

        for(String url: _le.getUrls()) {
            insLabelUrls.setInt(1, Integer.parseInt(_le.getId()));
            insLabelUrls.setString(2, url);
            insLabelUrls.addBatch();
            ImportStatistics.getInstance().increase("LabelUrls");
        }

        for(LabelSublabel ls: _le.getSublabels()) {
            insSublabel.setInt(1, Integer.parseInt(ls.getSublabelID()));
            insSublabel.setString(2, ls.getSublabelName());
            insSublabel.addBatch();
            ImportStatistics.getInstance().increase("LabelSublabel");

            insSublabelOf.setInt(1, Integer.parseInt(_le.getId()));
            insSublabelOf.setInt(2, Integer.parseInt(ls.getSublabelID()));
            insSublabelOf.addBatch();
            ImportStatistics.getInstance().increase("LabelSublabelOf");
        }

        for(Image i: _le.getImages()) {
            insLabelImages.setString(1, i.getUri());
            insLabelImages.setString(2, i.getUri150());
            insLabelImages.setString(3, i.getType().toString());
            insLabelImages.setInt(4, i.getWidth());
            insLabelImages.setInt(5, i.getHeight());
            insLabelImages.addBatch();
            ImportStatistics.getInstance().increase("LabelImage");

            insImageOfLabel.setString(1, i.getUri());
            insImageOfLabel.setInt(2, Integer.parseInt(_le.getId()));
            insImageOfLabel.addBatch();
            ImportStatistics.getInstance().increase("LabelImageOf");
        }

        labelCounter++;
        if(labelCounter % insertTrigger == 0) {
            executeLabelBatchs();
            con.commit();
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
