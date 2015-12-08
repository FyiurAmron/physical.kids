package vax.physics;

import vax.math.*;

public class SpherePlaneCollider extends Collider<SphereBody, PlaneBody> {
    public SpherePlaneCollider () {
        super( SphereBody.class, PlaneBody.class );
    }

    @Override
    public boolean collide ( /* Sphere */ Body body1, /* Plane */ Body body2 ) {
        if ( body1 == null || body2 == null ) {
            throw new NullPointerException();
        }

        SphereBody sb = (SphereBody) body1;
        PlaneBody pb = (PlaneBody) body2;

        float dist = pb.plane3f.calcDistance( sb.transform );
        float depth = sb.radius - dist;
        if ( depth < 0 || depth > sb.radius ) // a) sphere-to-plane collision occured, b) not too far yet
        {
            return false;
        }
        Vector3f normal = pb.plane3f.getNormal();
        sb.transform.addTranslation( normal.createScaled( depth ) );

        float res = sb.restitution * pb.restitution;
        float vn = sb.velocity.dot( normal );
        Vector3f vTangent = new Vector3f( sb.velocity );
        vTangent.subtract( normal.createScaled( vn ) );
        sb.velocity.add( normal.createScaled( ( -1f - res ) * vn ) );

        Vector3f F = sb.getForce();
        float fn = F.dot( normal );
        F.subtract( normal.createScaled( fn ) ); // surface reaction
        if ( vTangent.calcLengthSq() > Body.KINEMATIC_EPSILON_SQ ) {
            vTangent.normalize().scale( fn * pb.friction );
            sb.applyForce( vTangent );
        }

        return true;
    }
}
