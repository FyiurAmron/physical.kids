package vax.math;

import static org.junit.Assert.*;
import org.junit.Test;

/**

 @author toor
 */
public class Vector4fTest {
    public static final float DELTA = 1E-6f;

    @Test
    public void addTest () {
        float[][] testNum = { { 1, 2, 3, 4 }, { 5, 13, 37, 7 } };
        VectorFloat //
                v1 = new VectorFloat( testNum[0] ),
                v2 = new VectorFloat( testNum[1] ),
                expectedResult1 = new VectorFloat(
                        testNum[0][0] + testNum[1][0],
                        testNum[0][1] + testNum[1][1],
                        testNum[0][2] + testNum[1][2],
                        testNum[0][3] + testNum[1][3] );
        v1.add( v2 );
        assertEquals( v1, expectedResult1 );
    }

    @Test
    public void scaleTest () {
        float[][] testNum = { { 1, 2, 3, 4 }, { 5, 13, 37, 7 } };
        float scale = 3.14f;
        VectorFloat //
                v1 = new VectorFloat( testNum[0] ),
                v2 = new VectorFloat( testNum[1] ),
                expectedResult1 = new VectorFloat(
                        testNum[0][0] * scale,
                        testNum[0][1] * scale,
                        testNum[0][2] * scale,
                        testNum[0][3] * scale
                ),
                expectedResult2 = new VectorFloat(
                        testNum[1][0] * scale,
                        testNum[1][1] * scale,
                        testNum[1][2] * scale,
                        testNum[1][3] * scale
                );
        v1.scale( scale );
        v2.scale( scale );
        assertEquals( v1, expectedResult1 );
        assertEquals( v2, expectedResult2 );
    }

    @Test
    public void dotTest () {
        float[][] testNum = { { 1, 2, 3, 4 }, { 5, 13, 37, 7 } };
        VectorFloat //
                v1 = new VectorFloat( testNum[0] ),
                v2 = new VectorFloat( testNum[1] );
        assertEquals( v1.dot( v2 ),
                testNum[0][0] * testNum[1][0]
                + testNum[0][1] * testNum[1][1]
                + testNum[0][2] * testNum[1][2]
                + testNum[0][3] * testNum[1][3],
                DELTA );
    }

    @Test
    public void rotateTest () {
        VectorFloat //
                v0 = new VectorFloat( 0, 34, 3, 5 ),
                v1 = v0.copy(),
                v2 = new VectorFloat( 34, 3, 5, 0 ),
                v3 = new VectorFloat( 3, 5, 0, 34 );
        v1.rotate( -1 );
        assertEquals( v1, v2 );
        v1.rotate( 1 );
        assertEquals( v1, v0 );
        v1.rotate( 0 );
        assertEquals( v1, v0 );
        v1.rotate( 2 );
        assertEquals( v1, v3 );
        v1.rotate( 16 );
        assertEquals( v1, v3 );
    }
}
