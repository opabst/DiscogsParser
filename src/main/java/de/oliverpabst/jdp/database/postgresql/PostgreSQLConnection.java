package de.oliverpabst.jdp.database.postgresql;

import de.oliverpabst.jdp.database.ConnectionParameters;
import de.oliverpabst.jdp.database.SchemaDoesNotExistException;

import java.sql.*;

public class PostgreSQLConnection {

    private Connection con;
    private ConnectionParameters params;

    public PostgreSQLConnection(ConnectionParameters _params) {
        params = _params;
        try {
            connect(params);
        } catch (SchemaDoesNotExistException e) {
            e.printStackTrace();
        }
    }

    private void connect(ConnectionParameters _parameters) throws SchemaDoesNotExistException {
        String url = "jdbc:postgresql://" + _parameters.getHostname() + ":" + _parameters.getPort() + "/" + _parameters.getDatabasename();
        try {
            con = DriverManager.getConnection(url, _parameters.getUsername(), _parameters.getPassword());

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT schema_name FROM information_schema.schemata WHERE schema_name = 'discogs'");
            if(!rs.next()) {
                // Schema does not exist!
                throw new SchemaDoesNotExistException("Schema 'discogs' does not exist. Create schema before proceeding!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
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
