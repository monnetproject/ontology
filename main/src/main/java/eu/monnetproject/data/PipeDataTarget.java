/****************************************************************************
/* Copyright (c) 2011, Monnet Project
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the Monnet Project nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE MONNET PROJECT BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 ********************************************************************************/
package eu.monnetproject.data;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.URL;

/**
 * A data target that pipes to a source.
 * 
 * @author John McCrae
 */
public class PipeDataTarget implements DataTarget {
    private final PipedOutputStream pos;
    private final PipedInputStream pis;
    private final String mimeType;

    public PipeDataTarget(String mimeType) throws IOException {
        pos = new PipedOutputStream();
        pis = new PipedInputStream(pos);
        this.mimeType = mimeType;
    }
    
    public URL asURL() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Not a URL");
    }

    public OutputStream asOutputStream() throws UnsupportedOperationException {
        return pos;
    }

    public File asFile() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Not a file.");
    }

    public Writer asWriter() throws UnsupportedOperationException {
        return new OutputStreamWriter(asOutputStream());
    }
    
    
    
    /**
     * Get the data source being piped to
     */
    public DataSource getSource() {
        return new DataSource() {

            public URL asURL() throws UnsupportedOperationException {
                throw new UnsupportedOperationException("Not a URL.");
            }

            public InputStream asInputStream() throws UnsupportedOperationException {
                return pis;
            }

            public File asFile() throws UnsupportedOperationException {
                throw new UnsupportedOperationException("Not a file.");
            }

            public String getMIMEType() {
                return mimeType;
            }

            public Reader asReader() throws UnsupportedOperationException {
                return new InputStreamReader(asInputStream());
            }
          
            
        };
    }
}
