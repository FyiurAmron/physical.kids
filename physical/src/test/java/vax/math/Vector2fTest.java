package vax.math;

import static org.junit.Assert.*;
import org.junit.Test;

/**

 @author toor
 */
public class Vector2fTest {
    public static final float DELTA = 1E-6f;

    @Test
    public void addTest () {
        float[][] testNum = { { 1.27134610f, 2 }, { 5, 13 } };
        Vector2f //
                v1 = new Vector2f( testNum[0] ),
                v2 = new Vector2f( testNum[1] ),
                expectedResult1 = new Vector2f(
                        testNum[0][0] + testNum[1][0],
                        testNum[0][1] + testNum[1][1] );
        v1.add( v2 );
        assertEquals( v1, expectedResult1 );
    }

    @Test
    public void scaleTest () {
        float[][] testNum = { { 1, 2.37258f }, { 5.4452f, 13 } };
        float scale = 3.14f;
        Vector2f //
                v1 = new Vector2f( testNum[0] ),
                v2 = new Vector2f( testNum[1] ),
                expectedResult1 = new Vector2f(
                        testNum[0][0] * scale,
                        testNum[0][1] * scale ),
                expectedResult2 = new Vector2f(
                        testNum[1][0] * scale,
                        testNum[1][1] * scale );
        v1.scale( scale );
        v2.scale( scale );
        assertEquals( v1, expectedResult1 );
        assertEquals( v2, expectedResult2 );
    }

    @Test
    public void dotTest () {
        float[][] testNum = { { 1, 2, 3.5704f }, { 5, 0, 37 } };
        Vector2f //
                v1 = new Vector2f( testNum[0] ),
                v2 = new Vector2f( testNum[1] );
        assertEquals( v1.dot( v2 ),
                testNum[0][0] * testNum[1][0]
                + testNum[0][1] * testNum[1][1]
                + testNum[0][2] * testNum[1][2],
                DELTA );
    }

    @Test
    public void rotateTest () {
        Vector2f //
                v0 = new Vector2f( 0, 34.900000002f ),
                v1 = v0.copy(),
                v2 = new Vector2f( 34.900000001f, 0 );
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
