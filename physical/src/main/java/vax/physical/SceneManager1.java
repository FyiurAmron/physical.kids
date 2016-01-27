package vax.physical;

import java.util.ArrayList;
import java.util.HashSet;

import vax.math.*;
import vax.openglue.*;
import vax.openglue.constants.ClearBufferMask;
import vax.openglue.mesh.*;
import vax.physical.resource.Resource;

/**

 @author toor
 */
public class SceneManager1 implements EventListenerGL {
    private final ArrayList<Mesh> meshes = new ArrayList<>();
    private final ArrayList<Texture> textures = new ArrayList<>();

    private final boolean debug = false;

    private MeshBatch mainMeshBatch, noiseMeshBatch, overlayMeshBatch;

    private final Value1f //
            shininess = new Value1f(),
            time = new Value1f(),
            random = new Value1f();
    private final Value1i textureSampler = new Value1i( 0 );
    private final Vector4f //
            ambientColor = new Vector4f(),
            lightColor = new Vector4f(),
            backgroundColor = new Vector4f();
    private final Vector3f lightDirUnit = new Vector3f();
    private final Matrix4f //
            projectionMatrix = new Matrix4f(),
            viewMatrix = new Matrix4f(),
            modelMatrix = new Matrix4f( true );
    private final Vector2i viewportSize = new Vector2i(), mousePos = new Vector2i();

    private final DebugGLUE debugGLUE = new DebugGLUE();

    // misc junk
    private Texture dilloTex;
    private Material dilloMaterial;
    private Mesh ball;
    private Framebuffer framebuffer;
    private Material framebufferMaterial;
    private final WindowGLUE.Settings initialSettings;

    public SceneManager1 ( WindowGLUE.Settings initialSettings ) {
        this.initialSettings = initialSettings;
        viewportSize.set( initialSettings.windowSize );
    }

    private float getTime () {
        return (float) ( System.currentTimeMillis() % 1_000_000 ) / 1000; // 6 digits of precision, about max for float
    }

