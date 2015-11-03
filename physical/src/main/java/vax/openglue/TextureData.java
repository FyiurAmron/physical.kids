package vax.openglue;

import java.nio.Buffer;

/**

 @author toor
 @param <T>
 */
public class TextureData<T extends Buffer> {
    protected TextureDescriptor textureDescriptor;
    protected T buffer;
    protected boolean hasMipmaps;

    public TextureData ( TextureDescriptor textureDescriptor, T buffer ) {
        this.textureDescriptor = textureDescriptor;
        this.buffer = buffer;
    }

    public TextureDescriptor getTextureDescriptor () {
        return textureDescriptor;
    }

    public T getBuffer () {
        return buffer;
    }

    public Texture createTexture ( OpenGlUe gl, TextureParameters textureParameters, int targetEnum, boolean generateMipmaps ) {
        int handle = gl.glGenTexture();
        Texture tex = new Texture( handle, textureDescriptor, textureParameters, targetEnum );
        tex.bind( gl ); // sets the params

        gl.glTexImage2D( targetEnum, 0, textureDescriptor.getInternalFormat(),
                textureDescriptor.getWidth(), textureDescriptor.getHeight(), 0,
                textureDescriptor.getPixelFormat(), textureDescriptor.getType(), buffer );
        if ( generateMipmaps ) {
            gl.glGenerateMipmap( targetEnum ); // don't do this if using legacy param (GL_GENERATE_MIPMAP set to 'true')
            hasMipmaps = true;
        }
        return tex;
    }
}
