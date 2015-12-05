package vax.openglue.jogl;

import java.nio.*;

import com.jogamp.opengl.*;

import vax.openglue.AbstractGLUE;
import vax.openglue.BufferGLUE;
import vax.openglue.ImageIO;

/**

 @author toor
 */
public class JoglUe3 extends AbstractGLUE {
    public static final GLProfile DEFAULT_PROFILE = GLProfile.getGL2GL3();
    private GL2GL3 gl;

    public JoglUe3 () {
    }

    public JoglUe3 ( GL2GL3 gl ) {
        this.gl = gl;
    }

    public JoglUe3 setGl ( GL2GL3 gl ) {
        this.gl = gl;
        return this;
    }

    public JoglUe3 setGl ( GLAutoDrawable drawable ) {
        this.gl = drawable.getGL().getGL2GL3();
        return this;
    }

    @Override
    public Class<? extends BufferGLUE> getClassBufferGLUE () {
        return JoglBufferGLUE.class;
    }

    @Override
    public Class<? extends ImageIO.GLUE> getClassImageIO_GLUE () {
        return JoglImageIoGLUE.class;
    }

    @Override
    public int glGetUniformLocation ( int shaderProgramHandle, String name ) {
        return gl.glGetUniformLocation( shaderProgramHandle, name );
    }

    @Override
    public void glUniform1f ( int uniformLocation, float value ) {
        gl.glUniform1f( uniformLocation, value );
    }

    @Override
    public void glUniform2fv ( int uniformLocation, int i, float[] data, int offset ) {
        gl.glUniform2fv( uniformLocation, i, data, offset );
    }

    @Override
    public void glUniform3fv ( int uniformLocation, int i, float[] data, int offset ) {
        gl.glUniform3fv( uniformLocation, i, data, offset );
    }

    @Override
    public void glUniform4fv ( int uniformLocation, int i, float[] data, int offset ) {
        gl.glUniform4fv( uniformLocation, i, data, offset );
    }

    @Override
    public void glUniformMatrix4fv ( int uniformLocation, int count, boolean transpose, float[] data, int offset ) {
        gl.glUniformMatrix4fv( uniformLocation, count, transpose, data, offset );
    }

    @Override
    public void glUniform2fv ( int uniformLocation, int count, FloatBuffer data ) {
        gl.glUniform2fv( count, count, data );
    }

    @Override
    public void glUniform3fv ( int uniformLocation, int count, FloatBuffer data ) {
        gl.glUniform3fv( count, count, data );
    }

    @Override
    public void glUniform4fv ( int uniformLocation, int count, FloatBuffer data ) {
        gl.glUniform3fv( count, count, data );
    }

    @Override
    public void glUniformMatrix4fv ( int uniformLocation, int count, boolean transpose, FloatBuffer data ) {
        gl.glUniformMatrix4fv( uniformLocation, count, transpose, data );
    }

    @Override
    public void glEnable ( int glParam ) {
        gl.glEnable( glParam );
    }

    @Override
    public void glDisable ( int glParam ) {
        gl.glDisable( glParam );
    }

    @Override
    public void glClear ( int i ) {
        gl.glClear( i );
    }

    @Override
    public void glCompileShader ( int vertexShaderHandle ) {
        gl.glCompileShader( vertexShaderHandle );
    }

    @Override
    public int glCreateShader ( int shaderType ) {
        return gl.glCreateShader( shaderType );
    }

    @Override
    public void glDeleteShader ( int shaderHandle ) {
        gl.glDeleteShader( shaderHandle );
    }

    @Override
    public int glCreateProgram () {
        return gl.glCreateProgram();
    }

    @Override
    public void glDeleteProgram ( int shaderProgramHandle ) {
        gl.glDeleteProgram( shaderProgramHandle );
    }

    @Override
    public void glAttachShader ( int shaderProgramHandle, int vertexShaderHandle ) {
        gl.glAttachShader( shaderProgramHandle, vertexShaderHandle );
    }

    @Override
    public void glBindAttribLocation ( int shaderProgramHandle, int index, String name ) {
        gl.glBindAttribLocation( shaderProgramHandle, index, name );
    }

    @Override
    public void glLinkProgram ( int shaderProgramHandle ) {
        gl.glLinkProgram( shaderProgramHandle );
    }

    @Override
    public void glGetShaderiv ( int shaderHandle, int paramEnum, int[] params, int offset ) {
        gl.glGetShaderiv( shaderHandle, paramEnum, params, offset );
    }

    @Override
    public void glGetShaderiv ( int shaderHandle, int paramEnum, IntBuffer params ) {
        gl.glGetShaderiv( shaderHandle, paramEnum, params );
    }

