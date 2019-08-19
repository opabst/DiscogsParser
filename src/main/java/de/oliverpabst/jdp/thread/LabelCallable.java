package de.oliverpabst.jdp.thread;

import de.oliverpabst.jdp.parser.LabelParser;

import java.io.File;
import java.util.concurrent.Callable;

public class LabelThread implements Callable<String> {
    private File file;

    public LabelThread(File _file) {
        file = _file;
    }

    @Override
    public String call() {
        new LabelParser(file);

        return "Parsing labels completed";
    }
}
