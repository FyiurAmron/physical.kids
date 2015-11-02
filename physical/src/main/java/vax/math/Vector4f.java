package vax.math;

public class Vector4f extends VectorFloat {
    public final static int SIZE = 4;

    public Vector4f ( Vector4f vector ) {
        super( vector );
    }

    public Vector4f ( float[] data ) {
        super( SIZE, data );
    }

    public Vector4f () {
        super( new float[SIZE] );
    }

    public Vector4f ( float x, float y, float z, float w ) {
        super( new float[]{ x, y, z, w } );
    }

    public void setX ( float val ) {
        data[0] = val;
    }

    public float getX () {
        return data[0];
    }

    public void setY ( float val ) {
        data[1] = val;
    }

    public float getY () {
        return data[1];
    }

    public void setZ ( float val ) {
        data[2] = val;
    }

    public float getZ () {
        return data[2];
    }

    public void setW ( float val ) {
        data[3] = val;
    }

    public float getW () {
        return data[3];
    }

    /*
     public Vector4f set ( float x, float y, float z, float w ) {
     data[0] = x;
     data[1] = y;
     data[2] = z;
     data[3] = w;
     return this;
     }
     */
}
