package de.oliverpabst.jdp.model.xml2db_schema.label;

import de.oliverpabst.jdp.model.Image;

public class LabelsImage {
    private Integer labelId;
    private Image labelImage;

    public LabelsImage() {

    }

    public Integer getLabelId() {
        return labelId;
    }

    public void setLabelId(Integer _labelId) {
        labelId = _labelId;
    }

    public Image getImage() {
        return labelImage;
    }

    public void setImage(Image _image) {
        labelImage = _image;
    }
}
