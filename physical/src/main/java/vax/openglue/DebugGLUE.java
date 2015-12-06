package vax.openglue;

import java.nio.*;

/**
 An OpenGLUE interface wrapper, allowing easy glGetError() checking after each and every OpenGL call.
 <p>
 <em>Note: not to be used in production environment due to serious performance overhead.</em>

 @author toor
 */
public class DebugGLUE implements OpenGLUE {
    private OpenGLUE gl;

    public DebugGLUE () {
    }

    public DebugGLUE ( OpenGLUE gl ) {
        this.gl = gl;
    }

    public void setGlue ( OpenGLUE gl ) {
        this.gl = gl;
    }

    public OpenGLUE getGlue () {
        return gl;
    }

    @Override
    public GLUtils ueGetGLUtils () {
        return gl.ueGetGLUtils();
    }

    @Override
    public void glViewport ( int x, int y, int width, int height ) {
        gl.glViewport( x, y, width, height );
        ueCheckError();
    }

    @Override
    public int glGetUniformLocation ( int shaderProgramHandle, String name ) {
        int ret = gl.glGetUniformLocation( shaderProgramHandle, name );
        ueCheckError();
        return ret;
    }

    @Override
    public void glUniform1f ( int location, float v0 ) {
        gl.glUniform1f( location, v0 );
        ueCheckError();
    }

    @Override
    public void glUniform1fv ( int location, int count, FloatBuffer value ) {
        gl.glUniform1fv( location, count, value );
        ueCheckError();
    }

    @Override
    public void glUniform1fv ( int location, int count, float[] value, int valueOffset ) {
        gl.glUniform1fv( location, count, value, valueOffset );
        ueCheckError();
    }

    @Override
    public void glUniform1i ( int location, int v0 ) {
        gl.glUniform1i( location, v0 );
        ueCheckError();
    }

    @Override
    public void glUniform1iv ( int location, int count, IntBuffer value ) {
        gl.glUniform1iv( location, count, value );
        ueCheckError();
    }

    @Override
    public void glUniform1iv ( int location, int count, int[] value, int valueOffset ) {
        gl.glUniform1iv( location, count, value, valueOffset );
        ueCheckError();
    }

    @Override
    public void glUniform2f ( int location, float v0, float v1 ) {
        gl.glUniform2f( location, v0, v1 );
        ueCheckError();
    }

    @Override
    public void glUniform2fv ( int location, int count, FloatBuffer data ) {
        gl.glUniform2fv( location, count, data );
        ueCheckError();
    }

    @Override
    public void glUniform2fv ( int location, int count, float[] value, int valueOffset ) {
        gl.glUniform2fv( location, count, value, valueOffset );
        ueCheckError();
    }

    @Override
    public void glUniform2i ( int location, int v0, int v1 ) {
        gl.glUniform2i( location, v0, v1 );
        ueCheckError();
    }

    @Override
    public void glUniform2iv ( int location, int count, IntBuffer value ) {
        gl.glUniform2iv( location, count, value );
        ueCheckError();
    }

    @Override
    public void glUniform2iv ( int location, int count, int[] value, int valueOffset ) {
        gl.glUniform2iv( location, count, value, valueOffset );
        ueCheckError();
    }

    @Override
    public void glUniform3f ( int location, float v0, float v1, float v2 ) {
        gl.glUniform3f( location, v0, v1, v2 );
        ueCheckError();
    }

    @Override
    public void glUniform3fv ( int location, int count, FloatBuffer value ) {
        gl.glUniform3fv( location, count, value );
        ueCheckError();
    }

    @Override
    public void glUniform3fv ( int location, int count, float[] value, int offset ) {
        gl.glUniform3fv( location, count, value, offset );
        ueCheckError();
    }

    @Override
    public void glUniform3i ( int location, int v0, int v1, int v2 ) {
        gl.glUniform3i( location, v0, v1, v2 );
        ueCheckError();
    }

    @Override
    public void glUniform3iv ( int location, int count, IntBuffer value ) {
        gl.glUniform3iv( location, count, value );
        ueCheckError();
    }

    @Override
    public void glUniform3iv ( int location, int count, int[] value, int valueOffset ) {
        gl.glUniform3iv( location, count, value, valueOffset );
        ueCheckError();
    }

    @Override
    public void glUniform4f ( int location, float v0, float v1, float v2, float v3 ) {
        gl.glUniform4f( location, v0, v1, v2, v3 );
        ueCheckError();
    }

    @Override
    public void glUniform4fv ( int location, int count, FloatBuffer value ) {
        gl.glUniform4fv( location, count, value );
        ueCheckError();
    }

