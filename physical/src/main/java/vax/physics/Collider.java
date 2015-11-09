package vax.physics;

public abstract class Collider<T1 extends Body, T2 extends Body> {
    final ColliderDescriptor colliderDescriptor;

    public Collider ( Class<T1> t1, Class<T2> t2 ) {
        colliderDescriptor = new ColliderDescriptor( t1, t2 );
    }

    abstract public boolean collide ( /* T1 */ Body body1, /* T2 */ Body body2 );

    /*
     public void collide ( T2 body1, T1 body2 ) {
     collide( body2, body1 );
     }
     */
    public ColliderDescriptor getDescriptor () {
        return colliderDescriptor;
    }
}
