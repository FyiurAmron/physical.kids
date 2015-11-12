package vax.openglue;

import java.nio.*;

/**

 @author toor
 */
public class BufferUtils {
    private static BufferGLUE bufferGlUe = BufferGLUE.GLUE_NOT_SET;

    public static void setBufferGlUe ( BufferGLUE bufferGlUe ) {
        BufferUtils.bufferGlUe = bufferGlUe;
    }

    public static BufferGLUE getBufferGlUe () {
        return bufferGlUe;
    }

    public static IntBuffer createIntBuffer ( int size ) {
        return bufferGlUe.createIntBuffer( size );
    }

    public static IntBuffer createIntBuffer ( int... data ) {
        return bufferGlUe.createIntBuffer( data );
    }

    public static FloatBuffer createFloatBuffer ( int size ) {
        return bufferGlUe.createFloatBuffer( size );
    }

    public static FloatBuffer createFloatBuffer ( float... data ) {
        return bufferGlUe.createFloatBuffer( data );
    }

    private BufferUtils () {
        throw new UnsupportedOperationException();
    }
}
