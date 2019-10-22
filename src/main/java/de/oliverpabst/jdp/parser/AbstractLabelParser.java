package de.oliverpabst.jdp.parser;

import javax.xml.stream.XMLStreamException;
import java.io.File;

public abstract class AbstractLabelParser {

    protected boolean labels = false;
    protected boolean label = false;
    protected boolean images = false;
    protected boolean id = false;
    protected boolean name = true;
    protected boolean contactinfo = false;
    protected boolean profile = false;
    protected boolean dataquality = false;
    protected boolean urls = false;
    protected boolean url = false;
    protected boolean sublabels = false;
    protected boolean sublabel = false;

    protected abstract void parse(File _labelFile) throws XMLStreamException;
}
