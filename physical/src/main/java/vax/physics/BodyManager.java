package vax.physics;

import java.util.*;
import vax.math.*;
import vax.openglue.mesh.*;

public class BodyManager {
    List<Body> bodies = new ArrayList<Body>();
    HashSet<Body> bodySet = new HashSet<Body>();
    Map<Body,Mesh> bodyMeshMap = new HashMap<Body, Mesh>();
    Vector3f gravity = new Vector3f( 0, -9.81f, 0 );
    Map<ColliderDescriptor, Collider> colliderMap = new HashMap<ColliderDescriptor, Collider>();
    Map<Body,HashSet<Body>> contactMap = new HashMap<Body, HashSet<Body>>();

    public BodyManager () {
        addCollider( new SphereSphereCollider() );
        addCollider( new SpherePlaneCollider() );
    }

    void addCollider ( Collider collider ) {
        colliderMap.put(collider.getDescriptor(), collider);
    }

    public void addBody ( Body body ) {
        if ( bodySet.contains(body) )
            throw new IllegalArgumentException( "body already added" );
        bodies.add(body);
        bodySet.add(body);
    }

    public void addBody ( Body body, Mesh mesh ) {
        addBody( body );
        mesh.getTransform().set( body.transform );
        bodyMeshMap.put(body, mesh);
    }

    void addContact ( Body b1, Body b2 ) {
        HashSet<Body> contacts;

        if (contactMap.containsKey( b1 )) {
            contacts = contactMap.get(b1);
        }
        if ( contacts == null ) {
            contacts = new HashSet<Body>();
            contactMap.put( b1, contacts );
        }
        contacts.add( b2 );

        if (contactMap.containsKey( b2 )) {
            contacts = contactMap.get(b2);
        }
        if ( contacts == null ) {
            contacts = new HashSet<Body>();
            contactMap.put( b2, contacts );
        }
        contacts.add( b1 );
    }

    void removeContact ( Body b1, Body b2 ) {
        HashSet<Body> contacts;

        if (contactMap.containsKey( b1 ))
            contacts = contactMap.get( b1 );
        if ( contacts != null )
            contacts.remove( b2 );

        if (contactMap.containsKey( b2 ))
            contacts = contactMap.get( b2 );
        if ( contacts != null )
            contacts.remove( b1 );

    }

    boolean hasContact ( Body b1, Body b2, HashSet<Body> contacts ) {
//        HashSet<Body> contacts;

        if (contactMap.containsKey( b1 ))
            contacts = contactMap.get( b1 );
        if ( contacts != null && contacts.contains( b2 ) )
            return true;

        if (contactMap.containsKey( b2 ))
            contacts = contactMap.get( b2 );
        return (contacts != null && contacts.contains(b1));
    }

    void collide ( Collider collider, boolean contact, Body body1, Body body2, int i, int j ) {
        if ( collider.collide( body1, body2 ) ) {
            if ( contact ) {
                //Console.WriteLine( "contact continued: " + body1 + " [" + i + "] vs " + body2 + " [" + j + "]" );
            } else {
                //Console.WriteLine( "contact started: " + body1 + " [" + i + "] vs " + body2 + " [" + j + "]" );
                addContact( body1, body2 );
            }
        } else if ( contact ) {
            //Console.WriteLine( "contact ended: " + body1 + " [" + i + "] vs " + body2 + " [" + j + "]" );
            removeContact( body1, body2 );
        }
    }

    public void update ( float deltaT ) {
        if ( deltaT == 0 )
            return;
        if ( deltaT < 0 )
            throw new IllegalArgumentException( "deltaT < 0" );
        //Console.WriteLine( "update; deltaT = " + deltaT );

        for ( int i = bodies.size() - 1; i >= 0; i-- ) {
            Body body1 = bodies.get(i);
            if ( body1.mass != Float.POSITIVE_INFINITY ) {
                body1.acceleration.add( gravity );
            }

            for ( int j = i - 1; j >= 0; j-- ) {
                Body body2 = bodies.get(j);
                boolean contact = hasContact( body1, body2 );

                Class c1 = body1.getClass();
                Class c2 = body2.getClass();
                Collider collider;

                ColliderDescriptor cd = new ColliderDescriptor( c1, c2 );
                if (colliderMap.containsKey( cd ))
                    collider = colliderMap.get( cd );
                if ( collider != null ) {
                    collide( collider, contact, body1, body2, i, j );
                    continue;
                }

                cd = new ColliderDescriptor( c2, c1 );
                if (colliderMap.containsKey( cd ))
                    collider = colliderMap.get( cd );
                if ( collider != null ) {
                    collide( collider, contact, body2, body1, i, j );
                }
            }

            if ( body1.mass != Float.POSITIVE_INFINITY ) {
                body1.timeStep( deltaT );
                //Console.WriteLine( "position: " + body1.Transform.TranslationX + "," + body1.Transform.TranslationY + "," + body1.Transform.TranslationZ );

                if ( bodyMeshMap.containsKey( body1 ) )
                    bodyMeshMap.get(body1).getTransform().set(body1.transform);
            }
        }
        //Console.WriteLine( "updated" );
    }

}


