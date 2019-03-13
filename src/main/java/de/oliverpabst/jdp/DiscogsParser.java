package de.oliverpabst.jdp;

import de.oliverpabst.jdp.database.ConnectionParameters;
import de.oliverpabst.jdp.database.SchemaDoesNotExistException;
import de.oliverpabst.jdp.database.postgresql.PostgreSQLConnector;
import de.oliverpabst.jdp.parser.ArtistParser;
import de.oliverpabst.jdp.parser.LabelParser;
import de.oliverpabst.jdp.parser.MasterParser;
import de.oliverpabst.jdp.parser.ReleaseParser;
import org.apache.commons.cli.*;

import java.io.File;

public class DiscogsParser {
    public static void main(String[] args) {
        Options options = new Options();

        Option dumpDate = new Option("d", "date", true, "Date of the creation of the dump (part of the filename)");
        Option hostName = new Option("h", "hostname", true, "Name of the databaseserver");
        Option dbPort = new Option("p", "password", true, "Port of the running database instance");
        Option dbName = new Option("db", "database", true, "Name of the database");
        Option dbUser = new Option("u", "username", true, "Database username");
        Option dbPassword = new Option("pw", "password", true, "Corresponding password for database username");
        dumpDate.setRequired(true);
        options.addOption(dumpDate);
        options.addOption(hostName);
        options.addOption(dbPort);
        options.addOption(dbName);
        options.addOption(dbUser);
        options.addOption(dbPassword);

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

        String hostname = cmdLine.getOptionValue("h");
        String port = cmdLine.getOptionValue("p");
        String db = cmdLine.getOptionValue("db");
        String username = cmdLine.getOptionValue("u");
        String password = cmdLine.getOptionValue("pw");

        ConnectionParameters cParams = new ConnectionParameters(hostname, port, db, username, password);

        try {
            PostgreSQLConnector.getInstance().connect(cParams);
        } catch (SchemaDoesNotExistException e) {
            System.err.println(e.getMessage());
            System.exit(1); // Exit because db schema does not exist in the specified database
        }

        ArtistParser ap = new ArtistParser(artistsFile);
        LabelParser lp = new LabelParser(labelsFile);
        MasterParser mp = new MasterParser(mastersFile);
        ReleaseParser rp = new ReleaseParser(releasesFile);
    }
}
