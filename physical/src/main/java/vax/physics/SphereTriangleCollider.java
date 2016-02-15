package vax.physics;

import vax.math.Line3f;
import vax.math.Plane3f;
import vax.math.Vector3f;
import vax.util.Pair;

import java.util.HashMap;
import java.util.Map;

/**
 Created by Kuba on 2016-01-22.
 */
public class SphereTriangleCollider extends Collider<SphereBody, TriangleBody> {

    Plane3f plane = new Plane3f( new Vector3f(), 0 );
    Vector3f //
            tbCenter = new Vector3f(),
            sbCenter = new Vector3f(),
            sbPlaneCenter = new Vector3f(),
            edgeIntersectionPoint = new Vector3f(),
            edgeDirection = new Vector3f(),
            tbCenterCross = new Vector3f(),
            sbPlaneCenterCross = new Vector3f(),
            vectorTmp1 = new Vector3f();
    Vector3f[] triangleVertices = new Vector3f[3];
    Line3f //
            centerLine = new Line3f(),
            tbEdge = new Line3f(),
            lineTmp = new Line3f();
    Pair<Vector3f> //
            pair1 = new Pair<Vector3f>(),
            pair2 = new Pair<Vector3f>(),
            pair3 = new Pair<Vector3f>();
    Pair<?>[] pairs = new Pair<?>[]{ pair1, pair2, pair3 };
    Map<Pair<?>, Boolean> sideMap = new HashMap<>();
    Map<Pair<?>, Vector3f> oppositeMap = new HashMap<>();

    public SphereTriangleCollider () {
        super( SphereBody.class, TriangleBody.class );
    }

