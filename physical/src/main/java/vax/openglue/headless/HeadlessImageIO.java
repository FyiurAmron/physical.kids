package vax.openglue.headless;

import java.io.InputStream;
import vax.openglue.ImageIO;
import vax.openglue.TextureData;

/**

 @author toor
 */
public class HeadlessImageIO implements ImageIO.GLUE {
    @Override
    public TextureData<?> ueReadTexture ( String name, InputStream inputStream ) {
        throw new UnsupportedOperationException( "not yet implemented" ); // use Java's ImageIO here
    }
}
