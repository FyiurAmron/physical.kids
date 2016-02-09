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

    /**
    Copies one array into the other. Only <code>min(src.length, dest.length)</code> elements are copied, i.e.:
    <p>
    If dest.length &lt; src.length, only as many elements as can fit into dest are copied.
    <p>
    If dest.length &gt; src.length, only as many elements as src has are copied.
    @param src
    @param dest
    @return
    */
    public static int[] copy ( int[] src, int[] dest ) {
        System.arraycopy( src, 0, dest, 0, Math.min( src.length, dest.length ) );
        return dest;
    }

    private IntUtils () {
        throw new UnsupportedOperationException();
    }
}
