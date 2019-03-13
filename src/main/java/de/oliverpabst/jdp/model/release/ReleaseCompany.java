package de.oliverpabst.jdp.model.release;

public class ReleaseCompany {

    private String id;
    private String name;
    private String catno;
    private String entityType;
    private String entityTypeValue;

    public String getId() {
        return id;
    }

    public void setId(String _id) {
        id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String _name) {
        name = _name;
    }

    public String getCatno() {
        return catno;
    }

    public void setCatno(String _catno) {
        catno = _catno;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String _entityType) {
        entityType = _entityType;
    }

    public String getEntityTypeValue() {
        return entityTypeValue;
    }

    public void setEntityTypeValue(String _entityTypeName) {
        entityTypeValue = _entityTypeName;
    }

    public String getResourceUrl() {
        return resourceUrl;
    }

    public void setResourceUrl(String _resourceUrl) {
        resourceUrl = _resourceUrl;
    }

    private String resourceUrl;

    public ReleaseCompany() {

    }

}
