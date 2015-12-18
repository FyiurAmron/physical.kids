package vax.openglue.mesh;

import java.io.*;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import vax.math.Matrix4f;
import vax.openglue.constants.BufferTarget;
import vax.openglue.constants.PrimitiveType;
import vax.openglue.*;
import vax.physical.Main;
import vax.util.Action;

public class Mesh implements Renderable {
    public static final int VERTEX_COUNT = 3, // because we use triangles
            V_DIMS = 3, VN_DIMS = 3, VT_DIMS = 2, // for reference for other implementing classes
            VS_COUNT = 3;
    // v, vn, vt; 3 total.
    protected static final String[][] OBJ_SECTION_NAMES = {
        { "vertices", "v" },
        { "normals", "vn" },
        { "UVs", "vt" }
    };
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

    protected final Matrix4f transform = new Matrix4f( true );

    private Action<Mesh> updateAction, prerenderAction;
    private Texture texture;
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

    public Mesh ( MeshData meshData, Matrix4f sourceTransform ) {
        this.meshData = meshData;
        transform.set( sourceTransform );
    }

    public Mesh ( float[] vertices, float[] normals, float[] uvs, int[] indices ) {
        this( new MeshData( vertices, normals, uvs, indices ) );
    }

    public Mesh ( FloatBuffer vertices, FloatBuffer normals, FloatBuffer uvs, IntBuffer indices ) {
        this( new MeshData( vertices.array(), normals.array(), uvs.array(), indices.array() ) );
    }

    /*
     getters/setters
     */
    public Action<Mesh> getUpdateAction () {
        return updateAction;
    }

    public void setUpdateAction ( Action<Mesh> updateAction ) {
        this.updateAction = updateAction;
    }

    public Matrix4f getTransform () {
        return transform;
    }

    public Texture getTexture () {
        return texture;
    }

    public void setTexture ( Texture Texture ) {
        this.texture = Texture;
    }

    public MeshData getModelData () {
        return meshData;
    }

    /*
     interface implementation
     */
    //@Override
    public void update () {
        if ( updateAction != null ) {
            updateAction.exec( this );
        }
    }

    @Override
    public void render ( OpenGLUE gl ) {
        if ( texture != null ) {
            texture.bind( gl );
        }
        gl.glBindVertexArray( vaoHandle );

        gl.glDrawElements( primitiveType.getGlConstant(), meshData.getIndices().length, OpenGL.Constants.GL_UNSIGNED_INT, 0 );
        if ( texture != null ) {
            texture.unbind( gl );
        }
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

    protected void writeOBJ_buf ( DataOutputStream dos, int bufNr ) throws IOException {
        FloatBuffer fb = FloatBuffer.wrap( meshData.getData()[bufNr] );
        dos.writeBytes( "#\n# " + OBJ_SECTION_NAMES[bufNr][0] + "\n#\n\n" );
        String prefix = OBJ_SECTION_NAMES[bufNr][1] + " ";
        if ( bufNr != 2 ) {
            for( int j = 0; j < meshData.getVertexCount(); j++ ) {
                dos.writeBytes( prefix + fb.get() + " " + fb.get() + " " + fb.get() + "\n" );
            }
        } else {
            for( int j = 0; j < meshData.getVertexCount(); j++ ) {
                dos.writeBytes( prefix + fb.get() + " " + fb.get() + "\n" );
            }
        }
        dos.writeBytes( "\n" );
    }

    public void writeOBJ ( String filename ) throws IOException {
        try (FileOutputStream fos = new FileOutputStream( filename + ".obj" );
                DataOutputStream dos = new DataOutputStream( fos )) {
            dos.writeBytes( "# created by " + Main.APP_NAME + "\n" );
            int tri_cnt = meshData.getVertexCount() / VERTEX_COUNT;
            dos.writeBytes( "# " + meshData.getVertexCount() + " vertex total == normals == UVs\n"
                    + "# " + tri_cnt + " tris == faces\n\n"
                    + "mtllib " + filename + ".mtl\nusemtl " + filename + "\n\n"
                    + "#\n# " + getClass() + "\n#\n\n" );
            writeOBJ_buf( dos, 0 );
            writeOBJ_buf( dos, 2 );
            writeOBJ_buf( dos, 1 );
            dos.writeBytes( "#\n# faces\n#\n" );
            for( int i = 0, j = 1, k = 2, l = 3; i < tri_cnt; i++, k += 3, j += 3, l += 3 ) {
                dos.writeBytes( "f " + j + "/" + j + "/" + j + " " + k + "/" + k + "/" + k + " " + l + "/" + l + "/" + l + "\n" );
            }
            dos.writeBytes( "\n# EOF\n" );
        }
    }

    public void writeOBJ () throws IOException {
        writeOBJ( "" + getClass() );
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
