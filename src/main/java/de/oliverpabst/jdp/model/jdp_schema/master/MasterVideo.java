package de.oliverpabst.jdp.model.jdp_schema.master;

public class MasterVideo {
    private String embed;
    private String sourceUrl;
    private String description;
    private String duration;
    private String title;


    public MasterVideo(String _duration, String _embed, String _src) {
        duration = _duration;

        embed = _embed;

        sourceUrl = _src;
    }


    public void setTitle(String _title) {
        title = _title;
    }

    public String getTitle() {
        return title;
    }

    public void setDescription(String _description) {
        description = _description;
    }

    public String getDescription() {
        return description;
    }

    public String getEmbed() {
        return embed;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public String getDuration() {
        return duration;
    }
}
