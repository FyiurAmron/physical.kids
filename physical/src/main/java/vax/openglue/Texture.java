package vax.openglue;

import vax.openglue.constants.TextureTarget;

/**
 Manages OpenGL state of the Texture; heavyweight object, requiring OpenGL context to initialize.

 @author toor
 */
public class Texture {
    private int targetEnum;
    private TextureData<?> textureData;
    private TextureParameters textureParameters;
    private boolean updateTexParams = true;
    private boolean generateMipmaps, mipmaps;

    private int handle;

    public Texture ( TextureData<?> textureData, TextureParameters textureParameters, boolean generateMipmaps, int targetEnum ) {
        this.textureData = textureData;
        this.textureParameters = textureParameters;
        this.generateMipmaps = generateMipmaps;
        this.targetEnum = targetEnum;
    }

    public Texture ( TextureData<?> textureData, TextureParameters textureParameters, boolean generateMipmaps, TextureTarget textureTarget ) {
        this( textureData, textureParameters, generateMipmaps, textureTarget.getValue() );
    }

    public Texture ( TextureData<?> textureData, TextureParameters textureParameters, boolean generateMipmaps ) {
        this( textureData, textureParameters, generateMipmaps, OpenGL.Constants.GL_TEXTURE_2D );
    }

    public Texture ( TextureData<?> textureData ) {
        this( textureData, TextureParameters.BILINEAR, false, OpenGL.Constants.GL_TEXTURE_2D );
    }

    public int getHandle () {
        return handle;
    }

    public TextureData<?> getTextureData () {
        return textureData;
    }

    public boolean hasMipmaps () {
        return mipmaps;
    }

    public void init ( OpenGLUE gl ) {
        handle = gl.glGenTexture();
        bind( gl ); // sets the params
        TextureDescriptor td = textureData.getTextureDescriptor();

        gl.glTexImage2D( targetEnum, 0, td.getInternalFormat(),
                td.getWidth(), td.getHeight(), 0,
                td.getPixelFormat(), td.getType(), textureData.getBuffer() );

        if ( generateMipmaps ) {
            gl.glGenerateMipmap( targetEnum ); // don't do this if using legacy param (GL_GENERATE_MIPMAP set to 'true')
            mipmaps = true;
        }
    }

    public void bind ( OpenGLUE gl ) {
        gl.glBindTexture( targetEnum, handle );
        if ( updateTexParams ) {
            textureParameters.update( gl, targetEnum );
        }
    }

    /**
     useful to mimic disabling the texturing unit at all [i.e. glDisable(GL_TEXTURE_2D)].

     @param gl
     */
    public void unbind ( OpenGLUE gl ) {
        gl.glBindTexture( targetEnum, 0 );
    }

    public void setTextureParameters ( TextureParameters textureParameters ) {
        if ( this.textureParameters.equals( textureParameters ) ) {
            return;
        }
        this.textureParameters = textureParameters;
        updateTexParams = true;
    }

    public void dispose ( OpenGLUE gl ) {
        if ( handle != 0 ) { // else no init() called yet
            gl.glDeleteTexture( handle );
        }
    }
}
