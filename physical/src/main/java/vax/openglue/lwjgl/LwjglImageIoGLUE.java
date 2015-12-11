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
    public TextureData<ByteBuffer> readTextureDataImpl ( String name, InputStream inputStream ) {
        try {
            PNGDecoder pngDecoder = new PNGDecoder( inputStream );
            if ( !pngDecoder.isRGB() ) {
                throw new IOException( "not a RGB/RGBA PNG" );
            }
            boolean hasAlpha = pngDecoder.hasAlpha();
            int bytesPP = hasAlpha ? 4 : 3,
                    height = pngDecoder.getHeight(),
                    width = pngDecoder.getWidth(),
                    lineLen = width * bytesPP;
            ByteBuffer bb = BufferUtils.createByteBuffer( height * lineLen );
            pngDecoder.decode( bb, lineLen, hasAlpha ? PNGDecoder.Format.RGBA : PNGDecoder.Format.RGB );
            bb.rewind(); // a good practice IMO
            // note: although BGRA is faster to upload to GPU, it would have to be CPU-swizzled first from PNG, so we stick with RGB(A) here
            TextureDescriptor td = new TextureDescriptor( name, width, height,
                    hasAlpha ? GL_RGBA : GL_RGB, GL_RGBA8, GL_UNSIGNED_BYTE );
            return new TextureData<>( td, bb );
        } catch (IOException ex) {
            throw new RuntimeException( ex );
        }
    }
}
