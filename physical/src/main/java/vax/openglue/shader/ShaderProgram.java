package vax.openglue.shader;

import java.util.ArrayList;
import vax.openglue.GLException;
import vax.openglue.OpenGLUE;
import vax.openglue.constants.ErrorCode;
import vax.openglue.constants.ShaderType;

/**

 @author toor
 */
public class ShaderProgram {
    public static final String[] ATTRIBS = { "in_position", "in_normal", "in_uv" };

    private final ArrayList<Shader> shaders = new ArrayList<>( 2 );
    private int shaderProgramHandle;

    public ShaderProgram () {
    }

    public ShaderProgram ( Shader vertexShader, Shader fragmentShader ) {
        if ( !vertexShader.isType( ShaderType.VertexShader ) || fragmentShader.isType( ShaderType.FragmentShader ) ) {
            throw new IllegalArgumentException( "shader(s) type(s) invalid" );
        }
        shaders.add( vertexShader );
        shaders.add( fragmentShader );
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

    /**
     Compiles and links vertex and fragment shaders.
     <p>
     Generates output to <code>System.err</code> in case there are any compilation/linking errors generated.

     @param gl
     @throws GLException thrown if there are any errors during any of the initialization steps
     */
    public void init ( OpenGLUE gl ) {
        ErrorCode errorCode;
        shaderProgramHandle = gl.glCreateProgram();
        for( Shader s : shaders ) {
            int shaderHandle = gl.glCreateShader( s.getType() );
            gl.glShaderSource( shaderHandle, s.getSource() );
            gl.glCompileShader( shaderHandle );
            errorCode = gl.ueGetError();
            if ( errorCode.isError() ) {
                System.err.println( gl.ueGetShaderInfoLog( shaderHandle ) );
                throw new GLException( errorCode );
            }
            //System.out.println( gl.glGetShaderInfoLog( shaderHandle ) );
            gl.glAttachShader( shaderProgramHandle, shaderHandle );
        }

        for( int i = 0; i < ATTRIBS.length; i++ ) {
            gl.glBindAttribLocation( shaderProgramHandle, i, ATTRIBS[i] );
        }

        gl.glLinkProgram( shaderProgramHandle );

        errorCode = gl.ueGetError();
        if ( errorCode.isError() ) {
            System.err.println( gl.ueGetProgramInfoLog( shaderProgramHandle ) );
            throw new GLException( errorCode );
        }
        //System.out.println( gl.glGetProgramInfoLog( shaderProgramHandle ) );
    }

    public void use ( OpenGLUE gl ) {
        gl.glUseProgram( shaderProgramHandle );
    }
}
