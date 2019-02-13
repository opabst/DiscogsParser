package de.oliverpabst.jdp.model;

public class LabelImage {
    private Integer height;
    private Integer width;
    private String uri;
    private String uri150;
    private LabelImageType type;

    public LabelImage(String _height, String _width, String _uri, String _uri150, String _type) {
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
            type = LabelImageType.PRIMARY;
        } else if (_type.equals("secondary")) {
            type = LabelImageType.SECONDARY;
        } else {
            type = LabelImageType.UNKNOWN;
            System.err.println("Unknown image type!");
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("LabelImage: [");
        sb.append(" height: " + height);
        sb.append(" width: " + width);
        sb.append(" uri: " + uri);
        sb.append(" uri150: " + uri150);
        sb.append(" type: " + type.name());
        sb.append("]");
        return sb.toString();
    }
}
