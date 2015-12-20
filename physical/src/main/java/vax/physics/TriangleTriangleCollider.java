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

    @Deprecated
    @Override
    public boolean collide ( Body body1, Body body2 ) {
        if ( body1 == null || body2 == null ) {
            throw new NullPointerException();
        }

        TriangleBody tb1 = (TriangleBody) body1;
        TriangleBody tb2 = (TriangleBody) body2;

        if ( !collisionAABB( tb1, tb2 ) ) {
            return false;
        }
        Plane3f tb1p = tb1.getPlane( new Plane3f() ), tb2p = tb2.getPlane( new Plane3f() );
        Line3f line = tb1p.intersect( tb2p );

        // FIXME do poprawki, czy plaszczyzny sa rownolegle lub pokrywaja sie i co wtedy
        if ( line == null ) {
            return ( tb1.equals( tb2 ) );
        }

        Vector3f[] points = { new Vector3f(), new Vector3f(), new Vector3f() };
        tb1.getPoints( points[0], points[1], points[2] );
        if ( !checkTriangle( line, points ) ) {
            return false;
        }
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

    @Override
    public CollisionResult collideCR ( Body body1, Body body2 ) {

        if ( body1 == null || body2 == null ) {
            throw new NullPointerException();
        }

        TriangleBody tb1 = (TriangleBody) body1;
        TriangleBody tb2 = (TriangleBody) body2;
        CollisionResult resultFalse = new CollisionResult( false, null, Float.POSITIVE_INFINITY );

        if ( !collisionAABB( tb1, tb2 ) ) {
            return resultFalse;
        }

        Vector3f // intersection points
                point1A = null,
                point1B = null,
                point2A = null,
                point2B = null;

        // planes are parallel
        if ( tb1.getPlane( new Plane3f() ).getNormal().equals( tb2.getPlane( new Plane3f() ).getNormal() ) ) {

            // non-coincident planes
            if ( !( tb1.getPlane( new Plane3f() ).getDistanceToOrigin() == tb2.getPlane( new Plane3f() ).getDistanceToOrigin() ) ) {
                return resultFalse;
            }

            // TODO coincident planes
        }

        Line3f line = tb1.getPlane( new Plane3f() ).intersect( tb2.getPlane( new Plane3f() ) );

        setIntersectionPoints( tb1, line, point1A, point1B );
        if ( point1A == null ) {
            return resultFalse;
        }
        setIntersectionPoints( tb2, line, point2A, point2B );
        if ( point1B == null ) {
            return resultFalse;
        }

        // pre-check (to be deleted), three '!betweens' satisfies false result
        if ( !isBetween( point1A, point2A, point2B )
                && !isBetween( point1B, point2A, point2B )
                && !isBetween( point2A, point1A, point1B ) ) {
            return resultFalse;
        }

        Vector3f collisionNormal = null;
        float depth = 0f;
        if ( isBetween( point1A, point2A, point2B ) && isBetween( point1B, point2A, point2B ) ) {
            //tb1 vertex - tb2 plane collision,
            collisionNormal = tb2.getPlane( new Plane3f() ).getNormal();
            depth = getCollisionDepth( tb2, collisionNormal, point1A );
        } else if ( isBetween( point2A, point1A, point1B ) && isBetween( point2B, point1A, point1B ) ) {
            //tb1 plane - tb2 vertex collision,
            collisionNormal = tb1.getPlane( new Plane3f() ).getNormal();
            depth = getCollisionDepth( tb2, collisionNormal, point1A );
        } else if ( false ) {
            // TODO Edge - Edge collision
        }

        if ( depth != 0f && collisionNormal != null ) {
            return new CollisionResult( true, collisionNormal, depth );
        } else {
            return resultFalse;
        }

    }

    // outputs either two Vectors (line intersects triangle) or two null values (no intersection)
    private void setIntersectionPoints ( TriangleBody tb, Line3f line, Vector3f outPoint1, Vector3f outPoint2 ) {
        Vector3f point, trianglePoint1, trianglePoint2;
        outPoint1 = null;
        outPoint2 = null;

        trianglePoint1 = tb.point1;
        trianglePoint2 = tb.point2;
        point = line.intersect( new Line3f( trianglePoint1, trianglePoint2 ) );
        if ( isBetween( point, trianglePoint1, trianglePoint2 ) ) {
            outPoint1 = point;
        }

        trianglePoint1 = tb.point2;
        trianglePoint2 = tb.point3;
        point = line.intersect( new Line3f( trianglePoint1, trianglePoint2 ) );
        if ( isBetween( point, trianglePoint1, trianglePoint2 ) ) {
            if ( outPoint1 == null ) {
                outPoint1 = point;
            } else {
                outPoint2 = point;
                return;
            }
        }

        if ( outPoint1 == null ) {
            return;
        }

        trianglePoint1 = tb.point3;
        trianglePoint2 = tb.point1;
        point = line.intersect( new Line3f( trianglePoint1, trianglePoint2 ) );
        if ( isBetween( point, trianglePoint1, trianglePoint2 ) ) {
            outPoint2 = point;
        }

    }

    /**
     assumes that all points are colinear

     @param point
     @param point1
     @param point2
     @return true if point is between point1 and point2
     */
    private boolean isBetween ( Vector3f point, Vector3f point1, Vector3f point2 ) {
        return ( point.getX() <= point1.getX() && point.getX() >= point2.getX() )
                || ( point.getX() <= point2.getX() && point.getX() >= point1.getX() );
    }

    /**
     checks which one from 3 triangle vertices should be chosen to calculate depth based on triangle velocity,
     then calculates collision depth from intersectionPoint(point on intersection line) to chosen triangle vertex

     @param tb
     @param collisionNormal
     @param intersectionPoint
     @return collisionDepth
     */
    private float getCollisionDepth ( TriangleBody tb, Vector3f collisionNormal, Vector3f intersectionPoint ) {

        //if velocity == 0 returns max distance from intersectionPoint to triangleVertex
        if ( tb.velocity == null || tb.velocity.calcLengthSq() == 0 ) {
            return Math.max( Math.max(
                            new Vector3f( tb.point1 ).subtract( intersectionPoint ).dot( collisionNormal ),
                            new Vector3f( tb.point2 ).subtract( intersectionPoint ).dot( collisionNormal ) ),
                    new Vector3f( tb.point3 ).subtract( intersectionPoint ).dot( collisionNormal ) );
        }

        // assume point1
        Vector3f point = tb.point1;

        // check point2 depth
        if ( tb.point2.dot( tb.velocity ) >= point.dot( tb.velocity ) ) {
            point = tb.point2;
        }

        // check point3 depth
        if ( tb.point3.dot( tb.velocity ) >= point.dot( tb.velocity ) ) {
            point = tb.point3;
        }

        return new Vector3f( point ).subtract( intersectionPoint ).dot( collisionNormal );
    }

    private void getMin ( Vector3f v1, Vector3f v2, Vector3f v3, Vector3f out ) {
        out.setX( Math.min( Math.min( v1.getX(), v2.getX() ), v3.getX() ) );
        out.setY( Math.min( Math.min( v1.getY(), v2.getY() ), v3.getY() ) );
        out.setZ( Math.min( Math.min( v1.getZ(), v2.getZ() ), v3.getZ() ) );
    }

    private void getMax ( Vector3f v1, Vector3f v2, Vector3f v3, Vector3f out ) {
        out.setX( Math.max( Math.max( v1.getX(), v2.getX() ), v3.getX() ) );
        out.setY( Math.max( Math.max( v1.getY(), v2.getY() ), v3.getY() ) );
        out.setZ( Math.max( Math.max( v1.getZ(), v2.getZ() ), v3.getZ() ) );
    }

    private boolean collisionAABB ( TriangleBody tb1, TriangleBody tb2 ) {
        Vector3f tb1Min = new Vector3f(),
                tb1Max = new Vector3f(),
                tb2Min = new Vector3f(),
                tb2Max = new Vector3f(),
                v11 = tb1.point1,
                v12 = tb1.point2,
                v13 = tb1.point3,
                v21 = tb2.point1,
                v22 = tb2.point2,
                v23 = tb2.point3;

        getMin( v11, v12, v13, tb1Min );
        getMax( v11, v12, v13, tb1Max );

        getMin( v21, v22, v23, tb2Min );
        getMax( v21, v22, v23, tb2Max );

        return tb1Max.getX() >= tb2Min.getX() && tb2Max.getX() >= tb1Min.getX()
                && tb1Max.getY() >= tb2Min.getY() && tb2Max.getY() >= tb1Min.getY()
                && tb1Max.getZ() >= tb2Min.getZ() && tb2Max.getZ() >= tb1Min.getZ();
    }
}
