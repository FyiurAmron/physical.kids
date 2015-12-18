package vax.openglue;

import java.nio.*;
import vax.math.Vector4f;
import vax.openglue.constants.*;

/**

 @author toor
 */
public interface OpenGLUE extends OpenGL {
    /*
     public void glBindTexture ( int GL_TEXTURE_2D, int handle );

     public void glVertexAttribPointer ( int attribNr, int size, int GL_FLOAT, int GL_TRUE, int i, int i0 );
     */

    // new/signature-incompatible methods
    GLUtils ueGetGLUtils ();

    ImageGLUE getImageGLUE ();

    default void ueSetParam ( int glParam, boolean state ) {
        if ( state ) {
            glEnable( glParam );
        } else {
            glDisable( glParam );
        }
    }

    default ErrorCode ueGetError () {
        return ErrorCode.valueOf( glGetError() );
    }

    default String ueGetLogBufferContent () {
        return BufferUtils.toString( ueGetGLUtils().getLogBuffer() );
    }

    static void ueCheckError ( int errorEnum ) {
        if ( errorEnum != Constants.GL_NO_ERROR ) {
            throw new GLException( ErrorCode.valueOf( errorEnum ) );
        }
    }

    default void ueCheckError () {
        ueCheckError( glGetError() );
    }

    default String ueGetProgramInfoLog ( int shaderProgramHandle ) {
        GLUtils glu = ueGetGLUtils();
        ByteBuffer bb = glu.getLogBuffer();
        IntBuffer lbsb = glu.getTempIntBuffer( 1 );
        glGetProgramInfoLog( shaderProgramHandle, bb.capacity(), lbsb, bb );
        bb.limit( lbsb.get( 0 ) );
        return ueGetLogBufferContent();
    }

    default String ueGetShaderInfoLog ( int shaderHandle ) {
        GLUtils glu = ueGetGLUtils();
        ByteBuffer bb = glu.getLogBuffer();
        IntBuffer lbsb = glu.getTempIntBuffer( 1 );
        glGetShaderInfoLog( shaderHandle, bb.capacity(), lbsb, bb );
        bb.limit( lbsb.get( 0 ) );
        return ueGetLogBufferContent();
    }

    // legacy/signature-compatible method extenders
    default void glClear ( ClearBufferMask cbm, ClearBufferMask... cbms ) {
        glClear( OpenGlConstantWrapper.getGlConstant( cbm, cbms ) );
    }

    default int glCreateShader ( ShaderType shaderType ) {
        return glCreateShader( shaderType.getGlConstant() );
    }

    default void glBindBuffer ( BufferTarget bufferTarget, int handle ) {
        glBindBuffer( bufferTarget.getGlConstant(), handle );
    }

    default void glUniform1f ( int location, FloatBuffer value ) {
        glUniform1fv( location, 1, value );
    }

    default void glUniform1fv ( int location, FloatBuffer value ) {
        glUniform1fv( location, value.remaining(), value );
    }

    default void glUniform1i ( int location, IntBuffer value ) {
        glUniform1iv( location, 1, value );
    }

    default void glUniform1iv ( int location, IntBuffer value ) {
        glUniform1iv( location, value.remaining(), value );
    }

    default void glUniform2f ( int location, FloatBuffer value ) {
        glUniform2fv( location, 1, value );
    }

    default void glUniform2fv ( int location, FloatBuffer value ) {
        glUniform2fv( location, value.remaining() / 2, value );
    }

    default void glUniform2i ( int location, IntBuffer value ) {
        glUniform2iv( location, 1, value );
    }

    default void glUniform2iv ( int location, IntBuffer value ) {
        glUniform2iv( location, value.remaining() / 2, value );
    }

    default void glUniform3f ( int location, FloatBuffer value ) {
        glUniform3fv( location, 1, value );
    }

    default void glUniform3fv ( int location, FloatBuffer value ) {
        glUniform3fv( location, value.remaining() / 3, value );
    }

    default void glUniform3i ( int location, IntBuffer value ) {
        glUniform3iv( location, 1, value );
    }

    default void glUniform3iv ( int location, IntBuffer value ) {
        glUniform3iv( location, value.remaining() / 3, value );
    }

    default void glUniform4f ( int location, FloatBuffer value ) {
        glUniform4fv( location, 1, value );
    }

    default void glUniform4fv ( int location, FloatBuffer value ) {
        glUniform4fv( location, value.remaining() / 4, value );
    }

    default void glUniform4i ( int location, IntBuffer value ) {
        glUniform4iv( location, 1, value );
    }

    default void glUniform4iv ( int location, IntBuffer value ) {
        glUniform4iv( location, value.remaining() / 4, value );
    }

    default void glUniformMatrix2f ( int location, FloatBuffer value ) {
        glUniformMatrix2fv( location, 1, false, value );
    }

