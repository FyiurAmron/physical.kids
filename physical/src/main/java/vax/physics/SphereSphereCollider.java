package vax.physics;

import java.util.*;
import physical.math;

public class SphereSphereCollider extends Collider {
    public SphereSphereCollider () {
        super( typeof(SphereBody), typeof(SphereBody));
        }

        override public bool collide ( Body body1, Body body2 ) {
        SphereBody sb1 = body1 as SphereBody, sb2 = body2 as SphereBody;
        if ( sb1 == null || sb2 == null )
        throw new InvalidOperationException();

        float res = sb1.Restitution * sb2.Restitution;
        Vector3f disp = sb1.Transform.getDisplacement( sb2.Transform );
        float totalRadius = sb1.Radius + sb2.Radius;
        float dist = disp.length();
        //Console.WriteLine( totalRadius + " " + dist );
        float depth = totalRadius - dist;
        if ( depth < 0 /* || depth > totalRadius */ /* <- has to be false*/ )
        return false;
        disp.normalize();
        Vector3f normal = disp;
        depth *= 0.5f;

        sb1.Transform.addTranslation( normal.getScaled( -depth ) );
        //sb2.Transform.addTranslation( normal.getScaled( depth ) );

        float v1n = sb1.Velocity.dot( normal ), v2n = sb2.Velocity.dot( normal ), v1n2, v2n2,
        vDiffRes = res * ( v1n - v2n );
        v1n2 = ( sb1.Mass * v1n + sb2.Mass * ( v2n - vDiffRes ) ) / ( sb1.Mass + sb2.Mass );
        v2n2 = vDiffRes + v1n2;

        sb1.Velocity.add( normal.getScaled( v1n2 - v1n ) );
        sb2.Velocity.add( normal.getScaled( v2n2 - v2n ) );
        return true;
        }
        }