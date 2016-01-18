package vax.physics;

import vax.math.Line3f;
import vax.math.Plane3f;
import vax.math.Vector3f;

/**
 Created by Kuba on 2015-11-25.
 */
public class PolygonPolygonCollider extends Collider<PolygonBody, PolygonBody> {

    Vector3f[] vertices3Tmp = new Vector3f[3];
    Vector3f[] vertices4Tmp = new Vector3f[4];
    Vector3f //
            /*
            vectorValues usage: AABB, then Polygons intersection points
            vectorTmp usage: areCollinear(), setIntersectionPoints();
             */
            vectorValue1 = new Vector3f(),
            vectorValue2 = new Vector3f(),
            vectorValue3 = new Vector3f(),
            vectorValue4 = new Vector3f(),
            vectorTmp1 = new Vector3f(),
            vectorTmp2 = new Vector3f(),
            vectorTmp3 = new Vector3f();
    Plane3f //
            plane1 = new Plane3f(),
            plane2 = new Plane3f();
    Line3f lineTmp = new Line3f();

    public PolygonPolygonCollider () {
        super( PolygonBody.class, PolygonBody.class );
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

        PolygonBody b1 = (PolygonBody) body1;
        PolygonBody b2 = (PolygonBody) body2;

        if ( !collisionAABB( b1, b2 ) ) {
            return resultFalse;
        }


        b1.getPlane( plane1 );
        b2.getPlane( plane2 );

        // planes are parallel
        if ( plane1.getNormal().equals( plane2.getNormal() ) ) {

            // non-coincident planes
            if ( !( plane1.getDistanceToOrigin() == plane2.getDistanceToOrigin() ) ) {
                return resultFalse;
            }

            // TODO coincident planes
        }

        Line3f intersectionLine = plane1.intersect( plane2 );
        Vector3f // intersection points
                point1A = vectorValue1,
                point1B = vectorValue2,
                point2A = vectorValue3,
                point2B = vectorValue4;


        if ( !setIntersectionPoints( b1, intersectionLine, point1A, point1B ) ) {
            return resultFalse;
        }

        if ( !setIntersectionPoints( b2, intersectionLine, point2A, point2B ) ) {
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

        // TODO getCollisionDepth
        // consider Plane3f getNormal( Vector3f output )
        if ( isBetween( point1A, point2A, point2B ) && isBetween( point1B, point2A, point2B ) ) {
            // tb1 vertex - tb2 plane collision,
            collisionNormal = plane2.getNormal();
            depth = getCollisionDepth( b2, collisionNormal, point1A );
        } else if ( isBetween( point2A, point1A, point1B ) && isBetween( point2B, point1A, point1B ) ) {
            // tb1 plane - tb2 vertex collision,
            collisionNormal = plane1.getNormal();
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
                // line segments 1A-1B, 2A-2B does not intersect
                return resultFalse;
            }

            // describing triangle edge as ( insidepoint - colinearPoint )
            Vector3f edge1 = new Vector3f( insidePoint1 );
            Vector3f edge2 = new Vector3f( insidePoint2 );

            // TODO areColinear( polygon.getVerticesArray() )
//            if ( areColinear( insidePoint1, b1.point1, b1.point2 ) ) {
//                edge1.subtract( b1.point1 );
//            } else if ( areColinear( insidePoint1, b1.point2, b1.point3 ) ) {
//                edge1.subtract( b1.point2 );
//            } else if ( areColinear( insidePoint1, b1.point3, b1.point1 ) ) {
//                edge1.subtract( b1.point3 );
//            }
//
//            if ( areColinear( insidePoint2, b2.point1, b2.point2 ) ) {
//                edge2.subtract( b2.point1 );
//            } else if ( areColinear( insidePoint2, b2.point2, b2.point3 ) ) {
//                edge2.subtract( b2.point2 );
//            } else if ( areColinear( insidePoint2, b2.point3, b2.point1 ) ) {
//                edge2.subtract( b2.point3 );
//            }
//
//            depth = new Vector3f( insidePoint1 ).subtract( insidePoint2 ).calcLength();
//            collisionNormal = edge1.createCross( edge2 );
//
//            return new CollisionResult( true, collisionNormal, depth );
            // FIXME
            return resultFalse;
        }


        if ( depth != 0f && collisionNormal != null ) {
            return new CollisionResult( true, collisionNormal, depth );
        }

        return resultFalse;
    }


