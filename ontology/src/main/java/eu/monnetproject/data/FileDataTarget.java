package eu.monnetproject.data;

import java.io.*;
import java.net.URL;

/**
 * A simple implementation that wraps a file as a target
 *
 * @author John McCrae
 */
public final class FileDataTarget implements DataTarget {

    private final File file;

    public FileDataTarget(String fileName) {
        this.file = new File(fileName);
    }

    public FileDataTarget(File file) {
        this.file = file;
    }

    public File asFile() {
        return file;
    }

    public OutputStream asOutputStream() {
        try {
            return new FileOutputStream(file);
        } catch (FileNotFoundException x) {
            throw new RuntimeException(x);
        }
    }

    public URL asURL() {
        try {
            return file.toURI().toURL();
        } catch (Exception x) {
            throw new RuntimeException(x);
        }
    }

    public Writer asWriter() throws UnsupportedOperationException {
        try {
            return new FileWriter(file);
        } catch (IOException x) {
            throw new RuntimeException(x);
        }
    }
}