    default void glUniformMatrix2fv ( int location, FloatBuffer value ) {
        glUniformMatrix2fv( location, value.remaining() / 4, false, value );
    }

    default void glUniformMatrix3f ( int location, FloatBuffer value ) {
        glUniformMatrix3fv( location, 1, false, value );
    }

    default void glUniformMatrix3fv ( int location, FloatBuffer value ) {
        glUniformMatrix3fv( location, value.remaining() / 9, false, value );
    }

    default void glUniformMatrix4f ( int location, FloatBuffer value ) {
        glUniformMatrix4fv( location, 1, false, value );
    }

    default void glUniformMatrix4fv ( int location, FloatBuffer value ) {
        glUniformMatrix4fv( location, value.remaining() / 16, false, value );
    }

    default int glGetShaderi ( int shaderHandle, int paramEnum ) {
        IntBuffer ib = ueGetGLUtils().getTempIntBuffer( 1 );
        glGetShaderiv( shaderHandle, paramEnum, ib );
        ib.rewind();
        return ib.get();
    }

    default int glGetProgrami ( int shaderProgramHandle, int paramEnum ) {
        IntBuffer ib = ueGetGLUtils().getTempIntBuffer( 1 );
        glGetProgramiv( shaderProgramHandle, paramEnum, ib );
        ib.rewind();
        return ib.get();
    }

    default void glShaderSource ( int shaderHandle, String source ) {
        glShaderSource( shaderHandle, 1, ueGetGLUtils().toArray( source ), null );
    }

    default void glDrawBuffer ( int bufferHandle ) {
        glDrawBuffers( 1, ueGetGLUtils().wrap( bufferHandle ) );
    }

    default void glDrawBuffers ( IntBuffer bufferHandles ) {
        glDrawBuffers( bufferHandles.remaining(), bufferHandles );
    }

    default int glGenVertexArray () {
        IntBuffer ib = ueGetGLUtils().getTempIntBuffer( 1 );
        glGenVertexArrays( 1, ib );
        ib.rewind();
        return ib.get();
    }

    default void glDeleteVertexArrays ( IntBuffer vertexArrayNames ) {
        glDeleteVertexArrays( vertexArrayNames.remaining(), vertexArrayNames );
    }

    default void glDeleteVertexArray ( int vertexArrayHandle ) {
        glDeleteVertexArrays( 1, ueGetGLUtils().wrap( vertexArrayHandle ) );
    }

    default int glGenTexture () {
        IntBuffer ib = ueGetGLUtils().getTempIntBuffer( 1 );
        glGenTextures( 1, ib );
        ib.rewind();
        return ib.get();
    }

    default void glDeleteTextures ( IntBuffer textureNames ) {
        glDeleteTextures( textureNames.remaining(), textureNames );
    }

    default void glDeleteTexture ( int textureHandle ) {
        glDeleteTextures( 1, ueGetGLUtils().wrap( textureHandle ) );
    }

    default int glGenBuffer () {
        IntBuffer ib = ueGetGLUtils().getTempIntBuffer( 1 );
        glGenBuffers( 1, ib );
        ib.rewind();
        return ib.get();
    }

    default void glDeleteBuffers ( IntBuffer bufferNames ) {
        glDeleteBuffers( bufferNames.remaining(), bufferNames );
    }

    default void glDeleteBuffer ( int bufferHandle ) {
        glDeleteBuffers( 1, ueGetGLUtils().wrap( bufferHandle ) );
    }

    default int glGenFramebuffer () {
        IntBuffer ib = ueGetGLUtils().getTempIntBuffer( 1 );
        glGenFramebuffers( 1, ib );
        ib.rewind();
        return ib.get();
    }

    default void glDeleteFramebuffers ( IntBuffer bufferNames ) {
        glDeleteFramebuffers( bufferNames.remaining(), bufferNames );
    }

    default void glDeleteFramebuffer ( int bufferHandle ) {
        glDeleteFramebuffers( 1, ueGetGLUtils().wrap( bufferHandle ) );
    }

    default int glGenRenderbuffer () {
        IntBuffer ib = ueGetGLUtils().getTempIntBuffer( 1 );
        glGenRenderbuffers( 1, ib );
        ib.rewind();
        return ib.get();
    }

    default void glDeleteRenderbuffers ( IntBuffer bufferNames ) {
        glDeleteRenderbuffers( bufferNames.remaining(), bufferNames );
    }

    default void glDeleteRenderbuffer ( int bufferHandle ) {
        glDeleteRenderbuffers( 1, ueGetGLUtils().wrap( bufferHandle ) );
    }

    default void glClearColor ( Vector4f backgroundColor ) {
        glClearColor( backgroundColor.getX(), backgroundColor.getY(), backgroundColor.getZ(), backgroundColor.getW() );
    }

