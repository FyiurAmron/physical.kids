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
        assertEquals( new Matrix4f( true ).det(), 1.0f );
        assertEquals( new Matrix4f().det(), 0.0f );

        assertEquals( new Matrix4f(
                2, 5, 3, 5,
                4, 6, 6, 3,
                11, 3, 2, -2,
                4, -7, 9, 3
        ).det(), 2960 );

        assertEquals( new Matrix4f(
                1, 0, -5, 0,
                0, 1, 0, 2.456f,
                -0.123f, 1.987f, -56.29f, 0,
                0, 2.3f, 0, 1
        ).det(), 264.539964f );
    }

    @Test
    public void det3x3Test () {
        assertEquals( new Matrix4f( true ).det3x3(), 1.0f );
        assertEquals( new Matrix4f().det3x3(), 0.0f );

        assertEquals( new Matrix4f(
                2, 5, 3, 5,
                4, 6, 6, 3,
                11, 3, 2, -2,
                4, -7, 9, 3
        ).det3x3(), 116 );

        assertEquals( new Matrix4f(
                2, 5, 3, 5,
                4, 6, 6, 3,
                11, 3, 2, -2,
                0, 0, 0, 1
        ).transpose().det3x3(), 116 );

        assertEquals( new Matrix4f(
                1, 0, -5, 0,
                0, 1, 0, 2.456f,
                -0.123f, 1.987f, -56.29f, 0,
                0, 0, 0, 1
        ).det3x3(), -56.905f );
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
        Matrix4f invertedIdentity = new Matrix4f( true );
        invertedIdentity.invert();
        assertEquals( identity, invertedIdentity );

        float[] matrixVals = {
            4, 6, 0.874f, 0,
            0, 42.223f, 0, -0.1f,
            -34.4f, 0, 41, 2,
            976.3345f, 1, 88.98f, 10
        };
        float[] matrixInvertedVals = {
            0.099905f, -0.0142086f, -0.00321816f, 0.000501545f,
            -0.0439214f, 0.0299204f, 0.000506986f, 0.000197807f,
            0.988454f, -0.140375f, 0.011248f, -0.00365334f,
            -18.5449f, 2.63331f, 0.214065f, 0.0835201f
        };
        Matrix4f matrix1 = new Matrix4f( matrixVals );
        Matrix4f matrix2 = new Matrix4f( matrixInvertedVals );
        matrix1.invert();
        assertEquals( matrix1, matrix2, 0.001f );
    }

    @Test
    public void invertMVTest () {
        Matrix4f identity = new Matrix4f( true );
        Matrix4f invertedIdentity = new Matrix4f( true );
        invertedIdentity.invertMV();
        assertEquals( identity, invertedIdentity );

        float[] matrixVals = {
            4, 6, 0.874f, 0,
            0, 42.223f, 0, -0.1f,
            -34.4f, 0, 41, 2,
            0, 0, 0, 1
        };
        float[] matrixInvertedVals = {
            0.211269f, -0.0300219f, -0.00450363f, 0.00600508f,
            0, 0.0236838f, 0, 0.00236838f,
            0.17726f, -0.0251891f, 0.0206116f, -0.0437421f,
            0, 0, 0, 1
        };
        Matrix4f matrix1 = new Matrix4f( matrixVals ).transpose();
        Matrix4f matrix2 = new Matrix4f( matrixInvertedVals ).transpose();
        matrix1.invert();
        assertEquals( matrix1, matrix2, 0.001f );
    }

    @Test
    public void multiplyTest () {
        float[] M1arr = { // note: transposition is needed to match this actual matrix in memory
            2.34f, 23.637f, 3.2119f, -536,
            -76.23f, 4.9803f, -32.0001f, 0.11922f,
            87.293f, 5, -0.0083f, 5.2394f,
            2.1345f, 6.66f, 9.98f, 283.214f
        };
        float[] M2arr = {
            1.9909f, 842.594f, -3.42f, 2.7213f,
            -88.8f, 87.3213f, 4.213f, 0.889f,
            -223.41f, -313.34f, 732.1234f, 5374,
            34, 1, 364.28f, -100
        };
        float[] M1xM2arr = {
            -21035.877473f, 2493.2667820999995f, -192810.99297053998f, 70888.131735f,
            6559.178874f, -53769.02379561001f, -23102.90394684f, -172183.47661230003f,
            -90.21446329999995f, 73997.00466400001f, 1625.0549477799998f, -326.5487590999999f,
            6812.48577605f, -463.8424489999997f, 110496.546042f, 25322.849354850005f
        };
        float[] M2xM1arr = {
            -64519.01535915f, 4244.4536595f, -26929.51072769f, -213.87683312f,
            -6494.6317195f, -1637.09358961f, -3070.66980003f, 47881.061283585994f,
            98743.1797562f, 32610.187628f, 62935.78413078f, 1.64553832694716E6f,
            31588.97404f, 1964.0383000000002f, -923.819024f, -44636.672148f
        };

        Matrix4f m1 = new Matrix4f( true, M1arr ).transpose(), m2 = new Matrix4f( true, M2arr ).transpose();
        Matrix4f m1x2 = new Matrix4f( true, M1xM2arr ).transpose(), m2x1 = new Matrix4f( true, M2xM1arr ).transpose();
        Matrix4f id = new Matrix4f( true ), zero = new Matrix4f();

        assertEquals( new Matrix4f( zero ).multiply( m1 ), zero );
        assertEquals( new Matrix4f( m1 ).multiply( zero ), zero );

        assertEquals( m1, new Matrix4f( m1 ).multiply( id ) );
        assertEquals( m1, new Matrix4f( id ).multiply( m1 ) );

        assertEquals( new Matrix4f( m1 ).multiply( m2 ), m1x2, 0.2f );
        assertEquals( new Matrix4f( m2 ).multiply( m1 ), m2x1, 0.2f );

        assertEquals( new Matrix4f( m1 ).multiplyLeft( m2 ), m2x1, 0.2f );
        assertEquals( new Matrix4f( m2 ).multiplyLeft( m1 ), m1x2, 0.2f );

        assertEquals( new Matrix4f( m1 ).multiplyLeft( m2 ), new Matrix4f( m2 ).multiply( m1 ), 0.001f );
        assertEquals( new Matrix4f( m1 ).transpose().multiplyLeft( new Matrix4f( m2 ).transpose() ),
                new Matrix4f( m1 ).multiply( m2 ).transpose(), 0.001f );
    }

    @Test
    public void multiplyMVTest () {
        float[] M1arr = { // note the actual matrices are transposed vs what's visible here!
            2.34f, 23.637f, 3.2119f, -536,
            -76.23f, 4.9803f, -32.0001f, 0.11922f,
            87.293f, 5, -0.0083f, 5.2394f,
            0, 0, 0, 1
        };
        float[] M2arr = {
            1.9909f, 842.594f, -3.42f, 2.7213f,
            -88.8f, 87.3213f, 4.213f, 0.889f,
            -223.41f, -313.34f, 732.1234f, 5374,
            0, 0, 0, 1
        };

        float[] M1xM2arr = {
            -2811.8775f, 3029.2668f, 2443.087f, 16752.132f,
            6555.1254f, -53769.143f, -23146.333f, -172171.44f,
            -268.35406f, 73991.765f, -283.55368f, 202.63064f,
            0, 0, 0, 1
        };
        float[] M2xM1arr = {
            -64524.824f, 4226.3298f, -26956.669f, -981.86579f,
            -6496.5293f, -1643.0143f, -3079.542f, 47630.173f,
            87272.377f, -3180.6524f, 9303.2641f, 128920.29f,
            0, 0, 0, 1
        };

        Matrix4f m1 = new Matrix4f( true, M1arr ).transpose(), m2 = new Matrix4f( true, M2arr ).transpose();
        Matrix4f m1x2 = new Matrix4f( true, M1xM2arr ).transpose(), m2x1 = new Matrix4f( true, M2xM1arr ).transpose();
        Matrix4f id = new Matrix4f( true ), zero = new Matrix4f();

        Matrix4f h = new Matrix4f( zero );
        h.setTranslation( m1.getTranslation( new Vector3f() ) );

        assertEquals( new Matrix4f( zero ).multiplyLeftMV( m1 ), h );
        h.setToZero();
        h.setValue( 1, Matrix4f.M44 );
        assertEquals( new Matrix4f( m1 ).multiplyLeftMV( zero ), h );

        assertEquals( m1, new Matrix4f( m1 ).multiplyMV( id ) );
        assertEquals( m1, new Matrix4f( id ).multiplyMV( m1 ) );

        assertEquals( new Matrix4f( m1 ).multiplyMV( m2 ), m1x2, 0.2f );
        assertEquals( new Matrix4f( m2 ).multiplyMV( m1 ), m2x1, 0.2f );

        assertEquals( new Matrix4f( m1 ).multiplyLeftMV( m2 ), m2x1, 0.2f );
        assertEquals( new Matrix4f( m2 ).multiplyLeftMV( m1 ), m1x2, 0.2f );

        assertEquals( new Matrix4f( m1 ).multiplyLeftMV( m2 ), new Matrix4f( m2 ).multiplyMV( m1 ), 0.001f );
        m1 = new Matrix4f(
                2.34f, 23.637f, 3.2119f, 0,
                -76.23f, 4.9803f, -32.0001f, 0,
                87.293f, 5, -0.0083f, 0,
                0, 0, 0, 1
        );
        m2 = new Matrix4f(
                1.9909f, 842.594f, -3.42f, 0,
                -88.8f, 87.3213f, 4.213f, 0,
                -223.41f, -313.34f, 732.1234f, 0,
                0, 0, 0, 1
        );

        assertEquals(
                new Matrix4f( m1 ).transpose3x3().multiplyLeftMV( new Matrix4f( m2 ).transpose3x3() ),
                new Matrix4f( m1 ).multiplyMV( m2 ).transpose3x3(), 0.001f );
    }

    @Test
    public void lookAtTest () {
        Matrix4f m = new Matrix4f();
        m.setToLookAt( 1, 2, 3, 6, 7, 8, 0, 1, 0 );
        float[] result = { // please note the visual transposition here!
            -0.70710677f, -0.40824825f, -0.5773502f, 0,
            0, 0.8164965f, -0.5773502f, 0,
            0.70710677f, -0.40824825f, -0.5773502f, 0,
            -1.4142134f, 0, 3.4641013f, 1
        };

        assertEquals( m, new Matrix4f( result ) );
    }
}
