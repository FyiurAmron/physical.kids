package vax.openglue.lwjgl;

import vax.util.NativeLibraryLoader;
import java.nio.*;
import org.lwjgl.opengl.*;
import static org.lwjgl.system.MemoryUtil.memAddress;
import vax.openglue.AbstractGLUE;
import vax.openglue.BufferGLUE;
import vax.openglue.ImageIO;

/**

 @author toor
 */
public class LwjglUe3 extends AbstractGLUE {
    public LwjglUe3 () {
        NativeLibraryLoader.load();
    }

    @Override
    public Class<? extends BufferGLUE> getClassBufferGLUE () {
        return LwjglBufferGLUE.class;
    }

    @Override
    public Class<? extends ImageIO.GLUE> getClassImageIO_GLUE () {
        return LwjglImageIoGLUE.class;
    }

    @Override
    public void glGenTextures ( int count, IntBuffer outBuffer ) {
        GL11.nglGenTextures( count, memAddress( outBuffer ) );
    }

    @Override
    public void glBindTexture ( int targetEnum, int handle ) {
        GL11.glBindTexture( targetEnum, handle );
    }

    @Override
    public void glDeleteTextures ( int n, IntBuffer textures ) {
        GL11.nglDeleteTextures( n, memAddress( textures ) );
    }

    @Override
    public void glTexImage2D ( int targetEnum, int level, int internalFormat,
            int width, int height, int border, int format, int type, ByteBuffer data ) {
        GL11.glTexImage2D( targetEnum, level, internalFormat, width, height, border, format, type, data );
    }

    @Override
    public void glTexImage2D ( int targetEnum, int level, int internalFormat,
            int width, int height, int border, int format, int type, ShortBuffer data ) {
        GL11.glTexImage2D( targetEnum, level, internalFormat, width, height, border, format, type, data );
    }

    @Override
    public void glTexImage2D ( int targetEnum, int level, int internalFormat,
            int width, int height, int border, int format, int type, IntBuffer data ) {
        GL11.glTexImage2D( targetEnum, level, internalFormat, width, height, border, format, type, data );
    }

    @Override
    public void glTexImage2D ( int targetEnum, int level, int internalFormat,
            int width, int height, int border, int format, int type, FloatBuffer data ) {
        GL11.glTexImage2D( targetEnum, level, internalFormat, width, height, border, format, type, data );
    }

    @Override
    public void glTexImage2D ( int targetEnum, int level, int internalFormat,
            int width, int height, int border, int format, int type, DoubleBuffer data ) {
        GL11.glTexImage2D( targetEnum, level, internalFormat, width, height, border, format, type, data );
    }

    @Override
    public void glTexParameteri ( int targetEnum, int paramEnum, int param ) {
        GL11.glTexParameteri( targetEnum, paramEnum, param );
    }

    @Override
    public void glTexParameterf ( int targetEnum, int paramEnum, float param ) {
        GL11.glTexParameterf( targetEnum, paramEnum, param );
    }

    @Override
    public void glEnable ( int glParam ) {
        GL11.glEnable( glParam );
    }

    @Override
    public void glDisable ( int glParam ) {
        GL11.glDisable( glParam );
    }

    @Override
    public void glClear ( int i ) {
        GL11.glClear( i );
    }

    /*

     @Override
     public void glDrawElements ( int primitiveType, int count, int valueType, IntBuffer buf ) {
     GL11.glDrawElements( primitiveType, count, valueType, i );
     }

     @Override
     public void glDrawElements ( int primitiveType, int count, int valueType, int[] data, int offset ) {
     GL11.glDrawElements( primitiveType, count, valueType, i );
     }
     */
    @Override
    public void glDrawElements ( int primitiveType, int count, int valueType, long offset ) {
        GL11.glDrawElements( primitiveType, count, valueType, offset );
    }

    @Override
    public void glPolygonMode ( int faceEnum, int modeEnum ) {
        GL11.glPolygonMode( faceEnum, modeEnum );
    }

    @Override
    public void glCullFace ( int cullFaceEnum ) {
        GL11.glCullFace( cullFaceEnum );
    }

    @Override
    public void glClearColor ( float r, float g, float b, float a ) {
        GL11.glClearColor( r, g, b, a );
    }

    @Override
    public void glViewport ( int x, int y, int width, int height ) {
        GL11.glViewport( x, y, width, height );
    }

    @Override
    public void glBlendFunc ( int blendSourceEnum, int blendDestEnum ) {
        GL11.glBlendFunc( blendSourceEnum, blendDestEnum );
    }

    @Override
    public void glBlendFuncSeparate ( int blendRgbSourceEnum, int blendRgbDestEnum, int blendAlphaSourceEnum, int blendAlphaDestEnum ) {
        GL14.glBlendFuncSeparate( blendRgbSourceEnum, blendRgbDestEnum, blendAlphaSourceEnum, blendAlphaDestEnum );
    }

