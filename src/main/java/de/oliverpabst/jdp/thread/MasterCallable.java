package de.oliverpabst.jdp.thread;

import de.oliverpabst.jdp.parser.MasterParser;

import java.io.File;
import java.util.concurrent.Callable;

public class MasterCallable implements Callable<String> {
    private File file;

    public MasterCallable(File _file) {
        file = _file;
    }

    @Override
    public String call() {
        new MasterParser(file);

        return "Parsing masters completed";
    }
}
