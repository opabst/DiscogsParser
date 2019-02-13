package de.oliverpabst.jdp.model;

import java.util.ArrayList;

public class ArtistEntity {

    private String name;
    private String realName;
    private ArrayList<ArtistImage> images;
    private Integer id;
    private String profile;
    private ArtistDataQuality dataQuality;
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

    public ArrayList<ArtistImage> getImages() {
        return images;
    }

    public void addImage(ArtistImage _image) {
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

    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Artist: " + name + "\n");
        sb.append("Realname: " + realName + "\n");
        sb.append("Images: ");
        for(ArtistImage i: images) {
            sb.append("  " + i.toString());
        }
        sb.append("ID: " + id + "\n");
        sb.append("Profile: " + profile + "\n");
        sb.append("ArtistDataQuality: " + dataQuality.name() + "\n");
        sb.append("NameVariations: ");
        for(String nv: nameVariations) {
            sb.append("  " + nv + "\n");
        }
        for(ArtistAlias aa: aliases) {
            sb.append("  " + aa.toString() + "\n");
        }
        return sb.toString();
    }
}
