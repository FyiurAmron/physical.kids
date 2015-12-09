package vax.math;

/**

 @author toor
 */
public class LineSegment3f {
    protected Vector3f point1, point2;

    public LineSegment3f () {
    }

    public LineSegment3f ( Vector3f point1, Vector3f point2 ) {
        this.point1 = point1;
        this.point2 = point2;
    }

    public Vector3f getPoint1 () {
        return point1;
    }

    public Vector3f getPoint2 () {
        return point2;
    }

    public void setPoint1 ( Vector3f point1 ) {
        this.point1 = point1;
    }

    public void setPoint2 ( Vector3f point2 ) {
        this.point2 = point2;
    }

    /**
     Sets the components of output (point1, point2) to this object's values.

     @param output
     @return output, for chaining
     */
    public LineSegment3f set ( LineSegment3f output ) {
        output.point1.set( point1 );
        output.point2.set( point2 );
        return output;
    }

//    http://mathworld.wolfram.com/Point-LineDistance3-Dimensional.html
    public float calcDistance ( Vector3f point ) {
        throw new UnsupportedOperationException( "not yet implemented" );
    }

//    http://mathworld.wolfram.com/Line-LineDistance.html
    public float calcDistance ( Line3f lineSegment ) {
        return lineSegment.calcDistance( this );
    }

//    http://mathworld.wolfram.com/Line-LineDistance.html
    public float calcDistance ( LineSegment3f lineSegment ) {
        throw new UnsupportedOperationException( "not yet implemented" );
    }

    public Vector3f intersect ( LineSegment3f lineSegment ) {
        Vector3f target = new Vector3f();
        return intersect( lineSegment, target ) ? target : null;
    }

    public boolean intersect ( LineSegment3f lineSegment, Vector3f target ) {
        throw new UnsupportedOperationException( "not yet implemented" );
    }

    public Vector3f intersect ( Line3f line ) {
        return line.intersect( this );
    }

    public boolean intersect ( Line3f line, Vector3f target ) {
        return line.intersect( this, target );
    }

    public float calcLength () {
        return point2.calcDistance( point1 );
    }

    public Vector3f calcDirection ( Vector3f target, boolean normalize ) {
        target.set( point2 ).subtract( point1 );
        if ( normalize ) {
            target.normalize();
        }
        return target;
    }

    public Vector3f calcDirection ( Vector3f target ) {
        return calcDirection( target, true );
    }

    public Vector3f calcDirection ( boolean normalize ) {
        return calcDirection( new Vector3f(), normalize );
    }

    public Vector3f calcDirection () {
        return calcDirection( new Vector3f(), true );
    }
}
