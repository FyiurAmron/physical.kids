package vax.math;

public class Matrix4f extends VectorFloat {
    public final static int SIZE = 4 * 4;

    public static final float //
            M11 = 0, M12 = 1, M13 = 2, M14 = 3,
            M21 = 4, M22 = 5, M23 = 6, M24 = 7,
            M31 = 8, M32 = 9, M33 = 10, M34 = 11,
            M41 = 12, M42 = 13, M43 = 14, M44 = 15;

    public final static Matrix4f IDENTITY = new Matrix4f( true );

    public Matrix4f () {
        super( new float[SIZE] );
    }

    public Matrix4f ( boolean identity ) {
        this();
        if ( identity ) {
            data[0] = 1;
            data[5] = 1;
            data[10] = 1;
            data[15] = 1;
        }
    }

    public Matrix4f ( float... data ) {
        super( SIZE, data );
    }

    public Matrix4f ( Matrix4f matrix4f ) {
        super( matrix4f );
    }

    public void setTranslationX ( float transX ) {
        this.data[12] = transX;
    }

    public float getTranslationX () {
        return data[12];
    }

    public void setTranslationY ( float transY ) {
        this.data[13] = transY;
    }

    public float getTranslationY () {
        return data[13];
    }

    public void setTranslationZ ( float transZ ) {
        this.data[14] = transZ;
    }

    public float getTranslationZ () {
        return data[14];
    }

    public void setToIdentity () {
        data[0] = 1;
        data[1] = 0;
        data[2] = 0;
        data[3] = 0;

        data[4] = 0;
        data[5] = 1;
        data[6] = 0;
        data[7] = 0;

        data[8] = 0;
        data[9] = 0;
        data[10] = 1;
        data[11] = 0;

        data[12] = 0;
        data[13] = 0;
        data[14] = 0;
        data[15] = 1;
    }

    public void addTranslation ( float x, float y, float z ) {
        data[12] += x;
        data[13] += y;
        data[14] += z;
    }

    public void addTranslation ( float... translation ) {
        addTranslation( translation[0], translation[1], translation[2] );
    }

    public void addTranslation ( VectorFloat vectorFloat ) {
        addTranslation( vectorFloat.data );
    }

    public void setTranslation ( float x, float y, float z ) {
        data[12] = x;
        data[13] = y;
        data[14] = z;
    }

    public void setTranslation ( float... translation ) {
        setTranslation( translation[0], translation[1], translation[2] );
    }

    public void setTranslation ( VectorFloat vectorFloat ) {
        setTranslation( vectorFloat.data );
    }

    public Vector3f getDisplacement ( Vector3f target ) {
        /*
         return new Vector3f(
         target.getX() - getTranslationX(),
         target.getY() - getTranslationY(),
         target.getZ() - getTranslationZ() );
         */
        return new Vector3f(
                target.data[0] - data[12],
                target.data[1] - data[13],
                target.data[2] - data[14] );
    }

    public Vector3f getDisplacement ( Matrix4f target ) {
        /*
         return new Vector3f(
         target.getTranslationX() - getTranslationX(),
         target.getTranslationY() - getTranslationY(),
         target.getTranslationZ() - getTranslationZ() );
         */
        return new Vector3f(
                target.data[12] - data[12],
                target.data[13] - data[13],
                target.data[14] - data[14] );
    }

    public float translationDistance ( Vector3f v ) {
        /*
         return Vector3f.distance(
         getTranslationX(), getTranslationY(), getTranslationZ(),
         v.getX(), v.getY(), v.getZ()
         );
         */
        return Vector3f.distance(
                data[12], data[13], data[14],
                v.data[0], v.data[1], v.data[2]
        );
    }

    public float translationDistance ( Matrix4f matrix4 ) {
        /*
         return Vector3f.distance(
         getTranslationX(), getTranslationY(), getTranslationZ(),
         matrix4.getTranslationX(), matrix4.getTranslationY(), matrix4.getTranslationZ()
         );
         */
        return Vector3f.distance(
                data[12], data[13], data[14],
                matrix4.data[12], matrix4.data[13], matrix4.data[14]
        );
    }

    public float translationDistanceSq ( Matrix4f matrix4 ) {
        /*
         return Vector3f.distanceSq(
         getTranslationX(), getTranslationY(), getTranslationZ(),
         matrix4.getTranslationX(), matrix4.getTranslationY(), matrix4.getTranslationZ()
         );
         */
        return Vector3f.distanceSq(
                data[12], data[13], data[14],
                matrix4.data[12], matrix4.data[13], matrix4.data[14]
        );
    }

