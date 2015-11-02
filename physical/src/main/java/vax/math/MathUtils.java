package vax.math;

import java.util.Random;

public class MathUtils {
    static final private Random rng = new Random();

    private MathUtils () {
        throw new IllegalStateException();
    }

    static public float nextFloat () {
        return rng.nextFloat();
    }

    static public float nextFloat ( float min, float max ) {
        return rng.nextFloat() * ( max - min ) + min;
    }

    /*
     static public int floatToIntBits ( float f ) {
     return Float.floatToIntBits( f );
     }

     static public long doubleToLongBits ( double d ) {
     return Double.doubleToLongBits( d );
     }
     */
}
