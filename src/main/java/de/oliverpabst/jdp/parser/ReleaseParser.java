package de.oliverpabst.jdp.parser;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * <releases>
 *     <release id="..." status="...">
 *         <images>
 *             <image height="..." type="..." uri="..." uri150="..." width="..." />
 *         </images>
 *         <artists>
 *             <id> </id>
 *             <name> </name>
 *             <anv> </anv>
 *             <join> </join>
 *             <role> </role>
 *             <tracks>
 *                 // TODO: here should be tracks
 *             </tracks>
 *
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

    public ReleaseParser(File _file) {
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
                    }


                    break;
                case XMLStreamConstants.CHARACTERS:
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    break;
                default:
                    break;
            }

            xmlParser.next();
        }
    }
}
