package de.oliverpabst.jdp;

import de.oliverpabst.jdp.database.ConnectionParameters;
import de.oliverpabst.jdp.database.SchemaDoesNotExistException;
import de.oliverpabst.jdp.database.SchemaType;
import de.oliverpabst.jdp.database.WrongSchemaTypeException;
import de.oliverpabst.jdp.database.postgresql.PostgreSQLConnection;
import de.oliverpabst.jdp.thread.ArtistCallable;
import de.oliverpabst.jdp.thread.LabelCallable;
import de.oliverpabst.jdp.thread.MasterCallable;
import de.oliverpabst.jdp.thread.ReleaseCallable;
import org.apache.commons.cli.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class DiscogsParser {
    private static ConnectionParameters params;

    private static Boolean showStatistics = true;

    private static Boolean xml2dbSchema = false;

    private static ExecutorService pool;

    public static void main(String[] args) {
        Options options = new Options();

        Option dumpDate = new Option("d", "date", true, "Date of the creation of the dump (part of the filename)");
        Option hostName = new Option("h", "hostname", true, "Name of the databaseserver");
        Option dbPort = new Option("p", "password", true, "Port of the running database instance");
        Option dbName = new Option("db", "database", true, "Name of the database");
        Option dbUser = new Option("u", "username", true, "Database username");
        Option dbPassword = new Option("pw", "password", true, "Corresponding password for database username");
        Option db2xmlSchema = new Option("s", "db2xml", false, "If set, use old discogs-db2xml schema");
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
        Boolean useDb2xmlSchema = cmdLine.hasOption("s");

        params = new ConnectionParameters(hostname, port, db, username, password);

        try {
            PostgreSQLConnection con = new PostgreSQLConnection(DiscogsParser.getConnectionParameters());
            SchemaType st = con.getSchemaType();

            if(useDb2xmlSchema && st.equals(SchemaType.JDP_SCHEMA)) {
                throw new WrongSchemaTypeException("Parser set for xml2db-schema, but database schema is jdp!");
            } else if (!useDb2xmlSchema && st.equals(SchemaType.XML2DB_SCHEMA)) {
                throw new WrongSchemaTypeException("Parser set for jdp-schema, but database schema is xml2db!");
            }
        } catch (SchemaDoesNotExistException | WrongSchemaTypeException e) {
            System.err.println(e.getMessage());
            System.exit(1); // Exit because db schema does not exist in the specified database or selected parser does not match the database schema
        }


        int poolsize = Runtime.getRuntime().availableProcessors();
        pool = Executors.newFixedThreadPool(poolsize);

        Thread.setDefaultUncaughtExceptionHandler((thread, throwable) -> System.out.println("Caught " + throwable));

        ArrayList<Callable<String>> threads = new ArrayList<>();
        threads.add(new ArtistCallable(artistsFile, useDb2xmlSchema));
        threads.add(new LabelCallable(labelsFile, useDb2xmlSchema));
        threads.add(new MasterCallable(mastersFile, useDb2xmlSchema));
        threads.add(new ReleaseCallable(releasesFile, useDb2xmlSchema));

        List<Future<String>> results = null;
        try {
            results = pool.invokeAll(threads);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for(Future<String> future: results) {
            try {
                System.out.println(future.get());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        pool.shutdown();

        System.out.println("Finished parsing");
        if (showStatistics) {
            System.out.println(ImportStatistics.getInstance().getStatistics());
        }
    }

    public static ConnectionParameters getConnectionParameters() {
        return params;
    }
}
