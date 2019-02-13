package de.oliverpabst.jdp.model;

public class LabelSublabel {
    private String sublabelName;
    private Integer sublabelID;

    public LabelSublabel(String _name, Integer _id) {
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

    public void setSublabelID(Integer _id) {
        sublabelID = _id;
    }

    public Integer getSublabelID() {
        return sublabelID;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Sublabelname: " + sublabelName);
        sb.append(" SublabelID: " + sublabelID);
        return sb.toString();
    }
}
