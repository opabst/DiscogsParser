package de.oliverpabst.jdp.database.postgresql.xml2db_schema;

import de.oliverpabst.jdp.DiscogsParser;
import de.oliverpabst.jdp.database.SchemaDoesNotExistException;
import de.oliverpabst.jdp.database.postgresql.PostgreSQLConnection;
import de.oliverpabst.jdp.model.xml2db_schema.label.Label;
import de.oliverpabst.jdp.model.xml2db_schema.label.LabelsImage;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LabelWriter {

    private static LabelWriter instance = null;

    private Integer insertTrigger = 1000;

    private Integer insLabelCnt = 0;
    private Integer insLabelImageCnt = 0;

    private PreparedStatement insLabel;
    private PreparedStatement insLabelImage;

    private Connection con;

    private LabelWriter() {
        try {
            con = new PostgreSQLConnection(DiscogsParser.getConnectionParameters()).getConnection();
        } catch (SchemaDoesNotExistException e) {
            System.err.println(e.getMessage());
        }
    }

    public static LabelWriter getInstance() {
        if(instance == null) {
            instance = new LabelWriter();
        }
        return instance;
    }

    private void setupPreparedStatements() throws SQLException {
        insLabel = con.prepareStatement("INSERT INTO discogs.label (id, name, contactinfo, profile, parent_label, sublabels, urls, data_quality) " +
                " VALUES (?,?,?,?,?,?,?,?)");
        insLabelImage = con.prepareStatement("INSERT INTO discogs.labels_images (label_id, type, height, width, image_uri) " +
                " VALUES (?, ?, ?, ?, ?)");
    }

    public void insertLabel(Label _l) throws SQLException{
        insLabel.setInt(1, _l.getId());
        insLabel.setString(2, _l.getName());
        insLabel.setString(3, _l.getContactinfo());
        insLabel.setString(4, _l.getProfile());
        insLabel.setString(5, _l.getParentLabel());
        Array sublabels = con.createArrayOf("text", _l.getSublabels().toArray());
        insLabel.setArray(6, sublabels);
        Array urls = con.createArrayOf("text", _l.getUrls().toArray());
        insLabel.setArray(7, urls);
        insLabel.setString(8, _l.getDataQuality().toString());
        insLabel.addBatch();
        insLabelCnt++;

        if(insLabelCnt % insertTrigger == 0) {
            insLabel.execute();
        }
    }

    public void insertLabelsImage(LabelsImage _li) throws SQLException {
        insLabelImage.setInt(1, _li.getLabelId());
        insLabelImage.setString(2, _li.getImage().getType().toString());
        insLabelImage.setInt(3, _li.getImage().getHeight());
        insLabelImage.setInt(4, _li.getImage().getWidth());
        insLabelImage.setString(5, _li.getImage().getUri());
        insLabelImage.addBatch();
        insLabelImageCnt++;

        if(insLabelImageCnt % insertTrigger == 0) {
            insLabelImage.execute();
        }
    }

    public void finalBatchExecute() throws SQLException {
        insLabel.execute();
        insLabelImage.execute();
    }

    public void disconnect() {
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    };
}
