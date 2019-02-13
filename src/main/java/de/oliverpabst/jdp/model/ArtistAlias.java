package de.oliverpabst.jdp.model;

public class ArtistAlias {
    private String aliasName;
    private Integer aliasID;

    public ArtistAlias(String _aliasName, Integer _aliasID) {
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

    public Integer getAliasID() {
        return aliasID;
    }

    public void setAliasID(Integer _aliasId) {
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
