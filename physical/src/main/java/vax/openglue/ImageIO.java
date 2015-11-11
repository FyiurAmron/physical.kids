package vax.openglue;

/**

 @author toor
 */
public class ImageIO {
    private static OpenGLUE.ImageIO imageIoGLUE = OpenGLUE.ImageIO.GLUE_NOT_SET;

    public static void setBufferGLUE ( OpenGLUE.ImageIO bufferGLUE ) {
        ImageIO.imageIoGLUE = bufferGLUE;
    }

    public static OpenGLUE.ImageIO getImageIoGLUE () {
        return imageIoGLUE;
    }

    private ImageIO () {
        throw new UnsupportedOperationException();
    }
}
