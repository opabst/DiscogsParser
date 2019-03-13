package de.oliverpabst.jdp.model.master;

public class MasterVideo {
    private boolean embed;
    private String sourceUrl;
    private String description;
    private String duration;
    private String title;


    public MasterVideo(String _duration, String _embed, String _src) {
        duration = _duration;

        if(_embed.equals("true")) {
            embed = true;
        } else if (_embed.equals("false")) {
            embed = false;
        } else {
            System.err.println("Unknown embed value: " + _embed);
        }

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

    public boolean getEmbed() {
        return embed;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public String getDuration() {
        return duration;
    }
}
