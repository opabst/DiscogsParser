package de.oliverpabst.jdp.model.xml2db_schema.release;

public class ReleasesLabels {
    private String label;
    private Integer release_id;
    private String catno;

    public ReleasesLabels() {

    }

    public void setLabel(String _label) {
        label = _label;
    }

    public String getLabel() {
        return label;
    }

    public void setReleaseId(Integer _id) {
        release_id = _id;
    }

    public Integer getReleaseId() {
        return release_id;
    }

    public void setCatno(String _catno) {
        catno = _catno;
    }

    public String getCatno() {
        return catno;
    }
}
