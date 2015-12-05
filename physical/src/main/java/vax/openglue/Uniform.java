package vax.openglue;

/**

 @author toor
 */
public interface Uniform {
    void updateGl ( OpenGLUE gl, int uniformLocation );

    String getName ();

    String getPrefix ();

    String toShaderString ();
}
