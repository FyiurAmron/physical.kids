package vax.math;

import java.util.Random;

public class MathUtils {
    static final private Random RNG = new Random();

    private MathUtils () {
        throw new IllegalStateException();
    }

    static public float nextFloat () {
        return RNG.nextFloat();
    }

    static public float nextFloat ( float min, float max ) {
        return RNG.nextFloat() * ( max - min ) + min;
    }

    static public int nextInt () {
        return RNG.nextInt();
    }

    static public int nextInt ( int min, int max ) {
        return RNG.nextInt( max - min ) + min;
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
