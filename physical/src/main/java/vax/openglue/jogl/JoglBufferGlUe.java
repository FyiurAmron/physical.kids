package vax.openglue.jogl;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import com.jogamp.common.nio.Buffers;
import vax.openglue.BufferGlUe;

/**

 @author toor
 */
public class JoglBufferGlUe implements BufferGlUe {
    @Override
    public IntBuffer ueCreateIntBuffer ( int size ) {
        return Buffers.newDirectIntBuffer( size );
    }

    @Override
    public IntBuffer ueCreateIntBuffer ( int[] data ) {
        return Buffers.newDirectIntBuffer( data );
    }

    @Override
    public FloatBuffer ueCreateFloatBuffer ( int size ) {
        return Buffers.newDirectFloatBuffer( size );
    }

    @Override
    public FloatBuffer ueCreateFloatBuffer ( float[] data ) {
        return Buffers.newDirectFloatBuffer( data );
    }
}
