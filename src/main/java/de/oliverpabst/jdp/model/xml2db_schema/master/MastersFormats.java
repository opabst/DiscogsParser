package de.oliverpabst.jdp.model.xml2db_schema.master;

import java.util.ArrayList;

public class MastersFormats {
    private Integer master_id;
    private String format_name;
    private Integer qty;
    private ArrayList<String> descriptions;

    public MastersFormats() {
        descriptions = new ArrayList<>();
    }
}
