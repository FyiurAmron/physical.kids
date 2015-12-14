package vax.openglue;

import vax.openglue.constants.FramebufferStatus;
import vax.openglue.constants.FramebufferType;

import static vax.openglue.OpenGL.Constants.*;

/**
 Note: to have proper mipmaps generated, it's enough to call getTexture().generateMipmaps() after each unbind() call.

 @author toor
 */
public class Framebuffer implements LifecycleListenerGL {
    private final Texture texture;
    private int framebufferHandle, typeEnum, depthbufferHandle;
    private boolean useDepthBuffer, disposeTextureOnDispose;

    /**
     Note that it's a good idea to use POT size here, probably not going higher than 2048x2048 on mobile devices.
     <p>
     Note: this constructor creates a local texture, owned by this framebuffer, which will get disposed automatically with it.

     @param width
     @param height
     */
    public Framebuffer ( int width, int height ) {
        this( new TextureDescriptor( "FBO texture", width, height,
                GL_BGRA, GL_RGBA8, GL_UNSIGNED_BYTE ) );
    }

    /**
     Note: this constructor creates a local texture, owned by this framebuffer, which will get disposed automatically with it.

     @param textureDescriptor
     */
    public Framebuffer ( TextureDescriptor textureDescriptor ) {
        this( new Texture( new TextureData<>( textureDescriptor, null ) ) );
        disposeTextureOnDispose = true;
    }

    public Framebuffer ( Texture texture ) {
        this( texture, FramebufferType.ReadAndDraw );
    }

    public Framebuffer ( Texture texture, FramebufferType framebufferType ) {
        this( texture, framebufferType.getGlConstant(), true );
    }

    public Framebuffer ( Texture texture, int typeEnum, boolean useDepthBuffer ) {
        this.texture = texture;
        this.typeEnum = typeEnum;
        this.useDepthBuffer = useDepthBuffer;
    }

    @Override
    public void init ( OpenGLUE gl ) {
        framebufferHandle = gl.glGenFramebuffer();
        bind( gl );

        if ( !texture.isInitialized() ) {
            texture.init( gl );
        }
        texture.bind( gl );

        // glFramebufferTexture() unsupported until 3.2; we're 3.0/3.1-compatible here, so:
        gl.glFramebufferTexture2D( typeEnum, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, texture.getHandle(), 0 );
        texture.unbind( gl );

        if ( useDepthBuffer ) {
            depthbufferHandle = gl.glGenRenderbuffer();
            TextureDescriptor td = texture.getTextureDescriptor();
            gl.glBindRenderbuffer( GL_RENDERBUFFER, depthbufferHandle );
            gl.glRenderbufferStorage( GL_RENDERBUFFER, GL_DEPTH_COMPONENT,
                    td.getWidth(), td.getHeight() );
            gl.glBindRenderbuffer( GL_RENDERBUFFER, 0 );
            gl.glFramebufferRenderbuffer( typeEnum, GL_DEPTH_ATTACHMENT,
                    GL_RENDERBUFFER, depthbufferHandle );
        }

        //gl.glDrawBuffer( GL_COLOR_ATTACHMENT0 ); // is this really needed?
        int framebufferStatus = gl.glCheckFramebufferStatus( typeEnum );
        if ( framebufferStatus != GL_FRAMEBUFFER_COMPLETE ) {
            throw new GLException( "framebuffer creation failed; FramebufferStatus == " + FramebufferStatus.valueOf( framebufferStatus ) );
        }
        unbind( gl );
    }

    public void bind ( OpenGLUE gl ) {
        gl.glBindFramebuffer( typeEnum, framebufferHandle );
    }

    public void bind ( OpenGLUE gl, FramebufferType framebufferType ) {
        gl.glBindFramebuffer( framebufferType.getGlConstant(), framebufferHandle );
    }

    public void unbind ( OpenGLUE gl ) {
        gl.glBindFramebuffer( typeEnum, 0 );
    }

    public void unbind ( OpenGLUE gl, FramebufferType framebufferType ) {
        gl.glBindFramebuffer( framebufferType.getGlConstant(), 0 );
    }

    public Texture getTexture () {
        return texture;
    }

    public boolean isDisposeTextureOnDispose () {
        return disposeTextureOnDispose;
    }

    public boolean isUseDepthBuffer () {
        return useDepthBuffer;
    }

    public void setDisposeTextureOnDispose ( boolean disposeTextureOnDispose ) {
        this.disposeTextureOnDispose = disposeTextureOnDispose;
    }

    @Override
    public void dispose ( OpenGLUE gl ) {
        if ( disposeTextureOnDispose && texture != null ) {
            texture.dispose( gl );
        }
        if ( framebufferHandle != 0 ) {
            gl.glDeleteFramebuffer( framebufferHandle );
            if ( depthbufferHandle != 0 ) {
                gl.glDeleteRenderbuffer( depthbufferHandle );
            }
        }
    }

}
