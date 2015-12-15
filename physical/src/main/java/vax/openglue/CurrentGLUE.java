package vax.openglue;

import java.nio.*;

/**

 @author toor
 */
public class CurrentGLUE {
    private static OpenGLUE currentGLUE;

    public static void setCurrentGLUE ( OpenGLUE currentGLUE ) {
        CurrentGLUE.currentGLUE = currentGLUE;
    }

    public static OpenGLUE getCurrentGLUE () {
        return currentGLUE;
    }

    private CurrentGLUE () {
        throw new UnsupportedOperationException();
    }

    public static BufferGLUE getBufferGLUE () {
        return currentGLUE.getBufferGLUE();
    }

    public static IntBuffer createIntBuffer ( int size ) {
        return getBufferGLUE().createIntBuffer( size );
    }

    public static IntBuffer createIntBuffer ( int... data ) {
        return getBufferGLUE().createIntBuffer( data );
    }

    public static FloatBuffer createFloatBuffer ( int size ) {
        return getBufferGLUE().createFloatBuffer( size );
    }

    public static FloatBuffer createFloatBuffer ( float... data ) {
        return getBufferGLUE().createFloatBuffer( data );
    }

    public static ByteBuffer createByteBuffer ( int size ) {
        return getBufferGLUE().createByteBuffer( size );
    }

    public static ByteBuffer createByteBuffer ( byte... data ) {
        return getBufferGLUE().createByteBuffer( data );
    }

}
