package vax.physical;

import java.util.ArrayList;
import java.util.HashSet;
import com.jogamp.opengl.GL;
import vax.math.*;
import vax.openglue.*;
import vax.openglue.constants.ClearBufferMask;
import vax.openglue.mesh.*;
import vax.physical.resource.Resource;

/**

 @author toor
 */
public class SceneManager implements EventListenerGL {
    private final ArrayList<Mesh> meshes = new ArrayList<>();
    private final ArrayList<Texture> textures = new ArrayList<>();

    private final boolean debug = false;

    private MeshBatch mainMeshBatch, noiseMeshBatch;

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
    private final Vector2i viewportSize = new Vector2i();

    private final DebugGLUE debugGLUE = new DebugGLUE();

    public SceneManager () {
    }

    private float getTime () {
        return (float) ( System.currentTimeMillis() % 1_000_000 ) / 1000; // 6 digits of precision, about max for float
    }

    @Override
    public void init ( OpenGLUE gl ) {
        debugGLUE.setGlue( gl );
        gl = debugGLUE;
        ImageIO.GLUE glue = ImageIO.getGLUE();

        CameraDistanceSorter cds = new CameraDistanceSorter( modelviewMatrix );
        mainMeshBatch = new MeshBatch( "main", cds );
        noiseMeshBatch = new MeshBatch( "noise", cds );

        TextureData<?> dilloTD = glue.readTextureData( "angry-armadillo.png", Resource.class );
        TextureData<?> leftInterfaceTD = glue.readTextureData( "interface.png", Resource.class );
        SphereMesh ball = new SphereMesh( 0.1f, 12, 12, true );
        RectangleMesh leftInterface = new RectangleMesh( -1f, -2f, RectangleMesh.RECT_VT_PROTO_2 );
        leftInterface.getTransform().setTranslationX( -0.5f );
        leftInterface.getTransform().setTranslationY( 1f );
        //RectangleMesh leftInterface = new RectangleMesh( 2, 2, 2 );

        Texture //
                dilloTex = new Texture( dilloTD, TextureParameters.TRILINEAR, true ),
                interfaceLeftTex = new Texture( leftInterfaceTD, TextureParameters.TRILINEAR, true );
        ball.setTexture( dilloTex );
        leftInterface.setTexture( interfaceLeftTex );

        textures.add( dilloTex );
        textures.add( interfaceLeftTex );

        for( Texture tex : textures ) {
            tex.init( gl );
        }

        ball.setUpdateAction( (Mesh target) -> {
            Matrix4f trans = target.getTransform();

            float t = getTime();
            trans.setTranslationX( (float) Math.sin( t ) );
            trans.setTranslationZ( -1.5f + (float) Math.cos( t ) );

            transformMatrix.set( trans );
        } );

        leftInterface.setUpdateAction( (Mesh target) -> {
            transformMatrix.set( target.getTransform() );
        } );

        meshes.add( ball );
        meshes.add( leftInterface );
        mainMeshBatch.getNonAlphaBlendedMeshes().add( ball );
        //mainMeshBatch.getNonAlphaBlendedMeshes().add( leftInterface );
        noiseMeshBatch.getAlphaBlendedMeshes().add( leftInterface );

        //float aspectRatio = ( (float) settings.windowSize.getX() ) / settings.windowSize.getY();
        float aspectRatio = 4f / 3; // TODO infer this from window size
        //gl.glPolygonMode( OpenGLUE.Constants.GL_FRONT_AND_BACK, OpenGLUE.Constants.GL_LINE ); // DEBUG
        gl.glCullFace( OpenGLUE.Constants.GL_BACK );
        gl.glEnable( GL.GL_CULL_FACE );
        gl.glEnable( GL.GL_DEPTH_TEST );
        //gl.glEnable( GL.GL_BLEND ); // per-mesh now
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

        Uniform[] perRenderUniforms = {
            Uniform.from( "projectionMatrix", projectionMatrix ),
            Uniform.from( "modelviewMatrix", modelviewMatrix ),
            Uniform.from( "ambientColor", ambientColor ),
            Uniform.from( "lightColor", lightColor ),
            Uniform.from( "lightDirUnit", lightDirUnit ),
            Uniform.from( "time", time ),
            Uniform.from( "random", random ),
            Uniform.from( "textureSamples", textureSampler ),
            Uniform.from( "viewportSize", viewportSize )
        };

        Uniform[] perMeshUniforms = {
            Uniform.from( "transform", transformMatrix )
        };

        HashSet<Uniform> //
                mainUniSet = mainMeshBatch.getPerRenderUniforms(),
                noiseUniSet = noiseMeshBatch.getPerRenderUniforms();
        for( Uniform u : perRenderUniforms ) {
            mainUniSet.add( u );
            noiseUniSet.add( u );
        }

        mainUniSet = mainMeshBatch.getPerMeshUniforms();
        noiseUniSet = noiseMeshBatch.getPerMeshUniforms();
        for( Uniform u : perMeshUniforms ) {
            mainUniSet.add( u );
            noiseUniSet.add( u );
        }

        mainMeshBatch.init( gl );
        noiseMeshBatch.init( gl );
    }

    @Override
    public void render ( OpenGLUE gl ) {
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
        mainMeshBatch.render( gl );
        noiseMeshBatch.render( gl );
    }

    @Override
    public void reshape ( OpenGLUE gl, int x, int y, int width, int height ) {
        if ( debug ) {
            debugGLUE.setGlue( gl );
            gl = debugGLUE;
        }
        viewportSize.set( width, height );
        // TODO if aspect changed & persp. used: recalc persp. matrix
    }

    @Override
    public void dispose ( OpenGLUE gl ) {
        if ( debug ) {
            debugGLUE.setGlue( gl );
            gl = debugGLUE;
        }

        mainMeshBatch.dispose( gl );
        noiseMeshBatch.dispose( gl );

        for( Mesh mesh : meshes ) {
            mesh.dispose( gl );
        }

        for( Texture tex : textures ) {
            tex.dispose( gl );
        }
    }
}
