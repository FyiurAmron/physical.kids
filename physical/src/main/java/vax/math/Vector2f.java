package vax.math;

public class Vector2f extends VectorFloat {
    public final static int SIZE = 2;

    public Vector2f () {
        super( new float[SIZE] );
    }

    public Vector2f ( Vector2f vector ) {
        super( vector );
    }

    public Vector2f ( float[] data ) {
        super( SIZE, data );
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

    @Override
    public Vector2f copy () {
        return new Vector2f( this );
    }

    /*
     public Vector2f set ( float x, float y ) {
     data[0] = x;
     data[1] = y;
     return this;
     }
     */
}
