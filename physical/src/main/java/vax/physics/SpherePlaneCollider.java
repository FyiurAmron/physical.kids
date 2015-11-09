package vax.physics;

import java.util.*;
import physical.math;

public class SpherePlaneCollider extends Collider {
    public SpherePlaneCollider () {
        super( typeof(SphereBody), typeof(PlaneBody) ) ;
    }

    override public bool collide ( Body body1, Body body2 ) {
        SphereBody sb = body1 as SphereBody;
        PlaneBody pb = body2 as PlaneBody;
        if ( sb == null || pb == null )
            throw new InvalidOperationException();
        float dist = pb.Plane3f.getDistance( sb.Transform );
        float depth = sb.Radius - dist;
        if ( depth < 0 || depth > sb.Radius ) // a) sphere-to-plane collision occured, b) not too far yet
            return false;
        Vector3f normal = pb.Plane3f.Normal;
        sb.Transform.addTranslation( normal.getScaled( depth ) );

        float res = sb.Restitution * pb.Restitution;
        float vn = sb.Velocity.dot( normal );
        Vector3f vTangent = new Vector3f( sb.Velocity );
        vTangent.subtract( normal.getScaled( vn ) );
        sb.Velocity.add( normal.getScaled( ( -1f - res ) * vn ) );

        Vector3f F = sb.getForce();
        float fn = F.dot( normal );
        F.subtract( normal.getScaled( fn ) ); // surface reaction
        if ( vTangent.lengthSq() > Body.KINEMATIC_EPSILON_SQ ) {
            vTangent.normalize().scale( fn * pb.Friction );
            sb.applyForce( vTangent );
        }

        return true;
    }
}