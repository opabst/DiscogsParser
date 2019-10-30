package de.oliverpabst.jdp.model.xml2db_schema.master;

import java.util.ArrayList;

public class MastersExtraartists {
    private Integer master_id;
    private String artist_name;
    private ArrayList<String> roles;

    public MastersExtraartists() {
        roles = new ArrayList<>();
    }

    public void setMasterId(Integer _id) {
        master_id = _id;
    }

    public Integer getMasterId() {
        return master_id;
    }

    public void setArtistName(String _name) {
        artist_name = _name;
    }

    public String getArtistName() {
        return artist_name;
    }

    public void addRole(String _role) {
        roles.add(_role);
    }

    public ArrayList<String> getRoles() {
        return roles;
    }
}
