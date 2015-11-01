package vax.math;


public class Vector4f extends VectorFloat {
    public final static int SIZE = 4;

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

    public float z;

    public void setZ( float val ) {
        data[2] = val;
    }

    public float getZ() {
        return data[2];
    }

    public float w;

    public void setW( float val ) {
        data[3] = val;
    }

    public float getW() {
        return data[3];
    }

    public Vector4f ( Vector4f vector ) {
        super( vector );
    }

    public Vector4f ( float[] data ) {
        super( data, SIZE );
    }

    public Vector4f () {
        super(new float[SIZE]);
    }

    public Vector4f ( float x, float y, float z, float w ) {
        super( new float[]{ x, y, z, w } );
    }

    public Vector4f set ( float x, float y, float z, float w ) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
        return this;
    }
}

