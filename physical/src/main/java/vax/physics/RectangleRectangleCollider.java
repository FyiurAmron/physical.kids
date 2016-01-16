package vax.physics;

import vax.math.Line3f;
import vax.math.Plane3f;
import vax.math.Vector3f;

/**
 Created by Kuba on 2015-11-25.
 */
public class RectangleRectangleCollider extends Collider<RectangleBody, RectangleBody> {

    public RectangleRectangleCollider () {
        super( RectangleBody.class, RectangleBody.class );
    }

    @Deprecated
    @Override
    public boolean collide ( Body body1, Body body2 ) {
        throw new UnsupportedOperationException( "Not implemented" );
    }

    @Override
    public CollisionResult collideCR ( Body body1, Body body2 ) {

        if ( body1 == null || body2 == null ) {
            throw new NullPointerException();
        }

        RectangleBody b1 = (RectangleBody) body1;
        RectangleBody b2 = (RectangleBody) body2;

        if ( !collisionAABB( b1, b2 ) ) {
            return resultFalse;
        }

        Vector3f // intersection points
                point1A = null,
                point1B = null,
                point2A = null,
                point2B = null;

        // planes are parallel
        if ( b1.getPlane( new Plane3f() ).getNormal().equals( b2.getPlane( new Plane3f() ).getNormal() ) ) {

            // non-coincident planes
            if ( !( b1.getPlane( new Plane3f() ).getDistanceToOrigin() == b2.getPlane( new Plane3f() ).getDistanceToOrigin() ) ) {
                return resultFalse;
            }


            // TODO coincident planes
        }

        Line3f line = b1.getPlane( new Plane3f() ).intersect( b2.getPlane( new Plane3f() ) );

        // TODO setIntersectionPoints
        setIntersectionPoints( b1, line, point1A, point1B );
        if ( point1A == null ) {
            return resultFalse;
        }
        setIntersectionPoints( b2, line, point2A, point2B );
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

        // TODO getColloisionDepth
        if ( isBetween( point1A, point2A, point2B ) && isBetween( point1B, point2A, point2B ) ) {
            // tb1 vertex - tb2 plane collision,
            collisionNormal = b2.getPlane( new Plane3f() ).getNormal();
            depth = getCollisionDepth( b2, collisionNormal, point1A );
        } else if ( isBetween( point2A, point1A, point1B ) && isBetween( point2B, point1A, point1B ) ) {
            // tb1 plane - tb2 vertex collision,
            collisionNormal = b1.getPlane( new Plane3f() ).getNormal();
            depth = getCollisionDepth( b2, collisionNormal, point1A );
        } else {
            // edge - edge collision
            Vector3f insidePoint1;
            Vector3f insidePoint2;

            if ( isBetween( point1A, point1B, point2A ) && isBetween( point2A, point2B, point1A ) ) {
                insidePoint1 = point1A;
                insidePoint2 = point2A;
            } else if ( isBetween( point1A, point1B, point2B ) && isBetween( point2A, point2B, point1A ) ) {
                insidePoint1 = point1A;
                insidePoint2 = point2B;
            } else if ( isBetween( point1A, point1B, point2A ) && isBetween( point2A, point2B, point1B ) ) {
                insidePoint1 = point1B;
                insidePoint2 = point2A;
            } else if ( isBetween( point1A, point1B, point2B ) && isBetween( point2A, point2B, point1B ) ) {
                insidePoint1 = point1B;
                insidePoint2 = point2B;
            } else {
                // line segments 1A-1B, 2A-2B does not intersects
                return resultFalse;
            }

            // describing triangle edge as ( insidepoint - colinearPoint )
            Vector3f edge1 = new Vector3f( insidePoint1 );
            Vector3f edge2 = new Vector3f( insidePoint2 );

            // TODO areColinear( polygon.getPointsArray() )
            if ( areColinear( insidePoint1, b1.point1, b1.point2 ) ) {
                edge1.subtract( b1.point1 );
            } else if ( areColinear( insidePoint1, b1.point2, b1.point3 ) ) {
                edge1.subtract( b1.point2 );
            } else if ( areColinear( insidePoint1, b1.point3, b1.point1 ) ) {
                edge1.subtract( b1.point3 );
            }

            if ( areColinear( insidePoint2, b2.point1, b2.point2 ) ) {
                edge2.subtract( b2.point1 );
            } else if ( areColinear( insidePoint2, b2.point2, b2.point3 ) ) {
                edge2.subtract( b2.point2 );
            } else if ( areColinear( insidePoint2, b2.point3, b2.point1 ) ) {
                edge2.subtract( b2.point3 );
            }

            depth = new Vector3f( insidePoint1 ).subtract( insidePoint2 ).calcLength();
            collisionNormal = edge1.createCross( edge2 );

            return new CollisionResult( true, collisionNormal, depth );

        }


        if ( depth != 0f && collisionNormal != null )

        {
            return new CollisionResult( true, collisionNormal, depth );
        } else

        {
            return resultFalse;
        }

    }

