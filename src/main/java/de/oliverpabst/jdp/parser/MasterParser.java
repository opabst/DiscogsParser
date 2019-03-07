package de.oliverpabst.jdp.parser;

import de.oliverpabst.jdp.model.Image;
import de.oliverpabst.jdp.model.master.MasterArtist;
import de.oliverpabst.jdp.model.master.MasterEntity;
import de.oliverpabst.jdp.model.master.MasterVideo;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * <masters>
 *     <master>
 *         <images>
 *             <image height="..." type="..." uri="..." uri150="..." width="..."
 *         </images>
 *         <artists>
 *             <artist>
 *                 <id> </id>
 *                 <name> </name>
 *                 <anv> </anv>
 *                 <join> </join>
 *                 <role> </role>
 *                 <tracks> TODO: parse tracks </tracks>
 *             </artist>
 *         </artists>
 *         <genres>
 *             <genre> </genre>
 *         </genres>
 *         <styles>
 *             <style> </style>
 *         </styles>
 *         <year> </year>
 *         <title> </title>
 *         <data_quality> </data_quality>
 *         <videos>
 *             <video duration="..." embed="..." src="...">
 *                 <title> </title>
 *                 <description> </description>
 *             </video>
 *         </videos>
 *     </master>
 * </masters>
 */

// TODO: Videos parsen

public class MasterParser {

    private boolean masters = false;
    private boolean master = false;
    private boolean images = false;
    private boolean artists = false;
    private boolean artist = false;
    private boolean tracks = false;
    private boolean role = false;
    private boolean name = false;
    private boolean anv = false;
    private boolean join = false;
    private boolean id = false;
    private boolean genres = false;
    private boolean genre = false;
    private boolean styles = false;
    private boolean style = false;
    private boolean year = false;
    private boolean title = false;
    private boolean dataQualitiy = false;
    private boolean videos = false;
    private boolean video = false;
    private boolean description = false;

