package vax.openglue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import vax.openglue.shader.ShaderProgram;

/**

 @author toor
 */
public class UniformManager {
    private final ArrayList<Uniform> managedUniforms = new ArrayList<>();
    private final HashMap<Uniform, Integer> uniformMap = new HashMap<>();
    private final HashMap<String, Uniform> uniformNameMap = new HashMap<>();

    public void addUniform ( Uniform uniform ) {
        managedUniforms.add( uniform );
    }

    public void addUniforms ( Uniform... uniforms ) {
        managedUniforms.addAll( Arrays.asList( uniforms ) );
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

    public void init ( OpenGLUE gl, int shaderProgramHandle ) {
        for( Uniform uniform : managedUniforms ) {
            int uniformLocation = gl.glGetUniformLocation( shaderProgramHandle, uniform.getName() );
            uniformMap.put( uniform, uniformLocation );
            uniformNameMap.put( uniform.getName(), uniform );
            uniform.updateGl( gl, uniformLocation );
        }
    }

    public void updateGl ( OpenGLUE gl, String uniformName ) {
        updateGl( gl, getUniform( uniformName ) );
    }

    public void updateGl ( OpenGLUE gl, Uniform uniform ) {
        uniform.updateGl( gl, getLocation( uniform ) );
    }

    public void updateGl ( OpenGLUE gl ) {
        for( Uniform uniform : managedUniforms ) {
            uniform.updateGl( gl, getLocation( uniform ) );
        }
    }
}
