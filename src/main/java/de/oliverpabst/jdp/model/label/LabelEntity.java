package de.oliverpabst.jdp.model.label;

import de.oliverpabst.jdp.model.DataQuality;
import de.oliverpabst.jdp.model.Image;

import java.util.ArrayList;

public class LabelEntity {

    private String name;
    private StringBuilder contactinfo;
    private ArrayList<Image> images;
    private String id;
    private StringBuilder profile;
    private DataQuality dataQuality;
    private ArrayList<String> urls;
    private ArrayList<LabelSublabel> sublabels;

    public LabelEntity() {
        images = new ArrayList<>();
        urls = new ArrayList<>();
        sublabels = new ArrayList<>();
        contactinfo = new StringBuilder();
        profile = new StringBuilder();
    }

    public String getName() {
        return name;
    }

    public void setName(String _name) {
        name = _name;
    }

    public String getContactinfo() {
        return contactinfo.toString();
    }

    public void setContactinfo(String _contactInfo) {
        contactinfo.append(_contactInfo);
    }

    public ArrayList<Image> getImages() {
        return images;
    }

    public void addImage(Image _image) {
        images.add(_image);
    }

    public String getId() {
        return id;
    }

    public void setId(String _id) {
        id = _id;
    }

    public String getProfile() {
        return profile.toString();
    }

    public void setProfile(String _profile) {
        profile.append(_profile);
    }

    public DataQuality getDataQuality() {
        return dataQuality;
    }

    public void setDataQuality(String _quality) {
        if(_quality.equals("Correct")) {
            dataQuality = DataQuality.CORRECT;
        } else if (_quality.equals("Needs Major Changes")) {
            dataQuality = DataQuality.NEEDS_MAJOR_CHANGES;
        } else if (_quality.equals("Needs Vote")) {
            dataQuality = DataQuality.NEEDS_VOTE;
        } else if (_quality.equals("Needs Minor Changes")) {
            dataQuality = DataQuality.NEEDS_MINOR_CHANGES;
        } else if (_quality.equals("Complete and Correct")) {
            dataQuality = DataQuality.COMPLETE_AND_CORRECT;
        } else if (_quality.equals("Entirely Incorrect")) {
            dataQuality = DataQuality.ENTIRELY_INCORRECT;
        } else {
            dataQuality = DataQuality.UNKNOWN;
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
        for(Image i: images) {
            sb.append("  " + i.toString());
        }
        sb.append("ID: " + id + "\n");
        sb.append("Profile: " + profile + "\n");
        sb.append("DataQuality: " + dataQuality.name() + "\n");
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

