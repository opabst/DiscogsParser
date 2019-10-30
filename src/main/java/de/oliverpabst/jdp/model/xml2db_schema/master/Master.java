package de.oliverpabst.jdp.model.xml2db_schema.master;

import de.oliverpabst.jdp.model.DataQuality;

public class Master {
    private Integer id;
    private String title;
    private Integer main_release;
    private Integer year;
    private String notes;
    private String genres;
    private String styles;
    private String role;
    private DataQuality data_quality;

    public Master() {

    }

    public void setId(Integer _id) {
        id = _id;
    }

    public Integer getId() {
        return id;
    }
}
