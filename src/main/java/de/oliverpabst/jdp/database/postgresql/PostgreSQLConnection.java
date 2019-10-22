package de.oliverpabst.jdp.database.postgresql;

import de.oliverpabst.jdp.database.ConnectionParameters;
import de.oliverpabst.jdp.database.SchemaDoesNotExistException;
import de.oliverpabst.jdp.database.SchemaType;

import java.sql.*;

public class PostgreSQLConnection {

    private Connection con;
    private ConnectionParameters params;

    public PostgreSQLConnection(ConnectionParameters _params) throws SchemaDoesNotExistException {
        params = _params;
        connect(params);
    }

    private void connect(ConnectionParameters _parameters) throws SchemaDoesNotExistException {
        String url = "jdbc:postgresql://" + _parameters.getHostname() + ":" + _parameters.getPort() + "/" + _parameters.getDatabasename();
        try {
            con = DriverManager.getConnection(url, _parameters.getUsername(), _parameters.getPassword());

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT schema_name FROM information_schema.schemata WHERE schema_name = 'discogs'");
            if(!rs.next()) {
                // Schema does not exist!
                rs.close();
                stmt.close();
                con.close();
                throw new SchemaDoesNotExistException("Schema 'discogs' does not exist. Create schema before proceeding!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public SchemaType getSchemaType() {
        String schema = null;
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM information_schema.tables WHERE table_schema = 'discogs' AND table_name = 'artists_image'");
            if(rs.next()) {
                schema = "XML2DB";
            } else {
                schema = "JDP";
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(schema.equals("JDP")) {
            return SchemaType.JDP_SCHEMA;
        } else {
            return SchemaType.XML2DB_SCHEMA;
        }
    }

    public Connection getConnection() {
        return con;
    }

    public void disconnect() {
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
