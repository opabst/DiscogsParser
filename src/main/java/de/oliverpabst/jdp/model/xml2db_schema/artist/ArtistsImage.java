package de.oliverpabst.jdp.model.xml2db_schema.artist;

import de.oliverpabst.jdp.model.Image;

public class ArtistsImage {

    private Integer artistId;
    private Image artistImage;

    public ArtistsImage() {

    }

    public Integer getArtistId() {
        return artistId;
    }

    public void setArtistId(Integer _artistId) {
        artistId = _artistId;
    }

    public Image getImage() {
        return artistImage;
    }

    public void setImage(Image _image) {
        artistImage = _image;
    }
}
