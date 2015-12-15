package vax.openglue;

/**

 @author toor
 */
public abstract class AbstractGLUE implements OpenGLUE {
    public static final int DEFAULT_LOG_BUFFER_SIZE = 4096;

    private GLUtils glUtils;
    private BufferGLUE bufferGLUE;
    private ImageGLUE imageGLUE;

    /**
     Note: the default behaviour is to set this OpenGLUE implementation as current GLUE in CurrentGLUE for static utility methods.
     */
    @SuppressWarnings( "OverridableMethodCallInConstructor" )
    public AbstractGLUE () {
        init();

        CurrentGLUE.setCurrentGLUE( this );
    }

    public AbstractGLUE ( boolean setAsCurrentGLUE ) {
        init();

        if ( setAsCurrentGLUE ) {
            CurrentGLUE.setCurrentGLUE( this );
        }
    }

    private void init () {
        glUtils = new GLUtils();
        try {
            bufferGLUE = getClassBufferGLUE().newInstance();
            imageGLUE = getClassImageGLUE().newInstance();
        } catch (InstantiationException | IllegalAccessException ex) {
            throw new RuntimeException( ex );
        }
        glUtils.setLogBuffer( bufferGLUE.createByteBuffer( DEFAULT_LOG_BUFFER_SIZE ) );
        glUtils.setTempIntBuffer( bufferGLUE.createIntBuffer( 4 * 4 ) );
        glUtils.setTempFloatBuffer( bufferGLUE.createFloatBuffer( 4 * 4 ) );
    }

    @Override
    public GLUtils ueGetGLUtils () {
        return glUtils;
    }

    @Override
    public ImageGLUE getImageGLUE () {
        return imageGLUE;
    }

    @Override
    public BufferGLUE getBufferGLUE () {
        return bufferGLUE;
    }

    protected abstract Class<? extends BufferGLUE> getClassBufferGLUE ();

    protected abstract Class<? extends ImageGLUE> getClassImageGLUE ();
}
