package vax.openglue.jogl;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import com.jogamp.common.nio.Buffers;
import vax.openglue.BufferGLUE;

/**

 @author toor
 */
public class JoglBufferGLUE implements BufferGLUE {
    @Override
    public IntBuffer createIntBuffer ( int size ) {
        return Buffers.newDirectIntBuffer( size );
    }

    @Override
    public IntBuffer createIntBuffer ( int[] data ) {
        return Buffers.newDirectIntBuffer( data );
    }

    @Override
    public FloatBuffer createFloatBuffer ( int size ) {
        return Buffers.newDirectFloatBuffer( size );
    }

    @Override
    public FloatBuffer createFloatBuffer ( float[] data ) {
        return Buffers.newDirectFloatBuffer( data );
    }

    @Override
    public ByteBuffer createByteBuffer ( int size ) {
        return Buffers.newDirectByteBuffer( size );
    }

    @Override
    public ByteBuffer createByteBuffer ( byte[] data ) {
        return Buffers.newDirectByteBuffer( data );
    }
}
