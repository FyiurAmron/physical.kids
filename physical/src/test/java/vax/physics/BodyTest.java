package vax.physics;

import static org.junit.Assert.*;

import org.junit.Test;
import vax.math.Matrix4f;
import vax.math.Vector3f;

/**
 Created by Kuba on 2015-11-17.
 */
public class BodyTest {

    @Test
    public void applyForceTest () {
        Body b1 = new Body( 1f );
        b1.applyForce( 3f, 52f, -7f );
        b1.applyForce( new Vector3f( -3f, -52f, 7f ) );

        float massB2 = 2.5f;
        Body b2 = new Body( massB2 );
        b2.applyForce( 3.5f, -5.2f, 7.88f );

        assertEquals( b1.getAcceleration(), new Vector3f( 0f, 0f, 0f ) );
        assertEquals( b2.getAcceleration(), ( new Vector3f( 3.5f, -5.2f, 7.88f ).createScaled( 1f / massB2 ) ) );
    }

    @Test
    public void applyImpulseTest () {
        Body b1 = new Body( 1f );
        b1.applyImpulse( 3f, 52f, -7f );
        b1.applyImpulse( new Vector3f( -3f, -52f, 7f ) );

        float massB2 = 2.5f;
        Body b2 = new Body( massB2 );
        b2.applyImpulse( 3.5f, -5.2f, 7.88f );

        assertEquals( b1.getVelocity(), new Vector3f( 0f, 0f, 0f ) );
        assertEquals( b2.getVelocity(), ( new Vector3f( 3.5f, -5.2f, 7.88f ) ).createScaled( 1f / massB2 ) );
    }

    @Test
    public void timeStepTest () {
        Body b1 = new Body( 1f );
        b1.applyForce( 1f, 1f, 1f );
        b1.timeStep( 1f );

        Matrix4f transform = new Matrix4f();
        transform.setToIdentity();
        transform.addTranslation( 1f, 1f, 1f );

        assertEquals( b1.getVelocity(), new Vector3f( 1f, 1f, 1f ) );
        assertEquals( b1.getTransform(), transform );
    }

    @Test
    public void generalBodyTimeStepTest () {

        float mass = 2.5f;
        Vector3f vector = new Vector3f( 1f, 0f, -1f );
        Body b1 = new Body( mass );

        boolean shouldInvert = false;
        for( int i = 0; i <= 7; i++ ) {
            if ( shouldInvert ) {
                vector.setToOpposite();
            }
            b1.applyForce( vector );
            b1.timeStep( 1f );
            shouldInvert = !shouldInvert;
        }
        for( int i = 0; i <= 7; i++ ) {
            if ( shouldInvert ) {
                vector.setToOpposite();
            }
            b1.applyImpulse( vector );
            b1.timeStep( 1f );
            shouldInvert = !shouldInvert;
        }

        b1.applyImpulse( 1f, 0f, -1f );
        b1.applyImpulse( -1f, 1f, 0f );
        b1.applyImpulse( 0f, -1f, 1f );
        for( int i = 0; i <= 99; i++ ) {
            b1.timeStep( 1.01f );
        }

        Matrix4f matrix = new Matrix4f();
        matrix.setToIdentity();

        assertEquals( matrix, b1.getTransform() );

    }

    @Test(expected = IllegalArgumentException.class)
    public void illegalArgumentsTest1 () {
        new Body( -5f );
    }

    @Test(expected = IllegalArgumentException.class)
    public void illegalArgumentsTest2 () {
        new Body( 0f );
    }
}
