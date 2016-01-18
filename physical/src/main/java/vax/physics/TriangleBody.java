package vax.physics;

import vax.math.Vector3f;

/**
 Created by Kuba on 2015-12-02.
 */
public class TriangleBody extends PolygonBody {

    static {
        verticesCount = 3;
    }

    public TriangleBody () {
        super();
    }

    public TriangleBody ( Vector3f[] vArray ) {
        super( vArray );
    }

}
