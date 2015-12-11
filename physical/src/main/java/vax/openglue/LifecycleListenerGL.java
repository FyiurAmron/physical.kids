package vax.openglue;

/**

 @author toor
 */
public interface LifecycleListenerGL {
    /**
     Called during the initialization phase of the object.

     @param gl
     */
    void init ( OpenGLUE gl );

    //boolean isInitialized ();

    /**
     Requests the object to release all acquired resources.

     @param gl
     */
    void dispose ( OpenGLUE gl );
}
