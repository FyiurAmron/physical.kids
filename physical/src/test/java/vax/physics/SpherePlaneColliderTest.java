package vax.physics;

import java.util.*;

import org.junit.Test;
import static org.junit.Assert.*;
import vax.math.Plane3f;
import vax.math.Vector3f;

/**
 * Created by Kuba on 2015-11-28.
 */
public class SpherePlaneColliderTest {
    private static final int COLLISION_ITERATIONS = 1000;

    @Test
    public void testCollide () throws Exception {

        SpherePlaneCollider collider = new SpherePlaneCollider();
        Random rand = new Random();
        SphereBody sphereBody;
        PlaneBody planeBody;
        for( int i = 0; i <= COLLISION_ITERATIONS; i++ ) {
            float[] f1 = {
                    rand.nextFloat(),
                    rand.nextFloat(),
                    rand.nextFloat() };
            float[] f2 = {
                    2 * rand.nextFloat(),
                    2 * rand.nextFloat(),
                    2 * rand.nextFloat() };
            planeBody = new PlaneBody( new Plane3f( new Vector3f( f1 ), 0 ) );
            sphereBody = new SphereBody( 1f, 1f );
            sphereBody.getTransform().addTranslation( f2 );
            float distance = planeBody.getPlane3f().calcDistance( sphereBody.getTransform() );

            assertEquals( distance <= 1f, collider.collide( sphereBody, planeBody ) );

        }
    }

    @Test(expected = NullPointerException.class)
    public void testCollideNullValue1 () {
        new SpherePlaneCollider().collide( new SphereBody( 1f, 1f ), null );
    }

    @Test(expected = NullPointerException.class)
    public void testCollideNullValue2 () {
        new SpherePlaneCollider().collide( null, new PlaneBody( new Vector3f( 1f, 1f, 1f ), 0 ) );
    }

    @Test(expected = NullPointerException.class)
    public void testCollideNullValue3 () {
        new SpherePlaneCollider().collide( null, null );
    }


}