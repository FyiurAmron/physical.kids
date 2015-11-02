package vax.openglue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**

 @author toor
 */
public class UniformManager {
    private final ArrayList<Uniform.UBase<?>> managedUniforms = new ArrayList<>();
    private final HashMap<Uniform.UBase<?>, Integer> uniformMap = new HashMap<>();
    private final HashMap<String, Uniform.UBase<?>> uniformNameMap = new HashMap<>();

    public void addUniform ( Uniform.UBase<?> uniform ) {
        managedUniforms.add( uniform );
    }

    public void addUniforms ( Uniform.UBase<?>... uniforms ) {
        managedUniforms.addAll( Arrays.asList( uniforms ) );
    }

    public Uniform.UBase<?> getUniform ( String name ) {
        return uniformNameMap.get( name );
    }

    public int getLocation ( Uniform.UBase<?> uniform ) {
        return uniformMap.get( uniform );
    }

    public void init ( OpenGlUe gl, int shaderProgramHandle ) {
        for( Uniform.UBase<?> uniform : managedUniforms ) {
            int uniformLocation = gl.glGetUniformLocation( shaderProgramHandle, uniform.getName() );
            uniformMap.put( uniform, uniformLocation );
            uniformNameMap.put( uniform.getName(), uniform );
            uniform.updateGl( gl, uniformLocation );
        }
    }

    public void updateGl ( OpenGlUe gl, String uniformName ) {
        updateGl( gl, getUniform( uniformName ) );
    }

    public void updateGl ( OpenGlUe gl, Uniform.UBase<?> uniform ) {
        uniform.updateGl( gl, getLocation( uniform ) );
    }

    public void updateGl ( OpenGlUe gl ) {
        for( Uniform.UBase<?> uniform : managedUniforms ) {
            uniform.updateGl( gl, getLocation( uniform ) );
        }
    }
}
