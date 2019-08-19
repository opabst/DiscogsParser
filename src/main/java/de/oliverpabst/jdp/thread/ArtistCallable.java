package de.oliverpabst.jdp.thread;

import de.oliverpabst.jdp.parser.ArtistParser;

import java.io.File;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class ArtistCallable implements Callable<String> {
    private File file;

    public ArtistCallable(File _file) {
        file = _file;
    }
    @Override
    public String call() {
        Long startTime = System.nanoTime();

        new ArtistParser(file);

        Long endTime = System.nanoTime();
        Long duration = TimeUnit.SECONDS.convert(endTime - startTime, TimeUnit.NANOSECONDS);

        return "Parsing artists completed -|- duration: " + duration + "sec";
    }
}
