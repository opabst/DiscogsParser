package de.oliverpabst.jdp.model.master;

import de.oliverpabst.jdp.model.DataQuality;

import java.util.ArrayList;

public class MasterEntity {

    private ArrayList<MasterImage> images;
    private ArrayList<MasterVideo> videos;
    private ArrayList<MasterArtist> artists;

    private ArrayList<String> genres;
    private ArrayList<String> styles;

    private Integer year;
    private String title;
    private DataQuality dataQuality;

    public MasterEntity() {
        images = new ArrayList<>();
        videos = new ArrayList<>();
        artists = new ArrayList<>();

        genres = new ArrayList<>();
        styles = new ArrayList<>();
    }

    public void addImage(MasterImage _image) {
        images.add(_image);
    }

    public ArrayList<MasterImage> getImages() {
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
            year = Integer.parseInt(_year);
        } else {
            year = 0;
        }
    }

    public Integer getYear() {
        return year;
    }

    public void setTitle(String _title) {
        title = _title;
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
