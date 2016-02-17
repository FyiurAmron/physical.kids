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
public class UiTestScene implements EventListenerGL {
    private final ArrayList<Mesh> meshes = new ArrayList<>();
    private final ArrayList<Texture> textures = new ArrayList<>();

    private final boolean debug = false;

    private MeshBatch mainMeshBatch, noiseMeshBatch, overlayMeshBatch;

    private final CommonUniformDescriptor cud = new CommonUniformDescriptor();

    private final DebugGLUE debugGLUE = new DebugGLUE();

    // misc junk
    private Texture dilloTex;
    private Material dilloMaterial;
    private MeshInstance ball;
    private Framebuffer framebuffer;
    private Material framebufferMaterial;
    private final WindowGLUE.Settings initialSettings;

    public UiTestScene ( WindowGLUE.Settings initialSettings ) {
        this.initialSettings = initialSettings;
        cud.viewportSize.set( initialSettings.windowSize );
    }

    private float getTime () {
        return (float) ( System.currentTimeMillis() % 1_000_000 ) / 1000; // 6 digits of precision, about max for float
    }

    @Override
    public void init ( OpenGLUE gl ) {
        cud.init();
        debugGLUE.setGlue( gl );
        gl = debugGLUE;
        ImageGLUE imageGlue = gl.getImageGLUE();

        CameraDistanceSorter cds = new CameraDistanceSorter( cud.viewMatrix );
        mainMeshBatch = new MeshBatch( "main", cds );
        noiseMeshBatch = new MeshBatch( "noise", cds );
        overlayMeshBatch = new MeshBatch( "overlay", cds );

        TextureData<?> dilloTD = imageGlue.readTextureData( "angry-armadillo.png", Resource.class );
        TextureData<?> leftInterfaceTD = imageGlue.readTextureData( "interface.png", Resource.class );
        TextureData<?> squareHairTD = imageGlue.readTextureData( "squarehair.png", Resource.class );

        SphereMesh ballMesh = new SphereMesh( /* 0.1f */ 0.3f, 40, 40, true );
        ball = new MeshInstance( ballMesh );
        ///* SphereMesh */ ball = new PrismMesh( new float[]{ 0, 0, 0 }, new float[]{ -0.5f, 0, -0.5f }, new float[]{ 0.5f, 0, -0.5f }, 0.3f );
        RectangleMesh leftInterface = new RectangleMesh( -1f, -2f, RectangleMesh.RECT_VT_PROTO_2 );
        //RectangleMesh rightInterface = new RectangleMesh( -1f, -2f, RectangleMesh.RECT_VT_PROTO_2 );
        MeshInstance leftInterfaceMI = new MeshInstance( leftInterface );
        leftInterfaceMI.getTransform().setTranslationX( -0.5f );
        leftInterfaceMI.getTransform().setTranslationY( 1f );
        //rightInterface.getTransform().setTranslationX( -0.5f );
        //rightInterface.getTransform().setTranslationY( 1f );
        //rightInterface.getTransform().setTranslationZ( -1f );

        RectangleMesh overlay = new RectangleMesh( -2f, -2f, RectangleMesh.RECT_VT_PROTO_2 );
        MeshInstance overlayMI = new MeshInstance( overlay );
        //overlay.getTransform().setTranslationX( -2f );
        overlayMI.getTransform().setTranslationY( 1f );


        //RectangleMesh leftInterface = new RectangleMesh( 2, 2, 2 );

        /* Texture */ dilloTex = new Texture( dilloTD, TextureParameters.TRILINEAR_ANISO_CLAMP, true );
        Texture squareHairTex = new Texture( squareHairTD, TextureParameters.TRILINEAR_ANISO_CLAMP, true );
        Texture //
                interfaceLeftTex = new Texture( leftInterfaceTD, TextureParameters.TRILINEAR_ANISO_CLAMP, true );
        dilloMaterial = new Material( dilloTex );
        Material squareHairMaterial = new Material( squareHairTex );
        //dilloMaterial.color.set( 1, 0, 0, 1);
        ball.setMaterial( dilloMaterial );
        Material ilt = new Material( interfaceLeftTex );
        leftInterfaceMI.setMaterial( ilt );
        //rightInterface.setMaterial( new Material( interfaceLeftTex ) );
        //rightInterface.setMaterial( ilt );

        textures.add( dilloTex );
        textures.add( interfaceLeftTex );
        textures.add( squareHairTex );

        for( Texture tex : textures ) {
            tex.init( gl );
        }

        ball.setUpdateAction( (MeshInstance target) -> {
            target.getTransform().setTranslationZ( (float) Math.sin( getTime() ) );
            //trans.setTranslationZ( -1.5f + (float) Math.cos( t ) );
        } );
        ball.setUpdateUniformAction( cud.uniformUpdater );
        leftInterfaceMI.setUpdateUniformAction( cud.uniformUpdater );
        //rightInterface.setUpdateUniformAction( uniformUpdater );
        overlayMI.setUpdateUniformAction( cud.uniformUpdater );

        meshes.add( ballMesh );
        meshes.add( leftInterface );
        //meshes.add( rightInterface );
        mainMeshBatch.getNonAlphaBlendedMeshInstances().add( ball );
        mainMeshBatch.getAlphaBlendedMeshInstances().add( leftInterfaceMI );
        //mainMeshBatch.getAlphaBlendedMeshInstances().add( rightInterface );
        //mainMeshBatch.getNonAlphaBlendedMeshInstances().add( leftInterface );
        noiseMeshBatch.getAlphaBlendedMeshInstances().add( overlayMI );
        overlayMeshBatch.getAlphaBlendedMeshInstances().add( overlayMI );

        float aspectRatio = initialSettings.getAspectRatio();
        //gl.glPolygonMode( OpenGLUE.Constants.GL_FRONT_AND_BACK, OpenGLUE.Constants.GL_LINE ); // DEBUG
        gl.glCullFace( OpenGLUE.Constants.GL_BACK );
        gl.glEnable( OpenGLUE.Constants.GL_CULL_FACE );
        gl.glEnable( OpenGLUE.Constants.GL_DEPTH_TEST );
        //gl.glEnable( GL.GL_BLEND ); // per-mesh now
        gl.glBlendFunc( OpenGLUE.Constants.GL_SRC_ALPHA, OpenGLUE.Constants.GL_ONE_MINUS_SRC_ALPHA );
        //projectionMatrix.setToPerspective( 0.1f, 100f, 67, aspectRatio );
        float halfSizeX = 1f, halfSizeY = 1f, sizeX = 2 * halfSizeX, sizeY = 2 * halfSizeY;
        cud.projectionMatrix.setToOrthoWindow( -halfSizeX, -halfSizeY, sizeX, sizeY, -10, 10 );
        //projectionMatrix.setToIdentity();
        cud.viewMatrix.setToIdentity();
        //modelviewMatrix.scaleZ( -1f );
        //modelviewMatrix.scaleY( -1f );
        cud.viewMatrix.setTranslationZ( 3f );

        //backgroundColor.set( 0.01f, 0.01f, 0.01f, 0.01f );
        cud.backgroundColor.set( 0.1f, 0.1f, 0.1f, 0.1f );
        cud.ambientColor.set( 0.4f, 0.4f, 0.4f, 1.0f );
        cud.lightColor.set( 1.0f, 1.0f, 1.0f, 1.0f );
        cud.lightDirUnit.set( 0.5f, 1.0f, 0.5f );
        //lightDirUnit.set( 0.1f, 5.0f, 0.1f );
        cud.lightDirUnit.normalize();

        HashSet<Uniform> //
                mainUniSet = mainMeshBatch.getPerRenderUniforms(),
                noiseUniSet = noiseMeshBatch.getPerRenderUniforms(),
                overlayUniSet = overlayMeshBatch.getPerRenderUniforms();
        for( Uniform u : cud.perRenderUniforms ) {
            mainUniSet.add( u );
            noiseUniSet.add( u );
            overlayUniSet.add( u );
        }

        mainUniSet = mainMeshBatch.getPerMeshUniforms();
        noiseUniSet = noiseMeshBatch.getPerMeshUniforms();
        overlayUniSet = overlayMeshBatch.getPerMeshUniforms();
        for( Uniform u : cud.perMeshUniforms ) {
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
        cud.mousePos.set( mouseX, mouseY );
        cud.lightDirUnit.setX( mg.getRatioX() * 2 - 1 );
        cud.lightDirUnit.setY( -( mg.getRatioY() * 2 - 1 ) );

        cud.randomX.set( MathUtils.nextFloat() );
        cud.randomY.set( MathUtils.nextFloat() );
        //float ftime = (float) ( System.currentTimeMillis() % 1000 ) / 1000;
        //System.out.println( ftime );
        cud.time.set( getTime() );

        //ball.getTransform().setToRotationZ( time.get() );
        //ball.getTransform().setToRotationTB( 0, 0, time.get() );
        //ball.getTransform().setToRotationTB( time.get(), time.get(), time.get() );
        if ( framebuffer != null ) {
            framebuffer.bind( gl );
            ball.setMaterial( dilloMaterial );
        }
        gl.glClearColor( cud.backgroundColor );
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

            gl.glClearColor( cud.backgroundColor );
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
        cud.viewportSize.set( width, height );
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
