package vax.openglue;

import java.nio.Buffer;
import java.util.Objects;

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

    @Override
    public String toString () {
        return "buffer capacity: " + Objects.toString( buffer ) + " " + Objects.toString( textureDescriptor );
    }
}
