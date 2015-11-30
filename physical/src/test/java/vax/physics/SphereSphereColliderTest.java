package vax.physics;

import java.util.*;

import org.junit.Test;
import static org.junit.Assert.*;
import vax.math.Vector3f;

/**
 Created by Kuba on 2015-11-27.
 */
public class SphereSphereColliderTest {
    private static final int COLLISION_ITERATIONS = 1000;

    @Test
    public void testCollide () throws Exception {
        /*
         body setting:

         b1 : 0,0 0,0 0,0
         b2 : 0,0 0,0 0,0
         bX : 1,0 0,0 0,0
         bY : 0,0 1,0 0,0
         bZ : 0,0 0,0 1,0
         bR : 2*rand 2*rand 2*rand
         */
        SphereSphereCollider collider = new SphereSphereCollider();
        //float radius = 1f;

        SphereBody b1 = new SphereBody( 1f, 1f );
        SphereBody b2 = new SphereBody( 1f, 1f );

        SphereBody bX = new SphereBody( 1f, 1f );
        SphereBody bY = new SphereBody( 1f, 1f );
        SphereBody bZ = new SphereBody( 1f, 1f );
        bX.getTransform().addTranslation( 1f, 0f, 0f );
        bY.getTransform().addTranslation( 0f, 1f, 0f );
        bZ.getTransform().addTranslation( 0f, 0f, 1f );

        assertTrue( collider.collide( b1, b2 ) );
        assertTrue( collider.collide( b1, bX ) );
        assertTrue( collider.collide( b1, bY ) );
        assertTrue( collider.collide( b1, bZ ) );

        SphereBody bR;
        Random rand = new Random();
        for( int i = 0; i <= COLLISION_ITERATIONS; i++ ) {
            float[] f = {
                3 * rand.nextFloat(),
                3 * rand.nextFloat(),
                3 * rand.nextFloat() };
            b1 = new SphereBody( 1f, 1f );
            bR = new SphereBody( 1f, 1f );
            bR.getTransform().addTranslation( f );
            float distance = Vector3f.calcLength( f );
//            try {
            assertEquals( distance <= 2 * 1f, collider.collide( b1, bR ) );
//                System.out.println( "WIN\t dist: " + calcDistance + " \t coll: " + collider.collide( b1, bR ) + "\t b1: " + b1.getTransform() + "\t bR: " + bR.getTransform());
//            } catch (AssertionError ae) {
//                System.out.println( "LOSE\t dist: " + calcDistance + " \t coll: " + collider.collide( b1, bR ) + "\t b1: " + b1.getTransform() + "\t bR: " + bR.getTransform());
//            }
        }

    }

    @Test( expected = NullPointerException.class )
    public void testCollideNullValue1 () {
        new SphereSphereCollider().collide( new SphereBody( 1f, 1f ), null );
    }

    @Test( expected = NullPointerException.class )
    public void testCollideNullValue2 () {
        new SphereSphereCollider().collide( null, new SphereBody( 1f, 1f ) );
    }

    @Test( expected = NullPointerException.class )
    public void testCollideNullValue3 () {
        new SphereSphereCollider().collide( null, null );
    }
}
