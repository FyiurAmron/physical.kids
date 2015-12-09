package vax.math;

public class Line3f {
    protected Vector3f origin, direction;

    /**
     Constructs a Line3f with no backing data, which should be set before actually using it.
     Useful for serialization, subclassing etc.
     */
    public Line3f () {
        //this( new Vector3f(), new Vector3f() );
    }

    /**
     Constructs an infinite line, based on origin and direction.
     The original values are referenced, not copied.
     Note that direction <b>doesn't have</b> to be normalized, but in most cases it <b>should</b>.

     @param origin
     @param direction
     */
    public Line3f ( Vector3f origin, Vector3f direction ) {
        this.origin = origin;
        this.direction = direction;
    }

    /**
     Constructs an infinite line, based on two points it contains.
     The original values are copied, not referenced.

     @param point1
     @param point2
     @param normalize true if the resulting direction vector should be normalized
     */
    public Line3f ( Vector3f point1, Vector3f point2, boolean normalize ) {
        this( new Vector3f( point1 ), new Vector3f( point2 ) );
        direction.subtract( point1 );
        if ( normalize ) {
            direction.normalize();
        }
    }

    public Vector3f getOrigin () {
        return origin;
    }

    public Vector3f getDirection () {
        return direction;
    }

    public void setOrigin ( Vector3f origin ) {
        this.origin = origin;
    }

    public void setDirection ( Vector3f direction ) {
        this.direction = direction;
    }

    /**
     Sets the components of output (direction, origin) to this object's values.

     @param output
     @return output, for chaining
     */
    public Line3f set ( Line3f output ) {
        output.direction.set( direction );
        output.origin.set( origin );
        return output;
    }

    //    http://mathworld.wolfram.com/Point-LineDistance3-Dimensional.html
    public float calcDistance ( Vector3f point ) {
        throw new UnsupportedOperationException( "not yet implemented" );
    }

//    http://mathworld.wolfram.com/Line-LineDistance.html
    public float calcDistance ( Line3f lineSegment ) {
        throw new UnsupportedOperationException( "not yet implemented" );
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
        Vector3f target = new Vector3f();
        return intersect( line, target ) ? target : null;
    }

    public boolean intersect ( Line3f line, Vector3f target ) {
        throw new UnsupportedOperationException( "not yet implemented" );
    }
}
