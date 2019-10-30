package de.oliverpabst.jdp.model.xml2db_schema.release;

import de.oliverpabst.jdp.model.Image;

public class ReleasesImages {
    private Integer release_id;
    private Image image;

    public ReleasesImages() {

    }

    public void setReleaseId(Integer _id) {
        release_id = _id;
    }

    public Integer getReleaseId() {
        return release_id;
    }

    public void setImage(Image _image) {
        image = _image;
    }

    public Image getImage() {
        return image;
    }
}
