package vax.physics;

/**
 Created by Kuba on 2015-11-25.
 */
@Deprecated
public class TriangleTriangleCollider extends Collider<TriangleBody, TriangleBody> {

    public TriangleTriangleCollider () {
        super( TriangleBody.class, TriangleBody.class );
    }

    @Deprecated
    @Override
    public boolean collide ( Body body1, Body body2 ) {
        throw new UnsupportedOperationException( "Not implemented" );
    }

    @Override
    public CollisionResult collideCR ( Body body1, Body body2 ) {
        throw new UnsupportedOperationException( "Not implemented" );
    }

}
