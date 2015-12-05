package vax.openglue;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
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

    default void ueSetParam ( int glParam, boolean state ) {
        if ( state ) {
            glEnable( glParam );
        } else {
            glDisable( glParam );
        }
    }

    default ErrorCode ueGetError () {
        return ErrorCode.forValue( glGetError() );
    }

    default String ueGetLogBufferContent () {
        return BufferUtils.toString( ueGetGLUtils().getLogBuffer() );
    }

    static void ueCheckError ( int errorEnum ) {
        if ( errorEnum != Constants.GL_NO_ERROR ) {
            throw new GLException( ErrorCode.forValue( errorEnum ) );
        }
    }

    default void ueCheckError () {
        ueCheckError( glGetError() );
    }

    default String ueGetProgramInfoLog ( int shaderProgramHandle ) {
        GLUtils glu = ueGetGLUtils();
        ByteBuffer bb = glu.getLogBuffer();
        IntBuffer lbsb = glu.getLogBufferSizeBuffer();
        glGetProgramInfoLog( shaderProgramHandle, bb.capacity(), lbsb, bb );
        lbsb.rewind();
        bb.limit( lbsb.get() );
        return ueGetLogBufferContent();
    }

    default String ueGetShaderInfoLog ( int shaderHandle ) {
        GLUtils glu = ueGetGLUtils();
        ByteBuffer bb = glu.getLogBuffer();
        IntBuffer lbsb = glu.getLogBufferSizeBuffer();
        glGetShaderInfoLog( shaderHandle, bb.capacity(), lbsb, bb );
        lbsb.rewind();
        bb.limit( lbsb.get() );
        return ueGetLogBufferContent();
    }

    // legacy/signature-compatible method extenders
    default void glClear ( ClearBufferMask cbm, ClearBufferMask... cbms ) {
        glClear( OpenGlConstantWrapper.getValue( cbm, cbms ) );
    }

    default int glCreateShader ( ShaderType shaderType ) {
        return glCreateShader( shaderType.getValue() );
    }

    default void glBindBuffer ( BufferTarget bufferTarget, int handle ) {
        glBindBuffer( bufferTarget.getValue(), handle );
    }

    default void glUniform2fv ( int uniformLocation, float[] data ) {
        glUniform2fv( uniformLocation, 1, data, 0 );
    }

    default void glUniform3fv ( int uniformLocation, float[] data ) {
        glUniform3fv( uniformLocation, 1, data, 0 );
    }

    default void glUniform4fv ( int uniformLocation, float[] data ) {
        glUniform4fv( uniformLocation, 1, data, 0 );
    }

    default void glUniformMatrix4fv ( int uniformLocation, float[] data ) {
        glUniformMatrix4fv( uniformLocation, 1, false, data, 0 );
    }

    default void glGetShaderiv ( int shaderHandle, int paramEnum, int[] params ) {
        glGetShaderiv( shaderHandle, paramEnum, params, 0 );
    }

    default int glGetShaderi ( int shaderHandle, int paramEnum ) {
        int[] iArr = ueGetGLUtils().getSingleIntArray();
        glGetShaderiv( shaderHandle, paramEnum, iArr, 0 );
        return iArr[0];
    }

    default void glGetProgramiv ( int shaderProgramHandle, int paramEnum, int[] params ) {
        glGetProgramiv( shaderProgramHandle, paramEnum, params, 0 );
    }

    default int glGetProgrami ( int shaderProgramHandle, int paramEnum ) {
        int[] iArr = ueGetGLUtils().getSingleIntArray();
        glGetProgramiv( shaderProgramHandle, paramEnum, iArr, 0 );
        return iArr[0];
    }

    default void glGetShaderInfoLog ( int vertexShaderHandle, int maxLength,
            int[] length, byte[] infoLog ) {
        glGetShaderInfoLog( vertexShaderHandle, maxLength, length, 0, infoLog, 0 );
    }

    default void glGetProgramInfoLog ( int shaderProgramHandle, int maxLength,
            int[] length, byte[] infoLog ) {
        glGetProgramInfoLog( shaderProgramHandle, maxLength, length, 0, infoLog, 0 );
    }

    default void glShaderSource ( int shaderHandle, String source ) {
        glShaderSource( shaderHandle, 1, ueGetGLUtils().toArray( source ), null );
    }

    default void glGenVertexArrays ( int[] outBuffer ) {
        glGenVertexArrays( outBuffer.length, outBuffer, 0 );
    }

    default void glGenVertexArrays ( int count, int[] outBuffer ) {
        glGenVertexArrays( count, outBuffer, 0 );
    }

    default int glGenVertexArray () {
        int[] intArr = ueGetGLUtils().getSingleIntArray();
        glGenVertexArrays( 1, intArr, 0 );
        return intArr[0];
    }

    default void glDeleteVertexArrays ( int[] vertexArrayNames ) {
        glDeleteVertexArrays( vertexArrayNames.length, vertexArrayNames, 0 );
    }

    default void glDeleteVertexArrays ( int count, int[] vertexArrayNames ) {
        glDeleteVertexArrays( count, vertexArrayNames, 0 );
    }

    default void glDeleteVertexArray ( int vertexArrayHandle ) {
        glDeleteVertexArrays( 1, ueGetGLUtils().toArray( vertexArrayHandle ), 0 );
    }

    default void glGenTextures ( int[] outBuffer ) {
        glGenTextures( outBuffer.length, outBuffer, 0 );
    }

    default void glGenTextures ( int count, int[] outBuffer ) {
        glGenTextures( count, outBuffer, 0 );
    }

    default int glGenTexture () {
        int[] intArr = ueGetGLUtils().getSingleIntArray();
        glGenTextures( 1, intArr, 0 );
        return intArr[0];
    }

    default void glDeleteTextures ( int[] textureNames ) {
        glDeleteTextures( textureNames.length, textureNames, 0 );
    }

    default void glDeleteTextures ( int count, int[] textureNames ) {
        glDeleteTextures( count, textureNames, 0 );
    }

    default void glDeleteTexture ( int textureHandle ) {
        glDeleteTextures( 1, ueGetGLUtils().toArray( textureHandle ), 0 );
    }

    default void glGenBuffers ( int[] outBuffer ) {
        glGenBuffers( outBuffer.length, outBuffer, 0 );
    }

    default void glGenBuffers ( int count, int[] outBuffer ) {
        glGenBuffers( count, outBuffer, 0 );
    }

    default int glGenBuffer () {
        int[] intArr = ueGetGLUtils().getSingleIntArray();
        glGenBuffers( 1, intArr, 0 );
        return intArr[0];
    }

    default void glDeleteBuffers ( int[] bufferNames ) {
        glDeleteBuffers( bufferNames.length, bufferNames, 0 );
    }

    default void glDeleteBuffers ( int count, int[] bufferNames ) {
        glDeleteBuffers( count, bufferNames, 0 );
    }

    default void glDeleteBuffer ( int bufferHandle ) {
        glDeleteBuffers( 1, ueGetGLUtils().toArray( bufferHandle ), 0 );
    }

    default void glGenFramebuffers ( int[] outBuffer ) {
        glGenFramebuffers( outBuffer.length, outBuffer, 0 );
    }

    default void glGenFramebuffers ( int count, int[] outBuffer ) {
        glGenFramebuffers( count, outBuffer, 0 );
    }

    default int glGenFramebuffer () {
        int[] intArr = ueGetGLUtils().getSingleIntArray();
        glGenFramebuffers( 1, intArr, 0 );
        return intArr[0];
    }

    default void glDeleteFramebuffers ( int[] bufferNames ) {
        glDeleteFramebuffers( bufferNames.length, bufferNames, 0 );
    }

    default void glDeleteFramebuffers ( int count, int[] bufferNames ) {
        glDeleteFramebuffers( count, bufferNames, 0 );
    }

    default void glDeleteFramebuffer ( int bufferHandle ) {
        glDeleteFramebuffers( 1, ueGetGLUtils().toArray( bufferHandle ), 0 );
    }

    default void glGenRenderbuffers ( int[] outBuffer ) {
        glGenRenderbuffers( outBuffer.length, outBuffer, 0 );
    }

    default void glGenRenderbuffers ( int count, int[] outBuffer ) {
        glGenRenderbuffers( count, outBuffer, 0 );
    }

    default int glGenRenderbuffer () {
        int[] intArr = ueGetGLUtils().getSingleIntArray();
        glGenRenderbuffers( 1, intArr, 0 );
        return intArr[0];
    }

    default void glDeleteRenderbuffers ( int[] bufferNames ) {
        glDeleteRenderbuffers( bufferNames.length, bufferNames, 0 );
    }

    default void glDeleteRenderbuffers ( int count, int[] bufferNames ) {
        glDeleteRenderbuffers( count, bufferNames, 0 );
    }

    default void glDeleteRenderbuffer ( int bufferHandle ) {
        glDeleteRenderbuffers( 1, ueGetGLUtils().toArray( bufferHandle ), 0 );
    }

    default void glClearColor ( Vector4f backgroundColor ) {
        glClearColor( backgroundColor.getX(), backgroundColor.getY(), backgroundColor.getZ(), backgroundColor.getW() );
    }
}
