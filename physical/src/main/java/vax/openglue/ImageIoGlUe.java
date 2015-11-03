package vax.openglue;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

/**

 @author toor
 */
public interface ImageIoGlUe {
    @SuppressWarnings( "Convert2Lambda" )
    public final static ImageIoGlUe GLUE_NOT_SET = new ImageIoGlUe() {
        @Override
        public TextureData<?> ueReadTexture ( String name, InputStream inputStream ) {
            throw new IllegalArgumentException();
        }
    };

    default TextureData<?> ueReadTextureUrl ( String urlString ) {
        try {
            return ueReadTexture( new URL( urlString ) );
        } catch (MalformedURLException ex) {
            throw new IllegalArgumentException( ex );
        }
    }

    default TextureData<?> ueReadTextureFile ( String filename ) {
        return ueReadTexture( new File( filename ) );
    }

    default TextureData<?> ueReadTexture ( File file ) {
        try (InputStream is = new BufferedInputStream( new FileInputStream( file ) )) {
            return ueReadTexture( file.toString(), is );
        } catch (IOException ex) {
            throw new RuntimeException( ex );
        }
    }

    default TextureData<?> ueReadTexture ( URL url ) {
        try (InputStream is = new BufferedInputStream( url.openStream() )) {
            return ueReadTexture( url.toString(), is );
        } catch (IOException ex) {
            throw new RuntimeException( ex );
        }
    }

    TextureData<?> ueReadTexture ( String name, InputStream inputStream );
}
