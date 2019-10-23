package de.oliverpabst.jdp.model.xml2db_schema.artist;

import de.oliverpabst.jdp.model.DataQuality;

import java.util.ArrayList;

public class Artist {

    private Integer id;
    private String name;
    private String realname;
    private ArrayList<String> urls;
    private ArrayList<String> namevariations;
    private ArrayList<String> aliases;
    private ArrayList<String> releases;
    private String profile;
    private ArrayList<String> members;
    private ArrayList<String> groups;
    private DataQuality data_quality;

    public Artist() {
        urls = new ArrayList<>();
        namevariations = new ArrayList<>();
        aliases = new ArrayList<>();
        releases = new ArrayList<>();
        members = new ArrayList<>();
        groups = new ArrayList<>();
    }

    public void setId(Integer _id) {
        id = _id;
    }

    public Integer getId() {
        return id;
    }

    public void setName(String _name) {
        name = _name;
    }

    public String getName() {
        return name;
    }

    public void setRealname(String _realname) {
        realname = _realname;
    }

    public String getRealname() {
        return realname;
    }

    public void addUrl(String _url) {
        urls.add(_url);
    }

    public ArrayList<String> getUrls() {
        return urls;
    }

    public void addNamevariation(String _namevariation) {
        namevariations.add(_namevariation);
    }

    public ArrayList<String> getNamevariations() {
        return namevariations;
    }

    public void addAlias(String _alias) {
        aliases.add(_alias);
    }

    public ArrayList<String> getAliases() {
        return aliases;
    }

    public void addRelease(String _release) {
        releases.add(_release);
    }

    public ArrayList<String> getReleases() {
        return aliases;
    }

    public void setProfile(String _profile) {
        profile = _profile;
    }

    public String getProfile() {
        return profile;
    }

    public void addMember(String _member) {
        members.add(_member);
    }

    public ArrayList<String> getMembers() {
        return members;
    }

    public void addGroup(String _group) {
        groups.add(_group);
    }

    public ArrayList<String> getGroups() {
        return groups;
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

    private DataQuality getDataQuality() {
        return data_quality;
    }
}
