package de.oliverpabst.jdp.model.jdp_schema.release;

import java.util.ArrayList;

public class ReleaseFormat  {

    private String name;
    private String qty;
    private String text;
    private ArrayList<String> descriptions;

    public ReleaseFormat(String _name, String _qty, String _text) {
        descriptions = new ArrayList<>();
        name = _name;
        qty = _qty;
        text = _text;
    }

    public void addDescription(String _description) {
        descriptions.add(_description);
    }

    public ArrayList<String> getDescriptions() {
        return descriptions;
    }

    public String getName() {
        return name;
    }

    public String getQty() {
        return qty;
    }

    public String getText() {
        return text;
    }
}
