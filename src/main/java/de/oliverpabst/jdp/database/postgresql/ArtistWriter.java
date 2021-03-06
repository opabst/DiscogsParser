package de.oliverpabst.jdp.database.postgresql;

import de.oliverpabst.jdp.DiscogsParser;
import de.oliverpabst.jdp.ImportStatistics;
import de.oliverpabst.jdp.database.SchemaDoesNotExistException;
import de.oliverpabst.jdp.model.Image;
import de.oliverpabst.jdp.model.artist.ArtistAlias;
import de.oliverpabst.jdp.model.artist.ArtistEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ArtistWriter {

    private static ArtistWriter instance = null;

    private Connection con;

    private Integer objectCounter = 0;

    // ArtistEntity
    private PreparedStatement insArtist;
    private PreparedStatement insArtistNameVariations;
    private PreparedStatement insArtistAlias;
    private PreparedStatement insAliasOfArtist;
    private PreparedStatement insArtistImage;
    private PreparedStatement insImageOfArtist;

    private final Integer insertTrigger = 1000;

    private Integer artistCnt = 0;
    private Integer artistNameVariationCnt = 0;
    private Integer artistAliasCnt = 0;
    private Integer aliasOfArtistCnt = 0;
    private Integer artistImageCnt = 0;
    private Integer imageOfArtistCnt = 0;

    private ArtistWriter() {
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

    public static ArtistWriter getInstance() {
        if(instance == null) {
            instance = new ArtistWriter();
        }
        return instance;
    }


    public void executeArtistBatchs() throws SQLException {
        insArtist.executeBatch();
        insArtistNameVariations.executeBatch();
        insArtistAlias.executeBatch();
        insAliasOfArtist.executeBatch();
        insArtistImage.executeBatch();
        insImageOfArtist.executeBatch();
    }

    public void finalBatchExecute() throws SQLException {
        executeArtistBatchs();
        con.commit();

        ImportStatistics.getInstance().setValue("Artist", artistCnt);
        ImportStatistics.getInstance().setValue("ArtistNameVariation", artistNameVariationCnt);
        ImportStatistics.getInstance().setValue("ArtistAlias", artistAliasCnt);
        ImportStatistics.getInstance().setValue("AliasOfArtist", aliasOfArtistCnt);
        ImportStatistics.getInstance().setValue("ArtistImage", artistImageCnt);
        ImportStatistics.getInstance().setValue("ArtistImageOf", imageOfArtistCnt);

        con.setAutoCommit(true);
    }

    private void setupPreparedStatements() throws SQLException {
        con.setAutoCommit(false);
        insArtist = con.prepareStatement("INSERT INTO discogs.artist_import (id, name, realname, data_quality, profile) VALUES (?, ?, ?, ?, ?)");
        insArtistNameVariations = con.prepareStatement("INSERT INTO discogs.artist_namevariations_import (id, name_variation) VALUES (?, ?)");
        insArtistAlias = con.prepareStatement("INSERT INTO discogs.artist_alias_import (id, alias) VALUES (?, ?)");
        insAliasOfArtist = con.prepareStatement("INSERT INTO discogs.alias_of_artist_import(artist_id, alias_id, alias_name) VALUES (?, ?, ?)");
        insArtistImage = con.prepareStatement("INSERT INTO discogs.artist_image_import (id, uri, uri150, type, width, height) VALUES (?, ?, ?, ?, ?, ?)");
        insImageOfArtist = con.prepareStatement("INSERT INTO discogs.image_of_artist_import (image_id, artist_id) VALUES (?, ?)");
    }

    public void insertArtist(ArtistEntity _ae) throws SQLException {
        insArtist.setInt(1, Integer.parseInt(_ae.getId()));
        insArtist.setString(2, _ae.getName());
        insArtist.setString(3, _ae.getRealName());
        insArtist.setString(4, _ae.getDataQuality().toString());
        insArtist.setString(5, _ae.getProfile());
        insArtist.addBatch();
        artistCnt++;

        for(String nv: _ae.getNameVariations()) {
            insArtistNameVariations.setInt(1, Integer.parseInt(_ae.getId()));
            insArtistNameVariations.setString(2, nv);
            insArtistNameVariations.addBatch();
            artistNameVariationCnt++;
        }

        for(ArtistAlias aa: _ae.getAliases()) {
            insArtistAlias.setInt(1, Integer.parseInt(aa.getAliasID()));
            insArtistAlias.setString(2, aa.getAliasName());
            insArtistAlias.addBatch();
            artistAliasCnt++;

            insAliasOfArtist.setInt(1, Integer.parseInt(_ae.getId()));
            insAliasOfArtist.setInt(2, Integer.parseInt(aa.getAliasID()));
            insAliasOfArtist.setString(3, aa.getAliasName());
            insAliasOfArtist.addBatch();
            aliasOfArtistCnt++;
        }

        for(Image i: _ae.getImages()) {
            insArtistImage.setInt(1, artistImageCnt+1);
            insArtistImage.setString(2, i.getUri());
            insArtistImage.setString(3, i.getUri150());
            insArtistImage.setString(4, i.getType().toString());
            insArtistImage.setInt(5, i.getWidth());
            insArtistImage.setInt(6, i.getHeight());
            insArtistImage.addBatch();
            artistImageCnt++;

            insImageOfArtist.setInt(1, imageOfArtistCnt+1);
            insImageOfArtist.setInt(2, Integer.parseInt(_ae.getId()));
            insImageOfArtist.addBatch();
            imageOfArtistCnt++;
        }

        objectCounter++;
        if(objectCounter % insertTrigger == 0) {
            executeArtistBatchs();
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
