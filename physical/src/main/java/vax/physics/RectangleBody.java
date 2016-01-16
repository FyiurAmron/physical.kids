package vax.physics;

import vax.math.Plane3f;
import vax.math.Vector3f;

/**
 Created by Kuba on 2015-01-15.
 */
public class RectangleBody extends Body {
    protected final Vector3f //
            point1 = new Vector3f(),
            point2 = new Vector3f(),
            point3 = new Vector3f(),
            point4 = new Vector3f();
    protected Plane3f plane = new Plane3f();

    /**
     No-arg for serialization/subclassing.
     */
    public RectangleBody () {
    }

    /**
     equivalent to <code>new TriangleBody().setPoints(v1,v2,v3)</code>

     @param v1
     @param v2
     @param v3
     */
    public RectangleBody ( Vector3f v1, Vector3f v2, Vector3f v3 ) {
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
     Sets the outputs to values contained in this TriangleBody's corner points.

     @param output1
     @param output2
     @param output3
     @param output4
     */
    public void getPoints ( Vector3f output1, Vector3f output2, Vector3f output3, Vector3f output4 ) {
        output1.set( point1 );
        output2.set( point2 );
        output3.set( point3 );
        output4.set( point4 );
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
        if ( v1 == null || v2 == null || v3 == null ) {
            throw new IllegalArgumentException();
        }
        point1.set( v1 );
        point2.set( v2 );
        point3.set( v3 );
        plane.set( point1, point2, point3 );
        designatePoint4();
    }

    // TODO
    private void designatePoint4 () {
        throw new UnsupportedOperationException( "Not yet implemented" );
    }

    /**
     Copies local plane values to output.

     @param output
     @return reference to output, for chaining
     */
    public Plane3f getPlane ( Plane3f output ) {
        output.set( plane );
        return output;
    }

    public Vector3f[] getPointsArray () {
        return new Vector3f[]{
                new Vector3f( point1 ),
                new Vector3f( point2 ),
                new Vector3f( point3 ),
                new Vector3f( point4 )
        };
    }

}
