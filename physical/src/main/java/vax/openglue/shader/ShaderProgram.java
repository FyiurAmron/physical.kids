package vax.openglue.shader;

import java.util.ArrayList;
import vax.openglue.*;
import vax.openglue.constants.ShaderType;

/**

 @author toor
 */
public class ShaderProgram implements LifecycleListenerGL {
    public static final String[] ATTRIBS = { "in_position", "in_normal", "in_uv" };

    private final ArrayList<Shader> shaders = new ArrayList<>( 2 );
    private int shaderProgramHandle;
    private int[] shaderHandles;

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
    @Override
    public void init ( OpenGLUE gl ) {
        shaderProgramHandle = gl.glCreateProgram();
        int shaderCount = shaders.size();
        shaderHandles = new int[shaderCount];
        for( int i = 0; i < shaderCount; i++ ) {
            Shader s = shaders.get( i );
            int shaderHandle = gl.glCreateShader( s.getType() );
            shaderHandles[i] = shaderHandle;
            gl.glShaderSource( shaderHandle, s.getSource() );
            gl.glCompileShader( shaderHandle );
            if ( gl.glGetShaderi( shaderHandle, OpenGL.Constants.GL_COMPILE_STATUS ) == OpenGL.Constants.GL_FALSE ) {
                throw new GLException( gl.ueGetShaderInfoLog( shaderHandle ) );
            }
            gl.glAttachShader( shaderProgramHandle, shaderHandle );
        }

        for( int i = 0; i < ATTRIBS.length; i++ ) {
            gl.glBindAttribLocation( shaderProgramHandle, i, ATTRIBS[i] );
        }

        gl.glLinkProgram( shaderProgramHandle );

        boolean compileError = false;
        if ( compileError ) {
            throw new GLException( gl.ueGetProgramInfoLog( shaderProgramHandle ) );
        }
    }

    public void use ( OpenGLUE gl ) {
        gl.glUseProgram( shaderProgramHandle );
    }

    @Override
    public void dispose ( OpenGLUE gl ) {
        if ( shaderHandles == null ) {
            return; // init() not called yet
        }
        for( int i : shaderHandles ) {
            gl.glDeleteShader( i );
        }
        gl.glDeleteProgram( shaderProgramHandle );
    }
}
