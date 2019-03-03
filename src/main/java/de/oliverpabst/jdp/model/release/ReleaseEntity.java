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

    }
}
