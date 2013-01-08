package eu.monnetproject.data;

import java.io.*;
import java.net.*;

/**
 * A data target that writes to a string buffer
 * @author John McCrae
 */
public final class StringBufferTarget implements DataTarget {
	private StringBufferOutputStream sbos;
        private StringWriter sw = null;
	
	public File asFile() { throw new UnsupportedOperationException(); }
	public OutputStream asOutputStream() { 
            if(sw == null) {
                return sbos == null ? sbos = new StringBufferOutputStream() : sbos; 
            } else {
                throw new IllegalArgumentException("Cannot open string target as both output stream and writer");
            }
        }
	public URL asURL() { throw new UnsupportedOperationException(); }
        public Writer asWriter() throws UnsupportedOperationException {
            if(sbos == null) {
                return sw == null ? sw = new StringWriter() : sw;
            } else {
                throw new IllegalArgumentException("Cannot open string target as both output stream and writer");
            }
        }
	
        
	@Override
	public String toString() {
            if(sbos != null) {
		return sbos.textBuffer.toString();
            } else if(sw != null) {
                return sw.toString();
            } else {
                return "";
            }
	}

	private static class StringBufferOutputStream extends OutputStream {
		StringBuffer textBuffer = new StringBuffer();
    
		public StringBufferOutputStream() {
			super();
		}
		
		public void write(int b) throws IOException  {
			char a = (char)b;
			textBuffer.append(a);
		}
  
		public void clear() {
			textBuffer.delete(0, textBuffer.length());
		}
	}
}
