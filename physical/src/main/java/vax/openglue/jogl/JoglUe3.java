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
    public void glUniform1f ( int location, float v0 ) {
        gl.glUniform1f( location, v0 );
    }

    @Override
    public void glUniform1fv ( int location, int count, FloatBuffer value ) {
        gl.glUniform1fv( location, count, value );
    }

    @Override
    public void glUniform1i ( int location, int v0 ) {
        gl.glUniform1i( location, v0 );
    }

    @Override
    public void glUniform1iv ( int location, int count, IntBuffer value ) {
        gl.glUniform1iv( location, count, value );
    }

    @Override
    public void glUniform2f ( int location, float v0, float v1 ) {
        gl.glUniform2f( location, v0, v1 );
    }

    @Override
    public void glUniform2fv ( int location, int count, FloatBuffer data ) {
        gl.glUniform2fv( location, count, data );
    }

    @Override
    public void glUniform2i ( int location, int v0, int v1 ) {
        gl.glUniform2i( location, v0, v1 );
    }

    @Override
    public void glUniform2iv ( int location, int count, IntBuffer value ) {
        gl.glUniform2iv( location, count, value );
    }

    @Override
    public void glUniform3f ( int location, float v0, float v1, float v2 ) {
        gl.glUniform3f( location, v0, v1, v2 );
    }

    @Override
    public void glUniform3fv ( int location, int count, FloatBuffer value ) {
        gl.glUniform3fv( location, count, value );
    }

    @Override
    public void glUniform3i ( int location, int v0, int v1, int v2 ) {
        gl.glUniform3i( location, v0, v1, v2 );
    }

    @Override
    public void glUniform3iv ( int location, int count, IntBuffer value ) {
        gl.glUniform3iv( location, count, value );
    }

    @Override
    public void glUniform4f ( int location, float v0, float v1, float v2, float v3 ) {
        gl.glUniform4f( location, v0, v1, v2, v3 );
    }

    @Override
    public void glUniform4fv ( int location, int count, FloatBuffer value ) {
        gl.glUniform4fv( location, count, value );
    }

    @Override
    public void glUniform4i ( int location, int v0, int v1, int v2, int v3 ) {
        gl.glUniform4i( location, v0, v1, v2, v3 );
    }

    @Override
    public void glUniform4iv ( int location, int count, IntBuffer value ) {
        gl.glUniform4iv( location, count, value );
    }

    @Override
    public void glUniformMatrix2fv ( int location, int count, boolean transpose, FloatBuffer value ) {
        gl.glUniformMatrix2fv( location, count, transpose, value );
    }

    @Override
    public void glUniformMatrix3fv ( int location, int count, boolean transpose, FloatBuffer value ) {
        gl.glUniformMatrix3fv( location, count, transpose, value );
    }

    @Override
    public void glUniformMatrix4fv ( int location, int count, boolean transpose, FloatBuffer value ) {
        gl.glUniformMatrix4fv( location, count, transpose, value );
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
    public void glGetShaderiv ( int shaderHandle, int paramEnum, IntBuffer params ) {
        gl.glGetShaderiv( shaderHandle, paramEnum, params );
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
    public void glTexImage2D ( int targetEnum, int level, int internalFormat,
            int width, int height, int border, int format, int type, ByteBuffer data ) {
        gl.glTexImage2D( targetEnum, level, internalFormat, width, height, border, format, type, data );
    }

    @Override
    public void glTexImage2D ( int targetEnum, int level, int internalFormat,
            int width, int height, int border, int format, int type, ShortBuffer data ) {
        gl.glTexImage2D( targetEnum, level, internalFormat, width, height, border, format, type, data );
    }

    @Override
    public void glTexImage2D ( int targetEnum, int level, int internalFormat,
            int width, int height, int border, int format, int type, IntBuffer data ) {
        gl.glTexImage2D( targetEnum, level, internalFormat, width, height, border, format, type, data );
    }

    @Override
    public void glTexImage2D ( int targetEnum, int level, int internalFormat,
            int width, int height, int border, int format, int type, FloatBuffer data ) {
        gl.glTexImage2D( targetEnum, level, internalFormat, width, height, border, format, type, data );
    }

    @Override
    public void glTexImage2D ( int targetEnum, int level, int internalFormat,
            int width, int height, int border, int format, int type, DoubleBuffer data ) {
        gl.glTexImage2D( targetEnum, level, internalFormat, width, height, border, format, type, data );
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
    public void glBufferData ( int bufferTarget, long size, ByteBuffer data, int usageEnum ) {
        gl.glBufferData( bufferTarget, size, data, usageEnum );
    }

    @Override
    public void glBufferData ( int bufferTarget, ByteBuffer data, int usageEnum ) {
        gl.glBufferData( bufferTarget, data.limit(), data, usageEnum );
    }

    @Override
    public void glBufferData ( int bufferTarget, long size, ShortBuffer data, int usageEnum ) {
        gl.glBufferData( bufferTarget, size, data, usageEnum );
    }

    @Override
    public void glBufferData ( int bufferTarget, long size, IntBuffer data, int usageEnum ) {
        gl.glBufferData( bufferTarget, size, data, usageEnum );
    }

    @Override
    public void glBufferData ( int bufferTarget, long size, FloatBuffer data, int usageEnum ) {
        gl.glBufferData( bufferTarget, size, data, usageEnum );
    }

    @Override
    public void glBufferData ( int bufferTarget, long size, DoubleBuffer data, int usageEnum ) {
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
    public void glEnableVertexAttribArray ( int attribNr ) {
        gl.glEnableVertexAttribArray( attribNr );
    }

    @Override
    public void glGenVertexArrays ( int count, IntBuffer outBuffer ) {
        gl.glGenVertexArrays( count, outBuffer );
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
    public void glBindBuffer ( int targetEnum, int handle ) {
        gl.glBindBuffer( targetEnum, handle );
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
    public void glBindTexture ( int targetEnum, int handle ) {
        gl.glBindTexture( targetEnum, handle );
    }

    @Override
    public void glDeleteTextures ( int n, IntBuffer textures ) {
        gl.glDeleteTextures( n, textures );
    }

    @Override
    public void glGenFramebuffers ( int n, IntBuffer framebuffers ) {
        gl.glGenFramebuffers( n, framebuffers );
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
    public void glGenRenderbuffers ( int n, IntBuffer renderbuffers ) {
        gl.glGenRenderbuffers( n, renderbuffers );
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
    public void glDeleteVertexArrays ( int n, IntBuffer arrays ) {
        gl.glDeleteVertexArrays( n, arrays );
    }

    @Override
    public int glGetError () {
        return gl.glGetError();
    }
}
