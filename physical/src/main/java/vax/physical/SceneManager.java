package vax.physical;

import java.util.ArrayList;
import java.util.HashSet;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import vax.math.*;
import vax.openglue.*;
import vax.openglue.constants.ClearBufferMask;
import vax.openglue.constants.FramebufferType;
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

    // misc junk
    private Texture dilloTex;
    private Mesh ball;
    private Framebuffer framebuffer;
    private final WindowGLUE.Settings initialSettings;

    public SceneManager ( WindowGLUE.Settings initialSettings ) {
        this.initialSettings = initialSettings;
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
        /* SphereMesh */ ball = new SphereMesh( /*0.1f*/ 0.3f, 40, 40, true );
        RectangleMesh leftInterface = new RectangleMesh( -1f, -2f, RectangleMesh.RECT_VT_PROTO_2 );
        leftInterface.getTransform().setTranslationX( -0.5f );
        leftInterface.getTransform().setTranslationY( 1f );


        //RectangleMesh leftInterface = new RectangleMesh( 2, 2, 2 );

        /* Texture */ dilloTex = new Texture( dilloTD, TextureParameters.TRILINEAR_ANISO_CLAMP, true );
        Texture //
                interfaceLeftTex = new Texture( leftInterfaceTD, TextureParameters.TRILINEAR_ANISO_CLAMP, true );
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

        float aspectRatio = initialSettings.getAspectRatio();
        //gl.glPolygonMode( OpenGLUE.Constants.GL_FRONT_AND_BACK, OpenGLUE.Constants.GL_LINE ); // DEBUG
        gl.glCullFace( OpenGLUE.Constants.GL_BACK );
        gl.glEnable( OpenGLUE.Constants.GL_CULL_FACE );
        gl.glEnable( OpenGLUE.Constants.GL_DEPTH_TEST );
        //gl.glEnable( GL.GL_BLEND ); // per-mesh now
        gl.glBlendFunc( OpenGLUE.Constants.GL_SRC_ALPHA, OpenGLUE.Constants.GL_ONE_MINUS_SRC_ALPHA );
        //projectionMatrix.setToPerspective( 0.1f, 100f, 67, aspectRatio );
        float halfSizeX = 1f, halfSizeY = 1f, sizeX = 2 * halfSizeX, sizeY = 2 * halfSizeY;
        projectionMatrix.setToOrthoWindow( -halfSizeX, -halfSizeY, sizeX, sizeY, -10, 10 );
        //projectionMatrix.setToIdentity();
        modelviewMatrix.setToIdentity();
        //modelviewMatrix.scaleZ( -1f );
        //modelviewMatrix.scaleY( -1f );
        modelviewMatrix.setTranslationZ( 3f );

        //backgroundColor.set( 0.01f, 0.01f, 0.01f, 0.01f );
        backgroundColor.set( 0.1f, 0.1f, 0.1f, 0.1f );
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

        Vector2i windowSize = initialSettings.windowSize;
        framebuffer = new Framebuffer( windowSize.getX(), windowSize.getY() );
        framebuffer.init( gl );
    }

    @Override
    public void render ( OpenGLUE gl ) {
        if ( debug ) {
            debugGLUE.setGlue( gl );
            gl = debugGLUE;
        }

        random.setValue( MathUtils.nextFloat() );
        //float ftime = (float) ( System.currentTimeMillis() % 1000 ) / 1000;
        //System.out.println( ftime );
        time.setValue( getTime() );

        ball.getTransform().setToRotationZ( time.getValue() );

        if ( framebuffer != null ) {
            framebuffer.bind( gl );
            ball.setTexture( dilloTex );
        }
        gl.glClearColor( backgroundColor );
        gl.glClear( ClearBufferMask.ColorBufferBit, ClearBufferMask.DepthBufferBit );

        //modelviewMatrix.setTranslationX( (float) Math.sin( ftime ) );
        //modelviewMatrix.setTranslationZ( -1.5f + (float) Math.cos( ftime ) );
        mainMeshBatch.render( gl );
        noiseMeshBatch.render( gl );
        if ( framebuffer != null ) {
            i++;
            if ( i % 60 == 0 ) {
                //screenshot( gl );
                //dilloTex.createBufferImage( gl ).saveTo( "PNG", new File( "output.png" ) );
                //framebuffer.getTexture().createBufferImage( gl ).saveTo( "PNG", new File( "output.png" ) );
            }
            framebuffer.unbind( gl );
            /*
            framebuffer.bind( gl, FramebufferType.ReadOnly );
            GL11.glReadBuffer( GL30.GL_COLOR_ATTACHMENT0 );
            framebuffer.unbind( gl, FramebufferType.DrawOnly );
            GL20.glDrawBuffers( GL11.GL_BACK_LEFT );
            */

            gl.glClearColor( backgroundColor );
            gl.glClear( ClearBufferMask.ColorBufferBit, ClearBufferMask.DepthBufferBit );
            ball.setTexture( framebuffer.getTexture() );
            mainMeshBatch.render( gl );
            noiseMeshBatch.render( gl );
            //GL30.glBlitFramebuffer( 0, 0, 400, 400, 0, 0, 400, 400, GL11.GL_COLOR_BUFFER_BIT, GL11.GL_LINEAR );
        }
    }

    int i;

    // TODO implement a proper export filter here
    private BufferImage screenshot ( OpenGLUE gl ) {
        boolean depth = false;
        BufferImage bi = new BufferImage( initialSettings.windowSize, /* depth ? 1 : */ 4, depth );

        gl.glReadBuffer( OpenGLUE.Constants.GL_FRONT );

        gl.glReadPixels( 0, 0, bi.width, bi.height,
                depth ? OpenGLUE.Constants.GL_DEPTH_COMPONENT : OpenGLUE.Constants.GL_RGBA,
                depth ? OpenGLUE.Constants.GL_FLOAT : OpenGLUE.Constants.GL_UNSIGNED_BYTE, bi.buffer );
        return bi;
    }

    @Override
    public void resize ( OpenGLUE gl, int x, int y, int width, int height ) {
        if ( debug ) {
            debugGLUE.setGlue( gl );
            gl = debugGLUE;
        }
        // TODO if aspect changed & persp. used: recalc persp. matrix
        // also note: does LWJGL/JOGL automagically change glViewport on resize?
        viewportSize.set( width, height );
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
