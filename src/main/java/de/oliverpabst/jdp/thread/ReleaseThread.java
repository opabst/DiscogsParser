package de.oliverpabst.jdp.thread;

import de.oliverpabst.jdp.parser.ReleaseParser;

import java.io.File;

public class ReleaseThread implements Runnable {
    private File file;

    public ReleaseThread(File _file) {
        file = _file;
    }

    @Override
    public void run() {
        new ReleaseParser(file);
    }
}
