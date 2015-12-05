package vax.openglue;

import java.nio.*;

/**

 @author toor
 */
public class BufferUtils {
    private static BufferGLUE bufferGlue = BufferGLUE.GLUE_NOT_SET;

    public static void setBufferGLUE ( BufferGLUE bufferGlue ) {
        BufferUtils.bufferGlue = bufferGlue;
    }

    public static BufferGLUE getBufferGLUE () {
        return bufferGlue;
    }

    public static ByteBuffer createByteBuffer ( int size ) {
        return bufferGlue.createByteBuffer( size );
    }

    public static ByteBuffer createByteBuffer ( byte... data ) {
        return bufferGlue.createByteBuffer( data );
    }

    public static IntBuffer createIntBuffer ( int size ) {
        return bufferGlue.createIntBuffer( size );
    }

    public static IntBuffer createIntBuffer ( int... data ) {
        return bufferGlue.createIntBuffer( data );
    }

    public static FloatBuffer createFloatBuffer ( int size ) {
        return bufferGlue.createFloatBuffer( size );
    }

    public static FloatBuffer createFloatBuffer ( float... data ) {
        return bufferGlue.createFloatBuffer( data );
    }

    public static String toString ( ByteBuffer byteBuffer ) {
        byteBuffer.rewind();
        StringBuilder sb = new StringBuilder( byteBuffer.capacity() );
        while( byteBuffer.hasRemaining() ) {
            sb.append( (char) byteBuffer.get() );
        }
        return sb.toString();
    }

    private BufferUtils () {
        throw new UnsupportedOperationException();
    }
}
