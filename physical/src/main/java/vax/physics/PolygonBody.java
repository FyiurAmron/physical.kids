package vax.physics;

import vax.math.Plane3f;
import vax.math.Vector3f;

import javax.sound.midi.Soundbank;

/**
 Created by Kuba on 2016-01-16.
 */
public abstract class PolygonBody extends Body {
    protected static int verticesCount;
    protected Vector3f[] vertices;
    protected Plane3f plane = new Plane3f(new Vector3f( ), 0f );


    /**
     No-arg for serialization/subclassing.
     */
    public PolygonBody () {
    }

    /**
     equivalent to <code>new PolygonBody().setPoints( vArray )</code>

     @param vArray
     */
    public PolygonBody ( Vector3f... vArray ) {
        super();
        initializeVerticesArray();
        _setPoints( vArray );
    }

    private void initializeVerticesArray () {
        vertices = new Vector3f[verticesCount];
        for (Vector3f v : vertices) {
            v = new Vector3f();
        }
    }

    /**
     Sets the outputs to values contained in this PolygonBody's corner points.

     @param outputArray
     @return reference to output, for chaining
     */
    public Vector3f[] getVerticesArray ( Vector3f[] outputArray ) {

        if ( outputArray.length != vertices.length ) {
            throw new IllegalStateException();
        }

        for( int i = 0; i < vertices.length; i++ ) {
            if ( outputArray[i] == null ) {
                outputArray[i] = new Vector3f();
            }
            outputArray[i].set( vertices[i] );
        }

        return outputArray;
    }

    /**
     Copies the values from source vectors into local data.

     @param vArray
     */
    public void setPoints ( Vector3f[] vArray ) {
        _setPoints( vArray );
    }

    protected void _setPoints ( Vector3f[] vArray ) {

        if ( vArray.length != vertices.length ) {
            throw new IllegalStateException();
        }
        for( int i = 0; i < vArray.length; i++ ) {
            if (vertices[i] == null) {
                vertices[i] = new Vector3f();
            }
            vertices[i].set( vArray[i] );
        }

        plane.set( vertices[0], vertices[1], vertices[2] );
    }

    /**
     Copies local plane values to output.

     @param output
     @return reference to output, for chaining
     */
    public Plane3f getPlane ( Plane3f output ) {
        output.set( plane );
        return output;
    }

    /**
     Returns number of polygon vertices

     @return
     */
    public int getVerticesCount () {
        return verticesCount;
    }

}
