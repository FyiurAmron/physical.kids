package vax.openglue;

import java.nio.*;

/**

 @author toor
 */
public interface BufferGLUE {
    public final static BufferGLUE GLUE_NOT_SET = new BufferGLUE() {
        @Override
        public IntBuffer createIntBuffer ( int size ) {
            throw new IllegalStateException();
        }

        @Override
        public IntBuffer createIntBuffer ( int[] data ) {
            throw new IllegalStateException();
        }

        @Override
        public FloatBuffer createFloatBuffer ( int size ) {
            throw new IllegalStateException();
        }

        @Override
        public FloatBuffer createFloatBuffer ( float[] data ) {
            throw new IllegalStateException();
        }
    };

    IntBuffer createIntBuffer ( int size );

    IntBuffer createIntBuffer ( int[] data );

    FloatBuffer createFloatBuffer ( int size );

    FloatBuffer createFloatBuffer ( float[] data );

}
