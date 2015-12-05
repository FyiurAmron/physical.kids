package vax.openglue;

import java.nio.Buffer;

/**

 @author toor
 @param <T>
 */
public class TextureData<T extends Buffer> {
    protected TextureDescriptor textureDescriptor;
    protected T buffer;

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
}
