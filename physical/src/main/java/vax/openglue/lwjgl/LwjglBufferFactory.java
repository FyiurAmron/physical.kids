package vax.openglue.lwjgl;

import java.nio.ByteBuffer;
import org.lwjgl.BufferUtils;
import vax.openglue.BufferFactory;

/**

 @author toor
 */
public class LwjglBufferFactory extends BufferFactory {
    @Override
    public ByteBuffer createByteBuffer ( int elementCount ) {
        return BufferUtils.createByteBuffer( elementCount );
    }
}
