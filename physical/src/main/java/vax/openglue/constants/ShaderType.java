package vax.openglue.constants;

import static vax.openglue.OpenGL.Constants.*;

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

    private final int glConstant;

    private ShaderType ( int glConstant ) {
        this.glConstant = glConstant;
    }
    
    public static ShaderType valueOf( int shaderTypeEnum ) {
        //TEMP TODO use static map
        switch( shaderTypeEnum) {
            case GL_VERTEX_SHADER:
                return VertexShader;
            case GL_FRAGMENT_SHADER:
                return FragmentShader;
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public int getGlConstant () {
        return glConstant;
    }
}
