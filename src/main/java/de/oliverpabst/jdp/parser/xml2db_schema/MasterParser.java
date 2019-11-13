package de.oliverpabst.jdp.parser.xml2db_schema;

import de.oliverpabst.jdp.database.postgresql.xml2db_schema.MasterWriter;

import de.oliverpabst.jdp.model.Image;
import de.oliverpabst.jdp.model.xml2db_schema.master.*;
import de.oliverpabst.jdp.parser.AbstractMasterParser;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.*;
import java.sql.SQLException;

/**
 * <masters>
 *     <master id="...">
 *         <main_release></main_release>
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

public class MasterParser extends AbstractMasterParser {

    private MasterWriter writer;

    public MasterParser(File _file) {
        writer = MasterWriter.getInstance();
        try {
            parse(_file);
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
    }

    protected void parse(File _masterFile) throws XMLStreamException {
        InputStream is = null;
        try {
            is = new FileInputStream(_masterFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        XMLInputFactory inFactory = XMLInputFactory.newInstance();

        XMLStreamReader xmlParser = inFactory.createXMLStreamReader(is);

        Master m = null;
        MasterArtists ma = null;
        MastersArtistsJoins maj = null;
        MastersExtraartists mea = null;
        MastersFormats mf = null;
        MastersImages mi = null;
        Integer masterId = Integer.MIN_VALUE;

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
                        m = new Master();
                        masterId = Integer.parseInt(xmlParser.getAttributeValue(null, "id"));
                        m.setId(masterId);
                    } else if (xmlParser.getLocalName().equals("main_release")) {
                        mainRelease = true;
                    } else if (xmlParser.getLocalName().equals("images")) {
                        images = true;
                    } else if (xmlParser.getLocalName().equals("image") && images) {
                        String height = xmlParser.getAttributeValue(null, "height");
                        String type = xmlParser.getAttributeValue(null, "type");
                        String uri = xmlParser.getAttributeValue(null, "uri");
                        String width = xmlParser.getAttributeValue(null, "width");
                        mi = new MastersImages();
                        Image image = new Image(height, width, uri, type);
                        mi.setMasterId(masterId);
                        mi.setImage(image);
                    } else if (xmlParser.getLocalName().equals("artists")) {
                        artists = true;
                        ma = new MasterArtists();
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

                    }

                    break;
                case XMLStreamConstants.CHARACTERS:
                    // Artist, Style und Genre verarbeitet
                    String chars = xmlParser.getText();
                    if(mainRelease) {
                        m.setMainRelease(Integer.parseInt(xmlParser.getText()));
                    } else if(artist && id) {
                        //ma.setMaster(Integer.parseInt(xmlParser.getText())); ignore
                    } else if (artist && role) {
                        m.setRole(xmlParser.getText());
                    } else if (artist && join) {
                        ma.setJoin(xmlParser.getText());
                    } else if (artist && anv) {
                        ma.setAnv(xmlParser.getText());
                    } else if (artist && name) {
                        ma.setArtistName(xmlParser.getText());
                    } else if (genres && genre) {
                        m.setGenres(xmlParser.getText());
                    } else if (styles && style) {
                        m.setStyles(xmlParser.getText());
                    } else if (!videos && title) {
                        m.setTitle(xmlParser.getText());
                    } else if (year) {
                        m.setYear(Integer.parseInt(xmlParser.getText()));
                    } else if (dataQualitiy) {
                        m.setDataQuality(xmlParser.getText());
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
                        try {
                            writer.insertMaster(m);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    } else if (xmlParser.getLocalName().equals("main_release")) {
                        mainRelease = false;
                    } else if (xmlParser.getLocalName().equals("images")) {
                        images = false;
                    } else if (xmlParser.getLocalName().equals("artists")) {
                        artists = false;
                    } else if (xmlParser.getLocalName().equals("artist")) {
                        try {
                            writer.insertMasterArtists(ma);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
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

