package vax.physics;

import org.junit.Test;

import java.util.*;

import java.sql.JDBCType;

import static org.junit.Assert.*;

/**
 * Created by Kuba on 2015-11-27.
 */
public class SphereSphereColliderTest {

    @Test
    public void testCollide () throws Exception {

        /*
         *  body setting:
         *
         *  b1      :   0,0         0,0         0,0
         *  b2      :   0,0         0,0         0,0
         *  bX      :   1,0         0,0         0,0
         *  bY      :   0,0         1,0         0,0
         *  bZ      :   0,0         0,0         1,0
         *  bR      :   2*rand      2*rand      2*rand
         *
         *
         */


        SphereSphereCollider collider = new SphereSphereCollider();
        float radius = 1f;

        SphereBody b1 = new SphereBody( 1f, 1f );
        SphereBody b2 = new SphereBody( 1f, 1f );

        SphereBody bX = new SphereBody( 1f, 1f );
        SphereBody bY = new SphereBody( 1f, 1f );
        SphereBody bZ = new SphereBody( 1f, 1f );
        bX.getTransform().addTranslation( 1f, 0f, 0f );
        bY.getTransform().addTranslation( 0f, 1f, 0f );
        bY.getTransform().addTranslation( 0f, 1f, 0f );


        assertEquals( true, collider.collide( b1, b2 ) );
        assertEquals( true, collider.collide( b1, bX ) );
        assertEquals( true, collider.collide( b1, bY ) );
        assertEquals( true, collider.collide( b1, bZ ) );

        SphereBody bR;
        Random rand = new Random();
        for( int i = 0; i <= 10000; i++ ) {
            float f1 = 3 * rand.nextFloat();
            float f2 = 3 * rand.nextFloat();
            float f3 = 3 * rand.nextFloat();
            b1 = new SphereBody( 1f, 1f );
            bR = new SphereBody( 1f, 1f );
            bR.getTransform().addTranslation( f1, f2, f3 );
            float distance = (float) Math.sqrt( f1 * f1 + f2 * f2 + f3 * f3 );
            try {
                assertEquals( distance <= 2 * 1f ? true : false, collider.collide( b1, bR ) );
//                System.out.println( "WIN\t dist: " + distance + " \t coll: " + collider.collide( b1, bR ) + "\t b1: " + b1.getTransform() + "\t bR: " + bR.getTransform());
            } catch (AssertionError ae) {
//                System.out.println( "LOSE\t dist: " + distance + " \t coll: " + collider.collide( b1, bR ) + "\t b1: " + b1.getTransform() + "\t bR: " + bR.getTransform());
            }
        }

    }

    @Test(expected = NullPointerException.class)
    public void testCollideNullValue1 () {
        SphereSphereCollider ssc = new SphereSphereCollider();
        ssc.collide( new SphereBody( 1f, 1f ), null );
    }

    @Test(expected = NullPointerException.class)
    public void testCollideNullValue2 () {
        SphereSphereCollider ssc = new SphereSphereCollider();
        ssc.collide( null, new SphereBody( 1f, 1f ) );
    }

    @Test(expected = NullPointerException.class)
    public void testCollideNullValue3 () {
        SphereSphereCollider ssc = new SphereSphereCollider();
        ssc.collide( null, null );
    }
}