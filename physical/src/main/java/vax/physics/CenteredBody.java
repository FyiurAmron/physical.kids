package vax.physics;

import vax.math.*;

abstract public class CenteredBody extends Body {

    public float radius;

    public CenteredBody ( float mass, float radius ){
        super(mass);
        this.radius = radius;
    }

    public CenteredBody ( float mass, float radius, Matrix4f initialTransform ) {
        super( mass, initialTransform );
        this.radius = radius;
    }


    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public void timeStep ( float deltaT ) {
        throw new UnsupportedOperationException( "Not yet implemented." );
    }


}

