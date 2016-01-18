package vax.math;

/**
 OpenGL-compatible column-major 4x4 square matrix.

 @author toor
 */
public class Matrix4f extends VectorFloat {
    public final static int SIZE = 4 * 4;

    public static final double DELTA = 1E-4f;

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
     Note: if <code>data</code> is an explicit array, this constructor doesn't
     copy the values provided, but wraps the array instead.

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

    public Vector3f createDisplacement ( Vector3f target ) {
        return getDisplacement( target, new Vector3f() );
    }

    public Vector3f getDisplacement ( Vector3f target, Vector3f output ) {
        return output.set(
                target.data[0] - data[TRANSLATION_X],
                target.data[1] - data[TRANSLATION_Y],
                target.data[2] - data[TRANSLATION_Z] );
    }

    public Vector3f createDisplacement ( Matrix4f target ) {
        return getDisplacement( target, new Vector3f() );
    }

    public Vector3f getDisplacement ( Matrix4f target, Vector3f output ) {
        return output.set(
                target.data[TRANSLATION_X] - data[TRANSLATION_X],
                target.data[TRANSLATION_Y] - data[TRANSLATION_Y],
                target.data[TRANSLATION_Z] - data[TRANSLATION_Z] );
    }

    public float calcTranslationDistance ( Vector3f v ) {
        return Vector3f.distance(
                data[TRANSLATION_X], data[TRANSLATION_Y], data[TRANSLATION_Z],
                v.data[0], v.data[1], v.data[2]
        );
    }

    public float calcTranslationDistance ( Matrix4f matrix4 ) {
        return Vector3f.distance(
                data[TRANSLATION_X], data[TRANSLATION_Y], data[TRANSLATION_Z],
                matrix4.data[TRANSLATION_X], matrix4.data[TRANSLATION_Y], matrix4.data[TRANSLATION_Z]
        );
    }

    public float calcTranslationDistanceSq ( Matrix4f matrix4 ) {
        return Vector3f.distanceSq(
                data[TRANSLATION_X], data[TRANSLATION_Y], data[TRANSLATION_Z],
                matrix4.data[TRANSLATION_X], matrix4.data[TRANSLATION_Y], matrix4.data[TRANSLATION_Z]
        );
    }

