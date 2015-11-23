package vax.physical;

import vax.openglue.shader.Shader;
import vax.openglue.shader.ShaderProgram;
import vax.util.MiscUtils;

/**

 @author toor
 */
public class DefaultShaderProgram extends ShaderProgram {
    public DefaultShaderProgram () {
        super( MiscUtils.readResource( Shader.class, "new.vertex.glsl" ),
                MiscUtils.readResource( Shader.class, "new.fragment.glsl" ) );
    }
}
