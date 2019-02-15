package de.oliverpabst.jdp.model.master;

import de.oliverpabst.jdp.model.ImageType;

public class MasterImage {

    private Integer height;
    private Integer width;
    private String uri;
    private String uri150;
    private ImageType type;

    public MasterImage(String _height, String _width, String _uri, String _uri150, String _type) {
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
        uri150 = _uri150;

        if(_type.equals("primary")) {
            type = ImageType.PRIMARY;
        } else if(_type.equals("secondary")) {
            type = ImageType.SECONDARY;
        } else {
            type = ImageType.UNKNOWN;
            System.err.println("Unknow image type: " + _type);
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Image: [");
        sb.append(" height: " + height);
        sb.append(" width: " + width);
        sb.append(" uri: " + uri);
        sb.append(" uri150: " + uri150);
        sb.append(" type: " + type.name());
        sb.append("]");
        return sb.toString();
    }
}
