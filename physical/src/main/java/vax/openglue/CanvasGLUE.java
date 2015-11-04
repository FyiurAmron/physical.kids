package vax.openglue;

/**

 @author toor
 */
public class CanvasGLUE {
    public interface EventListener {
        /** Called by the CanvasGLUE immediately after the OpenGL context is
         initialized.
         <p>
         Note that this method may be called more than once if the underlying
         OpenGL context for the CanvasGLUE is destroyed and
         recreated.

         @param gl
         */
        void init ( OpenGLUE gl );

        /**
         Notifies the listener to perform the release of all OpenGL
         resources, such as memory buffers and GLSL programs.

         @param gl
         */
        void dispose ( OpenGLUE gl );

        /**
         Called by the drawable to initiate OpenGL rendering by the
         client.

         @param gl
         */
        void display ( OpenGLUE gl );

        /**
         Called by the drawable during the first repaint after the
         component has been resized.
         <p>
         The client can update it's viewport associated data
         and view volume of the window appropriately.
         <p>
         Note: it's possible (thought implementation dependent) that
         the GL viewport has already been updated
         via <code>glViewport(x, y, width, height)</code> when this method is called.

         @param gl
         @param x viewport x-coord in pixel units
         @param y viewport y-coord in pixel units
         @param width viewport width in pixel units
         @param height viewport height in pixel units
         */
        void reshape ( OpenGLUE gl, int x, int y, int width, int height );
    }
}
