package vax.openglue.constants;

import static vax.openglue.OpenGlConstants.*;

/**

 @author toor
 */
public enum ShaderType implements OpenGlConstantWrapper {
    VertexShader( GL_VERTEX_SHADER ),
    FragmentShader( GL_FRAGMENT_SHADER ), /*
     GeometryShader(GL_GEOMETRY_SHADER),
     TessControlShader(GL_TESS_CONTROL_SHADER),
     TessEvaluationShader(GL_TESS_EVALUATION_SHADER),
     ComputerShader(GL_COMPUTE_SHADER),
     */;

    private final int value;

    private ShaderType ( int value ) {
        this.value = value;
    }

    @Override
    public int getValue () {
        return value;
    }
}
