package de.oliverpabst.jdp.model.artist;

public class ArtistAlias {
    private String aliasName;
    private String aliasID;

    public ArtistAlias(String _aliasName, String _aliasID) {
        aliasID = _aliasID;
        aliasName = _aliasName;
    }

    public ArtistAlias() {

    }

    public String getAliasName() {
        return aliasName;
    }

    public void setAlias(String _aliasName) {
        aliasName = _aliasName;
    }

    public String getAliasID() {
        return aliasID;
    }

    public void setAliasID(String _aliasId) {
        aliasID = _aliasId;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Alias: ");
        sb.append(" id: " + aliasID);
        sb.append(" name: " + aliasName);

        return sb.toString();
    }
}