    @Override
    public void init ( OpenGLUE gl ) {
        debugGLUE.setGlue( gl );
        gl = debugGLUE;
        ImageGLUE imageGlue = gl.getImageGLUE();

        CameraDistanceSorter cds = new CameraDistanceSorter( viewMatrix );
        mainMeshBatch = new MeshBatch( "main", cds );
        noiseMeshBatch = new MeshBatch( "noise", cds );
        overlayMeshBatch = new MeshBatch( "overlay", cds );

        TextureData<?> dilloTD = imageGlue.readTextureData( "angry-armadillo.png", Resource.class );
        TextureData<?> leftInterfaceTD = imageGlue.readTextureData( "interface.png", Resource.class );


        /* SphereMesh */ ball = new SphereMesh( /* 0.1f */ 0.3f, 40, 40, true );
        ///* SphereMesh */ ball = new PrismMesh( new float[]{ 0, 0, 0 }, new float[]{ -0.5f, 0, -0.5f }, new float[]{ 0.5f, 0, -0.5f }, 0.3f );
        RectangleMesh leftInterface = new RectangleMesh( -1f, -2f, RectangleMesh.RECT_VT_PROTO_2 );
        //RectangleMesh rightInterface = new RectangleMesh( -1f, -2f, RectangleMesh.RECT_VT_PROTO_2 );
        leftInterface.getTransform().setTranslationX( -0.5f );
        leftInterface.getTransform().setTranslationY( 1f );
        //rightInterface.getTransform().setTranslationX( -0.5f );
        //rightInterface.getTransform().setTranslationY( 1f );
        //rightInterface.getTransform().setTranslationZ( -1f );

        RectangleMesh overlay = new RectangleMesh( -2f, -2f, RectangleMesh.RECT_VT_PROTO_2 );
        //overlay.getTransform().setTranslationX( -2f );
        overlay.getTransform().setTranslationY( 1f );


        //RectangleMesh leftInterface = new RectangleMesh( 2, 2, 2 );

        /* Texture */ dilloTex = new Texture( dilloTD, TextureParameters.TRILINEAR_ANISO_CLAMP, true );
        Texture //
                interfaceLeftTex = new Texture( leftInterfaceTD, TextureParameters.TRILINEAR_ANISO_CLAMP, true );
        dilloMaterial = new Material( dilloTex );
        ball.setMaterial( dilloMaterial );
        Material ilt = new Material( interfaceLeftTex );
        leftInterface.setMaterial( ilt );
        //rightInterface.setMaterial( new Material( interfaceLeftTex ) );
        //rightInterface.setMaterial( ilt );

        textures.add( dilloTex );
        textures.add( interfaceLeftTex );

        for( Texture tex : textures ) {
            tex.init( gl );
        }

        ball.setUpdateAction( (Mesh target) -> {
            Matrix4f trans = target.getTransform();

             float t = getTime();
             trans.setTranslationZ( (float) Math.sin( t ) );
             //trans.setTranslationZ( -1.5f + (float) Math.cos( t ) );


            Material mat = target.getMaterial();
            if ( mat != null ) {
                shininess.setValue( mat.shininess );
            }
            modelMatrix.set( trans );
        } );

        leftInterface.setUpdateAction( (Mesh target) -> {
            Material mat = target.getMaterial();
            if ( mat != null ) {
                shininess.setValue( mat.shininess );
            }
            modelMatrix.set( target.getTransform() );
        } );
/*
        rightInterface.setUpdateAction( (Mesh target) -> {
            Material mat = target.getMaterial();
            if ( mat != null ) {
                shininess.setValue( mat.shininess );
            }
            modelMatrix.set( target.getTransform() );
        } );
*/
        overlay.setUpdateAction( (Mesh target) -> {
            Material mat = target.getMaterial();
            if ( mat != null ) {
                shininess.setValue( mat.shininess );
            }
            modelMatrix.set( target.getTransform() );
        } );

        meshes.add( ball );
        meshes.add( leftInterface );
        //meshes.add( rightInterface );
        mainMeshBatch.getNonAlphaBlendedMeshes().add( ball );
        mainMeshBatch.getAlphaBlendedMeshes().add( leftInterface );
        //mainMeshBatch.getAlphaBlendedMeshes().add( rightInterface );
        //mainMeshBatch.getNonAlphaBlendedMeshes().add( leftInterface );
        noiseMeshBatch.getAlphaBlendedMeshes().add( overlay );
        overlayMeshBatch.getAlphaBlendedMeshes().add( overlay );

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
        viewMatrix.setToIdentity();
        //modelviewMatrix.scaleZ( -1f );
        //modelviewMatrix.scaleY( -1f );
        viewMatrix.setTranslationZ( 3f );

        //backgroundColor.set( 0.01f, 0.01f, 0.01f, 0.01f );
        backgroundColor.set( 0.1f, 0.1f, 0.1f, 0.1f );
        ambientColor.set( 0.4f, 0.4f, 0.4f, 1.0f );
        lightColor.set( 1.0f, 1.0f, 1.0f, 1.0f );
        lightDirUnit.set( 0.5f, 1.0f, 0.5f );
        //lightDirUnit.set( 0.1f, 5.0f, 0.1f );
        lightDirUnit.normalize();

        Uniform[] perRenderUniforms = {
            Uniform.from( "projectionMatrix", projectionMatrix ),
            Uniform.from( "viewMatrix", viewMatrix ),
            Uniform.from( "ambientColor", ambientColor ),
            Uniform.from( "lightColor", lightColor ),
            Uniform.from( "lightDirUnit", lightDirUnit ),
            Uniform.from( "time", time ),
            Uniform.from( "random", random ),
            Uniform.from( "textureSampler", textureSampler ),
            Uniform.from( "viewportSize", viewportSize ),
            Uniform.from( "mousePos", mousePos )
        };

        Uniform[] perMeshUniforms = {
            Uniform.from( "modelMatrix", modelMatrix ),
            Uniform.from( "shininess", shininess )
        };

        HashSet<Uniform> //
                mainUniSet = mainMeshBatch.getPerRenderUniforms(),
                noiseUniSet = noiseMeshBatch.getPerRenderUniforms(),
                overlayUniSet = overlayMeshBatch.getPerRenderUniforms();
        for( Uniform u : perRenderUniforms ) {
            mainUniSet.add( u );
            noiseUniSet.add( u );
            overlayUniSet.add( u );
        }

        mainUniSet = mainMeshBatch.getPerMeshUniforms();
        noiseUniSet = noiseMeshBatch.getPerMeshUniforms();
        overlayUniSet = overlayMeshBatch.getPerMeshUniforms();
        for( Uniform u : perMeshUniforms ) {
            mainUniSet.add( u );
            noiseUniSet.add( u );
            overlayUniSet.add( u );
        }

        mainMeshBatch.init( gl );
        noiseMeshBatch.init( gl );
        overlayMeshBatch.init( gl );

        /*
         Vector2i windowSize = initialSettings.windowSize;
         framebuffer = new Framebuffer( windowSize.getX(), windowSize.getY() );
         framebuffer.init( gl );
         framebufferMaterial = new Material(framebuffer.getTexture() );
         */
    }

