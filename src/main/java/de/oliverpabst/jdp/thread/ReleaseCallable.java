package de.oliverpabst.jdp.thread;

import de.oliverpabst.jdp.parser.ReleaseParser;

import java.io.File;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class ReleaseCallable implements Callable<String> {
    private File file;

    public ReleaseCallable(File _file) {
        file = _file;
    }

    @Override
    public String call() {
        Long startTime = System.nanoTime();

        new ReleaseParser(file);

        Long endTime = System.nanoTime();
        Long duration = TimeUnit.SECONDS.convert(endTime - startTime, TimeUnit.NANOSECONDS);

        return "Parsing releases completed -|- duration: " + duration + "sec";
    }
}
