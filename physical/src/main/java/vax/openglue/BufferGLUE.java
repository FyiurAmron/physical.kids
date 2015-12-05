package vax.openglue;

import java.nio.*;

/**

 @author toor
 */
public interface BufferGLUE {
    public final static BufferGLUE GLUE_NOT_SET = new BufferGLUE() {
        private final String GLUE_NOT_SET_YET = "GLUE not set yet";

        @Override
        public IntBuffer createIntBuffer ( int size ) {
            throw new IllegalStateException( GLUE_NOT_SET_YET );
        }

        @Override
        public IntBuffer createIntBuffer ( int[] data ) {
            throw new IllegalStateException( GLUE_NOT_SET_YET );
        }

        @Override
        public FloatBuffer createFloatBuffer ( int size ) {
            throw new IllegalStateException( GLUE_NOT_SET_YET );
        }

        @Override
        public FloatBuffer createFloatBuffer ( float[] data ) {
            throw new IllegalStateException( GLUE_NOT_SET_YET );
        }

        @Override
        public ByteBuffer createByteBuffer ( int size ) {
            throw new IllegalStateException( GLUE_NOT_SET_YET );
        }

        @Override
        public ByteBuffer createByteBuffer ( byte[] data ) {
            throw new IllegalStateException( GLUE_NOT_SET_YET );
        }
    };

    IntBuffer createIntBuffer ( int size );

    IntBuffer createIntBuffer ( int[] data );

    FloatBuffer createFloatBuffer ( int size );

    FloatBuffer createFloatBuffer ( float[] data );

    ByteBuffer createByteBuffer ( int size );

    ByteBuffer createByteBuffer ( byte[] data );

}
