package de.oliverpabst.jdp.model.release;

import java.util.ArrayList;

public class ReleaseArtist {
    private String id;
    private String name;
    private String anv;
    private String join;
    private String role;

    private ArrayList<String> tracks; // TODO: Define track


    public ReleaseArtist() {
        tracks = new ArrayList<>();
    }

    public void setId(String _id) {
        id = _id;
    }

    public String getId() {
        return id;
    }

    public void setName(String _name) {
        name = _name;
    }

    public String getName() {
        return name;
    }

    public void setAnv(String _anv) {
        anv = _anv;
    }

    public String getAnv() {
        return anv;
    }

    public void setJoin(String _join) {
        join = _join;
    }

    public String getJoin() {
        return join;
    }

    public void setRole(String _role) {
        role = _role;
    }

    public String getRole() {
        return role;
    }

    public void addTrack(String _track) {
        tracks.add(_track);
    }

    public ArrayList<String> getTracks() {
        return tracks;
    }
}
