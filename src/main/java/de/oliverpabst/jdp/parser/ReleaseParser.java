package de.oliverpabst.jdp.parser;

import de.oliverpabst.jdp.database.postgresql.ReleaseWriter;
import de.oliverpabst.jdp.model.Image;
import de.oliverpabst.jdp.model.release.*;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.*;
import java.sql.SQLException;

/**
 * <releases>
 *     <release id="..." status="...">
 *         <images>
 *             <image height="..." type="..." uri="..." uri150="..." width="..." />
 *         </images>
 *         <artists>
 *              <artist>
 *                  <id> </id>
 *                  <name> </name>
 *                  <anv> </anv>
 *                  <join> </join>
 *                  <role> </role>
 *                  <tracks>
 *                 // TODO: here should be tracks
 *                  </tracks>
 *             </artist>
 *         </artists>
 *         <title> </title>
 *         <labels>
 *             <label catno="..." id="..." name="..."/>
 *         </labels>
 *         <extraartists>
 *             <artist>
 *                 <id> </id>
 *                 <name> </name>
 *                 <anv> </anv>
 *                 <join> </join>
 *                 <role> </role>
 *                 <tracks> TODO: here should be tracks
 *                 </tracks>
 *             </artist>
 *         </extraartists>
 *         <formats>
 *             <format name="..." qty="..." text="...">
 *                 <descriptions>
 *                     <description> </description>
 *                 </descriptions>
 *             </format>
 *         </formats>
 *         <genres>
 *             <genre> </genre>
 *         </genres>
 *         <styles>
 *             <style> </style>
 *         </styles>
 *         <country> </country>
 *         <released> </released>
 *         <notes> </notes>
 *         <data_quality> </data_quality>
 *         <tracklist>
 *             <track>
 *                 <position> </position>
 *                 <title> </title>
 *                 <duration> </duration>
 *             </track>
 *         </tracklist>
 *         <identifiers>
 *             <identifier description="..." type="..." value="..." />
 *         </identifiers>
 *         <videos>
 *             <video duration="..." embed="..." src="...">
 *                 <title> </title>
 *                 <description> </description>
 *             </video>
 *         </videos>
 *         <companies>
 *             <company>
 *                 <id> </id>
 *                 <name> </name>
 *                 <catno> </catno>
 *                 <entity_type> </entity_type>
 *                 <entity_type_name> </entity_type_name>
 *                 <resource_url> </resource_url>
 *             </company>
 *         </companies>
 *     </release>
 * </releases>
 */

public class ReleaseParser {

    private boolean releases = false;
    private boolean release = false;
    private boolean images = false;
    private boolean image = false;
    private boolean artists = false;
    private boolean artist = false;
    private boolean id = false;
    private boolean name = false;
    private boolean anv = false;
    private boolean join = false;
    private boolean role = false;
    private boolean tracks = false;
    private boolean title = false;
    private boolean track = false;
    private boolean video = false;
    private boolean labels = false;
    private boolean label = false;
    private boolean extraartists = false;
    private boolean formats = false;
    private boolean format = false;
    private boolean descriptions = false;
    private boolean description = false;
    private boolean genres = false;
    private boolean genre = false;
    private boolean styles = false;
    private boolean style = false;
    private boolean country = false;
    private boolean released = false;
    private boolean notes = false;
    private boolean dataQuality = false;
    private boolean tracklist = false;
    private boolean position = false;
    private boolean duration = false;
    private boolean identifiers = false;
    private boolean identifier = false;
    private boolean videos = false;
    private boolean companies = false;
    private boolean company = false;
    private boolean catno = false;
    private boolean entityType = false;
    private boolean entityTypeName = false;
    private boolean resourceUrl = false;

    private ReleaseWriter writer;

