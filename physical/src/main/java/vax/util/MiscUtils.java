package vax.util;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**

 @author toor
 */
public class MiscUtils {
    private MiscUtils () {
        throw new UnsupportedOperationException();
    }

    public static boolean isNullOrEmpty ( String s ) {
        return s == null || s.isEmpty();
    }

    public static int[] toArray ( IntBuffer fb ) {
        int[] arr = new int[fb.limit()];
        fb.get( arr );
        fb.rewind();
        return arr;
    }

    public static float[] toArray ( FloatBuffer fb ) {
        float[] arr = new float[fb.limit()];
        fb.get( arr );
        fb.rewind();
        return arr;
    }
}
