package vax.openglue.constants;

import java.util.HashMap;
import static vax.openglue.OpenGL.Constants.*;

/**

 @author toor
 */
public enum ErrorCode implements OpenGlConstantWrapper {
    NoError( GL_NO_ERROR ),
    InvalidEnum( GL_INVALID_ENUM ),
    InvalidValue( GL_INVALID_VALUE ),
    InvalidOperation( GL_INVALID_OPERATION ),
    StackOverflow( GL_STACK_OVERFLOW ),
    StackUnderflow( GL_STACK_UNDERFLOW ),
    OutOfMemory( GL_OUT_OF_MEMORY ),
    InvalidFramebufferOperation( GL_INVALID_FRAMEBUFFER_OPERATION ),
    ContextLost( GL_CONTEXT_LOST ),
    TableTooLarge( GL_TABLE_TOO_LARGE ),
    TextureTooLargeExt( GL_TEXTURE_TOO_LARGE_EXT ),
    UnknownError( -1 ), //
    ;

    private final int value;
    private static final HashMap<Integer, ErrorCode> valueMap = new HashMap<>();

    static {
        for( ErrorCode ec : ErrorCode.values() ) {
            valueMap.put( ec.value, ec );
        }
    }

    public static ErrorCode forValue ( int value ) {
        ErrorCode ec = valueMap.get( value );
        return ( ec == null ) ? UnknownError : ec;
    }

    private ErrorCode ( int value ) {
        this.value = value;
    }

    @Override
    public int getValue () {
        return value;
    }

    public boolean isError() {
        return this != NoError;
    }
}