    public Matrix4f setToPerspective ( float near, float far, float degFovY, float aspectRatio ) {
        float //
                f = 1.0f / (float) Math.tan( degFovY * ( Math.PI / 360 ) ),
                nf = 1.0f / ( near - far );

        data[0] = f / aspectRatio;
        data[1] = 0;
        data[2] = 0;
        data[3] = 0;

        data[4] = 0;
        data[5] = f;
        data[6] = 0;
        data[7] = 0;

        data[8] = 0;
        data[9] = 0;
        data[10] = ( far + near ) * nf;
        data[11] = -1;

        data[12] = 0;
        data[13] = 0;
        data[14] = 2 * far * near * nf;
        data[15] = 0;

        return this;

    }

    /**

     @param left
     @param right
     @param bottom
     @param top
     @param near should be strictly &gt; 0 to avoid depth buffer problems
     @param far
     @return
     */
    public Matrix4f setToFrustum ( float left, float right, float bottom, float top, float near, float far ) {
        float //
                rl = 1.0f / ( right - left ),
                tb = 1.0f / ( top - bottom ),
                nf = 1.0f / ( near - far ); // == -fn

        data[0] = 2.0f * near * rl;
        data[1] = 0;
        data[2] = 0;
        data[3] = 0;

        data[4] = 0;
        data[5] = 2.0f * near * tb;
        data[6] = 0;
        data[7] = 0;

        data[8] = ( right + left ) * rl;
        data[9] = ( top + bottom ) * tb;
        data[10] = ( far + near ) * nf;
        data[11] = -1;

        data[12] = 0;
        data[13] = 0;
        data[14] = 2 * far * near * nf;
        data[15] = 0;

        return this;
    }

    /**

     @param x leftmost window coord
     @param y bottommost window coord
     @param width
     @param height
     @return
     */
    public Matrix4f setToOrthoWindow ( float x, float y, float width, float height ) {
        setToOrthoWindow( x, y, width, height, 0, 1 );
        return this;
    }

    /**

     @param x leftmost window coord
     @param y bottommost window coord
     @param width
     @param height
     @param near
     @param far
     @return
     */
    public Matrix4f setToOrthoWindow ( float x, float y, float width, float height, float near, float far ) {
        setToOrtho( x, x + width, y, y + height, near, far );
        return this;
    }

    public Matrix4f setToOrtho ( float left, float right, float bottom, float top, float near, float far ) {
        float //
                rl = 1.0f / ( right - left ),
                tb = 1.0f / ( top - bottom ),
                fn = 1.0f / ( far - near );

        data[0] = 2 * rl;
        data[1] = 0;
        data[2] = 0;
        data[3] = 0;

        data[4] = 0;
        data[5] = 2 * tb;
        data[6] = 0;
        data[7] = 0;

        data[8] = 0;
        data[9] = 0;
        data[10] = -2 * fn;
        data[11] = 0;

        data[12] = ( right + left ) * -rl;
        data[13] = ( top + bottom ) * -tb;
        data[14] = ( far + near ) * -fn;
        data[15] = 1;

        return this;
    }


    /*
     public void setScaleAndRotation ( Matrix4 matrix4 ) {
     data[0] = matrix4.M11;
     data[1] = matrix4.M12;
     data[2] = matrix4.M13;

     data[4] = matrix4.M21;
     data[5] = matrix4.M22;
     data[6] = matrix4.M23;

     data[8] = matrix4.M31;
     data[9] = matrix4.M32;
     data[10] = matrix4.M33;
     }
     */
    public static Matrix4f setToRotationX ( float rotationAngle ) {
        throw new UnsupportedOperationException( "Not yet implemented." );
    }

    public static Matrix4f setToRotationY ( float rotationAngle ) {
        throw new UnsupportedOperationException( "Not yet implemented." );
    }

    public static Matrix4f setToRotationZ ( float rotationAngle ) {
        throw new UnsupportedOperationException( "Not yet implemented." );
    }

    public static Matrix4f lookAt ( Vector3f pos, Vector3f vector3f, Vector3f vector3f0 ) {
        throw new UnsupportedOperationException( "Not yet implemented." );
    }
}
