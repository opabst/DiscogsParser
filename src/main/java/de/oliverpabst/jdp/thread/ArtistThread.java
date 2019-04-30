package de.oliverpabst.jdp.thread;

import de.oliverpabst.jdp.parser.ArtistParser;

import java.io.File;

public class ArtistThread implements Runnable {
    private File file;

    public ArtistThread(File _file) {
        file = _file;
    }
    @Override
    public void run() {
        new ArtistParser(file);
    }
}
