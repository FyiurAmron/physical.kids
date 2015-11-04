package vax.physical.body;

import java.util.List;
import vax.math.Matrix4f;
import vax.math.Vector3f;
import vax.physics.Body;
import vax.util.Action;

/**

 @author toor
 */
public class SphereBody {

    public SphereBody ( int i, float BALL_RADIUS ) {
    }

    public Matrix4f getTransform () {
        throw new UnsupportedOperationException( "Not yet implemented." );
    }

    public void setRotationSpeed ( float f ) {
        throw new UnsupportedOperationException( "Not yet implemented." );
    }

    public int getRestitution () {
        throw new UnsupportedOperationException( "Not yet implemented." );
    }

    public void setRestitution ( int i ) {
        throw new UnsupportedOperationException( "Not yet implemented." );
    }

    public Vector3f getVelocity () {
        throw new UnsupportedOperationException( "Not yet implemented." );
    }

    public List<Action<Body>> getForces () {
        throw new UnsupportedOperationException( "Not yet implemented." );
    }

}
