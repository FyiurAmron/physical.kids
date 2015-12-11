package vax.openglue.constants;

import vax.openglue.OpenGL;

/**

 @author toor
 */
public enum FramebufferType implements OpenGlConstantWrapper {
    ReadOnly( OpenGL.Constants.GL_READ_FRAMEBUFFER ),
    DrawOnly( OpenGL.Constants.GL_DRAW_FRAMEBUFFER ),
    ReadAndDraw( OpenGL.Constants.GL_FRAMEBUFFER );

    private final int glConstant;

    private FramebufferType ( int glConstant ) {
        this.glConstant = glConstant;
    }

    @Override
    public int getGlConstant () {
        return glConstant;
    }

}
