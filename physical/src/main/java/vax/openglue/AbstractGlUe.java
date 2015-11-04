package vax.openglue;

/**

 @author toor
 */
public abstract class AbstractGLUE implements OpenGLUE {
    private GlUtils glUtil;

    public AbstractGLUE () {
        this( new GlUtils() );
    }

    public AbstractGLUE ( GlUtils glUtil ) {
        this.glUtil = glUtil;
    }

    @Override
    public GlUtils ueGetGlUtil () {
        return glUtil;
    }
}
