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

    private BufferUtils () {
        throw new UnsupportedOperationException();
    }
}