    /**
     checks if planesIntersectionLine intersects polygonBody, sets two intersectionPoints
     <p>
     take polygon edge
     find ( edge , planesIntersectionLine ) intersectionPoint
     check if intersectionPoint is between edge vertices
     set output
     <p>
     ASSUMES that polygon vertices are sorted -> Edges are designated by vertices pairs {v1, v2}, {v2, v3}, .. {vN, v1}

     @param b       one out of two colliding bodies
     @param line    planesIntersectionLine
     @param output1 intersectionPoint1
     @param output2 intersectionPoint2
     @return true if two intersection points are found, false otherwise
     */
    private boolean setIntersectionPoints ( PolygonBody b, Line3f line, Vector3f output1, Vector3f output2 ) {
        Line3f edge = lineTmp;
        boolean outputSet1 = false;
        Vector3f prevVertex = vectorTmp1;
        Vector3f vertex = vectorTmp2;
        Vector3f intersectionPoint = vectorTmp3;
        b.getVerticesArray( getTmpArray( b ) );

        for( int i = 0; i < getTmpArray( b ).length; i++ ) {

            vertex.set( getTmpArray( b )[i] );
            if ( i == 0 ) {
                prevVertex.set( getTmpArray( b )[b.getVerticesCount() - 1] );
            } else {
                prevVertex.set( getTmpArray( b )[i - 1] );
            }

            edge.setOrigin( prevVertex );
            edge.setDirection( (Vector3f) intersectionPoint.set( vertex ).subtract( prevVertex ) ); // temporary intersectionPoint usage
            intersectionPoint.set( line.intersect( edge ) );


            if ( isBetween( intersectionPoint, prevVertex, vertex ) ) {
                if ( !outputSet1 ) {
                    output1.set( intersectionPoint );
                    outputSet1 = true;
                } else {
                    output2.set( intersectionPoint );
                    return true;
                }
            }
        }

        return false;
    }

    /**
     assumes that all points are collinear

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
     checks which one from polygon vertices should be chosen to calculate depth based on polygon velocity,
     then calculates collision depth from intersectionPoint(point on intersection line) to chosen polygon vertex

     @param b
     @param collisionNormal
     @param intersectionPoint
     @return collisionDepth
     */
    private float getCollisionDepth ( PolygonBody b, Vector3f collisionNormal, Vector3f intersectionPoint ) {
        // FIXME
//        //if velocity == 0 returns max distance from intersectionPoint to triangleVertex
//        if ( b.velocity == null || b.velocity.calcLengthSq() == 0 ) {
//            return Math.max( Math.max(
//                            new Vector3f( b.point1 ).subtract( intersectionPoint ).dot( collisionNormal ),
//                            new Vector3f( b.point2 ).subtract( intersectionPoint ).dot( collisionNormal ) ),
//                    new Vector3f( b.point3 ).subtract( intersectionPoint ).dot( collisionNormal ) );
//        }
//
//        // assume point1
//        Vector3f point = b.point1;
//
//        // check point2 depth
//        if ( b.point2.dot( b.velocity ) >= point.dot( b.velocity ) ) {
//            point = b.point2;
//        }
//
//        // check point3 depth
//        if ( b.point3.dot( b.velocity ) >= point.dot( b.velocity ) ) {
//            point = b.point3;
//        }
//
//        return new Vector3f( point ).subtract( intersectionPoint ).dot( collisionNormal );
        return 0f;
    }

    /**
     checks if 3 points (p1, p2, p3) (2 vectors (p2->p1, p3->p2)) are colinear

     @param p1
     @param p2
     @param p3
     @return
     */
    private boolean areCollinear ( Vector3f p1, Vector3f p2, Vector3f p3 ) {
        vectorTmp1.set( p1 ).subtract( p2 );
        vectorTmp2.set( p2 ).subtract( p3 );

        return vectorTmp1.getX() / vectorTmp2.getX() == vectorTmp1.getY() / vectorTmp2.getY()
                && vectorTmp1.getY() / vectorTmp2.getY() == vectorTmp1.getZ() / vectorTmp2.getZ();
    }

    private Vector3f[] getTmpArray ( PolygonBody pb ) {
        if ( pb.getVerticesCount() == 3 ) {
            return vertices3Tmp;
        } else {
            return vertices4Tmp;
        }
    }

    private boolean collisionAABB ( PolygonBody b1, PolygonBody b2 ) {

        b1.getVerticesArray( getTmpArray( b1 ) );
        getMin( getTmpArray( b1 ), vectorValue1 );
        getMax( getTmpArray( b1 ), vectorValue2 );

        b2.getVerticesArray( getTmpArray( b2 ) );
        getMin( getTmpArray( b2 ), vectorValue3 );
        getMax( getTmpArray( b2 ), vectorValue4 );

        Vector3f // readability purposes
                b1Min = vectorValue1,
                b1Max = vectorValue2,
                b2Min = vectorValue3,
                b2Max = vectorValue4;

        return b1Max.getX() >= b2Min.getX() && b2Max.getX() >= b1Min.getX()
                && b1Max.getY() >= b2Min.getY() && b2Max.getY() >= b1Min.getY()
                && b1Max.getZ() >= b2Min.getZ() && b2Max.getZ() >= b1Min.getZ();
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


}