    @Override
    public void glBufferData ( int bufferTarget, long size, DoubleBuffer data, int usageEnum ) {
        GL15.glBufferData( bufferTarget, data, usageEnum );
    }

    @Override
    public void glGenBuffers ( int count, IntBuffer outBuffer ) {
        GL15.nglGenBuffers( count, memAddress( outBuffer ) );
    }

    @Override
    public void glBindBuffer ( int targetEnum, int handle ) {
        GL15.glBindBuffer( targetEnum, handle );
    }

    @Override
    public void glDeleteBuffers ( int count, IntBuffer bufferNames ) {
        GL15.nglDeleteBuffers( count, memAddress( bufferNames ) );
    }

    @Override
    public void glBufferData ( int bufferTarget, long size, ByteBuffer data, int usageEnum ) {
        GL15.glBufferData( bufferTarget, size, data, usageEnum );
    }

    @Override
    public void glBufferData ( int bufferTarget, ByteBuffer data, int usageEnum ) {
        GL15.glBufferData( bufferTarget, data, usageEnum );
    }

    @Override
    public void glBufferData ( int bufferTarget, long size, ShortBuffer data, int usageEnum ) {
        GL15.glBufferData( bufferTarget, data, usageEnum );
    }

    @Override
    public void glBufferData ( int bufferTarget, long size, IntBuffer data, int usageEnum ) {
        GL15.glBufferData( bufferTarget, data, usageEnum );
    }

    @Override
    public void glBufferData ( int bufferTarget, long size, FloatBuffer data, int usageEnum ) {
        GL15.glBufferData( bufferTarget, data, usageEnum );
    }

    @Override
    public int glGetUniformLocation ( int shaderProgramHandle, String name ) {
        return GL20.glGetUniformLocation( shaderProgramHandle, name );
    }

    @Override
    public void glUniform1f ( int location, float v0 ) {
        GL20.glUniform1f( location, v0 );
    }

    @Override
    public void glUniform1fv ( int location, int count, FloatBuffer value ) {
        GL20.nglUniform1fv( location, count, memAddress( value ) );
    }

    @Override
    public void glUniform1i ( int location, int v0 ) {
        GL20.glUniform1i( location, v0 );
    }

    @Override
    public void glUniform1iv ( int location, int count, IntBuffer value ) {
        GL20.nglUniform1iv( location, count, memAddress( value ) );
    }

    @Override
    public void glUniform2f ( int location, float v0, float v1 ) {
        GL20.glUniform2f( location, v0, v1 );
    }

    @Override
    public void glUniform2fv ( int location, int count, FloatBuffer value ) {
        GL20.nglUniform2fv( location, count, memAddress( value ) );
    }

    @Override
    public void glUniform2i ( int location, int v0, int v1 ) {
        GL20.glUniform2i( location, v0, v1 );
    }

    @Override
    public void glUniform2iv ( int location, int count, IntBuffer value ) {
        GL20.nglUniform2iv( location, count, memAddress( value ) );
    }

    @Override
    public void glUniform3f ( int location, float v0, float v1, float v2 ) {
        GL20.glUniform3f( location, v0, v1, v2 );
    }

    @Override
    public void glUniform3fv ( int location, int count, FloatBuffer value ) {
        GL20.nglUniform3fv( location, count, memAddress( value ) );
    }

    @Override
    public void glUniform3i ( int location, int v0, int v1, int v2 ) {
        GL20.glUniform3i( location, v0, v1, v2 );
    }

    @Override
    public void glUniform3iv ( int location, int count, IntBuffer value ) {
        GL20.nglUniform3iv( location, count, memAddress( value ) );
    }

    @Override
    public void glUniform4f ( int location, float v0, float v1, float v2, float v3 ) {
        GL20.glUniform4f( location, v0, v1, v2, v3 );
    }

    @Override
    public void glUniform4fv ( int location, int count, FloatBuffer value ) {
        GL20.nglUniform4fv( location, count, memAddress( value ) );
    }

    @Override
    public void glUniform4i ( int location, int v0, int v1, int v2, int v3 ) {
        GL20.glUniform4i( location, v0, v1, v2, v3 );
    }

    @Override
    public void glUniform4iv ( int location, int count, IntBuffer value ) {
        GL20.nglUniform4iv( location, count, memAddress( value ) );
    }

    @Override
    public void glUniformMatrix2fv ( int location, int count, boolean transpose, FloatBuffer value ) {
        GL20.nglUniformMatrix2fv( location, count, transpose, memAddress( value ) );
    }

    @Override
    public void glUniformMatrix3fv ( int location, int count, boolean transpose, FloatBuffer value ) {
        GL20.nglUniformMatrix3fv( location, count, transpose, memAddress( value ) );
    }

