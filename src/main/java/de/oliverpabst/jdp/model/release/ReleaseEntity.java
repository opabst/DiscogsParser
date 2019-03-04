package de.oliverpabst.jdp.model.release;

import de.oliverpabst.jdp.model.DataQuality;
import de.oliverpabst.jdp.model.ImageType;

import java.util.ArrayList;

public class ReleaseEntity {

    private ArrayList<ImageType> images;

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

    public void addImage(ImageType _image) {
        images.add(_image);
    }

    public ArrayList<ImageType> getImages() {
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

    public void setDataQuality(DataQuality _quality) {
        dataQuality = _quality;
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

    public void setIdentifier(ReleaseIdentifier _identifier) {
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
