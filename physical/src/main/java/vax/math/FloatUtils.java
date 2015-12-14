package vax.math;

/**

 @author toor
 */
public class FloatUtils {
    public static final float PI = (float) Math.PI, TWO_PI = 2 * PI, HALF_PI = 0.5f * PI, INV_PI = 1.0f / PI;

    public static float sin ( float f ) {
        return (float) Math.sin( f );
    }

    public static float cos ( float f ) {
        return (float) Math.cos( f );
    }

    public static float tan ( float f ) {
        return (float) Math.tan( f );
    }

    public static float cot ( float f ) {
        return 1.0f / (float) Math.tan( f );
    }

    public static float sqrt ( float f ) {
        return (float) Math.sqrt( f );
    }

    private FloatUtils () {
        throw new UnsupportedOperationException();
    }
}
