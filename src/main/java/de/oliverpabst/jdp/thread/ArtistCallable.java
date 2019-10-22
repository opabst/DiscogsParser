package de.oliverpabst.jdp.thread;

import de.oliverpabst.jdp.DiscogsParser;
import de.oliverpabst.jdp.parser.jdp_schema.ArtistParser;

import java.io.File;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class ArtistCallable implements Callable<String> {
    private final File file;
    private final Boolean useXml2DbSchema;

    public ArtistCallable(File _file, Boolean _xml2db) {
        file = _file;
        useXml2DbSchema = _xml2db;
    }
    @Override
    public String call() {
        Long startTime = System.nanoTime();

        if(useXml2DbSchema) {
            new de.oliverpabst.jdp.parser.db2xml.ArtistParser(file);
        } else {
            new de.oliverpabst.jdp.parser.jdp_schema.ArtistParser(file);
        }

        Long endTime = System.nanoTime();
        Long duration = TimeUnit.SECONDS.convert(endTime - startTime, TimeUnit.NANOSECONDS);

        return "Parsing artists completed -|- duration: " + duration + "sec";
    }
}
