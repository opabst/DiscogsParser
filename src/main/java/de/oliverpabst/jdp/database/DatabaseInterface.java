package de.oliverpabst.jdp.database;

import de.oliverpabst.jdp.model.artist.ArtistEntity;
import de.oliverpabst.jdp.model.label.LabelEntity;
import de.oliverpabst.jdp.model.master.MasterEntity;
import de.oliverpabst.jdp.model.release.ReleaseEntity;

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

    /**
     * Prepare all statements that are required to insert the *Entity objects in the database
     */
    public void setupPreparedStatements();

    public void insertArtist(ArtistEntity _ae);

    public void insertLabel(LabelEntity _le);

    public void insertMaster(MasterEntity _me);

    public void insertRelease(ReleaseEntity _re);
}
