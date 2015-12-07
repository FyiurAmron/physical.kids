package vax.physics;

import vax.math.Line3f;
import vax.math.Vector3f;

/**
 Created by Kuba on 2015-11-25.
 */
public class TriangleTriangleCollider extends Collider<TriangleBody, TriangleBody> {

    public TriangleTriangleCollider () {
        super( TriangleBody.class, TriangleBody.class );
    }

    @Override
    public boolean collide ( Body body1, Body body2 ) {
        if ( body1 == null || body2 == null ) {
            throw new NullPointerException();
        }

        TriangleBody tb1 = (TriangleBody) body1;
        TriangleBody tb2 = (TriangleBody) body2;

        Line3f line = tb1.getPlane3f().intersect( tb2.getPlane3f() );

        // FIXME do poprawki, czy plaszczyzny sa rownolegle lub pokrywaja sie i co wtedy
        if ( line == null ) {
            return ( tb1.equals( tb2 ) );
        }

        return checkTriangle( line, tb1.getPoints() ) && checkTriangle( line, tb2.getPoints() );
    }

    private boolean checkTriangle ( Line3f line, Vector3f[] points ) {
        Vector3f cross = new Vector3f();
        Vector3f crossPrev = new Vector3f();
        Vector3f pointRelative = new Vector3f();

        Vector3f lineOrigin = line.getOrigin(), direction = line.getDirection();

        for( int i = 0; i < points.length; i++ ) {
            Vector3f point = points[i];
            pointRelative.set( point ).subtract( lineOrigin );
            crossPrev.set( cross );
            cross.setToCross( direction, pointRelative );

            if ( cross.isZero() || ( i != 0 && cross.dot( crossPrev ) < 0 ) ) {
                return true;
            }
        }
        return false;
    }
}