    @Override
    public void render ( OpenGLUE gl ) {
        if ( debug ) {
            debugGLUE.setGlue( gl );
            gl = debugGLUE;
        }

        MouseGLUE mg = Main.wg.getMouseGLUE();
        int mouseX = mg.getX(), mouseY = mg.getY();
        mousePos.set( mouseX, mouseY );
        lightDirUnit.setX( mg.getRatioX() * 2 - 1 );
        lightDirUnit.setY( -( mg.getRatioY() * 2 - 1 ) );

        random.setValue( MathUtils.nextFloat() );
        //float ftime = (float) ( System.currentTimeMillis() % 1000 ) / 1000;
        //System.out.println( ftime );
        time.setValue( getTime() );

        //ball.getTransform().setToRotationZ( time.getValue() );
        //ball.getTransform().setToRotationTB( 0, 0, time.getValue() );
        //ball.getTransform().setToRotationTB( time.getValue(), time.getValue(), time.getValue() );
        if ( framebuffer != null ) {
            framebuffer.bind( gl );
            ball.setMaterial( dilloMaterial );
        }
        gl.glClearColor( backgroundColor );
        gl.glClear( ClearBufferMask.ColorBufferBit, ClearBufferMask.DepthBufferBit );

        //modelviewMatrix.setTranslationX( (float) Math.sin( ftime ) );
        //modelviewMatrix.setTranslationZ( -1.5f + (float) Math.cos( ftime ) );
        mainMeshBatch.render( gl );
        if ( framebuffer != null ) {
            //i++;
            //if ( i % 60 == 0 ) {
            //screenshot( gl );
            //dilloTex.createBufferImage( gl ).saveTo( "PNG", new File( "output.png" ) );
            //framebuffer.getTexture().createBufferImage( gl ).saveTo( "PNG", new File( "output.png" ) );
            //}
            framebuffer.unbind( gl );
            /*
             framebuffer.bind( gl, FramebufferType.ReadOnly );
             GL11.glReadBuffer( GL30.GL_COLOR_ATTACHMENT0 );
             framebuffer.unbind( gl, FramebufferType.DrawOnly );
             GL20.glDrawBuffers( GL11.GL_BACK_LEFT );
             */

            gl.glClearColor( backgroundColor );
            gl.glClear( ClearBufferMask.ColorBufferBit, ClearBufferMask.DepthBufferBit );
            ball.setMaterial( framebufferMaterial );
            mainMeshBatch.render( gl );
            //GL30.glBlitFramebuffer( 0, 0, 400, 400, 0, 0, 400, 400, GL11.GL_COLOR_BUFFER_BIT, GL11.GL_LINEAR );
        }

        // overlays
        gl.glClear( ClearBufferMask.DepthBufferBit );
        noiseMeshBatch.render( gl );
        gl.glClear( ClearBufferMask.DepthBufferBit );
        overlayMeshBatch.render( gl );
    }

    // TODO implement a proper export filter here
    private BufferImage screenshot ( OpenGLUE gl ) {
        //boolean depth = false;
        BufferImage bi = new BufferImage( initialSettings.windowSize, /* depth ? 1 : */ 4/* , depth */ );

        gl.glReadBuffer( OpenGLUE.Constants.GL_FRONT );

        /*
         gl.glReadPixels( 0, 0, bi.width, bi.height,
         depth ? OpenGLUE.Constants.GL_DEPTH_COMPONENT : OpenGLUE.Constants.GL_RGBA,
         depth ? OpenGLUE.Constants.GL_FLOAT : OpenGLUE.Constants.GL_UNSIGNED_BYTE, bi.buffer );
         */
        gl.glReadPixels( 0, 0, bi.width, bi.height, OpenGLUE.Constants.GL_RGBA, OpenGLUE.Constants.GL_UNSIGNED_BYTE, bi.buffer );

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

        if ( mainMeshBatch != null ) {
            mainMeshBatch.dispose( gl );
        }
        if ( noiseMeshBatch != null ) {
            noiseMeshBatch.dispose( gl );
        }

        for( Mesh mesh : meshes ) {
            mesh.dispose( gl );
        }

        for( Texture tex : textures ) {
            tex.dispose( gl );
        }
    }
}
