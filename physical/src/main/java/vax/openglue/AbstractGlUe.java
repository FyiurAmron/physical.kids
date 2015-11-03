package vax.openglue;

/**

 @author toor
 */
public abstract class AbstractGlUe implements OpenGlUe {
    private GlUtils glUtil;

    public AbstractGlUe () {
        this( new GlUtils() );
    }

    public AbstractGlUe ( GlUtils glUtil ) {
        this.glUtil = glUtil;
    }

    @Override
    public GlUtils ueGetGlUtil () {
        return glUtil;
    }
}
