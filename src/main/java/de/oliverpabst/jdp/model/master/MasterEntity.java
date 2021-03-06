package de.oliverpabst.jdp.model.master;

import de.oliverpabst.jdp.model.DataQuality;
import de.oliverpabst.jdp.model.Image;

import java.util.ArrayList;

public class MasterEntity {

    private String id;
    private String mainRelease;

    private ArrayList<Image> images;
    private ArrayList<MasterVideo> videos;
    private ArrayList<MasterArtist> artists;

    private ArrayList<String> genres;
    private ArrayList<String> styles;

    private String year;
    private String title;
    private DataQuality dataQuality;

    public MasterEntity() {
        images = new ArrayList<>();
        videos = new ArrayList<>();
        artists = new ArrayList<>();

        genres = new ArrayList<>();
        styles = new ArrayList<>();
    }

    public void setId(String _id) {
        id = _id;
    }

    public String getId() {
        return id;
    }

    public void setMainRelease(String _mainRelease) {
        mainRelease = _mainRelease;
    }

    public String getMainRelease() {
        return mainRelease;
    }

    public void addImage(Image _image) {
        images.add(_image);
    }

    public ArrayList<Image> getImages() {
        return images;
    }

    public void addVideo(MasterVideo _video) {
        videos.add(_video);
    }

    public ArrayList<MasterVideo> getVideos() {
        return videos;
    }

    public void addArtist(MasterArtist _artist) {
        artists.add(_artist);
    }

    public ArrayList<MasterArtist> getArtists(){
        return artists;
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

    public void setYear(String _year) {
        if(_year.length() > 0) {
            year = _year;
        } else {
            year = "0";
        }
    }

    public String getYear() {
        return year;
    }

    public void setTitle(String _title) {
        title = _title;
    }

    public String getTitle() {
        return title;
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
}
