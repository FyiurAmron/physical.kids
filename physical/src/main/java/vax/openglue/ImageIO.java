package vax.openglue;

/**

 @author toor
 */
public class ImageIO {
    private static ImageIoGlUe imageIoGlUe = ImageIoGlUe.GLUE_NOT_SET;

    public static void setBufferGlUe ( ImageIoGlUe bufferGlUe ) {
        ImageIO.imageIoGlUe = bufferGlUe;
    }

    public static ImageIoGlUe getImageIoGlUe () {
        return imageIoGlUe;
    }



    private ImageIO () {
        throw new UnsupportedOperationException();
    }
}
