package vax.openglue.headless;

import java.nio.*;
import vax.openglue.BufferGLUE;

/**

 @author toor
 */
public class HeadlessBufferGLUE implements BufferGLUE {
    private ByteBuffer allocateDirect( int size ) {
        ByteBuffer bb = ByteBuffer.allocateDirect( size );
        bb.order( ByteOrder.nativeOrder() );
        return bb;
    }

    @Override
    public IntBuffer createIntBuffer ( int size ) {
        //return IntBuffer.allocate(size );
        return allocateDirect( size * Integer.BYTES ).asIntBuffer();
    }

    @Override
    public IntBuffer createIntBuffer ( int[] data ) {
        //return IntBuffer.wrap( data );
        ByteBuffer bb = allocateDirect( data.length * Integer.BYTES );
        for( int i : data ) {
            bb.putInt( i );
        }
        return bb.asIntBuffer();

    }

    @Override
    public FloatBuffer createFloatBuffer ( int size ) {
        //return FloatBuffer.allocate( size );
        return allocateDirect( size * Float.BYTES ).asFloatBuffer();
    }

    @Override
    public FloatBuffer createFloatBuffer ( float[] data ) {
        //return FloatBuffer.wrap( data );
        ByteBuffer bb = allocateDirect( data.length * Float.BYTES );
        for( float f : data ) {
            bb.putFloat( f );
        }
        return bb.asFloatBuffer();
    }

    @Override
    public ByteBuffer createByteBuffer ( int size ) {
        //return ByteBuffer.allocate( size );
        return allocateDirect( size );
    }

    @Override
    public ByteBuffer createByteBuffer ( byte[] data ) {
        //return ByteBuffer.wrap( data );
        ByteBuffer bb = allocateDirect( data.length * Float.BYTES );
        bb.put( data );
        return bb;
    }
}
