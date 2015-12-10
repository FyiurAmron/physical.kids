package vax.physics;

import vax.math.Line3f;
import vax.math.Plane3f;
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

        Plane3f tb1p = tb1.getPlane( new Plane3f() ), tb2p = tb2.getPlane( new Plane3f() );
        Line3f line = tb1p.intersect( tb2p );

        // FIXME do poprawki, czy plaszczyzny sa rownolegle lub pokrywaja sie i co wtedy
        if ( line == null ) {
            return ( tb1.equals( tb2 ) );
        }

        Vector3f[] points = { new Vector3f(), new Vector3f(), new Vector3f() };
        tb1.getPoints( points[0], points[1], points[2] );
        if ( !checkTriangle( line, points ))
            return false;
        tb2.getPoints( points[0], points[1], points[2] );
        return checkTriangle( line, points );
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
