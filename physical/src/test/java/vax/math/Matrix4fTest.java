package vax.math;

import java.util.Arrays;
import org.junit.ComparisonFailure;
import org.junit.Test;

/**

 @author mac
 */
public class Matrix4fTest {

    public static final float DELTA = 1E-4f;

    private void assertEquals ( float tested, float expected ) {
        if ( Math.abs( tested - expected ) >= DELTA ) {
            throw new ComparisonFailure( "", Float.toString( tested ), Float.toString( expected ) );
        }
    }

    public static void assertEquals ( Matrix4f m1, Matrix4f m2 ) {
        if ( !m1.equals( m2, DELTA ) ) {
            throw new ComparisonFailure( "", m1.toString(), m2.toString() );
        }
    }

    public static void assertEquals ( Matrix4f m1, Matrix4f m2, float epsilon ) {
        if ( !m1.equals( m2, epsilon ) ) {
            throw new ComparisonFailure( "", m1.toString(), m2.toString() );
        }
    }

    public static void assertEquals ( float[] m1, float[] m2, float epsilon ) {
        if ( m1.length != m2.length ) {
            throw new ComparisonFailure( "", Arrays.toString( m1 ), Arrays.toString( m2 ) );
        }
        int len = m1.length;
        for( int i = 0; i < len; i++ ) {
            if ( Math.abs( m1[i] - m2[i] ) > epsilon ) {
                throw new ComparisonFailure( "", Arrays.toString( m1 ), Arrays.toString( m2 ) );
            }
        }

    }

    @Test
    public void detTest () {
        Matrix4f matrix1 = new Matrix4f( true );
        float det1 = matrix1.det();
        assertEquals( det1, 1.0f );

        float[] matrix2Vals = {
            2, 5, 3, 5,
            4, 6, 6, 3,
            11, 3, 2, -2,
            4, -7, 9, 3
        };
        Matrix4f matrix2 = new Matrix4f( matrix2Vals );
        float det2 = matrix2.det();
        assertEquals( det2, 2960 );

        float[] matrix3Vals = {
            1, 0, -5, 0,
            0, 1, 0, 2.456f,
            -0.123f, 1.987f, -56.29f, 0,
            0, 2.3f, 0, 1
        };
        Matrix4f matrix3 = new Matrix4f( matrix3Vals );
        float det3 = matrix3.det();
        assertEquals( det3, 264.539964f );
    }

    @Test
    public void transposeTest () {
        float[] matrixVals = {
            2.1723f, 5, 3, 5,
            4, 6f, 6.7736f, 3,
            11, 3, 2.99999f, -2.5523f,
            4, -7.0001f, 9, 3
        };
        float[] matrixTransposedVals = {
            2.1723f, 4, 11, 4,
            5, 6f, 3, -7.0001f,
            3, 6.7736f, 2.99991f, 9,
            5, 3, -2.5523f, 3
        };
        Matrix4f matrix1 = new Matrix4f( matrixVals );
        Matrix4f matrix2 = new Matrix4f( matrixTransposedVals );
        Matrix4f matrix3 = new Matrix4f( matrix2 );

        matrix2.transpose();
        assertEquals( matrix2, matrix1 );

        matrix2.transpose();
        assertEquals( matrix2, matrix3 );

        matrix2.transpose();
        assertEquals( matrix2, matrix1 );

        // transpose called on identity matrix should give identity matrix
        Matrix4f identity = new Matrix4f( true );
        Matrix4f transposedIdentity = new Matrix4f( identity );

        transposedIdentity.transpose();
        assertEquals( transposedIdentity, identity );

        Matrix4f matrix4 = new Matrix4f( matrixVals );
        Matrix4f matrix5 = new Matrix4f( matrix4 ).transpose();
        Matrix4f matrix6 = new Matrix4f();

        matrix4.transpose( matrix6 );
        assertEquals( matrix6, matrix5 );
    }

    @Test
    public void invertTest () {
        Matrix4f identity = new Matrix4f( true );
        Matrix4f invertedIdentity = new Matrix4f( true ).invert();
        assertEquals( identity, invertedIdentity );

        float[] matrixVals = {
            4, 6, 0.874f, 0,
            0, 42.223f, 0, -0.1f,
            -34.4f, 0, 41, 2,
            976.3345f, 1, 88.98f, 10
        };
        float[] matrixInvertedVals = {
            0.100548f, -0.0146469f, -0.00322558f, 0.000498648f,
            -0.0452761f, 0.0308433f, 0.000522623f, 0.000203908f,
            0.994809f, -0.144705f, 0.0111746f, -0.00368197f,
            -18.6642f, 2.71453f, 0.215441f, 0.0840571f
        };
        Matrix4f matrix1 = new Matrix4f( matrixVals );
        Matrix4f matrix2 = new Matrix4f( matrixInvertedVals );
        matrix1.invert();
        assertEquals( matrix1, matrix2, 0.125f );

    }

