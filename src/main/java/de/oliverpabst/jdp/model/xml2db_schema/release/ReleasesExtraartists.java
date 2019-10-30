package de.oliverpabst.jdp.model.xml2db_schema.release;

public class ReleasesExtraartists {
    private Integer release_id;
    private Integer artist_id;
    private String artist_name;
    private String anv;
    private String role;

    public ReleasesExtraartists() {

    }

    public void setReleaseId(Integer _id) {
        release_id = _id;
    }

    public Integer getReleaseId() {
        return release_id;
    }

    public void setArtistId(Integer _id) {
        artist_id = _id;
    }

    public Integer getArtistId() {
        return artist_id;
    }

    public void setArtistName(String _name) {
        artist_name = _name;
    }

    public String getArtistName() {
        return artist_name;
    }

    public void setAnv(String _anv) {
        anv = _anv;
    }

    public String getAnv() {
        return anv;
    }

    public void setRole(String _role)
    {
        role = _role;
    }

    public String getRole() {
        return role;
    }
}
