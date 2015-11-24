package vax.physical;

import java.util.HashSet;
import java.util.Random;
import vax.math.*;
import vax.openglue.*;
import vax.openglue.constants.ClearBufferMask;
import vax.openglue.mesh.Mesh;
import vax.openglue.shader.ShaderProgram;

/**

 @author toor
 */
public class SceneManager implements CanvasGLUE.EventListener {
    private final UniformManager uniformManager = new UniformManager();
    private final HashSet<Mesh> meshes = new HashSet<>();

    private final ShaderProgram shaderProgram;

    private final Value1f //
            time = new Value1f(),
            random = new Value1f(),
            textureSampler = new Value1f( 0 );
    private final Vector3f //
            ambientColor = new Vector3f(),
            lightColor = new Vector3f(),
            lightDirUnit = new Vector3f();
    private final Matrix4f //
            projectionMatrix = new Matrix4f(),
            modelviewMatrix = new Matrix4f(),
            transformMatrix = new Matrix4f( true );

    private Uniform.UMatrix4f transformMatrixUniform;

    public SceneManager ( String shadername ) {
        shaderProgram = new ResourceShaderProgram( shadername );
    }

    public void addMesh ( Mesh mesh ) {
        meshes.add( mesh );
    }

    @Override
    public void init ( OpenGLUE gl ) {
        //float aspectRatio = ( (float) settings.windowSize.getX() ) / settings.windowSize.getY();

        float aspectRatio = 4f / 3;
        gl.glPolygonMode( OpenGLUE.Constants.GL_FRONT_AND_BACK, OpenGLUE.Constants.GL_LINE );
        projectionMatrix.setToPerspective( 0.1f, 100f, 67, aspectRatio );
        modelviewMatrix.setToIdentity();
        modelviewMatrix.setTranslationZ( -3f );

        ambientColor.set( 0.4f, 0.4f, 0.4f );
        lightColor.set( 1.0f, 1.0f, 1.0f );
        lightDirUnit.set( 0.5f, 1.0f, 0.5f );
        //lightDirUnit.set( 0.1f, 5.0f, 0.1f );
        lightDirUnit.normalize();

        shaderProgram.init( gl );

        uniformManager.addUniforms(
                Uniform.from( "projectionMatrix", projectionMatrix ),
                Uniform.from( "modelviewMatrix", modelviewMatrix ),
                transformMatrixUniform = Uniform.from( "transform", transformMatrix ),
                Uniform.from( "ambientColor", ambientColor ),
                Uniform.from( "lightColor", lightColor ),
                Uniform.from( "lightDirUnit", lightDirUnit ),
                Uniform.from( "time", time ),
                Uniform.from( "random", random ),
                Uniform.from( "textureSamples", textureSampler )
        );
        uniformManager.getUniform( "transform" );
        uniformManager.init( gl, shaderProgram.getShaderProgramHandle() );

        for( Mesh m : meshes ) {
            m.init( gl );
        }
    }

    @Override
    public void display ( OpenGLUE gl ) {
        gl.glClearColor( 0.2f, 0.2f, 0.2f, 1f );
        //gl.glClearColor( backgroundColor );
        gl.glClear( ClearBufferMask.ColorBufferBit, ClearBufferMask.DepthBufferBit );

        random.setValue( MathUtils.nextFloat() );
        //float ftime = (float) ( System.currentTimeMillis() % 1000 ) / 1000;
        float ftime = (float) ( System.currentTimeMillis() % 1_000_000 ) / 1000; // 6 digits of precision, about max for float
        //System.out.println( ftime );
        time.setValue( ftime );
        uniformManager.updateGl( gl );

        for( Mesh m : meshes ) {
            transformMatrix.set( m.getTransform() );
            uniformManager.updateGl( gl, transformMatrixUniform );
            m.render( gl );
        }

        int errorCode = gl.glGetError();
        if ( errorCode != OpenGL.Constants.GL_NO_ERROR ) {
            throw new RuntimeException( "glGetError() = " + errorCode );
        }
    }

    @Override
    public void reshape ( OpenGLUE gl, int x, int y, int width, int height ) {
    }

    @Override
    public void dispose ( OpenGLUE gl ) {
    }
}
