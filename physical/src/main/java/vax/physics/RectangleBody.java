package vax.physics;

import vax.math.Vector3f;

/**
 Created by Kuba on 2015-01-15.
 */
public class RectangleBody extends PolygonBody {

    static {
        verticesCount = 4;
    }

    public RectangleBody () {
        super();
    }

    public RectangleBody ( Vector3f[] vArray ) {
        super( vArray );
        designatePoint4();
    }


    // TODO
    private void designatePoint4 () {
        throw new UnsupportedOperationException( "Not yet implemented" );
    }


}
