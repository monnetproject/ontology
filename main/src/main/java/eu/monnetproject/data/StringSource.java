package eu.monnetproject.data;

import java.io.*;
import java.net.*;

/**
 * Implements a data source that reads from a given string
 * @author John McCrae
 */
public final class StringSource implements DataSource {

    private final String source;
    private final String mimeType;

    /** 
     * Create this source
     * @param source The source string
     */
    public StringSource(String source) {
        this.source = source;
        this.mimeType = "application/octet-stream";
    }

    /** 
     * Create this source
     * @param source The source string
     * @param mimeType The MIME type of the string
     */
    public StringSource(String source, String mimeType) {
        this.source = source;
        this.mimeType = mimeType;
    }

    public URL asURL() {
        throw new UnsupportedOperationException();
    }

    public InputStream asInputStream() {
        try {
            return new ByteArrayInputStream(source.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException x) {
            throw new RuntimeException(x);
        }
    }

    public Reader asReader() throws UnsupportedOperationException {
        return new StringReader(source);
    }

    public File asFile() {
        throw new UnsupportedOperationException();
    }

    public String getMIMEType() {
        return mimeType;
    }
}
