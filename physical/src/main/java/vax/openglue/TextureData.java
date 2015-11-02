package vax.openglue;

import java.nio.Buffer;

/**

 @author toor
 @param <T>
 */
public abstract class TextureData<T extends Buffer> {
    protected TextureDescriptor textureDescriptor;
    protected T buffer;

    public TextureDescriptor getTextureDescriptor () {
        return textureDescriptor;
    }

    public T getBuffer () {
        return buffer;
    }

    public Texture createTexture ( OpenGlUe gl, TextureParameters textureParameters, int targetEnum ) {
        int handle = gl.glGenTexture();
        Texture tex = new Texture( handle, textureDescriptor, textureParameters, targetEnum );
        tex.bind( gl ); // sets the params

        gl.glTexImage2D( targetEnum, 0, textureDescriptor.getInternalFormat(),
                textureDescriptor.getWidth(), textureDescriptor.getHeight(), 0,
                textureDescriptor.getPixelFormat(), textureDescriptor.getType(), buffer );
        if ( textureDescriptor.isGenerateMipmaps() ) { // don't do this if using legacy param (GL_GENERATE_MIPMAP set to 'true')
            gl.glGenerateMipmap( targetEnum );
        }
        return tex;
    }
}
