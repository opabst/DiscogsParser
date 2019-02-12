package de.oliverpabst.jdp.model;

public class Image {
    private Integer height;
    private Integer width;
    private String uri;
    private ImageType type;

    public Image(String _height, String _width, String _uri, String _type) {
        height = Integer.parseInt(_height);
        width = Integer.parseInt(_width);
        uri = _uri;

        if(_type.equals("primary")) {
            type = ImageType.PRIMARY;
        } else if (_type.equals("secondary")) {
            type = ImageType.SECONDARY;
        } else {
            type = ImageType.UNKNOWN;
            System.err.println("Unknown image type!");
        }
    }
}
