package de.oliverpabst.jdp.thread;

import java.io.File;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class LabelCallable implements Callable<String> {
    private final File file;
    private final Boolean useXml2DbSchema;

    public LabelCallable(File _file, Boolean _xml2db) {
        file = _file;
        useXml2DbSchema = _xml2db;
    }

    @Override
    public String call() {
        Long startTime = System.nanoTime();

        if(useXml2DbSchema) {
            new de.oliverpabst.jdp.parser.xml2db_schema.LabelParser(file);
        } else {
            new de.oliverpabst.jdp.parser.jdp_schema.LabelParser(file);
        }

        Long endTime = System.nanoTime();
        Long duration = TimeUnit.SECONDS.convert(endTime - startTime, TimeUnit.NANOSECONDS);

        return "Parsing labels completed -|- duration: " + duration + "sec";
    }
}
