package de.oliverpabst.jdp.database.postgresql.xml2db;

import de.oliverpabst.jdp.DiscogsParser;
import de.oliverpabst.jdp.database.SchemaDoesNotExistException;
import de.oliverpabst.jdp.database.postgresql.PostgreSQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ArtistWriter {

    private static ArtistWriter instance = null;

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
        insArtistImage = con.prepareStatement("");
    }

}
