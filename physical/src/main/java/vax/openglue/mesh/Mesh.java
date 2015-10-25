package vax.openglue.mesh;

import java.io.*;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import vax.math.Matrix4f;
import vax.openglue.constants.BufferTarget;
import vax.openglue.OpenGLUE;
import vax.openglue.OpenGlConstants;
import vax.openglue.constants.PrimitiveType;
import vax.openglue.Renderable;
import vax.openglue.Texture;
import vax.physical.Main;
import vax.util.Action;

public class Mesh implements Renderable {
    public static final int VERTEX_COUNT = 3, // because we use triangles
            V_DIMS = 3, VN_DIMS = 3, VT_DIMS = 2, // for reference for other implementing classes
            VS_COUNT = 3;
    // v, vn, vt; 3 total.
    public static final float PI = (float) Math.PI, TWO_PI = 2 * PI;
    protected static final String[][] OBJ_section_name = {
        { "vertices", "v" },
        { "normals", "vn" },
        { "UVs", "vt" }
    };
    protected static final float[] TRI_VT_PROTO = {
        1, 1, /* */ 1, 0, /* */ 0, 1
    };
    protected static final float[] RECT_VT_PROTO = {
        0, 1, /* */ 1, 1,/* */ 0, 0,
        0, 0, /* */ 1, 1,/* */ 1, 0, //
    };

    protected final Matrix4f transform = new Matrix4f( true );

    public Action<Mesh> updateAction;

    public Action<Mesh> getUpdateAction () {
        return updateAction;
    }

    public void setUpdateAction ( Action<Mesh> updateAction ) {
        this.updateAction = updateAction;
    }

    public Matrix4f getTransform () {
        return transform;
    }

    public Texture texture;

    public Texture getTexture () {
        return texture;
    }

    public void setTexture ( Texture Texture ) {
        this.texture = Texture;
    }

    protected PrimitiveType primitiveType = PrimitiveType.Triangles;

    public PrimitiveType glPrimitiveType;

    public void setGlPrimitiveType ( PrimitiveType glPrimitiveType ) {
        this.glPrimitiveType = glPrimitiveType;
    }

    public PrimitiveType getGlPrimitiveType () {
        return glPrimitiveType;
    }

    protected int vaoHandle,
            positionVboHandle,
            normalVboHandle,
            uvsVboHandle,
            eboHandle;

    protected final MeshData modelData;

    public MeshData getModelData () {
        return modelData;
    }

    public Mesh ( MeshData modelData ) {
        this.modelData = modelData;
    }

    public Mesh ( MeshData modelData, Matrix4f sourceTransform ) {
        this.modelData = modelData;
        transform.set( sourceTransform );
    }

    public Mesh ( float[] vertices, float[] normals, float[] uvs, int[] indices ) {
        this( new MeshData( vertices, normals, uvs, indices ) );
    }

    public Mesh ( FloatBuffer vertices, FloatBuffer normals, FloatBuffer uvs, IntBuffer indices ) {
        this( new MeshData( vertices.array(), normals.array(), uvs.array(), indices.array() ) );
    }

    @Override
    public void update ( OpenGLUE gl ) {
        if ( updateAction != null ) {
            updateAction.exec( this );
        }
    }

    @Override
    public void render ( OpenGLUE gl ) {
        if ( texture != null ) {
            gl.glBindTexture( OpenGlConstants.GL_TEXTURE_2D, texture.getHandle() );
        }
        gl.glBindVertexArray( vaoHandle );

        gl.glDrawElements( primitiveType, modelData.getIndices().length, DrawElementsType.UnsignedInt, IntPtr.Zero );
    }

    public <T> int genBuffer ( OpenGLUE gl, int bufferTarget, float[] data ) {
        int handle = gl.glGenBuffer();
        gl.glBindBuffer( bufferTarget, handle );
        gl.glBufferData( bufferTarget, new IntPtr( data.Length * Marshal.SizeOf( data[0] ) ), data, BufferUsageHint.StaticDraw );
        return handle;
    }

    public <T> int genBuffer ( OpenGLUE gl, int bufferTarget, int[] data ) {
        int handle = gl.glGenBuffer();
        gl.glBindBuffer( bufferTarget, handle );
        gl.glBufferData( bufferTarget, new IntPtr( data.Length * Marshal.SizeOf( data[0] ) ), data, BufferUsageHint.StaticDraw );
        return handle;
    }

    void enableAttribute ( OpenGLUE gl, int attribNr, int handle, int size ) {
        gl.glEnableVertexAttribArray( attribNr );
        gl.glBindBuffer( OpenGlConstants.GL_ARRAY_BUFFER, handle );
        gl.glVertexAttribPointer( attribNr, size, OpenGlConstants.GL_FLOAT, OpenGlConstants.GL_TRUE, size * Float.BYTES, 0 );
    }

    public void init ( OpenGLUE gl ) {
        positionVboHandle = genBuffer( gl, OpenGlConstants.GL_ARRAY_BUFFER, modelData.getVertices() );
        normalVboHandle = genBuffer( gl, OpenGlConstants.GL_ARRAY_BUFFER, modelData.getNormals() );
        uvsVboHandle = genBuffer( gl, OpenGlConstants.GL_ARRAY_BUFFER, modelData.getUvs() );
        eboHandle = genBuffer( gl, OpenGlConstants.GL_ELEMENT_ARRAY_BUFFER, modelData.getIndices() );
        gl.glBindBuffer( BufferTarget.ArrayBuffer, 0 );
        gl.glBindBuffer( BufferTarget.ElementArrayBuffer, 0 );

        vaoHandle = gl.glGenVertexArray();
        gl.glBindVertexArray( vaoHandle );
        enableAttribute( gl, 0, positionVboHandle, Mesh.V_DIMS );
        enableAttribute( gl, 1, normalVboHandle, Mesh.VN_DIMS );
        enableAttribute( gl, 2, uvsVboHandle, Mesh.VT_DIMS );
        gl.glBindBuffer( BufferTarget.ElementArrayBuffer, eboHandle );
        gl.glBindVertexArray( 0 );
    }

    protected void writeOBJ_buf ( DataOutputStream dos, int bufNr ) throws IOException {
        FloatBuffer fb = FloatBuffer.wrap( modelData.getData()[bufNr] );
        dos.writeBytes( "#\n# " + OBJ_section_name[bufNr][0] + "\n#\n\n" );
        String prefix = OBJ_section_name[bufNr][1] + " ";
        if ( bufNr != 2 ) {
            for( int j = 0; j < modelData.getVertexCount(); j++ ) {
                dos.writeBytes( prefix + fb.get() + " " + fb.get() + " " + fb.get() + "\n" );
            }
        } else {
            for( int j = 0; j < modelData.getVertexCount(); j++ ) {
                dos.writeBytes( prefix + fb.get() + " " + fb.get() + "\n" );
            }
        }
        dos.writeBytes( "\n" );
    }

    public void writeOBJ ( String filename ) throws IOException {
        try (FileOutputStream fos = new FileOutputStream( filename + ".obj" );
                DataOutputStream dos = new DataOutputStream( fos )) {
            dos.writeBytes( "# created by " + Main.APP_NAME + "\n" );
            int tri_cnt = modelData.getVertexCount() / VERTEX_COUNT;
            dos.writeBytes( "# " + modelData.getVertexCount() + " vertex total == normals == UVs\n"
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

}
