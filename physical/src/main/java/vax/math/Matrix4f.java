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
    /*
     M11 M21 M31 M41
     M12 M22 M32 M42
     M13 M23 M33 M43
     M14 M24 M34 M44
     */

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

    public Matrix4f ( boolean copyData, float[] data ) {
        super( copyData, data );
    }

    public Matrix4f setTranslationX ( float transX ) {
        this.data[TRANSLATION_X] = transX;
        return this;
    }

    public float getTranslationX () {
        return data[TRANSLATION_X];
    }

    public Matrix4f setTranslationY ( float transY ) {
        this.data[TRANSLATION_Y] = transY;
        return this;
    }

    public float getTranslationY () {
        return data[TRANSLATION_Y];
    }

    public Matrix4f setTranslationZ ( float transZ ) {
        this.data[TRANSLATION_Z] = transZ;
        return this;
    }

    public float getTranslationZ () {
        return data[TRANSLATION_Z];
    }

    public Vector3f getTranslation ( Vector3f output ) {
        return output.set( data[TRANSLATION_X], data[TRANSLATION_Y], data[TRANSLATION_Z] );
    }

    public Matrix4f setToIdentity () {
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

        return this;
    }

    public Matrix4f addTranslation ( float x, float y, float z ) {
        data[TRANSLATION_X] += x;
        data[TRANSLATION_Y] += y;
        data[TRANSLATION_Z] += z;

        return this;
    }

    public Matrix4f addTranslation ( float... translation ) {
        return addTranslation( translation[0], translation[1], translation[2] );
    }

    public Matrix4f addTranslation ( VectorFloat vectorFloat ) {
        return addTranslation( vectorFloat.data );
    }

    public Matrix4f setTranslation ( float x, float y, float z ) {
        data[TRANSLATION_X] = x;
        data[TRANSLATION_Y] = y;
        data[TRANSLATION_Z] = z;
        return this;
    }

    public Matrix4f setTranslation ( float... translation ) {
        return setTranslation( translation[0], translation[1], translation[2] );
    }

    public Matrix4f setTranslation ( VectorFloat vectorFloat ) {
        return setTranslation( vectorFloat.data );
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
                f = FloatUtils.cot( degFovY * FloatUtils.PI / 360f ),
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

    public Matrix4f setToLookAt (
            float eyeX, float eyeY, float eyeZ, float centerX, float centerY, float centerZ ) {
        setToLookAt( eyeX, eyeY, eyeZ, centerX, centerY, centerZ, data );
        return this;
    }

    public Matrix4f setToLookAt (
            Vector3f eye, Vector3f center ) {
        float[] eyeData = eye.data, centerData = center.data;
        setToLookAt( eyeData[0], eyeData[1], eyeData[2], centerData[0], centerData[1], centerData[2], data );
        return this;
    }

    public Matrix4f setToLookAt (
            float eyeX, float eyeY, float eyeZ, float centerX, float centerY, float centerZ, float upX, float upY, float upZ ) {
        setToLookAt( eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ, data );
        return this;
    }

    public Matrix4f setToLookAt (
            Vector3f eye, Vector3f center, Vector3f up ) {
        float[] eyeData = eye.data, centerData = center.data, upData = up.data;
        setToLookAt( eyeData[0], eyeData[1], eyeData[2], centerData[0], centerData[1], centerData[2], upData[0], upData[1], upData[2], data );
        return this;
    }

    public static float[] setToLookAt ( float[] eye, float[] center, float[] up, float[] output ) {
        return setToLookAt( eye[0], eye[1], eye[2], center[0], center[1], center[2], up[0], up[1], up[2], output );
    }

    public static float[] setToLookAt (
            float eyeX, float eyeY, float eyeZ, float centerX, float centerY, float centerZ, float upX, float upY, float upZ,
            float[] output ) {
        float //
                dirX = eyeX - centerX,
                dirY = eyeY - centerY,
                dirZ = eyeZ - centerZ;
        float invDirLength = 1.0f / FloatUtils.sqrt( dirX * dirX + dirY * dirY + dirZ * dirZ );
        dirX *= invDirLength;
        dirY *= invDirLength;
        dirZ *= invDirLength;

        float // right = dir x up
                rightX = dirZ * upY - dirY * upZ,
                rightY = dirX * upZ - dirZ * upX,
                rightZ = dirY * upX - dirX * upY;
        float invRightLength = 1.0f / FloatUtils.sqrt( rightX * rightX + rightY * rightY + rightZ * rightZ );
        rightX *= invRightLength;
        rightY *= invRightLength;
        rightZ *= invRightLength;

        float // up2 = right x dir
                up2X = rightZ * dirY - rightY * dirZ,
                up2Y = rightX * dirZ - rightZ * dirX,
                up2Z = rightY * dirX - rightX * dirY;

        output[0] = rightX;
        output[1] = up2X;
        output[2] = dirX;
        output[3] = 0;

        output[4] = rightY;
        output[5] = up2Y;
        output[6] = dirY;
        output[7] = 0;

        output[8] = rightZ;
        output[9] = up2Z;
        output[10] = dirZ;
        output[11] = 0;

        output[12] = -rightX * eyeX - rightY * eyeY - rightZ * eyeZ;
        output[13] = -up2X * eyeX - up2Y * eyeY - up2Z * eyeZ;
        output[14] = -dirX * eyeX - dirY * eyeY - dirZ * eyeZ;
        output[15] = 1;

        return output;
    }

    /**
     Trivialized version with up always being (0,1,0). Slightly faster.

     @param eye
     @param center
     @param output
     @return
     */
    public static float[] setToLookAt ( float[] eye, float[] center, float[] output ) {
        return setToLookAt( eye[0], eye[1], eye[2], center[0], center[1], center[2], output );
    }

    /**
     Trivialized version with up always being (0,1,0). Slightly faster.

     @param eyeX
     @param eyeY
     @param eyeZ
     @param centerX
     @param centerY
     @param centerZ
     @param output
     @return
     */
    public static float[] setToLookAt (
            float eyeX, float eyeY, float eyeZ, float centerX, float centerY, float centerZ,
            float[] output ) {
        float //
                dirX = centerX - eyeX,
                dirY = centerY - eyeY,
                dirZ = centerZ - eyeZ;
        float invDirLength = -1.0f / FloatUtils.sqrt( dirX * dirX + dirY * dirY + dirZ * dirZ );
        dirX *= invDirLength;
        dirY *= invDirLength;
        dirZ *= invDirLength;

        float //
                rightX = dirZ,
                //rightY = 0,
                rightZ = -dirX;
        float invRightLength = 1.0f / FloatUtils.sqrt( rightX * rightX + rightZ * rightZ );
        rightX *= invRightLength;
        //rightY *= invRightLength;
        rightZ *= invRightLength;

        float // up = right x direction
                upnX = rightZ * dirY,
                upnY = rightX * dirZ - rightZ * dirX,
                upnZ = -rightX * dirY;

        output[0] = rightX;
        output[1] = upnX;
        output[2] = dirX;
        output[3] = 0;

        output[4] = 0;
        output[5] = upnY;
        output[6] = dirY;
        output[7] = 0;

        output[8] = rightZ;
        output[9] = upnZ;
        output[10] = dirZ;
        output[11] = 0;

        output[12] = -rightX * eyeX - rightZ * eyeZ;
        output[13] = -upnX * eyeX - upnY * eyeY - upnZ * eyeZ;
        output[14] = -dirX * eyeX - dirY * eyeY - dirZ * eyeZ;
        output[15] = 1;

        return output;
    }

    /**
     Calculates the determinant of the upper 3x3 matrix.
     <p>
     Can be used to calculate det for rot-scale-trans matrices (e.g. OpenGL
     model/view matrices), since their 4th (W) row is [0,0,0,1] by definition.

     @return the determinant of the upper 3x3 matrix.
     */
    public float det3x3 () {
        float //
                /* dataM11 = data[M11], */ dataM21 = data[M21], dataM31 = data[M31], /* dataM41
                 = data[M41],
                 dataM12 = data[M12], */ dataM22 = data[M22], dataM32 = data[M32], /* dataM42 =
                 data[M42],
                 dataM13 = data[M13], */ dataM23 = data[M23], dataM33 = data[M33]; //dataM43 = data[M43],
        //dataM14 = data[M14], dataM24 = data[M24], dataM34 = data[M34], dataM44 = data[M44];
        return data[M11] * ( dataM22 * dataM33 - dataM23 * dataM32 )
                + data[M12] * ( dataM23 * dataM31 - dataM21 * dataM33 )
                + data[M13] * ( dataM21 * dataM32 - dataM22 * dataM31 );
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
                dataM11 = data[M11], dataM21 = data[M21], dataM31 = data[M31], dataM41 = data[M41],
                dataM12 = data[M12], dataM22 = data[M22], dataM32 = data[M32], dataM42 = data[M42],
                dataM13 = data[M13], dataM23 = data[M23], dataM33 = data[M33], dataM43 = data[M43];//,
        //dataM14 = data[M14], dataM24 = data[M24], dataM34 = data[M34], dataM44 = data[M44];
        float //
                ab = dataM33 * dataM42 - dataM32 * dataM43,
                cd = dataM31 * dataM43 - dataM33 * dataM41,
                ef = dataM32 * dataM41 - dataM31 * dataM42,
                hg = dataM13 * dataM21 - dataM11 * dataM23,
                ij = dataM13 * dataM22 - dataM12 * dataM23,
                kl = dataM12 * dataM21 - dataM11 * dataM22;

        return data[M14]
                * ( dataM21 * ab
                + dataM22 * cd
                + dataM23 * ef )
                - data[M24]
                * ( dataM11 * ab
                + dataM12 * cd
                + dataM13 * ef )
                + data[M34]
                * ( dataM41 * ij
                - dataM42 * hg
                + dataM43 * kl )
                - data[M44]
                * ( dataM31 * ij
                - dataM32 * hg
                + dataM33 * kl );
    }

    /**
     Note: if this matrix is known to have a [0,0,0,1] in W row (e.g. model/view
     matrix), it's slightly better performance-wise to simply call det3x3().

     @return the determinant of this matrix
     */
    public float det () {
        return ( data[M14] == 0 && data[M24] == 0 && data[M34] == 0 && data[M44] == 1 ) ? det3x3() : det4x4();
    }

    // note: see http://stackoverflow.com/questions/18810505/ for rationale of using stack variables instead of local helper arrays
    // OTOH, a static helper would break threading etc.
    // tl;dr it's a JVM 7 feature, so it certainly *can't* be taken for granted (older VMs doesn't do this, other VMs may or may not do it)
    /**
     @param data
     @param output has to be != m1 && != m2, otherwise UB
     @return
     */
    public static float[] transpose ( float[] data, float[] output ) {
        output[M11] = data[M11];
        output[M22] = data[M22];
        output[M33] = data[M33];
        output[M44] = data[M44];

        output[M12] = data[M21];
        output[M21] = data[M12];

        output[M13] = data[M31];
        output[M31] = data[M13];

        output[M14] = data[M41];
        output[M41] = data[M14];

        output[M23] = data[M32];
        output[M32] = data[M23];

        output[M24] = data[M42];
        output[M42] = data[M24];

        output[M34] = data[M43];
        output[M43] = data[M34];

        return output;
    }

    public static float[] transpose3x3 ( float[] data, float[] output ) {
        output[M11] = data[M11];
        output[M22] = data[M22];
        output[M33] = data[M33];

        output[M12] = data[M21];
        output[M21] = data[M12];

        output[M13] = data[M31];
        output[M31] = data[M13];

        output[M23] = data[M32];
        output[M32] = data[M23];

        return output;
    }

    public static float[] transpose ( float[] data ) {
        float tmp;

        tmp = data[M12];
        data[M12] = data[M21];
        data[M21] = tmp;

        tmp = data[M13];
        data[M13] = data[M31];
        data[M31] = tmp;

        tmp = data[M14];
        data[M14] = data[M41];
        data[M41] = tmp;

        tmp = data[M23];
        data[M23] = data[M32];
        data[M32] = tmp;

        tmp = data[M24];
        data[M24] = data[M42];
        data[M42] = tmp;

        tmp = data[M34];
        data[M34] = data[M43];
        data[M43] = tmp;

        return data;
    }

    /**
     If source matrix is a MV matrix, this generally results in inverted rotation with preserved scale and translation.

     @param data
     @return
     */
    public static float[] transpose3x3 ( float[] data ) {
        float tmp;

        tmp = data[M12];
        data[M12] = data[M21];
        data[M21] = tmp;

        tmp = data[M13];
        data[M13] = data[M31];
        data[M31] = tmp;

        tmp = data[M23];
        data[M23] = data[M32];
        data[M32] = tmp;

        return data;
    }

    public Matrix4f transpose () {
        transpose( data );
        return this;
    }

    public Matrix4f transpose3x3 () {
        transpose3x3( data );
        return this;
    }

    public Matrix4f transpose ( Matrix4f output ) {
        transpose( data, output.data );
        return output;
    }

    public Matrix4f transpose3x3 ( Matrix4f output ) {
        transpose3x3( data, output.data );
        return output;
    }

    /**
     No inversion happens if the matrix is singular.

     @return det of the input matrix (if 0, no inversion happens); 1.0f / return is the det of the inverted matrix
     */
    public float invert () {
        return invert( data );
    }

    /**
     Matrix is assumed of model-view kind, with fourth row assumed to be (0,0,0,1);
     much faster than regular invert.
     No inversion happens if the matrix is singular.

     @return det of the input matrix (if 0, no inversion happens); 1.0f / return is the det of the inverted matrix
     */
    public float invertMV () {
        return invertMV( data );
    }

    /**
     No inversion happens if the matrix is singular.

     @param output
     @return det of the input matrix (if 0, no inversion happens); 1.0f / return is the det of the inverted matrix
     */
    public float invert ( Matrix4f output ) {
        return invert( data, output.data );
    }

    /**
     Matrix is assumed of model-view kind, with fourth row assumed to be (0,0,0,1);
     much faster than regular invert.
     No inversion happens if the matrix is singular.

     @param output
     @return det of the input matrix (if 0, no inversion happens); 1.0f / return is the det of the inverted matrix
     */
    public float invertMV ( Matrix4f output ) {
        return invertMV( data, output.data );
    }

    /**
     No inversion happens if the matrix is singular.

     @param matrix
     @return det of the input matrix (if 0, no inversion happens); 1.0f / return is the det of the inverted matrix
     */
    public static float invert ( float[] matrix ) {
        return invert( matrix, matrix );
    }

    /**
     No inversion happens if the matrix is singular.

     @param matrix
     @param output can be == data (values are cached, no UB here)
     @return det of the input matrix (if 0, no inversion happens); 1.0f / return is the det of the inverted matrix
     */
    public static float invert ( float[] matrix, float[] output ) {
        float //
                dataM11 = matrix[M11], dataM21 = matrix[M21], dataM31 = matrix[M31], dataM41 = matrix[M41],
                dataM12 = matrix[M12], dataM22 = matrix[M22], dataM32 = matrix[M32], dataM42 = matrix[M42],
                dataM13 = matrix[M13], dataM23 = matrix[M23], dataM33 = matrix[M33], dataM43 = matrix[M43],
                dataM14 = matrix[M14], dataM24 = matrix[M24], dataM34 = matrix[M34], dataM44 = matrix[M44];

        float //
                a = dataM11 * dataM22 - dataM12 * dataM21,
                b = dataM11 * dataM23 - dataM13 * dataM21,
                c = dataM11 * dataM24 - dataM14 * dataM21,
                d = dataM12 * dataM23 - dataM13 * dataM22,
                e = dataM12 * dataM24 - dataM14 * dataM22,
                f = dataM13 * dataM24 - dataM14 * dataM23,
                g = dataM31 * dataM42 - dataM32 * dataM41,
                h = dataM31 * dataM43 - dataM33 * dataM41,
                i = dataM31 * dataM44 - dataM34 * dataM41,
                j = dataM32 * dataM43 - dataM33 * dataM42,
                k = dataM32 * dataM44 - dataM34 * dataM42,
                l = dataM33 * dataM44 - dataM34 * dataM43;

        float det1 = a * l - b * k + c * j + d * i - e * h + f * g;
        if ( det1 == 0 ) {
            return 0;
        }

        float det2 = 1.0f / det1;

        a *= det2;
        b *= det2;
        c *= det2;
        d *= det2;
        e *= det2;
        f *= det2;
        g *= det2;
        h *= det2;
        i *= det2;
        j *= det2;
        k *= det2;
        l *= det2;

        output[0] = dataM22 * l - dataM23 * k + dataM24 * j;
        output[1] = dataM13 * k - dataM14 * j - dataM12 * l;
        output[2] = dataM42 * f - dataM43 * e + dataM44 * d;
        output[3] = dataM33 * e - dataM34 * d - dataM32 * f;
        output[4] = dataM23 * i - dataM24 * h - dataM21 * l;
        output[5] = dataM11 * l - dataM13 * i + dataM14 * h;
        output[6] = dataM43 * c - dataM44 * b - dataM41 * f;
        output[7] = dataM31 * f - dataM33 * c + dataM34 * b;
        output[8] = dataM21 * k - dataM22 * i + dataM24 * g;
        output[9] = dataM12 * i - dataM14 * g - dataM11 * k;
        output[10] = dataM41 * e - dataM42 * c + dataM44 * a;
        output[11] = dataM32 * c - dataM34 * a - dataM31 * e;
        output[12] = dataM22 * h - dataM23 * g - dataM21 * j;
        output[13] = dataM11 * j - dataM12 * h + dataM13 * g;
        output[14] = dataM42 * b - dataM43 * a - dataM41 * d;
        output[15] = dataM31 * d - dataM32 * b + dataM33 * a;

        return det1;
    }

    /**
     Matrix is assumed of model-view kind, with fourth row assumed to be (0,0,0,1);
     much faster than regular invert.
     No inversion happens if the matrix is singular.

     @param matrix
     @return det of the input matrix (if 0, no inversion happens); 1.0f / return is the det of the inverted matrix
     */
    public static float invertMV ( float[] matrix ) {
        float //
                dataM11 = matrix[M11], dataM21 = matrix[M21], dataM31 = matrix[M31], dataM41 = matrix[M41],
                dataM12 = matrix[M12], dataM22 = matrix[M22], dataM32 = matrix[M32], dataM42 = matrix[M42],
                dataM13 = matrix[M13], dataM23 = matrix[M23], dataM33 = matrix[M33], dataM43 = matrix[M43];//,
        //dataM14 = data[M14], dataM24 = data[M24], dataM34 = data[M34], dataM44 = data[M44];

        float //
                a = dataM11 * dataM22 - dataM12 * dataM21,
                b = dataM11 * dataM23 - dataM13 * dataM21,
                //c = 0,
                d = dataM12 * dataM23 - dataM13 * dataM22,
                //e = 0,
                //f = 0,
                g = dataM31 * dataM42 - dataM32 * dataM41,
                h = dataM31 * dataM43 - dataM33 * dataM41,
                //i = dataM31,
                j = dataM32 * dataM43 - dataM33 * dataM42;//,
        //k = dataM32,
        //l = dataM33;

        float det1 = a * dataM33 - b * dataM32 + d * dataM31;
        if ( det1 == 0 ) {
            return 0;
        }
        float det2 = 1.0f / det1;

        a *= det2;
        b *= det2;
        //c *= det;
        d *= det2;
        //e *= det;
        //f *= det;
        g *= det2;
        h *= det2;
        //i *= det;
        j *= det2;
        //k *= det;
        //l *= det;
        dataM31 *= det2;
        dataM32 *= det2;
        dataM33 *= det2;

        matrix[0] = dataM22 * dataM33 - dataM23 * dataM32;
        matrix[1] = dataM13 * dataM32 - dataM12 * dataM33;
        matrix[2] = d;
        //data[3] = 0;
        matrix[4] = dataM23 * dataM31 - dataM21 * dataM33;
        matrix[5] = dataM11 * dataM33 - dataM13 * dataM31;
        matrix[6] = -b;
        //data[7] = 0;
        matrix[8] = dataM21 * dataM32 - dataM22 * dataM31;
        matrix[9] = dataM12 * dataM31 - dataM11 * dataM32;
        matrix[10] = a;
        //data[11] = 0;
        matrix[12] = dataM22 * h - dataM23 * g - dataM21 * j;
        matrix[13] = dataM11 * j - dataM12 * h + dataM13 * g;
        matrix[14] = dataM42 * b - dataM43 * a - dataM41 * d;
        //data[15] = 1;

        return det1;
    }

    /**
     Matrix is assumed of model-view kind, with fourth row assumed to be (0,0,0,1);
     much faster than regular invert.
     No inversion happens if the matrix is singular.

     @param matrix
     @param output can be == data (values are cached, no UB here)
     @return det of the input matrix (if 0, no inversion happens); 1.0f / return is the det of the inverted matrix
     */
    public static float invertMV ( float[] matrix, float[] output ) {
        float //
                dataM11 = matrix[M11], dataM21 = matrix[M21], dataM31 = matrix[M31], dataM41 = matrix[M41],
                dataM12 = matrix[M12], dataM22 = matrix[M22], dataM32 = matrix[M32], dataM42 = matrix[M42],
                dataM13 = matrix[M13], dataM23 = matrix[M23], dataM33 = matrix[M33], dataM43 = matrix[M43];//,
        //dataM14 = data[M14], dataM24 = data[M24], dataM34 = data[M34], dataM44 = data[M44];

        float //
                a = dataM11 * dataM22 - dataM12 * dataM21,
                b = dataM11 * dataM23 - dataM13 * dataM21,
                //c = 0,
                d = dataM12 * dataM23 - dataM13 * dataM22,
                //e = 0,
                //f = 0,
                g = dataM31 * dataM42 - dataM32 * dataM41,
                h = dataM31 * dataM43 - dataM33 * dataM41,
                //i = dataM31,
                j = dataM32 * dataM43 - dataM33 * dataM42;//,
        //k = dataM32,
        //l = dataM33;

        float det1 = a * dataM33 - b * dataM32 + d * dataM31;
        if ( det1 == 0 ) {
            return 0;
        }
        float det2 = 1.0f / det1;

        a *= det2;
        b *= det2;
        //c *= det;
        d *= det2;
        //e *= det;
        //f *= det;
        g *= det2;
        h *= det2;
        //i *= det;
        j *= det2;
        //k *= det;
        //l *= det;
        dataM31 *= det2;
        dataM32 *= det2;
        dataM33 *= det2;

        output[0] = dataM22 * dataM33 - dataM23 * dataM32;
        output[1] = dataM13 * dataM32 - dataM12 * dataM33;
        output[2] = d;
        output[3] = 0;
        output[4] = dataM23 * dataM31 - dataM21 * dataM33;
        output[5] = dataM11 * dataM33 - dataM13 * dataM31;
        output[6] = -b;
        output[7] = 0;
        output[8] = dataM21 * dataM32 - dataM22 * dataM31;
        output[9] = dataM12 * dataM31 - dataM11 * dataM32;
        output[10] = a;
        output[11] = 0;
        output[12] = dataM22 * h - dataM23 * g - dataM21 * j;
        output[13] = dataM11 * j - dataM12 * h + dataM13 * g;
        output[14] = dataM42 * b - dataM43 * a - dataM41 * d;
        output[15] = 1;

        return det1;
    }

    public Vector4f multiply ( Vector4f v ) {
        float[] sourceData = v.data;
        multiply( data, sourceData[0], sourceData[1], sourceData[2], sourceData[3], sourceData );
        return v;
    }

    public Vector4f multiply ( Vector4f source, Vector4f output ) {
        float[] sourceData = source.data;
        multiply( data, sourceData[0], sourceData[1], sourceData[2], sourceData[3], output.data );
        return output;
    }

    public Vector3f multiplyMV ( Vector3f v ) {
        float[] sourceData = v.data;
        multiplyMV( data, sourceData[0], sourceData[1], sourceData[2], sourceData );
        return v;
    }

    public Vector3f multiplyMV ( Vector3f source, Vector3f output ) {
        float[] sourceData = source.data;
        multiplyMV( data, sourceData[0], sourceData[1], sourceData[2], output.data );
        return output;
    }

    /**
     Does a self-mutable left side multiplication of this matrix, i.e. this = this M2

     @param m
     @return this matrix for chaining
     */
    public Matrix4f multiply ( Matrix4f m ) {
        multiply( data, m.data );
        return this;
    }

    /**
     Does a self-mutable right side multiplication of this matrix, i.e. this = M1 this

     @param m
     @return this matrix for chaining
     */
    public Matrix4f multiplyLeft ( Matrix4f m ) {
        multiplyLeft( data, m.data );
        return this;
    }

    /**
     Does a non-mutating multiplication, i.e. output = this M2

     @param m
     @param output
     @return output for chaining
     */
    public Matrix4f multiply ( Matrix4f m, Matrix4f output ) {
        multiply( data, m.data, output.data );
        return output;
    }

    /**
     Does a self-mutable left side multiplication of this matrix, i.e. this = this M2

     @param m
     @return this matrix for chaining
     */
    public Matrix4f multiplyMV ( Matrix4f m ) {
        multiplyMV( data, m.data );
        return this;
    }

    /**
     Does a self-mutable right side multiplication of this matrix, i.e. this = M1 this
     <p>
     Both matrices are assumed of model-view kind, with fourth row assumed to be (0,0,0,1);
     this is about 25% faster than regular matrix multiply.

     @param m
     @return this matrix for chaining
     */
    public Matrix4f multiplyLeftMV ( Matrix4f m ) {
        multiplyLeftMV( data, m.data );
        return this;
    }

    /**
     Does a non-mutating multiplication, i.e. output = this M2
     <p>
     Both matrices are assumed of model-view kind, with fourth row assumed to be (0,0,0,1);
     this is about 25% faster than regular matrix multiply.

     @param m
     @param output
     @return output for chaining
     */
    public Matrix4f multiplyMV ( Matrix4f m, Matrix4f output ) {
        multiplyMV( data, m.data, output.data );
        return output;
    }

    /**
     Multiplies a given matrix by (x,y,z,w) vector, essentially transforming the vector by a transformation matrix.

     @param matrix
     @param x
     @param y
     @param z
     @param w
     @param output
     @return
     */
    public static float[] multiply ( float[] matrix, float x, float y, float z, float w, float[] output ) {
        output[0] = matrix[M11] * x + matrix[M21] * y + matrix[M31] * z + matrix[M41] * w;
        output[1] = matrix[M12] * x + matrix[M22] * y + matrix[M32] * z + matrix[M42] * w;
        output[2] = matrix[M13] * x + matrix[M23] * y + matrix[M33] * z + matrix[M43] * w;
        output[3] = matrix[M14] * x + matrix[M24] * y + matrix[M34] * z + matrix[M44] * w;
        return output;
    }

    public static float[] multiplyMV ( float[] matrix, float x, float y, float z, float[] output ) {
        output[0] = matrix[M11] * x + matrix[M21] * y + matrix[M31] * z + matrix[M41];
        output[1] = matrix[M12] * x + matrix[M22] * y + matrix[M32] * z + matrix[M42];
        output[2] = matrix[M13] * x + matrix[M23] * y + matrix[M33] * z + matrix[M43];
        return output;
    }

    /**
     Does a non-mutating multiplication, i.e. output = M1 M2

     @param m2
     @param m1
     @param output can be == m1 (values are cached, no UB here)
     @return output for chaining
     */
    public static float[] multiply ( float[] m1, float[] m2, float[] output ) {
        float //
                m1M11 = m1[M11], m1M21 = m1[M21], m1M31 = m1[M31], m1M41 = m1[M41],
                m1M12 = m1[M12], m1M22 = m1[M22], m1M32 = m1[M32], m1M42 = m1[M42],
                m1M13 = m1[M13], m1M23 = m1[M23], m1M33 = m1[M33], m1M43 = m1[M43],
                m1M14 = m1[M14], m1M24 = m1[M24], m1M34 = m1[M34], m1M44 = m1[M44];
        float //
                m2M11 = m2[M11], m2M21 = m2[M21], m2M31 = m2[M31], m2M41 = m2[M41],
                m2M12 = m2[M12], m2M22 = m2[M22], m2M32 = m2[M32], m2M42 = m2[M42],
                m2M13 = m2[M13], m2M23 = m2[M23], m2M33 = m2[M33], m2M43 = m2[M43],
                m2M14 = m2[M14], m2M24 = m2[M24], m2M34 = m2[M34], m2M44 = m2[M44];

        output[0] = m1M11 * m2M11 + m1M21 * m2M12 + m1M31 * m2M13 + m1M41 * m2M14;
        output[1] = m1M12 * m2M11 + m1M22 * m2M12 + m1M32 * m2M13 + m1M42 * m2M14;
        output[2] = m1M13 * m2M11 + m1M23 * m2M12 + m1M33 * m2M13 + m1M43 * m2M14;
        output[3] = m1M14 * m2M11 + m1M24 * m2M12 + m1M34 * m2M13 + m1M44 * m2M14;

        output[4] = m1M11 * m2M21 + m1M21 * m2M22 + m1M31 * m2M23 + m1M41 * m2M24;
        output[5] = m1M12 * m2M21 + m1M22 * m2M22 + m1M32 * m2M23 + m1M42 * m2M24;
        output[6] = m1M13 * m2M21 + m1M23 * m2M22 + m1M33 * m2M23 + m1M43 * m2M24;
        output[7] = m1M14 * m2M21 + m1M24 * m2M22 + m1M34 * m2M23 + m1M44 * m2M24;

        output[8] = m1M11 * m2M31 + m1M21 * m2M32 + m1M31 * m2M33 + m1M41 * m2M34;
        output[9] = m1M12 * m2M31 + m1M22 * m2M32 + m1M32 * m2M33 + m1M42 * m2M34;
        output[10] = m1M13 * m2M31 + m1M23 * m2M32 + m1M33 * m2M33 + m1M43 * m2M34;
        output[11] = m1M14 * m2M31 + m1M24 * m2M32 + m1M34 * m2M33 + m1M44 * m2M34;

        output[12] = m1M11 * m2M41 + m1M21 * m2M42 + m1M31 * m2M43 + m1M41 * m2M44;
        output[13] = m1M12 * m2M41 + m1M22 * m2M42 + m1M32 * m2M43 + m1M42 * m2M44;
        output[14] = m1M13 * m2M41 + m1M23 * m2M42 + m1M33 * m2M43 + m1M43 * m2M44;
        output[15] = m1M14 * m2M41 + m1M24 * m2M42 + m1M34 * m2M43 + m1M44 * m2M44;

        return output;
    }

    /**
     Does a non-mutating multiplication, i.e. output = M1 M2
     <p>
     Both matrices are assumed of model-view kind, with fourth row assumed to be (0,0,0,1);
     this is about 25% faster than regular matrix multiply.

     @param m2
     @param m1
     @param output can be == m1 (values are cached, no UB here)
     @return output for chaining
     */
    public static float[] multiplyMV ( float[] m1, float[] m2, float[] output ) {
        float //
                m1M11 = m1[M11], m1M21 = m1[M21], m1M31 = m1[M31], m1M41 = m1[M41],
                m1M12 = m1[M12], m1M22 = m1[M22], m1M32 = m1[M32], m1M42 = m1[M42],
                m1M13 = m1[M13], m1M23 = m1[M23], m1M33 = m1[M33], m1M43 = m1[M43];//,
        //m1M14 = m1[M14], m1M24 = m1[M24], m1M34 = m1[M34], m1M44 = m1[M44];
        float //
                m2M11 = m2[M11], m2M21 = m2[M21], m2M31 = m2[M31], m2M41 = m2[M41],
                m2M12 = m2[M12], m2M22 = m2[M22], m2M32 = m2[M32], m2M42 = m2[M42],
                m2M13 = m2[M13], m2M23 = m2[M23], m2M33 = m2[M33], m2M43 = m2[M43];//,
        //m2M14 = m2[M14], m2M24 = m2[M24], m2M34 = m2[M34], m2M44 = m2[M44];

        output[0] = m1M11 * m2M11 + m1M21 * m2M12 + m1M31 * m2M13;
        output[1] = m1M12 * m2M11 + m1M22 * m2M12 + m1M32 * m2M13;
        output[2] = m1M13 * m2M11 + m1M23 * m2M12 + m1M33 * m2M13;
        output[3] = 0;

        output[4] = m1M11 * m2M21 + m1M21 * m2M22 + m1M31 * m2M23;
        output[5] = m1M12 * m2M21 + m1M22 * m2M22 + m1M32 * m2M23;
        output[6] = m1M13 * m2M21 + m1M23 * m2M22 + m1M33 * m2M23;
        output[7] = 0;

        output[8] = m1M11 * m2M31 + m1M21 * m2M32 + m1M31 * m2M33;
        output[9] = m1M12 * m2M31 + m1M22 * m2M32 + m1M32 * m2M33;
        output[10] = m1M13 * m2M31 + m1M23 * m2M32 + m1M33 * m2M33;
        output[11] = 0;

        output[12] = m1M11 * m2M41 + m1M21 * m2M42 + m1M31 * m2M43 + m1M41;
        output[13] = m1M12 * m2M41 + m1M22 * m2M42 + m1M32 * m2M43 + m1M42;
        output[14] = m1M13 * m2M41 + m1M23 * m2M42 + m1M33 * m2M43 + m1M43;
        output[15] = 1;

        return output;
    }

    /**
     Does a self-mutable right side multiplication of m1, i.e. M1 = M1 M2

     @param m1 both source left matrix and output target
     @param m2 right matrix
     @return m1 for chaining
     */
    public static float[] multiply ( float[] m1, float[] m2 ) {
        return multiply( m1, m2, m1 );
    }

    /**
     Does a self-mutable left side multiplication of m1, i.e. M1 = M2 M1

     @param m1 both source left matrix and output target
     @param m2 right matrix
     @return m1 for chaining
     */
    public static float[] multiplyLeft ( float[] m1, float[] m2 ) {
        return multiply( m2, m1, m1 );
    }

    /**
     Does a self-mutable right side multiplication of m1, i.e. M1 = M1 M2
     <p>
     Both matrices are assumed of model-view kind, with fourth row assumed to be (0,0,0,1);
     this is about 25% faster than regular matrix multiply.

     @param m1 both source left matrix and output target
     @param m2 right matrix
     @return m1 for chaining
     */
    public static float[] multiplyMV ( float[] m1, float[] m2 ) {
        float //
                m1M11 = m1[M11], m1M21 = m1[M21], m1M31 = m1[M31], // m1M41 = m1[M41],
                m1M12 = m1[M12], m1M22 = m1[M22], m1M32 = m1[M32], //m1M42 = m1[M42],
                m1M13 = m1[M13], m1M23 = m1[M23], m1M33 = m1[M33];//, m1M43 = m1[M43],
        //m1M14 = m1[M14], m1M24 = m1[M24], m1M34 = m1[M34], m1M44 = m1[M44];
        float //
                m2M11 = m2[M11], m2M21 = m2[M21], m2M31 = m2[M31], m2M41 = m2[M41],
                m2M12 = m2[M12], m2M22 = m2[M22], m2M32 = m2[M32], m2M42 = m2[M42],
                m2M13 = m2[M13], m2M23 = m2[M23], m2M33 = m2[M33], m2M43 = m2[M43];//,
        //m2M14 = m2[M14], m2M24 = m2[M24], m2M34 = m2[M34], m2M44 = m2[M44];

        m1[0] = m1M11 * m2M11 + m1M21 * m2M12 + m1M31 * m2M13;
        m1[1] = m1M12 * m2M11 + m1M22 * m2M12 + m1M32 * m2M13;
        m1[2] = m1M13 * m2M11 + m1M23 * m2M12 + m1M33 * m2M13;
        //m1[3] = 0;

        m1[4] = m1M11 * m2M21 + m1M21 * m2M22 + m1M31 * m2M23;
        m1[5] = m1M12 * m2M21 + m1M22 * m2M22 + m1M32 * m2M23;
        m1[6] = m1M13 * m2M21 + m1M23 * m2M22 + m1M33 * m2M23;
        //m1[7] = 0;

        m1[8] = m1M11 * m2M31 + m1M21 * m2M32 + m1M31 * m2M33;
        m1[9] = m1M12 * m2M31 + m1M22 * m2M32 + m1M32 * m2M33;
        m1[10] = m1M13 * m2M31 + m1M23 * m2M32 + m1M33 * m2M33;
        //m1[11] = 0;

        m1[12] += m1M11 * m2M41 + m1M21 * m2M42 + m1M31 * m2M43;
        m1[13] += m1M12 * m2M41 + m1M22 * m2M42 + m1M32 * m2M43;
        m1[14] += m1M13 * m2M41 + m1M23 * m2M42 + m1M33 * m2M43;
        //m1[15] = 1;

        return m1;
    }

    /**
     Does a self-mutable left side multiplication of m1, i.e. M1 = M2 M1
     <p>
     Both matrices are assumed of model-view kind, with fourth row assumed to be (0,0,0,1);
     this is about 25% faster than regular matrix multiply.

     @param m1 both source left matrix and output target
     @param m2 right matrix
     @return m1 for chaining
     */
    public static float[] multiplyLeftMV ( float[] m1, float[] m2 ) {
        float //
                m1M11 = m1[M11], m1M21 = m1[M21], m1M31 = m1[M31], m1M41 = m1[M41],
                m1M12 = m1[M12], m1M22 = m1[M22], m1M32 = m1[M32], m1M42 = m1[M42],
                m1M13 = m1[M13], m1M23 = m1[M23], m1M33 = m1[M33], m1M43 = m1[M43];//,
        //m1M14 = m1[M14], m1M24 = m1[M24], m1M34 = m1[M34], m1M44 = m1[M44];
        float //
                m2M11 = m2[M11], m2M21 = m2[M21], m2M31 = m2[M31], m2M41 = m2[M41],
                m2M12 = m2[M12], m2M22 = m2[M22], m2M32 = m2[M32], m2M42 = m2[M42],
                m2M13 = m2[M13], m2M23 = m2[M23], m2M33 = m2[M33], m2M43 = m2[M43];//,
        //m2M14 = m2[M14], m2M24 = m2[M24], m2M34 = m2[M34], m2M44 = m2[M44];

        m1[0] = m2M11 * m1M11 + m2M21 * m1M12 + m2M31 * m1M13;
        m1[1] = m2M12 * m1M11 + m2M22 * m1M12 + m2M32 * m1M13;
        m1[2] = m2M13 * m1M11 + m2M23 * m1M12 + m2M33 * m1M13;
        //m1[3] = 0;

        m1[4] = m2M11 * m1M21 + m2M21 * m1M22 + m2M31 * m1M23;
        m1[5] = m2M12 * m1M21 + m2M22 * m1M22 + m2M32 * m1M23;
        m1[6] = m2M13 * m1M21 + m2M23 * m1M22 + m2M33 * m1M23;
        //m1[7] = 0;

        m1[8] = m2M11 * m1M31 + m2M21 * m1M32 + m2M31 * m1M33;
        m1[9] = m2M12 * m1M31 + m2M22 * m1M32 + m2M32 * m1M33;
        m1[10] = m2M13 * m1M31 + m2M23 * m1M32 + m2M33 * m1M33;
        //m1[11] = 0;

        m1[12] = m2M11 * m1M41 + m2M21 * m1M42 + m2M31 * m1M43 + m2M41;
        m1[13] = m2M12 * m1M41 + m2M22 * m1M42 + m2M32 * m1M43 + m2M42;
        m1[14] = m2M13 * m1M41 + m2M23 * m1M42 + m2M33 * m1M43 + m2M43;
        //m1[15] = 1;

        return m1;
    }
}
