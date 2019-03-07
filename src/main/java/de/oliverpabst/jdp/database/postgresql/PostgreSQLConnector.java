package de.oliverpabst.jdp.database.postgresql;

import de.oliverpabst.jdp.database.ConnectionParameters;
import de.oliverpabst.jdp.database.DatabaseInterface;
import de.oliverpabst.jdp.model.artist.ArtistEntity;
import de.oliverpabst.jdp.model.label.LabelEntity;
import de.oliverpabst.jdp.model.master.MasterEntity;
import de.oliverpabst.jdp.model.release.ReleaseEntity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PostgreSQLConnector implements DatabaseInterface {

    private static PostgreSQLConnector instance;

    private Connection con;

    private PreparedStatement insArtist;
    private PreparedStatement insLabel;
    private PreparedStatement insMaster;
    private PreparedStatement insRelease;

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
    public void setupPreparedStatements() {

    }

    @Override
    public void insertArtist(ArtistEntity _ae) {

    }

    @Override
    public void insertLabel(LabelEntity _le) {

    }

    @Override
    public void insertMaster(MasterEntity _me) {

    }

    @Override
    public void insertRelease(ReleaseEntity _re) {

    }
}