    @Override
    public void glUniformMatrix4fv ( int location, int count, boolean transpose, FloatBuffer value ) {
        GL20.nglUniformMatrix4fv( location, count, transpose, memAddress( value ) );
    }

    @Override
    public void glCompileShader ( int vertexShaderHandle ) {
        GL20.glCompileShader( vertexShaderHandle );
    }

    @Override
    public int glCreateShader ( int shaderType ) {
        return GL20.glCreateShader( shaderType );
    }

    @Override
    public void glDeleteShader ( int shaderHandle ) {
        GL20.glDeleteShader( shaderHandle );
    }

    @Override
    public int glCreateProgram () {
        return GL20.glCreateProgram();
    }

    @Override
    public void glDeleteProgram ( int shaderProgramHandle ) {
        GL20.glDeleteProgram( shaderProgramHandle );
    }

    @Override
    public void glAttachShader ( int shaderProgramHandle, int vertexShaderHandle ) {
        GL20.glAttachShader( shaderProgramHandle, vertexShaderHandle );
    }

    @Override
    public void glBindAttribLocation ( int shaderProgramHandle, int index, String name ) {
        GL20.glBindAttribLocation( shaderProgramHandle, index, name );
    }

    @Override
    public void glLinkProgram ( int shaderProgramHandle ) {
        GL20.glLinkProgram( shaderProgramHandle );
    }

    @Override
    public void glGetShaderiv ( int shaderHandle, int paramEnum, IntBuffer params ) {
        GL20.glGetShaderiv( shaderHandle, paramEnum, params );
    }

    @Override
    public void glGetProgramiv ( int shaderProgramHandle, int paramEnum, IntBuffer params ) {
        GL20.glGetProgramiv( shaderProgramHandle, paramEnum, params );
    }

    @Override
    public void glGetShaderInfoLog ( int vertexShaderHandle, int maxLength, IntBuffer lengthBuf, ByteBuffer infoLogBuf ) {
        GL20.glGetShaderInfoLog( vertexShaderHandle, lengthBuf, infoLogBuf );
        throw new UnsupportedOperationException();
    }

    @Override
    public void glGetProgramInfoLog ( int shaderProgramHandle, int maxLength, IntBuffer lengthBuf, ByteBuffer infoLogBuf ) {
        GL20.glGetProgramInfoLog( shaderProgramHandle, lengthBuf, infoLogBuf );
        throw new UnsupportedOperationException();
    }

    @Override
    public void glUseProgram ( int shaderProgramHandle ) {
        GL20.glUseProgram( shaderProgramHandle );
    }

    @Override
    public void glVertexAttribPointer ( int attribNr, int size, int typeEnum, boolean normalize, int stride, long pointer ) {
        GL20.glVertexAttribPointer( attribNr, size, typeEnum, normalize, stride, pointer );
    }

    @Override
    public void glShaderSource ( int shaderHandle, int count, String[] sources, IntBuffer lengths ) {
        for( String s : sources ) {
            GL20.glShaderSource( shaderHandle, s );
        }
    }

    @Override
    public void glEnableVertexAttribArray ( int attribNr ) {
        GL20.glEnableVertexAttribArray( attribNr );
    }

    @Override
    public void glGenVertexArrays ( int count, IntBuffer outBuffer ) {
        GL30.nglGenVertexArrays( count, memAddress( outBuffer ) );
    }

    @Override
    public void glBindVertexArray ( int vaoHandle ) {
        GL30.glBindVertexArray( vaoHandle );
    }

    @Override
    public void glGenFramebuffers ( int n, IntBuffer framebuffers ) {
        GL30.nglGenFramebuffers( n, memAddress( framebuffers ) );
    }

    @Override
    public void glBindFramebuffer ( int target, int framebuffer ) {
        GL30.glBindFramebuffer( target, framebuffer );
    }

    @Override
    public void glDeleteFramebuffers ( int n, IntBuffer framebuffers ) {
        GL30.nglDeleteFramebuffers( n, memAddress( framebuffers ) );
    }

    @Override
    public void glGenRenderbuffers ( int n, IntBuffer renderbuffers ) {
        GL30.nglGenRenderbuffers( n, memAddress( renderbuffers ) );
    }

    @Override
    public void glBindRenderbuffer ( int target, int renderbuffer ) {
        GL30.glBindRenderbuffer( target, renderbuffer );
    }

    @Override
    public void glDeleteRenderbuffers ( int n, IntBuffer renderbuffers ) {
        GL30.nglDeleteRenderbuffers( n, memAddress( renderbuffers ) );
    }

    @Override
    public void glDeleteVertexArrays ( int n, IntBuffer arrays ) {
        GL30.nglDeleteVertexArrays( n, memAddress( arrays ) );
    }

    @Override
    public void glGenerateMipmap ( int targetEnum ) {
        GL30.glGenerateMipmap( targetEnum );
    }

    @Override
    public int glGetError () {
        return GL11.glGetError();
    }
}
