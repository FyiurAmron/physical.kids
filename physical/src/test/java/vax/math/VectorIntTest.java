package vax.math;

import static org.junit.Assert.*;
import org.junit.Test;

/**

 @author toor
 */
public class VectorIntTest {

    @Test
    public void addTest () {
        int[][] testNum = { { 1, 2, 3, 4, 23 }, { 5, 13, 37, 7, -5 } };
        VectorInt //
                v1 = new VectorInt( testNum[0] ),
                v2 = new VectorInt( testNum[1] ),
                expectedResult1 = new VectorInt(
                        testNum[0][0] + testNum[1][0],
                        testNum[0][1] + testNum[1][1],
                        testNum[0][2] + testNum[1][2],
                        testNum[0][3] + testNum[1][3],
                        testNum[0][4] + testNum[1][4] );
        v1.add( v2 );
        assertEquals( v1, expectedResult1 );
    }

    @Test
    public void scaleTest () {
        int[][] testNum = { { 1, 2, 3, 4, 23 }, { 5, 13, 37, 7, -5 } };
        int scale = 3;
        VectorInt //
                v1 = new VectorInt( testNum[0] ),
                v2 = new VectorInt( testNum[1] ),
                expectedResult1 = new VectorInt(
                        testNum[0][0] * scale,
                        testNum[0][1] * scale,
                        testNum[0][2] * scale,
                        testNum[0][3] * scale,
                        testNum[0][4] * scale
                ),
                expectedResult2 = new VectorInt(
                        testNum[1][0] * scale,
                        testNum[1][1] * scale,
                        testNum[1][2] * scale,
                        testNum[1][3] * scale,
                        testNum[1][4] * scale
                );
        v1.scale( scale );
        v2.scale( scale );
        assertEquals( v1, expectedResult1 );
        assertEquals( v2, expectedResult2 );
    }

    @Test
    public void dotTest () {
        int[][] testNum = { { 1, 2, 3, 4, 23 }, { 5, 13, 37, 7, -5 } };
        VectorInt //
                v1 = new VectorInt( testNum[0] ),
                v2 = new VectorInt( testNum[1] );
        assertEquals( v1.dot( v2 ),
                testNum[0][0] * testNum[1][0]
                + testNum[0][1] * testNum[1][1]
                + testNum[0][2] * testNum[1][2]
                + testNum[0][3] * testNum[1][3]
                + testNum[0][4] * testNum[1][4] );
    }

    @Test
    public void rotateTest () {
        VectorInt //
                v0 = new VectorInt( 0, 34, 3, 5, 73 ),
                v1 = v0.copy(),
                v2 = new VectorInt( 34, 3, 5, 73, 0 ),
                v3 = new VectorInt( 3, 5, 73, 0, 34 );
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
