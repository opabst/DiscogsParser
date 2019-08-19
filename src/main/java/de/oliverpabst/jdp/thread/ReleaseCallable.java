package de.oliverpabst.jdp.thread;

import de.oliverpabst.jdp.parser.ReleaseParser;

import java.io.File;
import java.util.concurrent.Callable;

public class ReleaseCallable implements Callable<String> {
    private File file;

    public ReleaseCallable(File _file) {
        file = _file;
    }

    @Override
    public String call() {
        new ReleaseParser(file);

        return "Parsing releases completed";
    }
}
