package vax.math;

public class Matrix4f extends VectorFloat {

    public final static int SIZE = 4 * 4;
    
    public final static Matrix4f IDENTITY = new Matrix4f( true );
    
    public float translationX;
    
    public void setTranslationX ( float transX ) {
        this.data[12] = transX;
    }
    
    public float getTranslationX () {
        return data[12];
    }
    
    public float translationY;
    
    public void setTranslationY ( float transY ) {
        this.data[13] = transY;
    }
    
    public float getTranslationY () {
        return data[13];
    }
    
    public float translationZ;
    
    public void setTranslationZ ( float transZ ) {
        this.data[14] = transZ;
    }
    
    public float getTranslationZ () {
        return data[14];
    }
    
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
    
    public Matrix4f ( float[] data ) {
        super( data, SIZE );
    }
    
    public Matrix4f ( Matrix4f matrix4f ) {
        super( matrix4f );
    }
    
    public void setIdentity () {
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
    
    public void addTranslation ( float[] translation ) {
        addTranslation( translation[0], translation[1], translation[2] );
    }
    
    public void addTranslation ( VectorFloat vectorFloat ) {
        addTranslation( vectorFloat.getData() );
    }
    
    public void setTranslation ( float x, float y, float z ) {
        data[12] = x;
        data[13] = y;
        data[14] = z;
    }
    
    public void setTranslation ( float[] translation ) {
            setTranslation( translation[0], translation[1], translation[2] );
        }

    public void setTranslation ( VectorFloat vectorFloat ) {
        setTranslation( vectorFloat.getData() );
    }

    public Vector3f getDisplacement ( Vector3f target ) {
        return new Vector3f( target.getX() - getTranslationX(), target.getY() - getTranslationY(), target.getZ() - getTranslationZ() );
    }

    public Vector3f getDisplacement ( Matrix4f target ) {
        return new Vector3f( target.getTranslationX() - getTranslationX(), target.getTranslationY() - getTranslationY(), target.getTranslationZ() - getTranslationZ() );
    }

    public float translationDistance ( Vector3f v ) {
        return Vector3f.distance(
            getTranslationX(), getTranslationY(), getTranslationZ(),
            v.getX(), v.getY(), v.getZ()
        );
    }

    public float translationDistance ( Matrix4f matrix4 ) {
        return Vector3f.distance(
            getTranslationX(), getTranslationY(), getTranslationZ(),
            matrix4.getTranslationX(), matrix4.getTranslationY(), matrix4.getTranslationZ()
        );
    }

    public float translationDistanceSq ( Matrix4f matrix4 ) {
        return Vector3f.distanceSq(
            getTranslationX(), getTranslationY(), getTranslationZ(),
            matrix4.getTranslationX(), matrix4.getTranslationY(), matrix4.getTranslationZ()
        );
    }

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
    /*
    public void setValue ( float value ) {
        for ( int i = data.Length - 1; i >= 0; i-- )
            data[i] = value;
    }

    public void setZero () {
        setValue( 0 );
    }
*/

    public void set ( Matrix4 matrix4 ) {
        data[0] = matrix4.M11;
        data[1] = matrix4.M12;
        data[2] = matrix4.M13;
        data[3] = matrix4.M14;

        data[4] = matrix4.M21;
        data[5] = matrix4.M22;
        data[6] = matrix4.M23;
        data[7] = matrix4.M24;

        data[8] = matrix4.M31;
        data[9] = matrix4.M32;
        data[10] = matrix4.M33;
        data[11] = matrix4.M34;

        data[12] = matrix4.M41;
        data[13] = matrix4.M42;
        data[14] = matrix4.M43;
        data[15] = matrix4.M44;
    }

    public Matrix4 toMatrix4 () {
        return new Matrix4(
            data[0], data[1], data[2], data[3],
            data[4], data[5], data[6], data[7],
            data[8], data[9], data[10], data[11],
            data[12], data[13], data[14], data[15]
        );
    }
}
