package de.oliverpabst.jdp.database.postgresql.xml2db_schema;

import de.oliverpabst.jdp.DiscogsParser;
import de.oliverpabst.jdp.database.SchemaDoesNotExistException;
import de.oliverpabst.jdp.database.postgresql.PostgreSQLConnection;
import de.oliverpabst.jdp.model.xml2db_schema.master.*;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MasterWriter {

    private static MasterWriter instance = null;

    private Integer insertTrigger = 1000;

    private Integer insMasterCnt = 0;
    private Integer insMasterArtistCnt = 0;
    private Integer insMasterArtistsJoinsCnt = 0;
    private Integer insMastersExtraartistsCnt = 0;
    private Integer insMastersFormatsCnt = 0;
    private Integer insMastersImagesCnt = 0;

    private PreparedStatement insMaster;
    private PreparedStatement insMasterArtists;
    private PreparedStatement insMastersArtistsJoins;
    private PreparedStatement insMastersExtraartists;
    private PreparedStatement insMastersFormats;
    private PreparedStatement insMastersImages;

    private Connection con;

    private MasterWriter() {
        try {
            con = new PostgreSQLConnection(DiscogsParser.getConnectionParameters()).getConnection();
        } catch (SchemaDoesNotExistException e) {
            System.err.println(e.getMessage());
        }
    }

    public static MasterWriter getInstance() {
        if(instance == null) {
            instance = new MasterWriter();
        }
        return instance;
    }

    private void setupPreparedStatements() throws SQLException {
        insMaster = con.prepareStatement("INSERT INTO discogs.master (id, title, main_release, year, notes, genres, styles, role, data_quality) " +
                " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");

        insMasterArtists = con.prepareStatement("INSERT INTO discogs.master_artists (artist_name, master_id) " +
                " VALUES (?, ?)");

        insMastersArtistsJoins = con.prepareStatement("INSERT INTO discogs.masters_artists_joins (artist1, artist2, join_releation, master_id) + " +
                " VALUES (?, ?, ?, ?)");

        insMastersExtraartists = con.prepareStatement("INSERT INTO discogs.masters_extraartists (master_id, artist_name, roles) +" +
                " VALUES (?, ?, ?)");

        insMastersFormats = con.prepareStatement("INSERT INTO discogs.masters_formats (master_id, format_name, qty, descriptions) " +
                " VALUES (?, ?, ?, ?)");

        insMastersImages = con.prepareStatement("INSERT INTO discogs.masters_images (master_id, type, height, width, image_uri) " +
                " VALUES (?, ?, ?, ?, ?)");
    }

    public void insertMaster(Master _m) throws SQLException {
        insMaster.setInt(1, _m.getId());
        insMaster.setString(2, _m.getTitle());
        insMaster.setInt(3, _m.getMainRelease());
        insMaster.setInt(4, _m.getYear());
        insMaster.setString(5, _m.getNotes());
        insMaster.setString(6, _m.getGenres());
        insMaster.setString(7, _m.getStyles());
        insMaster.setString(8, _m.getRole());
        insMaster.setString(9, _m.getDataQuality().toString());
        insMaster.addBatch();
        insMasterCnt++;

        if(insMasterCnt % insertTrigger == 0) {
            insMaster.execute();
        }
    }

    public void insertMasterArtists(MasterArtists _ma) throws SQLException {
        insMasterArtists.setString(1, _ma.getArtistName());
        insMasterArtists.setInt(2, _ma.getMasterId());
        insMasterArtists.addBatch();
        insMasterCnt++;

        if(insMasterArtistCnt % insertTrigger == 0) {
            insMasterArtists.execute();
        }
    }

    public void insertMastersArtistsJoins(MastersArtistsJoins _maj) throws SQLException {
        insMastersArtistsJoins.setString(1, _maj.getArtist1());
        insMastersArtistsJoins.setString(2, _maj.getArtist2());
        insMastersArtistsJoins.setString(3, _maj.getJoinRelation());
        insMastersArtistsJoins.setInt(4, _maj.getMasterId());
        insMastersArtistsJoins.addBatch();
        insMasterArtistsJoinsCnt++;

        if(insMasterArtistsJoinsCnt % insertTrigger == 0) {
            insMastersArtistsJoins.execute();
        }
    }

    public void insertMastersExtraartists(MastersExtraartists _mea) throws SQLException {
        insMastersExtraartists.setInt(1, _mea.getMasterId());
        insMastersExtraartists.setString(2, _mea.getArtistName());
        Array roles = con.createArrayOf("text", _mea.getRoles().toArray());
        insMastersExtraartists.setArray(3, roles);
        insMastersExtraartists.addBatch();
        insMastersExtraartistsCnt++;

        if(insMastersExtraartistsCnt % insertTrigger == 0) {
            insMastersExtraartists.execute();
        }
    }

    public void insertMastersFormats(MastersFormats _mf) throws SQLException {
        insMastersFormats.setInt(1, _mf.getMasterId());
        insMastersFormats.setString(2, _mf.getFormatName());
        insMastersFormats.setInt(3, _mf.getQty());
        Array descriptions = con.createArrayOf("text", _mf.getDescriptions().toArray());
        insMastersFormats.setArray(4, descriptions);

        insMastersFormats.addBatch();
        insMastersFormatsCnt++;

        if(insMastersFormatsCnt % insertTrigger == 0) {
            insMastersFormats.execute();
        }
    }

    public void insertMastersImages(MastersImages _mi) throws SQLException {
        insMastersImages.setInt(1, _mi.getMasterId());
        insMastersImages.setString(2, _mi.getImage().getType().toString());
        insMastersImages.setInt(3, _mi.getImage().getHeight());
        insMastersImages.setInt(4, _mi.getImage().getWidth());
        insMastersImages.setString(5, _mi.getImage().getUri());

        insMastersImages.addBatch();
        insMastersImagesCnt++;

        if(insMastersImagesCnt % insertTrigger == 0) {
            insMastersImages.execute();
        }
    }

    public void finalBatchExecute() throws SQLException {
        insMaster.execute();
        insMasterArtists.execute();
        insMastersArtistsJoins.execute();
        insMastersExtraartists.execute();
        insMastersFormats.execute();
        insMastersImages.execute();
    }

    public void disconnect() {
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