    @Override
    public void glGetProgramiv ( int shaderProgramHandle, int paramEnum, int[] params, int offset ) {
        gl.glGetProgramiv( shaderProgramHandle, paramEnum, params, offset );
    }

    @Override
    public void glGetProgramiv ( int shaderProgramHandle, int paramEnum, IntBuffer params ) {
        gl.glGetProgramiv( shaderProgramHandle, paramEnum, params );
    }

    @Override
    public void glGetShaderInfoLog ( int vertexShaderHandle, int maxLength, IntBuffer lengthBuf, ByteBuffer infoLogBuf ) {
        gl.glGetShaderInfoLog( vertexShaderHandle, maxLength, lengthBuf, infoLogBuf );
    }

    @Override
    public void glGetProgramInfoLog ( int shaderProgramHandle, int maxLength, IntBuffer lengthBuf, ByteBuffer infoLogBuf ) {
        gl.glGetProgramInfoLog( shaderProgramHandle, maxLength, lengthBuf, infoLogBuf );
    }

    @Override
    public void glGetShaderInfoLog ( int vertexShaderHandle, int maxLength,
            int[] length, int lengthOffset, byte[] infoLog, int infoLogOffset ) {
        gl.glGetShaderInfoLog( vertexShaderHandle, maxLength, length, lengthOffset, infoLog, infoLogOffset );
    }

    @Override
    public void glGetProgramInfoLog ( int shaderProgramHandle, int maxLength,
            int[] length, int lengthOffset, byte[] infoLog, int infoLogOffset ) {
        gl.glGetProgramInfoLog( shaderProgramHandle, maxLength, length, lengthOffset, infoLog, infoLogOffset );
    }

    @Override
    public void glUseProgram ( int shaderProgramHandle ) {
        gl.glUseProgram( shaderProgramHandle );
    }

    @Override
    public void glVertexAttribPointer ( int attribNr, int size, int typeEnum, boolean normalize, int stride, long pointer ) {
        gl.glVertexAttribPointer( attribNr, size, typeEnum, normalize, stride, pointer );
    }

    @Override
    public void glShaderSource ( int shaderHandle, int count, String[] sources, IntBuffer lengths ) {
        gl.glShaderSource( shaderHandle, count, sources, lengths );
    }

    @Override
    public void glShaderSource ( int shaderHandle, int count, String[] sources, int[] lengths, int lengthsOffset ) {
        gl.glShaderSource( shaderHandle, count, sources, lengths, lengthsOffset );
    }

    @Override
    public void glEnableVertexAttribArray ( int attribNr ) {
        gl.glEnableVertexAttribArray( attribNr );
    }

    @Override
    public void glGenVertexArrays ( int count, IntBuffer outBuffer ) {
        gl.glGenVertexArrays( count, outBuffer );
    }

    @Override
    public void glGenVertexArrays ( int count, int[] outBuffer, int offset ) {
        gl.glGenVertexArrays( count, outBuffer, offset );
    }

    @Override
    public void glBindVertexArray ( int vaoHandle ) {
        gl.glBindVertexArray( vaoHandle );
    }

    @Override
    public void glGenBuffers ( int count, IntBuffer outBuffer ) {
        gl.glGenBuffers( count, outBuffer );
    }

    @Override
    public void glGenBuffers ( int count, int[] outBuffer, int offset ) {
        gl.glGenBuffers( count, outBuffer, offset );
    }

    @Override
    public void glBindBuffer ( int targetEnum, int handle ) {
        gl.glBindBuffer( targetEnum, handle );
    }

    @Override
    public void glDeleteBuffers ( int count, int[] bufferNames, int offset ) {
        gl.glDeleteBuffers( count, bufferNames, offset );
    }

    @Override
    public void glDeleteBuffers ( int count, IntBuffer bufferNames ) {
        gl.glDeleteBuffers( count, bufferNames );
    }

    @Override
    public void glGenTextures ( int count, IntBuffer outBuffer ) {
        gl.glGenTextures( count, outBuffer );
    }

    @Override
    public void glGenTextures ( int count, int[] outBuffer, int offset ) {
        gl.glGenTextures( count, outBuffer, offset );
    }

    @Override
    public void glBindTexture ( int targetEnum, int handle ) {
        gl.glBindTexture( targetEnum, handle );
    }

    @Override
    public void glDeleteTextures ( int n, IntBuffer textures ) {
        gl.glDeleteTextures( n, textures );
    }

    @Override
    public void glDeleteTextures ( int n, int[] textures, int offset ) {
        gl.glDeleteTextures( n, textures, offset );
    }