    public MasterParser(File _file) {
        try {
            parse(_file);
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
    }

    private void parse(File _masterFile) throws XMLStreamException {
        InputStream is = null;
        try {
            is = new FileInputStream(_masterFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        XMLInputFactory inFactory = XMLInputFactory.newInstance();

        XMLStreamReader xmlParser = inFactory.createXMLStreamReader(is);

        MasterEntity me = null;
        MasterArtist ma = null;
        MasterVideo mv = null;

        while(xmlParser.hasNext()) {
            switch(xmlParser.getEventType()) {
                case XMLStreamConstants.START_DOCUMENT:
                    // DO NOTHING
                    break;
                case XMLStreamConstants.END_DOCUMENT:
                    // EOF reached
                    xmlParser.close();
                    break;
                case XMLStreamConstants.START_ELEMENT:
                    String startElem = xmlParser.getLocalName();
                    if (xmlParser.getLocalName().equals("masters")) {
                        masters = true;
                    } else if (xmlParser.getLocalName().equals("master")) {
                        master = true;
                        me = new MasterEntity();
                    } else if (xmlParser.getLocalName().equals("images")) {
                        images = true;
                    } else if (xmlParser.getLocalName().equals("image") && images) {
                        String height = xmlParser.getAttributeValue(null, "height");
                        String type = xmlParser.getAttributeValue(null, "type");
                        String uri = xmlParser.getAttributeValue(null, "uri");
                        String uri150 = xmlParser.getAttributeValue(null, "uri150");
                        String width = xmlParser.getAttributeValue(null, "width");
                        Image image = new Image(height, width, uri, uri150, type);
                        me.addImage(image);
                    } else if (xmlParser.getLocalName().equals("artists")) {
                        artists = true;
                        ma = new MasterArtist();
                    } else if (xmlParser.getLocalName().equals("artist")) {
                        artist = true;
                    } else if (artist && xmlParser.getLocalName().equals("id")) {
                        id = true;
                    } else if (artist && xmlParser.getLocalName().equals("anv")) {
                        anv = true;
                    } else if (artist && xmlParser.getLocalName().equals("join")) {
                        join = true;
                    } else if (artist && xmlParser.getLocalName().equals("name")) {
                        name = true;
                    } else if (artist && xmlParser.getLocalName().equals("role")) {
                        role = true;
                    } else if (artist && xmlParser.getLocalName().equals("tracks")) {
                        tracks = true;
                    } else if (xmlParser.getLocalName().equals("genres")) {
                        genres = true;
                    } else if (genres && xmlParser.getLocalName().equals("genre")) {
                        genre = true;
                    } else if (xmlParser.getLocalName().equals("styles")) {
                        styles = true;
                    } else if (styles && xmlParser.getLocalName().equals("style")) {
                        style = true;
                    } else if (xmlParser.getLocalName().equals("title")) {
                        title = true;
                    } else if (xmlParser.getLocalName().equals("data_quality")) {
                        dataQualitiy = true;
                    } else if (xmlParser.getLocalName().equals("year")) {
                        year = true;
                    } else if (xmlParser.getLocalName().equals("videos")) {
                        videos = true;
                    } else if (xmlParser.getLocalName().equals("video")) {
                        video = true;
                        String duration = xmlParser.getAttributeValue(null, "duration");
                        String embed = xmlParser.getAttributeValue(null, "embed");
                        String source = xmlParser.getAttributeValue(null, "src");

                        mv = new MasterVideo(duration, embed, source);
                    }

                    break;
                case XMLStreamConstants.CHARACTERS:
                    // Artist, Style und Genre verarbeitet
                    String chars = xmlParser.getText();
                    if(artist && id) {
                        ma.setId(Integer.parseInt(xmlParser.getText()));
                    } else if (artist && role) {
                        ma.setRole(xmlParser.getText());
                    } else if (artist && join) {
                        ma.setRole(xmlParser.getText());
                    } else if (artist && anv) {
                        ma.setRole(xmlParser.getText());
                    } else if (artist && name) {
                        ma.setName(xmlParser.getText());
                    } else if (genres && genre) {
                        me.addGenre(xmlParser.getText());
                    } else if (styles && style) {
                        me.addStyle(xmlParser.getText());
                    } else if (!videos && title) {
                        me.setTitle(xmlParser.getText());
                    } else if (year) {
                        me.setYear(xmlParser.getText());
                    } else if (dataQualitiy) {
                        me.setDataQuality(xmlParser.getText());
                    } else if (videos && video && title) {
                        mv.setTitle(xmlParser.getText());
                    } else if (videos && video && description) {
                        mv.setDescription(xmlParser.getText());
                    }
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    String endElem = xmlParser.getLocalName();
                    if(xmlParser.getLocalName().equals("masters")) {
                        masters = false;
                    } else if (xmlParser.getLocalName().equals("master")) {
                        master = false;
                    } else if (xmlParser.getLocalName().equals("images")) {
                        images = false;
                    } else if (xmlParser.getLocalName().equals("artists")) {
                        artists = false;
                    } else if (xmlParser.getLocalName().equals("artist")) {
                        me.addArtist(ma);
                        artist = false;
                    } else if (xmlParser.getLocalName().equals("genre")) {
                        genre = false;
                    } else if (xmlParser.getLocalName().equals("genres")) {
                        genres = false;
                    } else if (xmlParser.getLocalName().equals("style")) {
                        style = false;
                    } else if (xmlParser.getLocalName().equals("styles")) {
                        styles = false;
                    } else if (xmlParser.getLocalName().equals("id")) {
                        id = false;
                    } else if (xmlParser.getLocalName().equals("join")) {
                        join = false;
                    } else if (xmlParser.getLocalName().equals("role")) {
                        role = false;
                    } else if (xmlParser.getLocalName().equals("anv")) {
                        anv = false;
                    } else if (xmlParser.getLocalName().equals("tracks")) {
                        tracks = false;
                    } else if (xmlParser.getLocalName().equals("title")) {
                        title = false;
                    } else if (xmlParser.getLocalName().equals("data_quality")) {
                        dataQualitiy = false;
                    } else if (xmlParser.getLocalName().equals("year")) {
                        year = false;
                    } else if (xmlParser.getLocalName().equals("video")) {
                        video = false;
                        me.addVideo(mv);
                    } else if (xmlParser.getLocalName().equals("videos")) {
                        videos = false;
                    }
                    break;
                default:
                    break;
            }

            xmlParser.next();
        }
    }
}
