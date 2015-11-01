package vax.math;

import java.util.Random;

public class MathUtils {

    MathUtils () {
        throw new IllegalStateException();
    }

    static public float nextFloat () {
        Random rand = new Random();
        return rand.nextFloat();
    }

    static public float nextFloat ( float min, float max ) {
        Random rand = new Random();
        return rand.nextFloat() * (max - min) + min;
    }

    static public int floatToIntBits ( float f ) {
        return Float.floatToIntBits(f);
    }
    
    static public long doubleToLongBits (double d) {
        return Double.doubleToLongBits( d );
    }
}
