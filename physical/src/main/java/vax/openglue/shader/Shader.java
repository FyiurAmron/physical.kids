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

    static public String createFromResource ( String filename ) {
        if ( path == null ) {
            path = Shader.class.getPackage().getName().replace( ".", "/" );
        }
        return path;
    }

    private final String source;
    private final int typeEnum;

    public Shader ( String source, int typeEnum ) {
        this.source = source;
        this.typeEnum = typeEnum;
    }

    public Shader ( String source, ShaderType shaderType ) {
        this( source, shaderType.getValue() );
    }

    public String getSource () {
        return source;
    }

    public int getType () {
        return typeEnum;
    }

    public boolean isType ( int typeEnum ) {
        return this.typeEnum == typeEnum;
    }

    public boolean isType ( ShaderType shaderType ) {
        return isType( shaderType.getValue() );
    }
}
