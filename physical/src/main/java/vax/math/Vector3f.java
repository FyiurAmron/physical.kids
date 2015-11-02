package vax.math;

public class Vector3f extends VectorFloat {
    public final static int SIZE = 3,
            OX = 0, OY = 1, OZ = 2;

    public Vector3f ( Vector3f vector ) {
        super( vector );
    }

    public Vector3f ( float... data ) {
        super( SIZE, data );
    }

    public Vector3f () {
        super( new float[SIZE] );
    }

    public Vector3f ( float x, float y, float z ) {
        super( new float[]{ x, y, z } );
    }

    //
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

    /*
     the overloads below are supplied strictly for performance reasons
     */
    public Vector3f set ( float x, float y, float z ) {
        data[0] = x;
        data[1] = y;
        data[2] = z;
        return this;
    }

    public Vector3f add ( float x, float y, float z ) {
        data[0] += x;
        data[1] += y;
        data[2] += z;
        return this;
    }

    public Vector3f scale ( float x, float y, float z ) {
        data[0] *= x;
        data[1] *= y;
        data[2] *= z;
        return this;
    }

    public float dot ( float x, float y, float z ) {
        return data[0] * x + data[1] * y + data[2] * z;
    }

    public Vector3f cross ( Vector3f vector ) {
        return new Vector3f( cross( data, vector.data ) );
    }

    public Vector3f getNormal ( Vector3f v1, Vector3f v2 ) {
        return new Vector3f( getNormal( data, v1.data, v2.data ) );
    }

    /*
     static methods
     */
    static public float[] cross ( float[] v1, float[] v2 ) {
        return new float[]{
            v1[1] * v2[2] - v1[2] * v2[1],
            v1[2] * v2[0] - v1[0] * v2[2],
            v1[0] * v2[1] - v1[1] * v2[0]
        };
    }

    static public float[] getNormal ( float[] v1, float[] v2, float[] v3 ) {
        float[] norm = cross(
                new float[]{ v2[0] - v1[0], v2[1] - v1[1], v2[2] - v1[2] },
                new float[]{ v3[0] - v1[0], v3[1] - v1[1], v3[2] - v1[2] } );
        normalize( norm );
        return norm;
    }

    static public float distance ( float x1, float y1, float z1, float x2, float y2, float z2 ) {
        return (float) Math.sqrt( distanceSq( x1, y1, z1, x2, y2, z2 ) );
    }

    static public float distanceSq ( float x1, float y1, float z1, float x2, float y2, float z2 ) {
        float x = x2 - x1, y = y2 - y1, z = z2 - z1;
        return x * x + y * y + z * z;
    }
}
