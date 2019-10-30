package de.oliverpabst.jdp.model.xml2db_schema.release;

import java.util.ArrayList;

public class ReleasesFormats {

    private Integer release_id;
    private Integer position;
    private String format_name;
    private Integer qty;
    private ArrayList<String> descriptions;

    public ReleasesFormats() {
        descriptions = new ArrayList<>();
    }
}
