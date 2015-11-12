package vax.openglue;

import java.nio.*;

/**

 @author toor
 */
public interface BufferGLUE {
    public final static BufferGLUE GLUE_NOT_SET = new BufferGLUE() {
        @Override
        public IntBuffer ueCreateIntBuffer ( int size ) {
            throw new IllegalStateException();
        }

        @Override
        public IntBuffer ueCreateIntBuffer ( int[] data ) {
            throw new IllegalStateException();
        }

        @Override
        public FloatBuffer ueCreateFloatBuffer ( int size ) {
            throw new IllegalStateException();
        }

        @Override
        public FloatBuffer ueCreateFloatBuffer ( float[] data ) {
            throw new IllegalStateException();
        }
    };

    IntBuffer ueCreateIntBuffer ( int size );

    IntBuffer ueCreateIntBuffer ( int[] data );

    FloatBuffer ueCreateFloatBuffer ( int size );

    FloatBuffer ueCreateFloatBuffer ( float[] data );

}