package vax.openglue;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
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

    default boolean ueGetProgramInfoLog ( int shaderProgramHandle, int maxLength, IntBuffer lengthBuf, ByteBuffer infoLogBuf ) {
        ByteBuffer bb = ueGetGLUtils().getLogBuffer();
        glGetProgramInfoLog( shaderProgramHandle, bb.capacity(), null, bb );
        return ( glGetError() != ErrorCode.NoError.getValue() );
    }

    default boolean ueGetShaderInfoLog ( int vertexShaderHandle, int maxLength, IntBuffer lengthBuf, ByteBuffer infoLogBuf ) {
        ByteBuffer bb = ueGetGLUtils().getLogBuffer();
        glGetShaderInfoLog( vertexShaderHandle, bb.capacity(), null, bb );
        return ( glGetError() != ErrorCode.NoError.getValue() );
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

    default void glShaderSource ( int shaderHandle, String source ) {
        glShaderSource(shaderHandle, 1, ueGetGLUtils().toArray( source ), null );
    }

    default void glGenVertexArrays ( int count, int[] outBuffer ) {
        glGenVertexArrays( count, outBuffer, 0 );
    }

    default int glGenVertexArray () {
        int[] intArr = ueGetGLUtils().getSingleIntArray();
        glGenVertexArrays( 1, intArr, 0 );
        return intArr[0];
    }

    default void glGenTextures ( int count, int[] outBuffer ) {
        glGenTextures( count, outBuffer, 0 );
    }

    default int glGenTexture () {
        int[] intArr = ueGetGLUtils().getSingleIntArray();
        glGenTextures( 1, intArr, 0 );
        return intArr[0];
    }

    default void glGenBuffers ( int count, int[] outBuffer ) {
        glGenBuffers( count, outBuffer, 0 );
    }

    default int glGenBuffer () {
        int[] intArr = ueGetGLUtils().getSingleIntArray();
        glGenBuffers( 1, intArr, 0 );
        return intArr[0];
    }
}
