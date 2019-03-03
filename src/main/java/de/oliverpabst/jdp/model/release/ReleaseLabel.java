package de.oliverpabst.jdp.model.release;

public class ReleaseLabel {

    private String catno;
    private String name;

    public ReleaseLabel(String _catno, String _name) {
        catno = _catno;
        name = _name;
    }

    public String getCatno() {
        return catno;
    }

    public String getName() {
        return name;
    }
}
