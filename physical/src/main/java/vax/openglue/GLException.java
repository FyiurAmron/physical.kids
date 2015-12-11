package vax.openglue;

import vax.openglue.constants.ErrorCode;

/**

 @author toor
 */
public class GLException extends RuntimeException {
    public GLException () {
    }

    public GLException ( String exceptionMessage ) {
        super( exceptionMessage );
    }

    public GLException ( int errorCodeEnum ) {
        this( ErrorCode.valueOf( errorCodeEnum ) );
    }

    public GLException ( ErrorCode errorCode ) {
        this( "glGetError() = " + errorCode.toString() );
    }
}
