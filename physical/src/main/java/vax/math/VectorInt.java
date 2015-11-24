package vax.math;

import java.util.Arrays;

public class VectorInt {
    protected int[] data;

    public VectorInt ( int requiredSize ) {
        this.data = new int[requiredSize];
    }

    public VectorInt ( int... data ) {
        this.data = data;
    }

    public VectorInt ( int requiredSize, int[] data ) {
        if ( data.length < requiredSize ) {
            throw new IllegalStateException();
        }
        this.data = data;
    }

    public VectorInt ( VectorInt vector ) {
        int len = vector.data.length;
        this.data = new int[len];
        System.arraycopy( vector.data, 0, this.data, 0, len );
    }

    public int[] getData () {
        return data;
    }

    @Deprecated
    public void setData ( int[] data ) {
        this.data = data;
    }

    public final void set ( int... data ) {
        System.arraycopy( data, 0, this.data, 0, Math.min( this.data.length, data.length ) );
    }

    public final void set ( VectorInt vector ) {
        System.arraycopy( vector.data, 0, this.data, 0, Math.min( this.data.length, vector.data.length ) );
    }

    public void rotate ( int offset ) {
        if ( offset == 0 ) {
            return;
        }
        offset %= data.length;
        int absOffset = Math.abs( offset ), len2 = data.length - absOffset;
        int[] tmp = new int[absOffset];
        if ( offset > 0 ) {
            System.arraycopy( data, len2, tmp, 0, absOffset );
            System.arraycopy( data, 0, data, absOffset, len2 );
            System.arraycopy( tmp, 0, data, 0, absOffset );
        } else {
            System.arraycopy( data, 0, tmp, 0, absOffset );
            System.arraycopy( data, absOffset, data, 0, len2 );
            System.arraycopy( tmp, 0, data, len2, absOffset );
        }
    }

    public int getValue ( int index ) {
        return data[index];
    }

    public void setValue ( int value ) {
        for( int i = data.length - 1; i >= 0; i-- ) {
            data[i] = value;
        }
    }

    public void setValue ( int value, int index ) {
        data[index] = value;
    }

    public void setToZero () {
        setValue( 0 );
    }

    public boolean isZero () {
        for( int i = data.length - 1; i >= 0; i-- ) {
            if ( data[i] != 0 ) {
                return false;
            }
        }
        return true;
    }

    public int lengthSq () {
        return lengthSq( data );
    }

    public int length () {
        return length( data );
    }

    public int dot ( VectorInt vector ) {
        return dot( data, vector.data );
    }

    public VectorInt add ( VectorInt vector ) {
        add( data, vector.data );
        return this;
    }

    public VectorInt subtract ( VectorInt vector ) {
        subtract( data, vector.data );
        return this;
    }

    public VectorInt invert () {
        return scale( -1 );
    }

    public VectorInt scale ( int scaler ) {
        scale( data, scaler );
        return this;
    }

    public VectorInt scale ( VectorInt scaler ) {
        scale( data, scaler.data );
        return this;
    }

    public VectorInt getSum ( VectorInt vector ) {
        return new VectorInt( getSum( data, vector.data ) );
    }

    public VectorInt getDiff ( VectorInt vector ) {
        return new VectorInt( getDiff( data, vector.data ) );
    }

    public VectorInt getScaled ( int scaler ) {
        return new VectorInt( getScaled( data, scaler ) );
    }

    public VectorInt getScaled ( VectorInt scaler ) {
        return new VectorInt( getScaled( data, scaler.data ) );
    }

    public int distance ( VectorInt vector ) {
        return distance( data, vector.data );
    }

    public int distanceSq ( VectorInt vector ) {
        return distanceSq( data, vector.data );
    }

    @Override
    public String toString () {
        return String.join( ",", Arrays.toString( data ) );
    }

    public VectorInt copy () {
        return new VectorInt( this );
    }

    /*
     static methods
     */
    static public int lengthSq ( int... data ) {
        int lenSum = 0;
        for( int i = data.length - 1; i >= 0; i-- ) {
            lenSum += data[i] * data[i];
        }
        return lenSum;
    }

    static public int length ( int... data ) {
        return (int) Math.sqrt( lengthSq( data ) );
    }

    static public int dot ( int[] v1, int[] v2 ) { // simple regardless of dimension count
        int sum = 0;
        for( int i = v1.length - 1; i >= 0; i-- ) {
            sum += v1[i] * v2[i];
        }
        return sum;
    }

    static public int[] add ( int[] v, int[] t ) { // translate with t
        for( int i = v.length - 1; i >= 0; i-- ) {
            v[i] += t[i];
        }
        return v;
    }

    static public int[] subtract ( int[] v, int[] t ) { // translate with -t
        for( int i = v.length - 1; i >= 0; i-- ) {
            v[i] -= t[i];
        }
        return v;
    }

    static public int[] getSum ( int[] v, int[] t ) {
        int len = v.length;
        int[] ret = new int[len];
        for( len--; len >= 0; len-- ) {
            ret[len] = v[len] + t[len];
        }
        return ret;
    }

    static public int[] getDiff ( int[] v, int[] t ) {
        int i = v.length;
        int[] ret = new int[i];
        for( i--; i >= 0; i-- ) {
            ret[i] = v[i] - t[i];
        }
        return ret;
    }

    static public int[] scale ( int[] v, int f ) {
        for( int i = v.length - 1; i >= 0; i-- ) {
            v[i] *= f;
        }
        return v;
    }

    static public int[] scale ( int[] v, int[] f ) {
        for( int i = v.length - 1; i >= 0; i-- ) {
            v[i] *= f[i];
        }
        return v;
    }

    static public int[] getScaled ( int[] v, int f ) {
        int i = v.length;
        int[] ret = new int[i];
        for( i--; i >= 0; i-- ) {
            ret[i] = v[i] * f;
        }
        return ret;
    }

    static public int[] getScaled ( int[] v, int[] f ) {
        int i = v.length;
        int[] ret = new int[i];
        for( i--; i >= 0; i-- ) {
            ret[i] = v[i] * f[i];
        }
        return ret;
    }

    static public int distance ( int[] v1, int[] v2 ) {
        return (int) Math.sqrt( distanceSq( v1, v2 ) );
    }

    static public int distanceSq ( int[] v1, int[] v2 ) {
        int sum = 0;
        for( int i = v1.length - 1; i >= 0; i-- ) {
            int diff = v2[i] - v1[i];
            sum += diff * diff;
        }
        return sum;
    }

    static public int[] getRandomArray ( int length, int min, int max ) {
        int[] ret = new int[length];
        for( int i = 0; i < length; i++ ) {
            ret[i] = MathUtils.nextInt( min, max );
        }
        return ret;
    }

    static public VectorInt getRandom ( int length, int min, int max ) {
        return new VectorInt( getRandomArray( length, min, max ) );
    }

    @Override
    public boolean equals ( Object obj ) {
        if ( obj == null ) {
            return false;
        }
        if ( obj == this ) {
            return true;
        }
        if ( !( obj instanceof VectorInt ) ) {
            return false;
        }
        return Arrays.equals( data, ( (VectorInt) obj ).data );
    }

    @Override
    public int hashCode () {
        return Arrays.hashCode( data );
    }
}
