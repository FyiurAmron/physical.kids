package vax.openglue.jogl;

import java.io.InputStream;
import java.nio.*;

import com.jogamp.opengl.GL3;

import com.jogamp.opengl.util.texture.TextureIO;
import vax.openglue.AbstractGlUe;
import vax.openglue.TextureData;

/**

 @author toor
 */
public class JoglUe3 extends AbstractGlUe {
    private GL3 gl;

    public JoglUe3 ( GL3 gl ) {
        this.gl = gl;
    }

    public void setGl ( GL3 gl ) {
        this.gl = gl;
    }

    @Override
    public TextureData<?> ueReadTexture ( InputStream inputStream ) {
        com.jogamp.opengl.util.texture.TextureData td = TextureIO.newTextureData( null, inputStream, true, null );
        td.
        return null;
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
    public int glCreateProgram () {
        return gl.glCreateProgram();
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
        gl.glGenVertexArrays( count, outBuffer, count );
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
    public void glGenTextures ( int count, IntBuffer outBuffer ) {
        gl.glGenTextures( count, outBuffer );
    }

    @Override
    public void glGenTextures ( int count, int[] outBuffer, int offset ) {
        gl.glGenBuffers( count, outBuffer, offset );
    }

    @Override
    public void glBindTexture ( int targetEnum, int handle ) {
        gl.glBindTexture( targetEnum, handle );
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

    @Override
    public int glGetError () {
        return gl.glGetError();
    }
}
