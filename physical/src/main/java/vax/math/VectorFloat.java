package vax.math;

import java.util.Arrays;

public class VectorFloat {
    protected float[] data;

    public float[] getData () {
        return data;
    }

    public VectorFloat ( int requiredSize ) {
        this.data = new float[requiredSize];
    }

    public VectorFloat ( float... data ) {
        this.data = data;
    }

    public VectorFloat ( int requiredSize, float[] data ) {
        if ( data.length < requiredSize ) {
            throw new IllegalStateException();
        }
        this.data = data;
    }

    public VectorFloat ( VectorFloat vector ) {
        set( vector );
    }

    public final void set ( float... data ) {
        for( int i = Math.min( this.data.length, data.length ) - 1; i >= 0; i-- ) {
            this.data[i] = data[i];
        }
    }

    public final void set ( VectorFloat vector ) {
        float[] src = vector.data;
        for( int i = Math.min( data.length, vector.data.length ) - 1; i >= 0; i-- ) {
            data[i] = src[i];
        }
    }

    // FIXME
    public void rotate ( int offset ) {
        if ( offset == 0 ) {
            return;
        }
        int absOffset = Math.abs( offset ), len2 = data.length - absOffset;
        float[] tmp;
        if ( offset > 0 ) {
            tmp = Arrays.copyOfRange( data, len2, 0 );
            data = Arrays.copyOfRange( data, 0, absOffset );
            data = Arrays.copyOfRange( tmp, 0, 0 );
        } else {
            tmp = Arrays.copyOfRange( data, 0, 0 );
            data = Arrays.copyOfRange( data, absOffset, 0 );
            data = Arrays.copyOfRange( tmp, 0, len2 );
        }
        for( float item : data ) {
            System.out.print( item + " " );
        }
        System.out.println();
    }

    public void setValue ( float value ) {
        for( int i = data.length - 1; i >= 0; i-- ) {
            data[i] = value;
        }
    }

    public void setZero () {
        setValue( (float) 0 );
    }

    public boolean isZero () {
        for( int i = data.length - 1; i >= 0; i-- ) {
            if ( data[i] != 0 ) {
                return false;
            }
        }
        return true;
    }

    public float lengthSq () {
        return lengthSq( data );
    }

    public float length () {
        return length( data );
    }

    public VectorFloat normalize () {
        normalize( data );
        return this;
    }

    public float dot ( VectorFloat vector ) {
        return dot( data, vector.data );
    }

    public VectorFloat add ( VectorFloat vector ) {
        add( data, vector.data );
        return this;
    }

    public VectorFloat subtract ( VectorFloat vector ) {
        subtract( data, vector.data );
        return this;
    }

    public VectorFloat invert () {
        return scale( -1f );
    }

    public VectorFloat getInverted () {
        return getScaled( -1f );
    }

    public VectorFloat scale ( float scaler ) {
        scale( data, scaler );
        return this;
    }

    public VectorFloat scale ( VectorFloat scaler ) {
        scale( data, scaler.data );
        return this;
    }

    public VectorFloat getSum ( VectorFloat vector ) {
        return new VectorFloat( getSum( data, vector.data ) );
    }

    public VectorFloat getDiff ( VectorFloat vector ) {
        return new VectorFloat( getDiff( data, vector.data ) );
    }

    public VectorFloat getScaled ( float scaler ) {
        return new VectorFloat( getScaled( data, scaler ) );
    }

    public VectorFloat getScaled ( VectorFloat scaler ) {
        return new VectorFloat( getScaled( data, scaler.data ) );
    }

    public VectorFloat getNormalized () {
        return new VectorFloat( getNormalized( data ) );
    }

    public float distance ( VectorFloat vector ) {
        return distance( data, vector.data );
    }

    public float distanceSq ( VectorFloat vector ) {
        return distanceSq( data, vector.data );
    }

