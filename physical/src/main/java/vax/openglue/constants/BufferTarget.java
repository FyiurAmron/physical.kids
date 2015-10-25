package vax.openglue.constants;

import static vax.openglue.OpenGlConstants.*;

/**

 @author toor
 */
public enum BufferTarget implements OpenGlConstantWrapper {

    ArrayBuffer( GL_ARRAY_BUFFER ),
    ElementArrayBuffer( GL_ELEMENT_ARRAY_BUFFER );

    private final int value;

    private BufferTarget ( int value ) {
        this.value = value;
    }

    @Override
    public int getValue () {
        return value;
    }
}
