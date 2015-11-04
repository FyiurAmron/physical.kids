package vax.math;

import java.util.Arrays;

/**

 @author toor
 */
public class Vector2i {
    private final int[] data = new int[2];

    public Vector2i () {
    }

    public Vector2i ( int x, int y ) {
        data[0] = x;
        data[1] = y;
    }

    public int getX () {
        return data[0];
    }

    public int getY () {
        return data[1];
    }

    public void setX ( int x ) {
        data[0] = x;
    }

    public void setY ( int y ) {
        data[1] = y;
    }

    public void add ( int i ) {
        data[0] += i;
        data[1] += i;
    }

    public void add ( int x, int y ) {
        data[0] += x;
        data[1] += y;
    }

    public void add ( Vector2i v2i ) {
        data[0] *= v2i.data[0];
        data[1] *= v2i.data[1];
    }

    public void scale ( float f ) {
        data[0] *= f;
        data[1] *= f;
    }

    @Override
    public boolean equals ( Object obj ) {
        if ( obj == null ) {
            return false;
        }
        if ( obj == this ) {
            return true;
        }
        if ( !( obj instanceof VectorFloat ) ) {
            return false;
        }
        return Arrays.equals( data, ( (Vector2i) obj ).data );
    }

    @Override
    public int hashCode () {
        return Arrays.hashCode( data );
    }

}
