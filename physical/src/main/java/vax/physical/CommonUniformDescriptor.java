package vax.physical;

import vax.math.*;
import vax.openglue.UniformDescriptor;
import vax.openglue.UniformDescriptor.PerMeshUniform;
import vax.openglue.UniformDescriptor.PerRenderUniform;

/**
 POJO ATM.

 @author toor
 */
public class CommonUniformDescriptor extends UniformDescriptor.Reflective {
    @PerRenderUniform
    public final Value1f //
            time = new Value1f(),
            randomX = new Value1f(),
            randomY = new Value1f();
    @PerRenderUniform
    public final Value1i //
            textureSampler = new Value1i( 0 ); // explicitly stated, since it's actually 0
    @PerRenderUniform
    public final Vector4f //
            ambientColor = new Vector4f(),
            lightColor = new Vector4f(),
            backgroundColor = new Vector4f();
    @PerRenderUniform
    public final Vector3f lightDirUnit = new Vector3f();
    @PerRenderUniform
    public final Matrix4f //
            projectionMatrix = new Matrix4f(),
            viewMatrix = new Matrix4f();
    @PerRenderUniform
    public final //
            Vector2i viewportSize = new Vector2i(),
            mousePos = new Vector2i();

    @PerMeshUniform
    public final Value1f shininess = new Value1f();
    @PerMeshUniform
    public final Vector4f modelColor = new Vector4f();
    @PerMeshUniform
    public final Matrix4f
            modelMatrix = new Matrix4f(),
            combinedMatrix = new Matrix4f();

    public void updateCombinedMatrix() {
        combinedMatrix.set( viewMatrix ); // C = V
        combinedMatrix.multiplyMV( modelMatrix ); // C := V M // mMV is a bit faster
        combinedMatrix.multiplyLeft( projectionMatrix ); // C := P ( V M ) = P V M
    }
}
