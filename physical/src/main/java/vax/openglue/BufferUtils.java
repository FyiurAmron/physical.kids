package vax.openglue;

import java.nio.*;

/**

 @author toor
 */
public class BufferUtils {
    private static BufferGlUe bufferGlUe = BufferGlUe.GLUE_NOT_SET;

    public static void setBufferGlUe ( BufferGlUe bufferGlUe ) {
        BufferUtils.bufferGlUe = bufferGlUe;
    }

    public static BufferGlUe getBufferGlUe () {
        return bufferGlUe;
    }

    public static IntBuffer createIntBuffer ( int size ) {
        return bufferGlUe.ueCreateIntBuffer( size );
    }

    public static IntBuffer createIntBuffer ( int... data ) {
        return bufferGlUe.ueCreateIntBuffer( data );
    }

    public static FloatBuffer createFloatBuffer ( int size ) {
        return bufferGlUe.ueCreateFloatBuffer( size );
    }

    public static FloatBuffer createFloatBuffer ( float... data ) {
        return bufferGlUe.ueCreateFloatBuffer( data );
    }

    private BufferUtils () {
        throw new UnsupportedOperationException();
    }
}
