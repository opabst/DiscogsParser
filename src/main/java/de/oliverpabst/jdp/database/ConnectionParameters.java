package de.oliverpabst.jdp.database;

public class ConnectionParameters {
    private String hostname;
    private String port;
    private String databasename;
    private String username;
    private String password;

    public ConnectionParameters(String _hostname, String _port, String _databasename, String _username, String _password) {
        hostname = _hostname;
        port = _port;
        databasename = _databasename;
        username = _username;
        password = _password;
    }

    public String getHostname() {
        return hostname;
    }

    public String getPort() {
        return port;
    }

    public String getDatabasename() {
        return databasename;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
