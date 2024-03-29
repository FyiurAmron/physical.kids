package vax.physics;

import vax.math.*;

abstract public class CenteredBody extends Body {
    public float radius;

    /**
     No-arg for serialization/subclassing.
     */
    public CenteredBody () {
    }

    public CenteredBody ( float mass, float radius ) {
        super( mass );
        this.radius = radius;
    }

    public CenteredBody ( float mass, float restitution, float radius, Matrix4f initialTransform ) {
        super( mass, restitution, initialTransform );
        this.radius = radius;
    }

    public float getRadius () {
        return radius;
    }

    public void setRadius ( float radius ) {
        this.radius = radius;
    }
}
