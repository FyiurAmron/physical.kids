package vax.openglue;

/**

 @author toor
 */
public abstract class AbstractGLUE implements OpenGLUE {
    private GLUtils glUtil;

    public AbstractGLUE () {
        this( new GLUtils() );
    }

    public AbstractGLUE ( GLUtils glUtil ) {
        this.glUtil = glUtil;
    }

    @Override
    public GLUtils ueGetGLUtils () {
        return glUtil;
    }
}
