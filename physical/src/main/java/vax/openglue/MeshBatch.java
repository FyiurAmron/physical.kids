package vax.openglue;

import java.util.HashSet;

import vax.openglue.mesh.Mesh;
import vax.openglue.mesh.MeshInstance;
import vax.openglue.mesh.MeshSorter;
import vax.openglue.shader.ShaderProgram;
import vax.physical.ResourceShaderProgram;

/**

 @author toor
 */
public class MeshBatch implements EventListenerGL {
    private final HashSet<MeshInstance> nonAlphaBlendedMeshInstances;
    private final HashSet<MeshInstance> alphaBlendedMeshInstances;
    private final HashSet<Mesh> meshes;
    private final MeshSorter meshSorter;
    private final ShaderProgram shaderProgram;
    private final HashSet<Uniform> perRenderUniforms;
    private final HashSet<Uniform> perMeshUniforms;
    private final UniformManager uniformManager;

    public MeshBatch ( String shaderName, MeshSorter meshSorter ) {
        this( new HashSet<>(), new HashSet<>(), new HashSet<>(), meshSorter, new ResourceShaderProgram( shaderName ),
                new HashSet<>(), new HashSet<>(), new UniformManager() );
    }

    public MeshBatch ( HashSet<MeshInstance> nonAlphaBlendedMeshes, HashSet<MeshInstance> alphaBlendedMeshes, HashSet<Mesh> meshes,
            MeshSorter meshSorter,
            ShaderProgram shaderProgram, HashSet<Uniform> perPassUniforms, HashSet<Uniform> perMeshUniforms, UniformManager uniformManager ) {
        this.nonAlphaBlendedMeshInstances = nonAlphaBlendedMeshes;
        this.alphaBlendedMeshInstances = alphaBlendedMeshes;
        this.meshes = meshes;
        this.meshSorter = meshSorter;
        this.shaderProgram = shaderProgram;
        this.perRenderUniforms = perPassUniforms;
        this.perMeshUniforms = perMeshUniforms;
        this.uniformManager = uniformManager;
    }

    /**

     @return explicitly mutable batch's per-render uniform set
     */
    public HashSet<Uniform> getPerRenderUniforms () {
        return perRenderUniforms;
    }

    /**

     @return explicitly mutable batch's per-mesh uniform set
     */
    public HashSet<Uniform> getPerMeshUniforms () {
        return perMeshUniforms;
    }

    /**

     @return explicitly mutable batch's mesh set
     */
    public HashSet<MeshInstance> getAlphaBlendedMeshInstances () {
        return alphaBlendedMeshInstances;
    }

    /**

     @return explicitly mutable batch's mesh set
     */
    public HashSet<MeshInstance> getNonAlphaBlendedMeshInstances () {
        return nonAlphaBlendedMeshInstances;
    }

    @Override
    public void init ( OpenGLUE gl ) {
        shaderProgram.init( gl );
        shaderProgram.use( gl );

        uniformManager.addUniforms( perRenderUniforms );
        uniformManager.addUniforms( perMeshUniforms );
        uniformManager.init( gl, shaderProgram );

        for( MeshInstance m : nonAlphaBlendedMeshInstances ) {
            meshes.add( m.getMesh() );
        }

        for( MeshInstance m : alphaBlendedMeshInstances ) {
            meshes.add( m.getMesh() );
        }

        for( Mesh m : meshes ) {
            m.init( gl );
        }
    }

    @Override
    public void render ( OpenGLUE gl ) {
        shaderProgram.use( gl );
        for( Uniform u : perRenderUniforms ) {
            uniformManager.updateGl( gl, u );
        }

        if ( !nonAlphaBlendedMeshInstances.isEmpty() ) {
            gl.glDisable( OpenGL.Constants.GL_BLEND );
            for( MeshInstance m : meshSorter.sort( nonAlphaBlendedMeshInstances ) ) {
                m.update();
                for( Uniform u : perMeshUniforms ) {
                    uniformManager.updateGl( gl, u );
                }
                m.render( gl );
            }
        }

        if ( !alphaBlendedMeshInstances.isEmpty() ) {
            gl.glEnable( OpenGL.Constants.GL_BLEND );
            for( MeshInstance m : meshSorter.sort( alphaBlendedMeshInstances, false ) ) {
                m.update();
                for( Uniform u : perMeshUniforms ) {
                    uniformManager.updateGl( gl, u );
                }
                m.render( gl );
            }
        }
    }

    @Override
    public void resize ( OpenGLUE gl, int x, int y, int width, int height ) {
        // updating all of the matrices handled in the render(gl) itself
    }

    public void disposeMeshes ( OpenGLUE gl ) {
        for( Mesh mesh : meshes ) {
            mesh.dispose( gl );
        }
    }

    /**
     Note: this method doesn't dispose the meshes themselves.

     @param gl
     */
    @Override
    public void dispose ( OpenGLUE gl ) {
        shaderProgram.dispose( gl );
    }

}
