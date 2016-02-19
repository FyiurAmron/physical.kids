package vax.math;

public class Vector3f extends VectorFloat {
    public final static int SIZE = 3;

    public Vector3f () {
        super( new float[SIZE] );
    }

    public Vector3f ( Vector3f vector ) {
        super( vector );
    }

    /**
     Note: this constructor doesn't copy the values provided, but wraps the array instead.

     @param data
     */
    public Vector3f ( float[] data ) {
        super( SIZE, data );
    }

    public Vector3f ( boolean copyData, float[] data ) {
        super( copyData, data );
    }

    /**
     Creates a getCross product.

     @param v1
     @param v2
     */
    public Vector3f ( Vector3f v1, Vector3f v2 ) {
        super( createCross( v1.data, v2.data ) );
    }

    /**
     Creates a getNormal vector, utilizing getCross product.

     @param point1
     @param point2
     @param point3
     */
    public Vector3f ( Vector3f point1, Vector3f point2, Vector3f point3 ) {
        super( createNormal( point1.data, point2.data, point3.data ) );
    }

    public Vector3f ( float x, float y, float z ) {
        super( new float[]{ x, y, z } );
    }

    //
    public float getX () {
        return data[0];
    }

    public float getY () {
        return data[1];
    }

    public float getZ () {
        return data[2];
    }

    public void setX ( float val ) {
        data[0] = val;
    }

    public void setY ( float val ) {
        data[1] = val;
    }

    public void setZ ( float val ) {
        data[2] = val;
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
        Vector3f.getCross( data, vector.data, data );
        return this;
    }

    public Vector3f normal ( Vector3f v1, Vector3f v2 ) {
        getNormal( data, v1.data, v2.data, data );
        return this;
    }

    public Vector3f setToCross ( Vector3f v1, Vector3f v2 ) {
        Vector3f.getCross( v1.data, v2.data, data );
        return this;
    }

    public Vector3f setToNormal ( Vector3f v1, Vector3f v2, Vector3f v3 ) {
        Vector3f.getNormal( v1.data, v2.data, v3.data, data );
        return this;
    }

    public Vector3f createCross ( Vector3f vector ) {
        return new Vector3f( createCross( data, vector.data ) );
    }

    public Vector3f createNormal ( Vector3f v1, Vector3f v2 ) {
        return new Vector3f( createNormal( data, v1.data, v2.data ) );
    }

    @Override
    public Vector3f copy () {
        return new Vector3f( this );
    }

    /*
     static methods
     */
    static public float[] getCross ( float v10, float v11, float v12, float v20, float v21, float v22, float[] output ) {
        output[0] = v11 * v22 - v12 * v21;
        output[1] = v12 * v20 - v10 * v22;
        output[2] = v10 * v21 - v11 * v20;
        return output;
    }

    /**
     Note: output <b>may</b> be equal to v1 or v2 without any errors.

     @param v1
     @param v2
     @param output
     @return
     */
    static public float[] getCross ( float[] v1, float[] v2, float[] output ) {
        return getCross( v1[0], v1[1], v1[2], v2[0], v2[1], v2[2], output );
    }

    static public float[] createCross ( float[] v1, float[] v2 ) {
        return Vector3f.getCross( v1, v2, new float[SIZE] );
    }

    static public float[] getNormal ( float v10, float v11, float v12, float v20, float v21, float v22, float v30, float v31, float v32,
            float[] output ) {
        getCross(
                v20 - v10, v21 - v11, v22 - v12,
                v30 - v10, v31 - v11, v32 - v12,
                output );
        normalize( output );
        return output;
    }

    /**
     Note: output <b>may</b> be equal to v1 or v2 without any errors.

     @param v1
     @param v2
     @param v3
     @param output
     @return
     */
    static public float[] getNormal ( float[] v1, float[] v2, float[] v3, float[] output ) {
        return Vector3f.getNormal( v1[0], v1[1], v1[2], v2[0], v2[1], v2[2], v3[0], v3[1], v3[2], output );
    }

    static public float[] createNormal ( float[] v1, float[] v2, float[] v3 ) {
        return getNormal( v1, v2, v3, new float[SIZE] );
    }

    static public float distance ( float x1, float y1, float z1, float x2, float y2, float z2 ) {
        return (float) Math.sqrt( distanceSq( x1, y1, z1, x2, y2, z2 ) );
    }

    static public float distanceSq ( float x1, float y1, float z1, float x2, float y2, float z2 ) {
        float x = x2 - x1, y = y2 - y1, z = z2 - z1;
        return x * x + y * y + z * z;
    }
}
