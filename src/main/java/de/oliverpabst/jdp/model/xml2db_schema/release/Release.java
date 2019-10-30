package de.oliverpabst.jdp.model.xml2db_schema.release;

import de.oliverpabst.jdp.model.DataQuality;

public class Release {
    private Integer id;
    private String status;
    private String title;
    private String country;
    private String released;
    private String barcode;
    private String notes;
    private String genres;
    private String styles;
    private Integer master_id;
    private DataQuality data_quality;

    public Release() {

    }
}
