package de.oliverpabst.jdp.model;

public class Image {

    protected Integer height;
    protected Integer width;
    protected String uri;
    protected ImageType type;

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
        } else if(_type.equals("secondary")) {
            type = ImageType.SECONDARY;
        } else {
            type = ImageType.UNKNOWN;
            System.err.println("Unknow image type: " + _type);
        }
    }

    public Integer getHeight() {
        return height;
    }

    public Integer getWidth() {
        return width;
    }

    public String getUri() {
        return uri;
    }

    public ImageType getType() {
        return type;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Image: [");
        sb.append(" height: " + height);
        sb.append(" width: " + width);
        sb.append(" uri: " + uri);
        sb.append(" type: " + type.name());
        sb.append("]");
        return sb.toString();
    }
}

