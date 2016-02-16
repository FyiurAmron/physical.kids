package vax.openglue;

import vax.math.Vector4f;

/**

 @author toor
 */
public class Material {
    public Texture texture;
    public Vector4f color;
    public int shininess;

    public Material () {
    }

    public Material ( Texture texture ) {
        this.texture = texture;
        color = new Vector4f( 1, 1, 1, 1 );
        shininess = 10;
    }

    public Material ( Texture texture, Vector4f color, int shininess ) {
        this.texture = texture;
        this.color = color;
        this.shininess = shininess;
    }

    public void bind ( OpenGLUE gl ) {
        texture.bind( gl );
    }

    public void unbind ( OpenGLUE gl ) {
        texture.unbind( gl );
    }
}
