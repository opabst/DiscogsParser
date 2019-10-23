package de.oliverpabst.jdp.parser;

import javax.xml.stream.XMLStreamException;
import java.io.File;

public abstract class AbstractArtistParser {
    protected boolean artists = false;
    protected boolean artist = false;
    protected boolean images = false;
    protected boolean image = false;
    protected boolean id = false;
    protected boolean name = false; // triple allocation: if namevariations/aliases is set, name will be a name variation or an alias
    protected boolean realname = false;
    protected boolean profile = false;
    protected boolean dataquality = false;
    protected boolean namevariations = false;
    protected boolean aliases = false;

    protected abstract void parse(File _artistFile) throws XMLStreamException;
}
