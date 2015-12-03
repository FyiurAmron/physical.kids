package vax.physics;

import vax.math.Plane3f;
import vax.math.Vector3f;

/**
 Created by Kuba on 2015-12-02.
 */
public class TriangleBody extends PlaneBody {
    public Vector3f[] points = new Vector3f[3];

    /**
     No-arg for serialization/subclassing.
     */
    public TriangleBody () {
    }

    public TriangleBody ( Vector3f v1, Vector3f v2, Vector3f v3 ) {
        _setPoints( v1, v2, v3 );
    }

    public boolean equals ( TriangleBody tb ) {
        return points[0].equals( tb.points[0] )
                && points[1].equals( tb.points[1] )
                && points[2].equals( tb.points[2] );
    }

    @Override
    public int hashCode () {
        throw new UnsupportedOperationException(); // TODO [Jakub Sengerski] - always implement hashCode() when implementing equals()
    }

    public Vector3f[] getPoints () {
        return points;
    }

    public void setPoints ( Vector3f v1, Vector3f v2, Vector3f v3 ) {
        _setPoints( v1, v2, v3 );
    }

    private void _setPoints ( Vector3f v1, Vector3f v2, Vector3f v3 ) {
        setPlane3f( new Plane3f( v1, v2, v3 ) );
        this.points[0] = v1;
        this.points[1] = v2;
        this.points[2] = v3;
    }
}
