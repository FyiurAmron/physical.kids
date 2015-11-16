package vax.math;

import static org.junit.Assert.*;
import org.junit.Test;

/**

 @author toor
 */
public class Vector2iTest {

    @Test
    public void addTest () {
        int[][] testNum = { { 1, 2 }, { 5, 13 } };
        Vector2i //
                v1 = new Vector2i( testNum[0] ),
                v2 = new Vector2i( testNum[1] ),
                expectedResult1 = new Vector2i(
                        testNum[0][0] + testNum[1][0],
                        testNum[0][1] + testNum[1][1] );
        v1.add( v2 );
        assertEquals( v1, expectedResult1 );
    }

    @Test
    public void scaleTest () {
        int[][] testNum = { { 1, 2 }, { 5, 13 } };
        int scale = 3;
        Vector2i //
                v1 = new Vector2i( testNum[0] ),
                v2 = new Vector2i( testNum[1] ),
                expectedResult1 = new Vector2i(
                        testNum[0][0] * scale,
                        testNum[0][1] * scale ),
                expectedResult2 = new Vector2i(
                        testNum[1][0] * scale,
                        testNum[1][1] * scale );
        v1.scale( scale );
        v2.scale( scale );
        assertEquals( v1, expectedResult1 );
        assertEquals( v2, expectedResult2 );
    }

    @Test
    public void dotTest () {
        int[][] testNum = { { 1, 2, 3 }, { 5, 0, 37 } };
        Vector2i //
                v1 = new Vector2i( testNum[0] ),
                v2 = new Vector2i( testNum[1] );
        assertEquals( v1.dot( v2 ),
                testNum[0][0] * testNum[1][0]
                + testNum[0][1] * testNum[1][1]
                + testNum[0][2] * testNum[1][2] );
    }

    @Test
    public void rotateTest () {
        Vector2i //
                v0 = new Vector2i( 0, 34 ),
                v1 = v0.copy(),
                v2 = new Vector2i( 34, 0 );
        v1.rotate( -1 );
        assertEquals( v1, v2 );
        v1.rotate( 1 );
        assertEquals( v1, v0 );
        v1.rotate( 0 );
        assertEquals( v1, v0 );
        v1.rotate( 5 );
        assertEquals( v1, v2 );
    }
}
