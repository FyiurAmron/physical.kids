package vax.math;

/**

 @author toor
 */
public class FloatUtils {
    public static final float PI = (float) Math.PI, TWO_PI = 2 * PI, HALF_PI = 0.5f * PI, INV_PI = 1.0f / PI;

    public static float sin ( float rads ) {
        return (float) Math.sin( rads );
    }

    public static float cos ( float rads ) {
        return (float) Math.cos( rads );
    }

    // TODO check actual deviation of this value from cos(rads) & benchmark speed
    public static float cosFromSin ( float rads, float sin ) {
        float quadrant = ( rads - FloatUtils.HALF_PI ) % FloatUtils.TWO_PI;
        return ( quadrant < FloatUtils.PI )
                ? -sqrt( 1 - sin * sin )
                : sqrt( 1 - sin * sin );
    }

    public static float tan ( float rads ) {
        return (float) Math.tan( rads );
    }

    public static float cot ( float rads ) {
        return 1.0f / (float) Math.tan( rads );
    }

    public static float sqrt ( float f ) {
        return (float) Math.sqrt( f );
    }

    /**
     Note: as described in
     <a href="http://www.javaspecialists.eu/archive/Issue124.html">Copying Arrays Fast by Dr. Heinz M. Kabutz</a>,
     for small (length ~&lt; 10000 elements) arrays, <code>arraycopy</code> is actually faster than <code>clone</code>.

     @param fs
     @return
     */
    public static float[] copy ( float[] fs ) {
        int len = fs.length;
        if ( len > Short.MAX_VALUE ) {
            return fs.clone();
        }
        float[] ret = new float[len];
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
    public static float[] copy ( float[] src, float[] dest ) {
        System.arraycopy( src, 0, dest, 0, Math.min( src.length, dest.length ) );
        return dest;
    }

    private FloatUtils () {
        throw new UnsupportedOperationException();
    }
}
