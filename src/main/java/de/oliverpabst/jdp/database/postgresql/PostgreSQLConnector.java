package de.oliverpabst.jdp.database.postgresql;

import de.oliverpabst.jdp.database.ConnectionParameters;
import de.oliverpabst.jdp.database.DatabaseInterface;
import de.oliverpabst.jdp.model.Image;
import de.oliverpabst.jdp.model.artist.ArtistAlias;
import de.oliverpabst.jdp.model.artist.ArtistEntity;
import de.oliverpabst.jdp.model.label.LabelEntity;
import de.oliverpabst.jdp.model.master.MasterEntity;
import de.oliverpabst.jdp.model.release.ReleaseEntity;
import jdk.tools.jlink.internal.PathResourcePoolEntry;

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
    private PreparedStatement insLabelUrls;
    private PreparedStatement insSublabel;
    private PreparedStatement insSublabelOf;
    private PreparedStatement insLabelImages;
    private PreparedStatement insImageOfLabel;
    // MasterEntity
    private PreparedStatement insMaster;
    private PreparedStatement insMasterStyles;
    private PreparedStatement insMasterGenres;
    private PreparedStatement insMasterImages;
    private PreparedStatement insImagesOfMaster;
    private PreparedStatement insMasterArtist;
    private PreparedStatement insMasterArtistPerforms;
    // ReleaseEntity
    private PreparedStatement insRelease;
    private PreparedStatement insReleaseStyles;
    private PreparedStatement insReleaseGenres;
    private PreparedStatement insReleaseArtist;
    private PreparedStatement insArtistOfRelease;
    private PreparedStatement insReleaseExtraartist;
    private PreparedStatement insExtraartistOfRelease;
    private PreparedStatement insReleaseIdentifier;
    private PreparedStatement insIdentifies;
    private PreparedStatement insReleaseVideo;
    private PreparedStatement insVideoOfRelease;
    private PreparedStatement insReleaseCompany;
    private PreparedStatement insCompanyOfRelease;
    private PreparedStatement insReleaseImage;
    private PreparedStatement insImageOfRelease;
    private PreparedStatement insReleaseLabel;
    private PreparedStatement insLabelOfRelease;

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
        insLabel = con.prepareStatement("INSERT INTO discogs.label (id, name, contactinfo, profile, data_quality) VALUES (?, ?, ?, ?, ?)");
        insLabelUrls = con.prepareStatement("INSERT INTO discogs.label_urls (id, url) VALUES (?, ?)");
        insSublabel = con.prepareStatement("INSERT INTO discogs.sublabel (id, name) VALUES (?, ?)");
        insSublabelOf = con.prepareStatement("INSERT INTO discogs.sublabel_of (label_id, sublabel_id) VALUES (?, ?)");
        insLabelImages = con.prepareStatement("INSERT INTO discogs.label_images (uri, uri150, type, width, height) VALUES (?, ?, ?, ?, ?)");
        insImageOfLabel = con.prepareStatement("INSERT INTO discogs.image_of_labels (uri, label_id) VALUES (?, ?)");
        // MasterEntity
        insMaster = con.prepareStatement("INSERT INTO discogs.master (id, year, data_quality, title, main_release) VALUES (?, ?, ?, ?, ?)");
        insMasterStyles = con.prepareStatement("INSERT INTO discogs.master_styles (id, style) VALUES (?, ?)");
        insMasterGenres = con.prepareStatement("INSERT INTO discogs.master_genres (id, genre) VALUES (?, ?)");
        insMasterImages = con.prepareStatement("INSERT INTO discogs.master_images (uri, uri150, type, width, height) VALUES (?, ?, ?, ?, ?)");
        insImagesOfMaster = con.prepareStatement("INSERT INTO discogs.images_of_master (uri, master_id) VALUES (?, ?)");
        insMasterArtist = con.prepareStatement("INSERT INTO discogs.master_artist (id, name, role, join, anv) VALUES (?, ?, ?, ?, ?)");
        insMasterArtistPerforms = con.prepareStatement("INSERT INTO discogs.master_artist_performs (master_id, artist_id) VALUES (?, ?)");
        // ReleaseEntity
        insRelease = con.prepareStatement("INSERT INTO discogs.release (id, released, country, notes, status, title, data_quality) VALUES (?, ?, ?, ?, ?, ?)");
        insReleaseStyles = con.prepareStatement("INSERT INTO discogs.release_styles (id, style) VALUES (?. ?)");
        insReleaseGenres = con.prepareStatement("INSERT INTO discogs.release_genres (id, genre) VALUES (?, ?)");
        insReleaseArtist = con.prepareStatement("INSERT INTO discogs.release_artist (id, name, role, anv, join) VALUES (?, ?, ?, ?, ?)");
        insArtistOfRelease = con.prepareStatement("INSERT INTO discogs.artist_of_release (release_id, artist_id) VALUES (?, ?)");
        insReleaseExtraartist = con.prepareStatement("INSERT INTO discogs.release_extraartist (id, name, role, anv, join) VALUES (?, ?, ?, ?, ?)");
        insExtraartistOfRelease = con.prepareStatement("INSERT INTO discogs.extraartist_of_release (release_id, artist_id) VALUES (?, ?)");
        insReleaseIdentifier = con.prepareStatement("INSERT INTO discogs.release_identifier (value, type, description) VALUES (?, ?, ?)");
        insIdentifies = con.prepareStatement("INSERT INTO discogs.identifies (release_id, identifier_value) VALUES (?, ?");
        insReleaseVideo = con.prepareStatement("INSERT INTO discogs.release_video (src, duration, description, title, embed) VALUES (?, ?, ?, ?, ?)");
        insVideoOfRelease = con.prepareStatement("INSERT INTO discogs.video_of_release (release_id, video_src) VALUES (?, ?)");
        insReleaseCompany = con.prepareStatement("INSERT INTO discogs.release_company (id, resource_url, name, entity_type, entity_type_value, catno) VALUES (?, ?, ?, ?, ?, ?)");
        insCompanyOfRelease = con.prepareStatement("INSERT INTO discogs.company_of_release (release_id, company_id) VALUES (?, ?)");
        insReleaseImage = con.prepareStatement("INSERT INTO discogs.release_image (uri, uri150, type, width, heigth) VALUES (?, ?, ?, ?, ?)");
        insImageOfRelease = con.prepareStatement("INSERT INTO discogs.image_of_release (uri, release_id) VALUES (?, ?)");
        insReleaseLabel = con.prepareStatement("INSERT INTO discogs.release_label (id, catno, name) VALUES (?, ?, ?)");
        insLabelOfRelease = con.prepareStatement("INSERT INTO discogs.label_of_release (label_id, release_id) VALUES (?, ?)");
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
    public void insertLabel(LabelEntity _le) throws SQLException{

    }

    @Override
    public void insertMaster(MasterEntity _me) {

    }

    @Override
    public void insertRelease(ReleaseEntity _re) {

    }
}
