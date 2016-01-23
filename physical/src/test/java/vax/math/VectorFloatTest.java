package vax.math;

import org.junit.ComparisonFailure;
import org.junit.Test;

/**

 @author toor
 */
public class VectorFloatTest {
    public static final float DELTA = 1E-4f;

    public static void assertEquals( VectorFloat v1, VectorFloat v2 ) {
        if ( !v1.equals( v2, DELTA ))
            throw new ComparisonFailure("", v1.toString(), v2.toString() );
    }

    @Test
    public void addTest () {
        float[][] testNum = { { 1, 2, 3, 4, 23.098f }, { 5, 13.876f, 37, 7, -5 } };
        VectorFloat //
                v0 = new VectorFloat( testNum[0] ),
                v1 = v0.copy(),
                v2 = new VectorFloat( testNum[1] ),
                expectedResult1 = new VectorFloat(
                        testNum[0][0] + testNum[1][0],
                        testNum[0][1] + testNum[1][1],
                        testNum[0][2] + testNum[1][2],
                        testNum[0][3] + testNum[1][3],
                        testNum[0][4] + testNum[1][4] );
        v1.add( new VectorFloat( testNum[0].length ));
        assertEquals( v1, v0 );
        v1.add( v2 );
        assertEquals( v1, expectedResult1 );
        v1.subtract( v2 );
        assertEquals( v1, v0 );
    }

    @Test
    public void scaleTest () {
        float[][] testNum = { { 1, 2, 3, 4.03f, 23 }, { 5, 13.12f, 37, 7, -5 } };
        float scale = 3.14f;
        VectorFloat //
                v0 = new VectorFloat( testNum[0] ),
                v1 = v0.copy(),
                v2 = new VectorFloat( testNum[1] ),
                expectedResult1 = new VectorFloat(
                        testNum[0][0] * scale,
                        testNum[0][1] * scale,
                        testNum[0][2] * scale,
                        testNum[0][3] * scale,
                        testNum[0][4] * scale
                ),
                expectedResult2 = new VectorFloat(
                        testNum[1][0] * scale,
                        testNum[1][1] * scale,
                        testNum[1][2] * scale,
                        testNum[1][3] * scale,
                        testNum[1][4] * scale
                );
        v1.scale( 1.000000001f );
        assertEquals( v1, v0 );
        v1.scale( scale );
        v2.scale( scale );
        assertEquals( v1, expectedResult1 );
        assertEquals( v2, expectedResult2 );
        v1.scale( 1.0f / scale);
        assertEquals( v1, v0 );
    }

    @Test
    public void dotTest () {
        float[][] testNum = { { 1, 2, 3.4f, 4, 23 }, { 5, 13, 37, 7, -5 } };
        VectorFloat //
                v1 = new VectorFloat( testNum[0] ),
                v2 = new VectorFloat( testNum[1] );
        org.junit.Assert.assertEquals( v1.dot( v2 ),
                testNum[0][0] * testNum[1][0]
                + testNum[0][1] * testNum[1][1]
                + testNum[0][2] * testNum[1][2]
                + testNum[0][3] * testNum[1][3]
                + testNum[0][4] * testNum[1][4],
                DELTA );
    }

    @Test
    public void rotateTest () {
        VectorFloat //
                v0 = new VectorFloat( 0, 34, 3, 5.2f, 73 ),
                v1 = v0.copy(),
                v2 = new VectorFloat( 34, 3, 5.2f, 73, 0 ),
                v3 = new VectorFloat( 3, 5.2f, 73, 0, 34 );
        v1.rotate( -1 );
        assertEquals( v1, v2 );
        v1.rotate( 1 );
        assertEquals( v1, v0 );
        v1.rotate( 0 );
        assertEquals( v1, v0 );
        v1.rotate( 3 );
        assertEquals( v1, v3 );
        v1.rotate( 15 );
        assertEquals( v1, v3 );
    }
}