    @Override
    public void glGenFramebuffers ( int n, IntBuffer framebuffers ) {
        gl.glGenFramebuffers( n, framebuffers );
    }

    @Override
    public void glGenFramebuffers ( int n, int[] framebuffers, int offset ) {
        gl.glGenFramebuffers( n, framebuffers, offset );
    }

    @Override
    public void glBindFramebuffer ( int target, int framebuffer ) {
        gl.glBindFramebuffer( target, framebuffer );
    }

    @Override
    public void glDeleteFramebuffers ( int n, IntBuffer framebuffers ) {
        gl.glDeleteFramebuffers( n, framebuffers );
    }

    @Override
    public void glDeleteFramebuffers ( int n, int[] framebuffers, int offset ) {
        gl.glDeleteFramebuffers( n, framebuffers, offset );
    }

    @Override
    public void glGenRenderbuffers ( int n, IntBuffer renderbuffers ) {
        gl.glGenRenderbuffers( n, renderbuffers );
    }

    @Override
    public void glGenRenderbuffers ( int n, int[] renderbuffers, int offset ) {
        gl.glGenRenderbuffers( n, renderbuffers, offset );
    }

    @Override
    public void glBindRenderbuffer ( int target, int renderbuffer ) {
        gl.glBindRenderbuffer( target, renderbuffer );
    }

    @Override
    public void glDeleteRenderbuffers ( int n, IntBuffer renderbuffers ) {
        gl.glDeleteRenderbuffers( n, renderbuffers );
    }

    @Override
    public void glDeleteRenderbuffers ( int n, int[] renderbuffers, int offset ) {
        gl.glDeleteRenderbuffers( n, renderbuffers, offset );
    }

    @Override
    public void glDeleteVertexArrays ( int n, IntBuffer arrays ) {
        gl.glDeleteVertexArrays( n, arrays );
    }

    @Override
    public void glDeleteVertexArrays ( int n, int[] arrays, int offset ) {
        gl.glDeleteVertexArrays( n, arrays, offset );
    }

    @Override
    public void glTexImage2D ( int targetEnum, int level, int internalFormat,
            int width, int height, int border, int format, int type, Buffer data ) {
        gl.glTexImage2D( targetEnum, level, internalFormat, width, height, border, format, type, data );
    }

    @Override
    public void glTexParameteri ( int targetEnum, int paramEnum, int param ) {
        gl.glTexParameteri( targetEnum, paramEnum, param );
    }

    @Override
    public void glTexParameterf ( int targetEnum, int paramEnum, float param ) {
        gl.glTexParameterf( targetEnum, paramEnum, param );
    }

    @Override
    public void glGenerateMipmap ( int targetEnum ) {
        gl.glGenerateMipmap( targetEnum );
    }

    /*

     @Override
     public void glDrawElements ( int primitiveType, int count, int valueType, IntBuffer buf ) {
     //gl.glDraw
     gl
     .glDrawElements( primitiveType, count, valueType, i );
     }

     @Override
     public void glDrawElements ( int primitiveType, int count, int valueType, int[] data, int offset ) {
     gl
     .glDrawElements( primitiveType, count, valueType, i );
     }
     */
    @Override
    public void glDrawElements ( int primitiveType, int count, int valueType, long offset ) {
        gl.glDrawElements( primitiveType, count, valueType, offset );
    }

    @Override
    public void glPolygonMode ( int faceEnum, int modeEnum ) {
        gl.glPolygonMode( faceEnum, modeEnum );
    }

    @Override
    public void glCullFace ( int cullFaceEnum ) {
        gl.glCullFace( cullFaceEnum );
    }

    @Override
    public void glClearColor ( float r, float g, float b, float a ) {
        gl.glClearColor( r, g, b, a );
    }

    @Override
    public void glViewport ( int x, int y, int width, int height ) {
        gl.glViewport( x, y, width, height );
    }

    @Override
    public void glBufferData ( int bufferTarget, long size, Buffer data, int usageEnum ) {
        gl.glBufferData( bufferTarget, size, data, usageEnum );
    }

    @Override
    public void glBlendFunc ( int blendSourceEnum, int blendDestEnum ) {
        gl.glBlendFunc( blendSourceEnum, blendDestEnum );
    }

    @Override
    public void glBlendFuncSeparate ( int blendRgbSourceEnum, int blendRgbDestEnum, int blendAlphaSourceEnum, int blendAlphaDestEnum ) {
        gl.glBlendFuncSeparate( blendRgbSourceEnum, blendRgbDestEnum, blendAlphaSourceEnum, blendAlphaDestEnum );
    }

    @Override
    public int glGetError () {
        return gl.glGetError();
    }
}
