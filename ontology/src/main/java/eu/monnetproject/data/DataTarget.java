package eu.monnetproject.data;

import java.io.*;
import java.net.*;

/**
 * A target for data. This is an abstraction over files, URLs, input streams etc.
 * @author John McCrae
 */
public interface DataTarget {
	/** Get the target as a URL
	 * @throws UnsupportedOperationException If the target cannot be transformed into a URL
	 */
	public URL asURL() throws UnsupportedOperationException;
	/** Get the target as an output stream
	 * @throws UnsupportedOperationException If the target cannot be transformed into an output stream
	 */
	public OutputStream asOutputStream() throws UnsupportedOperationException;
        
	/** Get the target as an output stream
	 * @throws UnsupportedOperationException If the target cannot be transformed into an output stream
	 */
	public Writer asWriter() throws UnsupportedOperationException;
	/** Get the target as a file
	 * @throws UnsupportedOperationException If the target cannot be transformed into a file
	 */	
	public File asFile() throws UnsupportedOperationException;
}
	
