package vax.openglue;

import java.nio.*;

/**

 @author toor
 */
public interface BufferGLUE {
    public static String toString ( ByteBuffer byteBuffer ) {
        byteBuffer.rewind();
        StringBuilder sb = new StringBuilder( byteBuffer.capacity() );
        while( byteBuffer.hasRemaining() ) {
            sb.append( (char) byteBuffer.get() );
        }
        byteBuffer.rewind();
        return sb.toString();
    }

    ByteBuffer createByteBuffer ( int size );

    ByteBuffer createByteBuffer ( byte... data );

    default CharBuffer createCharBuffer ( int elementCount ) {
        return createByteBuffer( elementCount * Character.BYTES ).asCharBuffer();
    }

    default CharBuffer createCharBuffer ( char... data ) {
        CharBuffer buf = createCharBuffer( data.length );
        buf.put( data );
        buf.rewind();
        return buf;
    }

    default ShortBuffer createShortBuffer ( int elementCount ) {
        return createByteBuffer( elementCount * Short.BYTES ).asShortBuffer();
    }

    default ShortBuffer createShortBuffer ( short... data ) {
        ShortBuffer buf = createShortBuffer( data.length );
        buf.put( data );
        buf.rewind();
        return buf;
    }

    default IntBuffer createIntBuffer ( int elementCount ) {
        return createByteBuffer( elementCount * Integer.BYTES ).asIntBuffer();
    }

    default IntBuffer createIntBuffer ( int... data ) {
        IntBuffer buf = createIntBuffer( data.length );
        buf.put( data );
        buf.rewind();
        return buf;
    }

    default LongBuffer createLongBuffer ( int elementCount ) {
        return createByteBuffer( elementCount * Long.BYTES ).asLongBuffer();
    }

    default LongBuffer createLongBuffer ( long... data ) {
        LongBuffer buf = createLongBuffer( data.length );
        buf.put( data );
        buf.rewind();
        return buf;
    }

    default FloatBuffer createFloatBuffer ( int elementCount ) {
        return createByteBuffer( elementCount * Float.BYTES ).asFloatBuffer();
    }

    default FloatBuffer createFloatBuffer ( float... data ) {
        FloatBuffer buf = createFloatBuffer( data.length );
        buf.put( data );
        buf.rewind();
        return buf;
    }

    default DoubleBuffer createDoubleBuffer ( int elementCount ) {
        return createByteBuffer( elementCount * Double.BYTES ).asDoubleBuffer();
    }

    default DoubleBuffer createDoubleBuffer ( double... data ) {
        DoubleBuffer buf = createDoubleBuffer( data.length );
        buf.put( data );
        buf.rewind();
        return buf;
    }

}
