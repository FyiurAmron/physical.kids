package vax.openglue.constants;

import static vax.openglue.OpenGlConstants.*;

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

    private final int value;

    private ClearBufferMask ( int value ) {
        this.value = value;
    }

    @Override
    public int getValue () {
        return value;
    }
}
