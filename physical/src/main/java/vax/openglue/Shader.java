package vax.openglue;

import vax.openglue.constants.ShaderType;

/**

 @author toor
 */
public class Shader {
    private final String source;
    private final int type;

    public Shader ( String source, int type ) {
        this.source = source;
        this.type = type;
    }

    public Shader ( String source, ShaderType shaderType ) {
        this( source, shaderType.getValue() );
    }

    public String getSource () {
        return source;
    }

    public int getType () {
        return type;
    }
}
