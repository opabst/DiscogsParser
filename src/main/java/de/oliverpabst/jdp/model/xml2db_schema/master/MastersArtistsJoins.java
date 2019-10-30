package de.oliverpabst.jdp.model.xml2db_schema.master;

public class MastersArtistsJoins {
    private String artist1;
    private String artist2;
    private String join_relation;
    private Integer master_id;

    public MastersArtistsJoins() {
        
    }

    public void setArtist1(String _artist) {
        artist1 = _artist;
    }

    public String getArtist1() {
        return artist1;
    }

    public void setArtist2(String _artist) {
        artist2 = _artist;
    }

    public String getArtist2() {
        return artist2;
    }

    public void setJoinRelation(String _joinReleation) {
        join_relation = _joinReleation;
    }

    public String getJoinRelation() {
        return join_relation;
    }

    public void setMasterId(Integer _id) {
        master_id = _id;
    }

    public Integer getMasterId() {
        return master_id;
    }
}
