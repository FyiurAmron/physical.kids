package vax.openglue;

import java.util.ArrayList;
import vax.openglue.constants.ShaderType;
import static vax.physical.PhysicalWindow.attribs;

/**

 @author toor
 */
public class ShaderProgram {
    private final ArrayList<Shader> shaders = new ArrayList<>( 2 );
    private int shaderProgramHandle;

    public ShaderProgram () {
    }

    public ShaderProgram ( String vertexShaderSource, String fragmentShaderSource ) {
        shaders.add( new Shader( vertexShaderSource, ShaderType.VertexShader ) );
        shaders.add( new Shader( fragmentShaderSource, ShaderType.FragmentShader ) );
    }

    public void addShader ( String source, ShaderType shaderType ) {
        shaders.add( new Shader( source, shaderType ) );
    }

    public void addShader ( Shader shader ) {
        shaders.add( shader );
    }

    public int getShaderProgramHandle () {
        return shaderProgramHandle;
    }

    /**
     @return mutable reference
     */
    public ArrayList<Shader> getShaders () {
        return shaders;
    }

    public void init ( OpenGlUe gl ) {
        shaderProgramHandle = gl.glCreateProgram();
        for( Shader s : shaders ) {
            int shaderHandle = gl.glCreateShader( s.getType() );
            gl.glShaderSource( shaderHandle, s.getSource() );
            gl.glCompileShader( shaderHandle );
            //System.out.println( gl.glGetShaderInfoLog( shaderHandle ) );
            gl.glAttachShader( shaderProgramHandle, shaderHandle );
        }

        for( int i = 0; i < attribs.length; i++ ) {
            gl.glBindAttribLocation( shaderProgramHandle, i, attribs[i] );
        }

        gl.glLinkProgram( shaderProgramHandle );
        //System.out.println( gl.glGetProgramInfoLog( shaderProgramHandle ) );
        gl.glUseProgram( shaderProgramHandle );
    }
}
