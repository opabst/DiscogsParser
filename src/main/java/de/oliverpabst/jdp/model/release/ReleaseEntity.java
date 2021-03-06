package de.oliverpabst.jdp.model.release;

import de.oliverpabst.jdp.model.DataQuality;
import de.oliverpabst.jdp.model.Image;

import java.util.ArrayList;

public class ReleaseEntity {

    private String id;

    private String status;

    private ArrayList<Image> images;

    private String title;

    private ArrayList<ReleaseArtist> artists;

    private ArrayList<ReleaseLabel> labels;

    private ArrayList<ReleaseExtraArtist> extraArtists;

    private ArrayList<ReleaseFormat> formats;

    private ArrayList<String> genres;

    private ArrayList<String> styles;

    private String country;

    private String released;

    private String notes;

    private DataQuality dataQuality;

    private ArrayList<ReleaseTrack> tracks;

    private ArrayList<ReleaseIdentifier> identifiers;

    private ArrayList<ReleaseVideo> videos;

    private ArrayList<ReleaseCompany> companies;

    public ReleaseEntity() {
        images = new ArrayList<>();

        artists = new ArrayList<>();

        labels = new ArrayList<>();

        extraArtists = new ArrayList<>();

        formats = new ArrayList<>();

        genres = new ArrayList<>();

        styles = new ArrayList<>();

        tracks = new ArrayList<>();

        identifiers = new ArrayList<>();

        videos = new ArrayList<>();

        companies = new ArrayList<>();
    }

    public void setId(String _id) {
        id = _id;
    }

    public String getId() {
        return id;
    }

    public void setStatus(String _status) {
        status = _status;
    }

    public String getStatus() {
        return status;
    }

    public void addImage(Image _image) {
        images.add(_image);
    }

    public ArrayList<Image> getImages() {
        return images;
    }

    public void setTitle(String _title) {
        title = _title;
    }

    public String getTitle() {
        return title;
    }

    public void addArtist(ReleaseArtist _artist) {
        artists.add(_artist);
    }

    public ArrayList<ReleaseArtist> getArtists() {
        return artists;
    }

    public void addLabel(ReleaseLabel _label) {
        labels.add(_label);
    }

    public ArrayList<ReleaseLabel> getLabels() {
        return labels;
    }

    public void addExtraArtist(ReleaseExtraArtist _artist) {
        extraArtists.add(_artist);
    }

    public ArrayList<ReleaseExtraArtist> getExtraArtists() {
        return extraArtists;
    }

    public void addFormat(ReleaseFormat _format) {
        formats.add(_format);
    }

    public ArrayList<ReleaseFormat> getFormats() {
        return formats;
    }

    public void addGenre(String _genre) {
        genres.add(_genre);
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    public void addStyle(String _style) {
        styles.add(_style);
    }

    public ArrayList<String> getStyles() {
        return styles;
    }

    public void setCountry(String _country) {
        country = _country;
    }

    public String getCountry() {
        return country;
    }

    public void setReleased(String _released) {
        released = _released;
    }

    public String getReleased() {
        return released;
    }

    public void setNotes(String _notes) {
        notes = _notes;
    }

    public String getNotes() {
        return notes;
    }

    public void setDataQuality(String _quality) {
        if(_quality.equals("Correct")) {
            dataQuality = DataQuality.CORRECT;
        } else if (_quality.equals("Needs Major Changes")) {
            dataQuality = DataQuality.NEEDS_MAJOR_CHANGES;
        } else if (_quality.equals("Needs Vote")) {
            dataQuality = DataQuality.NEEDS_VOTE;
        } else if (_quality.equals("Needs Minor Changes")) {
            dataQuality = DataQuality.NEEDS_MINOR_CHANGES;
        } else if (_quality.equals("Complete and Correct")) {
            dataQuality = DataQuality.COMPLETE_AND_CORRECT;
        } else if (_quality.equals("Entirely Incorrect")) {
            dataQuality = DataQuality.ENTIRELY_INCORRECT;
        } else {
            dataQuality = DataQuality.UNKNOWN;
            System.err.println("Unknown data quality: " + _quality);
        }
    }

    public DataQuality getDataQuality() {
        return dataQuality;
    }

    public void addTrack(ReleaseTrack _track) {
        tracks.add(_track);
    }

    public ArrayList<ReleaseTrack> getTracks() {
        return tracks;
    }

    public void addIdentifier(ReleaseIdentifier _identifier) {
        identifiers.add(_identifier);
    }

    public ArrayList<ReleaseIdentifier> getIdentifiers() {
        return identifiers;
    }

    public void addVideo(ReleaseVideo _video) {
        videos.add(_video);
    }

    public ArrayList<ReleaseVideo> getVideos() {
        return videos;
    }

    public void addCompany(ReleaseCompany _company) {
        companies.add(_company);
    }

    public ArrayList<ReleaseCompany> getCompanies() {
        return companies;
    }
}
