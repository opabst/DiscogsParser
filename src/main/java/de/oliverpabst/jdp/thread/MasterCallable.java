package de.oliverpabst.jdp.thread;

import de.oliverpabst.jdp.parser.MasterParser;

import java.io.File;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class MasterCallable implements Callable<String> {
    private File file;

    public MasterCallable(File _file) {
        file = _file;
    }

    @Override
    public String call() {
        Long startTime = System.nanoTime();

        new MasterParser(file);

        Long endTime = System.nanoTime();
        Long duration = TimeUnit.SECONDS.convert(endTime - startTime, TimeUnit.NANOSECONDS);

        return "Parsing masters completed -|- duration: " + duration + "sec";
    }
}
