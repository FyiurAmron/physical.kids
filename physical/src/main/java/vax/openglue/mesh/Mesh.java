package vax.openglue.mesh;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import vax.openglue.constants.BufferTarget;
import vax.openglue.constants.PrimitiveType;
import vax.openglue.*;

public class Mesh implements Renderable {
    public static final int //
            VERTEX_COUNT = 3, // implicitly assume we're using triangles
            V_DIMS = 3, VN_DIMS = 3, VT_DIMS = 2, // for reference for other implementing classes
            VS_COUNT = 3;
    // v, vn, vt; 3 total.
    public static final float[] TRI_VT_PROTO = {
        1, 1, /* */ 1, 0, /* */ 0, 1
    };
    // used by Rect & Prism
    public static final float[] //
            RECT_VT_PROTO_1 = {
                0, 1, /* */ 1, 1,/* */ 0, 0,
                0, 0, /* */ 1, 1,/* */ 1, 0, //
            },
            RECT_VT_PROTO_2 = {
                1, 1, /* */ 0, 1,/* */ 1, 0,
                1, 0, /* */ 0, 1,/* */ 0, 0, //
            },
            RECT_VT_PROTO_3 = {
                0, 0, /* */ 1, 0,/* */ 0, 1,
                0, 1, /* */ 1, 0,/* */ 1, 1, //
            },
            RECT_VT_PROTO_4 = {
                1, 0, /* */ 0, 0,/* */ 1, 1,
                1, 1, /* */ 0, 0,/* */ 0, 1, //
            };

    protected PrimitiveType primitiveType = PrimitiveType.Triangles;
    protected int //
            vaoHandle,
            positionVboHandle,
            normalVboHandle,
            uvsVboHandle,
            eboHandle;

    protected final MeshData meshData;

    /*
     c-tors
     */
    public Mesh ( MeshData meshData ) {
        this.meshData = meshData;
    }

    public Mesh ( float[] vertices, float[] normals, float[] uvs, int[] indices ) {
        this( new MeshData( vertices, normals, uvs, indices ) );
    }

    public Mesh ( FloatBuffer vertices, FloatBuffer normals, FloatBuffer uvs, IntBuffer indices ) {
        this( new MeshData( vertices.array(), normals.array(), uvs.array(), indices.array() ) );
    }

    public MeshData getModelData () {
        return meshData;
    }

    @Override
    public void render ( OpenGLUE gl ) {
        gl.glBindVertexArray( vaoHandle );
        gl.glDrawElements( primitiveType.getGlConstant(), meshData.getIndices().length, OpenGL.Constants.GL_UNSIGNED_INT, 0 );
    }

    /*
     other methods
     */
    public int genBuffer ( OpenGLUE gl, int bufferTarget, float[] data ) {
        int handle = gl.glGenBuffer();
        gl.glBindBuffer( bufferTarget, handle );
        FloatBuffer fb = BufferUtils.createFloatBuffer( data );
        gl.glBufferData( bufferTarget, data.length * Float.BYTES, fb, OpenGL.Constants.GL_STATIC_DRAW );
        return handle;
    }

    public int genBuffer ( OpenGLUE gl, int bufferTarget, int[] data ) {
        int handle = gl.glGenBuffer();
        gl.glBindBuffer( bufferTarget, handle );
        IntBuffer ib = BufferUtils.createIntBuffer( data );
        gl.glBufferData( bufferTarget, data.length * Integer.BYTES, ib, OpenGL.Constants.GL_STATIC_DRAW );
        return handle;
    }

    private void enableAttribute ( OpenGLUE gl, int attribNr, int handle, int size ) {
        gl.glEnableVertexAttribArray( attribNr );
        gl.glBindBuffer( OpenGL.Constants.GL_ARRAY_BUFFER, handle );
        gl.glVertexAttribPointer( attribNr, size, OpenGL.Constants.GL_FLOAT, true, size * Float.BYTES, 0 );
    }

    @Override
    public void init ( OpenGLUE gl ) {
        positionVboHandle = genBuffer( gl, OpenGL.Constants.GL_ARRAY_BUFFER, meshData.getVertices() );
        normalVboHandle = genBuffer( gl, OpenGL.Constants.GL_ARRAY_BUFFER, meshData.getNormals() );
        uvsVboHandle = genBuffer( gl, OpenGL.Constants.GL_ARRAY_BUFFER, meshData.getUvs() );
        eboHandle = genBuffer( gl, OpenGL.Constants.GL_ELEMENT_ARRAY_BUFFER, meshData.getIndices() );
        gl.glBindBuffer( BufferTarget.ArrayBuffer, 0 ); // important!
        gl.glBindBuffer( BufferTarget.ElementArrayBuffer, 0 );  // important!

        vaoHandle = gl.glGenVertexArray();
        gl.glBindVertexArray( vaoHandle );
        enableAttribute( gl, 0, positionVboHandle, Mesh.V_DIMS );
        enableAttribute( gl, 1, normalVboHandle, Mesh.VN_DIMS );
        enableAttribute( gl, 2, uvsVboHandle, Mesh.VT_DIMS );
        gl.glBindBuffer( BufferTarget.ElementArrayBuffer, eboHandle );
        gl.glBindVertexArray( 0 ); // important!
    }

    /**
     Note: this doesn't dispose() any textures.

     @param gl
     */
    @Override
    public void dispose ( OpenGLUE gl ) {
        if ( positionVboHandle == 0 ) {
            return; // init() not called yet
        }
        gl.glDeleteBuffers( gl.ueGetGLUtils().wrap( positionVboHandle, normalVboHandle, uvsVboHandle, eboHandle ) );
        gl.glDeleteVertexArray( vaoHandle );
    }
}