    @Override
    public boolean collide ( Body body1, Body body2 ) {

        if ( body1 == null || body2 == null ) {
            throw new NullPointerException();
        }

        SphereBody sb = (SphereBody) body1;
        TriangleBody tb = (TriangleBody) body2;

        tb.getVerticesArray( triangleVertices );
        tbCenter.setX( ( triangleVertices[0].getX() + triangleVertices[1].getX() + triangleVertices[2].getX() ) / 3 );
        tbCenter.setY( ( triangleVertices[0].getY() + triangleVertices[1].getY() + triangleVertices[2].getY() ) / 3 );
        tbCenter.setZ( ( triangleVertices[0].getZ() + triangleVertices[1].getZ() + triangleVertices[2].getZ() ) / 3 );
        tb.getPlane( plane );
        sbCenter.set( sb.getTransform().getTranslationX(), sb.getTransform().getTranslationY(), sb.getTransform().getTranslationZ() );

        float distance = plane.calcDistance( sbCenter );
        vectorTmp1.set( plane.getNormal() ).scale( distance );
        sbPlaneCenter.set( sbCenter ).subtract( vectorTmp1 );

        float depth = sb.getRadius() - Math.abs( distance );
        float radiusInPlane = (float) Math.sqrt( sb.getRadius() * sb.getRadius() - distance * distance );
        int sameSideCount = 0;

        if ( Math.abs( distance ) >= sb.getRadius() ) {
            printCollisionResult( false, distance, sameSideCount, null );
            return false;
        }

        pair1.set( triangleVertices[0], triangleVertices[1] );
        pair2.set( triangleVertices[1], triangleVertices[2] );
        pair3.set( triangleVertices[2], triangleVertices[0] );
        oppositeMap.put( pair1, triangleVertices[2] );
        oppositeMap.put( pair2, triangleVertices[0] );
        oppositeMap.put( pair3, triangleVertices[1] );

        for( Pair<?> verticesPair : pairs ) {
            Pair<Vector3f> pair = (Pair<Vector3f>) verticesPair;
            edgeDirection.set( pair.value2 ).subtract( pair.value1 );
            tbCenterCross.set( edgeDirection.createCross( (Vector3f) vectorTmp1.set( tbCenter ).subtract( pair.value1 ) ) );
            sbPlaneCenterCross.set( edgeDirection.createCross( (Vector3f) vectorTmp1.set( sbPlaneCenter ).subtract( pair.value1 ) ) );
            float dot = tbCenterCross.dot( sbPlaneCenterCross );
            boolean b = dot > 0 ? true : false;
            if ( b ) {
                sameSideCount++;
            }
            sideMap.put( pair, b );
        }

        if ( sameSideCount == 3 ) {
            printCollisionResult( true, distance, sameSideCount, null );
        } else if ( sameSideCount == 2 ) {

            for( Pair<?> verticesPair : pairs ) {
                Pair<Vector3f> pair = (Pair<Vector3f>) verticesPair;
                if ( sideMap.get( pair ) ) {
                    continue;
                }
                tbEdge.setOrigin( pair.value1 );
                tbEdge.setDirection( (Vector3f) edgeDirection.set( pair.value2 ).subtract( pair.value1 ) );
                break;
            }
            if ( Math.abs( tbEdge.calcDistance( sbPlaneCenter ) ) >= radiusInPlane ) {
                printCollisionResult( false, distance, sameSideCount, Math.abs( tbEdge.calcDistance( sbPlaneCenter ) ) );
                return false;
            } else {
                printCollisionResult( true, distance, sameSideCount, Math.abs( tbEdge.calcDistance( sbPlaneCenter ) ) );
            }

        } else if ( sameSideCount == 1 ) {

            for( Pair<?> verticesPair : pairs ) {
                Pair<Vector3f> pair = (Pair<Vector3f>) verticesPair;
                if ( sideMap.get( pair ) ) {
                    if ( sbPlaneCenter.calcDistance( oppositeMap.get( pair ) ) >= radiusInPlane ) {
                        printCollisionResult( false, distance, sameSideCount, sbPlaneCenter.calcDistance( oppositeMap.get( pair ) ) );
                        return false;
                    } else {
                        printCollisionResult( true, distance, sameSideCount, sbPlaneCenter.calcDistance( oppositeMap.get( pair ) ) );
                    }
                }
            }

        } else {
            printCollisionResult( null, distance, sameSideCount, null );
        }

        Vector3f normal = vectorTmp1;
        normal.set( plane.getNormal() ).normalize();
        sb.transform.addTranslation( normal.createScaled( -depth ) );

        float res = sb.restitution * tb.restitution;
        float vn = sb.velocity.dot( normal );
        Vector3f vTangent = new Vector3f( sb.velocity );
        vTangent.subtract( normal.createScaled( vn ) );
        sb.velocity.add( normal.createScaled( ( -1f - res ) * vn ) );

        return true;
    }

    public void printCollisionResult ( Boolean collision, float spherePlaneDistance, int sameSideCount, Float additionalDistance ) {
        /*
        String s = "";
        s += collision == null ? "NOTHING\t" : collision ? "COLLISION\t" : " --- \t";
        s += " planeDistance: %.2f ";
        s += " sameSide: %d ";
//        s += " sbPlaneCenter: %.2f %.2f %.2f";
        s += additionalDistance != null ? sameSideCount == 1 ? " vertexDist: %.2f" : " edgeDist: %.2f" : "";

        s += "%n";
        if ( additionalDistance != null ) {
            System.out.printf( s, spherePlaneDistance, sameSideCount, additionalDistance );
//            System.out.printf( s, spherePlaneDistance, sameSideCount, sbPlaneCenter.getX(), sbPlaneCenter.getY(), sbPlaneCenter.getZ(), additionalDistance );
        } else {
            System.out.printf( s, spherePlaneDistance, sameSideCount );
//            System.out.printf( s, spherePlaneDistance, sameSideCount, sbPlaneCenter.getX(), sbPlaneCenter.getY(), sbPlaneCenter.getZ() );
        }
*/
    }

    @Override
    public CollisionResult collideCR ( Body body1, Body body2 ) {

        if ( body1 == null || body2 == null ) {
            throw new NullPointerException();
        }

        SphereBody sb = (SphereBody) body1;
        TriangleBody tb = (TriangleBody) body2;

        return resultFalse;
    }

}


