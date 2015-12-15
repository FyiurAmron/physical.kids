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
    public ByteBuffer createByteBuffer ( int size ) {
        return Buffers.newDirectByteBuffer( size );
    }

    @Override
    public ByteBuffer createByteBuffer ( byte... data ) {
        return Buffers.newDirectByteBuffer( data );
    }
}
