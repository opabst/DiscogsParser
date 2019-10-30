package de.oliverpabst.jdp.model.xml2db_schema.master;

import de.oliverpabst.jdp.model.DataQuality;

public class Master {
    private Integer id;
    private String title;
    private Integer main_release;
    private Integer year;
    private String notes;
    private String genres;
    private String styles;
    private String role;
    private DataQuality data_quality;

    public Master() {

    }

    public void setId(Integer _id) {
        id = _id;
    }

    public Integer getId() {
        return id;
    }

    public void setTitle(String _title) {
        title = _title;
    }

    public String getTitle() {
        return title;
    }

    public void setMainRelease(Integer _mainRelease) {
        main_release = _mainRelease;
    }

    public Integer getMainRelease() {
        return main_release;
    }

    public void setYear(Integer _year) {
        year = _year;
    }

    public Integer getYear() {
        return year;
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