    @Override
    public void glUniform4fv ( int location, int count, float[] value, int valueOffset ) {
        gl.glUniform4fv( location, count, value, valueOffset );
        ueCheckError();
    }

    @Override
    public void glUniform4i ( int location, int v0, int v1, int v2, int v3 ) {
        gl.glUniform4i( location, v0, v1, v2, v3 );
        ueCheckError();
    }

    @Override
    public void glUniform4iv ( int location, int count, IntBuffer value ) {
        gl.glUniform4iv( location, count, value );
        ueCheckError();
    }

    @Override
    public void glUniform4iv ( int location, int count, int[] value, int valueOffset ) {
        gl.glUniform4iv( location, count, value, valueOffset );
        ueCheckError();
    }

    @Override
    public void glUniformMatrix2fv ( int location, int count, boolean transpose, FloatBuffer value ) {
        gl.glUniformMatrix2fv( location, count, transpose, value );
        ueCheckError();
    }

    @Override
    public void glUniformMatrix2fv ( int location, int count, boolean transpose, float[] value, int valueOffset ) {
        gl.glUniformMatrix2fv( location, count, transpose, value, valueOffset );
        ueCheckError();
    }

    @Override
    public void glUniformMatrix3fv ( int location, int count, boolean transpose, FloatBuffer value ) {
        gl.glUniformMatrix3fv( location, count, transpose, value );
        ueCheckError();
    }

    @Override
    public void glUniformMatrix3fv ( int location, int count, boolean transpose, float[] value, int valueOffset ) {
        gl.glUniformMatrix3fv( location, count, transpose, value, valueOffset );
        ueCheckError();
    }

    @Override
    public void glUniformMatrix4fv ( int location, int count, boolean transpose, FloatBuffer value ) {
        gl.glUniformMatrix4fv( location, count, transpose, value );
        ueCheckError();
    }

    @Override
    public void glUniformMatrix4fv ( int location, int count, boolean transpose, float[] value, int valueOffset ) {
        gl.glUniformMatrix4fv( location, count, transpose, value, valueOffset );
        ueCheckError();
    }

    @Override
    public void glEnable ( int glParam ) {
        gl.glEnable( glParam );
        ueCheckError();
    }

    @Override
    public void glDisable ( int glParam ) {
        gl.glDisable( glParam );
        ueCheckError();
    }

    @Override
    public void glClear ( int i ) {
        gl.glClear( i );
        ueCheckError();
    }

    @Override
    public void glClearColor ( float r, float g, float b, float a ) {
        gl.glClearColor( r, g, b, a );
        ueCheckError();
    }

    @Override
    public int glCreateShader ( int shaderType ) {
        int ret = gl.glCreateShader( shaderType );
        ueCheckError();
        return ret;
    }

    @Override
    public void glDeleteShader ( int shaderHandle ) {
        gl.glDeleteShader( shaderHandle );
        ueCheckError();
    }

    @Override
    public int glCreateProgram () {
        int ret = gl.glCreateProgram();
        ueCheckError();
        return ret;
    }

    @Override
    public void glDeleteProgram ( int shaderProgramHandle ) {
        gl.glDeleteProgram( shaderProgramHandle );
        ueCheckError();
    }

    @Override
    public void glShaderSource ( int shaderHandle, int count, String[] sources, IntBuffer lengths ) {
        gl.glShaderSource( shaderHandle, count, sources, lengths );
        ueCheckError();
    }

    @Override
    public void glShaderSource ( int shaderHandle, int count, String[] sources, int[] lengths, int lengthsOffset ) {
        gl.glShaderSource( shaderHandle, count, sources, lengths, lengthsOffset );
        ueCheckError();
    }

    @Override
    public void glAttachShader ( int shaderProgramHandle, int vertexShaderHandle ) {
        gl.glAttachShader( shaderProgramHandle, vertexShaderHandle );
        ueCheckError();
    }

    @Override
    public void glBindAttribLocation ( int shaderProgramHandle, int index, String name ) {
        gl.glBindAttribLocation( shaderProgramHandle, index, name );
        ueCheckError();
    }

    @Override
    public void glLinkProgram ( int shaderProgramHandle ) {
        gl.glLinkProgram( shaderProgramHandle );
        ueCheckError();
    }

    @Override
    public void glUseProgram ( int shaderProgramHandle ) {
        gl.glUseProgram( shaderProgramHandle );
        ueCheckError();
    }

    @Override
    public void glCompileShader ( int vertexShaderHandle ) {
        gl.glCompileShader( vertexShaderHandle );
        ueCheckError();
    }