    @Test
    public void multiplyTest () {
        float[] m1Vals = {
            2.34f, 23.637f, 3.2119f, -536,
            -76.23f, 4.9803f, -32.0001f, 0.11922f,
            87.293f, 5, -0.0083f, 5.2394f,
            2.1345f, 6.66f, 9.98f, 283.214f
        };
        float[] m2Vals = {
            1.9909f, 842.594f, -3.42f, 2.7213f,
            -88.8f, 87.3213f, 4.213f, 0.889f,
            -223.41f, -313.34f, 732.1234f, 5374,
            34, 1, 364.28f, -100
        };
        float[] m3Vals = {
            -21035.877473f, 2493.2667820999995f, -192810.99297053998f, 70888.131735f,
            6559.178874f, -53769.02379561001f, -23102.90394684f, -172183.47661230003f,
            -90.21446329999995f, 73997.00466400001f, 1625.0549477799998f, -326.5487590999999f,
            6812.48577605f, -463.8424489999997f, 110496.546042f, 25322.849354850005f
        };

        float[] m1ValsCopy = new float[m1Vals.length];
        float[] m2ValsCopy = new float[m2Vals.length];
        float[] m3ValsCopy = new float[m3Vals.length];
        System.arraycopy( m1Vals, 0, m1ValsCopy, 0, m1Vals.length );
        System.arraycopy( m2Vals, 0, m2ValsCopy, 0, m2Vals.length );
        System.arraycopy( m3Vals, 0, m3ValsCopy, 0, m3Vals.length );

        Matrix4f.multiply( m1ValsCopy, m2ValsCopy );
        assertEquals( m1ValsCopy, m3ValsCopy, 0.01564f );

        Matrix4f identity = new Matrix4f( true );
        Matrix4f m1 = new Matrix4f( m1Vals );
        Matrix4f m1Copy = new Matrix4f( m1 );
        Matrix4f m2 = new Matrix4f( m2Vals );
        Matrix4f m3 = new Matrix4f( m3Vals );

        m1.multiply( identity );
        assertEquals( m1, m1Copy );

        assertEquals( identity.multiply( m1 ), m1Copy );

        m1.multiply( m2 );
        assertEquals( m1, m3, 0.01563f );
    }

    @Test
    public void multiplyMVTest () {
        float[] m1Vals = {
            2.34f, 23.637f, 3.2119f, -536,
            -76.23f, 4.9803f, -32.0001f, 0.11922f,
            87.293f, 5, -0.0083f, 5.2394f,
            0, 0, 0, 1
        };
        float[] m2Vals = {
            1.9909f, 842.594f, -3.42f, 2.7213f,
            -88.8f, 87.3213f, 4.213f, 0.889f,
            -223.41f, -313.34f, 732.1234f, 5374,
            0, 0, 0, 1
        };
        /*
         float[] m3Vals = {
         -21035.877473f, 2493.2667820999995f, -192810.99297053998f, 70888.131735f,
         6559.178874f, -53769.02379561001f, -23102.90394684f, -172183.47661230003f,
         -90.21446329999995f, 73997.00466400001f, 1625.0549477799998f, -326.5487590999999f,
         0, 0, 0, 1
         };
         */
        float[] m3Vals = new float[16];
        Matrix4f.multiply( m1Vals, m2Vals, m3Vals );

        float[] m1ValsCopy = new float[m1Vals.length];
        float[] m2ValsCopy = new float[m2Vals.length];
        float[] m3ValsCopy = new float[m3Vals.length];
        System.arraycopy( m1Vals, 0, m1ValsCopy, 0, m1Vals.length );
        System.arraycopy( m2Vals, 0, m2ValsCopy, 0, m2Vals.length );
        System.arraycopy( m3Vals, 0, m3ValsCopy, 0, m3Vals.length );

        Matrix4f.multiplyMV( m1ValsCopy, m2ValsCopy );
        assertEquals( m1ValsCopy, m3ValsCopy, 0.01564f );

        Matrix4f identity = new Matrix4f( true );
        Matrix4f m1 = new Matrix4f( m1Vals );
        Matrix4f m1Copy = new Matrix4f( m1 );
        Matrix4f m2 = new Matrix4f( m2Vals );
        Matrix4f m3 = new Matrix4f( m3Vals );

        m1.multiplyMV( identity );
        assertEquals( m1, m1Copy );

        assertEquals( identity.multiplyMV( m1 ), m1Copy );

        m1.multiplyMV( m2 );
        assertEquals( m1, m3, 0.01563f );
    }
}
