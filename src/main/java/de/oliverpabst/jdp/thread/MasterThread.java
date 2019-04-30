package de.oliverpabst.jdp.thread;

import de.oliverpabst.jdp.parser.MasterParser;

import java.io.File;

public class MasterThread implements Runnable {
    private File file;

    public MasterThread(File _file) {
        file = _file;
    }

    @Override
    public void run() {
        new MasterParser(file);
    }
}
