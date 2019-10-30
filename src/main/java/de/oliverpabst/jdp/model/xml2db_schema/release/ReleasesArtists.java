package de.oliverpabst.jdp.model.xml2db_schema.release;

public class ReleasesArtists {
    private Integer release_id;
    private Integer position;
    private Integer artist_id;
    private String artist_name;
    private String anv;
    private String join_relation;

    public ReleasesArtists() {

    }

    public void setReleaseId(Integer _id) {
        release_id = _id;
    }

    public Integer getReleaseId() {
        return release_id;
    }

    public void setPosition(Integer _postition) {
        position = _postition;
    }

    public Integer getPosition() {
        return position;
    }

    public void setArtistId(Integer _id) {
        artist_id = _id;
    }

    public Integer getArtistId() {
        return artist_id;
    }

    public void setArtistName(String _artistName) {
        artist_name = _artistName;
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


    public void setJoinRelation(String _joinRelation) {
        join_relation = _joinRelation;
    }

    public String getJoinReleation() {
        return join_relation;
    }
}
