package vax.math;

public class LineSegment3f extends Line3f {
    protected float length;

    public LineSegment3f () {
        super();
    }

    public LineSegment3f ( Vector3f origin, Vector3f distance, float length ) {
        super( origin, distance );
        this.length = length;
    }

    public LineSegment3f ( Vector3f point1, Vector3f point2 ) {
        super( point1, point2, null );
    }

    public float getLength () {
        if ( length == 0.0 ) {
            length = origin.distance( direction );
        }
        return length;
    }

    public void setLength ( float length ) {
        this.length = length;
    }

}
