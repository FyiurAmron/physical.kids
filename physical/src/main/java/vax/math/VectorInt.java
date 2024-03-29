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

    /**
     @param copyData copies the data to new array if true; creates a wrapper VectorFloat otherwise.
     @param data
     */
    public VectorInt ( boolean copyData, int[] data ) {
        this.data = copyData ? IntUtils.copy( data ) : data;
    }

    /**

     @param requiredSize
     @param data
     */
    public VectorInt ( int requiredSize, int[] data ) {
        if ( data.length < requiredSize ) {
            throw new IllegalStateException();
        }
        this.data = data;
    }

    /**
     Note: this constructor always copies the data from the vector provided.

     @param vector
     */
    public VectorInt ( VectorInt vector ) {
        this( IntUtils.copy( vector.data ) );
    }

    public int[] getData () {
        return data;
    }

    public int getDataLength () {
        return data.length;
    }

    @Deprecated
    public void setData ( int[] data ) {
        this.data = data;
    }

    public final VectorInt set ( int... data ) {
        IntUtils.copy( data, this.data );
        return this;
    }

    public final VectorInt set ( VectorInt vector ) {
        return set( vector.data );
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

    public int calcLengthSq () {
        return calcLengthSq( data );
    }

    public int calcLength () {
        return calcLength( data );
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

    public VectorInt setToOpposite () {
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

    public VectorInt createSum ( VectorInt vector ) {
        return new VectorInt( createSum( data, vector.data ) );
    }

    public VectorInt createDiff ( VectorInt vector ) {
        return new VectorInt( createDiff( data, vector.data ) );
    }

    public VectorInt createScaled ( int scaler ) {
        return new VectorInt( getScaled( data, scaler ) );
    }

    public VectorInt createScaled ( VectorInt scaler ) {
        return new VectorInt( getScaled( data, scaler.data ) );
    }

    public int calcDistance ( VectorInt vector ) {
        return calcDistance( data, vector.data );
    }

    public int calcDistanceSq ( VectorInt vector ) {
        return calcDistanceSq( data, vector.data );
    }

    public VectorInt setToRandom ( int min, int max ) {
        setArrayToRandom( data, min, max );
        return this;
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
    static public int calcLengthSq ( int... data ) {
        int lenSum = 0;
        for( int i = data.length - 1; i >= 0; i-- ) {
            lenSum += data[i] * data[i];
        }
        return lenSum;
    }

    static public int calcLength ( int... data ) {
        return (int) Math.sqrt( calcLengthSq( data ) );
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

    static public int[] createSum ( int[] v, int[] t ) {
        return getSum( v, t, new int[v.length] );
    }

    static public int[] getSum ( int[] v, int[] t, int[] output ) {
        for( int i = v.length; i >= 0; i-- ) {
            output[i] = v[i] + t[i];
        }
        return output;
    }

    static public int[] createDiff ( int[] v, int[] t ) {
        return getDiff( v, t, new int[v.length] );
    }

    static public int[] getDiff ( int[] v, int[] t, int[] output ) {
        for( int i = v.length - 1; i >= 0; i-- ) {
            output[i] = v[i] - t[i];
        }
        return output;
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
        return getScaled( v, f, new int[v.length] );
    }

    static public int[] getScaled ( int[] v, int f, int[] output ) {
        for( int i = v.length - 1; i >= 0; i-- ) {
            output[i] = v[i] * f;
        }
        return output;
    }

    static public int[] getScaled ( int[] v, int[] f ) {
        return getScaled( v, f, new int[v.length] );
    }

    static public int[] getScaled ( int[] v, int[] f, int[] output ) {
        for( int i = v.length - 1; i >= 0; i-- ) {
            output[i] = v[i] * f[i];
        }
        return output;
    }

    static public int calcDistance ( int[] v1, int[] v2 ) {
        return (int) Math.sqrt( calcDistanceSq( v1, v2 ) );
    }

    static public int calcDistanceSq ( int[] v1, int[] v2 ) {
        int sum = 0;
        for( int i = v1.length - 1; i >= 0; i-- ) {
            int diff = v2[i] - v1[i];
            sum += diff * diff;
        }
        return sum;
    }

    /*
     static methods
     */
    static public int[] createRandomArray ( int length, int min, int max ) {
        return setArrayToRandom( new int[length], min, max );
    }

    static public int[] setArrayToRandom ( int[] array, int min, int max ) {
        for( int i = array.length - 1; i >= 0; i-- ) {
            array[i] = MathUtils.nextInt( min, max );
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
    static public VectorInt createRandom ( int length, int min, int max ) {
        return new VectorInt( createRandomArray( length, min, max ) );
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
