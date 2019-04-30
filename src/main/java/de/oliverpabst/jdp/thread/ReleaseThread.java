package de.oliverpabst.jdp.thread;

import java.io.File;

public class ReleaseThread implements Runnable {
    private File file;

    public ReleaseThread(File _file) {
        file = _file;
    }

    @Override
    public void run() {
        new ReleaseThread(file);
    }
}
