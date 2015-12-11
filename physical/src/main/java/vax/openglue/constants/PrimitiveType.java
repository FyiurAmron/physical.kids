package vax.openglue.constants;

import static vax.openglue.OpenGL.Constants.*;

/**

 @author toor
 */
public enum PrimitiveType implements OpenGlConstantWrapper {
    Points( GL_POINTS ),
    Lines( GL_LINES ),
    LineStrip( GL_LINE_STRIP ),
    LineLoop( GL_LINE_LOOP ),
    Triangles( GL_TRIANGLES ),
    TriangleFan( GL_TRIANGLE_FAN ),
    TriangleStrip( GL_TRIANGLE_STRIP ), //
    // Quads, QuadStrip & Polygons unsupported since 3.0/ES 2.0
    ;

    private final int glConstant;

    private PrimitiveType ( int glConstant ) {
        this.glConstant = glConstant;
    }

    @Override
    public int getGlConstant () {
        return glConstant;
    }
}
