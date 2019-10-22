package de.oliverpabst.jdp.parser;

import javax.xml.stream.XMLStreamException;
import java.io.File;

public abstract class AbstractMasterParser {

    protected boolean masters = false;
    protected boolean master = false;
    protected boolean mainRelease = false;
    protected boolean images = false;
    protected boolean artists = false;
    protected boolean artist = false;
    protected boolean tracks = false;
    protected boolean role = false;
    protected boolean name = false;
    protected boolean anv = false;
    protected boolean join = false;
    protected boolean id = false;
    protected boolean genres = false;
    protected boolean genre = false;
    protected boolean styles = false;
    protected boolean style = false;
    protected boolean year = false;
    protected boolean title = false;
    protected boolean dataQualitiy = false;
    protected boolean videos = false;
    protected boolean video = false;
    protected boolean description = false;

    protected abstract void parse(File _masterFile) throws XMLStreamException;
}