    public String ToString () {
        return String.join( ",", Arrays.toString( data ) );
    }

    /*
     static methods
     */
    static public float lengthSq ( float... data ) {
        float lenSum = 0;
        for( int i = data.length - 1; i >= 0; i-- ) {
            lenSum += data[i] * data[i];
        }
        return lenSum;
    }

    static public float length ( float... data ) {
        return (float) Math.sqrt( lengthSq( data ) );
    }

    static public void normalize ( float... data ) {
        float lenFactor = 1.0f / length( data );
        for( int i = data.length - 1; i >= 0; i-- ) {
            data[i] *= lenFactor;
        }
        //return this;
    }

    static public float dot ( float[] v1, float[] v2 ) { // simple regardless of dimension count
        float sum = 0;
        for( int i = v1.length - 1; i >= 0; i-- ) {
            sum += v1[i] * v2[i];
        }
        return sum;
    }

    static public float[] add ( float[] v, float[] t ) { // translate with t
        for( int i = v.length - 1; i >= 0; i-- ) {
            v[i] += t[i];
        }
        return v;
    }

    static public float[] subtract ( float[] v, float[] t ) { // translate with -t
        for( int i = v.length - 1; i >= 0; i-- ) {
            v[i] -= t[i];
        }
        return v;
    }

    static public float[] getNormalized ( float... data ) {
        float[] ret = new float[data.length];
        float lenFactor = 1.0f / length( data );
        for( int i = data.length - 1; i >= 0; i-- ) {
            ret[i] = data[i] * lenFactor;
        }
        return ret;
    }

    static public float[] getSum ( float[] v, float[] t ) {
        int len = v.length;
        float[] ret = new float[len];
        for( len--; len >= 0; len-- ) {
            ret[len] = v[len] + t[len];
        }
        return ret;
    }

    static public float[] getDiff ( float[] v, float[] t ) {
        int i = v.length;
        float[] ret = new float[i];
        for( i--; i >= 0; i-- ) {
            ret[i] = v[i] - t[i];
        }
        return ret;
    }

    static public float[] scale ( float[] v, float f ) {
        for( int i = v.length - 1; i >= 0; i-- ) {
            v[i] *= f;
        }
        return v;
    }

    static public float[] scale ( float[] v, float[] f ) {
        for( int i = v.length - 1; i >= 0; i-- ) {
            v[i] *= f[i];
        }
        return v;
    }

    static public float[] getScaled ( float[] v, float f ) {
        int i = v.length;
        float[] ret = new float[i];
        for( i--; i >= 0; i-- ) {
            ret[i] = v[i] * f;
        }
        return ret;
    }

    static public float[] getScaled ( float[] v, float[] f ) {
        int i = v.length;
        float[] ret = new float[i];
        for( i--; i >= 0; i-- ) {
            ret[i] = v[i] * f[i];
        }
        return ret;
    }

    static public float distance ( float[] v1, float[] v2 ) {
        return (float) Math.sqrt( distanceSq( v1, v2 ) );
    }

    static public float distanceSq ( float[] v1, float[] v2 ) {
        float sum = (float) 0;
        for( int i = v1.length - 1; i >= 0; i-- ) {
            float diff = v2[i] - v1[i];
            sum += diff * diff;
        }
        return sum;
    }

    static public Vector3f getRandom ( float min, float max ) {
        return new Vector3f( MathUtils.nextFloat( min, max ), MathUtils.nextFloat( min, max ), MathUtils.nextFloat( min, max ) );
    }

    @Override
    public boolean equals ( Object obj ) {
        if ( obj == null ) {
            return false;
        }
        if ( obj == this ) {
            return true;
        }
        if ( !( obj instanceof VectorFloat ) ) {
            return false;
        }
        return Arrays.equals( data, ( (VectorFloat) obj ).data );
    }

    @Override
    public int hashCode () {
        return Arrays.hashCode( data );
    }
}
