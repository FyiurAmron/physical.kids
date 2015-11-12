package vax.openglue.headless;

import java.nio.*;
import vax.openglue.AbstractGLUE;
import vax.openglue.OpenGLUE;

/**

 @author toor
 */
public class HeadlessGLUE extends AbstractGLUE {
    @Override
    public void glViewport ( int x, int y, int width, int height ) {
    }

    @Override
    public int glGetUniformLocation ( int shaderProgramHandle, String name ) {
        return 0;
    }

    @Override
    public void glUniform1f ( int uniformLocation, float value ) {
    }

    @Override
    public void glUniform2fv ( int uniformLocation, int count, float[] data, int offset ) {
    }

    @Override
    public void glUniform3fv ( int uniformLocation, int count, float[] data, int offset ) {
    }

    @Override
    public void glUniform4fv ( int uniformLocation, int count, float[] data, int offset ) {
    }

    @Override
    public void glUniformMatrix4fv ( int uniformLocation, int count, boolean transpose, float[] data, int offset ) {
    }

    @Override
    public void glUniform2fv ( int uniformLocation, int count, FloatBuffer data ) {
    }

    @Override
    public void glUniform3fv ( int uniformLocation, int count, FloatBuffer data ) {
    }

    @Override
    public void glUniform4fv ( int uniformLocation, int count, FloatBuffer data ) {
    }

    @Override
    public void glUniformMatrix4fv ( int uniformLocation, int count, boolean transpose, FloatBuffer data ) {
    }

    @Override
    public void glEnable ( int glParam ) {
    }

    @Override
    public void glDisable ( int glParam ) {
    }

    @Override
    public void glClear ( int i ) {
    }

    @Override
    public void glClearColor ( float r, float g, float b, float a ) {
    }

    @Override
    public int glCreateShader ( int shaderType ) {
        return 0;
    }

    @Override
    public int glCreateProgram () {
        return 0;
    }

    @Override
    public void glShaderSource ( int shaderHandle, int count, String[] sources, IntBuffer lengths ) {
    }

    @Override
    public void glShaderSource ( int shaderHandle, int count, String[] sources, int[] lengths, int lengthsOffset ) {
    }

    @Override
    public void glAttachShader ( int shaderProgramHandle, int vertexShaderHandle ) {
    }

    @Override
    public void glBindAttribLocation ( int shaderProgramHandle, int index, String name ) {
    }

    @Override
    public void glLinkProgram ( int shaderProgramHandle ) {
    }

    @Override
    public void glUseProgram ( int shaderProgramHandle ) {
    }

    @Override
    public void glCompileShader ( int vertexShaderHandle ) {
    }

    @Override
    public void glGetProgramInfoLog ( int shaderProgramHandle, int maxLength, IntBuffer lengthBuf, ByteBuffer infoLogBuf ) {
    }

    @Override
    public void glGetShaderInfoLog ( int vertexShaderHandle, int maxLength, IntBuffer lengthBuf, ByteBuffer infoLogBuf ) {
    }

    @Override
    public void glGenTextures ( int count, int[] outBuffer, int offset ) {
    }

    @Override
    public void glGenTextures ( int count, IntBuffer outBuffer ) {
    }

    @Override
    public void glBindTexture ( int targetEnum, int handle ) {
    }

    @Override
    public void glTexParameteri ( int targetEnum, int paramEnum, int param ) {
    }

    @Override
    public void glTexParameterf ( int targetEnum, int paramEnum, float param ) {
    }

    @Override
    public void glTexImage2D ( int targetEnum, int level, int internalFormat, int width, int height, int border, int format, int type, Buffer data ) {
    }

    @Override
    public void glGenerateMipmap ( int targetEnum ) {
    }

    @Override
    public void glGenBuffers ( int count, int[] outBuffer, int offset ) {
    }

    @Override
    public void glGenBuffers ( int count, IntBuffer outBuffer ) {
    }

    @Override
    public void glBindBuffer ( int targetEnum, int handle ) {
    }

    @Override
    public void glEnableVertexAttribArray ( int attribNr ) {
    }

    @Override
    public void glGenVertexArrays ( int count, IntBuffer outBuffer ) {
    }

    @Override
    public void glGenVertexArrays ( int count, int[] outBuffer, int offset ) {
    }

    @Override
    public void glBindVertexArray ( int vaoHandle ) {
    }

    @Override
    public void glVertexAttribPointer ( int attribNr, int size, int typeEnum, boolean normalize, int stride, long pointer ) {
    }

    @Override
    public void glDrawElements ( int primitiveType, int count, int valueType, long offset ) {
    }

    @Override
    public void glBufferData ( int bufferTarget, long size, Buffer data, int usageEnum ) {
    }

    @Override
    public int glGetError () {
        return OpenGLUE.Constants.GL_NO_ERROR;
    }

}
