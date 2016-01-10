package vax.openglue;

/**

 @author toor
 */
public class Material {
    public Texture texture;

    public Material ( Texture texture ) {
        this.texture = texture;
    }

    public void bind ( OpenGLUE gl ) {
        texture.bind( gl );
    }

    public void unbind ( OpenGLUE gl ) {
        texture.unbind( gl );
    }

}
