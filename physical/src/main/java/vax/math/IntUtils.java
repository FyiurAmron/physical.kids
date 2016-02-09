package vax.math;

/**

 @author toor
 */
public class IntUtils {
    public static int[] copy ( int[] fs ) {
        int len = fs.length;
        int[] ret = new int[len];
        System.arraycopy( fs, 0, ret, 0, len );
        return ret;
    }

    public static int[] set ( int[] dest, int[] src ) {
        System.arraycopy( src, 0, dest, 0, Math.min( src.length, dest.length ) );
        return dest;
    }

    private IntUtils () {
        throw new UnsupportedOperationException();
    }
}
