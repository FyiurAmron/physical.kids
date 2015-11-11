package vax.openglue.jogl;

import java.io.IOException;
import java.io.InputStream;

import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.util.texture.TextureIO;

import vax.openglue.OpenGLUE;
import vax.openglue.TextureData;
import vax.openglue.TextureDescriptor;

/**

 @author toor
 */
public class JoglImageIoGLUE implements OpenGLUE.ImageIO {
    @Override
    public TextureData<?> ueReadTexture ( String name, InputStream inputStream ) {
        com.jogamp.opengl.util.texture.TextureData td;
        try {
            td = TextureIO.newTextureData( GLProfile.getDefault(), inputStream, false, null );
        } catch (IOException ex) {
            throw new RuntimeException( ex );
        }
        TextureDescriptor tdesc = new TextureDescriptor( name, td.getWidth(), td.getHeight(),
                td.getPixelFormat(), td.getInternalFormat(), td.getPixelType() );
        return new TextureData<>( tdesc, td.getBuffer() );
    }
}
