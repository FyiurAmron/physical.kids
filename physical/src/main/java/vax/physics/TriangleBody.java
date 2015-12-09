package vax.physics;

import vax.math.Plane3f;
import vax.math.Vector3f;

/**
 Created by Kuba on 2015-12-02.
 */
public class TriangleBody extends PlaneBody {
    protected final Vector3f //
            point1 = new Vector3f(),
            point2 = new Vector3f(),
            point3 = new Vector3f();

    /**
     No-arg for serialization/subclassing.
     */
    public TriangleBody () {
    }

    /**
     equivalent to <code>new TriangleBody().setPoints(v1,v2,v3)</code>

     @param v1
     @param v2
     @param v3
     */
    public TriangleBody ( Vector3f v1, Vector3f v2, Vector3f v3 ) {
        _setPoints( v1, v2, v3 );
    }

    /**
     Sets the outputs to values contained in this TriangleBody's corner points.

     @param output1
     @param output2
     @param output3
     */
    public void getPoints ( Vector3f output1, Vector3f output2, Vector3f output3 ) {
        output1.set( point1 );
        output2.set( point2 );
        output3.set( point3 );
    }

    /**
     Copies the values from source vectors into local data.

     @param v1
     @param v2
     @param v3
     */
    public void setPoints ( Vector3f v1, Vector3f v2, Vector3f v3 ) {
        _setPoints( v1, v2, v3 );
    }

    private void _setPoints ( Vector3f v1, Vector3f v2, Vector3f v3 ) {
        setPlane3f( new Plane3f( v1, v2, v3 ) );
        point1.set( v1 );
        point2.set( v2 );
        point3.set( v3 );
    }

    public boolean equals ( TriangleBody tb ) {
        return point1.equals( tb.point1 )
                && point2.equals( tb.point2 )
                && point3.equals( tb.point3 );
    }

    @Override
    public int hashCode () {
        throw new UnsupportedOperationException(); // TODO [Jakub Sengerski] - always implement hashCode() when implementing equals()
    }
}
