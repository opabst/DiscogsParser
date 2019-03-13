package de.oliverpabst.jdp.database;

import de.oliverpabst.jdp.model.artist.ArtistEntity;
import de.oliverpabst.jdp.model.label.LabelEntity;
import de.oliverpabst.jdp.model.master.MasterEntity;
import de.oliverpabst.jdp.model.release.ReleaseEntity;

import java.sql.SQLException;

/**
 * Interface for database operations. Provides the following capabilities:
 *
 * - connect to database
 * - disconnect from database
 * - setup required statements
 * - provide access to execute prepared statements to insert parsed data to a selected database
 */

public interface DatabaseInterface {

    public void connect(ConnectionParameters _parameters);

    public void disconnect();

    public void executeAllBatchs() throws SQLException;

    public void executeArtistBatchs() throws SQLException;

    public void executeLabelBatchs() throws SQLException;

    public void executeMasterBatchs() throws SQLException;

    public void executeReleaseBatchs() throws SQLException;

    /**
     * Prepare all statements that are required to insert the *Entity objects in the database
     */
    public void setupPreparedStatements() throws SQLException;

    public void insertArtist(ArtistEntity _ae) throws SQLException;

    public void insertLabel(LabelEntity _le) throws SQLException ;

    public void insertMaster(MasterEntity _me) throws SQLException;

    public void insertRelease(ReleaseEntity _re) throws SQLException;
}
