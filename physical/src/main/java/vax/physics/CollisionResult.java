package vax.physics;

import vax.math.Vector3f;

/**
 Created by Kuba on 2015-12-09.
 */
public class CollisionResult {

    private boolean collision;
    private Vector3f normal;
    private float depth;


    public CollisionResult () {
    }

    public CollisionResult ( boolean collision, Vector3f normal, float depth ) {
        this.collision = collision;
        this.normal = normal;
        this.depth = depth;
    }


}
