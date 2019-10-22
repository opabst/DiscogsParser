package de.oliverpabst.jdp.database.postgresql.jdp;

import de.oliverpabst.jdp.DiscogsParser;
import de.oliverpabst.jdp.ImportStatistics;
import de.oliverpabst.jdp.database.SchemaDoesNotExistException;
import de.oliverpabst.jdp.database.postgresql.PostgreSQLConnection;
import de.oliverpabst.jdp.model.Image;
import de.oliverpabst.jdp.model.master.MasterArtist;
import de.oliverpabst.jdp.model.master.MasterEntity;
import de.oliverpabst.jdp.model.master.MasterVideo;

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
    private PreparedStatement insMasterVideo;
    private PreparedStatement insMasterVideoOf;

    private Integer insertTrigger = 1000;

    private Integer masterCnt = 0;
    private Integer masterStylesCnt = 0;
    private Integer masterGenresCnt = 0;
    private Integer masterImageCnt = 0;
    private Integer imageOfMasterCnt = 0;
    private Integer masterArtistCnt = 0;
    private Integer masterArtistPerformsCnt = 0;
    private Integer masterVideoCnt = 0;
    private Integer masterVideoOfCnt = 0;

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
        insMasterVideo.executeBatch();
        insMasterVideoOf.executeBatch();
    }

    public void finalBatchExecute() throws SQLException {
        executeMasterBatchs();
        con.commit();

        ImportStatistics.getInstance().setValue("Master", masterCnt);
        ImportStatistics.getInstance().setValue("MasterStyles", masterStylesCnt);
        ImportStatistics.getInstance().setValue("MasterGenres", masterGenresCnt);
        ImportStatistics.getInstance().setValue("MasterImage", masterImageCnt);
        ImportStatistics.getInstance().setValue("MasterImageOf", imageOfMasterCnt);
        ImportStatistics.getInstance().setValue("MasterArtist", masterArtistCnt);
        ImportStatistics.getInstance().setValue("MasterArtistPerforms", masterArtistPerformsCnt);
        ImportStatistics.getInstance().setValue("MasterVideo", masterVideoCnt);
        ImportStatistics.getInstance().setValue("MasterVideoOf", masterVideoOfCnt);

        con.setAutoCommit(true);
    }

    public void setupPreparedStatements() throws SQLException {
        con.setAutoCommit(false);
        insMaster = con.prepareStatement("INSERT INTO discogs.master_import (id, year, data_quality, title, main_release) VALUES (?, ?, ?, ?, ?)");
        insMasterStyles = con.prepareStatement("INSERT INTO discogs.master_styles_import (id, style) VALUES (?, ?)");
        insMasterGenres = con.prepareStatement("INSERT INTO discogs.master_genres_import (id, genre) VALUES (?, ?)");
        insMasterImages = con.prepareStatement("INSERT INTO discogs.master_images_import (id, uri, uri150, type, width, height) VALUES (?, ?, ?, ?, ?, ?)");
        insImagesOfMaster = con.prepareStatement("INSERT INTO discogs.images_of_master_import (image_id, master_id) VALUES (?, ?)");
        insMasterArtist = con.prepareStatement("INSERT INTO discogs.master_artist_import (id, name, role, join_att, anv) VALUES (?, ?, ?, ?, ?)");
        insMasterArtistPerforms = con.prepareStatement("INSERT INTO discogs.master_artist_performs_import (master_id, artist_id) VALUES (?, ?)");
        insMasterVideo = con.prepareStatement("INSERT INTO discogs.master_video_import (id, embed, source, description, duration, title) VALUES (?, ?, ?, ?, ?, ?)");
        insMasterVideoOf = con.prepareStatement("INSERT INTO discogs.video_of_master_import (video_id, master_id) VALUES (?, ?)");
    }

    public void insertMaster(MasterEntity _me) throws SQLException {
        insMaster.setInt(1, Integer.parseInt(_me.getId()));
        insMaster.setInt(2, Integer.parseInt(_me.getYear()));
        insMaster.setString(3, _me.getDataQuality().toString());
        insMaster.setString(4, _me.getTitle());
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
            insMasterImages.setInt(1, masterImageCnt+1);
            insMasterImages.setString(2, i.getUri());
            insMasterImages.setString(3, i.getUri150());
            insMasterImages.setString(4, i.getType().toString());
            insMasterImages.setInt(5, i.getWidth());
            insMasterImages.setInt(6, i.getHeight());
            insMasterImages.addBatch();
            masterImageCnt++;

            insImagesOfMaster.setInt(1, imageOfMasterCnt+1);
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

        for(MasterVideo mv: _me.getVideos()) {
            insMasterVideo.setInt(1, masterVideoCnt+1);
            insMasterVideo.setString(2, mv.getEmbed());
            insMasterVideo.setString(3, mv.getSourceUrl());
            insMasterVideo.setString(4, mv.getDescription());
            insMasterVideo.setString(5, mv.getDuration());
            insMasterVideo.setString(6, mv.getTitle());
            insMasterVideo.addBatch();
            masterVideoCnt++;

            insMasterVideoOf.setInt(1, masterVideoOfCnt+1);
            insMasterVideoOf.setInt(2, Integer.parseInt(_me.getId()));
            insMasterVideoOf.addBatch();
            masterVideoOfCnt++;
        }

        objectCounter++;
        if(objectCounter % insertTrigger == 0) {
            executeMasterBatchs();
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
