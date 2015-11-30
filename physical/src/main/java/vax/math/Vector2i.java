package vax.math;

/**

 @author toor
 */
public class Vector2i extends VectorInt {

    public final static int SIZE = 2;

    public Vector2i () {
        super( new int[SIZE] );
    }

    public Vector2i ( Vector2i vector ) {
        super( vector );
    }

    /**
     Note: this constructor doesn't copy the values provided, but wraps the array instead.

     @param data
     */
    public Vector2i ( int[] data ) {
        super( SIZE, data );
    }

    public Vector2i ( int x, int y ) {
        super( new int[]{ x, y } );
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
        data[0] += v2i.data[0];
        data[1] += v2i.data[1];
    }

    public void scale ( float f ) {
        data[0] *= f;
        data[1] *= f;
    }

    public Vector2i set ( int x, int y ) {
        data[0] = x;
        data[1] = y;
        return this;
    }

    @Override
    public Vector2i copy () {
        return new Vector2i( this );
    }
}
