package vax.openglue;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.ByteBuffer;
import de.matthiasmann.twl.utils.PNGDecoder;
import static vax.openglue.OpenGL.Constants.*;

/**

 @author toor
 */
public interface ImageGLUE {
    default TextureData<?> readTextureDataUrl ( String urlString ) {
        try {
            return readTextureData( new URL( urlString ) );
        } catch (MalformedURLException ex) {
            throw new IllegalArgumentException( ex );
        }
    }

    default TextureData<?> readTextureDataFile ( String filename ) {
        return readTextureData( new File( filename ) );
    }

    default TextureData<?> readTextureData ( File file ) {
        try (InputStream is = new BufferedInputStream( new FileInputStream( file ) )) {
            return readTextureDataImpl( file.toString(), is );
        } catch (IOException ex) {
            throw new RuntimeException( ex );
        }
    }

    default TextureData<?> readTextureData ( URL url ) {
        try (InputStream is = new BufferedInputStream( url.openStream() )) {
            return readTextureDataImpl( url.toString(), is );
        } catch (IOException ex) {
            throw new RuntimeException( ex );
        }
    }

    default TextureData<?> readTextureData ( String resourceName, Class<?> resourceClass ) {
        try (InputStream is = new BufferedInputStream( resourceClass.getResourceAsStream( resourceName ) )) {
            return readTextureDataImpl( resourceName, is );
        } catch (IOException ex) {
            throw new RuntimeException( ex );
        }
    }

    /**
     Adds buffering (via BufferedInputStream) and IOException wrapping.
     Calls readTextureDataImpl(name, inputStream) internally.

     @param name
     @param inputStream
     @return
     */
    default TextureData<?> readTextureData ( String name, InputStream inputStream ) {
        try (InputStream is = new BufferedInputStream( inputStream )) {
            return readTextureDataImpl( name, is );
        } catch (IOException ex) {
            throw new RuntimeException( ex );
        }
    }

    /**
     Note: this method is intended for internal/expert use.
     Prefer readTextureData in regular use cases.

     @param name
     @param inputStream
     @return
     */
    TextureData<?> readTextureDataImpl ( String name, InputStream inputStream );

    public class DefaultLocal implements ImageGLUE {
        @Override
        public TextureData<?> readTextureDataImpl ( String name, InputStream inputStream ) {
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
                ByteBuffer bb = CurrentGLUE.createByteBuffer( height * lineLen );
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
}
