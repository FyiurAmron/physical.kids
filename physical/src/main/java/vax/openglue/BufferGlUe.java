package vax.openglue;

import java.nio.*;

/**

 @author toor
 */
public interface BufferGlUe {
    public final static BufferGlUe GLUE_NOT_SET = new BufferGlUe() {
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
