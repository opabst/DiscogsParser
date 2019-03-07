package de.oliverpabst.jdp.database.postgresql;

import de.oliverpabst.jdp.database.ConnectionParameters;
import de.oliverpabst.jdp.database.DatabaseInterface;
import de.oliverpabst.jdp.model.artist.ArtistEntity;
import de.oliverpabst.jdp.model.label.LabelEntity;
import de.oliverpabst.jdp.model.master.MasterEntity;
import de.oliverpabst.jdp.model.release.ReleaseEntity;

public class PostgreSQLConnector implements DatabaseInterface {
    @Override
    public void connect(ConnectionParameters _parameters) {

    }

    @Override
    public void disconnect() {

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
