package de.oliverpabst.jdp.model;

public class ArtistImage {
    private Integer height;
    private Integer width;
    private String uri;
    private ArtistImageType type;

    public ArtistImage(String _height, String _width, String _uri, String _type) {
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
            type = ArtistImageType.PRIMARY;
        } else if (_type.equals("secondary")) {
            type = ArtistImageType.SECONDARY;
        } else {
            type = ArtistImageType.UNKNOWN;
            System.err.println("Unknown image type!");
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ArtistImage: [");
        sb.append(" height: " + height);
        sb.append(" width: " + width);
        sb.append(" uri: " + uri);
        sb.append(" type: " + type.name());
        sb.append("]");
        return sb.toString();
    }
}
