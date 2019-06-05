package de.oliverpabst.jdp.database.postgresql;

import de.oliverpabst.jdp.DiscogsParser;
import de.oliverpabst.jdp.ImportStatistics;
import de.oliverpabst.jdp.database.SchemaDoesNotExistException;
import de.oliverpabst.jdp.model.Image;
import de.oliverpabst.jdp.model.master.MasterArtist;
import de.oliverpabst.jdp.model.master.MasterEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MasterWriter {

    private static MasterWriter instance = null;

    private Connection con;

    private Integer objectCounter = 0;

    // MasterEntity
    private PreparedStatement insMaster;
    private PreparedStatement insMasterStyles;
    private PreparedStatement insMasterGenres;
    private PreparedStatement insMasterImages;
    private PreparedStatement insImagesOfMaster;
    private PreparedStatement insMasterArtist;
    private PreparedStatement insMasterArtistPerforms;

    private Integer insertTrigger = 50000;

    private Integer masterCnt = 0;
    private Integer masterStylesCnt = 0;
    private Integer masterGenresCnt = 0;
    private Integer masterImageCnt = 0;
    private Integer imageOfMasterCnt = 0;
    private Integer masterArtistCnt = 0;
    private Integer masterArtistPerformsCnt = 0;

    private MasterWriter() {
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

    public static MasterWriter getInstance() {
        if(instance == null) {
            instance = new MasterWriter();
        }
        return instance;
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

    public void finalBatchExecute() throws SQLException {
        executeMasterBatchs();
        ImportStatistics.getInstance().setValue("Master", masterCnt);
        ImportStatistics.getInstance().setValue("MasterStyles", masterStylesCnt);
        ImportStatistics.getInstance().setValue("MasterGenres", masterGenresCnt);
        ImportStatistics.getInstance().setValue("MasterImage", masterImageCnt);
        ImportStatistics.getInstance().setValue("MasterImageOf", imageOfMasterCnt);
        ImportStatistics.getInstance().setValue("MasterArtist", masterArtistCnt);
        ImportStatistics.getInstance().setValue("MasterArtistPerforms", masterArtistPerformsCnt);
        con.setAutoCommit(true);
    }

    public void setupPreparedStatements() throws SQLException {
        con.setAutoCommit(false);
        insMaster = con.prepareStatement("INSERT INTO discogs.master_import (id, year, data_quality, title, main_release) VALUES (?, ?, ?, ?, ?)");
        insMasterStyles = con.prepareStatement("INSERT INTO discogs.master_styles_import (id, style) VALUES (?, ?)");
        insMasterGenres = con.prepareStatement("INSERT INTO discogs.master_genres_import (id, genre) VALUES (?, ?)");
        insMasterImages = con.prepareStatement("INSERT INTO discogs.master_images_import (uri, uri150, type, width, height) VALUES (?, ?, ?, ?, ?)");
        insImagesOfMaster = con.prepareStatement("INSERT INTO discogs.images_of_master_import (uri, master_id) VALUES (?, ?)");
        insMasterArtist = con.prepareStatement("INSERT INTO discogs.master_artist_import (id, name, role, join_att, anv) VALUES (?, ?, ?, ?, ?)");
        insMasterArtistPerforms = con.prepareStatement("INSERT INTO discogs.master_artist_performs_import (master_id, artist_id) VALUES (?, ?)");
    }

    public void insertMaster(MasterEntity _me) throws SQLException {
        insMaster.setInt(1, Integer.parseInt(_me.getId()));
        insMaster.setInt(2, Integer.parseInt(_me.getYear()));
        insMaster.setString(3, _me.getDataQuality().toString());
        insMaster.setString(4, _me.getTitle());// title
        insMaster.setInt(5, Integer.parseInt(_me.getMainRelease()));
        insMaster.addBatch();
        masterCnt++;

        for(String genre: _me.getGenres()) {
            insMasterGenres.setInt(1, Integer.parseInt(_me.getId()));
            insMasterGenres.setString(2, genre);
            insMasterGenres.addBatch();
            masterGenresCnt++;
        }

        for(String style: _me.getStyles()) {
            insMasterStyles.setInt(1, Integer.parseInt(_me.getId()));
            insMasterStyles.setString(2, style);
            insMasterStyles.addBatch();
            masterStylesCnt++;
        }

        for(Image i: _me.getImages()) {
            insMasterImages.setString(1, i.getUri());
            insMasterImages.setString(2, i.getUri150());
            insMasterImages.setString(3, i.getType().toString());
            insMasterImages.setInt(4, i.getWidth());
            insMasterImages.setInt(5, i.getHeight());
            insMasterImages.addBatch();
            masterImageCnt++;

            insImagesOfMaster.setString(1, i.getUri());
            insImagesOfMaster.setInt(2, Integer.parseInt(_me.getId()));
            insImagesOfMaster.addBatch();
            imageOfMasterCnt++;
        }

        for(MasterArtist ma: _me.getArtists()) {
            insMasterArtist.setInt(1, Integer.parseInt(ma.getId()));
            insMasterArtist.setString(2, ma.getName());
            insMasterArtist.setString(3, ma.getRole());
            insMasterArtist.setString(4, ma.getJoin());
            insMasterArtist.setString(5, ma.getAnv());
            insMasterArtist.addBatch();
            masterArtistCnt++;

            insMasterArtistPerforms.setInt(1, Integer.parseInt(_me.getId()));
            insMasterArtistPerforms.setInt(2, Integer.parseInt(ma.getId()));
            insMasterArtistPerforms.addBatch();
            masterArtistPerformsCnt++;
        }

        objectCounter++;
        if(objectCounter % insertTrigger == 0) {
            executeMasterBatchs();
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