    public Matrix4f setToPerspective ( float near, float far, float degFovY, float aspectRatio ) {
        float //
                f = FloatUtils.cot( degFovY * FloatUtils.PI / 360 ),
                nf = 1.0f / (near - far);

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
        data[10] = (far + near) * nf;
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
                rl = 1.0f / (right - left),
                tb = 1.0f / (top - bottom),
                nf = 1.0f / (near - far); // == -fn

        data[0] = 2.0f * near * rl;
        data[1] = 0;
        data[2] = 0;
        data[3] = 0;

        data[4] = 0;
        data[5] = 2.0f * near * tb;
        data[6] = 0;
        data[7] = 0;

        data[8] = (right + left) * rl;
        data[9] = (top + bottom) * tb;
        data[10] = (far + near) * nf;
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
                rl = 1.0f / (right - left),
                tb = 1.0f / (top - bottom),
                fn = 1.0f / (far - near);

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

        data[12] = (right + left) * -rl;
        data[13] = (top + bottom) * -tb;
        data[14] = (far + near) * -fn;
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
    public Matrix4f setToRotationX ( float rotationAngleRad ) {
        float sin = FloatUtils.sin( rotationAngleRad ),
                cos = FloatUtils.cosFromSin( rotationAngleRad, sin );

        data[0] = 1;
        data[1] = 0;
        data[2] = 0;
        data[3] = 0;

        data[4] = 0;
        data[5] = cos;
        data[6] = sin;
        data[7] = 0;

        data[8] = 0;
        data[9] = -sin;
        data[10] = cos;
        data[11] = 0;

        data[12] = 0;
        data[13] = 0;
        data[14] = 0;
        data[15] = 1;

        return this;
    }

    public Matrix4f setToRotationY ( float rotationAngleRad ) {
        float sin = FloatUtils.sin( rotationAngleRad ),
                cos = FloatUtils.cosFromSin( rotationAngleRad, sin );

        data[0] = cos;
        data[1] = 0;
        data[2] = -sin;
        data[3] = 0;

        data[4] = 0;
        data[5] = 1;
        data[6] = 0;
        data[7] = 0;

        data[8] = sin;
        data[9] = 0;
        data[10] = cos;
        data[11] = 0;

        data[12] = 0;
        data[13] = 0;
        data[14] = 0;
        data[15] = 1;

        return this;
    }

    public Matrix4f setToRotationZ ( float rotationAngleRad ) {
        float sin = FloatUtils.sin( rotationAngleRad ),
                cos = FloatUtils.cosFromSin( rotationAngleRad, sin );

        data[0] = cos;
        data[1] = sin;
        data[2] = 0;
        data[3] = 0;

        data[4] = -sin;
        data[5] = cos;
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

        return this;
    }

    /**
     Tait-Bryan angles.

     @param roll
     @param yaw
     @param pitch
     @return
     */
    public Matrix4f setToRotationTB ( float yaw, float pitch, float roll ) {
        float sinYaw = FloatUtils.sin( roll ),
                sinPitch = FloatUtils.sin( yaw ),
                sinRoll = FloatUtils.sin( pitch );
        float cosYaw = FloatUtils.cosFromSin( roll, sinYaw ),
                cosPitch = FloatUtils.cosFromSin( yaw, sinPitch ),
                cosRoll = FloatUtils.cosFromSin( pitch, sinRoll );
        float sYcR = sinYaw * cosRoll,
                sYsR = sinYaw * sinRoll,
                cYcR = cosYaw * cosRoll,
                cYsR = cosYaw * sinRoll;

        data[0] = cosPitch * cosYaw;
        data[1] = cosPitch * sinYaw;
        data[2] = -sinPitch;
        data[3] = 0;

        data[4] = cYsR * sinPitch - sYcR;
        data[5] = sYsR * sinPitch + cYcR;
        data[6] = cosPitch * sinRoll;
        data[7] = 0;

        data[8] = cYcR * sinPitch + sYsR;
        data[9] = sYcR * sinPitch - cYsR;
        data[10] = cosPitch * cosRoll;
        data[11] = 0;

        data[12] = 0;
        data[13] = 0;
        data[14] = 0;
        data[15] = 1;

        return this;
    }

    public static Matrix4f lookAt ( Vector3f pos, Vector3f vector3f, Vector3f vector3f0 ) {
        throw new UnsupportedOperationException( "Not yet implemented." );
    }

    /**
     Calculates the determinant of the upper 3x3 matrix.
     <p>
     Can be used to calculate det for rot-scale-trans matrices (e.g. OpenGL
     model/view matrices), since their 4th (W) row is [0,0,0,1] by definition.

     @return the determinant of the upper 3x3 matrix.
     */
    public float det3x3 () {
        return data[M11] * (data[M22] * data[M33] - data[M23] * data[M32])
                + data[M12] * (data[M23] * data[M31] - data[M21] * data[M33])
                + data[M13] * (data[M21] * data[M32] - data[M22] * data[M31]);
    }

    /**
     Forces the calculation of the full matrix without checking for any
     simplifying conditions.
     <p>
     Should be used if the matrix is guaranteed <b>not</b> to have any zeros in
     W row.

     @return the determinant of this matrix
     */
    public float det4x4 () {
        float //
                ab = data[M33] * data[M42] - data[M32] * data[M43],
                cd = data[M31] * data[M43] - data[M33] * data[M41],
                ef = data[M32] * data[M41] - data[M31] * data[M42],
                hg = data[M13] * data[M21] - data[M11] * data[M23],
                ij = data[M13] * data[M22] - data[M12] * data[M23],
                kl = data[M12] * data[M21] - data[M11] * data[M22];

        return data[M14]
                * (data[M21] * ab
                + data[M22] * cd
                + data[M23] * ef)
                - data[M24]
                * (data[M11] * ab
                + data[M12] * cd
                + data[M13] * ef)
                + data[M34]
                * (data[M41] * ij
                - data[M42] * hg
                + data[M43] * kl)
                - data[M44]
                * (data[M31] * ij
                - data[M32] * hg
                + data[M33] * kl);
    }

    /**
     Calculates the determinant using simplifying conditions for 0s in W row;
     speeds up the calculations for e.g. perspective matrix, and other similar
     matrices.
     <p>
     Note: if this matrix is known to have a [0,0,0,1] in W row (e.g. model/view
     matrix), it's slightly better performance-wise to simply call det3x3().

     @return the determinant of this matrix
     */
    public float det () {
        float ab, cd, ef, hg, ij, kl, det;
        if ( data[M14] == 0 ) {
            if ( data[M24] == 0 ) {
                if ( data[M34] == 0 && data[M44] == 1 ) {
                    return det3x3(); // main branch
                }
                det = 0;
            } else {
                ab = data[M33] * data[M42] - data[M32] * data[M43];
                cd = data[M31] * data[M43] - data[M33] * data[M41];
                ef = data[M32] * data[M41] - data[M31] * data[M42];
                det = -data[M24] * (data[M11] * ab
                        + data[M12] * cd
                        + data[M13] * ef);
            }
        } else {
            ab = data[M33] * data[M42] - data[M32] * data[M43];
            cd = data[M31] * data[M43] - data[M33] * data[M41];
            ef = data[M32] * data[M41] - data[M31] * data[M42];
            det = data[M14]
                    * (data[M21] * ab
                    + data[M22] * cd
                    + data[M23] * ef)
                    - data[M24]
                    * (data[M11] * ab
                    + data[M12] * cd
                    + data[M13] * ef);
        }
        if ( data[M34] != 0 ) {
            hg = data[M13] * data[M21] - data[M11] * data[M23];
            ij = data[M13] * data[M22] - data[M12] * data[M23];
            kl = data[M12] * data[M21] - data[M11] * data[M22];
            det += data[M34]
                    * (data[M41] * ij
                    - data[M42] * hg
                    + data[M43] * kl);
            if ( data[M44] != 0 ) { // 2nd most common branch cont.
                det -= data[M44]
                        * (data[M31] * ij
                        - data[M32] * hg
                        + data[M33] * kl);
            }
        } else if ( data[M44] != 0 ) {
            hg = data[M13] * data[M21] - data[M11] * data[M23];
            ij = data[M13] * data[M22] - data[M12] * data[M23];
            kl = data[M12] * data[M21] - data[M11] * data[M22];
            det -= data[M44]
                    * (data[M31] * ij
                    - data[M32] * hg
                    + data[M33] * kl);
        } // else nada; both == 0
        return det;
    }

    public Matrix4f transpose () {
        return transpose( this );
    }

    public Matrix4f transpose ( Matrix4f destination ) {
        float tmp;

        destination.data[M11] = data[M11];
        destination.data[M22] = data[M22];
        destination.data[M33] = data[M33];
        destination.data[M44] = data[M44];

        tmp = data[M12];
        destination.data[M12] = data[M21];
        destination.data[M21] = tmp;

        tmp = data[M13];
        destination.data[M13] = data[M31];
        destination.data[M31] = tmp;

        tmp = data[M14];
        destination.data[M14] = data[M41];
        destination.data[M41] = tmp;

        tmp = data[M23];
        destination.data[M23] = data[M32];
        destination.data[M32] = tmp;

        tmp = data[M24];
        destination.data[M24] = data[M42];
        destination.data[M42] = tmp;

        tmp = data[M34];
        destination.data[M34] = data[M43];
        destination.data[M43] = tmp;

        return destination;
    }

    /**
    Note: this method allocates a temporary helper float[16].
    @return this matrix for chaining
    */
    public Matrix4f invert () {
        float[] tmp = new float[16];
        invert( data, tmp );
        set( tmp );
        return this;
    }

    public Matrix4f invert ( Matrix4f output ) {
        invert( data, output.data );
        return this;
    }

    public static float[] invert ( float[] data, float[] output ) {
        float a = data[M11] * data[M22] - data[M12] * data[M21];
        float b = data[M11] * data[M23] - data[M13] * data[M21];
        float c = data[M11] * data[M24] - data[M14] * data[M21];
        float d = data[M12] * data[M23] - data[M13] * data[M22];
        float e = data[M12] * data[M24] - data[M14] * data[M22];
        float f = data[M13] * data[M24] - data[M14] * data[M23];
        float g = data[M31] * data[M42] - data[M32] * data[M41];
        float h = data[M31] * data[M43] - data[M33] * data[M41];
        float i = data[M31] * data[M44] - data[M34] * data[M41];
        float j = data[M32] * data[M43] - data[M33] * data[M42];
        float k = data[M32] * data[M44] - data[M34] * data[M42];
        float l = data[M33] * data[M44] - data[M34] * data[M43];

        float det = a * l - b * k + c * j + d * i - e * h + f * g;
        det = 1.0f / det;
        a *= det;
        b *= det;
        c *= det;
        d *= det;
        e *= det;
        f *= det;
        g *= det;
        h *= det;
        i *= det;
        j *= det;
        k *= det;
        l *= det;

        output[0] = data[M22] * l - data[M23] * k + data[M24] * j;
        output[1] = -data[M12] * l + data[M13] * k - data[M14] * j;
        output[2] = data[M42] * f - data[M43] * e + data[M44] * d;
        output[3] = -data[M32] * f + data[M33] * e - data[M34] * d;
        output[4] = -data[M21] * l + data[M23] * i - data[M24] * h;
        output[5] = data[M11] * l - data[M13] * i + data[M14] * h;
        output[6] = -data[M41] * f + data[M43] * c - data[M44] * b;
        output[7] = data[M31] * f - data[M33] * c + data[M34] * b;
        output[8] = data[M21] * k - data[M22] * i + data[M24] * g;
        output[9] = -data[M11] * k + data[M12] * i - data[M14] * g;
        output[10] = data[M41] * e - data[M42] * c + data[M44] * a;
        output[11] = -data[M31] * e + data[M32] * c - data[M34] * a;
        output[12] = -data[M21] * j + data[M22] * h - data[M23] * g;
        output[13] = data[M11] * j - data[M12] * h + data[M13] * g;
        output[14] = -data[M41] * d + data[M42] * b - data[M43] * a;
        output[15] = data[M31] * d - data[M32] * b + data[M33] * a;
        return output;
    }

}
