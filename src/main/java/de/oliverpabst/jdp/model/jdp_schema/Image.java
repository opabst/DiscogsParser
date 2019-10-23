package de.oliverpabst.jdp.model.jdp_schema;

public class Image extends de.oliverpabst.jdp.model.Image {
    private String uri150;
    public Image(String _height, String _width, String _uri, String _uri150, String _type) {
        super(_height, _width, _uri, _type);
        uri150 = _uri150;
    }

    public void setUri150(String _uri150) {
        uri150 = _uri150;
    }

    public String getUri150() {
        return uri150;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Image: [");
        sb.append(" height: " + height);
        sb.append(" width: " + width);
        sb.append(" uri: " + uri);
        sb.append(" uri150 " + uri150);
        sb.append(" type: " + type.name());
        sb.append("]");
        return sb.toString();
    }
}
