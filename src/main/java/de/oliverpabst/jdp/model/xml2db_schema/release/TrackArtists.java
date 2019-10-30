package de.oliverpabst.jdp.model.xml2db_schema.release;

public class TrackArtists {

    private String track_id;
    private Integer position;
    private Integer artist_id;
    private String artist_name;
    private String anv;
    private String join_relation;

    public TrackArtists() {

    }

    public void setTrackId(String _id) {
        track_id = _id;
    }

    public String getTrackId() {
        return track_id;
    }

    public void setPosition(Integer _position) {
        position = _position;
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

    public void setJoinReleation(String _rel) {
        join_relation = _rel;
    }

    public String getJoinRelation() {
        return join_relation;
    }
}
