package de.oliverpabst.jdp.model.release;

public class ReleaseVideo {

    private String duration;
    private Boolean embed;
    private String src;
    private String title;
    private String description;

    public ReleaseVideo(String _duration, String _embed, String _url) {
        duration = _duration;
        setEmbed(_embed);
        src = _url;
    }

    private void setEmbed(String _embed) {
        if (_embed.equals("true")) {
            embed = true;
        } else {
            embed = false;
        }
    }

    public Boolean getEmbed() {
        return embed;
    }

    public String getDuration() {
        return duration;
    }

    public String getSrc() {
        return src;
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
}
