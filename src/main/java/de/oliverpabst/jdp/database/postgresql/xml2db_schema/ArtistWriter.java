package de.oliverpabst.jdp.database.postgresql.xml2db_schema;

import de.oliverpabst.jdp.DiscogsParser;
import de.oliverpabst.jdp.database.SchemaDoesNotExistException;
import de.oliverpabst.jdp.database.postgresql.PostgreSQLConnection;
import de.oliverpabst.jdp.model.xml2db_schema.artist.Artist;
import de.oliverpabst.jdp.model.xml2db_schema.artist.ArtistsImage;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ArtistWriter {

    private static ArtistWriter instance = null;

    private Integer artistImageCnt = 0;
    private Integer artistCnt = 0;

    private Integer insertTrigger = 1000;

    private PreparedStatement insArtist;
    private PreparedStatement insArtistImage;

    private Connection con;

    private ArtistWriter() {
        try {
            con = new PostgreSQLConnection(DiscogsParser.getConnectionParameters()).getConnection();
        } catch (SchemaDoesNotExistException e) {
            e.printStackTrace();
        }
    }

    public static ArtistWriter getInstance() {
        if(instance == null) {
            instance = new ArtistWriter();
        }
        return instance;
    }

    private void setupPreparedStatements() throws SQLException {
        con.setAutoCommit(false);
        insArtist = con.prepareStatement("INSERT INTO discogs.artist (id, name, realname, urls, namevariations, " +
                "aliases, releases, profile, members, groups, data_quality) VALUES (?,?,?,?,?,?,?,?,?,?,?)");
        insArtistImage = con.prepareStatement("INSERT INTO discogs.artists_image (artist_id, type, height, width, image_uri) " +
                " VALUES (?, ?, ?, ?, ?)");
    }

    public void insertArtist(Artist _a) throws SQLException {
        insArtist.setInt(1, _a.getId());
        insArtist.setString(2, _a.getName());
        insArtist.setString(3, _a.getRealname());
        Array namevariations = con.createArrayOf("text", _a.getNamevariations().toArray());
        insArtist.setArray(4, namevariations);
        Array aliases = con.createArrayOf("text", _a.getAliases().toArray());
        insArtist.setArray(6, aliases);
        insArtist.setString(8, _a.getProfile());

        if(artistCnt % insertTrigger == 0) {
            insArtist.execute();
        }
    }

    public void insertArtistImage(ArtistsImage _ai) throws SQLException {
        insArtistImage.setInt(1, _ai.getArtistId());
        insArtistImage.setString(2, _ai.getImage().getType().toString());
        insArtistImage.setInt(3, _ai.getImage().getHeight());
        insArtistImage.setInt(4, _ai.getImage().getWidth());
        insArtistImage.setString(5, _ai.getImage().getUri());
        insArtistImage.addBatch();
        artistImageCnt++;

        if(artistImageCnt % insertTrigger == 0) {
            insArtistImage.execute();
        }
    }

    public void disconnect() {

    }

    public void finalBatchExecute() throws SQLException{
        insArtistImage.execute();
        insArtist.execute();
    }

}