    /**
     This method <b>may</b> use <code>instanceof</code> to determine <code>Buffer</code> type
     (default behaviour if the implementation doesn't allow unknown <code>Buffer</code> calls directly)

     @param x
     @param y
     @param width
     @param height
     @param format
     @param type
     @param pixels
     */
    default void glReadPixels ( int x, int y, int width, int height, int format, int type, Buffer pixels ) {
        if ( pixels == null || pixels instanceof ByteBuffer ) {
            glReadPixels( x, y, width, height, format, type, (ByteBuffer) pixels );
        } else if ( pixels instanceof ShortBuffer ) {
            glReadPixels( x, y, width, height, format, type, (ShortBuffer) pixels );
        } else if ( pixels instanceof IntBuffer ) {
            glReadPixels( x, y, width, height, format, type, (IntBuffer) pixels );
        } else if ( pixels instanceof FloatBuffer ) {
            glReadPixels( x, y, width, height, format, type, (FloatBuffer) pixels );
            //} else if ( pixels instanceof DoubleBuffer ) {
            //    glReadPixels( x, y, width, height, format, type, (DoubleBuffer) pixels );
        } else {
            throw new GLException( "unknown buffer type" );
        }
    }

    /**
     This method <b>may</b> use <code>instanceof</code> to determine <code>Buffer</code> type
     (default behaviour if the implementation doesn't allow unknown <code>Buffer</code> calls directly)

     @param targetEnum
     @param level
     @param internalFormat
     @param width
     @param height
     @param border
     @param format
     @param type
     @param data
     */
    default void glTexImage2D ( int targetEnum, int level, int internalFormat,
            int width, int height, int border, int format, int type, Buffer data ) {
        if ( data == null || data instanceof ByteBuffer ) {
            glTexImage2D( targetEnum, level, internalFormat, width, height, border, format, type, (ByteBuffer) data );
        } else if ( data instanceof ShortBuffer ) {
            glTexImage2D( targetEnum, level, internalFormat, width, height, border, format, type, (ShortBuffer) data );
        } else if ( data instanceof IntBuffer ) {
            glTexImage2D( targetEnum, level, internalFormat, width, height, border, format, type, (IntBuffer) data );
        } else if ( data instanceof FloatBuffer ) {
            glTexImage2D( targetEnum, level, internalFormat, width, height, border, format, type, (FloatBuffer) data );
        } else if ( data instanceof DoubleBuffer ) {
            glTexImage2D( targetEnum, level, internalFormat, width, height, border, format, type, (DoubleBuffer) data );
        } else {
            throw new GLException( "unknown buffer type" );
        }
    }

    /**
     This method <b>may</b> use <code>instanceof</code> to determine <code>Buffer</code> type
     (default behaviour if the implementation doesn't allow unknown <code>Buffer</code> calls directly)

     @param tex
     @param level
     @param format
     @param type
     @param pixels
     */
    default void glGetTexImage ( int tex, int level, int format, int type, Buffer pixels ) {
        if ( pixels == null || pixels instanceof ByteBuffer ) {
            glGetTexImage( tex, level, format, type, pixels );
        } else if ( pixels instanceof ShortBuffer ) {
            glGetTexImage( tex, level, format, type, pixels );
        } else if ( pixels instanceof IntBuffer ) {
            glGetTexImage( tex, level, format, type, pixels );
        } else if ( pixels instanceof FloatBuffer ) {
            glGetTexImage( tex, level, format, type, pixels );
        } else if ( pixels instanceof DoubleBuffer ) {
            glGetTexImage( tex, level, format, type, pixels );
        } else {
            throw new GLException( "unknown buffer type" );
        }
    }

    /**
     This method <b>may</b> use <code>instanceof</code> to determine <code>Buffer</code> type
     (default behaviour if the implementation doesn't allow unknown <code>Buffer</code> calls directly)

     @param bufferTarget
     @param size
     @param data
     @param usageEnum
     */
    default void glBufferData ( int bufferTarget, long size, Buffer data, int usageEnum ) {
        if ( data == null || data instanceof ByteBuffer ) {
            glBufferData( bufferTarget, size, (ByteBuffer) data, usageEnum );
        } else if ( data instanceof ShortBuffer ) {
            glBufferData( bufferTarget, size, (ShortBuffer) data, usageEnum );
        } else if ( data instanceof IntBuffer ) {
            glBufferData( bufferTarget, size, (IntBuffer) data, usageEnum );
        } else if ( data instanceof FloatBuffer ) {
            glBufferData( bufferTarget, size, (FloatBuffer) data, usageEnum );
        } else if ( data instanceof DoubleBuffer ) {
            glBufferData( bufferTarget, size, (DoubleBuffer) data, usageEnum );
        } else {
            throw new GLException( "unknown buffer type" );
        }
    }
}
