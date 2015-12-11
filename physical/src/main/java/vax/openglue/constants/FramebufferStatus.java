package vax.openglue.constants;

import java.util.HashMap;
import static vax.openglue.OpenGL.Constants.*;

/**

 @author toor
 */
public enum FramebufferStatus implements OpenGlConstantWrapper {
    /**
     framebuffer is complete and fully operational; no error
     */
    Complete( GL_FRAMEBUFFER_COMPLETE ),
    /**
     the specified framebuffer is the default read or draw framebuffer, but the default framebuffer does not exist
     */
    Undefined( GL_FRAMEBUFFER_UNDEFINED ),
    /**
     any of the framebuffer attachment points are framebuffer incomplete
     */
    IncompleteAttachment( GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT ),
    /**
     the framebuffer does not have at least one image attached to it
     */
    IncompleteMissingAttachment( GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT ),
    /**
     the value of GL_FRAMEBUFFER_ATTACHMENT_OBJECT_TYPE is GL_NONE for any color attachment point(s) named by GL_DRAW_BUFFERi
     */
    IncompleteDrawBuffer( GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER ),
    /**
     GL_READ_BUFFER is not GL_NONE and the value of GL_FRAMEBUFFER_ATTACHMENT_OBJECT_TYPE is GL_NONE for the color attachment point named by
     GL_READ_BUFFER.
     */
    IncompleteReadBuffer( GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER ),
    /**
     the combination of internal formats of the attached images violates an implementation-dependent set of restrictions
     */
    Unsupported( GL_FRAMEBUFFER_UNSUPPORTED ),
    /**
     <ul>
     <li>the value of GL_RENDERBUFFER_SAMPLES is not the same for all attached renderbuffers;</li>
     <li>the value of GL_TEXTURE_SAMPLES is the not same for all attached textures;</li>
     <li>the attached images are a mix of renderbuffers and textures, the value of GL_RENDERBUFFER_SAMPLES does not match
     the value of GL_TEXTURE_SAMPLES.</li>
     <li>the value of GL_TEXTURE_FIXED_SAMPLE_LOCATIONS is not the same for all attached textures;</li>
     <li>the attached images are a mix of renderbuffers and textures, the value of GL_TEXTURE_FIXED_SAMPLE_LOCATIONS is not GL_TRUE
     for all attached textures.</li>
     </ul>
     */
    IncompleteMultisample( GL_FRAMEBUFFER_INCOMPLETE_MULTISAMPLE ),
    /*
     any framebuffer attachment is layered, and any populated attachment is not layered, or if all populated color attachments
     are not from textures of the same target
     */
    //IncompleteLayerTargets( GL_FRAMEBUFFER_INCOMPLETE_LAYER_TARGETS ), // not implemented in this OpenGL version
    UnknownStatus( -1 ), //
    ;

    private final int glConstant;

    private static final HashMap<Integer, FramebufferStatus> constantToEnumMap = new HashMap<>();

    static {
        for( FramebufferStatus ec : FramebufferStatus.values() ) {
            constantToEnumMap.put( ec.glConstant, ec );
        }
    }

    public static FramebufferStatus valueOf ( int glConstant ) {
        FramebufferStatus ec = constantToEnumMap.get( glConstant );
        return ( ec == null ) ? UnknownStatus : ec;
    }

    private FramebufferStatus ( int glConstant ) {
        this.glConstant = glConstant;
    }

    @Override
    public int getGlConstant () {
        return glConstant;
    }

    public boolean isError () {
        return this != Complete;
    }
}