    @Override
    public void glGetShaderiv ( int shaderHandle, int paramEnum, int[] params, int offset ) {
        gl.glGetShaderiv( shaderHandle, paramEnum, params, offset );
        ueCheckError();
    }

    @Override
    public void glGetShaderiv ( int shaderHandle, int paramEnum, IntBuffer params ) {
        gl.glGetShaderiv( shaderHandle, paramEnum, params );
        ueCheckError();
    }

    @Override
    public void glGetProgramiv ( int shaderProgramHandle, int paramEnum, int[] params, int offset ) {
        gl.glGetProgramiv( shaderProgramHandle, paramEnum, params, offset );
        ueCheckError();
    }

    @Override
    public void glGetProgramiv ( int shaderProgramHandle, int paramEnum, IntBuffer params ) {
        gl.glGetProgramiv( shaderProgramHandle, paramEnum, params );
        ueCheckError();
    }

    @Override
    public void glGetProgramInfoLog ( int shaderProgramHandle, int maxLength, IntBuffer lengthBuf, ByteBuffer infoLogBuf ) {
        gl.glGetProgramInfoLog( shaderProgramHandle, maxLength, lengthBuf, infoLogBuf );
        ueCheckError();
    }

    @Override
    public void glGetShaderInfoLog ( int shaderHandle, int maxLength, IntBuffer lengthBuf, ByteBuffer infoLogBuf ) {
        gl.glGetShaderInfoLog( shaderHandle, maxLength, lengthBuf, infoLogBuf );
        ueCheckError();
    }

    @Override
    public void glGetShaderInfoLog ( int vertexShaderHandle, int maxLength,
            int[] length, int lengthOffset, byte[] infoLog, int infoLogOffset ) {
        gl.glGetShaderInfoLog( vertexShaderHandle, maxLength, length, lengthOffset, infoLog, infoLogOffset );
        ueCheckError();
    }

    @Override
    public void glGetProgramInfoLog ( int shaderProgramHandle, int maxLength,
            int[] length, int lengthOffset, byte[] infoLog, int infoLogOffset ) {
        gl.glGetProgramInfoLog( shaderProgramHandle, maxLength, length, lengthOffset, infoLog, infoLogOffset );
        ueCheckError();
    }

    @Override
    public void glGenTextures ( int count, int[] outBuffer, int offset ) {
        gl.glGenTextures( count, outBuffer, offset );
        ueCheckError();
    }

    @Override
    public void glGenTextures ( int count, IntBuffer outBuffer ) {
        gl.glGenTextures( count, outBuffer );
        ueCheckError();
    }

    @Override
    public void glBindTexture ( int targetEnum, int handle ) {
        gl.glBindTexture( targetEnum, handle );
        ueCheckError();
    }

    @Override
    public void glTexParameteri ( int targetEnum, int paramEnum, int param ) {
        gl.glTexParameteri( targetEnum, paramEnum, param );
        ueCheckError();
    }

    @Override
    public void glTexParameterf ( int targetEnum, int paramEnum, float param ) {
        gl.glTexParameterf( targetEnum, paramEnum, param );
        ueCheckError();
    }

    @Override
    public void glTexImage2D ( int targetEnum, int level, int internalFormat, int width, int height, int border, int format, int type,
            Buffer data ) {
        gl.glTexImage2D( targetEnum, level, internalFormat, width, height, border, format, type, data );
        ueCheckError();
    }

    @Override
    public void glGenerateMipmap ( int targetEnum ) {
        gl.glGenerateMipmap( targetEnum );
        ueCheckError();
    }

    @Override
    public void glGenBuffers ( int count, int[] outBuffer, int offset ) {
        gl.glGenBuffers( count, outBuffer, offset );
        ueCheckError();
    }

    @Override
    public void glGenBuffers ( int count, IntBuffer outBuffer ) {
        gl.glGenBuffers( count, outBuffer );
        ueCheckError();
    }

    @Override
    public void glBindBuffer ( int targetEnum, int handle ) {
        gl.glBindBuffer( targetEnum, handle );
        ueCheckError();
    }

    @Override
    public void glDeleteBuffers ( int count, int[] bufferNames, int offset ) {
        gl.glDeleteBuffers( count, bufferNames, offset );
        ueCheckError();
    }

    @Override
    public void glDeleteBuffers ( int count, IntBuffer bufferNames ) {
        gl.glDeleteBuffers( count, bufferNames );
        ueCheckError();
    }

    @Override
    public void glEnableVertexAttribArray ( int attribNr ) {
        gl.glEnableVertexAttribArray( attribNr );
        ueCheckError();
    }

    @Override
    public void glGenVertexArrays ( int count, IntBuffer outBuffer ) {
        gl.glGenVertexArrays( count, outBuffer );
        ueCheckError();
    }

