package de.oliverpabst.jdp.model.master;

public class MasterVideo {
    private boolean embed;
    private String sourceUrl;
    private String description;
    private Integer duration;
    private String title;


    public MasterVideo(String _duration, String _embed, String _src) {
        if(_duration.length() > 0) {
            duration = Integer.parseInt(_duration);
        } else {
            duration = 0;
        }

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

    public Integer getDuration() {
        return duration;
    }
}
