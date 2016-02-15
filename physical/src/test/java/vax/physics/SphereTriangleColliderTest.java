package vax.physics;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import vax.math.Vector3f;

import static org.junit.Assert.*;

/**
 Created by Kuba on 2016-01-23.
 */
public class SphereTriangleColliderTest {

    Vector3f //
            vertex1 = new Vector3f(),
            vertex2 = new Vector3f(),
            vertex3 = new Vector3f();
    Vector3f[] triangleVertices = new Vector3f[]{
        vertex1,
        vertex2,
        vertex3
    };
    TriangleBody tb = new TriangleBody( triangleVertices );
    SphereBody sb = new SphereBody( 1f, 1f );
    BodyManager bm;

    @Before
    public void setBodyManager () {
        bm = new BodyManager();
        bm.addBody( sb );
        bm.addBody( tb );
    }

    // general test - simple 1D movement
    @Ignore
    @Test
    public void generalTest1 () {
        println( "TEST START : general 1" );
        sb.getTransform().addTranslation( 0f, 0f, -1.0f );

        float tPosX = 2f;
        vertex1.set( tPosX, 0f, 0f );
        vertex2.set( tPosX, -1f, -1f );
        vertex3.set( tPosX, +1f, -1f );
        tb.setPoints( triangleVertices );
        sb.applyImpulse( new Vector3f( 1f, 0f, 1f ) );

        int steps = 0;
        while( steps <= 200 ) {
            printBodyInfo();
            bm.update( 0.1f );
            steps++;
        }
    }

    // general test - velocity direction change
    @Ignore
    @Test
    public void generalTest2 () {
        println( "TEST START : general 2" );
        sb.getTransform().addTranslation( 0f, 0f, -0.1f );

        float tPosX = 2f;
        vertex1.set( tPosX + 1f, 0f, 0f );
        vertex2.set( tPosX, -1f, -1f );
        vertex3.set( tPosX, +1f, -1f );
        tb.setPoints( triangleVertices );
        sb.applyImpulse( new Vector3f( 1f, 0f, 0f ) );

        int steps = 0;
        while( steps <= 200 ) {
            printBodyInfo();
            bm.update( 0.1f );
            steps++;
        }
    }

    /*
     @Test
     public void threePlusTest () {
     System.out.println( "TEST START : 3 +" );
     sb.getTransform().addTranslation( 0f, 0f, -0.1f );

     float tPosX = 2f;
     vertex1.set( tPosX, 0f, 0f );
     vertex2.set( tPosX, -1f, -1f );
     vertex3.set( tPosX, +1f, -1f );
     tb.setPoints( triangleVertices );
     sb.applyImpulse( new Vector3f( 1f, 0f, 0f ) );

     while( !bm.hasContact( sb, tb ) ) {
     printBodyInfo();
     bm.update( 0.1f );
     if ( getEndCondition() ) {
     break;
     }
     }

     assertTrue( bm.hasContact( sb, tb ) );
     }
     */
    @Ignore
    @Test
    public void twoPlusTestPositive1 () {
        println( "TEST START : 2 + positive (1)" );
        sb.getTransform().addTranslation( 0f, 0f, 0.9f );

        float tPosX = 1f;
        vertex1.set( tPosX, -1f, 0f );
        vertex2.set( tPosX, +1f, 0f );
        vertex3.set( tPosX, 0f, -1f );
        tb.setPoints( triangleVertices );
        sb.applyImpulse( new Vector3f( 1f, 0f, 0f ) );

        while( !bm.hasContact( sb, tb ) ) {
            printBodyInfo();
            bm.update( 0.1f );

            if ( getEndCondition() ) {
                break;
            }
        }

        assertTrue( bm.hasContact( sb, tb ) );
    }

