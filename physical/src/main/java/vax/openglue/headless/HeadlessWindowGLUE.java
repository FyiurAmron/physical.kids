package vax.openglue.headless;

import vax.openglue.OpenGLUE;
import vax.openglue.WindowGLUE;

/**

 @author toor
 */
public class HeadlessWindowGLUE implements WindowGLUE {
    private final HeadlessGLUE gl = new HeadlessGLUE();

    @Override
    public OpenGLUE getOpenGLUE () {
        return gl;
    }

    @Override
    public void dispose () {
    }
}
