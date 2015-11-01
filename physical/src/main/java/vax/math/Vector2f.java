package vax.math;

public class Vector2f extends VectorFloat {

    public final static int SIZE = 2;

    public float x;

    public void setX( float val ) {
        data[0] = val;
    }

    public float getX() {
        return data[0];
    }

    public float y;

    public void setY( float val ) {
        data[1] = val;
    }

    public float getY() {
        return data[1];
    }

    public Vector2f( Vector2f vector ) {
        super( vector );
    }

    public Vector2f( float[] data ) {
        super( data, SIZE );
    }

    public Vector2f() {
        super( new float[SIZE] );
    }

    public Vector2f( float x, float y ) {
        super( new float[] { x, y });
    }

    public Vector2f set( float x, float y ) {
        this.x = x;
        this.y = y;
        return this;
    }
}
