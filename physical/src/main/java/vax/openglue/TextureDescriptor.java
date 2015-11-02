package vax.openglue;

import vax.util.MiscUtils;

/**

 @author toor
 */
public class TextureDescriptor {
    private final int width, height;
    private final int pixelFormat, internalFormat, type;

    private final String name;
    private boolean generateMipmaps;

    public TextureDescriptor ( String name, int width, int height, int pixelFormat, int internalFormat, int type, boolean generateMipmaps ) {
        if ( MiscUtils.isNullOrEmpty( name ) ) {
            throw new IllegalArgumentException( name );
        }
        this.name = name;
        this.width = width;
        this.height = height;
        this.pixelFormat = pixelFormat;
        this.internalFormat = internalFormat;
        this.type = type;
        this.generateMipmaps = generateMipmaps;
    }

    public String getName () {
        return name;
    }

    public int getHeight () {
        return height;
    }

    public int getWidth () {
        return width;
    }

    public int getPixelFormat () {
        return pixelFormat;
    }

    public int getInternalFormat () {
        return internalFormat;
    }

    public int getType () {
        return type;
    }

    public boolean isGenerateMipmaps () {
        return generateMipmaps;
    }
}
