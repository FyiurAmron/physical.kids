package vax.openglue.mesh;

import vax.math.Vector3f;
import vax.math.VectorFloat;

public class RectangleMesh extends Mesh {
    /**
     Creates a new OXY rectangle of given sizes, centered at OY axis.

     @param size_x
     @param size_y
     */
    public RectangleMesh ( float size_x, float size_y ) {
        this( new float[]{ -size_x / 2, 0, 0 }, new float[]{ size_x / 2, 0, 0 }, new float[]{ -size_x / 2, size_y, 0 }, 1, 1 );
    }

    /**
     Creates a new rectangle orthogonal to given axis, centered at it.

     @param axis axis int as defined by SC3D_constant ( 0 == OX, 1 == OY, 2 == OZ )
     @param size_1 first size (X or Y)
     @param size_2 second size (Y or Z)
     */
    public RectangleMesh ( int axis, float size_1, float size_2 )


    {
            this( buildAxedRectangle( axis, size_1, size_2 ), 1, 1 );
        }

        /* public rectangle( float[] p1, float[] p2, float[] p3 ) {
     super( prepare_rectangle_arrs( p1, p2, p3, 1, 1 ) );
     } */
        public RectangleMesh ( float[][] ps, int seg_u, int seg_v ) {
            this( ps[0], ps[1], ps[2], seg_u, seg_v );
        }

        public RectangleMesh ( float[] p1, float[] p2, float[] p3, int seg_u, int seg_v ) {
            super( buildRectangle( p1, p2, p3, seg_u, seg_v ) );
        }

        static protected MeshData buildRectangle ( float[] p1, float[] p2, float[] p3, int seg_u, int seg_v ) {
        int v_total = 2 * Mesh.VERTEX_COUNT * seg_u * seg_v; // 2 tris for each seg, 6 verts per seg
        float[] vx = new float[Mesh.V_DIMS * v_total],
                vn = new float[Mesh.VN_DIMS * v_total],
                vt = new float[Mesh.VT_DIMS * v_total],
                u = VectorFloat.getDiff( p2, p1 ),
                v = VectorFloat.getDiff( p3, p1 );

        float u0, u1, v0, v1; // calculated in-place to avoid loss of precision for large surfaces
        for( int i = 0, i_step = 2 * Mesh.VERTEX_COUNT * Mesh.V_DIMS, i_u = 0, k; i_u < seg_u; i_u++ ) {
            for( int i_v = 0; i_v < seg_v; i_v++, i += i_step ) {
                for( int j = 0; j < Mesh.V_DIMS; j++ ) {
                    k = i + j;
                    u0 = u[j] * i_u / seg_u;
                    u1 = u[j] * ( i_u + 1 ) / seg_u;
                    v0 = v[j] * i_v / seg_v;
                    v1 = v[j] * ( i_v + 1 ) / seg_v;

                    vx[k] = p1[j] + u0 + v0;
                    vx[k + Mesh.V_DIMS] = p1[j] + u1 + v0;
                    vx[k + 2 * Mesh.V_DIMS] = p1[j] + u0 + v1;
                    vx[k + 3 * Mesh.V_DIMS] = vx[k + 2 * Mesh.V_DIMS];
                    vx[k + 4 * Mesh.V_DIMS] = vx[k + Mesh.V_DIMS];
                    vx[k + 5 * Mesh.V_DIMS] = p1[j] + u1 + v1;
                }
            }
        }

        for( int i = 0, max = Mesh.VT_DIMS * v_total, vt_step = Mesh.RECT_VT_PROTO.length; i < max; i += vt_step ) {
            System.arraycopy( Mesh.RECT_VT_PROTO, 0, vt, i, vt_step );
        }

        float[] n = Vector3f.getNormal( p1, p2, p3 ); // the normal should be equal for the whole rectangle
        for( int i = 0, max = Mesh.VN_DIMS * v_total; i < max; i += Mesh.VN_DIMS ) {
            System.arraycopy( n, 0, vn, i, Mesh.VN_DIMS );
        }

        return new MeshData( vx, vn, vt );
    }

    static protected float[][] buildAxedRectangle ( int axis, float size_1, float size_2 ) {
        size_1 *= 0.5f;
        size_2 *= 0.5f;
        switch ( axis ) {
            case Vector3f.OX:
                return new float[][]{
                    new float[]{ 0, -size_1, -size_2 },
                    new float[]{ 0, size_1, -size_2 },
                    new float[]{ 0, -size_1, size_2 }
                };
            case Vector3f.OY:
                return new float[][]{
                    new float[]{ -size_1, 0, -size_2 },
                    new float[]{ size_1, 0, -size_2 },
                    new float[]{ -size_1, 0, size_2 }
                };
            case Vector3f.OZ:
                return new float[][]{
                    new float[]{ -size_1, -size_2, 0 },
                    new float[]{ size_1, -size_2, 0 },
                    new float[]{ -size_1, size_2, 0 }
                };
        }
        throw new IllegalArgumentException( "unknown axis nr '" + axis + "'" );
    }
}
