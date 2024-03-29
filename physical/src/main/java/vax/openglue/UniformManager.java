package vax.openglue;

import java.util.*;
import vax.openglue.shader.ShaderProgram;

/**

 @author toor
 */
public class UniformManager /* implements LifecycleListenerGL */ {

    private final ArrayList<Uniform> managedUniforms = new ArrayList<>();
    private final HashMap<Uniform, Integer> uniformMap = new HashMap<>();
    private final HashMap<String, Uniform> uniformNameMap = new HashMap<>();

    private boolean enableWarnings;

    public UniformManager () {
    }

    public boolean isEnableWarnings () {
        return enableWarnings;
    }

    public void setEnableWarnings ( boolean enableWarnings ) {
        this.enableWarnings = enableWarnings;
    }

    /*
     private final int shaderProgramHandle;

     public UniformManager ( ShaderProgram shaderProgram ) {
     this( shaderProgram.getShaderProgramHandle() );
     }

     public UniformManager ( int shaderProgramHandle ) {
     this.shaderProgramHandle = shaderProgramHandle;
     }
     */
    public void addUniform ( Uniform uniform ) {
        managedUniforms.add( uniform );
    }

    public void addUniforms ( Uniform... uniforms ) {
        managedUniforms.addAll( Arrays.asList( uniforms ) );
    }

    public void addUniforms ( Collection<? extends Uniform> uniforms ) {
        managedUniforms.addAll( uniforms );
    }

    public Uniform getUniform ( String name ) {
        return uniformNameMap.get( name );
    }

    public int getLocation ( Uniform uniform ) {
        return uniformMap.get( uniform );
    }

    public void init ( OpenGLUE gl, ShaderProgram shaderProgram ) {
        init( gl, shaderProgram.getShaderProgramHandle() );
    }

    //@Override
    public void init ( OpenGLUE gl, int shaderProgramHandle ) {
        for( Uniform uniform : managedUniforms ) {
            int uniformLocation = gl.glGetUniformLocation( shaderProgramHandle, uniform.getName() );
            uniformMap.put( uniform, uniformLocation );
            uniformNameMap.put( uniform.getName(), uniform );
            if ( uniformLocation == OpenGL.Constants.GL_INVALID_INDEX ) {
                if ( enableWarnings ) {
                    throw new GLException( "uniform not found: '" + uniform + "'" ); // TODO logger.warning() actually
                }
            } else {
                uniform.updateGL( gl, uniformLocation ); // note: no-op on location == -1 by def
            }
        }
    }

    public void updateGl ( OpenGLUE gl, String uniformName ) {
        updateGl( gl, getUniform( uniformName ) );
    }

    public void updateGl ( OpenGLUE gl, Uniform uniform ) {
        uniform.updateGL( gl, getLocation( uniform ) );
    }

    public void updateGl ( OpenGLUE gl ) {
        for( Uniform uniform : managedUniforms ) {
            uniform.updateGL( gl, getLocation( uniform ) );
        }
    }

    @Override
    public String toString () {
        return uniformNameMap.toString();
    }
}
