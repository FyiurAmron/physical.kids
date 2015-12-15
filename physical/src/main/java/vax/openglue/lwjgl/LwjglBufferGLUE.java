package vax.openglue.lwjgl;

import java.nio.ByteBuffer;
import org.lwjgl.BufferUtils;
import vax.openglue.BufferGLUE;

/**

 @author toor
 */
public class LwjglBufferGLUE implements BufferGLUE {
    @Override
    public ByteBuffer createByteBuffer ( int size ) {
        return BufferUtils.createByteBuffer( size );
    }

    @Override
    public ByteBuffer createByteBuffer ( byte... data ) {
        ByteBuffer buf = BufferUtils.createByteBuffer( data.length );
        buf.put( data );
        buf.flip();
        return buf;
    }

}
