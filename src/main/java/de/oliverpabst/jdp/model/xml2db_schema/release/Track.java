package de.oliverpabst.jdp.model.xml2db_schema.release;

public class Track {
    private Integer release_id;
    private String position;
    private Integer track_id;
    private String title;
    private String duration;
    private Integer trackno;

    public Track() {

    }

    public void setReleaseId(Integer _id) {
        release_id = _id;
    }

    public Integer getReleaseId() {
        return release_id;
    }

    public void setPosition(String _position) {
        position = _position;
    }

    public String getPosition() {
        return position;
    }
    public void setTrackId(Integer _id) {
        track_id = _id;
    }

    public Integer getTrackId() {
        return track_id;
    }

    public void setTitle(String _title) {
        title = _title;
    }

    public String getTitle() {
        return title;
    }

    public void setDuration(String _duration) {
        duration = _duration;
    }

    public String getDuration() {
        return duration;
    }

    public void setTrackNo(Integer _trackNumber) {
        trackno = _trackNumber;
    }

    public  Integer getTrackNumber() {
        return trackno;
    }
}