    @Override
    public void glGenVertexArrays ( int count, int[] outBuffer, int offset ) {
        gl.glGenVertexArrays( count, outBuffer, offset );
        ueCheckError();
    }

    @Override
    public void glDeleteTextures ( int n, IntBuffer textures ) {
        gl.glDeleteTextures( n, textures );
        ueCheckError();
    }

    @Override
    public void glDeleteTextures ( int n, int[] textures, int offset ) {
        gl.glDeleteTextures( n, textures, offset );
        ueCheckError();
    }

    @Override
    public void glGenFramebuffers ( int n, IntBuffer framebuffers ) {
        gl.glGenFramebuffers( n, framebuffers );
        ueCheckError();
    }

    @Override
    public void glGenFramebuffers ( int n, int[] framebuffers, int offset ) {
        gl.glGenFramebuffers( n, framebuffers, offset );
        ueCheckError();
    }

    @Override
    public void glBindFramebuffer ( int target, int framebuffer ) {
        gl.glBindFramebuffer( target, framebuffer );
        ueCheckError();
    }

    @Override
    public void glDeleteFramebuffers ( int n, IntBuffer framebuffers ) {
        gl.glDeleteFramebuffers( n, framebuffers );
        ueCheckError();
    }

    @Override
    public void glDeleteFramebuffers ( int n, int[] framebuffers, int offset ) {
        gl.glDeleteFramebuffers( n, framebuffers, offset );
        ueCheckError();
    }

    @Override
    public void glGenRenderbuffers ( int n, IntBuffer renderbuffers ) {
        gl.glGenRenderbuffers( n, renderbuffers );
        ueCheckError();
    }

    @Override
    public void glGenRenderbuffers ( int n, int[] renderbuffers, int offset ) {
        gl.glGenRenderbuffers( n, renderbuffers, offset );
        ueCheckError();
    }

    @Override
    public void glBindRenderbuffer ( int target, int renderbuffer ) {
        gl.glBindRenderbuffer( target, renderbuffer );
        ueCheckError();
    }

    @Override
    public void glDeleteRenderbuffers ( int n, IntBuffer renderbuffers ) {
        gl.glDeleteRenderbuffers( n, renderbuffers );
        ueCheckError();
    }

    @Override
    public void glDeleteRenderbuffers ( int n, int[] renderbuffers, int offset ) {
        gl.glDeleteRenderbuffers( n, renderbuffers, offset );
        ueCheckError();
    }

    @Override
    public void glDeleteVertexArrays ( int n, IntBuffer arrays ) {
        gl.glDeleteVertexArrays( n, arrays );
        ueCheckError();
    }

    @Override
    public void glDeleteVertexArrays ( int n, int[] arrays, int offset ) {
        gl.glDeleteVertexArrays( n, arrays, offset );
        ueCheckError();
    }

    @Override
    public void glBindVertexArray ( int vaoHandle ) {
        gl.glBindVertexArray( vaoHandle );
        ueCheckError();
    }

    @Override
    public void glVertexAttribPointer ( int attribNr, int size, int typeEnum, boolean normalize, int stride, long pointer ) {
        gl.glVertexAttribPointer( attribNr, size, typeEnum, normalize, stride, pointer );
        ueCheckError();
    }

    @Override
    public void glDrawElements ( int primitiveType, int count, int valueType, long offset ) {
        gl.glDrawElements( primitiveType, count, valueType, offset );
        ueCheckError();
    }

    @Override
    public void glPolygonMode ( int faceEnum, int modeEnum ) {
        gl.glPolygonMode( faceEnum, modeEnum );
        ueCheckError();
    }

    @Override
    public void glCullFace ( int cullFaceEnum ) {
        gl.glCullFace( cullFaceEnum );
        ueCheckError();
    }

    @Override
    public void glBufferData ( int bufferTarget, long size, Buffer data, int usageEnum ) {
        gl.glBufferData( bufferTarget, size, data, usageEnum );
        ueCheckError();
    }

    @Override
    public void glBlendFunc ( int blendSourceEnum, int blendDestEnum ) {
        gl.glBlendFunc( blendSourceEnum, blendDestEnum );
        ueCheckError();
    }

    @Override
    public void glBlendFuncSeparate ( int blendRgbSourceEnum, int blendRgbDestEnum, int blendAlphaSourceEnum, int blendAlphaDestEnum ) {
        gl.glBlendFuncSeparate( blendRgbSourceEnum, blendRgbDestEnum, blendAlphaSourceEnum, blendAlphaDestEnum );
        ueCheckError();
    }

    @Override
    public int glGetError () {
        return gl.glGetError();
    }
}
