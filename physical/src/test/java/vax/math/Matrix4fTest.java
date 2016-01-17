package vax.math;

import static org.junit.Assert.*;
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
        if ( !m1.equals( m2, DELTA ) )
            throw new ComparisonFailure( "", m1.toString(), m2.toString() );
    }

    @Test
    public void determinantTest () {
        Matrix4f matrix1 = new Matrix4f();
        matrix1.setToIdentity();
        float det1 = matrix1.determinant();
        assertEquals( det1, 1.0f );

        float[] matrix2Vals = {
            2, 5, 3, 5,
            4, 6, 6, 3,
            11, 3, 2, -2,
            4, -7, 9, 3
        };
        Matrix4f matrix2 = new Matrix4f( matrix2Vals );
        float det2 = matrix2.determinant();
        assertEquals( det2, 2960 );

        float[] matrix3Vals = {
            1, 0, -5, 0,
            0, 1, 0, 2.456f,
            -0.123f, 1.987f, -56.29f, 0,
            0, 2.3f, 0, 1
        };
        Matrix4f matrix3 = new Matrix4f( matrix3Vals );
        float det3 = matrix3.determinant();
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

        matrix6.transpose( matrix4 );
        assertEquals( matrix6, matrix5 );
    }

}