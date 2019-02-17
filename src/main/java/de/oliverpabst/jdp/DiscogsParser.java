package de.oliverpabst.jdp;

import de.oliverpabst.jdp.parser.ArtistParser;
import de.oliverpabst.jdp.parser.LabelParser;
import de.oliverpabst.jdp.parser.MasterParser;
import org.apache.commons.cli.*;

import java.io.File;

public class DiscogsParser {
    public static void main(String[] args) {
        Options options = new Options();

        Option dumpDate = new Option("d", "date", true, "Date of the creation of the dump (part of the filename)");
        dumpDate.setRequired(true);
        options.addOption(dumpDate);

        CommandLineParser clParser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmdLine = null;

        try {
            cmdLine = clParser.parse(options, args);
        } catch (ParseException e) {
            System.out.println("Arguments could not be parsed.");
            formatter.printHelp("DiscogsParser", options);
        }

        String date = cmdLine.getOptionValue("d");

        File artistsFile = new File("data_" + date + "/discogs_" + date + "_artists.xml");
        File labelsFile = new File("data_" + date + "/discogs_" + date + "_labels.xml");
        File mastersFile = new File("data_" + date + "/discogs_" + date + "_masters.xml");
        File releasesFile = new File("data_" + date + "/discogs_" + date + "_releases.xml");

        //ArtistParser ap = new ArtistParser(artistsFile);
        //LabelParser lp = new LabelParser(labelsFile);
        MasterParser mp = new MasterParser(mastersFile);
    }
}
