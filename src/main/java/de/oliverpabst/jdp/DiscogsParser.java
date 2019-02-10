package de.oliverpabst.jdp;

import org.apache.commons.cli.*;

public class DiscogsParser {
    public static void main(String[] args) {
        Options options = new Options();

        Option dumpDate = new Option("d", "date", true, "Date of the creation of the dump (part of the filename)");
        dumpDate.setRequired(true);
        options.addOption(dumpDate);

        CommandLineParser clParser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();

        try {
            CommandLine cmdLine = clParser.parse(options, args);
        } catch (ParseException e) {
            System.out.println("Arguments could not be parsed.");
            formatter.printHelp("DiscogsParser", options);
        }

        
    }
}
