package de.oliverpabst.jdp;

import java.util.HashMap;

// TODO: add increasing statements to the data writers

public class ImportStatistics {
    private static ImportStatistics instance;

    private HashMap<String, Integer> statistics;

    private ImportStatistics() {
        statistics = new HashMap<>();

        // artists
        statistics.put("Artist", 0);
        statistics.put("ArtistNameVariation", 0);
        statistics.put("ArtistAlias", 0);
        statistics.put("AliasOfArtist", 0);
        statistics.put("ArtistImage", 0);
        statistics.put("ArtistImageOf", 0);

        // label
        statistics.put("Label", 0);
        statistics.put("LabelUrls", 0);
        statistics.put("LabelSublabel", 0);
        statistics.put("LabelSublabelOf", 0);
        statistics.put("LabelImage", 0);
        statistics.put("LabelImageOf", 0);

        // master
        statistics.put("Master", 0);
        statistics.put("MasterStyles", 0);
        statistics.put("MasterGenres", 0);
        statistics.put("MasterImage", 0);
        statistics.put("MasterImageOf", 0);
        statistics.put("MasterArtist", 0);
        statistics.put("MasterArtistPerforms", 0);

        // release
        statistics.put("Release", 0);
        statistics.put("ReleaseStyles", 0);
        statistics.put("ReleaseGenres", 0);
        statistics.put("ReleaseArtist", 0);
        statistics.put("ReleaseArtistOf", 0);
        statistics.put("ReleaseExtraArtist", 0);
        statistics.put("ReleaseExtraArtistOf", 0);
        statistics.put("ReleaseIdentifier", 0);
        statistics.put("ReleaseIdentifies", 0);
        statistics.put("ReleaseVideo", 0);
        statistics.put("ReleaseVideoOf", 0);
        statistics.put("ReleaseCompany", 0);
        statistics.put("ReleaseCompanyOf", 0);
        statistics.put("ReleaseImage", 0);
        statistics.put("ReleaseImageOf", 0);
        statistics.put("ReleaseLabel", 0);
        statistics.put("ReleaseLabelOf", 0);
    }

    public static ImportStatistics getInstance() {
        if(instance == null) {
            instance = new ImportStatistics();
        }
        return instance;
    }

    public void increase(String _entry) {
        if(statistics.containsKey(_entry)) {
            statistics.put(_entry, statistics.get(_entry) + 1);
        } else {
            System.err.println("Error! Key not found!");
        }
    }

    public String getStatistics() {
        StringBuilder sb = new StringBuilder();
        sb.append("ARTIST\n");
        sb.append("Artist: " + statistics.get("Artist") + "\n");
        sb.append("ArtistNameVariation: " + statistics.get("ArtistNameVariation") + "\n");
        sb.append("ArtistAlias: " + statistics.get("ArtistAlias") + "\n");
        sb.append("AliasOfArtist: " + statistics.get("AliasOfArtist") + "\n");
        sb.append("ArtistImage: " + statistics.get("ArtistImage") + "\n");
        sb.append("ArtistImageOf: " + statistics.get("ArtistImageOf") + "\n");
        sb.append("====================");
        sb.append("LABEL\n");
        sb.append("Label: " + statistics.get("Label") + "\n");
        sb.append("LabelUrls: " + statistics.get("LabelUrls") + "\n");
        sb.append("LabelSublabel: " + statistics.get("LabelSublabel") + "\n");
        sb.append("LabelSublabelOf: " + statistics.get("LabelSublabelOf") + "\n");
        sb.append("LabelImage: " + statistics.get("LabelImage") + "\n");
        sb.append("LabelImageOf: " + statistics.get("LabelImageOf") + "\n");
        sb.append("====================");
        sb.append("MASTER\n");
        sb.append("Master: " + statistics.get("Master") + "\n");
        sb.append("MasterStyles: " + statistics.get("MasterStyles") + "\n");
        sb.append("MasterGenres: " + statistics.get("MasterGenres") + "\n");
        sb.append("MasterImages: " + statistics.get("MasterImage") + "\n");
        sb.append("MasterImagesOf: " + statistics.get("MasterImageOf") + "\n");
        sb.append("MasterArtist: " + statistics.get("MasterArtist") + "\n");
        sb.append("MasterArtistPerforms: " + statistics.get("MasterArtistPerforms") + "\n");
        sb.append("====================");
        sb.append("RELEASE\n");
        sb.append("Release: " + statistics.get("Release") + "\n");
        sb.append("ReleaseStyles: " + statistics.get("ReleaseStyles") + "\n");
        sb.append("ReleaseGenres: " + statistics.get("ReleaseGenres") + "\n");
        sb.append("ReleaseArtist: " + statistics.get("ReleaseArtist") + "\n");
        sb.append("ReleaseArtistOf: " + statistics.get("ReleaseArtistOf") + "\n");
        sb.append("ReleaseExtraArtist: " + statistics.get("ReleaseExtraArtist") + "\n");
        sb.append("ReleaseExtraArtistOf: " + statistics.get("ReleaseExtraArtistOf") + "\n");
        sb.append("ReleaseIdentifier: " + statistics.get("ReleaseIdentifier") + "\n");
        sb.append("ReleaseIdentifies: " + statistics.get("ReleaseIdentifies") + "\n");
        sb.append("ReleaseVideo: " + statistics.get("ReleaseVideo") + "\n");
        sb.append("ReleaseVideoOf: " + statistics.get("ReleaseVideoOf") + "\n");
        sb.append("ReleaseCompany: " + statistics.get("ReleaseCompany") + "\n");
        sb.append("ReleaseCompanyOf: " + statistics.get("ReleaseCompanyOf") + "\n");
        sb.append("ReleaseImage: " + statistics.get("ReleaseImage") + "\n");
        sb.append("ReleaseImageOf: " + statistics.get("ReleaseImageOf") + "\n");
        sb.append("ReleaseLabel: " + statistics.get("ReleaseLabel") + "\n");
        sb.append("ReleaseLabelOf: " + statistics.get("ReleaseLabelOf") + "\n");
        sb.append("====================");
        return sb.toString();
    }
}
