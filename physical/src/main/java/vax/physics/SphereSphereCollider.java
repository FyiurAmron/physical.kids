package vax.physics;

import vax.math.*;

public class SphereSphereCollider extends Collider<SphereBody, SphereBody> {

    public SphereSphereCollider () {
        super( SphereBody.class, SphereBody.class );
    }

    @Override
    public boolean collide ( /* Sphere */ Body body1, /* Sphere */ Body body2 ) {
        SphereBody sb1 = (SphereBody) body1;
        SphereBody sb2 = (SphereBody) body2;
        if ( sb1 == null || sb2 == null ) {
            throw new NullPointerException();
        }

        float res = sb1.restitution * sb2.restitution;
        Vector3f disp = sb1.transform.getDisplacement( sb2.transform );
        float totalRadius = sb1.radius + sb2.radius;
        float dist = disp.calcLength();
        //System.out.println( totalRadius + " " + dist );
        float depth = totalRadius - dist;
        if ( depth < 0 /* || depth > totalRadius */ /* <- has to be false */ ) {
            return false;
        }
        disp.normalize();
        Vector3f normal = disp;
        depth *= 0.5f;

        sb1.transform.addTranslation( normal.createScaled( -depth ) );
        //sb2.transform.addTranslation( normal.createScaled( depth ) );

        float v1n = sb1.velocity.dot( normal ), v2n = sb2.velocity.dot( normal ), v1n2, v2n2,
                vDiffRes = res * ( v1n - v2n );
        v1n2 = ( sb1.mass * v1n + sb2.mass * ( v2n - vDiffRes ) ) / ( sb1.mass + sb2.mass );
        v2n2 = vDiffRes + v1n2;

        sb1.velocity.add( normal.createScaled( v1n2 - v1n ) );
        sb2.velocity.add( normal.createScaled( v2n2 - v2n ) );
        return true;
    }
}
