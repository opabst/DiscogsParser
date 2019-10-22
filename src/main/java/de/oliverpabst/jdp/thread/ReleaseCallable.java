package de.oliverpabst.jdp.thread;

import de.oliverpabst.jdp.parser.jdp_schema.ReleaseParser;

import java.io.File;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class ReleaseCallable implements Callable<String> {
    private final File file;
    private final Boolean useXml2DbSchema;

    public ReleaseCallable(File _file, Boolean _xml2db) {
        file = _file;
        useXml2DbSchema = _xml2db;
    }

    @Override
    public String call() {
        Long startTime = System.nanoTime();

        if(useXml2DbSchema) {
            new de.oliverpabst.jdp.parser.db2xml.ReleaseParser(file);
        } else {
            new de.oliverpabst.jdp.parser.jdp_schema.ReleaseParser(file);
        }

        Long endTime = System.nanoTime();
        Long duration = TimeUnit.SECONDS.convert(endTime - startTime, TimeUnit.NANOSECONDS);

        return "Parsing releases completed -|- duration: " + duration + "sec";
    }
}
