package vax.openglue.constants;

import static vax.openglue.OpenGL.Constants.*;

/**

 @author toor
 */
public enum BufferTarget implements OpenGlConstantWrapper {

    ArrayBuffer( GL_ARRAY_BUFFER ),
    ElementArrayBuffer( GL_ELEMENT_ARRAY_BUFFER );

    private final int glConstant;

    private BufferTarget ( int glConstant ) {
        this.glConstant = glConstant;
    }

    @Override
    public int getGlConstant () {
        return glConstant;
    }
}
