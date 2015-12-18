package vax.math;

import java.util.Random;

public class MathUtils {
    static final private Random RNG = new Random();

    private MathUtils () {
        throw new IllegalStateException();
    }

    public static byte[] nextBytes ( byte[] bytes ) {
        RNG.nextBytes( bytes );
        return bytes;
    }

    public static float nextFloat () {
        return RNG.nextFloat();
    }

    public static float nextFloat ( float min, float max ) {
        return RNG.nextFloat() * ( max - min ) + min;
    }

    public static int nextInt () {
        return RNG.nextInt();
    }

    /**
     @param max
     @return int in range [0,max)
     */
    public static int nextInt ( int max ) {
        return RNG.nextInt( max );
    }

    /**
     @param min
     @param max
     @return int in range [min,max)
     */
    public static int nextInt ( int min, int max ) {
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
