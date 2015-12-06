package vax.openglue.lwjgl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import de.matthiasmann.twl.utils.PNGDecoder;
import vax.openglue.*;
import static vax.openglue.OpenGL.Constants.*;

/**

 @author toor
 */
public class LwjglImageIoGLUE implements ImageIO.GLUE {
    @Override
    public TextureData<?> readTextureDataImpl ( String name, InputStream inputStream ) {
        try {
            PNGDecoder pngDecoder = new PNGDecoder( inputStream );
            if ( !pngDecoder.isRGB() ) {
                throw new IOException( "not a RGB/RGBA PNG" );
            }
            int height = pngDecoder.getHeight(),
                    width = pngDecoder.getWidth();
            boolean hasAlpha = pngDecoder.hasAlpha();
            ByteBuffer bb = BufferUtils.createByteBuffer( height * width * ( hasAlpha ? 4 : 3 ) );
            pngDecoder.decode( bb, 0, hasAlpha ? PNGDecoder.Format.RGBA : PNGDecoder.Format.RGB );
            TextureDescriptor td = new TextureDescriptor( name, width, height, hasAlpha ? GL_RGBA : GL_RGB,
                    //internalFormat, type );
                    //GL_RGBA8, GL_UNSIGNED_BYTE );
                    GL_RGBA, GL_UNSIGNED_BYTE );
            return new TextureData<>( td, bb );
        } catch (IOException ex) {
            throw new RuntimeException( ex );
        }
    }
}
