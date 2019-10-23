package de.oliverpabst.jdp.model.jdp_schema.release;


public class ReleaseIdentifier {

    private String description;
    private String type;
    private String value;

    public ReleaseIdentifier(String _description, String _type, String _value) {
        description = _description;
        type = _type;
        value = _value;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public String getValue() {
        return value;
    }
}
