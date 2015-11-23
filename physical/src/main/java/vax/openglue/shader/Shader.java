package vax.openglue.shader;

import vax.openglue.constants.ShaderType;

/**

 @author toor
 */
public class Shader {
    static private String path;

    static public String getPath () {
        if ( path == null ) {
            path = Shader.class.getPackage().getName().replace( ".", "/" );
        }
        return path;
    }

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