    @Ignore
    @Test
    public void twoPlusTestPositive2 () {
        println( "TEST START : 2 + positive (2)" );
        sb.getTransform().addTranslation( 0f, 0.9f, 0.9f );

        float tPosX = 1f;
        vertex1.set( tPosX, -1f, 0f );
        vertex2.set( tPosX, +1f, 0f );
        vertex3.set( tPosX, 0f, -1f );
        tb.setPoints( triangleVertices );
        sb.applyImpulse( new Vector3f( 1f, 0f, 0f ) );

        while( !bm.hasContact( sb, tb ) ) {
            printBodyInfo();
            bm.update( 0.1f );

            if ( getEndCondition() ) {
                break;
            }
        }

        assertTrue( bm.hasContact( sb, tb ) );
    }

    @Ignore
    @Test
    public void twoPlusTestNegative1 () {
        println( "TEST START : 2 + negative (1)" );
        sb.getTransform().addTranslation( 0f, 0f, 1.1f );

        float tPosX = 1f;
        vertex1.set( tPosX, -1f, 0f );
        vertex2.set( tPosX, +1f, 0f );
        vertex3.set( tPosX, 0f, -1f );
        tb.setPoints( triangleVertices );
        sb.applyImpulse( new Vector3f( 1f, 0f, 0f ) );

        while( !bm.hasContact( sb, tb ) ) {
            printBodyInfo();
            bm.update( 0.1f );

            if ( getEndCondition() ) {
                break;
            }
        }

        assertFalse( bm.hasContact( sb, tb ) );
    }

    @Ignore
    @Test
    public void twoPlusTestNegative2 () {
        println( "TEST START : 2 + negative (2)" );
        sb.getTransform().addTranslation( 0f, 2f, 1.1f );

        float tPosX = 1f;
        vertex1.set( tPosX, -1f, 0f );
        vertex2.set( tPosX, +1f, 0f );
        vertex3.set( tPosX, 0f, -1f );
        tb.setPoints( triangleVertices );
        sb.applyImpulse( new Vector3f( 1f, 0f, 0f ) );

        while( !bm.hasContact( sb, tb ) ) {
            printBodyInfo();
            bm.update( 0.1f );

            if ( getEndCondition() ) {
                break;
            }
        }

        assertFalse( bm.hasContact( sb, tb ) );
    }

    @Test
    public void onePlusTestPositive () {
        println( "TEST START : 1 + positive" );
        sb.getTransform().addTranslation( 0f, 0f, 0.1f );

        float tPosX = 1f;
        vertex1.set( tPosX, 0f, 0f );
        vertex2.set( tPosX, -1f, -1f );
        vertex3.set( tPosX, +1f, -1f );
        tb.setPoints( triangleVertices );
        sb.applyImpulse( new Vector3f( 1f, 0f, 0f ) );

        while( !bm.hasContact( sb, tb ) ) {
            printBodyInfo();
            bm.update( 0.1f );

            if ( getEndCondition() ) {
                break;
            }
        }

        assertTrue( bm.hasContact( sb, tb ) );
    }

    @Test
    public void onePlusTestNegative () {
        println( "TEST START : 1 + negative" );
        sb.getTransform().addTranslation( 0f, 1f, 1.1f );

        float tPosX = 1f;
        vertex1.set( tPosX, 0f, 0f );
        vertex2.set( tPosX, -1f, -1f );
        vertex3.set( tPosX, +1f, -1f );
        tb.setPoints( triangleVertices );
        sb.applyImpulse( new Vector3f( 1f, 0f, 0f ) );

        while( !bm.hasContact( sb, tb ) ) {
            printBodyInfo();
            bm.update( 0.1f );

            if ( getEndCondition() ) {
                break;
            }
        }

        assertFalse( bm.hasContact( sb, tb ) );
    }

    public void println ( String s ) {
        // System.out.println(s);
    }

    public void printBodyInfo () {
        /*
         System.out.printf( " Sphere: pos( %.2f %.2f %.2f ) velocity ( %.2f %.2f %.2f ) ",
         sb.getTransform().getTranslationX(),
         sb.getTransform().getTranslationY(),
         sb.getTransform().getTranslationZ(),
         sb.getVelocity().getX(),
         sb.getVelocity().getY(),
         sb.getVelocity().getZ()
         );
         */
    }

    public boolean getEndCondition () {
        return sb.getTransform().getTranslationX() >= 4f || sb.getTransform().getTranslationX() <= -1;
    }
}
