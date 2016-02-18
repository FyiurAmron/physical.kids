package vax.openglue.mesh;

import vax.math.Matrix4f;
import vax.openglue.Material;
import vax.openglue.OpenGLUE;
import vax.openglue.Renderable;
import vax.util.Action;

/**

 @author toor
 */
public class MeshInstance implements Renderable {
    private final Matrix4f transform = new Matrix4f( true );
    private final Mesh mesh;

    private Action<MeshInstance> updateAction, updateUniformAction;
    private Material material;

    public MeshInstance ( Mesh mesh ) {
        this.mesh = mesh;
    }

    public MeshInstance ( Mesh mesh, Action<MeshInstance> updateUniformAction, Material material ) {
        this.mesh = mesh;
        this.updateUniformAction = updateUniformAction;
        this.material = material;
    }

    public MeshInstance ( Mesh mesh, Action<MeshInstance> updateAction, Action<MeshInstance> updateUniformAction, Material material ) {
        this.mesh = mesh;
        this.updateAction = updateAction;
        this.updateUniformAction = updateUniformAction;
        this.material = material;
    }

    /*
     getters/setters
     */
    public Action<MeshInstance> getUpdateAction () {
        return updateAction;
    }

    public void setUpdateAction ( Action<MeshInstance> updateAction ) {
        this.updateAction = updateAction;
    }

    public Action<MeshInstance> getUpdateUniformAction () {
        return updateUniformAction;
    }

    public void setUpdateUniformAction ( Action<MeshInstance> updateUniformAction ) {
        this.updateUniformAction = updateUniformAction;
    }

    public Mesh getMesh () {
        return mesh;
    }

    public Matrix4f getTransform () {
        return transform;
    }

    public Material getMaterial () {
        return material;
    }

    public void setMaterial ( Material material ) {
        this.material = material;
    }

    public MeshInstance copy ( boolean copyTransform ) {
        MeshInstance copy = new MeshInstance( mesh, updateAction, updateUniformAction, material );
        if ( copyTransform ) {
            copy.transform.set( transform );
        }
        return copy;
    }

    /*
     interface implementation
     */
    //@Override
    public void update () {
        if ( updateAction != null ) {
            updateAction.exec( this );
        }
        if ( updateUniformAction != null ) {
            updateUniformAction.exec( this );
        }
    }

    @Override
    public void render ( OpenGLUE gl ) {
        if ( material != null ) {
            material.bind( gl );
        }
        mesh.render( gl );
        if ( material != null ) {
            material.unbind( gl );
        }
    }

    /**
     NOP. The instantiated Mesh has to be inited first.

     @param gl
     */
    @Override
    public void init ( OpenGLUE gl ) {
    }

    /**
     NOP. The instantiated Mesh has to be disposed later on.

     @param gl
     */
    @Override
    public void dispose ( OpenGLUE gl ) {
    }
}
