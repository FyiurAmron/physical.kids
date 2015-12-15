package vax.openglue.headless;

import vax.openglue.MouseGLUE;
import vax.openglue.OpenGLUE;
import vax.openglue.WindowGLUE;

/**

 @author toor
 */
public class HeadlessWindowGLUE implements WindowGLUE {
    private final HeadlessGLUE gl = new HeadlessGLUE();
    private final HeadlessMouseGLUE headlessMouse = new HeadlessMouseGLUE();

    @Override
    public OpenGLUE getOpenGLUE () {
        return gl;
    }

    @Override
    public void start () {
    }

    @Override
    public void dispose () {
    }

    @Override
    public MouseGLUE getMouseGLUE () {
        return headlessMouse;
    }
}
