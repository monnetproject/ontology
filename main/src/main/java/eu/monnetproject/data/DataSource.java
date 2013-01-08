package eu.monnetproject.data;

import java.io.*;
import java.net.*;

/**
 * A source of data. This is an abstraction over files, URLs, input streams etc.
 *
 * @author John McCrae
 */
public interface DataSource {
	/** Get the source as a URL
	 * @throws UnsupportedOperationException If the source cannot be transformed into a URL
	 */
	public URL asURL() throws UnsupportedOperationException;
	/** Get the source as an input stream
	 * @throws UnsupportedOperationException If the source cannot be transformed into a input stream
	 */
	public InputStream asInputStream() throws UnsupportedOperationException;
        /** Get the source as an input stream
	 * @throws UnsupportedOperationException If the source cannot be transformed into a input stream
	 */
	public Reader asReader() throws UnsupportedOperationException;
	/** Get the source as a file
	 * @throws UnsupportedOperationException If the source cannot be transformed into a file
	 */	
	public File asFile() throws UnsupportedOperationException;
	/** Get the MIME type of the source
	 */
	public String getMIMEType();
}
	
