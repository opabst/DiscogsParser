package de.oliverpabst.jdp.model;

public class Image {
    private Integer height;
    private Integer width;
    private String uri;
    private ImageType type;

    public Image(String _height, String _width, String _uri, String _type) {
        if(_height.length() > 0) {
            height = Integer.parseInt(_height);
        } else {
            height = 0;
        }

        if(_width.length() > 0) {
            width = Integer.parseInt(_width);
        } else {
            width = 0;
        }
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
