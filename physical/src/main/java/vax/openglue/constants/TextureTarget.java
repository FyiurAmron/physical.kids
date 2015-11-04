package vax.openglue.constants;

import static vax.openglue.OpenGL.Constants.*;

/**

 @author toor
 */
public enum TextureTarget implements OpenGlConstantWrapper {
    /*
     Texture1D( GL_TEXTURE_1D ),
     TextureArray1D( GL_TEXTURE_1D_ARRAY ),
     */
    Texture2D( GL_TEXTURE_2D ),
    /*
     TextureArray2D( GL_TEXTURE_2D_ARRAY ),
     TextureMultisample2D( GL_TEXTURE_2D_MULTISAMPLE ),
     TextureArrayMultisample2D( GL_TEXTURE_2D_MULTISAMPLE_ARRAY ),
     Texture3D( GL_TEXTURE_3D ),
     */
    TextureCubeMap( GL_TEXTURE_CUBE_MAP ),
    /*
     TextureCubeMapArray( GL_TEXTURE_CUBE_MAP_ARRAY ),
     TextureRectangle( GL_TEXTURE_RECTANGLE ),
     */
    TODO( 0 );

    private final int value;

    private TextureTarget ( int value ) {
        this.value = value;
    }

    @Override
    public int getValue () {
        return value;
    }
}