    public ReleaseParser(File _file) {
        writer = ReleaseWriter.getInstance();
        try {
            parse(_file);
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
    }

    private void parse(File _releaseFile) throws XMLStreamException {
        InputStream is = null;

        try {
            is = new FileInputStream(_releaseFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        XMLInputFactory inFactory = XMLInputFactory.newInstance();

        XMLStreamReader xmlParser = inFactory.createXMLStreamReader(is);

        ReleaseEntity re = null;
        ReleaseArtist ra = null;
        ReleaseExtraArtist rea = null;
        ReleaseFormat rf = null;
        ReleaseTrack rt = null;
        ReleaseVideo rv = null;
        ReleaseCompany rc = null;

        while(xmlParser.hasNext()) {
            switch(xmlParser.getEventType()) {
                case XMLStreamConstants.START_DOCUMENT:
                    // Do Nothing
                    break;
                case XMLStreamConstants.END_DOCUMENT:
                    // EOF reached
                    xmlParser.close();
                case XMLStreamConstants.START_ELEMENT:
                    if(xmlParser.getLocalName().equals("releases")) {
                        releases = true;
                    } else if (xmlParser.getLocalName().equals("release")) {
                        release = true;
                        re = new ReleaseEntity();
                        String id = xmlParser.getAttributeValue(null, "id");
                        String status = xmlParser.getAttributeValue(null, "status");
                        re.setId(id);
                        re.setStatus(status);
                    } else if (xmlParser.getLocalName().equals("images")) {
                        images = true;
                    } else if (images && xmlParser.getLocalName().equals("image")) {
                        image = true;
                        String height = xmlParser.getAttributeValue(null,"height");
                        String type = xmlParser.getAttributeValue(null, "type");
                        String uri = xmlParser.getAttributeValue(null, "uri");
                        String uri150 = xmlParser.getAttributeValue(null, "uri150");
                        String width = xmlParser.getAttributeValue(null, "width");
                        re.addImage(new Image(height, width, uri, uri150, type));
                    } else if (xmlParser.getLocalName().equals("artists")) {
                        artists = true;
                    } else if (artists && xmlParser.getLocalName().equals("artist")) {
                        artist = true;
                        ra = new ReleaseArtist();
                    } else if (artist && xmlParser.getLocalName().equals("id")) {
                        id = true;
                    } else if (artist && xmlParser.getLocalName().equals("name")) {
                        name = true;
                    } else if (artist && xmlParser.getLocalName().equals("anv")) {
                        anv = true;
                    } else if (artist && xmlParser.getLocalName().equals("join")) {
                        join = true;
                    } else if (artist && xmlParser.getLocalName().equals("role")) {
                        role = true;
                    } else if(artist && xmlParser.getLocalName().equals("tracks")) {
                        tracks = true;
                    } else if (!track && !video && xmlParser.getLocalName().equals("title")) {
                        title = true;
                    } else if (xmlParser.getLocalName().equals("labels")) {
                        labels = true;
                    } else if (xmlParser.getLocalName().equals("label")) {
                        label = true;
                        String catno = xmlParser.getAttributeValue(null, "catno");
                        String id = xmlParser.getAttributeValue(null, "id");
                        String name = xmlParser.getAttributeValue(null, "name");
                        re.addLabel(new ReleaseLabel(catno, id, name));
                    } else if (xmlParser.getLocalName().equals("extraartists")) {
                        extraartists = true;
                    } else if (extraartists && xmlParser.getLocalName().equals("artist")) {
                        artist = true;
                        rea = new ReleaseExtraArtist();
                    } else if (extraartists && artist && xmlParser.getLocalName().equals("id")) {
                        id = true;
                    } else if (extraartists && artist && xmlParser.getLocalName().equals("name")) {
                        name = true;
                    } else if (extraartists && artist && xmlParser.getLocalName().equals("anv")) {
                        anv = true;
                    } else if (extraartists && artist && xmlParser.getLocalName().equals("join")) {
                        join = true;
                    } else if (extraartists && artist && xmlParser.getLocalName().equals("role")) {
                        role = true;
                    } else if (extraartists && artist && xmlParser.getLocalName().equals("tracks")) {
                        tracks = true;
                    } else if (xmlParser.getLocalName().equals("formats")) {
                        formats = true;
                    } else if (formats && xmlParser.getLocalName().equals("format")) {
                        format = true;
                        String name = xmlParser.getAttributeValue(null, "name");
                        String qty = xmlParser.getAttributeValue(null, "qty");
                        String text = xmlParser.getAttributeValue(null, "text");
                        rf = new ReleaseFormat(name, qty, text);
                    } else if (format && xmlParser.getLocalName().equals("descriptions")) {
                        descriptions = true;
                    } else if (formats && format && descriptions && xmlParser.getLocalName().equals("description")) {
                        description = true;
                    } else if (xmlParser.getLocalName().equals("genres")) {
                        genres = true;
                    } else if (genres && xmlParser.getLocalName().equals("genre")) {
                        genre = true;
                    } else if (xmlParser.getLocalName().equals("styles")) {
                        styles = true;
                    } else if (styles && xmlParser.getLocalName().equals("style")) {
                        style = true;
                    } else if (xmlParser.getLocalName().equals("country")) {
                        country = true;
                    } else if (xmlParser.getLocalName().equals("released")) {
                        released = true;
                    } else if (xmlParser.getLocalName().equals("notes")) {
                        notes = true;
                    } else if (xmlParser.getLocalName().equals("data_quality")) {
                        dataQuality = true;
                    } else if (xmlParser.getLocalName().equals("tracklist")) {
                        tracklist = true;
                    } else if (tracklist && xmlParser.getLocalName().equals("track")) {
                        track = true;
                        rt = new ReleaseTrack();
                    } else if (tracklist && track && xmlParser.getLocalName().equals("postion")) {
                        position = true;
                    } else if (tracklist && track && xmlParser.getLocalName().equals("title")) {
                        title = true;
                    } else if (tracklist && track && xmlParser.getLocalName().equals("duration")) {
                        duration = true;
                    } else if (xmlParser.getLocalName().equals("identifiers")) {
                        identifiers = true;
                    } else if (identifiers && xmlParser.getLocalName().equals("identifier")) {
                        identifier = true;
                        String description = xmlParser.getAttributeValue(null, "description");
                        String type = xmlParser.getAttributeValue(null, "type");
                        String value = xmlParser.getAttributeValue(null, "value");
                        re.addIdentifier(new ReleaseIdentifier(description, type, value));
                    } else if (xmlParser.getLocalName().equals("videos")) {
                        videos = true;
                    } else if (videos && xmlParser.getLocalName().equals("video")) {
                        video = true;
                        String duration = xmlParser.getAttributeValue(null, "duration");
                        String embed = xmlParser.getAttributeValue(null, "embed");
                        String src = xmlParser.getAttributeValue(null, "src");
                        rv = new ReleaseVideo(duration, embed, src);
                    } else if (videos && video && xmlParser.getLocalName().equals("title")) {
                        title = true;
                    } else if (videos && video && xmlParser.getLocalName().equals("description")) {
                        description = true;
                    } else if (xmlParser.getLocalName().equals("companies")) {
                        companies = true;
                    } else if (companies && xmlParser.getLocalName().equals("company")) {
                        company = true;
                        rc = new ReleaseCompany();
                    } else if (companies && company && xmlParser.getLocalName().equals("id")) {
                        id = true;
                    } else if (companies && company && xmlParser.getLocalName().equals("name")) {
                        name = true;
                    } else if (companies && company && xmlParser.getLocalName().equals("catno")) {
                        catno = true;
                    } else if (companies && company && xmlParser.getLocalName().equals("entity_type")) {
                        entityType = true;
                    } else if (companies && company && xmlParser.getLocalName().equals("entity_type_name")) {
                        entityTypeName = true;
                    } else if (companies && company && xmlParser.getLocalName().equals("resource_url")) {
                        resourceUrl = true;
                    }

                    break;
                case XMLStreamConstants.CHARACTERS:
                    String chars = xmlParser.getText();
                    if(!extraartists && artist && id) {
                        ra.setId(xmlParser.getText());
                    } else if (!extraartists && artist && name) {
                        ra.setName(xmlParser.getText());
                    } else if (!extraartists && artist && anv) {
                        ra.setAnv(xmlParser.getText());
                    } else if (!extraartists && artist && join) {
                        ra.setJoin(xmlParser.getText());
                    } else if (!extraartists && artist && role) {
                        ra.setRole(xmlParser.getText());
                    } else if (!track && !video && title) {
                        re.setTitle(xmlParser.getText());
                    } else if (extraartists && artist && id) {
                        rea.setId(xmlParser.getText());
                    } else if (extraartists && artist && name) {
                        rea.setName(xmlParser.getText());
                    } else if (extraartists && artist && anv) {
                        rea.setAnv(xmlParser.getText());
                    } else if (extraartists && artist && join) {
                        rea.setJoin(xmlParser.getText());
                    } else if (extraartists && artist && role) {
                        rea.setRole(xmlParser.getText());
                    } else if (formats && format && descriptions && description) {
                        rf.addDescription(xmlParser.getText());
                    } else if (genres && genre) {
                        re.addGenre(xmlParser.getText());
                    } else if (styles && style) {
                        re.addStyle(xmlParser.getText());
                    } else if (country) {
                        re.setCountry(xmlParser.getText());
                    } else if (released) {
                        re.setReleased(xmlParser.getText());
                    } else if (notes) {
                        re.setNotes(xmlParser.getText());
                    } else if (dataQuality) {
                        re.setDataQuality(xmlParser.getText());
                    } else if (tracklist && track && position) {
                        rt.setPosition(xmlParser.getText());
                    } else if (tracklist && track && title) {
                        rt.setTitle(xmlParser.getText());
                    } else if (tracklist && track && duration) {
                        rt.setDuration(xmlParser.getText());
                    } else if (videos && video && title) {
                        rv.setTitle(xmlParser.getText());
                    } else if (videos && video && description) {
                        rv.setTitle(xmlParser.getText());
                    } else if (companies && company && id) {
                        rc.setId(xmlParser.getText());
                    } else if (companies && company && name) {
                        rc.setName(xmlParser.getText());
                    } else if (companies && company && catno) {
                        rc.setCatno(xmlParser.getText());
                    } else if (companies && company && entityType) {
                        rc.setEntityType(xmlParser.getText());
                    } else if (companies && company && entityTypeName) {
                        rc.setEntityTypeValue(xmlParser.getText());
                    } else if (companies && company && resourceUrl) {
                        rc.setResourceUrl(xmlParser.getText());
                    }
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    if(xmlParser.getLocalName().equals("releases")) {
                        releases = false;
                    } else if (xmlParser.getLocalName().equals("release")) {
                        release = false;
                        try {
                            writer.insertRelease(re);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    } else if (xmlParser.getLocalName().equals("images")) {
                        images = false;
                    } else if (images && xmlParser.getLocalName().equals("image")) {
                        image = false;
                    } else if (artists && xmlParser.getLocalName().equals("artist")) {
                        re.addArtist(ra);
                        artist = false;
                    } else if (xmlParser.getLocalName().equals("artists")) {
                        artists = false;
                    } else if (artist && xmlParser.getLocalName().equals("id")) {
                        id = false;
                    } else if (artist && xmlParser.getLocalName().equals("name")) {
                        name = false;
                    } else if (artist && xmlParser.getLocalName().equals("anv")) {
                        anv = false;
                    } else if (artist && xmlParser.getLocalName().equals("join")) {
                        join = false;
                    } else if (artist && xmlParser.getLocalName().equals("role")) {
                        role = false;
                    } else if (artist && xmlParser.getLocalName().equals("tracks")) {
                        tracks = false;
                    } else if (!track && !video && xmlParser.getLocalName().equals("title")) {
                        title = false;
                    } else if (xmlParser.getLocalName().equals("labels")) {
                        labels = false;
                    } else if (xmlParser.getLocalName().equals("label")) {
                        label = false;
                    } else if (xmlParser.getLocalName().equals("extraartists")) {
                        extraartists = false;
                    } else if (extraartists && xmlParser.getLocalName().equals("artist")) {
                        artist = false;
                        re.addExtraArtist(rea);
                    } else if (extraartists && artist && xmlParser.getLocalName().equals("id")) {
                        id = false;
                    } else if (extraartists && artist && xmlParser.getLocalName().equals("name")) {
                        name = false;
                    } else if (extraartists && artist && xmlParser.getLocalName().equals("anv")) {
                        anv = false;
                    } else if (extraartists && artist && xmlParser.getLocalName().equals("join")) {
                        join = false;
                    } else if (extraartists && artist && xmlParser.getLocalName().equals("role")) {
                        role = false;
                    } else if (extraartists && artist && xmlParser.getLocalName().equals("tracks")) {
                        tracks = false;
                    } else if (formats && format && descriptions && xmlParser.getLocalName().equals("description")) {
                        description = false;
                    } else if (formats && format && xmlParser.getLocalName().equals("descriptions")) {
                        descriptions = false;
                    } else if (formats && xmlParser.getLocalName().equals("format")) {
                        format = false;
                        re.addFormat(rf);
                    } else if (xmlParser.getLocalName().equals("formats")) {
                        formats = false;
                    } else if (genres && xmlParser.getLocalName().equals("genre")) {
                        genre = false;
                    } else if (xmlParser.getLocalName().equals("genres")) {
                        genres = false;
                    } else if (styles && xmlParser.getLocalName().equals("style")) {
                        style = false;
                    } else if (xmlParser.getLocalName().equals("styles")) {
                        styles = false;
                    } else if (xmlParser.getLocalName().equals("country")) {
                        country = false;
                    } else if (xmlParser.getLocalName().equals("released")) {
                        released = false;
                    } else if (xmlParser.getLocalName().equals("notes")) {
                        notes = false;
                    } else if (xmlParser.getLocalName().equals("data_quality")) {
                        dataQuality = false;
                    } else if (tracklist && track && xmlParser.getLocalName().equals("duration")) {
                        duration = false;
                    } else if (tracklist && track && xmlParser.getLocalName().equals("title")) {
                        title = false;
                    } else if (tracklist && track && xmlParser.getLocalName().equals("position")) {
                        position = false;
                    } else if (tracklist && xmlParser.getLocalName().equals("track")) {
                        re.addTrack(rt);
                        track = false;
                    } else if (xmlParser.getLocalName().equals("tracklist")) {
                        tracklist = false;
                    } else if (identifiers && xmlParser.getLocalName().equals("identifier")) {
                        identifier = false;
                    } else if (xmlParser.getLocalName().equals("identifiers")) {
                        identifiers = false;
                    } else if (videos && video && xmlParser.getLocalName().equals("description")) {
                        description = false;
                    } else if (videos && video && xmlParser.getLocalName().equals("title")) {
                        title = false;
                    } else if (videos && xmlParser.getLocalName().equals("video")) {
                        re.addVideo(rv);
                        video = false;
                    } else if (xmlParser.getLocalName().equals("videos")) {
                        videos = false;
                    } else if (companies && company && xmlParser.getLocalName().equals("id")) {
                        id = false;
                    } else if (companies && company && xmlParser.getLocalName().equals("name")) {
                        name = false;
                    } else if (companies && company && xmlParser.getLocalName().equals("catno")) {
                        catno = false;
                    } else if (companies && company && xmlParser.getLocalName().equals("entity_type")) {
                        entityType = false;
                    } else if (companies && company && xmlParser.getLocalName().equals("entity_type_name")) {
                        entityTypeName = false;
                    } else if (companies && company && xmlParser.getLocalName().equals("resource_url")) {
                        resourceUrl = false;
                    } else if (companies && xmlParser.getLocalName().equals("company")) {
                        company = false;
                        re.addCompany(rc);
                    } else if (xmlParser.getLocalName().equals("companies")) {
                        companies = false;
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
