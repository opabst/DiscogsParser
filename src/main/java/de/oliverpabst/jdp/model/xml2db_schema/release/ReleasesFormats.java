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

    public void setReleaseId(Integer _id) {
        release_id = _id;
    }

    public Integer getReleaseId() {
        return release_id;
    }

    public void setPosition(Integer _position) {
        position = _position;
    }

    public Integer getPosition() {
        return position;
    }

    public void setFormatName(String _name) {
        format_name = _name;
    }

    public String getFormatName() {
        return format_name;
    }

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
