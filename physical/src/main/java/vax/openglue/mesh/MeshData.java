package vax.openglue.mesh;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class MeshData {
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
}
