package de.oliverpabst.jdp.model.xml2db_schema.master;

import de.oliverpabst.jdp.model.Image;

public class MastersImages {
    private Integer master_id;
    private Image image;

    public MastersImages() {

    }

    public void setMasterId(Integer _id) {
        master_id = _id;
    }

    public Integer getMasterId() {
        return master_id;
    }

    public void setImage(Image _image) {
        image = _image;
    }

    public Image getImage() {
        return image;
    }
}
