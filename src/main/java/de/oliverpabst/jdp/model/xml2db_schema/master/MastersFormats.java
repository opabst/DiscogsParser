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

    public void setMasterId(Integer _id) {
        master_id = _id;
    }

    public Integer getMasterId() {
        return master_id;
    }

    public void setFormatName(String _name) {
        format_name = _name;
    }

    public String getFormatName() {
        return format_name;
    }
    // TOOD: Let setter check for db constraints (1-100)?
    public void setQty(Integer _qty) {
        qty = _qty;
    }

    public Integer getQty() {
        return qty;
    }

    public void addDescription(String _description) {
        descriptions.add(_description);
    }

    public ArrayList<String> getDescriptions() {
        return descriptions;
    }
}
