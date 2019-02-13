package de.oliverpabst.jdp.parser;

import de.oliverpabst.jdp.model.ArtistAlias;
import de.oliverpabst.jdp.model.ArtistEntity;
import de.oliverpabst.jdp.model.ArtistImage;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

// TODO: Test mit mehreren Aliasen und Namensvariation -> zum richtigen Zeitpunkt anlegen und zu ArtistEntity hinzufügen

/**
 * Parses contents of discogs_$date_artists.xml files.
 *
 * XML structure is as follows:
 * <artists>
 *     <artist>
 *         <images>
 *             <image height="..." type="..." uri="..." width="..."/>
 *         </images>
 *         <id> </id>
 *         <name> </name>
 *         <realname> </realname>
 *         <profile> </profile>
 *         <data_quality> </data_quality>
 *         <namevariations>
 *             <name> </name>
 *         </namevariations>
 *         <aliases>
 *             <name id=...> </name>
 *         </aliases>
 *     </artist>
 * </artists>
 */

public class ArtistParser {
    private boolean artists = false;
    private boolean artist = false;
    private boolean images = false; // at the moment unnecassary because images only contain more image objects
    private boolean id = false;
    private boolean name = false; // triple allocation: if namevariations/aliases is set, name will be a name variation or an alias
    private boolean realname = false;
    private boolean profile = false;
    private boolean dataquality = false;
    private boolean namevariations = false;
    private boolean aliases = false;

    public ArtistParser(File _file) {
        try {
            parse(_file);
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
    }

    private void parse(File _artistFile) throws XMLStreamException {
        InputStream is = null;

        XMLInputFactory inFactory = null;

        XMLStreamReader xmlParser = null;

        ArtistEntity ae = new ArtistEntity();

        ArtistAlias aa = new ArtistAlias();


        try {
            is = new FileInputStream(_artistFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        inFactory = XMLInputFactory.newInstance();


        xmlParser = inFactory.createXMLStreamReader(is);


        while(xmlParser.hasNext()) {
            switch(xmlParser.getEventType()) {
                case XMLStreamConstants.START_DOCUMENT:
                    // DO NOTHING
                    break;

                case XMLStreamConstants.END_DOCUMENT:
                    // EOF reached
                    xmlParser.close();
                case XMLStreamConstants.NAMESPACE:
                    // Namespaces are not used in this project => do nothing
                    break;
                case XMLStreamConstants.START_ELEMENT:
                    if(xmlParser.getLocalName().equals("artists")) {
                        artists = true;
                    } else if(xmlParser.getLocalName().equals("artist")) {
                        artist = true;
                        ae = new ArtistEntity();
                    } else if (xmlParser.getLocalName().equals("images")) {
                        images = true;
                    } else if (xmlParser.getLocalName().equals("image") && images == true) {
                        // subelement of images reached - values are attached as attributes to <image ... />
                        String height = xmlParser.getAttributeValue(null, "height");
                        String type = xmlParser.getAttributeValue(null, "type");
                        String uri = xmlParser.getAttributeValue(null, "uri");
                        String width = xmlParser.getAttributeValue(null, "width");
                        ArtistImage image = new ArtistImage(height, width, uri, type);
                        ae.addImage(image);
                    } else if (xmlParser.getLocalName().equals("id")) {
                        id = true;
                    } else if (xmlParser.getLocalName().equals("name")) {
                        name = true;
                    } else if (xmlParser.getLocalName().equals("name") && aliases) {
                        aa = new ArtistAlias();
                        aa.setAliasID(Integer.parseInt(xmlParser.getAttributeValue(0)));
                        name = true;
                    } else if (xmlParser.getLocalName().equals("realname")) {
                        realname = true;
                    } else if (xmlParser.getLocalName().equals("profile")) {
                        profile = true;
                    } else if (xmlParser.getLocalName().equals("data_quality")) {
                        dataquality = true;
                    } else if (xmlParser.getLocalName().equals("namevariations")) {
                        namevariations = true;
                    } else if (xmlParser.getLocalName().equals("aliases")) {
                        aliases = true;
                    }
                    break;
                case XMLStreamConstants.CHARACTERS:
                    if(id) {
                        ae.setId(Integer.parseInt(xmlParser.getText()));
                        id = false;
                    } else if (name && !namevariations && !aliases) {
                        ae.setName(xmlParser.getText());
                        name = false;
                    } else if (realname) {
                        ae.setRealName(xmlParser.getText());
                        realname = false;
                    } else if (profile) {
                        ae.setProfile(xmlParser.getText());
                        profile = false;
                    } else if (dataquality) {
                        ae.setDataQuality(xmlParser.getText());
                        dataquality = false;
                    } else if (name && namevariations && !aliases) {
                        ae.addNameVariation(xmlParser.getText());
                        name = false;
                    } else if (name && !namevariations && aliases) {
                        aa.setAlias(xmlParser.getText());
                    }
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    if(xmlParser.getLocalName().equals("artist")) {
                        artist = false;
                        // TODO: add further processing of the read artist object
                        // further process ArtistEntityObject (add to db)
                    } else if(xmlParser.getLocalName().equals("images")) {
                        images = false;
                    } else if(xmlParser.getLocalName().equals("namevariations")) {
                        namevariations = false;
                    } else if(xmlParser.getLocalName().equals("aliases")) {
                        ae.addAlias(aa);
                        aliases = false;
                    } else if(xmlParser.getLocalName().equals("profile")) {
                        profile = false;
                    } else if(xmlParser.getLocalName().equals("realname")) {
                        realname = false;
                    } else if (xmlParser.getLocalName().equals("name")) {
                        name = false;
                    }
                    break;
                default:
                    break;
            }
            xmlParser.next();
        }
    }
}