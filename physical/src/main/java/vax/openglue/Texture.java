package vax.openglue;

import vax.openglue.constants.TextureTarget;

/**
 Manages OpenGL state of the Texture; heavyweight object, requiring OpenGL context to initialize.

 @author toor
 */
public class Texture {
    private int handle;
    private int targetEnum;
    private TextureDescriptor textureDescriptor;
    private TextureParameters textureParameters;
    private boolean updateTexParams = true;

    public Texture ( int handle, TextureDescriptor textureDescriptor, TextureParameters textureParameters, int targetEnum ) {
        this.handle = handle;
        this.textureDescriptor = textureDescriptor;
        this.targetEnum = targetEnum;
        this.textureParameters = textureParameters;
    }

    public Texture ( int handle, TextureDescriptor textureDescriptor, TextureParameters textureParameters, TextureTarget textureTarget ) {
        this( handle, textureDescriptor, textureParameters, textureTarget.getValue() );
    }

    public Texture ( int handle, TextureDescriptor textureDescriptor, TextureParameters textureParameters ) {
        this( handle, textureDescriptor, textureParameters, OpenGL.Constants.GL_TEXTURE_2D );
    }

    public Texture ( int handle, TextureDescriptor textureDescriptor ) {
        this(handle, textureDescriptor, TextureParameters.BILINEAR, OpenGL.Constants.GL_TEXTURE_2D );
    }

    public Texture ( String mock ) {
        throw new UnsupportedOperationException();
    }

    public int getHandle () {
        return handle;
    }

    public TextureDescriptor getTextureDescriptor () {
        return textureDescriptor;
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
}
