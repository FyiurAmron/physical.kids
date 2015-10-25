package vax.openglue;

import vax.util.MiscUtils;

public class Texture {
    private int handle;
    private final String filename;

    public Texture ( String filename ) {
        this.filename = filename;
        if ( filename == null || filename.isEmpty() ) {

        }
        if ( MiscUtils.isNullOrEmpty( filename ) ) {
            throw new IllegalArgumentException( filename );
        }
    }

    public int getHandle () {
        return handle;
    }

    public void loadData () {
        // TODO replace with Java loader (port from SC maybe?)
        Bitmap bmp = new Bitmap( filename );
        BitmapData bmp_data = bmp.LockBits( new Rectangle( 0, 0, bmp.Width, bmp.Height ), ImageLockMode.ReadOnly,
                System.Drawing.Imaging.PixelFormat.Format32bppArgb );
        bmp.UnlockBits( bmp_data );
    }

    public void init ( OpenGLUE gl ) {
        handle = gl.glGenTexture();
        gl.glBindTexture( TextureTarget.Texture2D, handle );

        // TODO: replace below code with proper texture parameter abstraction

        // We will not upload mipmaps, so disable mipmapping (otherwise the texture will not appear).
        // We can use GL.GenerateMipmaps() or GL.Ext.GenerateMipmaps() to create
        // mipmaps automatically. In that case, use TextureMinFilter.LinearMipmapLinear to enable them.
        gl.glTexParameter( TextureTarget.Texture2D, TextureParameterName.TextureMinFilter, (int) TextureMinFilter.Linear );
        gl.glTexParameter( TextureTarget.Texture2D, TextureParameterName.TextureMagFilter, (int) TextureMagFilter.Linear );
        gl.glTexParameter( TextureTarget.Texture2D, TextureParameterName.TextureWrapS, (int) TextureParameterName.ClampToEdge );
        gl.glTexParameter( TextureTarget.Texture2D, TextureParameterName.TextureWrapT, (int) TextureParameterName.ClampToEdge );

        gl.glTexImage2D( TextureTarget.Texture2D, 0, PixelInternalFormat.Rgba, bmp_data.Width, bmp_data.Height, 0,
                OpenTK.Graphics.OpenGL.PixelFormat.Bgra, PixelType.UnsignedByte, bmp_data.Scan0 );
    }
}
