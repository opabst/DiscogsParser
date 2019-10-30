package de.oliverpabst.jdp.model.xml2db_schema.master;

public class MasterArtists {
    private String artist_name;
    private Integer master_id;

    public MasterArtists() {

    }

    public void setArtistName(String _artistName) {
        artist_name = _artistName;
    }

    public String getArtistName() {
        return artist_name;
    }

    public void setMasterId(Integer _masterId) {
        master_id = _masterId;
    }

    public Integer getMasterId() {
        return master_id;
    }
}
