package vax.openglue.headless;

import java.nio.*;
import vax.openglue.*;

/**

 @author toor
 */
public class HeadlessGLUE extends AbstractGLUE {
    @Override
    public Class<? extends ImageGLUE> getClassImageGLUE () {
        return ImageGLUE.DefaultLocal.class;
    }

    @Override
    public int glGetError () {
        return OpenGLUE.Constants.GL_NO_ERROR;
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
    public void glUniform1i ( int location, int v0 ) {
    }

    @Override
    public void glUniform1iv ( int location, int count, IntBuffer value ) {
    }

    @Override
    public void glUniform2f ( int location, float v0, float v1 ) {
    }

    @Override
    public void glUniform2fv ( int location, int count, FloatBuffer data ) {
    }

    @Override
    public void glUniform2i ( int location, int v0, int v1 ) {
    }

    @Override
    public void glUniform2iv ( int location, int count, IntBuffer value ) {
    }

    @Override
    public void glUniform3f ( int location, float v0, float v1, float v2 ) {
    }

    @Override
    public void glUniform3fv ( int location, int count, FloatBuffer value ) {
    }

    @Override
    public void glUniform3i ( int location, int v0, int v1, int v2 ) {
    }

    @Override
    public void glUniform3iv ( int location, int count, IntBuffer value ) {
    }

    @Override
    public void glUniform4f ( int location, float v0, float v1, float v2, float v3 ) {
    }

    @Override
    public void glUniform4fv ( int location, int count, FloatBuffer value ) {
    }

    @Override
    public void glUniform4i ( int location, int v0, int v1, int v2, int v3 ) {
    }

    @Override
    public void glUniform4iv ( int location, int count, IntBuffer value ) {
    }

    @Override
    public void glUniformMatrix2fv ( int location, int count, boolean transpose, FloatBuffer value ) {
    }

    @Override
    public void glUniformMatrix3fv ( int location, int count, boolean transpose, FloatBuffer value ) {
    }

    @Override
    public void glUniformMatrix4fv ( int location, int count, boolean transpose, FloatBuffer value ) {
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
    public void glGetShaderiv ( int shaderHandle, int paramEnum, IntBuffer params ) {
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
    public void glTexImage2D ( int targetEnum, int level, int internalFormat, int width, int height, int border, int format, int type,
            ByteBuffer data ) {
    }

    @Override
    public void glTexImage2D ( int targetEnum, int level, int internalFormat, int width, int height, int border, int format, int type,
            ShortBuffer data ) {
    }

    @Override
    public void glTexImage2D ( int targetEnum, int level, int internalFormat, int width, int height, int border, int format, int type,
            IntBuffer data ) {
    }

    @Override
    public void glTexImage2D ( int targetEnum, int level, int internalFormat, int width, int height, int border, int format, int type,
            FloatBuffer data ) {
    }

    @Override
    public void glTexImage2D ( int targetEnum, int level, int internalFormat, int width, int height, int border, int format, int type,
            DoubleBuffer data ) {
    }

    @Override
    public void glGenerateMipmap ( int targetEnum ) {
    }

    @Override
    public void glGenBuffers ( int count, IntBuffer outBuffer ) {
    }

    @Override
    public void glBindBuffer ( int targetEnum, int handle ) {
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
    public void glBufferData ( int bufferTarget, long size, ByteBuffer data, int usageEnum ) {
    }

    @Override
    public void glBufferData ( int bufferTarget, ByteBuffer data, int usageEnum ) {
    }

    @Override
    public void glBufferData ( int bufferTarget, long size, ShortBuffer data, int usageEnum ) {
    }

    @Override
    public void glBufferData ( int bufferTarget, long size, IntBuffer data, int usageEnum ) {
    }

    @Override
    public void glBufferData ( int bufferTarget, long size, FloatBuffer data, int usageEnum ) {
    }

    @Override
    public void glBufferData ( int bufferTarget, long size, DoubleBuffer data, int usageEnum ) {
    }

    @Override
    public void glBlendFunc ( int blendSourceEnum, int blendDestEnum ) {
    }

    @Override
    public void glBlendFuncSeparate ( int blendRgbSourceEnum, int blendRgbDestEnum, int blendAlphaSourceEnum, int blendAlphaDestEnum ) {
    }

    @Override
    public void glDeleteTextures ( int n, IntBuffer textures ) {
    }

    @Override
    public void glGenFramebuffers ( int n, IntBuffer framebuffers ) {
    }

    @Override
    public void glBindFramebuffer ( int target, int framebuffer ) {
    }

    @Override
    public void glDeleteFramebuffers ( int n, IntBuffer framebuffers ) {
    }

    @Override
    public void glGenRenderbuffers ( int n, IntBuffer renderbuffers ) {
    }

    @Override
    public void glBindRenderbuffer ( int target, int renderbuffer ) {
    }

    @Override
    public void glDeleteRenderbuffers ( int n, IntBuffer renderbuffers ) {
    }

    @Override
    public void glDeleteVertexArrays ( int n, IntBuffer arrays ) {
    }

    @Override
    public void glRenderbufferStorage ( int targetEnum, int internalformat, int width, int height ) {
    }

    @Override
    public void glRenderbufferStorageMultisample ( int targetEnum, int samples, int internalformat, int width, int height ) {
    }

    @Override
    public int glCheckFramebufferStatus ( int targetEnum ) {
        return OpenGL.Constants.GL_FRAMEBUFFER_COMPLETE;
    }

    @Override
    public void glFramebufferTexture1D ( int targetEnum, int attachmentEnum, int textarget, int texture, int level ) {
    }

    @Override
    public void glFramebufferTexture2D ( int targetEnum, int attachmentEnum, int textarget, int texture, int level ) {
    }

    @Override
    public void glFramebufferTexture3D ( int targetEnum, int attachmentEnum, int textarget, int texture, int level, int layer ) {
    }

    @Override
    public void glFramebufferTextureLayer ( int targetEnum, int attachmentEnum, int texture, int level, int layer ) {
    }

    @Override
    public void glFramebufferRenderbuffer ( int targetEnum, int attachmentEnum, int renderbufferTargetEnum, int renderbufferHandle ) {
    }

    @Override
    public void glDrawBuffers ( int count, IntBuffer bufs ) {
    }

    @Override
    public void glReadBuffer ( int srcEnum ) {
    }

    @Override
    public void glReadPixels ( int x, int y, int width, int height, int format, int type, ByteBuffer pixels ) {
    }

    @Override
    public void glReadPixels ( int x, int y, int width, int height, int format, int type, ShortBuffer pixels ) {
    }

    @Override
    public void glReadPixels ( int x, int y, int width, int height, int format, int type, IntBuffer pixels ) {
    }

    @Override
    public void glReadPixels ( int x, int y, int width, int height, int format, int type, FloatBuffer pixels ) {
    }

    @Override
    public void glGetTexImage ( int tex, int level, int format, int type, ByteBuffer pixels ) {
    }

    @Override
    public void glGetTexImage ( int tex, int level, int format, int type, ShortBuffer pixels ) {
    }

    @Override
    public void glGetTexImage ( int tex, int level, int format, int type, IntBuffer pixels ) {
    }

    @Override
    public void glGetTexImage ( int tex, int level, int format, int type, FloatBuffer pixels ) {
    }

    @Override
    public void glGetTexImage ( int tex, int level, int format, int type, DoubleBuffer pixels ) {
    }
}
