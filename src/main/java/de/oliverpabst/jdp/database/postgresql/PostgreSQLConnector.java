package de.oliverpabst.jdp.database.postgresql;

import de.oliverpabst.jdp.database.ConnectionParameters;
import de.oliverpabst.jdp.database.DatabaseInterface;
import de.oliverpabst.jdp.database.SchemaDoesNotExistException;
import de.oliverpabst.jdp.model.Image;
import de.oliverpabst.jdp.model.artist.ArtistAlias;
import de.oliverpabst.jdp.model.artist.ArtistEntity;
import de.oliverpabst.jdp.model.label.LabelEntity;
import de.oliverpabst.jdp.model.label.LabelSublabel;
import de.oliverpabst.jdp.model.master.MasterArtist;
import de.oliverpabst.jdp.model.master.MasterEntity;
import de.oliverpabst.jdp.model.release.*;

import java.sql.*;

public class PostgreSQLConnector implements DatabaseInterface {

    private static PostgreSQLConnector instance;

    private Connection con;

    private Integer artistCounter = 0;
    private Integer labelCounter = 0;
    private Integer masterCounter = 0;
    private Integer releaseCounter = 0;

    private Integer insertTrigger = 250;

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
    public void connect(ConnectionParameters _parameters) throws SchemaDoesNotExistException {
        String url = "jdbc:postgresql://" + _parameters.getHostname() + ":" + _parameters.getPort() + "/" + _parameters.getDatabasename();
        try {
            con = DriverManager.getConnection(url, _parameters.getUsername(), _parameters.getPassword());

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT schema_name FROM information.schemata WHERE schema_name = 'discogs'");
            if(!rs.next()) {
                // Schema does not exist!
                throw new SchemaDoesNotExistException("Schema 'discogs' does not exist. Create schema before proceeding!");
            }

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
    public void executeAllBatchs() throws SQLException {
        executeArtistBatchs();
        executeLabelBatchs();
        executeMasterBatchs();
        executeReleaseBatchs();
    }

    public void executeArtistBatchs() throws SQLException {
        insArtist.executeBatch();
        insArtistNameVariations.executeBatch();
        insArtistAlias.executeBatch();
        insAliasOfArtist.executeBatch();
        insArtistImage.executeBatch();
        insImageOfArtist.executeBatch();
    }

    public void executeLabelBatchs() throws SQLException {
        insLabel.executeBatch();
        insLabelUrls.executeBatch();
        insSublabel.executeBatch();
        insSublabelOf.executeBatch();
        insLabelImages.executeBatch();
        insImageOfLabel.executeBatch();
    }

    public void executeMasterBatchs() throws SQLException {
        insMaster.executeBatch();
        insMasterStyles.executeBatch();
        insMasterGenres.executeBatch();
        insMasterImages.executeBatch();
        insImagesOfMaster.executeBatch();
        insMasterArtist.executeBatch();
        insMasterArtistPerforms.executeBatch();
    }

    public void executeReleaseBatchs() throws SQLException {
        insRelease.executeBatch();
        insReleaseStyles.executeBatch();
        insReleaseGenres.executeBatch();
        insReleaseArtist.executeBatch();
        insArtistOfRelease.executeBatch();
        insReleaseExtraartist.executeBatch();
        insExtraartistOfRelease.executeBatch();
        insReleaseIdentifier.executeBatch();
        insIdentifies.executeBatch();
        insReleaseVideo.executeBatch();
        insVideoOfRelease.executeBatch();
        insReleaseCompany.executeBatch();
        insCompanyOfRelease.executeBatch();
        insReleaseImage.executeBatch();
        insImageOfRelease.executeBatch();
        insReleaseLabel.executeBatch();
        insLabelOfRelease.executeBatch();
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

        artistCounter++;
        if(artistCounter % insertTrigger == 0) {
            executeArtistBatchs();
        }
    }

    @Override
    public void insertLabel(LabelEntity _le) throws SQLException{
        insLabel.setInt(1, _le.getId());
        insLabel.setString(2, _le.getName());
        insLabel.setString(3, _le.getContactinfo());
        insLabel.setString(4, _le.getProfile());
        insLabel.setString(5, _le.getDataQuality().toString());
        insLabel.addBatch();

        for(String url: _le.getUrls()) {
            insLabelUrls.setInt(1, _le.getId());
            insLabelUrls.setString(2, url);
            insLabelUrls.addBatch();
        }

        for(LabelSublabel ls: _le.getSublabels()) {
            insSublabel.setInt(1, ls.getSublabelID());
            insSublabel.setString(2, ls.getSublabelName());
            insSublabel.addBatch();

            insSublabelOf.setInt(1, _le.getId());
            insSublabelOf.setInt(2, ls.getSublabelID());
            insSublabelOf.addBatch();
        }

        for(Image i: _le.getImages()) {
            insLabelImages.setString(1, i.getUri());
            insLabelImages.setString(2, i.getUri150());
            insLabelImages.setString(3, i.getType().toString());
            insLabelImages.setInt(4, i.getWidth());
            insLabelImages.setInt(5, i.getHeight());
            insLabelImages.addBatch();

            insImageOfLabel.setString(1, i.getUri());
            insImageOfLabel.setInt(2, _le.getId());
            insImageOfLabel.addBatch();
        }

        labelCounter++;
        if(labelCounter % insertTrigger == 0) {
            executeLabelBatchs();
        }
    }

    @Override
    public void insertMaster(MasterEntity _me) throws SQLException {
        insMaster.setInt(1, Integer.parseInt(_me.getId()));
        insMaster.setInt(2, Integer.parseInt(_me.getYear()));
        insMaster.setString(3, _me.getDataQuality().toString());
        insMaster.setString(4, _me.getTitle());// title
        insMaster.setInt(5, Integer.parseInt(_me.getMainRelease()));

        for(String genre: _me.getGenres()) {
            insMasterGenres.setInt(1, Integer.parseInt(_me.getId()));
            insMasterGenres.setString(2, genre);
            insMasterGenres.addBatch();
        }

        for(String style: _me.getStyles()) {
            insMasterStyles.setInt(1, Integer.parseInt(_me.getId()));
            insMasterStyles.setString(2, style);
            insMasterStyles.addBatch();
        }

        for(Image i: _me.getImages()) {
            insMasterImages.setString(1, i.getUri());
            insMasterImages.setString(2, i.getUri150());
            insMasterImages.setString(3, i.getType().toString());
            insMasterImages.setInt(4, i.getWidth());
            insMasterImages.setInt(5, i.getHeight());
            insMasterImages.addBatch();

            insImagesOfMaster.setString(1, i.getUri());
            insImagesOfMaster.setInt(2, Integer.parseInt(_me.getId()));
            insImagesOfMaster.addBatch();
        }

        for(MasterArtist ma: _me.getArtists()) {
            insMasterArtist.setInt(1, ma.getId());
            insMasterArtist.setString(2, ma.getName());
            insMasterArtist.setString(3, ma.getRole());
            insMasterArtist.setString(4, ma.getJoin());
            insMasterArtist.setString(5, ma.getAnv());
            insMasterArtist.addBatch();

            insMasterArtistPerforms.setInt(1, Integer.parseInt(_me.getId()));
            insMasterArtistPerforms.setInt(2, ma.getId());
            insMasterArtistPerforms.addBatch();
        }

        masterCounter++;
        if(masterCounter % insertTrigger == 0) {
            executeMasterBatchs();
        }
    }

    @Override
    public void insertRelease(ReleaseEntity _re) throws SQLException {
        insRelease.setInt(1, Integer.parseInt(_re.getId()));
        insRelease.setString(2, _re.getReleased());
        insRelease.setString(3, _re.getCountry());
        insRelease.setString(4, _re.getNotes());
        insRelease.setString(5, _re.getStatus());
        insRelease.setString(6, _re.getTitle());
        insRelease.setString(7, _re.getDataQuality().toString());

        for(String genre: _re.getGenres()) {
            insReleaseGenres.setInt(1, Integer.parseInt(_re.getId()));
            insReleaseGenres.setString(2, genre);
            insReleaseGenres.addBatch();
        }

        for(String style: _re.getStyles()) {
            insReleaseStyles.setInt(1, Integer.parseInt(_re.getId()));
            insReleaseStyles.setString(2, style);
            insReleaseStyles.addBatch();
        }

        for(ReleaseArtist ra: _re.getArtists()) {
            insReleaseArtist.setInt(1, ra.getId());
            insReleaseArtist.setString(2, ra.getName());
            insReleaseArtist.setString(3, ra.getRole());
            insReleaseArtist.setString(4, ra.getAnv());
            insReleaseArtist.setString(5, ra.getJoin());
            insReleaseArtist.addBatch();

            insArtistOfRelease.setInt(1, Integer.parseInt(_re.getId()));
            insArtistOfRelease.setInt(2, ra.getId());
            insArtistOfRelease.addBatch();
        }

        for(ReleaseExtraArtist rea: _re.getExtraArtists()) {
            insReleaseExtraartist.setInt(1, rea.getId());
            insReleaseExtraartist.setString(2, rea.getName());
            insReleaseExtraartist.setString(3, rea.getRole());
            insReleaseExtraartist.setString(4, rea.getAnv());
            insReleaseExtraartist.setString(5, rea.getJoin());
            insReleaseExtraartist.addBatch();

            insExtraartistOfRelease.setInt(1, Integer.parseInt(_re.getId()));
            insExtraartistOfRelease.setInt(2, rea.getId());
            insExtraartistOfRelease.addBatch();
        }

        for(ReleaseIdentifier ri: _re.getIdentifiers()) {
            insReleaseIdentifier.setString(1, ri.getValue());
            insReleaseIdentifier.setString(2, ri.getType());
            insReleaseIdentifier.setString(3, ri.getDescription());
            insReleaseIdentifier.addBatch();

            insIdentifies.setInt(1, Integer.parseInt(_re.getId()));
            insIdentifies.setString(2, ri.getValue());
            insIdentifies.addBatch();
        }

        for(ReleaseVideo rv: _re.getVideos()) {
            insReleaseVideo.setString(1, rv.getSrc());
            insReleaseVideo.setString(2, rv.getDuration());
            insReleaseVideo.setString(3, rv.getDescription());
            insReleaseVideo.setString(4, rv.getTitle());
            insReleaseVideo.setBoolean(5, rv.getEmbed());
            insReleaseVideo.addBatch();

            insVideoOfRelease.setInt(1, Integer.parseInt(_re.getId()));
            insVideoOfRelease.setString(2, rv.getSrc());
            insVideoOfRelease.addBatch();
        }

        for(ReleaseCompany rc: _re.getCompanies()) {
            insReleaseCompany.setInt(1, Integer.parseInt(rc.getId()));
            insReleaseCompany.setString(2, rc.getResourceUrl());
            insReleaseCompany.setString(3, rc.getName());
            insReleaseCompany.setString(4, rc.getEntityType());
            insReleaseCompany.setString(5, rc.getEntityTypeValue());
            insReleaseCompany.setString(6, rc.getCatno());
            insReleaseCompany.addBatch();

            insCompanyOfRelease.setInt(1, Integer.parseInt(_re.getId()));
            insCompanyOfRelease.setInt(2, Integer.parseInt(rc.getId()));
            insCompanyOfRelease.addBatch();
        }

        for(Image i: _re.getImages()) {
            insReleaseImage.setString(1, i.getUri());
            insReleaseImage.setString(2, i.getUri150());
            insReleaseImage.setString(3, i.getType().toString());
            insReleaseImage.setInt(4, i.getWidth());
            insReleaseImage.setInt(5, i.getHeight());
            insReleaseImage.addBatch();

            insImageOfRelease.setString(1, i.getUri());
            insImageOfRelease.setInt(2, Integer.parseInt(_re.getId()));
            insImageOfRelease.addBatch();
        }

        for(ReleaseLabel rl: _re.getLabels()) {
            insReleaseLabel.setInt(1, Integer.parseInt(rl.getId()));
            insReleaseLabel.setString(2, rl.getCatno());
            insReleaseLabel.setString(3, rl.getName());
            insReleaseLabel.addBatch();

            insLabelOfRelease.setInt(1, Integer.parseInt(rl.getId()));
            insLabelOfRelease.setInt(2, Integer.parseInt(_re.getId()));
            insLabelOfRelease.addBatch();
        }


        releaseCounter++;
        if(releaseCounter % insertTrigger == 0) {
            executeReleaseBatchs();
        }
    }
}
