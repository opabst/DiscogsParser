package de.oliverpabst.jdp.parser.xml2db_schema;

import de.oliverpabst.jdp.database.postgresql.xml2db_schema.ArtistWriter;
import de.oliverpabst.jdp.model.Image;
import de.oliverpabst.jdp.model.xml2db_schema.artist.Artist;
import de.oliverpabst.jdp.model.xml2db_schema.artist.ArtistsImage;
import de.oliverpabst.jdp.parser.AbstractArtistParser;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.*;
import java.sql.SQLException;

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

public class ArtistParser extends AbstractArtistParser {

    private ArtistWriter writer;

    public ArtistParser(File _file) {
        writer = ArtistWriter.getInstance();
        try {
            parse(_file);
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
    }

    protected void parse(File _artistFile) throws XMLStreamException {
        InputStream is = null;

        XMLInputFactory inFactory = null;

        XMLStreamReader xmlParser = null;

        Artist a = new Artist();

        ArtistsImage ai = new ArtistsImage();


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
                    if (xmlParser.getLocalName().equals("artists")) {
                        artists = true;
                    } else if (xmlParser.getLocalName().equals("artist")) {
                        artist = true;
                        a = new Artist();
                    } else if (xmlParser.getLocalName().equals("images")) {
                        images = true;
                    } else if (xmlParser.getLocalName().equals("image") && images == true) {
                        image = true;
                        // subelement of images reached - values are attached as attributes to <image ... />
                        String height = xmlParser.getAttributeValue(null, "height");
                        String type = xmlParser.getAttributeValue(null, "type");
                        String uri = xmlParser.getAttributeValue(null, "uri");
                        String width = xmlParser.getAttributeValue(null, "width");
                        Image image = new Image(height, width, uri, type);
                        ai.setArtistId(a.getId());
                        ai.setImage(image);
                    } else if (xmlParser.getLocalName().equals("id")) {
                        id = true;
                    } else if (xmlParser.getLocalName().equals("name") && !aliases) {
                        name = true;
                    } else if (xmlParser.getLocalName().equals("name") && aliases) {
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
                        //aa.setAliasID(xmlParser.getAttributeValue(null, "id")); //TODO: should not work as there is no id attribute at an alias element
                        aliases = true;
                    }
                    break;
                case XMLStreamConstants.CHARACTERS:
                    if (id) {
                        a.setId(Integer.parseInt(xmlParser.getText()));
                        id = false;
                    } else if (name && !namevariations && !aliases) {
                        a.setName(xmlParser.getText());
                        name = false;
                    } else if (realname) {
                        a.setRealname(xmlParser.getText());
                        realname = false;
                    } else if (profile) {
                        a.setProfile(xmlParser.getText());
                        profile = false;
                    } else if (dataquality) {
                        a.setDataQuality(xmlParser.getText());
                        dataquality = false;
                    } else if (name && namevariations && !aliases) {
                        a.addNamevariation(xmlParser.getText());
                        name = false;
                    } else if (name && !namevariations && aliases) {
                        a.addAlias(xmlParser.getText());
                    }
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    if(xmlParser.getLocalName().equals("artists")) {
                        artists = false;
                    } else if (xmlParser.getLocalName().equals("artist")) {
                        artist = false;
                        try {
                            writer.insertArtist(a);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    } else if (xmlParser.getLocalName().equals("images")) {
                        images = false;
                    } else if (xmlParser.getLocalName().equals("image") && images) {
                        image = false;
                        try {
                            writer.insertArtistImage(ai);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        ai = new ArtistsImage();
                    } else if (xmlParser.getLocalName().equals("namevariations")) {
                        namevariations = false;
                    } else if (xmlParser.getLocalName().equals("aliases")) {
                        aliases = false;
                    } else if (xmlParser.getLocalName().equals("profile")) {
                        profile = false;
                    } else if (xmlParser.getLocalName().equals("realname")) {
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

