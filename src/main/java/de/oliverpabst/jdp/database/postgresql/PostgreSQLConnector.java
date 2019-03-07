package de.oliverpabst.jdp.database.postgresql;

import de.oliverpabst.jdp.database.ConnectionParameters;
import de.oliverpabst.jdp.database.DatabaseInterface;
import de.oliverpabst.jdp.model.Image;
import de.oliverpabst.jdp.model.artist.ArtistAlias;
import de.oliverpabst.jdp.model.artist.ArtistEntity;
import de.oliverpabst.jdp.model.label.LabelEntity;
import de.oliverpabst.jdp.model.master.MasterEntity;
import de.oliverpabst.jdp.model.release.ReleaseEntity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PostgreSQLConnector implements DatabaseInterface {

    private static PostgreSQLConnector instance;

    private Connection con;

    // ArtistEntity
    private PreparedStatement insArtist;
    private PreparedStatement insArtistNameVariations;
    private PreparedStatement insArtistAlias;
    private PreparedStatement insAliasOfArtist;
    private PreparedStatement insArtistImage;
    private PreparedStatement insImageOfArtist;

    // LabelEntity
    private PreparedStatement insLabel;
    // MasterEntity
    private PreparedStatement insMaster;
    // ReleaseEntity
    private PreparedStatement insRelease;

    public static PostgreSQLConnector getInstance() {
        if(instance == null) {
            instance = new PostgreSQLConnector();
        }
        return instance;
    }

    private PostgreSQLConnector() {

    }
    @Override
    public void connect(ConnectionParameters _parameters) {
        // TODO: maybe probe schema and fail gracefully, problably before parsing to save time
        String url = "jdbc:postgresql://" + _parameters.getHostname() + ":" + _parameters.getPort() + "/" + _parameters.getDatabasename();
        try {
            con = DriverManager.getConnection(url, _parameters.getUsername(), _parameters.getPassword());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void disconnect() {
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setupPreparedStatements() throws SQLException {
        // ArtistEntity
        insArtist = con.prepareStatement("INSERT INTO discogs.artist (id, name, realname, data_quality, profile) VALUES (?, ?, ?, ?, ?)");
        insArtistNameVariations = con.prepareStatement("INSERT INTO discogs.artist_namevariations (id, name_variation) VALUES (?, ?)");
        insArtistAlias = con.prepareStatement("INSERT INTO discogs.artist_alias (id, alias) VALUES (?, ?");
        insAliasOfArtist = con.prepareStatement("INSERT INTO discogs.alias_of_artist(artist_id, alias_id, alias_name) VALUES (?, ?, ?");
        insArtistImage = con.prepareStatement("INSERT INTO discogs.artist_image (uri, uri150, type, width, height) VALUES (?, ?, ?, ?, ?)");
        insImageOfArtist = con.prepareStatement("INSERT INTO discogs.image_of_artist (id, uri) VALUES (?, ?)");
        // LabelEntity
        // MasterEntity
        // ReleaseEntity

    }

    @Override
    public void insertArtist(ArtistEntity _ae) throws SQLException {
        // Find a way to make a batch insert -> not all data can be stored
        // Maybe execute batch every 100 objects
        // TODO: last execute of batch statement when the end of the parsed document is reached -> expose a method to execute the batch statement
        insArtist.setInt(1, _ae.getId());
        insArtist.setString(2, _ae.getName());
        insArtist.setString(3, _ae.getRealName());
        insArtist.setString(4, _ae.getDataQuality().toString());
        insArtist.setString(5, _ae.getProfile());
        insArtist.execute();

        for(String nv: _ae.getNameVariations()) {
            insArtistNameVariations.setInt(1, _ae.getId());
            insArtistNameVariations.setString(2, nv);
            insArtistNameVariations.addBatch();
        }
        insArtistNameVariations.executeBatch();

        for(ArtistAlias aa: _ae.getAliases()) {
            insArtistAlias.setInt(1, aa.getAliasID());
            insArtistAlias.setString(2, aa.getAliasName());
            insArtistAlias.addBatch();

            insAliasOfArtist.setInt(1, _ae.getId());
            insAliasOfArtist.setInt(2, aa.getAliasID());
            insAliasOfArtist.setString(3, aa.getAliasName());
            insAliasOfArtist.addBatch();
        }
        insArtistAlias.executeBatch();
        insAliasOfArtist.executeBatch();

        for(Image i: _ae.getImages()) {
            insArtistImage.setString(1, i.getUri());
            insArtistImage.setString(2, i.getUri150());
            insArtistImage.setString(3, i.getType().toString());
            insArtistImage.setInt(4, i.getWidth());
            insArtistImage.setInt(5, i.getHeight());
            insArtistImage.addBatch();

            insImageOfArtist.setInt(1, _ae.getId());
            insImageOfArtist.setString(2, i.getUri());
            insImageOfArtist.addBatch();
        }

        insArtistImage.executeBatch();
        insImageOfArtist.executeBatch();
    }

    @Override
    public void insertLabel(LabelEntity _le) {

    }

    @Override
    public void insertMaster(MasterEntity _me) {

    }

    @Override
    public void insertRelease(ReleaseEntity _re) {

    }
}
