package de.oliverpabst.jdp.model.xml2db_schema.release;

import de.oliverpabst.jdp.model.DataQuality;

public class Release {
    private Integer id;
    private String status;
    private String title;
    private String country;
    private String released;
    private String barcode;
    private String notes;
    private String genres;
    private String styles;
    private Integer master_id;
    private DataQuality data_quality;

    public Release() {

    }

    public void setId(Integer _id) {
        id = _id;
    }

    public Integer getId() {
        return id;
    }

    public void setStatus(String _status) {
        status = _status;
    }

    public String getStatus() {
        return status;
    }

    public void setTitle(String _title) {
        title = _title;
    }

    public String getTitle() {
        return title;
    }

    public void setCountry(String _country) {
        country = _country;
    }

    public String getCountry() {
        return country;
    }

    public void setReleased(String _released) {
        released = _released;
    }

    public String getReleased() {
        return released;
    }

    public void setBarcode(String _barcode) {
        barcode = _barcode;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setNotes(String _notes) {
        notes = _notes;
    }

    public String getNotes() {
        return notes;
    }

    public void setGenres(String _genres) {
        genres = _genres;
    }

    public String getGenres() {
        return genres;
    }

    public void setStyles(String _styles) {
        styles = _styles;
    }

    public String getStyles() {
        return styles;
    }

    public void setMasterId(Integer _id) {
        master_id = _id;
    }

    public Integer getMasterId() {
        return master_id;
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
