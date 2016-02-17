package vax.openglue.mesh;

import vax.math.Vector3f;
import vax.math.VectorFloat;

public class RectangleMesh extends Mesh {
    public RectangleMesh ( float sizeX, float sizeY ) {
        this( sizeX, sizeY, RECT_VT_PROTO_1 );
    }

    public RectangleMesh ( int axis, float size1, float size2 ) {
        this( axis, size1, size2, RECT_VT_PROTO_1 );
    }

    public RectangleMesh ( float[][] ps, int segU, int segV ) {
        this( ps, segU, segV, RECT_VT_PROTO_1 );
    }

    public RectangleMesh ( float[] p1, float[] p2, float[] p3, int segU, int segV ) {
        this( p1, p2, p3, segU, segV, RECT_VT_PROTO_1 );
    }

    /**
     Creates a new OXY rectangle of given sizes, centered at OY axis.

     @param sizeX
     @param sizeY
     @param rectVtProto
     */
    public RectangleMesh ( float sizeX, float sizeY, float[] rectVtProto ) {
        this(
                new float[]{ -sizeX / 2, 0, 0 },
                new float[]{ sizeX / 2, 0, 0 },
                new float[]{ -sizeX / 2, sizeY, 0 },
                1, 1, rectVtProto );
    }

    /**
     Creates a new rectangle orthogonal to given axis, centered at it.

     @param axis axis int as defined by SC3D_constant ( 0 == OX, 1 == OY, 2 == OZ )
     @param size1 first size (X or Y)
     @param size2 second size (Y or Z)
     @param rectVtProto
     */
    public RectangleMesh ( int axis, float size1, float size2, float[] rectVtProto ) {
        this( buildAxedRectangle( axis, size1, size2 ), 1, 1, rectVtProto );
    }

    /* public rectangle( float[] p1, float[] p2, float[] p3 ) {
     super( prepare_rectangle_arrs( p1, p2, p3, 1, 1 ) );
     } */
    public RectangleMesh ( float[][] ps, int segU, int segV, float[] rectVtProto ) {
        this( ps[0], ps[1], ps[2], segU, segV, rectVtProto );
    }

    public RectangleMesh ( float[] p1, float[] p2, float[] p3, int segU, int segV, float[] rectVtProto ) {
        super( buildRectangle( p1, p2, p3, segU, segV, rectVtProto ) );
    }

    static protected MeshData buildRectangle ( float[] p1, float[] p2, float[] p3, int segU, int segV, float[] rectVtProto ) {
        int v_total = 2 * Mesh.VERTEX_COUNT * segU * segV; // 2 tris for each seg, 6 verts per seg
        float[] vx = new float[Mesh.V_DIMS * v_total],
                vn = new float[Mesh.VN_DIMS * v_total],
                vt = new float[Mesh.VT_DIMS * v_total],
                u = VectorFloat.createDiff( p2, p1 ),
                v = VectorFloat.createDiff( p3, p1 );
        float invSegU = 1.0f / segU, invSegV = 1.0f / segV;

        float u0, u1, v0, v1; // calculated in-place to avoid loss of precision for large surfaces
        for( int i = 0, i_step = 2 * Mesh.VERTEX_COUNT * Mesh.V_DIMS, i_u = 0, k; i_u < segU; i_u++ ) {
            for( int i_v = 0; i_v < segV; i_v++, i += i_step ) {
                for( int j = 0; j < Mesh.V_DIMS; j++ ) {
                    k = i + j;
                    float uU = u[j] * invSegU, vV = v[j] * invSegV;
                    u0 = i_u * uU;
                    u1 = u0 + uU;
                    v0 = i_v * vV;
                    v1 = v0 + vV;

                    vx[k] = p1[j] + u0 + v0;
                    vx[k + Mesh.V_DIMS] = p1[j] + u1 + v0;
                    vx[k + 2 * Mesh.V_DIMS] = p1[j] + u0 + v1;
                    vx[k + 3 * Mesh.V_DIMS] = vx[k + 2 * Mesh.V_DIMS];
                    vx[k + 4 * Mesh.V_DIMS] = vx[k + Mesh.V_DIMS];
                    vx[k + 5 * Mesh.V_DIMS] = p1[j] + u1 + v1;
                }
            }
        }

        for( int i = 0, max = Mesh.VT_DIMS * v_total, vt_step = rectVtProto.length; i < max; i += vt_step ) {
            System.arraycopy( rectVtProto, 0, vt, i, vt_step );
        }

        float[] n = Vector3f.createNormal( p1, p2, p3 ); // the getNormal should be equal for the whole rectangle
        for( int i = 0, max = Mesh.VN_DIMS * v_total; i < max; i += Mesh.VN_DIMS ) {
            System.arraycopy( n, 0, vn, i, Mesh.VN_DIMS );
        }

        return new MeshData( vx, vn, vt );
    }

    static protected float[][] buildAxedRectangle ( int axis, float size1, float size2 ) {
        size1 *= 0.5f;
        size2 *= 0.5f;
        switch ( axis ) {
            case Vector3f.OX:
                return new float[][]{
                    new float[]{ 0, -size1, -size2 },
                    new float[]{ 0, size1, -size2 },
                    new float[]{ 0, -size1, size2 }
                };
            case Vector3f.OY:
                return new float[][]{
                    new float[]{ -size1, 0, -size2 },
                    new float[]{ size1, 0, -size2 },
                    new float[]{ -size1, 0, size2 }
                };
            case Vector3f.OZ:
                return new float[][]{
                    new float[]{ -size1, -size2, 0 },
                    new float[]{ size1, -size2, 0 },
                    new float[]{ -size1, size2, 0 }
                };
        }
        throw new IllegalArgumentException( "unknown axis nr '" + axis + "'" );
    }
}
