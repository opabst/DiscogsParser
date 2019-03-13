package de.oliverpabst.jdp.model.label;

public class LabelSublabel {
    private String sublabelName;
    private String sublabelID;

    public LabelSublabel(String _name, String _id) {
        sublabelName = _name;
        sublabelID = _id;
    }

    public LabelSublabel() {

    }

    public void setSublabelName(String _name) {
        sublabelName = _name;
    }

    public String getSublabelName() {
        return sublabelName;
    }

    public void setSublabelID(String _id) {
        sublabelID = _id;
    }

    public String getSublabelID() {
        return sublabelID;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Sublabelname: " + sublabelName);
        sb.append(" SublabelID: " + sublabelID);
        return sb.toString();
    }
}
