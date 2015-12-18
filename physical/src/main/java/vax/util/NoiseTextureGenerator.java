package vax.util;

import java.io.File;
import java.io.IOException;
import de.matthiasmann.twl.utils.PNGEncoder;
import vax.math.MathUtils;
import vax.openglue.BufferImage;

/**

 @author toor
 */
public class NoiseTextureGenerator {
    /**
     Generates an 32-bit RGBA 1024x1024 noise texture, with output PNG file set to "./random.png".
     */
    public static void generate () {
        generate( 1024, 1024, 4, 8, new File( "random.png" ) );
    }

    /**
     Generates a noise texture, with size and components compatible with given BufferImage format, with PNG output to <code>file</code>.

     @param bi
     @param file
     */
    public static void generate ( BufferImage bi, File file ) {
        generate( bi.width, bi.height, bi.componentCount, bi.bitDepth, file );
    }

    public static void generate ( int width, int height, int componentCount, int bitDepth, File file ) {
        BufferImage bi = new BufferImage( width, height, componentCount, bitDepth, false );
        MathUtils.nextBytes( bi.buffer.array() );
        try {
            PNGEncoder.write( bi, file );
        } catch (IOException ex) {
            throw new RuntimeException( ex );
        }
    }

    private NoiseTextureGenerator () {
        throw new UnsupportedOperationException();
    }
}
