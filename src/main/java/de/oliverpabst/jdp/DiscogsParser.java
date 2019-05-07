package de.oliverpabst.jdp;

import de.oliverpabst.jdp.database.ConnectionParameters;
import de.oliverpabst.jdp.database.SchemaDoesNotExistException;
import de.oliverpabst.jdp.database.postgresql.PostgreSQLConnection;
import de.oliverpabst.jdp.thread.ArtistThread;
import de.oliverpabst.jdp.thread.LabelThread;
import de.oliverpabst.jdp.thread.MasterThread;
import de.oliverpabst.jdp.thread.ReleaseThread;
import org.apache.commons.cli.*;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class DiscogsParser {
    private static ConnectionParameters params;

    private static Boolean showStatistics = true;

    private static ExecutorService pool;

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

        params = new ConnectionParameters(hostname, port, db, username, password);

        try {
            PostgreSQLConnection con = new PostgreSQLConnection(DiscogsParser.getConnectionParameters());
        } catch (SchemaDoesNotExistException e) {
            System.err.println(e.getMessage());
            System.exit(1); // Exit because db schema does not exist in the specified database
        } finally {

        }

        int poolsize = Runtime.getRuntime().availableProcessors();
        pool = Executors.newFixedThreadPool(poolsize);

        Thread at = new Thread(new ArtistThread(artistsFile));
        pool.submit(at);

        Thread lt = new Thread(new LabelThread(labelsFile));
        pool.submit(lt);

        Thread mt = new Thread(new MasterThread(mastersFile));
        pool.submit(mt);

        Thread rt = new Thread(new ReleaseThread(releasesFile));
        pool.submit(rt);

        pool.shutdown();

        while(true) {
            try {
                if (!!pool.awaitTermination(2, TimeUnit.MINUTES)) break;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Waiting for completion");
        }

        System.out.println("Finished parsing");
        if(showStatistics) {
            System.out.println(ImportStatistics.getInstance().getStatistics());
        }
    }

    public static ConnectionParameters getConnectionParameters() {
        return params;
    }
}
