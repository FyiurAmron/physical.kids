﻿using System;

namespace physical.math {
    public class VectorFloat : Vector<float> {
        public VectorFloat ( int requiredSize ) : base( requiredSize ) {
        }

        public VectorFloat ( float[] data ) : base( data ) {
        }

        public VectorFloat ( float[] data, int requiredSize ) : base( data, requiredSize ) {
        }

        public VectorFloat ( VectorFloat vector ) : base( vector ) {
        }

        /*
        public static VectorFloat operator + ( VectorFloat v1, VectorFloat v2 ) {
            add( v1.data, v2.data );
            return v1;
        }

        public static VectorFloat operator - ( VectorFloat v1, VectorFloat v2 ) {
            subtract( v1.data, v2.data );
            return v1;
        }
        */
        /*
        public static VectorFloat operator * ( VectorFloat v1, VectorFloat v2 ) {
            dot( v1.data, v2.data );
            return v1;
        }
        */
        /*
        public static VectorFloat operator * ( VectorFloat v1, float f ) {
            scale( v1.data, f );
            return v1;
        }

        public static VectorFloat operator * ( float f, VectorFloat v1 ) {
            scale( v1.data, f );
            return v1;
        }
        */

        public void setValue ( float value ) {
            for ( int i = data.Length - 1; i >= 0; i-- )
                data[i] = value;
        }

        public void setZero () {
            setValue( 0 );
        }

        public bool isZero () {
            for ( int i = data.Length - 1; i >= 0; i-- )
                if ( data[i] != 0 )
                    return false;
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

        override public string ToString () {
            return string.Join( ",", data );
        }

        // static part

        static public float lengthSq ( float[] data ) {
            float lenSum = 0;
            for ( int i = data.Length - 1; i >= 0; i-- )
                lenSum += data[i] * data[i];
            return lenSum;
        }

        static public float length ( float[] data ) {
            return (float) Math.Sqrt( lengthSq( data ) );
        }

        static public void normalize ( float[] data ) {
            float lenFactor = 1.0f / length( data );
            for ( int i = data.Length - 1; i >= 0; i-- )
                data[i] *= lenFactor;
            //return this;
        }

        static public float dot ( float[] v1, float[] v2 ) { // simple regardless of dimension count
            int len = v1.Length;
            float sum = 0;
            for ( int i = 0; i < len; i++ )
                sum += v1[i] * v2[i];
            return sum;
        }

        static public float[] add ( float[] v, float[] t ) { // translate with t
            for ( int i = v.Length - 1; i >= 0; i-- )
                v[i] += t[i];
            return v;
        }

        static public float[] subtract ( float[] v, float[] t ) { // translate with -t
            for ( int i = v.Length - 1; i >= 0; i-- )
                v[i] -= t[i];
            return v;
        }

        static public float[] getNormalized ( float[] data ) {
            float[] ret = new float[data.Length];
            float lenFactor = 1.0f / length( data );
            for ( int i = data.Length - 1; i >= 0; i-- )
                ret[i] = data[i] * lenFactor;
            return ret;
        }

        static public float[] getSum ( float[] v, float[] t ) {
            int len = v.Length;
            float[] ret = new float[len];
            for ( len--; len >= 0; len-- )
                ret[len] = v[len] + t[len];
            return ret;
        }

        static public float[] getDiff ( float[] v, float[] t ) {
            int i = v.Length;
            float[] ret = new float[i];
            for ( i--; i >= 0; i-- )
                ret[i] = v[i] - t[i];
            return ret;
        }


        static public float[] scale ( float[] v, float f ) {
            for ( int i = v.Length - 1; i >= 0; i-- )
                v[i] *= f;
            return v;
        }

        static public float[] scale ( float[] v, float[] f ) {
            for ( int i = v.Length - 1; i >= 0; i-- )
                v[i] *= f[i];
            return v;
        }

        static public float[] getScaled ( float[] v, float f ) {
            int i = v.Length;
            float[] ret = new float[i];
            for ( i--; i >= 0; i-- )
                ret[i] = v[i] * f;
            return ret;
        }

        static public float[] getScaled ( float[] v, float[] f ) {
            int i = v.Length;
            float[] ret = new float[i];
            for ( i--; i >= 0; i-- )
                ret[i] = v[i] * f[i];
            return ret;
        }

        static public float distance ( float[] v1, float[] v2 ) {
            return (float) Math.Sqrt( distanceSq( v1, v2 ) );
        }

        static public float distanceSq ( float[] v1, float[] v2 ) {
            float sum = 0, diff;
            for ( int i = v1.Length - 1; i >= 0; i-- ) {
                diff = v2[i] - v1[i];
                sum += diff * diff;
            }
            return sum;
        }

        static public Vector3f getRandom ( float min, float max ) {
            return new Vector3f( MathUtils.nextFloat( min, max ), MathUtils.nextFloat( min, max ), MathUtils.nextFloat( min, max ) );
        }
    }
}

