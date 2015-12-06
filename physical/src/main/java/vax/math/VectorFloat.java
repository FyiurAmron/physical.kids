package vax.math;

import java.util.Arrays;

public class VectorFloat {
    protected float[] data;

    public VectorFloat ( int requiredSize ) {
        this.data = new float[requiredSize];
    }

    /**
     Note: if <code>data</code> is an explicit array, this constructor doesn't copy the values provided, but wraps the array instead.

     @param data
     */
    public VectorFloat ( float... data ) {
        this.data = data;
    }

    /**
     Note: this constructor doesn't copy the values provided, but wraps the array instead.

     @param requiredSize
     @param data
     */
    public VectorFloat ( int requiredSize, float[] data ) {
        if ( data.length < requiredSize ) {
            throw new IllegalStateException();
        }
        this.data = data;
    }

    public VectorFloat ( VectorFloat vector ) {
        int len = vector.data.length;
        this.data = new float[len];
        System.arraycopy( vector.data, 0, this.data, 0, len );
    }

    public float[] getData () {
        return data;
    }

    public int getDataLength () {
        return data.length;
    }

    /**
     Note: this method doesn't copy the data into the vector - it forces it to use external array as its data instead.
     Intended for debugging uses.

     @param data
     @return
     //@deprecated
     */
    //@Deprecated
    public VectorFloat setData ( float[] data ) {
        this.data = data;
        return this;
    }

    public final VectorFloat set ( float... data ) {
        System.arraycopy( data, 0, this.data, 0, Math.min( this.data.length, data.length ) );
        return this;
    }

    public final VectorFloat set ( VectorFloat vector ) {
        System.arraycopy( vector.data, 0, this.data, 0, Math.min( this.data.length, vector.data.length ) );
        return this;
    }

    public VectorFloat rotate ( int offset ) {
        if ( offset == 0 ) {
            return this;
        }
        offset %= data.length;
        int absOffset = Math.abs( offset ), len2 = data.length - absOffset;
        float[] tmp = new float[absOffset];
        if ( offset > 0 ) {
            System.arraycopy( data, len2, tmp, 0, absOffset );
            System.arraycopy( data, 0, data, absOffset, len2 );
            System.arraycopy( tmp, 0, data, 0, absOffset );
        } else {
            System.arraycopy( data, 0, tmp, 0, absOffset );
            System.arraycopy( data, absOffset, data, 0, len2 );
            System.arraycopy( tmp, 0, data, len2, absOffset );
        }
        return this;
    }

    public float getValue ( int index ) {
        return data[index];
    }

    public VectorFloat setValue ( float value ) {
        for( int i = data.length - 1; i >= 0; i-- ) {
            data[i] = value;
        }
        return this;
    }

    public VectorFloat setValue ( float value, int index ) {
        data[index] = value;
        return this;
    }

    public VectorFloat setToZero () {
        return setValue( 0 );
    }

    public boolean isZero () {
        for( int i = data.length - 1; i >= 0; i-- ) {
            if ( data[i] != 0 ) {
                return false;
            }
        }
        return true;
    }

    public float calcLengthSq () {
        return calcLengthSq( data );
    }

