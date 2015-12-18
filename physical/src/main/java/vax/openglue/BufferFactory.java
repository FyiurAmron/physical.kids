package vax.openglue;

import java.nio.*;

/**

 @author toor
 */
public abstract class BufferFactory {
    public abstract ByteBuffer createByteBuffer ( int elementCount );

    public ByteBuffer createByteBuffer ( byte... data ) {
        ByteBuffer bb = createByteBuffer( data.length );
        bb.put( data );
        bb.rewind();
        return bb;
    }

    public CharBuffer createCharBuffer ( int elementCount ) {
        return createByteBuffer( elementCount * Character.BYTES ).asCharBuffer();
    }

    public CharBuffer createCharBuffer ( char... data ) {
        CharBuffer buf = createCharBuffer( data.length );
        buf.put( data );
        buf.rewind();
        return buf;
    }

    public ShortBuffer createShortBuffer ( int elementCount ) {
        return createByteBuffer( elementCount * Short.BYTES ).asShortBuffer();
    }

    public ShortBuffer createShortBuffer ( short... data ) {
        ShortBuffer buf = createShortBuffer( data.length );
        buf.put( data );
        buf.rewind();
        return buf;
    }

    public IntBuffer createIntBuffer ( int elementCount ) {
        return createByteBuffer( elementCount * Integer.BYTES ).asIntBuffer();
    }

    public IntBuffer createIntBuffer ( int... data ) {
        IntBuffer buf = createIntBuffer( data.length );
        buf.put( data );
        buf.rewind();
        return buf;
    }

    public LongBuffer createLongBuffer ( int elementCount ) {
        return createByteBuffer( elementCount * Long.BYTES ).asLongBuffer();
    }

    public LongBuffer createLongBuffer ( long... data ) {
        LongBuffer buf = createLongBuffer( data.length );
        buf.put( data );
        buf.rewind();
        return buf;
    }

    public FloatBuffer createFloatBuffer ( int elementCount ) {
        return createByteBuffer( elementCount * Float.BYTES ).asFloatBuffer();
    }

    public FloatBuffer createFloatBuffer ( float... data ) {
        FloatBuffer buf = createFloatBuffer( data.length );
        buf.put( data );
        buf.rewind();
        return buf;
    }

    public DoubleBuffer createDoubleBuffer ( int elementCount ) {
        return createByteBuffer( elementCount * Double.BYTES ).asDoubleBuffer();
    }

    public DoubleBuffer createDoubleBuffer ( double... data ) {
        DoubleBuffer buf = createDoubleBuffer( data.length );
        buf.put( data );
        buf.rewind();
        return buf;
    }

    public static class NIO extends BufferFactory {
        @Override
        public ByteBuffer createByteBuffer ( int elementCount ) {
            ByteBuffer bb = ByteBuffer.allocateDirect( elementCount );
            bb.order( ByteOrder.nativeOrder() );
            return bb;
        }
    }
}
