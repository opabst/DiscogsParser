package de.oliverpabst.jdp.model.xml2db_schema.release;

import de.oliverpabst.jdp.model.DataQuality;

public class TracksExtraartists {
    private String track_id;
    private Integer artist_id;
    private String artist_name;
    private String anv;
    private String role;
    private DataQuality data_quality;

    public TracksExtraartists() {

    }

    public void setTrackId(String _id) {
        track_id = _id;
    }

    public String getTrackId() {
        return track_id;
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

    private void setAnv(String _anv) {
        anv = _anv;
    }

    private String getAnv() {
        return anv;
    }

    public void setRole(String _role) {
        role = _role;
    }

    public String getRole() {
        return role;
    }

    public void setDataQuality(String _quality) {
        if(_quality.equals("Correct")) {
            data_quality = DataQuality.CORRECT;
        } else if (_quality.equals("Needs Major Changes")) {
            data_quality = DataQuality.NEEDS_MAJOR_CHANGES;
        } else if (_quality.equals("Needs Vote")) {
            data_quality = DataQuality.NEEDS_VOTE;
        } else if (_quality.equals("Needs Minor Changes")) {
            data_quality = DataQuality.NEEDS_MINOR_CHANGES;
        } else if (_quality.equals("Complete and Correct")) {
            data_quality = DataQuality.COMPLETE_AND_CORRECT;
        } else if (_quality.equals("Entirely Incorrect")) {
            data_quality = DataQuality.ENTIRELY_INCORRECT;
        } else {
            data_quality = DataQuality.UNKNOWN;
            System.err.println("Unknown data quality: " + _quality);
        }
    }

    public DataQuality getDataQuality() {
        return data_quality;
    }
}
