/*
 PNGWriter.java

 Copyright (c) 2007 Matthias Mann - www.matthiasmann.de

 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in
 all copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 THE SOFTWARE.

 */
package de.matthiasmann.twl.utils;

import java.nio.ByteBuffer;
import java.io.*;
import java.util.zip.*;

import vax.gfx.ColorType;
import vax.openglue.BufferImage;

/**
 Simple PNG writer with the ability to save buffered pixmap data.

 @author Matthias Mann
 @author vaxquis
 */
public class PNGEncoder {
    private static final byte[] SIGNATURE = { (byte) 137, 80, 78, 71, 13, 10, 26, 10 };
    private static final int //
            IHDR = 0x49484452,
            IDAT = 0x49444154,
            IEND = 0x49454E44;
    private static final byte //
            COMPRESSION_DEFLATE = 0, // only one that's currently supported
            ADAPTIVE_FILTER = 0, // only one that's currently supported
            INTERLACE_NONE = 0,
            INTERLACE_ADAM7 = 1;
    private static final byte PAETH = 4;

    public static void write ( BufferImage bi, OutputStream os ) throws IOException {
        write( bi.width, bi.height, bi.componentCount, bi.bitDepth, bi.buffer.asReadOnlyBuffer(), os );
    }

    public static void write ( int height, int width, int componentCount, int bitDepth, ByteBuffer byteBuffer, OutputStream os )
            throws IOException {
        DataOutputStream dos = new DataOutputStream( os );
        dos.write( SIGNATURE );

        Chunk cIHDR = new Chunk( IHDR );
        cIHDR.writeInt( width );
        cIHDR.writeInt( height );
        cIHDR.writeByte( bitDepth );
        cIHDR.writeByte( ColorType.fromComponentCount( componentCount ).getPngConstant() );
        cIHDR.writeByte( COMPRESSION_DEFLATE );
        cIHDR.writeByte( ADAPTIVE_FILTER );
        cIHDR.writeByte( INTERLACE_NONE );
        cIHDR.writeTo( dos );

        Chunk cIDAT = new Chunk( IDAT );
        DeflaterOutputStream dfos = new DeflaterOutputStream( cIDAT, new Deflater( Deflater.BEST_COMPRESSION ) );

        int lineLen = width * componentCount;
        byte[] //
                lineOut = new byte[lineLen],
                curLine = new byte[lineLen],
                prevLine = new byte[lineLen];

        int pos = ( height - 1 ) * lineLen;
        for( int line = 0; line < height; line++, pos -= lineLen ) {
            byteBuffer.position( pos );
            byteBuffer.get( curLine );

            lineOut[0] = (byte) ( curLine[0] - prevLine[0] );
            lineOut[1] = (byte) ( curLine[1] - prevLine[1] );
            lineOut[2] = (byte) ( curLine[2] - prevLine[2] );

            for( int x = 3; x < lineLen; x++ ) {
                int a = curLine[x - 3] & 255;
                int b = prevLine[x] & 255;
                int c = prevLine[x - 3] & 255;
                int p = a + b - c;
                int pa = p - a;
                if ( pa < 0 ) {
                    pa = -pa;
                }
                int pb = p - b;
                if ( pb < 0 ) {
                    pb = -pb;
                }
                int pc = p - c;
                if ( pc < 0 ) {
                    pc = -pc;
                }
                if ( pa <= pb && pa <= pc ) {
                    c = a;
                } else if ( pb <= pc ) {
                    c = b;
                }
                lineOut[x] = (byte) ( curLine[x] - c );
            }

            dfos.write( PAETH );
            dfos.write( lineOut );

            byte[] temp = curLine; // swap the line buffers
            curLine = prevLine;
            prevLine = temp;
        }

        dfos.finish();
        cIDAT.writeTo( dos );

        Chunk cIEND = new Chunk( IEND );
        cIEND.writeTo( dos );

        dos.flush();
    }

    /**
     @param file it will be overwritten
     @param bi
     @throws java.io.IOException
     */
    public static void write ( BufferImage bi, File file ) throws IOException {
        try (FileOutputStream fos = new FileOutputStream( file )) {
            write( bi, fos );
        }
    }

    /**
     Creates a job for use with {@link java.util.concurrent.ExecutorService}

     @param file it will be overwritten
     @param bi
     @return
     */
    public Runnable createJob ( BufferImage bi, File file ) {
        return () -> {
            try {
                write( bi, file );
            } catch (IOException ex) {
                throw new RuntimeException( ex );
            }
        };
    }

    private static class Chunk extends DataOutputStream {
        final CRC32 crc;
        final ByteArrayOutputStream baos;

        private Chunk ( int chunkType ) throws IOException {
            this( chunkType, new ByteArrayOutputStream(), new CRC32() );
        }

        private Chunk ( int chunkType, ByteArrayOutputStream baos,
                CRC32 crc ) throws IOException {
            super( new CheckedOutputStream( baos, crc ) );
            this.crc = crc;
            this.baos = baos;

            writeInt( chunkType );
        }

        public void writeTo ( DataOutputStream out ) throws IOException {
            flush();
            out.writeInt( baos.size() - 4 );
            baos.writeTo( out );
            out.writeInt( (int) crc.getValue() );
        }
    }
}
