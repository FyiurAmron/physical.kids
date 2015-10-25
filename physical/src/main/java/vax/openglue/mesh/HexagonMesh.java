package vax.openglue.mesh;

import vax.openglue.constants.PrimitiveType;

public class HexagonMesh extends Mesh {

    public static final float A = 2, HALF_A = A * 0.5f;
    public static final float H = (float) Math.sqrt( 3 ) * HALF_A;
    protected static final float[] //
            v = { // hexagon vertices
                0, 0, 0, // center
                HALF_A, H, 0, // v1
                A, 0, 0,
                HALF_A, -H, 0,
                -HALF_A, -H, 0,
                -A, 0, 0,
                -HALF_A, H, 0, // v6
            },
            vn = { // vertex normals
                0, 0, 1, // center
                0, 0, 1, // v1
                0, 0, 1,
                0, 0, 1,
                0, 0, 1,
                0, 0, 1,
                0, 0, 1, // v6
            },
            vt1 = { // vertex UVs cropped
                0.5f, 0.5f, // center
                0.75f, 0, // v1
                1, 0.5f,
                0.75f, 1,
                0.25f, 1,
                0, 0.5f,
                0.25f, 0, // v6
            },
            vt2 = { // vertex UVs stretched
                0.5f, 0.5f, // center
                1, 0, // v1
                1, 0.5f,
                1, 1,
                0, 1,
                0, 0.5f,
                0, 0, // v6
            };
    protected static final int[] ix = { 0, 1, 6, 5, 4, 3, 2, 1 };
    // hexagon trifan indices
        /*
     protected static readonly Buffer<float> //
     fbv = new Buffer<float>( v ),
     fbvn = new Buffer<float>( vn ),
     fbvt1 = new Buffer<float>( vt1 ),
     fbvt2 = new Buffer<float>( vt2 );
     protected static readonly Buffer<int> ib = new Buffer<int>( ix );
     */

    /**
     Constructs a 6-triangle Hexagon with A=2 and center in (0,0).

     @param vt UV mapping (vt1 = crop mode, vt2 = stretch mode)
     */
    protected HexagonMesh ( float[] vt ) {
        super( v, vn, vt, ix );
        primitiveType = PrimitiveType.TriangleFan;
    }

    protected static final HexagonMesh //
            singletonCropped = new HexagonMesh( vt1 ),
            singletonStretched = new HexagonMesh( vt2 );

    static public HexagonMesh getSingleton ( boolean stretched ) {
        return stretched ? singletonStretched : singletonCropped;
    }

    static public HexagonMesh getSingleton () {
        return singletonCropped;
    }
}
