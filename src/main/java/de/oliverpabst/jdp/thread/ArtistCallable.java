package de.oliverpabst.jdp.thread;

import de.oliverpabst.jdp.parser.ArtistParser;

import java.io.File;
import java.util.concurrent.Callable;

public class ArtistThread implements Callable<String> {
    private File file;

    public ArtistThread(File _file) {
        file = _file;
    }
    @Override
    public String call() {
        new ArtistParser(file);

        return "Parsing artists completed";
    }
}
