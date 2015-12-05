package vax.openglue.headless;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import vax.openglue.BufferGLUE;

/**

 @author toor
 */
public class HeadlessBufferGLUE implements BufferGLUE {
    @Override
    public IntBuffer createIntBuffer ( int size ) {
        return IntBuffer.allocate( size );
    }

    @Override
    public IntBuffer createIntBuffer ( int[] data ) {
        return IntBuffer.wrap( data );
    }

    @Override
    public FloatBuffer createFloatBuffer ( int size ) {
        return FloatBuffer.allocate( size );
    }

    @Override
    public FloatBuffer createFloatBuffer ( float[] data ) {
        return FloatBuffer.wrap( data );
    }

    @Override
    public ByteBuffer createByteBuffer ( int size ) {
        return ByteBuffer.allocate( size );
    }

    @Override
    public ByteBuffer createByteBuffer ( byte[] data ) {
        return ByteBuffer.wrap( data );
    }
}
