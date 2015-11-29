package vax.openglue;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

/**

 @author toor
 */
public class ImageIO {
    private static GLUE imageIO = GLUE.GLUE_NOT_SET;

    public static void setGLUE ( GLUE bufferGLUE ) {
        ImageIO.imageIO = bufferGLUE;
    }

    public static GLUE getGLUE () {
        return imageIO;
    }

    private ImageIO () {
        throw new UnsupportedOperationException();
    }

    public interface GLUE {
        @SuppressWarnings( "Convert2Lambda" )
        public final static GLUE GLUE_NOT_SET = new GLUE() {
            @Override
            public TextureData<?> readTextureDataImpl ( String name, InputStream inputStream ) {
                throw new IllegalStateException( "GLUE not set yet" );
            }
        };

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
    }
}
