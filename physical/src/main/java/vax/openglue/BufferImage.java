package vax.openglue;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.ByteBuffer;
import vax.math.Vector2i;

/**
 POJO data wrapper

 @author toor
 */
public class BufferImage {
    public ByteBuffer buffer;
    //public Vector2i imageSize;
    public int width, height;
    public int bpp;
    public boolean isDepth;

    public BufferImage ( ByteBuffer buffer, int width, int height, int bpp, boolean isDepth ) {
        this.buffer = buffer;
        this.width = width;
        this.height = height;
        this.bpp = bpp;
        this.isDepth = isDepth;
    }

    public BufferImage ( Vector2i imageSize, int bpp, boolean isDepth ) {
        this( imageSize.getX(), imageSize.getY(), bpp, isDepth );
    }

    public BufferImage ( int width, int height, int bpp, boolean isDepth ) {
        this( BufferUtils.createByteBuffer( width * height * bpp ), width, height, bpp, isDepth );
    }

    public void saveTo ( String formatName, File file ) {
        BufferedImage image = new BufferedImage( width, height, BufferedImage.TYPE_INT_RGB );

        for( int x = 0; x < width; x++ ) {
            for( int y = 0; y < height; y++ ) {
                int i = ( x + ( width * y ) );
                int r, g, b;
                if ( isDepth ) {
                    int val = (int) ( 255 * buffer.asFloatBuffer().get( i ) );
                    r = g = b = val;
                } else {
                    i *= bpp;
                    r = buffer.get( i ) & 0xFF;
                    g = buffer.get( i + 1 ) & 0xFF;
                    b = buffer.get( i + 2 ) & 0xFF;
                }
                image.setRGB( x, height - ( y + 1 ), ( 0xFF << 24 ) | ( r << 16 ) | ( g << 8 ) | b );
            }
        }

        try {
            javax.imageio.ImageIO.write( image, formatName, file );
        } catch (Exception ex) {
            throw new RuntimeException( ex );
        }
    }

}
