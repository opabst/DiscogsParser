package de.oliverpabst.jdp.model.xml2db_schema.label;

import de.oliverpabst.jdp.model.DataQuality;

import java.util.ArrayList;

public class Label {
    private Integer id;
    private String name;
    private String contactinfo;
    private String profile;
    private String parent_label;
    private ArrayList<String> sublabels;
    private ArrayList<String> urls;
    private DataQuality data_quality;

    public Label() {
        sublabels = new ArrayList<>();
        urls = new ArrayList<>();
    }

    public void setId(Integer _id) {
        id = _id;
    }

    public Integer getId() {
        return id;
    }

    public void setName(String _name) {
        name = name;
    }

    public String getName() {
        return name;
    }

    public void setContactinfo(String _contactinfo) {
        contactinfo = _contactinfo;
    }

    public String getContactinfo() {
        return contactinfo;
    }

    public void setProfile(String _profile) {
        profile = _profile;
    }

    public String getProfile() {
        return profile;
    }

    public void setParentLabel(String _parentLabel) {
        parent_label = _parentLabel;
    }

    public String getParentLabel() {
        return parent_label;
    }

    public void addSublabel(String _sublabel) {
        sublabels.add(_sublabel);
    }

    public ArrayList<String> getSublabels() {
        return sublabels;
    }

    public void addUrl(String _url) {
        urls.add(_url);
    }

    public ArrayList<String> getUrls() {
        return urls;
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
