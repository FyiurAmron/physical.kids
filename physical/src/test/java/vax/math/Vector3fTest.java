package vax.math;

import static org.junit.Assert.*;
import org.junit.Test;

/**

 @author toor
 */
public class Vector3fTest {
    public static final float DELTA = 1E-6f;

    @Test
    public void addTest () {
        float[][] testNum = { { 1, 2, 3 }, { 5, 13, 37 } };
        Vector3f //
                v1 = new Vector3f( testNum[0] ),
                v2 = new Vector3f( testNum[1] ),
                expectedResult1 = new Vector3f(
                        testNum[0][0] + testNum[1][0],
                        testNum[0][1] + testNum[1][1],
                        testNum[0][2] + testNum[1][2] );
        v1.add( v2 );
        assertEquals( v1, expectedResult1 );
    }

    @Test
    public void scaleTest () {
        float[][] testNum = { { 1, 2, 3 }, { 5, 13, 37 } };
        float scale = 3.14f;
        Vector3f //
                v1 = new Vector3f( testNum[0] ),
                v2 = new Vector3f( testNum[1] ),
                expectedResult1 = new Vector3f(
                        testNum[0][0] * scale,
                        testNum[0][1] * scale,
                        testNum[0][2] * scale ),
                expectedResult2 = new Vector3f(
                        testNum[1][0] * scale,
                        testNum[1][1] * scale,
                        testNum[1][2] * scale );
        v1.scale( scale );
        v2.scale( scale );
        assertEquals( v1, expectedResult1 );
        assertEquals( v2, expectedResult2 );
    }

    @Test
    public void dotTest () {
        float[][] testNum = { { 1, 2, 3 }, { 5, 0, 37 } };
        Vector3f //
                v1 = new Vector3f( testNum[0] ),
                v2 = new Vector3f( testNum[1] );
        assertEquals( v1.dot( v2 ),
                testNum[0][0] * testNum[1][0]
                + testNum[0][1] * testNum[1][1]
                + testNum[0][2] * testNum[1][2],
                DELTA );
    }

    @Test
    public void rotateTest () {
        Vector3f //
                v0 = new Vector3f( 0, 34, 3 ),
                v1 = v0.copy(),
                v2 = new Vector3f( 34, 3, 0 ),
                v3 = new Vector3f( 3, 0, 34 );
        v1.rotate( -1 );
        assertEquals( v1, v2 );
        v1.rotate( 1 );
        assertEquals( v1, v0 );
        v1.rotate( 0 );
        assertEquals( v1, v0 );
        v1.rotate( 1 );
        assertEquals( v1, v3 );
        v1.rotate( 15 );
        assertEquals( v1, v3 );
    }
}
