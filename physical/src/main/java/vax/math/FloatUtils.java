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

    private FloatUtils () {
        throw new UnsupportedOperationException();
    }
}
