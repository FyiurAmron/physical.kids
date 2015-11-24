package vax.physical;

import vax.openglue.shader.Shader;
import vax.openglue.shader.ShaderProgram;
import vax.util.MiscUtils;

/**

 @author toor
 */
public class ResourceShaderProgram extends ShaderProgram {
    public ResourceShaderProgram ( String vertexShaderResourceName, String fragmentShaderResourceName ) {
        super( MiscUtils.readResource( Shader.class, vertexShaderResourceName ),
                MiscUtils.readResource( Shader.class, fragmentShaderResourceName ) );
    }

    /**
     equal to <code>this( shaderSource + ".vertex.glsl", shaderSource + ".fragment.glsl" );</code>

     @param shaderName
     */
    public ResourceShaderProgram ( String shaderName ) {
        this( shaderName + ".vertex.glsl", shaderName + ".fragment.glsl" );
    }
}
