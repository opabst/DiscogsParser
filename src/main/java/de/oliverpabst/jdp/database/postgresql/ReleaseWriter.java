package de.oliverpabst.jdp.database.postgresql;

import de.oliverpabst.jdp.DiscogsParser;
import de.oliverpabst.jdp.ImportStatistics;
import de.oliverpabst.jdp.database.SchemaDoesNotExistException;
import de.oliverpabst.jdp.model.Image;
import de.oliverpabst.jdp.model.release.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ReleaseWriter {

    private static ReleaseWriter instance = null;

    private Connection con;

    private Integer objectCounter = 0;

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

    private Integer insertTrigger = 10000;

    private Integer releaseCnt = 0;
    private Integer releaseStylesCnt = 0;
    private Integer releaseGenresCnt = 0;
    private Integer releaseArtistCnt = 0;
    private Integer releaseArtistOfCnt = 0;
    private Integer releaseExtraArtistCnt = 0;
    private Integer releaseExtraArtistOfCnt = 0;
    private Integer releaseIdentifierCnt = 0;
    private Integer releaseIdentifiesCnt = 0;
    private Integer releaseVideoCnt = 0;
    private Integer releaseVideoOfCnt = 0;
    private Integer releaseCompanyCnt = 0;
    private Integer releaseCompanyOfCnt = 0;
    private Integer releaseImageCnt = 0;
    private Integer releaseImageOfCnt = 0;
    private Integer releaseLabelCnt = 0;
    private Integer releaseLabelOfCnt = 0;

    private ReleaseWriter() {
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


    public static ReleaseWriter getInstance() {
        if(instance == null) {
            instance = new ReleaseWriter();
        }
        return instance;
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

    public void finalBatchExecute() throws SQLException {
        executeReleaseBatchs();
        con.commit();

        ImportStatistics.getInstance().setValue("Release", releaseCnt);
        ImportStatistics.getInstance().setValue("ReleaseStyles", releaseStylesCnt);
        ImportStatistics.getInstance().setValue("ReleaseGenres", releaseGenresCnt);
        ImportStatistics.getInstance().setValue("ReleaseArtist", releaseArtistCnt);
        ImportStatistics.getInstance().setValue("ReleaseArtistOf", releaseArtistOfCnt);
        ImportStatistics.getInstance().setValue("ReleaseExtraArtist", releaseExtraArtistCnt);
        ImportStatistics.getInstance().setValue("ReleaseExtraArtistOf", releaseExtraArtistOfCnt);
        ImportStatistics.getInstance().setValue("ReleaseIdentifier", releaseIdentifierCnt);
        ImportStatistics.getInstance().setValue("ReleaseIdentifies", releaseIdentifiesCnt);
        ImportStatistics.getInstance().setValue("ReleaseVideo", releaseVideoCnt);
        ImportStatistics.getInstance().setValue("ReleaseVideoOf", releaseVideoOfCnt);
        ImportStatistics.getInstance().setValue("ReleaseCompany", releaseCompanyCnt);
        ImportStatistics.getInstance().setValue("ReleaseCompanyOf", releaseCompanyOfCnt);
        ImportStatistics.getInstance().setValue("ReleaseImage", releaseImageCnt);
        ImportStatistics.getInstance().setValue("ReleaseImageOf", releaseImageOfCnt);
        ImportStatistics.getInstance().setValue("ReleaseLabel", releaseLabelCnt);
        ImportStatistics.getInstance().setValue("ReleaseLabelOf", releaseLabelOfCnt);

        con.setAutoCommit(true);
    }

    public void setupPreparedStatements() throws SQLException {
        con.setAutoCommit(false);
        insRelease = con.prepareStatement("INSERT INTO discogs.release_import (id, released, country, notes, status, title, data_quality) VALUES (?, ?, ?, ?, ?, ?, ?)");
        insReleaseStyles = con.prepareStatement("INSERT INTO discogs.release_styles_import (id, style) VALUES (?, ?)");
        insReleaseGenres = con.prepareStatement("INSERT INTO discogs.release_genres_import (id, genre) VALUES (?, ?)");
        insReleaseArtist = con.prepareStatement("INSERT INTO discogs.release_artist_import (id, name, role, join_att, anv) VALUES (?, ?, ?, ?, ?)");
        insArtistOfRelease = con.prepareStatement("INSERT INTO discogs.artist_of_release_import (release_id, artist_id) VALUES (?, ?)");
        insReleaseExtraartist = con.prepareStatement("INSERT INTO discogs.release_extraartist_import (id, name, role, join_att, anv) VALUES (?, ?, ?, ?, ?)");
        insExtraartistOfRelease = con.prepareStatement("INSERT INTO discogs.extraartist_of_release_import (release_id, artist_id) VALUES (?, ?)");
        insReleaseIdentifier = con.prepareStatement("INSERT INTO discogs.release_identifier_import (value, type, description) VALUES (?, ?, ?)");
        insIdentifies = con.prepareStatement("INSERT INTO discogs.identifies_import (release_id, identifier_value) VALUES (?, ?)");
        insReleaseVideo = con.prepareStatement("INSERT INTO discogs.release_video_import (src, duration, description, title, embed) VALUES (?, ?, ?, ?, ?)");
        insVideoOfRelease = con.prepareStatement("INSERT INTO discogs.video_of_release_import (release_id, video_src) VALUES (?, ?)");
        insReleaseCompany = con.prepareStatement("INSERT INTO discogs.release_company_import (id, resource_url, name, entity_type, entity_type_value, catno) VALUES (?, ?, ?, ?, ?, ?)");
        insCompanyOfRelease = con.prepareStatement("INSERT INTO discogs.company_of_release_import (release_id, company_id) VALUES (?, ?)");
        insReleaseImage = con.prepareStatement("INSERT INTO discogs.release_image_import (uri, uri150, type, width, heigth) VALUES (?, ?, ?, ?, ?)");
        insImageOfRelease = con.prepareStatement("INSERT INTO discogs.image_of_release_import (uri, release_id) VALUES (?, ?)");
        insReleaseLabel = con.prepareStatement("INSERT INTO discogs.release_label_import (id, catno, name) VALUES (?, ?, ?)");
        insLabelOfRelease = con.prepareStatement("INSERT INTO discogs.label_of_release_import (label_id, release_id) VALUES (?, ?)");
    }

    public void insertRelease(ReleaseEntity _re) throws SQLException {
        insRelease.setInt(1, Integer.parseInt(_re.getId()));
        insRelease.setString(2, _re.getReleased());
        insRelease.setString(3, _re.getCountry());
        insRelease.setString(4, _re.getNotes());
        insRelease.setString(5, _re.getStatus());
        insRelease.setString(6, _re.getTitle());
        insRelease.setString(7, _re.getDataQuality().toString());
        insRelease.addBatch();
        releaseCnt++;

        for(String genre: _re.getGenres()) {
            insReleaseGenres.setInt(1, Integer.parseInt(_re.getId()));
            insReleaseGenres.setString(2, genre);
            insReleaseGenres.addBatch();
            releaseGenresCnt++;
        }

        for(String style: _re.getStyles()) {
            insReleaseStyles.setInt(1, Integer.parseInt(_re.getId()));
            insReleaseStyles.setString(2, style);
            insReleaseStyles.addBatch();
            releaseStylesCnt++;
        }

        for(ReleaseArtist ra: _re.getArtists()) {
            insReleaseArtist.setInt(1, Integer.parseInt(ra.getId()));
            insReleaseArtist.setString(2, ra.getName());
            insReleaseArtist.setString(3, ra.getRole());
            insReleaseArtist.setString(4, ra.getAnv());
            insReleaseArtist.setString(5, ra.getJoin());
            insReleaseArtist.addBatch();
            releaseArtistCnt++;

            insArtistOfRelease.setInt(1, Integer.parseInt(_re.getId()));
            insArtistOfRelease.setInt(2, Integer.parseInt(ra.getId()));
            insArtistOfRelease.addBatch();
            releaseArtistOfCnt++;
        }

        for(ReleaseExtraArtist rea: _re.getExtraArtists()) {
            insReleaseExtraartist.setInt(1, Integer.parseInt(rea.getId()));
            insReleaseExtraartist.setString(2, rea.getName());
            insReleaseExtraartist.setString(3, rea.getRole());
            insReleaseExtraartist.setString(4, rea.getAnv());
            insReleaseExtraartist.setString(5, rea.getJoin());
            insReleaseExtraartist.addBatch();
            releaseExtraArtistCnt++;

            insExtraartistOfRelease.setInt(1, Integer.parseInt(_re.getId()));
            insExtraartistOfRelease.setInt(2, Integer.parseInt(rea.getId()));
            insExtraartistOfRelease.addBatch();
            releaseExtraArtistOfCnt++;
        }

        for(ReleaseIdentifier ri: _re.getIdentifiers()) {
            insReleaseIdentifier.setString(1, ri.getValue());
            insReleaseIdentifier.setString(2, ri.getType());
            insReleaseIdentifier.setString(3, ri.getDescription());
            insReleaseIdentifier.addBatch();
            releaseIdentifierCnt++;

            insIdentifies.setInt(1, Integer.parseInt(_re.getId()));
            insIdentifies.setString(2, ri.getValue());
            insIdentifies.addBatch();
            releaseIdentifiesCnt++;
        }

        for(ReleaseVideo rv: _re.getVideos()) {
            insReleaseVideo.setString(1, rv.getSrc());
            insReleaseVideo.setString(2, rv.getDuration());
            insReleaseVideo.setString(3, rv.getDescription());
            insReleaseVideo.setString(4, rv.getTitle());
            insReleaseVideo.setBoolean(5, rv.getEmbed());
            insReleaseVideo.addBatch();
            releaseVideoCnt++;

            insVideoOfRelease.setInt(1, Integer.parseInt(_re.getId()));
            insVideoOfRelease.setString(2, rv.getSrc());
            insVideoOfRelease.addBatch();
            releaseVideoOfCnt++;
        }

        for(ReleaseCompany rc: _re.getCompanies()) {
            insReleaseCompany.setInt(1, Integer.parseInt(rc.getId()));
            insReleaseCompany.setString(2, rc.getResourceUrl());
            insReleaseCompany.setString(3, rc.getName());
            insReleaseCompany.setString(4, rc.getEntityType());
            insReleaseCompany.setString(5, rc.getEntityTypeValue());
            insReleaseCompany.setString(6, rc.getCatno());
            insReleaseCompany.addBatch();
            releaseCompanyCnt++;

            insCompanyOfRelease.setInt(1, Integer.parseInt(_re.getId()));
            insCompanyOfRelease.setInt(2, Integer.parseInt(rc.getId()));
            insCompanyOfRelease.addBatch();
            releaseCompanyOfCnt++;
        }

        for(Image i: _re.getImages()) {
            insReleaseImage.setString(1, i.getUri());
            insReleaseImage.setString(2, i.getUri150());
            insReleaseImage.setString(3, i.getType().toString());
            insReleaseImage.setInt(4, i.getWidth());
            insReleaseImage.setInt(5, i.getHeight());
            insReleaseImage.addBatch();
            releaseImageCnt++;

            insImageOfRelease.setString(1, i.getUri());
            insImageOfRelease.setInt(2, Integer.parseInt(_re.getId()));
            insImageOfRelease.addBatch();
            releaseImageOfCnt++;
        }

        for(ReleaseLabel rl: _re.getLabels()) {
            insReleaseLabel.setInt(1, Integer.parseInt(rl.getId()));
            insReleaseLabel.setString(2, rl.getCatno());
            insReleaseLabel.setString(3, rl.getName());
            insReleaseLabel.addBatch();
            releaseLabelCnt++;

            insLabelOfRelease.setInt(1, Integer.parseInt(rl.getId()));
            insLabelOfRelease.setInt(2, Integer.parseInt(_re.getId()));
            insLabelOfRelease.addBatch();
            releaseLabelOfCnt++;
        }


        objectCounter++;
        if(objectCounter % insertTrigger == 0) {
            executeReleaseBatchs();
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
