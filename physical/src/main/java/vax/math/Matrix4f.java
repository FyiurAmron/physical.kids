package vax.math;

public class Matrix4f extends VectorFloat {
    public final static int SIZE = 4 * 4;

    public static final int // column-row
            M11 = 0, M12 = 1, M13 = 2, M14 = 3, // column 1
            M21 = 4, M22 = 5, M23 = 6, M24 = 7, // column 2
            M31 = 8, M32 = 9, M33 = 10, M34 = 11, // column 3
            M41 = 12, M42 = 13, M43 = 14, M44 = 15, // column 4 - translation
            SCALE_X = M11, SCALE_Y = M22, SCALE_Z = M33, SCALE_W = M44,
            TRANSLATION_X = M41, TRANSLATION_Y = M42, TRANSLATION_Z = M43;

    public final static Matrix4f IDENTITY = new Matrix4f( true );

    public Matrix4f () {
        super( new float[SIZE] );
    }

    public Matrix4f ( boolean identity ) {
        this();
        if ( identity ) {
            data[SCALE_X] = 1;
            data[SCALE_Y] = 1;
            data[SCALE_Z] = 1;
            data[SCALE_W] = 1;
        }
    }

    /**
     Note: if <code>data</code> is an explicit array, this constructor doesn't copy the values provided, but wraps the array instead.

     @param data
     */
    public Matrix4f ( float... data ) {
        super( SIZE, data );
    }

    public Matrix4f ( Matrix4f matrix4f ) {
        super( matrix4f );
    }

    public void setTranslationX ( float transX ) {
        this.data[TRANSLATION_X] = transX;
    }

    public float getTranslationX () {
        return data[TRANSLATION_X];
    }

    public void setTranslationY ( float transY ) {
        this.data[TRANSLATION_Y] = transY;
    }

    public float getTranslationY () {
        return data[TRANSLATION_Y];
    }

    public void setTranslationZ ( float transZ ) {
        this.data[TRANSLATION_Z] = transZ;
    }

    public float getTranslationZ () {
        return data[TRANSLATION_Z];
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
        data[TRANSLATION_X] += x;
        data[TRANSLATION_Y] += y;
        data[TRANSLATION_Z] += z;
    }

    public void addTranslation ( float... translation ) {
        addTranslation( translation[0], translation[1], translation[2] );
    }

    public void addTranslation ( VectorFloat vectorFloat ) {
        addTranslation( vectorFloat.data );
    }

    public void setTranslation ( float x, float y, float z ) {
        data[TRANSLATION_X] = x;
        data[TRANSLATION_Y] = y;
        data[TRANSLATION_Z] = z;
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
                target.data[0] - data[TRANSLATION_X],
                target.data[1] - data[TRANSLATION_Y],
                target.data[2] - data[TRANSLATION_Z] );
    }

    public Vector3f getDisplacement ( Matrix4f target ) {
        /*
         return new Vector3f(
         target.getTranslationX() - getTranslationX(),
         target.getTranslationY() - getTranslationY(),
         target.getTranslationZ() - getTranslationZ() );
         */
        return new Vector3f(
                target.data[TRANSLATION_X] - data[TRANSLATION_X],
                target.data[TRANSLATION_Y] - data[TRANSLATION_Y],
                target.data[TRANSLATION_Z] - data[TRANSLATION_Z] );
    }

    public float translationDistance ( Vector3f v ) {
        /*
         return Vector3f.calcDistance(
         getTranslationX(), getTranslationY(), getTranslationZ(),
         v.getX(), v.getY(), v.getZ()
         );
         */
        return Vector3f.distance(
                data[TRANSLATION_X], data[TRANSLATION_Y], data[TRANSLATION_Z],
                v.data[0], v.data[1], v.data[2]
        );
    }

    public float translationDistance ( Matrix4f matrix4 ) {
        /*
         return Vector3f.calcDistance(
         getTranslationX(), getTranslationY(), getTranslationZ(),
         matrix4.getTranslationX(), matrix4.getTranslationY(), matrix4.getTranslationZ()
         );
         */
        return Vector3f.distance(
                data[TRANSLATION_X], data[TRANSLATION_Y], data[TRANSLATION_Z],
                matrix4.data[TRANSLATION_X], matrix4.data[TRANSLATION_Y], matrix4.data[TRANSLATION_Z]
        );
    }

    public float translationDistanceSq ( Matrix4f matrix4 ) {
        /*
         return Vector3f.calcDistanceSq(
         getTranslationX(), getTranslationY(), getTranslationZ(),
         matrix4.getTranslationX(), matrix4.getTranslationY(), matrix4.getTranslationZ()
         );
         */
        return Vector3f.distanceSq(
                data[TRANSLATION_X], data[TRANSLATION_Y], data[TRANSLATION_Z],
                matrix4.data[TRANSLATION_X], matrix4.data[TRANSLATION_Y], matrix4.data[TRANSLATION_Z]
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

    public Matrix4f scaleXYZ ( float x, float y, float z ) {
        data[SCALE_X] *= x;
        data[SCALE_Y] *= y;
        data[SCALE_Z] *= z;
        return this;
    }

    public Matrix4f scaleXYZ ( Vector3f scale ) {
        scaleXYZ( scale.data[0], scale.data[1], scale.data[2] );
        return this;
    }

    public Matrix4f scaleXYZ ( float scale ) {
        return scaleXYZ( scale, scale, scale );
    }

    public Matrix4f scaleX ( float scale ) {
        data[SCALE_X] *= scale;
        return this;
    }

    public Matrix4f scaleY ( float scale ) {
        data[SCALE_Y] *= scale;
        return this;
    }

    public Matrix4f scaleZ ( float scale ) {
        data[SCALE_Z] *= scale;
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
