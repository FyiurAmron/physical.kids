package vax.openglue;

import java.nio.*;
import vax.math.MathUtils;

/**

 @author toor
 */
public class BufferUtils {
    private static BufferFactory bufferFactory;

    public static BufferFactory getBufferFactory () {
        if ( bufferFactory == null ) {
            bufferFactory = new BufferFactory.NIO();
        }
        return bufferFactory;
    }

    public static void setBufferFactory ( BufferFactory bufferFactory ) {
        BufferUtils.bufferFactory = bufferFactory;
    }

    public static ByteBuffer createByteBuffer ( int size ) {
        return getBufferFactory().createByteBuffer( size );
    }

    public static ByteBuffer createByteBuffer ( byte... data ) {
        return getBufferFactory().createByteBuffer( data );
    }

    public static CharBuffer createCharBuffer ( int elementCount ) {
        return getBufferFactory().createCharBuffer( elementCount );
    }

    public static CharBuffer createCharBuffer ( char... data ) {
        return getBufferFactory().createCharBuffer( data );
    }

    public static ShortBuffer createShortBuffer ( int elementCount ) {
        return getBufferFactory().createShortBuffer( elementCount );
    }

    public static ShortBuffer createShortBuffer ( short... data ) {
        return getBufferFactory().createShortBuffer( data );
    }

    public static IntBuffer createIntBuffer ( int elementCount ) {
        return getBufferFactory().createIntBuffer( elementCount );
    }

    public static IntBuffer createIntBuffer ( int... data ) {
        return getBufferFactory().createIntBuffer( data );
    }

    public static LongBuffer createLongBuffer ( int elementCount ) {
        return getBufferFactory().createLongBuffer( elementCount );
    }

    public static LongBuffer createLongBuffer ( long... data ) {
        return getBufferFactory().createLongBuffer( data );
    }

    public static FloatBuffer createFloatBuffer ( int elementCount ) {
        return getBufferFactory().createFloatBuffer( elementCount );
    }

    public static FloatBuffer createFloatBuffer ( float... data ) {
        return getBufferFactory().createFloatBuffer( data );
    }

    public static DoubleBuffer createDoubleBuffer ( int elementCount ) {
        return getBufferFactory().createDoubleBuffer( elementCount );
    }

    public static DoubleBuffer createDoubleBuffer ( double... data ) {
        return getBufferFactory().createDoubleBuffer( data );
    }

    public ByteBuffer fillRandom ( ByteBuffer buffer ) {
        byte[] bytes = new byte[buffer.capacity()];
        buffer.rewind();
        buffer.put( MathUtils.nextBytes( bytes ) );
        buffer.rewind();
        return buffer;
    }

    public static String toString ( ByteBuffer byteBuffer ) {
        byteBuffer.rewind();
        StringBuilder sb = new StringBuilder( byteBuffer.capacity() );
        while( byteBuffer.hasRemaining() ) {
            sb.append( (char) byteBuffer.get() );
        }
        byteBuffer.rewind();
        return sb.toString();
    }

    private BufferUtils () {
        throw new UnsupportedOperationException();
    }
}
