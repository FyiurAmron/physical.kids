package vax.openglue;

import vax.openglue.constants.*;

/**

 @author toor
 */
public interface OpenGLUE extends OpenGL {
    /*
     void glUniform1 ( int uniformLocation, float value );

     void glUniformMatrix4 ( int uniformLocation, int i, boolean b, float[] data );

     void glUniform2 ( int uniformLocation, int i, float[] data );

     void glUniform3 ( int uniformLocation, int i, float[] data );

     void glUniform4 ( int uniformLocation, int i, float[] data );

     void glEnable ( int glParam ); // TODO

     public void glBindTexture ( int GL_TEXTURE_2D, int handle );

     public void glVertexAttribPointer ( int attribNr, int size, int GL_FLOAT, int GL_TRUE, int i, int i0 );
     */

    // new/signature-incompatible methods

    default public void ueSetParam( int glParam, boolean state ) {
        if ( state ) {
            glEnable( glParam );
        } else {
            glDisable( glParam );
        }
    }

    default public ErrorCode ueGetError() {
        return ErrorCode.forValue( glGetError() );
    }

    // legacy/signature-compatible method extenders

    default public void glClear ( ClearBufferMask cbm, ClearBufferMask... cbms ) {
        glClear( OpenGlConstantWrapper.getValue( cbm, cbms ) );
    }

    default public int glCreateShader ( ShaderType shaderType ) {
        return glCreateShader( shaderType.getValue() );
    }

    default public void glBindBuffer ( BufferTarget bufferTarget, int handle ) {
        glBindBuffer( bufferTarget.getValue(), handle );
    }
}
