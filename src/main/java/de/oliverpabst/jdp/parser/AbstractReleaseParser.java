package de.oliverpabst.jdp.parser;

import javax.xml.stream.XMLStreamException;
import java.io.File;

public abstract class AbstractReleaseParser {

    protected boolean releases = false;
    protected boolean release = false;
    protected boolean images = false;
    protected boolean image = false;
    protected boolean artists = false;
    protected boolean artist = false;
    protected boolean id = false;
    protected boolean name = false;
    protected boolean anv = false;
    protected boolean join = false;
    protected boolean role = false;
    protected boolean tracks = false;
    protected boolean title = false;
    protected boolean track = false;
    protected boolean video = false;
    protected boolean labels = false;
    protected boolean label = false;
    protected boolean extraartists = false;
    protected boolean formats = false;
    protected boolean format = false;
    protected boolean descriptions = false;
    protected boolean description = false;
    protected boolean genres = false;
    protected boolean genre = false;
    protected boolean styles = false;
    protected boolean style = false;
    protected boolean country = false;
    protected boolean released = false;
    protected boolean notes = false;
    protected boolean dataQuality = false;
    protected boolean tracklist = false;
    protected boolean position = false;
    protected boolean duration = false;
    protected boolean identifiers = false;
    protected boolean identifier = false;
    protected boolean videos = false;
    protected boolean companies = false;
    protected boolean company = false;
    protected boolean catno = false;
    protected boolean entityType = false;
    protected boolean entityTypeName = false;
    protected boolean resourceUrl = false;

    protected abstract void parse(File _releaseFile) throws XMLStreamException;

}
