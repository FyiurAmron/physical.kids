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
		if ( !checkTriangle( line, points ) )
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

		// three '!betweens' satisfies false result
		if ( !isBetween( point1A, point2A, point2B )
				&& !isBetween( point1B, point2A, point2B )
				&& !isBetween( point2A, point1A, point1B ) ) {
			return resultFalse;
		}

		// TODO collision result, direction, depth, handling the results
		CollisionResult result = new CollisionResult( true, null, 0f );
		throw new UnsupportedOperationException( "Not implemented yet!" );
		//return result;
	}

	// outputs either two Vectors (line intersects triangle) or two null values (no intersection)
	private void setIntersectionPoints ( TriangleBody tb, Line3f line, Vector3f outPoint1, Vector3f outPoint2 ) {
		Vector3f point;
		Vector3f trianglePoint1;
		Vector3f trianglePoint2;
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

	// isBetween assumes that all points are colinear, return if point is between <point1, point2>
	private boolean isBetween ( Vector3f point, Vector3f point1, Vector3f point2 ) {
		return ( point.getX() <= point1.getX() && point.getX() >= point2.getX() )
				|| ( point.getX() <= point2.getX() && point.getX() >= point1.getX() );
	}


	private boolean collisionAABB ( TriangleBody tb1, TriangleBody tb2 ) {
		Vector3f
				tb1Min = new Vector3f(),
				tb1Max = new Vector3f(),
				tb2Min = new Vector3f(),
				tb2Max = new Vector3f();

		tb1Min.setX( Math.min( Math.min( tb1.point1.getX(), tb1.point2.getX() ), tb1.point3.getX() ) );
		tb1Min.setY( Math.min( Math.min( tb1.point1.getY(), tb1.point2.getY() ), tb1.point3.getY() ) );
		tb1Min.setZ( Math.min( Math.min( tb1.point1.getZ(), tb1.point2.getZ() ), tb1.point3.getZ() ) );
		tb1Max.setX( Math.max( Math.max( tb1.point1.getX(), tb1.point2.getX() ), tb1.point3.getX() ) );
		tb1Max.setY( Math.max( Math.max( tb1.point1.getY(), tb1.point2.getY() ), tb1.point3.getY() ) );
		tb1Max.setZ( Math.max( Math.max( tb1.point1.getZ(), tb1.point2.getZ() ), tb1.point3.getZ() ) );

		tb2Min.setX( Math.min( Math.min( tb2.point1.getX(), tb2.point2.getX() ), tb2.point3.getX() ) );
		tb2Min.setY( Math.min( Math.min( tb2.point1.getY(), tb2.point2.getY() ), tb2.point3.getY() ) );
		tb2Min.setZ( Math.min( Math.min( tb2.point1.getZ(), tb2.point2.getZ() ), tb2.point3.getZ() ) );
		tb2Max.setX( Math.max( Math.max( tb2.point1.getX(), tb2.point2.getX() ), tb2.point3.getX() ) );
		tb2Max.setY( Math.max( Math.max( tb2.point1.getY(), tb2.point2.getY() ), tb2.point3.getY() ) );
		tb2Max.setZ( Math.max( Math.max( tb2.point1.getZ(), tb2.point2.getZ() ), tb2.point3.getZ() ) );

		if ( tb1Max.getX() < tb2Min.getX() || tb2Max.getX() < tb1Min.getX() ) {
			return false;
		}
		if ( tb1Max.getY() < tb2Min.getY() || tb2Max.getY() < tb1Min.getY() ) {
			return false;
		}
		if ( tb1Max.getZ() < tb2Min.getZ() || tb2Max.getZ() < tb1Min.getZ() ) {
			return false;
		}

		return true;
	}
}
