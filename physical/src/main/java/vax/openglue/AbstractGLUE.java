package vax.openglue;

/**

 @author toor
 */
public abstract class AbstractGLUE implements OpenGLUE {
    public static final int DEFAULT_LOG_BUFFER_SIZE = 4096;

    private GLUtils glUtil;

    public AbstractGLUE () {
        this( new GLUtils() );
    }

    @SuppressWarnings( "OverridableMethodCallInConstructor" )
    public AbstractGLUE ( GLUtils glUtil ) {
        this.glUtil = glUtil;
        BufferGLUE bg = BufferUtils.getBufferGLUE();
        Class<? extends BufferGLUE> cbg = getClassBufferGLUE();
        if ( bg == BufferGLUE.GLUE_NOT_SET ) {
            try {
                bg = cbg.newInstance();
                BufferUtils.setBufferGLUE( bg );
            } catch (InstantiationException | IllegalAccessException ex) {
                throw new RuntimeException( ex );
            }
        } else if ( cbg.isInstance( bg ) ) {
            // already set, idemp.
        } else {
            throw new IllegalStateException( "GLUE already set to incompatible one" );
        }
        glUtil.setLogBuffer( bg.createByteBuffer( DEFAULT_LOG_BUFFER_SIZE ) );

        ImageIO.GLUE iiog = ImageIO.getGLUE();
        Class<? extends ImageIO.GLUE> ciig = getClassImageIO_GLUE();
        if ( iiog == ImageIO.GLUE.GLUE_NOT_SET ) {
            try {
                ImageIO.setGLUE( ciig.newInstance() );
            } catch (InstantiationException | IllegalAccessException ex) {
                throw new RuntimeException( ex );
            }
        } else if ( ciig.isInstance( iiog ) ) {
            // already set, idemp.
        } else {
            throw new IllegalStateException( "GLUE already set to incompatible one" );
        }
    }

    @Override
    public GLUtils ueGetGLUtils () {
        return glUtil;
    }

    public abstract Class<? extends BufferGLUE> getClassBufferGLUE ();

    public abstract Class<? extends ImageIO.GLUE> getClassImageIO_GLUE ();
}
