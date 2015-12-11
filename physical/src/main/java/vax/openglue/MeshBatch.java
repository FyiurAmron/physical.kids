package vax.openglue;

import java.util.HashSet;

import vax.openglue.mesh.Mesh;
import vax.openglue.mesh.MeshSorter;
import vax.openglue.shader.ShaderProgram;
import vax.physical.ResourceShaderProgram;

/**

 @author toor
 */
public class MeshBatch implements EventListenerGL {
    private final HashSet<Mesh> nonAlphaBlendedMeshes;
    private final HashSet<Mesh> alphaBlendedMeshes;
    private final MeshSorter meshSorter;
    private final ShaderProgram shaderProgram;
    private final HashSet<Uniform> perRenderUniforms;
    private final HashSet<Uniform> perMeshUniforms;
    private final UniformManager uniformManager;

    public MeshBatch ( String shaderName, MeshSorter meshSorter ) {
        this( new HashSet<>(), new HashSet<>(), meshSorter, new ResourceShaderProgram( shaderName ),
                new HashSet<>(), new HashSet<>(), new UniformManager() );
    }

    public MeshBatch ( HashSet<Mesh> nonAlphaBlendedMeshes, HashSet<Mesh> alphaBlendedMeshes, MeshSorter meshSorter,
            ShaderProgram shaderProgram, HashSet<Uniform> perPassUniforms, HashSet<Uniform> perMeshUniforms, UniformManager uniformManager ) {
        this.nonAlphaBlendedMeshes = nonAlphaBlendedMeshes;
        this.alphaBlendedMeshes = alphaBlendedMeshes;
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
    public HashSet<Mesh> getAlphaBlendedMeshes () {
        return alphaBlendedMeshes;
    }

    /**

     @return explicitly mutable batch's mesh set
     */
    public HashSet<Mesh> getNonAlphaBlendedMeshes () {
        return nonAlphaBlendedMeshes;
    }

    @Override
    public void init ( OpenGLUE gl ) {
        shaderProgram.init( gl );
        shaderProgram.use( gl );
        
        uniformManager.addUniforms( perRenderUniforms );
        uniformManager.addUniforms( perMeshUniforms );
        uniformManager.init( gl, shaderProgram );

        for( Mesh m : nonAlphaBlendedMeshes ) {
            m.init( gl );
        }

        for( Mesh m : alphaBlendedMeshes ) {
            m.init( gl );
        }
    }

    @Override
    public void render ( OpenGLUE gl ) {
        shaderProgram.use( gl );
        for( Uniform u : perRenderUniforms ) {
            uniformManager.updateGl( gl, u );
        }

        if ( !nonAlphaBlendedMeshes.isEmpty() ) {
            gl.glDisable( OpenGL.Constants.GL_BLEND );
            for( Mesh m : nonAlphaBlendedMeshes ) {
                m.update();
            }
            for( Mesh m : meshSorter.sort( nonAlphaBlendedMeshes ) ) {
                for( Uniform u : perMeshUniforms ) {
                    uniformManager.updateGl( gl, u );
                }
                m.render( gl );
            }
        }

        if ( !alphaBlendedMeshes.isEmpty() ) {
            gl.glEnable( OpenGL.Constants.GL_BLEND );
            for( Mesh m : alphaBlendedMeshes ) {
                m.update();
            }
            for( Mesh m : meshSorter.sort( alphaBlendedMeshes, false ) ) {
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
        for( Mesh mesh : nonAlphaBlendedMeshes ) {
            mesh.dispose( gl );
        }
        for( Mesh mesh : alphaBlendedMeshes ) {
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
