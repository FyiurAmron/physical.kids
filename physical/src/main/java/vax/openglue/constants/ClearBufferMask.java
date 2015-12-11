package vax.openglue.constants;

import static vax.openglue.OpenGL.Constants.*;

/**

 @author toor
 */
public enum ClearBufferMask implements OpenGlConstantWrapper {
    DepthBufferBit( GL_DEPTH_BUFFER_BIT ),
    //AccumBufferBit( GL_ACCUM_BUFFER_BIT ),
    StencilBufferBit( GL_STENCIL_BUFFER_BIT ),
    ColorBufferBit( GL_COLOR_BUFFER_BIT ),
    CoverageBufferBitNv( GL_COVERAGE_BUFFER_BIT_NV ),//
    ;

    private final int glConstant;

    private ClearBufferMask ( int glConstant ) {
        this.glConstant = glConstant;
    }

    @Override
    public int getGlConstant () {
        return glConstant;
    }
}
