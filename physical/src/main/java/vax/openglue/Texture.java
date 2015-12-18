package vax.openglue;

import java.nio.Buffer;
import java.util.Objects;
import static vax.openglue.OpenGL.Constants.*;
import vax.openglue.constants.TextureTarget;

/**
 Manages OpenGL state of the Texture; heavyweight object, requiring OpenGL context to initialize.

 @author toor
 */
public class Texture implements LifecycleListenerGL {
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
        this( textureData, textureParameters, generateMipmaps, textureTarget.getGlConstant() );
    }

    public Texture ( TextureData<?> textureData, TextureParameters textureParameters, boolean generateMipmaps ) {
        this( textureData, textureParameters, generateMipmaps, GL_TEXTURE_2D );
    }

    public Texture ( TextureData<?> textureData ) {
        this( textureData, TextureParameters.BILINEAR_ANISO_CLAMP, false, GL_TEXTURE_2D );
    }

    protected int getHandle () {
        return handle;
    }

    public TextureData<?> getTextureData () {
        return textureData;
    }

    public TextureDescriptor getTextureDescriptor () {
        return textureData.getTextureDescriptor();
    }

    public boolean hasMipmaps () {
        return mipmaps;
    }

    @Override
    public void init ( OpenGLUE gl ) {
        if ( handle != 0 ) {
            return;
        }
        handle = gl.glGenTexture();
        bind( gl ); // sets the params
        TextureDescriptor td = textureData.getTextureDescriptor();

        Buffer buf = textureData.getBuffer();
        if ( buf != null ) {
            buf.rewind(); // sanity OP; otherwise we could get nasty native OpenGL driver crashes on non-rewound Buffers
        }

        gl.glTexImage2D( targetEnum, 0, td.getInternalFormat(),
                td.getWidth(), td.getHeight(), 0,
                td.getPixelFormat(), td.getType(), buf );

        if ( generateMipmaps ) {
            gl.glGenerateMipmap( targetEnum ); // don't do this if using legacy param (GL_GENERATE_MIPMAP set to 'true')
            mipmaps = true;
        }
        unbind( gl );
    }

    //@Override
    public boolean isInitialized () {
        return handle != 0;
    }

    /**
     Idempotent.

     @param gl
     */
    public void generateMipmaps ( OpenGLUE gl ) {
        if ( !mipmaps ) {
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

    public void getTexImage ( OpenGLUE gl, int formatEnum, int typeEnum, Buffer buffer ) {
        gl.glGetTexImage( targetEnum, 0, formatEnum, typeEnum, buffer );
    }

    public BufferImage createBufferImage ( OpenGLUE gl ) {
        int pixelFormat = textureData.textureDescriptor.getPixelFormat();
        TextureDescriptor td = textureData.textureDescriptor;
        BufferImage bufferImage = new BufferImage( td.getWidth(), td.getHeight(), pixelFormat == GL_RGBA ? 4 : 3 );
        bind( gl );
        gl.glGetTexImage( targetEnum, 0, pixelFormat, GL_UNSIGNED_BYTE, bufferImage.buffer );
        unbind( gl );
        return bufferImage;
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

    @Override
    public void dispose ( OpenGLUE gl ) {
        if ( handle != 0 ) { // else no init() called yet
            gl.glDeleteTexture( handle );
        }
    }

    @Override
    public String toString () {
        return "mipmaps? " + mipmaps + " " + Objects.toString( textureData );
    }
}
