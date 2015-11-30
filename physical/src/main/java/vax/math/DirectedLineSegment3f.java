package vax.math;

/**
 An extension of Line3f, storing the direction and calcLength from the origin.
 In cases when a line segment defined exclusively by two points is needed, use LineSegment3f instead.

 @author toor
 */
public class DirectedLineSegment3f extends Line3f {
    protected float length;

    /**
     Constructs a DirectedLineSegment3f with no backing data, which should be set before actually using it.
     Useful for serialization, subclassing etc.
     */
    public DirectedLineSegment3f () {
        super();
    }

    /**
     Note: values of the points <b>are</b> referenced by this object. Use manual copy if that behaviour is not intended.

     @param origin
     @param distance
     @param length
     */
    public DirectedLineSegment3f ( Vector3f origin, Vector3f distance, float length ) {
        super( origin, distance );
        this.length = length;
    }

    /**
     Note: values of the points are <b>not</b> referenced by this object (point1 is explicitly copied).

     @param point1
     @param point2
     */
    public DirectedLineSegment3f ( Vector3f point1, Vector3f point2 ) {
        super();
        origin = point1.copy();
        length = point2.calcDistance( point1 );
    }

    public float getLength () {
        return length;
    }

    public void setLength ( float length ) {
        this.length = length;
    }
}
