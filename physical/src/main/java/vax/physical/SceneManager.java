package vax.physical;

import java.util.HashSet;
import com.jogamp.opengl.GL;
import vax.math.*;
import vax.openglue.*;
import vax.openglue.constants.ClearBufferMask;
import vax.openglue.mesh.Mesh;
import vax.openglue.mesh.RectangleMesh;
import vax.openglue.mesh.SphereMesh;
import vax.openglue.shader.ShaderProgram;
import vax.physical.resource.Resource;
import vax.util.Action;

/**

 @author toor
 */
public class SceneManager implements CanvasGLUE.EventListener {
    private final UniformManager mainUniformManager = new UniformManager(), noiseUniformManager = new UniformManager();
    private final HashSet<Mesh> mainMeshes = new HashSet<>(), noiseMeshes = new HashSet<>();

    private ShaderProgram mainShaderProgram, noiseShaderProgram;

    private final boolean debug = true;

    private final Value1f //
            time = new Value1f(),
            random = new Value1f(),
            textureSampler = new Value1f( 0 );
    private final Vector4f //
            ambientColor = new Vector4f(),
            lightColor = new Vector4f(),
            backgroundColor = new Vector4f();
    private final Vector3f lightDirUnit = new Vector3f();
    private final Matrix4f //
            projectionMatrix = new Matrix4f(),
            modelviewMatrix = new Matrix4f(),
            transformMatrix = new Matrix4f( true );

    private final DebugGLUE debugGLUE = new DebugGLUE();

    private AbstractUniform.UMatrix4f transformMatrixUniform;

    public SceneManager () {
    }

    public void addMesh ( Mesh mesh ) {
        mainMeshes.add( mesh );
    }

    private float getTime () {
        return (float) ( System.currentTimeMillis() % 1_000_000 ) / 1000; // 6 digits of precision, about max for float
    }

    @Override
    public void init ( OpenGLUE gl ) {
        debugGLUE.setGlue( gl );
        gl = debugGLUE;
        ImageIO.GLUE glue = ImageIO.getGLUE();

        mainShaderProgram = new ResourceShaderProgram( "main" );
        noiseShaderProgram = new ResourceShaderProgram( "noise" );

        TextureData<?> dilloTD = glue.readTextureData( "angry-armadillo.png", Resource.class );
        TextureData<?> leftInterfaceTD = glue.readTextureData( "interface.png", Resource.class );
        SphereMesh ball = new SphereMesh( 0.1f, 12, 12, true );
        RectangleMesh leftInterface = new RectangleMesh( -1f, -2f );
        leftInterface.getTransform().setTranslationX( -0.5f );
        leftInterface.getTransform().setTranslationY( 1f );
        //RectangleMesh leftInterface = new RectangleMesh( 2, 2, 2 );

        ball.setTexture( dilloTD.createTexture( gl, TextureParameters.TRILINEAR, true ) );
        leftInterface.setTexture( leftInterfaceTD.createTexture( gl, TextureParameters.TRILINEAR, true ) );

        ball.setUpdateAction( (Mesh target) -> {
            Matrix4f trans = target.getTransform();
            float t = getTime();
            trans.setTranslationX( (float) Math.sin( t ) );
            trans.setTranslationZ( -1.5f + (float) Math.cos( t ) );
        } );

        mainMeshes.add( ball );
        noiseMeshes.add( leftInterface );

        //float aspectRatio = ( (float) settings.windowSize.getX() ) / settings.windowSize.getY();
        float aspectRatio = 4f / 3; // TODO infer this from window size
        //gl.glPolygonMode( OpenGLUE.Constants.GL_FRONT_AND_BACK, OpenGLUE.Constants.GL_LINE ); // DEBUG
        gl.glCullFace( OpenGLUE.Constants.GL_BACK );
        gl.glEnable( GL.GL_CULL_FACE );
        gl.glEnable( GL.GL_DEPTH_TEST );
        gl.glEnable( GL.GL_BLEND );
        gl.glBlendFunc( GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA );
        //projectionMatrix.setToPerspective( 0.1f, 100f, 67, aspectRatio );
        float halfSizeX = 1f, halfSizeY = 1f, sizeX = 2 * halfSizeX, sizeY = 2 * halfSizeY;
        projectionMatrix.setToOrthoWindow( -halfSizeX, -halfSizeY, sizeX, sizeY, -10, 10 );
        //projectionMatrix.setToIdentity();
        modelviewMatrix.setToIdentity();
        //modelviewMatrix.scaleZ( -1f );
        //modelviewMatrix.scaleY( -1f );
        modelviewMatrix.setTranslationZ( 3f );

        backgroundColor.set( 0.01f, 0.01f, 0.01f, 0.01f );
        ambientColor.set( 0.4f, 0.4f, 0.4f, 1.0f );
        lightColor.set( 1.0f, 1.0f, 1.0f, 1.0f );
        lightDirUnit.set( 0.5f, 1.0f, 0.5f );
        //lightDirUnit.set( 0.1f, 5.0f, 0.1f );
        lightDirUnit.normalize();

        Uniform[] uniforms
                = {
                    AbstractUniform.from( "projectionMatrix", projectionMatrix ),
                    AbstractUniform.from( "modelviewMatrix", modelviewMatrix ),
                    transformMatrixUniform = AbstractUniform.from( "transform", transformMatrix ),
                    AbstractUniform.from( "ambientColor", ambientColor ),
                    AbstractUniform.from( "lightColor", lightColor ),
                    AbstractUniform.from( "lightDirUnit", lightDirUnit ),
                    AbstractUniform.from( "time", time ),
                    AbstractUniform.from( "random", random ),
                    AbstractUniform.from( "textureSamples", textureSampler )
                };

        mainShaderProgram.init( gl );
        mainShaderProgram.use( gl );
        mainUniformManager.addUniforms( uniforms );
        mainUniformManager.init( gl, mainShaderProgram );

        noiseShaderProgram.init( gl );
        noiseShaderProgram.use( gl );
        noiseUniformManager.addUniforms( uniforms );
        noiseUniformManager.init( gl, noiseShaderProgram );

        for( Mesh m : mainMeshes ) {
            m.init( gl );
        }
        for( Mesh m : noiseMeshes ) {
            m.init( gl );
        }

        //gl.ueCheckError();
    }

