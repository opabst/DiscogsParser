package de.oliverpabst.jdp.database.postgresql.xml2db_schema;

import de.oliverpabst.jdp.DiscogsParser;
import de.oliverpabst.jdp.database.SchemaDoesNotExistException;
import de.oliverpabst.jdp.database.postgresql.PostgreSQLConnection;
import de.oliverpabst.jdp.model.xml2db_schema.release.*;

import java.math.BigDecimal;
import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ReleaseWriter {

    private static ReleaseWriter instance = null;

    private Integer insertTrigger = 1000;

    private Integer insReleaseCnt = 0;
    private Integer insReleasesArtistsCnt = 0;
    private Integer insReleasesExtraartistsCnt = 0;
    private Integer insReleasesFormatsCnt = 0;
    private Integer insReleasesImagesCnt = 0;
    private Integer insReleasesLabelsCnt = 0;
    private Integer insTrackCnt = 0;
    private Integer insTrackArtistsCnt = 0;
    private Integer insTracksExtraartistsCnt = 0;

    private PreparedStatement insRelease;
    private PreparedStatement insReleasesArtists;
    private PreparedStatement insReleasesExtraartists;
    private PreparedStatement insReleasesFormats;
    private PreparedStatement insReleasesImages;
    private PreparedStatement insReleasesLabels;
    private PreparedStatement insTrack;
    private PreparedStatement insTrackArtists;
    private PreparedStatement insTracksExtraartists;

    private Connection con;

    private ReleaseWriter() {
        try {
            con = new PostgreSQLConnection(DiscogsParser.getConnectionParameters()).getConnection();
        } catch (SchemaDoesNotExistException e) {
            e.printStackTrace();
        }
    }

    public static ReleaseWriter getInstance() {
        if(instance == null) {
            instance = new ReleaseWriter();
        }
        return instance;
    }

    private void setupPreparedStatements() throws SQLException {
        insRelease = con.prepareStatement("INSERT INTO discogs.release (id, status, title, country, released, barcode, notes, styles, master_id, data_quality) " +
                " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

        insReleasesArtists = con.prepareStatement("INSERT INTO discogs.releases_artists (release_id, position, artist_id, artist_name, anv, join_releation) " +
                " VALUES (?, ?, ?, ?, ?, ?)");

        insReleasesExtraartists = con.prepareStatement("INSERT INTO discogs.releases_extraartists (release_id, artist_id, artist_name, anv, role) " +
                " VALUES (?, ?, ?, ?, ?)");

        insReleasesFormats = con.prepareStatement("INSERT INTO discogs.releases_formats (release_id, position, format_name, qty, descriptions) " +
                " VALUES (?, ?, ?, ?, ?)");

        insReleasesImages = con.prepareStatement("INSERT INTO discogs.releases_images (release_id, type, height, width, image_uri) " +
                " VALUES (?, ?, ?, ?, ?)");

        insReleasesLabels = con.prepareStatement("INSERT INTO discogs.release_labels (label, release_id, catno) " +
                " VALUES (?, ?, ?)");

        insTrack = con.prepareStatement("INSERT INTO discogs.track (release_id, position, track_id, title, duration, trackno) " +
                " VALUES (?, ?, ?, ?, ?, ?)");

        insTrackArtists = con.prepareStatement("INSERT INTO discogs.track_artists (track_id, position, artist_id, artist_name, anv, join_relation) " +
                " VALUES (?, ?, ?, ?, ?, ?)");

        insTracksExtraartists = con.prepareStatement("INSERT INTO discogs.tracks_extraartists (track_id, artist_id, artist_name, anv, role, data_quality) " +
                " VALUES (?, ?, ?, ?, ?, ?)");
    }

    public void insertRelease(Release _r) throws SQLException {
        insRelease.setInt(1, _r.getId());
        insRelease.setString(2, _r.getStatus());
        insRelease.setString(3, _r.getTitle());
        insRelease.setString(4, _r.getCountry());
        insRelease.setString(5, _r.getReleased());
        insRelease.setString(6, _r.getBarcode());
        insRelease.setString(7, _r.getNotes());
        insRelease.setString(8, _r.getGenres());
        insRelease.setString(9, _r.getStyles());
        insRelease.setInt(10, _r.getMasterId());
        insRelease.setString(11, _r.getDataQuality().toString());
        insRelease.addBatch();
        insReleaseCnt++;

        if(insReleaseCnt % insertTrigger == 0) {
            insRelease.execute();
        }

    }

    public void insertReleasesArtists(ReleasesArtists _ra) throws SQLException {
        insReleasesArtists.setInt(1, _ra.getReleaseId());
        insReleasesArtists.setInt(2, _ra.getPosition());
        insReleasesArtists.setInt(3, _ra.getArtistId());
        insReleasesArtists.setString(4, _ra.getArtistName());
        insReleasesArtists.setString(5, _ra.getAnv());
        insReleasesArtists.setString(6, _ra.getJoinReleation());
        insReleasesArtists.addBatch();
        insReleasesArtistsCnt++;

        if(insReleasesArtistsCnt % insertTrigger == 0) {
            insReleasesArtists.execute();
        }
    }

    public void insertReleasesExtraartists(ReleasesExtraartists _rea) throws SQLException {
        insReleasesExtraartists.setInt(1, _rea.getReleaseId());
        insReleasesExtraartists.setInt(2, _rea.getArtistId());
        insReleasesExtraartists.setString(3, _rea.getArtistName());
        insReleasesExtraartists.setString(4, _rea.getAnv());
        insReleasesExtraartists.setString(5, _rea.getRole());
        insReleasesExtraartists.addBatch();
        insReleasesExtraartistsCnt++;

        if(insReleasesExtraartistsCnt % insertTrigger == 0) {
            insReleasesExtraartists.execute();
        }
    }

    public void insertReleasesFormats(ReleasesFormats _rf) throws SQLException {
        insReleasesFormats.setInt(1, _rf.getReleaseId());
        insReleasesFormats.setInt(2, _rf.getPosition());
        insReleasesFormats.setString(3, _rf.getFormatName());
        insReleasesFormats.setBigDecimal(4, BigDecimal.valueOf(_rf.getQty()));
        Array descriptions = con.createArrayOf("text", _rf.getDescriptions().toArray());
        insReleasesFormats.setArray(5, descriptions);

        insReleasesFormats.addBatch();
        insReleasesFormatsCnt++;

        if(insReleasesFormatsCnt % insertTrigger == 0) {
            insReleasesFormats.execute();
        }
    }

    public void insertReleasesImages(ReleasesImages _ri) throws SQLException {
        insReleasesImages.setInt(1, _ri.getReleaseId());
        insReleasesImages.setString(2, _ri.getImage().getType().toString());
        insReleasesImages.setInt(3, _ri.getImage().getHeight());
        insReleasesImages.setInt(4, _ri.getImage().getWidth());
        insReleasesImages.setString(5, _ri.getImage().getUri());

        insReleasesImages.addBatch();
        insReleasesImagesCnt++;

        if(insReleasesImagesCnt % insertTrigger == 0) {
            insReleasesImages.execute();
        }
    }

    public void insertReleasesLabels(ReleasesLabels _rl) throws SQLException {
        insReleasesLabels.setString(1, _rl.getLabel());
        insReleasesLabels.setInt(2, _rl.getReleaseId());
        insReleasesLabels.setString(3, _rl.getCatno());
        insReleasesLabels.addBatch();
        insReleasesLabelsCnt++;

        if(insReleasesLabelsCnt % insertTrigger == 0) {
            insReleasesLabels.execute();
        }
    }

    public void insertTrack(Track _t) throws SQLException {
        insTrack.setInt(1, _t.getReleaseId());
        insTrack.setString(2, _t.getPosition());
        insTrack.setInt(3, _t.getTrackId());
        insTrack.setString(4, _t.getTitle());
        insTrack.setString(5, _t.getDuration());
        insTrack.setInt(7, _t.getTrackNumber());
        insTrack.addBatch();
        insTrackCnt++;

        if(insTrackCnt % insertTrigger == 0) {
            insTrack.execute();
        }
    }

    public void insertTrackArtists(TrackArtists _ta) throws SQLException {
        insTrackArtists.setString(1, _ta.getTrackId());
        insTrackArtists.setInt(2, _ta.getPosition());
        insTrackArtists.setInt(3, _ta.getArtistId());
        insTrackArtists.setString(4, _ta.getArtistName());
        insTrackArtists.setString(5, _ta.getAnv());
        insTrackArtists.setString(6, _ta.getJoinRelation());
        insTrackArtists.addBatch();
        insTrackArtistsCnt++;

        if(insTrackArtistsCnt % insertTrigger == 0) {
            insTrackArtists.execute();
        }
    }

    public void insertTracksExtraartists(TracksExtraartists _tea) throws SQLException {
        insTracksExtraartists.setString(1, _tea.getTrackId());
        insTracksExtraartists.setInt(2, _tea.getArtistId());
        insTracksExtraartists.setString(3, _tea.getArtistName());
        insTracksExtraartists.setString(4, _tea.getAnv());
        insTracksExtraartists.setString(5, _tea.getRole());
        insTracksExtraartists.setString(6, _tea.getDataQuality().toString());
        insTracksExtraartists.addBatch();
        insTracksExtraartistsCnt++;

        if(insTracksExtraartistsCnt % insertTrigger == 0) {
            insTracksExtraartists.execute();
        }
    }

    public void finalBatchExecute() throws SQLException {
        insRelease.execute();
        insReleasesArtists.execute();
        insReleasesExtraartists.execute();
        insReleasesFormats.execute();
        insReleasesImages.execute();
        insReleasesLabels.execute();
        insTrack.execute();
        insTrackArtists.execute();
        insTracksExtraartists.execute();
    }

    public void disconnect() {
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
