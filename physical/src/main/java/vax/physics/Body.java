package vax.physics;

import vax.math.Matrix4f;
import vax.math.Vector3f;

/**

 @author toor
 */
public class Body {
    static float KINEMATIC_EPSILON_SQ;
    protected float restitution, friction, mass;
    protected Vector3f velocity;
    protected Matrix4f transform;

    public Body () {
    }

    public Body ( float mass ) {
    }

    public Body ( float mass, Matrix4f initialTransform ) {
    }

    public Matrix4f getTransform () {
        throw new UnsupportedOperationException( "Not yet implemented." );
    }

    public void applyForce ( Vector3f disp ) {
        throw new UnsupportedOperationException( "Not yet implemented." );
    }

}
