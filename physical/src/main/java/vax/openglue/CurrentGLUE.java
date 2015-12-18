package vax.openglue;

import java.nio.*;

/**

 @author toor
 */
public class CurrentGLUE {
    private static OpenGLUE currentGLUE;

    public static void setCurrentGLUE ( OpenGLUE currentGLUE ) {
        CurrentGLUE.currentGLUE = currentGLUE;
    }

    public static OpenGLUE getCurrentGLUE () {
        return currentGLUE;
    }

    private CurrentGLUE () {
        throw new UnsupportedOperationException();
    }
}
