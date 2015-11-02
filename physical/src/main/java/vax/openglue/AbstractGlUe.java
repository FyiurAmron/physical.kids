package vax.openglue;

/**

 @author toor
 */
public abstract class AbstractGlUe implements OpenGlUe {
    private GlUtil glUtil;

    public AbstractGlUe () {
        this( new GlUtil() );
    }

    public AbstractGlUe ( GlUtil glUtil ) {
        this.glUtil = glUtil;
    }

    @Override
    public GlUtil ueGetGlUtil () {
        return glUtil;
    }
}
