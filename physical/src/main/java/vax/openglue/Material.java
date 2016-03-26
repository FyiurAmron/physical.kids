package vax.openglue;

import vax.math.Vector4f;

/**

 @author toor
 */
public class Material {
    public Texture texture;
    public Vector4f color;
    public float shininess, specularFactor, emissionFactor;

    public Material () {
    }

    public Material ( Texture texture ) {
        this( texture, new Vector4f( 1, 1, 1, 1 ), 10, 0.5f, 0 );
    }

    public Material ( Texture texture, Vector4f color ) {
        this( texture, color, 10, 0.5f, 0 );
    }

    public Material ( Texture texture, Vector4f color, float shininess, float specularFactor, float emissionFactor ) {
        this.texture = texture;
        this.color = color;
        this.shininess = shininess;
        this.specularFactor = specularFactor;
        this.emissionFactor = emissionFactor;
    }

    public Material copy ( boolean newColorCopy ) {
        return new Material( texture, newColorCopy ? new Vector4f( color ) : color, shininess, specularFactor, emissionFactor );
    }

    public void bind ( OpenGLUE gl ) {
        texture.bind( gl );
    }

    public void unbind ( OpenGLUE gl ) {
        texture.unbind( gl );
    }
}
