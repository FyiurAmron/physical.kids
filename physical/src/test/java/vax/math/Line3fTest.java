package vax.math;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**

 @author toor
 */
public class Line3fTest {
    public static final float DELTA = 1E-4f;

    @Test
    public void calcPointDistTest () {
        Vector3f point = new Vector3f( 1, 2, 3 );
        Line3f line = new Line3f( new Vector3f( -1, -1, -1 ), new Vector3f( -1, -1, -1 ) );
        assertEquals( line.calcDistance( point ), FloatUtils.sqrt( 2 ), DELTA );
    }
}
