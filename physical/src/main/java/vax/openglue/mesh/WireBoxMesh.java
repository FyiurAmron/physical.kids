package vax.openglue.mesh;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import vax.openglue.BufferUtils;
import vax.openglue.constants.PrimitiveType;

public class WireBoxMesh extends Mesh {
    protected static final IntBuffer BOX_INDICES_BUF
            = BufferUtils.createIntBuffer(
                    0, 1, 0,
                    2, 0, 4,
                    1, 3, 1,
                    5, 2, 3,
                    2, 6, 3,
                    7, 4, 5,
                    4, 6, 5,
                    7, 6, 7 );
    protected static final int OPT_X = 2, OPT_Y = 2, OPT_Z = 2;

    static protected MeshData buildWireBox ( float x, float y, float z ) {
        int size = OPT_X * OPT_Y * OPT_Z * VERTEX_COUNT;
        FloatBuffer fb = FloatBuffer.allocate( size );
        for( int i = 0; i < OPT_X; i++ ) {
            for( int j = 0; j < OPT_Y; j++ ) {
                for( int k = 0; k < OPT_Z; k++ ) {
                    fb.put( i * x );
                    fb.put( j * y );
                    fb.put( k * z );
                }
            }
        }
        fb.flip();
        return new MeshData( fb, BufferUtils.createFloatBuffer( size ), BufferUtils.createFloatBuffer( size ), BOX_INDICES_BUF );
    }

    public WireBoxMesh ( float sizeX, float sizeY, float sizeZ ) {
        super( buildWireBox( sizeX, sizeY, sizeZ ) );
        primitiveType = PrimitiveType.Lines;
    }

    public WireBoxMesh ( float[] sizes ) {
        this( sizes[0], sizes[1], sizes[2] );
    }
}
