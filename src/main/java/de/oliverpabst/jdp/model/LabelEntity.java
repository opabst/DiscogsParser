package de.oliverpabst.jdp.model;

import java.util.ArrayList;

public class LabelEntity {

    private String name;
    private String contactinfo;
    private ArrayList<LabelImage> images;
    private Integer id;
    private String profile;
    private ArtistDataQuality dataQuality;
    private ArrayList<String> urls;
    private ArrayList<LabelSublabel> sublabels;

    public LabelEntity() {
        images = new ArrayList<>();
        urls = new ArrayList<>();
        sublabels = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String _name) {
        name = _name;
    }

    public String getContactinfo() {
        return contactinfo;
    }

    public void setContactinfo(String _contactInfo) {
        contactinfo = _contactInfo;
    }

    public ArrayList<LabelImage> getImages() {
        return images;
    }

    public void addImage(LabelImage _image) {
        images.add(_image);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer _id) {
        id = _id;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String _profile) {
        profile = _profile;
    }

    public ArtistDataQuality getDataQuality() {
        return dataQuality;
    }

    public void setDataQuality(String _quality) {
        if(_quality.equals("Correct")) {
            dataQuality = ArtistDataQuality.CORRECT;
        } else if (_quality.equals("Needs Major Changes")) {
            dataQuality = ArtistDataQuality.NEEDS_MAJOR_CHANGES;
        } else if (_quality.equals("Needs Vote")) {
            dataQuality = ArtistDataQuality.NEEDS_VOTE;
        } else if (_quality.equals("Needs Minor Changes")) {
            dataQuality = ArtistDataQuality.NEEDS_MINOR_CHANGES;
        } else if (_quality.equals("Complete and Correct")) {
            dataQuality = ArtistDataQuality.COMPLETE_AND_CORRECT;
        } else if (_quality.equals("Entirely Incorrect")) {
            dataQuality = ArtistDataQuality.ENTIRELY_INCORRECT;
        } else {
            dataQuality = ArtistDataQuality.UNKNOWN;
            System.err.println("Unknown data quality: " + _quality);
        }
    }

    public ArrayList<String> getUrls() {
        return urls;
    }

    public void addUrl(String _url) {
        urls.add(_url);
    }

    public ArrayList<LabelSublabel> getSublabels() {
        return sublabels;
    }

    public void addSublabel(LabelSublabel _ls) {
        sublabels.add(_ls);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Artist: " + name + "\n");
        sb.append("Contactinfo: " + contactinfo + "\n");
        sb.append("Images: ");
        for(LabelImage i: images) {
            sb.append("  " + i.toString());
        }
        sb.append("ID: " + id + "\n");
        sb.append("Profile: " + profile + "\n");
        sb.append("ArtistDataQuality: " + dataQuality.name() + "\n");
        sb.append("NameVariations: ");
        for(String u: urls) {
            sb.append("  " + u + "\n");
        }
        for(LabelSublabel ls: sublabels) {
            sb.append("  " + ls.toString() + "\n");
        }
        return sb.toString();
    }
}

