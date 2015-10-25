package vax.util;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**

 @author toor
 */
public class BufferUtils {
    public static ByteBuffer wrap ( byte... data ) {
        return ByteBuffer.wrap( data );
    }

    public static IntBuffer wrap ( int... data ) {
        return IntBuffer.wrap( data );
    }

    public static FloatBuffer wrap ( float... data ) {
        return FloatBuffer.wrap( data );
    }
    /*
     public static DoubleBuffer wrap ( double... data ) {
     return DoubleBuffer.wrap( data );
     }
     */

    private BufferUtils () {
        throw new UnsupportedOperationException();
    }
}
