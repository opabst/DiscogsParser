package de.oliverpabst.jdp.database;

import de.oliverpabst.jdp.model.ArtistEntity;

/**
 * Interface for database operations. Provides the following capabilities:
 *
 * - connect to database
 * - disconnect from database
 * - setup required statements
 * - provide access to execute prepared statements to insert parsed data to a selected database
 */

public interface Database {

    public void connect(String _hostname, String _port, String _database, String _username, String _password);

    public void disconnect();

    public void setupPreparedStatements();

    public void insertArtist(ArtistEntity ae);

    public void insertLabel();

    public void insertMaster();

    public void insertRelease();
}
