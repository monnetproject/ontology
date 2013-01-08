package eu.monnetproject.data;

import java.io.*;
import java.net.URL;

/**
 * A simple implementation that wraps a file as a data source
 *
 * @author John McCrae
 */
public final class FileDataSource implements DataSource {

    private final File file;
    private final String mimeType;

    public FileDataSource(String fileName) {
        this.file = new File(fileName);
        this.mimeType = "application/octet-stream";
    }

    public FileDataSource(File file) {
        this.file = file;
        this.mimeType = "application/octet-stream";
    }

    public FileDataSource(String fileName, String mimeType) {
        this.file = new File(fileName);
        this.mimeType = mimeType;
    }

    public FileDataSource(File file, String mimeType) {
        this.file = file;
        this.mimeType = mimeType;
    }

    public File asFile() {
        return file;
    }

    public InputStream asInputStream() {
        try {
            return new FileInputStream(file);
        } catch (FileNotFoundException x) {
            throw new RuntimeException(x);
        }
    }

    public Reader asReader() throws UnsupportedOperationException {
        try {
            return new FileReader(file);
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

    public String getMIMEType() {
        return mimeType;
    }
}
