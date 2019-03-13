package de.oliverpabst.jdp.parser;

import de.oliverpabst.jdp.model.Image;
import de.oliverpabst.jdp.model.label.LabelEntity;
import de.oliverpabst.jdp.model.label.LabelSublabel;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Parses contents of discogs_$date_artists.xml files.
 *
 * XML structure is as follows:
 * <labels>
 *     <label>
 *         <images>
 *             <image height="..." type="..." uri="..." uri150="..." width="..."/>
 *         </images>
 *         <id> </id>
 *         <name> </name>
 *         <contactinfo> </contactinfo>
 *         <profile> </profile>
 *         <data_quality> </data_quality>
 *         <urls>
 *             <url> </url>
 *         </urls>
 *         <sublabels>
 *             <label id=""> </label>
 *         </sublabels>
 *     </label>
 * </labels>
 */

public class LabelParser {

    private boolean labels = false;
    private boolean label = false;
    private boolean images = false;
    private boolean id = false;
    private boolean name = true;
    private boolean contactinfo = false;
    private boolean profile = false;
    private boolean dataquality = false;
    private boolean urls = false;
    private boolean url = false;
    private boolean sublabels = false;
    private boolean sublabel = false;

    public LabelParser(File _file) {
        try {
            parse(_file);
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
    }

    private void parse(File _labelFile) throws XMLStreamException {
        InputStream is = null;
        try {
            is = new FileInputStream(_labelFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        XMLInputFactory inFactory = XMLInputFactory.newInstance();

        XMLStreamReader xmlParser = inFactory.createXMLStreamReader(is);

        LabelEntity le = new LabelEntity();

        LabelSublabel ls = new LabelSublabel();

        while(xmlParser.hasNext()) {
            switch(xmlParser.getEventType()) {
                case XMLStreamConstants.START_DOCUMENT:
                    // DO NOTHING
                    break;

                case XMLStreamConstants.END_DOCUMENT:
                    // EOF reached
                    xmlParser.close();

                case XMLStreamConstants.START_ELEMENT:
                    String startElem = xmlParser.getLocalName();
                    if (xmlParser.getLocalName().equals("labels")) {
                        labels = true;
                    } else if (xmlParser.getLocalName().equals("label")) {
                        label = true;
                        le = new LabelEntity();
                    } else if (xmlParser.getLocalName().equals("images")) {
                        images = true;
                    } else if (xmlParser.getLocalName().equals("image") && images) {
                        String height = xmlParser.getAttributeValue(null, "height");
                        String type = xmlParser.getAttributeValue(null, "type");
                        String uri = xmlParser.getAttributeValue(null, "uri");
                        String uri150 = xmlParser.getAttributeValue(null, "uri150");
                        String width = xmlParser.getAttributeValue(null, "width");
                        Image image = new Image(height, width, uri, uri150, type);
                        le.addImage(image);
                    } else if (xmlParser.getLocalName().equals("id")) {
                        id = true;
                    } else if (xmlParser.getLocalName().equals("name")) {
                        name = true;
                    } else if (xmlParser.getLocalName().equals("contactinfo")) {
                        contactinfo = true;
                    } else if (xmlParser.getLocalName().equals("profile")) {
                        profile = true;
                    } else if (xmlParser.getLocalName().equals("dataquality")) {
                        dataquality = true;
                    } else if (xmlParser.getLocalName().equals("urls")) {
                        urls = true;
                    } else if (xmlParser.getLocalName().equals("url")) {
                        url = true;
                    } else if (xmlParser.getLocalName().equals("sublabels")) {
                        sublabels = true;
                    } else if (xmlParser.getLocalName().equals("sublabel")) {
                        sublabel = true;
                        ls = new LabelSublabel();
                        ls.setSublabelID(xmlParser.getAttributeValue(0));
                    }
                    break;

                case XMLStreamConstants.CHARACTERS:
                    String chars = xmlParser.getText();
                    if (id) {
                        le.setId(xmlParser.getText());
                    } else if (name) {
                        le.setName(xmlParser.getText());
                    } else if (contactinfo) {
                        le.setContactinfo(xmlParser.getText());
                    } else if (profile) {
                        le.setProfile(xmlParser.getText());
                    } else if (dataquality) {
                        le.setDataQuality(xmlParser.getText());
                    } else if (url) {
                        le.addUrl(xmlParser.getText());
                    } else if (sublabel) {
                        ls.setSublabelName(xmlParser.getText());
                    }
                    break;

                case XMLStreamConstants.END_ELEMENT:
                    if (xmlParser.getLocalName().equals("label")) {
                        label = false;
                    } else if (xmlParser.getLocalName().equals("sublabel")) {
                        le.addSublabel(ls);
                        sublabel = false;
                    } else if (xmlParser.getLocalName().equals("name")) {
                        name = false;
                    } else if (xmlParser.getLocalName().equals("sublabels")) {
                        sublabels = false;
                    } else if (xmlParser.getLocalName().equals("profile")) {
                        profile = false;
                    } else if (xmlParser.getLocalName().equals("images")) {
                        images = false;
                    } else if (xmlParser.getLocalName().equals("urls")) {
                        urls = false;
                    } else if (xmlParser.getLocalName().equals("id")) {
                        id = false;
                    }

                    break;
                default:
                    break;
            }

            xmlParser.next();
        }
    }
}
