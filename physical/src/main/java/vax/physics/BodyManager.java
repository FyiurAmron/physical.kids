package vax.physics;

import java.util.*;

import vax.math.*;
import vax.openglue.mesh.*;

public class BodyManager {
    private final List<Body> bodies = new ArrayList<>();
    private final HashSet<Body> bodySet = new HashSet<>();
    private final Map<Body, Mesh> bodyMeshMap = new HashMap<>();
    private final Vector3f gravity = new Vector3f( 0, -9.81f, 0 );
    private final Map<ColliderDescriptor, Collider<?, ?>> colliderMap = new HashMap<>();
    private final Map<Body, HashSet<Body>> contactMap = new HashMap<>();
    private final ColliderDescriptor tmpDescriptor = new ColliderDescriptor( null, null );

    public BodyManager () {
        _addCollider( new SphereSphereCollider() );
        _addCollider( new SpherePlaneCollider() );
        _addCollider( new SphereTriangleCollider() );
    }

    public Vector3f getGravity () {
        return gravity;
    }

    private void _addCollider ( Collider<?, ?> collider ) {
        colliderMap.put( collider.getDescriptor(), collider );
    }

    public void addCollider ( Collider<?, ?> collider ) {
        _addCollider( collider );
    }

    public void addBody ( Body body ) {
        if ( bodySet.contains( body ) ) {
            throw new IllegalArgumentException( "body already added" );
        }
        bodies.add( body );
        bodySet.add( body );
    }

    public void addBody ( Body body, Mesh mesh ) {
        addBody( body );
        mesh.getTransform().set( body.transform );
        bodyMeshMap.put( body, mesh );
    }

    private void addContact ( Body b1, Body b2 ) {
        HashSet<Body> contacts = contactMap.getOrDefault( b1, null );
        if ( contacts == null ) {
            contacts = new HashSet<>();
            contactMap.put( b1, contacts );
        }
        contacts.add( b2 ); // b1->b2, idemp.

        contacts = contactMap.getOrDefault( b2, null );
        if ( contacts == null ) {
            contacts = new HashSet<>();
            contactMap.put( b2, contacts );
        }
        contacts.add( b1 ); // b2->b1, idemp.
    }

    private void removeContact ( Body b1, Body b2 ) {
        HashSet<Body> contacts = contactMap.getOrDefault( b1, null );
        if ( contacts != null ) {
            contacts.remove( b2 );
        }

        contacts = contactMap.getOrDefault( b2, null );
        if ( contacts != null ) {
            contacts.remove( b1 );
        }

    }

    public boolean hasContact ( Body b1, Body b2 ) {
        HashSet<Body> contacts = contactMap.getOrDefault( b1, null );
        if ( contacts != null && contacts.contains( b2 ) ) {
            return true;
        }

        contacts = contactMap.getOrDefault( b2, null );
        return contacts != null && contacts.contains( b1 );
    }

    private void collide ( Collider<?, ?> collider, boolean contact, Body body1, Body body2 /*, int i, int j*/ ) {
        if ( collider.collide( body1, body2 ) ) {
            if ( contact ) {
                //System.out.println("contact continued: " + body1 + " [" + i + "] vs " + body2 + " [" + j + "]" );
            } else {
                //System.out.println( "contact started: " + body1 + " [" + i + "] vs " + body2 + " [" + j + "]" );
                addContact( body1, body2 );
            }
        } else if ( contact ) {
            //System.out.println( "contact ended: " + body1 + " [" + i + "] vs " + body2 + " [" + j + "]" );
            removeContact( body1, body2 );
        }
    }

    public void update ( float deltaT ) {
        if ( deltaT == 0 ) {
            return;
        }
        if ( deltaT < 0 ) {
            throw new IllegalArgumentException( "deltaT < 0" );
        }

        for( int i = bodies.size() - 1; i >= 0; i-- ) {
            Body body1 = bodies.get( i );
            if ( body1.mass != Float.POSITIVE_INFINITY ) {
                body1.acceleration.add( gravity );
            }

            for( int j = i - 1; j >= 0; j-- ) {
                Body body2 = bodies.get( j );
                boolean contact = hasContact( body1, body2 );

                Class<? extends Body> c1 = body1.getClass();
                Class<? extends Body> c2 = body2.getClass();
                tmpDescriptor.set( c1, c2 );
                Collider<?, ?> collider = colliderMap.getOrDefault( tmpDescriptor, null );

                if ( collider != null ) {
                    collide( collider, contact, body1, body2/*, i, j*/ );
                    continue;
                }

                tmpDescriptor.set( c2, c1 ); // FIXME (c2, c1) or (c1, c2)
                collider = colliderMap.getOrDefault( tmpDescriptor, null );
                if ( collider != null ) {
                    collide( collider, contact, body2, body1/*, i, j*/ );
                }
            }

            if ( body1.mass != Float.POSITIVE_INFINITY ) {
                body1.timeStep( deltaT );
                //Console.WriteLine( "position: " + body1.Transform.TranslationX + "," + body1.Transform.TranslationY + "," + body1.Transform.TranslationZ );

                if ( bodyMeshMap.containsKey( body1 ) ) {
                    bodyMeshMap.get( body1 ).getTransform().set( body1.transform );
                }
            }
        }
        //Console.WriteLine( "updated" );
    }

}
