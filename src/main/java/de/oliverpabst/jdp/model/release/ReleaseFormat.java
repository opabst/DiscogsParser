package de.oliverpabst.jdp.model.release;

public class ReleaseFormat  {

    private String name;
    private String qty;
    private String text;
    private String desciption;

    public ReleaseFormat(String _name, String _qty, String _text) {
        name = _name;
        qty = _qty;
        text = _text;
    }

    public void setDescription(String _description) {
        desciption = _description;
    }

    public String getDescription() {
        return desciption;
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
