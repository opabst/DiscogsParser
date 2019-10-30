package de.oliverpabst.jdp.model.xml2db_schema.master;

import java.util.ArrayList;

public class MastersExtraartists {
    private Integer master_id;
    private String artist_name;
    private ArrayList<String> roles;

    public MastersExtraartists() {
        roles = new ArrayList<>();
    }
}
