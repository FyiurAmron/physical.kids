package vax.openglue.lwjgl;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;
import vax.openglue.BufferGLUE;

/**

 @author toor
 */
public class LwjglBufferGLUE implements BufferGLUE {
    @Override
    public IntBuffer createIntBuffer ( int size ) {
        return BufferUtils.createIntBuffer( size );
    }

    @Override
    public IntBuffer createIntBuffer ( int[] data ) {
        IntBuffer buf = BufferUtils.createIntBuffer( data.length );
        buf.put( data );
        buf.flip();
        return buf;
    }

    @Override
    public FloatBuffer createFloatBuffer ( int size ) {
        return BufferUtils.createFloatBuffer( size );
    }

    @Override
    public FloatBuffer createFloatBuffer ( float[] data ) {
        FloatBuffer buf = BufferUtils.createFloatBuffer( data.length );
        buf.put( data );
        buf.flip();
        return buf;
    }

    @Override
    public ByteBuffer createByteBuffer ( int size ) {
        return BufferUtils.createByteBuffer( size );
    }

    @Override
    public ByteBuffer createByteBuffer ( byte[] data ) {
        ByteBuffer buf = BufferUtils.createByteBuffer( data.length );
        buf.put( data );
        buf.flip();
        return buf;
    }

}
