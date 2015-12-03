package vax.physics;

import vax.math.*;

public class PlaneBody extends Body {

    protected Plane3f plane3f;

    /**
     No-arg for serialization/subclassing.
     */
    public PlaneBody () {
    }

    public PlaneBody ( Plane3f plane3f ) {
        this.plane3f = plane3f;
    }

    public PlaneBody ( Vector3f normal, float distanceToOrigin ) {
        this( new Plane3f( normal, distanceToOrigin ) );
    }

    public Plane3f getPlane3f () {
        return plane3f;
    }

    public void setPlane3f ( Plane3f plane3f ) {
        this.plane3f = plane3f;
    }
}