    // TODO
    // outputs either two Vectors (line intersects triangle) or two null values (no intersection)
    private void setIntersectionPoints ( RectangleBody b, Line3f line, Vector3f outPoint1, Vector3f outPoint2 ) {
        Vector3f point, trianglePoint1, trianglePoint2;
        outPoint1 = null;
        outPoint2 = null;

        trianglePoint1 = b.point1;
        trianglePoint2 = b.point2;
        point = line.intersect( new Line3f( trianglePoint1, trianglePoint2 ) );
        if ( isBetween( point, trianglePoint1, trianglePoint2 ) ) {
            outPoint1 = point;
        }

        trianglePoint1 = b.point2;
        trianglePoint2 = b.point3;
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

        trianglePoint1 = b.point3;
        trianglePoint2 = b.point1;
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

     @param b
     @param collisionNormal
     @param intersectionPoint
     @return collisionDepth
     */
    // TODO
    private float getCollisionDepth ( RectangleBody b, Vector3f collisionNormal, Vector3f intersectionPoint ) {

        //if velocity == 0 returns max distance from intersectionPoint to triangleVertex
        if ( b.velocity == null || b.velocity.calcLengthSq() == 0 ) {
            return Math.max( Math.max(
                            new Vector3f( b.point1 ).subtract( intersectionPoint ).dot( collisionNormal ),
                            new Vector3f( b.point2 ).subtract( intersectionPoint ).dot( collisionNormal ) ),
                    new Vector3f( b.point3 ).subtract( intersectionPoint ).dot( collisionNormal ) );
        }

        // assume point1
        Vector3f point = b.point1;

        // check point2 depth
        if ( b.point2.dot( b.velocity ) >= point.dot( b.velocity ) ) {
            point = b.point2;
        }

        // check point3 depth
        if ( b.point3.dot( b.velocity ) >= point.dot( b.velocity ) ) {
            point = b.point3;
        }

        return new Vector3f( point ).subtract( intersectionPoint ).dot( collisionNormal );
    }

    /**
     checks if 2 vectors (p2->p1, p3->p2) are colinear

     @param p1
     @param p2
     @param p3
     @return
     */
    // TODO
    private boolean areColinear ( Vector3f p1, Vector3f p2, Vector3f p3 ) {
        Vector3f v1 = new Vector3f( p1 );
        v1.subtract( p2 );
        Vector3f v2 = new Vector3f( p2 );
        v2.subtract( p3 );
        if ( ( v1.getX() / v2.getX() ) == ( v1.getY() / v2.getY() ) && ( v1.getY() / v2.getY() == v1.getZ() / v2.getZ() ) ) {
            return true;
        } else {
            return false;
        }
    }

    @Deprecated
    private void getMin ( Vector3f v1, Vector3f v2, Vector3f v3, Vector3f v4, Vector3f out ) {
        out.setX( Math.min( Math.min( Math.min( v1.getX(), v2.getX() ), v3.getX() ), v4.getX() ) );
        out.setY( Math.min( Math.min( Math.min( v1.getY(), v2.getY() ), v3.getY() ), v4.getY() ) );
        out.setZ( Math.min( Math.min( Math.min( v1.getZ(), v2.getZ() ), v3.getZ() ), v4.getZ() ) );
    }

    @Deprecated
    private void getMax ( Vector3f v1, Vector3f v2, Vector3f v3, Vector3f v4, Vector3f out ) {
        out.setX( Math.max( Math.max( Math.max( v1.getX(), v2.getX() ), v3.getX() ), v4.getX() ) );
        out.setY( Math.max( Math.max( Math.max( v1.getY(), v2.getY() ), v3.getY() ), v4.getY() ) );
        out.setZ( Math.max( Math.max( Math.max( v1.getZ(), v2.getZ() ), v3.getZ() ), v4.getZ() ) );
    }

    private void getMin ( Vector3f[] vArray, Vector3f out ) {
        out.setX( vArray[0].getX() );
        out.setY( vArray[0].getY() );
        out.setZ( vArray[0].getZ() );
        for( int i = 1; i < vArray.length; i++ ) {
            out.setX( Math.min( out.getX(), vArray[i].getX() ) );
            out.setY( Math.min( out.getY(), vArray[i].getY() ) );
            out.setZ( Math.min( out.getZ(), vArray[i].getZ() ) );
        }
    }

    private void getMax ( Vector3f[] vArray, Vector3f out ) {
        out.setX( vArray[0].getX() );
        out.setY( vArray[0].getY() );
        out.setZ( vArray[0].getZ() );
        for( int i = 1; i < vArray.length; i++ ) {
            out.setX( Math.max( out.getX(), vArray[i].getX() ) );
            out.setY( Math.max( out.getY(), vArray[i].getY() ) );
            out.setZ( Math.max( out.getZ(), vArray[i].getZ() ) );
        }
    }

    private boolean collisionAABB ( RectangleBody b1, RectangleBody b2 ) {
        Vector3f //
                b1Min = new Vector3f(),
                b1Max = new Vector3f(),
                b2Min = new Vector3f(),
                b2Max = new Vector3f();
        Vector3f[] //
                b1Points = b1.getPointsArray(),
                b2Points = b2.getPointsArray();

        getMin( b1Points, b1Min );
        getMax( b1Points, b1Max );

        getMin( b2Points, b2Min );
        getMax( b2Points, b2Max );

        return b1Max.getX() >= b2Min.getX() && b2Max.getX() >= b1Min.getX()
                && b1Max.getY() >= b2Min.getY() && b2Max.getY() >= b1Min.getY()
                && b1Max.getZ() >= b2Min.getZ() && b2Max.getZ() >= b1Min.getZ();
    }
}
