package vax.math;

public class Vector2f extends VectorFloat {
    public final static int SIZE = 2;

    public Vector2f () {
        super( new float[SIZE] );
    }

    public Vector2f ( Vector2f vector ) {
        super( vector );
    }

    /**
     Note: this constructor doesn't copy the values provided, but wraps the array instead.

     @param data
     */
    public Vector2f ( float[] data ) {
        super( SIZE, data );
    }

    public Vector2f ( boolean copyData, float[] data ) {
        super( copyData, data );
    }

    public Vector2f ( float x, float y ) {
        super( new float[]{ x, y } );
    }

    public float getX () {
        return data[0];
    }

    public float getY () {
        return data[1];
    }

    public void setX ( float val ) {
        data[0] = val;
    }

    public void setY ( float val ) {
        data[1] = val;
    }

    public Vector2f set ( float x, float y ) {
        data[0] = x;
        data[1] = y;
        return this;
    }

    @Override
    public Vector2f copy () {
        return new Vector2f( this );
    }
}
