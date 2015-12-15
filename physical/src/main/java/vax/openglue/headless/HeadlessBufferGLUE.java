package vax.openglue.headless;

import java.nio.*;
import vax.openglue.BufferGLUE;

/**

 @author toor
 */
public class HeadlessBufferGLUE implements BufferGLUE {
    private ByteBuffer allocateDirect ( int size ) {
        ByteBuffer bb = ByteBuffer.allocateDirect( size );
        bb.order( ByteOrder.nativeOrder() );
        return bb;
    }

    @Override
    public ByteBuffer createByteBuffer ( int size ) {
        //return ByteBuffer.allocate( size );
        return allocateDirect( size );
    }

    @Override
    public ByteBuffer createByteBuffer ( byte... data ) {
        //return ByteBuffer.wrap( data );
        ByteBuffer bb = allocateDirect( data.length * Float.BYTES );
        bb.put( data );
        return bb;
    }
}
