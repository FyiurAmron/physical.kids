package vax.openglue.mesh;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static vax.openglue.mesh.Mesh.VERTEX_COUNT;
import vax.physical.Main;

public class MeshData {
    protected static final String[][] OBJ_SECTION_NAMES = {
        { "vertices", "v" },
        { "normals", "vn" },
        { "UVs", "vt" }
    };

    private int[] indices;
    private float[] vertices, normals, uvs;
    private float[][] data;
    private int vertexCount;

    public int[] getIndices () {
        return indices;
    }

    public float[] getVertices () {
        return vertices;
    }

    public float[] getNormals () {
        return normals;
    }

    public float[] getUvs () {
        return uvs;
    }

    public float[][] getData () {
        return data;
    }

    public int getVertexCount () {
        return vertexCount;
    }

    protected static int[] createDefaultIndices ( int vertexCount ) {
        int[] indices = new int[vertexCount];
        for( int i = 0; i < vertexCount; i++ ) {
            indices[i] = i;
        }
        return indices;
    }

    public MeshData ( FloatBuffer vertices, FloatBuffer normals, FloatBuffer uvs ) {
        this( vertices.array(), normals.array(), uvs.array(), createDefaultIndices( vertices.array().length / Mesh.V_DIMS ) );
    }

    public MeshData ( FloatBuffer vertices, FloatBuffer normals, FloatBuffer uvs, IntBuffer indices ) {
        this( vertices.array(), normals.array(), uvs.array(), indices.array() );
    }

    public MeshData ( float[] vertices, float[] normals, float[] uvs ) {
        this( vertices, normals, uvs, createDefaultIndices( vertices.length / Mesh.V_DIMS ) );
    }

    public MeshData ( float[] vertices, float[] normals, float[] uvs, int[] indices ) {
        vertexCount = indices.length;
        this.vertices = vertices;
        this.normals = normals;
        this.uvs = uvs;
        data = new float[][]{ vertices, normals, uvs };

        this.indices = indices;
    }

    protected void writeOBJ_buf ( DataOutputStream dos, int bufNr ) throws IOException {
        FloatBuffer fb = FloatBuffer.wrap( data[bufNr] );
        dos.writeBytes( "#\n# " + OBJ_SECTION_NAMES[bufNr][0] + "\n#\n\n" );
        String prefix = OBJ_SECTION_NAMES[bufNr][1] + " ";
        if ( bufNr != 2 ) {
            for( int j = 0; j < vertexCount; j++ ) {
                dos.writeBytes( prefix + fb.get() + " " + fb.get() + " " + fb.get() + "\n" );
            }
        } else {
            for( int j = 0; j < vertexCount; j++ ) {
                dos.writeBytes( prefix + fb.get() + " " + fb.get() + "\n" );
            }
        }
        dos.writeBytes( "\n" );
    }

    public void writeOBJ ( String filename ) throws IOException {
        try ( FileOutputStream fos = new FileOutputStream( filename + ".obj" );
                DataOutputStream dos = new DataOutputStream( fos ) ) {
            dos.writeBytes( "# created by " + Main.APP_NAME + "\n" );
            int tri_cnt = vertexCount / VERTEX_COUNT;
            dos.writeBytes( "# " + vertexCount + " vertex total == normals == UVs\n"
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
}
