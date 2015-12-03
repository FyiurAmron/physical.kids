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

        TriangleBody tb1 = (TriangleBody) body1;
        TriangleBody tb2 = (TriangleBody) body2;
        if ( tb1 == null || tb2 == null ) {
            throw new NullPointerException();
        }

        boolean tb1Collision = false;
        boolean tb2Collision = false;
        Vector3f cross;
        Vector3f crossPrev;
        Vector3f pointRelative;
        Line3f line = tb1.getPlane3f().intersect( tb2.getPlane3f() );

        // FIXME do poprawki, czy plaszczyzny sa rownolegle lub pokrywaja sie i co wtedy
        if ( line == null ) {
            return ( tb1.equals( tb2 ) );
        }
        Vector3f lineOrigin = line.getOrigin();

        cross = null;
        for( Vector3f point : tb1.points ) {
            pointRelative = new Vector3f( point );
            pointRelative.add( -lineOrigin.getX(), -lineOrigin.getY(), -lineOrigin.getZ() );
            crossPrev = ( cross != null ) ? cross : null;
            cross = line.getDirection().createCross( pointRelative );

            if ( cross.calcLength() == 0 ) {
                tb1Collision = true;
                break;
            }

            if ( crossPrev != null ) {
                if ( cross.dot( crossPrev ) < 0 ) {
                    tb1Collision = true;
                    break;
                }
            }
        }

        cross = null;
        for( Vector3f point : tb2.points ) {
            pointRelative = new Vector3f( point );
            pointRelative.add( -lineOrigin.getX(), -lineOrigin.getY(), -lineOrigin.getZ() );
            crossPrev = ( cross != null ) ? cross : null;
            cross = line.getDirection().createCross( pointRelative );

            if ( cross.calcLength() == 0 ) {
                tb1Collision = true;
                break;
            }

            if ( crossPrev != null ) {
                if ( cross.dot( crossPrev ) < 0 ) {
                    tb1Collision = true;
                    break;
                }
            }
        }

        return tb1Collision && tb2Collision;
    }
}
