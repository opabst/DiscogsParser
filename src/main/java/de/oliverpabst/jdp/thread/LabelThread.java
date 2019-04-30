package de.oliverpabst.jdp.thread;

import de.oliverpabst.jdp.parser.LabelParser;

import java.io.File;

public class LabelThread implements Runnable {
    private File file;

    public LabelThread(File _file) {
        file = _file;
    }

    @Override
    public void run() {
        new LabelParser(file);
    }
}