    @Override
    public void display ( OpenGLUE gl ) {
        if ( debug ) {
            debugGLUE.setGlue( gl );
            gl = debugGLUE;
        }
        gl.glClearColor( backgroundColor );
        gl.glClear( ClearBufferMask.ColorBufferBit, ClearBufferMask.DepthBufferBit );

        random.setValue( MathUtils.nextFloat() );
        //float ftime = (float) ( System.currentTimeMillis() % 1000 ) / 1000;
        //System.out.println( ftime );
        time.setValue( getTime() );

        //modelviewMatrix.setTranslationX( (float) Math.sin( ftime ) );
        //modelviewMatrix.setTranslationZ( -1.5f + (float) Math.cos( ftime ) );
        // TODO sorting to ordered collection (list) vs cam dist
        mainShaderProgram.use( gl );
        mainUniformManager.updateGl( gl );
        for( Mesh m : mainMeshes ) {
            m.update( gl );
            transformMatrix.set( m.getTransform() );
            mainUniformManager.updateGl( gl, transformMatrixUniform ); // performance opt.
            m.render( gl );
        }

        noiseShaderProgram.use( gl );
        noiseUniformManager.updateGl( gl );
        for( Mesh m : noiseMeshes ) {
            m.update( gl );
            transformMatrix.set( m.getTransform() );
            noiseUniformManager.updateGl( gl, transformMatrixUniform ); // performance opt.
            m.render( gl );
        }

        //gl.ueCheckError();
    }

    @Override
    public void reshape ( OpenGLUE gl, int x, int y, int width, int height ) {
        if ( debug ) {
            debugGLUE.setGlue( gl );
            gl = debugGLUE;
        }
        // TODO if aspect changed: recalc persp. matrix

        //gl.ueCheckError();
    }

    @Override
    public void dispose ( OpenGLUE gl ) {
        if ( debug ) {
            debugGLUE.setGlue( gl );
            gl = debugGLUE;
        }
        /*
         for( Mesh mesh : meshes )
         mesh.dispose();
         */ // TODO implement

        //shaderProgram.dispose(); // TODO implement
        //uniformManager.dispose(); // TODO implement
        //gl.ueCheckError();
    }
}
