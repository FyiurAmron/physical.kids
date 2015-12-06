package vax.openglue.headless;

import java.nio.*;
import vax.openglue.*;

/**

 @author toor
 */
public class HeadlessGLUE extends AbstractGLUE {
    @Override
    public Class<? extends BufferGLUE> getClassBufferGLUE () {
        return HeadlessBufferGLUE.class;
    }

    @Override
    public Class<? extends ImageIO.GLUE> getClassImageIO_GLUE () {
        return HeadlessImageIO.class;
    }

    @Override
    public void glViewport ( int x, int y, int width, int height ) {
    }

    @Override
    public int glGetUniformLocation ( int shaderProgramHandle, String name ) {
        return 0;
    }

    @Override
    public void glUniform1f ( int location, float v0 ) {
    }

    @Override
    public void glUniform1fv ( int location, int count, FloatBuffer value ) {
    }

    @Override
    public void glUniform1fv ( int location, int count, float[] value, int valueOffset ) {
    }

    @Override
    public void glUniform1i ( int location, int v0 ) {
    }

    @Override
    public void glUniform1iv ( int location, int count, IntBuffer value ) {
    }

    @Override
    public void glUniform1iv ( int location, int count, int[] value, int valueOffset ) {
    }

    @Override
    public void glUniform2f ( int location, float v0, float v1 ) {
    }

    @Override
    public void glUniform2fv ( int location, int count, FloatBuffer data ) {
    }

    @Override
    public void glUniform2fv ( int location, int count, float[] value, int valueOffset ) {
    }

    @Override
    public void glUniform2i ( int location, int v0, int v1 ) {
    }

    @Override
    public void glUniform2iv ( int location, int count, IntBuffer value ) {
    }

    @Override
    public void glUniform2iv ( int location, int count, int[] value, int valueOffset ) {
    }

    @Override
    public void glUniform3f ( int location, float v0, float v1, float v2 ) {
    }

    @Override
    public void glUniform3fv ( int location, int count, FloatBuffer value ) {
    }

    @Override
    public void glUniform3fv ( int location, int count, float[] value, int offset ) {
    }

    @Override
    public void glUniform3i ( int location, int v0, int v1, int v2 ) {
    }

    @Override
    public void glUniform3iv ( int location, int count, IntBuffer value ) {
    }

    @Override
    public void glUniform3iv ( int location, int count, int[] value, int valueOffset ) {
    }

    @Override
    public void glUniform4f ( int location, float v0, float v1, float v2, float v3 ) {
    }

    @Override
    public void glUniform4fv ( int location, int count, FloatBuffer value ) {
    }

    @Override
    public void glUniform4fv ( int location, int count, float[] value, int valueOffset ) {
    }

    @Override
    public void glUniform4i ( int location, int v0, int v1, int v2, int v3 ) {
    }

    @Override
    public void glUniform4iv ( int location, int count, IntBuffer value ) {
    }

    @Override
    public void glUniform4iv ( int location, int count, int[] value, int valueOffset ) {
    }

    @Override
    public void glUniformMatrix2fv ( int location, int count, boolean transpose, FloatBuffer value ) {
    }

    @Override
    public void glUniformMatrix2fv ( int location, int count, boolean transpose, float[] value, int valueOffset ) {
    }

    @Override
    public void glUniformMatrix3fv ( int location, int count, boolean transpose, FloatBuffer value ) {
    }

    @Override
    public void glUniformMatrix3fv ( int location, int count, boolean transpose, float[] value, int valueOffset ) {
    }

    @Override
    public void glUniformMatrix4fv ( int location, int count, boolean transpose, FloatBuffer value ) {
    }

    @Override
    public void glUniformMatrix4fv ( int location, int count, boolean transpose, float[] value, int valueOffset ) {
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
    public void glDeleteShader ( int shaderHandle ) {
    }

    @Override
    public int glCreateProgram () {
        return 0;
    }

    @Override
    public void glDeleteProgram ( int shaderProgramHandle ) {
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
    public void glGetShaderiv ( int shaderHandle, int paramEnum, int[] params, int offset ) {
    }

    @Override
    public void glGetShaderiv ( int shaderHandle, int paramEnum, IntBuffer params ) {
    }

    @Override
    public void glGetProgramiv ( int shaderHandle, int paramEnum, int[] params, int offset ) {
    }

    @Override
    public void glGetProgramiv ( int shaderHandle, int paramEnum, IntBuffer params ) {
    }

    @Override
    public void glGetProgramInfoLog ( int shaderProgramHandle, int maxLength, IntBuffer lengthBuf, ByteBuffer infoLogBuf ) {
    }

    @Override
    public void glGetShaderInfoLog ( int vertexShaderHandle, int maxLength, IntBuffer lengthBuf, ByteBuffer infoLogBuf ) {
    }

    @Override
    public void glGetProgramInfoLog ( int program, int bufSize, int[] length, int lengthOffset, byte[] infoLog, int infoLogOffset ) {
    }

    @Override
    public void glGetShaderInfoLog ( int shader, int bufSize, int[] length, int lengthOffset, byte[] infoLog, int infoLogOffset ) {
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
    public void glDeleteBuffers ( int count, int[] bufferNames, int offset ) {
    }

    @Override
    public void glDeleteBuffers ( int count, IntBuffer bufferNames ) {
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
    public void glPolygonMode ( int faceEnum, int modeEnum ) {
    }

    @Override
    public void glCullFace ( int cullFaceEnum ) {
    }

    @Override
    public void glBufferData ( int bufferTarget, long size, Buffer data, int usageEnum ) {
    }

    @Override
    public void glBlendFunc ( int blendSourceEnum, int blendDestEnum ) {
    }

    @Override
    public void glBlendFuncSeparate ( int blendRgbSourceEnum, int blendRgbDestEnum, int blendAlphaSourceEnum, int blendAlphaDestEnum ) {
    }

    @Override
    public int glGetError () {
        return OpenGLUE.Constants.GL_NO_ERROR;
    }

    @Override
    public void glDeleteTextures ( int n, IntBuffer textures ) {
    }

    @Override
    public void glDeleteTextures ( int n, int[] textures, int offset ) {
    }

    @Override
    public void glGenFramebuffers ( int n, IntBuffer framebuffers ) {
    }

    @Override
    public void glGenFramebuffers ( int n, int[] framebuffers, int offset ) {
    }

    @Override
    public void glBindFramebuffer ( int target, int framebuffer ) {
    }

    @Override
    public void glDeleteFramebuffers ( int n, IntBuffer framebuffers ) {
    }

    @Override
    public void glDeleteFramebuffers ( int n, int[] framebuffers, int offset ) {
    }

    @Override
    public void glGenRenderbuffers ( int n, IntBuffer renderbuffers ) {
    }

    @Override
    public void glGenRenderbuffers ( int n, int[] renderbuffers, int offset ) {
    }

    @Override
    public void glBindRenderbuffer ( int target, int renderbuffer ) {
    }

    @Override
    public void glDeleteRenderbuffers ( int n, IntBuffer renderbuffers ) {
    }

    @Override
    public void glDeleteRenderbuffers ( int n, int[] renderbuffers, int offset ) {
    }

    @Override
    public void glDeleteVertexArrays ( int n, IntBuffer arrays ) {
    }

    @Override
    public void glDeleteVertexArrays ( int n, int[] arrays, int offset ) {
    }
}
