package de.oliverpabst.jdp.model.release;

public class ReleaseLabel {

    private String catno;
    private String id;
    private String name;

    public ReleaseLabel(String _catno, String _id, String _name) {
        catno = _catno;
        id = _id;
        name = _name;
    }

    public String getCatno() {
        return catno;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }
}