    public float calcLength () {
        return calcLength( data );
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

    /*
     public VectorFloat reverse ( VectorFloat vector ) { // etc.

     }
     */
    public VectorFloat setToOpposite () {
        return scale( -1f );
    }

    public VectorFloat createOpposite () {
        return VectorFloat.this.createScaled( -1f );
    }

    public VectorFloat scale ( float scaler ) {
        scale( data, scaler );
        return this;
    }

    public VectorFloat scale ( VectorFloat scaler ) {
        scale( data, scaler.data );
        return this;
    }

    public VectorFloat createSum ( VectorFloat vector ) {
        return new VectorFloat( createSum( data, vector.data ) );
    }

    public VectorFloat createDiff ( VectorFloat vector ) {
        return new VectorFloat( createDiff( data, vector.data ) );
    }

    public VectorFloat createScaled ( float scaler ) {
        return new VectorFloat( createScaled( data, scaler ) );
    }

    public VectorFloat createScaled ( VectorFloat scaler ) {
        return new VectorFloat( createScaled( data, scaler.data ) );
    }

    public VectorFloat createNormalized () {
        return new VectorFloat( createNormalized( data ) );
    }

    public float calcDistance ( VectorFloat vector ) {
        return calcDistance( data, vector.data );
    }

    public float calcDistanceSq ( VectorFloat vector ) {
        return calcDistanceSq( data, vector.data );
    }

    @Override
    public String toString () {
        return String.join( ",", Arrays.toString( data ) );
    }

    public VectorFloat copy () {
        return new VectorFloat( this );
    }

    public VectorFloat setToRandom ( float min, float max ) {
        setArrayToRandom( data, min, max );
        return this;
    }

    /*
     static methods
     */
    static public float[] createRandomArray ( int length, float min, float max ) {
        return setArrayToRandom( new float[length], min, max );
    }

    static public float[] setArrayToRandom ( float[] array, float min, float max ) {
        for( int i = array.length - 1; i >= 0; i-- ) {
            array[i] = MathUtils.nextFloat( min, max );
        }
        return array;
    }

    /**
     Note: since this method allocated a new VectorFloat, in looped contexts reusing a vector with setToRandom is recommended.

     @param length
     @param min
     @param max
     @return
     */
    static public VectorFloat createRandom ( int length, float min, float max ) {
        return new VectorFloat( createRandomArray( length, min, max ) );
    }

    static public float calcLengthSq ( float... data ) {
        float lenSum = 0;
        for( int i = data.length - 1; i >= 0; i-- ) {
            lenSum += data[i] * data[i];
        }
        return lenSum;
    }

    static public float calcLength ( float... data ) {
        return (float) Math.sqrt( calcLengthSq( data ) );
    }

    static public float[] normalize ( float... data ) {
        float lenFactor = 1.0f / calcLength( data );
        for( int i = data.length - 1; i >= 0; i-- ) {
            data[i] *= lenFactor;
        }
        return data;
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

    static public float[] createNormalized ( float[] data ) {
        return getNormalized( data, new float[data.length] );
    }

    static public float[] getNormalized ( float[] data, float[] output ) {
        float lenFactor = 1.0f / calcLength( data );
        for( int i = data.length - 1; i >= 0; i-- ) {
            output[i] = data[i] * lenFactor;
        }
        return output;
    }

    static public float[] createSum ( float[] v, float[] t ) {
        return getSum( v, t, new float[v.length] );
    }

    static public float[] getSum ( float[] v, float[] t, float[] output ) {
        for( int len = v.length - 1; len >= 0; len-- ) {
            output[len] = v[len] + t[len];
        }
        return output;
    }

    static public float[] createDiff ( float[] v, float[] t ) {
        return getDiff( v, t, new float[v.length] );
    }

    static public float[] getDiff ( float[] v, float[] t, float[] output ) {
        for( int i = v.length - 1; i >= 0; i-- ) {
            output[i] = v[i] - t[i];
        }
        return output;
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

    static public float[] createScaled ( float[] v, float f ) {
        return getScaled( v, f, new float[v.length] );
    }

    static public float[] getScaled ( float[] v, float f, float[] output ) {
        for( int i = v.length - 1; i >= 0; i-- ) {
            output[i] = v[i] * f;
        }
        return output;
    }

    static public float[] createScaled ( float[] v, float[] f ) {
        return getScaled( v, f, new float[v.length] );
    }

    static public float[] getScaled ( float[] v, float[] f, float[] output ) {
        for( int i = v.length - 1; i >= 0; i-- ) {
            output[i] = v[i] * f[i];
        }
        return output;
    }

    static public float[] createOpposite ( float[] v ) {
        return getOpposite( v, new float[v.length] );
    }

    static public float[] getOpposite ( float[] v, float[] output ) {
        return getScaled( v, -1f, output );
    }

    /**
     Note: this method creates a swap vector on-the-fly and therefore getReverse(), createReverse or reverse(v,swap) is preferred.

     @param v
     @return
     */
    @Deprecated
    static public float[] reverse ( float[] v ) {
        return reverse( v, new float[v.length / 2] );
    }

    static public float[] reverse ( float[] v, float[] swap ) {
        int halfLen = v.length / 2;
        for( int i1 = 0, i2 = v.length - 1; i1 < halfLen; i1++, i2-- ) {
            swap[i1] = v[i2];
        }
        for( int i1 = 0, i2 = v.length - 1; i1 < halfLen; i1++, i2-- ) {
            v[i2] = v[i1];
        }
        System.arraycopy( swap, 0, v, 0, halfLen );
        return v;
    }

    static public float[] createReverse ( float[] v ) {
        return getOpposite( v, new float[v.length] );
    }

    static public float[] getReverse ( float[] v, float[] output ) {
        for( int i1 = 0, i2 = v.length - 1; i2 >= 0; i1++, i2-- ) {
            output[i1] = v[i2];
        }
        return output;
    }

    static public float calcDistance ( float[] v1, float[] v2 ) {
        return (float) Math.sqrt( calcDistanceSq( v1, v2 ) );
    }

    static public float calcDistanceSq ( float[] v1, float[] v2 ) {
        float sum = (float) 0;
        for( int i = v1.length - 1; i >= 0; i-- ) {
            float diff = v2[i] - v1[i];
            sum += diff * diff;
        }
        return sum;
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

    public boolean equals ( VectorFloat v, float epsilon ) {
        int len = data.length;
        float[] data2 = v.data;
        if ( v.data.length != len ) {
            return false;
        }
        for( int i = 0; i < len; i++ ) {
            if ( Math.abs( data[i] - data2[i] ) > epsilon ) {
                return false;
            }
        }
        return true;
    }

    /*
     public boolean equalsWithRatio ( VectorFloat v, float epsilonRatio ) {
     int len = data.calcLength;
     float[] data2 = v.data;
     if ( v.data.calcLength != len ) {
     return false;
     }
     for( int i = 0; i < len; i++ ) {
     if (data[i] == 0 && data2[i] != 0 )
     return false;
     }
     if ( 1.0f - Math.abs( data2[i] / data[i] ) > epsilonRatio )
     return false;
     }
     return true;
     }
     */
    @Override
    public int hashCode () {
        return Arrays.hashCode( data );
    }
}
