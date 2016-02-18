package vax.math;

/**

 @author toor
 */
public class IntUtils {
    /**
     Note: as described in
     <a href="http://www.javaspecialists.eu/archive/Issue124.html">Copying Arrays Fast by Dr. Heinz M. Kabutz</a>,
     for small (length ~&lt; 10000 elements) arrays, <code>arraycopy</code> is actually faster than <code>clone</code>.

     @param fs
     @return
     */
    public static int[] copy ( int[] fs ) {
        int len = fs.length;
        if ( len > Short.MAX_VALUE ) {
            return fs.clone();
        }
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
