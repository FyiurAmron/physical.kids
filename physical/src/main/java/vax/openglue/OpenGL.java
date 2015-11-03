package vax.openglue;

import java.nio.*;

/**

 @author toor
 */
public interface OpenGl {
    void glViewport ( int x, int y, int width, int height );

    int glGetUniformLocation ( int shaderProgramHandle, String name );

    void glUniform1f ( int uniformLocation, float value );

    void glUniform2fv ( int uniformLocation, int count, float[] data, int offset );

    void glUniform3fv ( int uniformLocation, int count, float[] data, int offset );

    void glUniform4fv ( int uniformLocation, int count, float[] data, int offset );

    void glUniformMatrix4fv ( int uniformLocation, int count, boolean transpose, float[] data, int offset );

    void glUniform2fv ( int uniformLocation, int count, FloatBuffer data );

    void glUniform3fv ( int uniformLocation, int count, FloatBuffer data );

    void glUniform4fv ( int uniformLocation, int count, FloatBuffer data );

    void glUniformMatrix4fv ( int uniformLocation, int count, boolean transpose, FloatBuffer data );

    void glEnable ( int glParam );

    void glDisable ( int glParam );

    void glClear ( int i );

    void glClearColor ( float r, float g, float b, float a );

    int glCreateShader ( int shaderType );

    int glCreateProgram ();

    void glShaderSource ( int shaderHandle, int count, String[] sources, IntBuffer lengths );

    void glShaderSource ( int shaderHandle, int count, String[] sources, int[] lengths, int lengthsOffset );

    void glAttachShader ( int shaderProgramHandle, int vertexShaderHandle );

    void glBindAttribLocation ( int shaderProgramHandle, int index, String name );

    void glLinkProgram ( int shaderProgramHandle );

    void glUseProgram ( int shaderProgramHandle );

    void glCompileShader ( int vertexShaderHandle );

    void glGetProgramInfoLog ( int shaderProgramHandle, int maxLength, IntBuffer lengthBuf, ByteBuffer infoLogBuf );

    void glGetShaderInfoLog ( int vertexShaderHandle, int maxLength, IntBuffer lengthBuf, ByteBuffer infoLogBuf );

    void glGenTextures ( int count, int[] outBuffer, int offset );

    void glGenTextures ( int count, IntBuffer outBuffer );

    void glBindTexture ( int targetEnum, int handle );

    void glTexParameteri ( int targetEnum, int paramEnum, int param );

    void glTexParameterf ( int targetEnum, int paramEnum, float param );

    void glTexImage2D ( int targetEnum, int level, int internalFormat,
            int width, int height, int border, int format, int type, Buffer data );

    void glGenerateMipmap ( int targetEnum );

    void glGenBuffers ( int count, int[] outBuffer, int offset );

    void glGenBuffers ( int count, IntBuffer outBuffer );

    void glBindBuffer ( int targetEnum, int handle );

    void glEnableVertexAttribArray ( int attribNr );

    void glGenVertexArrays ( int count, IntBuffer outBuffer );

    void glGenVertexArrays ( int count, int[] outBuffer, int offset );

    void glBindVertexArray ( int vaoHandle );

    void glVertexAttribPointer ( int attribNr, int size, int typeEnum, boolean normalize, int stride, long pointer );

    void glDrawElements ( int primitiveType, int count, int valueType, long offset );
    /*
     // TODO
     void glDrawElements ( int primitiveType, int count, int valueType, IntBuffer buf );

     void glDrawElements ( int primitiveType, int count, int valueType, int[] data, int offset );
     */

    void glBufferData ( int bufferTarget, long size, Buffer data, int usageEnum );

    int glGetError ();
}
