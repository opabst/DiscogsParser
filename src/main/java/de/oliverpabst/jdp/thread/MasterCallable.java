package de.oliverpabst.jdp.thread;

import de.oliverpabst.jdp.parser.MasterParser;

import java.io.File;
import java.util.concurrent.Callable;

public class MasterThread implements Callable<String> {
    private File file;

    public MasterThread(File _file) {
        file = _file;
    }

    @Override
    public String call() {
        new MasterParser(file);

        return "Parsing masters completed";
    }
}
