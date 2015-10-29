package vax.physics;

import vax.math.*;

public class SpherePlaneCollider extends Collider {
    public SpherePlaneCollider () {
        super( SphereBody.class, PlaneBody.class ) ;
    }

    @Override
    public boolean collide ( Body body1, Body body2 ) {
        SphereBody sb = (SphereBody) body1;
        PlaneBody pb = (PlaneBody) body2;
        if ( sb == null || pb == null )
            throw new NullPointerException();
        float dist = pb.plane3f.getDistance( sb.transform );
        float depth = sb.radius - dist;
        if ( depth < 0 || depth > sb.radius ) // a) sphere-to-plane collision occured, b) not too far yet
            return false;
        Vector3f normal = pb.plane3f.getNormal();
        sb.transform.addTranslation( normal.getScaled( depth ) );

        float res = sb.restitution * pb.restitution;
        float vn = sb.velocity.dot( normal );
        Vector3f vTangent = new Vector3f( sb.velocity );
        vTangent.subtract( normal.getScaled( vn ) );
        sb.velocity.add( normal.getScaled( ( -1f - res ) * vn ) );

        Vector3f F = sb.getForce();
        float fn = F.dot( normal );
        F.subtract( normal.getScaled( fn ) ); // surface reaction
        if ( vTangent.lengthSq() > Body.KINEMATIC_EPSILON_SQ ) {
            vTangent.normalize().scale( fn * pb.friction );
            sb.applyForce( vTangent );
        }

        return true;
    }
}