package de.oliverpabst.jdp.thread;

import de.oliverpabst.jdp.parser.LabelParser;

import java.io.File;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class LabelCallable implements Callable<String> {
    private File file;

    public LabelCallable(File _file) {
        file = _file;
    }

    @Override
    public String call() {
        Long startTime = System.nanoTime();

        new LabelParser(file);

        Long endTime = System.nanoTime();
        Long duration = TimeUnit.SECONDS.convert(endTime - startTime, TimeUnit.NANOSECONDS);

        return "Parsing labels completed -|- duration: " + duration + "sec";
    }
}
