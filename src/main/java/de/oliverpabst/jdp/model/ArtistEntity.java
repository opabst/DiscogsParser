package de.oliverpabst.jdp.model;

import javax.xml.crypto.Data;
import java.util.ArrayList;

public class ArtistEntity {

    private String name;
    private String realName;
    private ArrayList<Image> images;
    private Integer id;
    private String profile;
    private DataQuality dataQuality;
    private ArrayList<String> nameVariations;
    private ArrayList<ArtistAlias> aliases;

    public ArtistEntity() {
        images = new ArrayList<>();
        nameVariations = new ArrayList<>();
        aliases = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String _name) {
        name = _name;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String _realName) {
        realName = _realName;
    }

    public ArrayList<Image> getImages() {
        return images;
    }

    public void addImage(Image _image) {
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
        } else {
            dataQuality = DataQuality.UNKNOWN;
            System.err.println("Unknown data quality: " + _quality);
        }
    }

    public ArrayList<String> getNameVariations() {
        return nameVariations;
    }

    public void addNameVariation(String _variation) {
        nameVariations.add(_variation);
    }

    public ArrayList<ArtistAlias> getAliases() {
        return aliases;
    }

    public void addAlias(ArtistAlias _aa) {
        aliases.add(_aa);
    }
}
