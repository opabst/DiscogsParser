package de.oliverpabst.jdp.parser.jdp_schema;

import de.oliverpabst.jdp.database.postgresql.jdp.LabelWriter;
import de.oliverpabst.jdp.model.Image;
import de.oliverpabst.jdp.model.label.LabelEntity;
import de.oliverpabst.jdp.model.label.LabelSublabel;
import de.oliverpabst.jdp.parser.AbstractLabelParser;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.*;
import java.sql.SQLException;

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

public class LabelParser extends AbstractLabelParser {



    private LabelWriter writer;

    public LabelParser(File _file) {
        writer = LabelWriter.getInstance();
        try {
            parse(_file);
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
    }

    protected void parse(File _labelFile) throws XMLStreamException {
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
                    } else if (labels && !sublabels && xmlParser.getLocalName().equals("label")) {
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
                    } else if (label && xmlParser.getLocalName().equals("id")) {
                        id = true;
                    } else if (label && xmlParser.getLocalName().equals("name")) {
                        name = true;
                    } else if (label && xmlParser.getLocalName().equals("contactinfo")) {
                        contactinfo = true;
                    } else if (label && xmlParser.getLocalName().equals("profile")) {
                        profile = true;
                    } else if (label && xmlParser.getLocalName().equals("data_quality")) {
                        dataquality = true;
                    } else if (label && xmlParser.getLocalName().equals("urls")) {
                        urls = true;
                    } else if (label && urls && xmlParser.getLocalName().equals("url")) {
                        url = true;
                    } else if (label && xmlParser.getLocalName().equals("sublabels")) {
                        sublabels = true;
                    } else if (label && sublabels && xmlParser.getLocalName().equals("label")) {
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
                    if (xmlParser.getLocalName().equals("labels")) {
                        labels = false;
                    } else if (labels && !sublabels && xmlParser.getLocalName().equals("label")) {
                        label = false;
                        try {
                            writer.insertLabel(le);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    } else if (sublabels && xmlParser.getLocalName().equals("label")) {
                        le.addSublabel(ls);
                        sublabel = false;
                    } else if (xmlParser.getLocalName().equals("name")) {
                        name = false;
                    } else if (xmlParser.getLocalName().equals("sublabels")) {
                        sublabels = false;
                    } else if (xmlParser.getLocalName().equals("contactinfo")) {
                        contactinfo = false;
                    } else if (xmlParser.getLocalName().equals("profile")) {
                        profile = false;
                    } else if (xmlParser.getLocalName().equals("data_quality")) {
                        dataquality = false;
                    } else if (xmlParser.getLocalName().equals("images")) {
                        images = false;
                    } else if (urls && xmlParser.getLocalName().equals("url")) {
                        url = false;
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
        xmlParser.close();
        try {
            writer.finalBatchExecute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        writer.disconnect();
        try {
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
