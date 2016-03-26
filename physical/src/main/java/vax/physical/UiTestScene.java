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

    private final boolean debug = true;

    private MeshBatch mainMeshBatch, uiMeshBatch, noiseMeshBatch, overlayMeshBatch;

    private final CommonUniformDescriptor cud = new CommonUniformDescriptor();

    private final DebugGLUE debugGLUE = new DebugGLUE();

    // misc junk
    private Texture dilloTex;
    private Material dilloMaterial;
    private MeshInstance ball;
    private Framebuffer framebuffer;
    private Material framebufferMaterial;
    private final WindowGLUE.Settings initialSettings;

    private Matrix4f shmit1, shmit2, shmit3;

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
        debugGLUE.setGlue( gl ); // note: always debug in init()
        gl = debugGLUE;
        ImageGLUE imageGlue = gl.getImageGLUE();

        Matrix4f m4;

        CameraDistanceSorter cds = new CameraDistanceSorter( cud.viewMatrix );
        mainMeshBatch = new MeshBatch( "main", cds );
        noiseMeshBatch = new MeshBatch( "noise", cds );
        overlayMeshBatch = new MeshBatch( "overlay", cds );
        uiMeshBatch = new MeshBatch( "ui", cds );

        TextureData<?> dilloTD = imageGlue.readTextureData( "angry-armadillo.png", Resource.class );
        TextureData<?> leftInterfaceTD = imageGlue.readTextureData( "interface.png", Resource.class );
        TextureData<?> squareHairTD = imageGlue.readTextureData( "squarehair.png", Resource.class );

        SphereMesh ballMesh = new SphereMesh( /* 0.1f */ 0.3f, 40, 40, true );
        ///* SphereMesh */ ball = new PrismMesh( new float[]{ 0, 0, 0 }, new float[]{ -0.5f, 0, -0.5f }, new float[]{ 0.5f, 0, -0.5f }, 0.3f );
        RectangleMesh rect = new RectangleMesh( Vector3f.OZ, 2f, 2f, RectangleMesh.RECT_VT_PROTO_1 );

        /* Texture */ dilloTex = new Texture( dilloTD, TextureParameters.TRILINEAR_ANISO_CLAMP, true );
        Texture squareHairTex = new Texture( squareHairTD, TextureParameters.TRILINEAR_ANISO_CLAMP, true );
        Texture //
                interfaceLeftTex = new Texture( leftInterfaceTD, TextureParameters.TRILINEAR_ANISO_CLAMP, true );

        textures.add( dilloTex );
        textures.add( interfaceLeftTex );
        textures.add( squareHairTex );

        for( Texture tex : textures ) {
            tex.init( gl );
        }

        dilloMaterial = new Material( dilloTex );
        //dilloMaterial.color.set( 1, 0, 0, 1);
        Material //
                squareHairMaterial1 = new Material( squareHairTex, new Vector4f( 0, 1, 0, 0.5f ), 1, 0, 1 ),
                squareHairMaterial2 = new Material( squareHairTex, new Vector4f( 1, 1, 0, 0.5f ), 1, 0, 1 ),
                squareHairMaterial3 = new Material( squareHairTex, new Vector4f( 1, 0, 0, 0.5f ), 1, 0, 1 );
        Material interfaceLeftMaterial = new Material( interfaceLeftTex, new Vector4f(1,1,1,1), 1, 0, 1 );
        //rightInterface.setMaterial( new Material( interfaceLeftTex ) );
        //rightInterface.setMaterial( ilt );
        MeshInstance squareHairMI1 = new MeshInstance( rect, cud.uniformUpdater, squareHairMaterial1 );
        MeshInstance squareHairMI2 = new MeshInstance( rect, cud.uniformUpdater, squareHairMaterial2 );
        MeshInstance squareHairMI3 = new MeshInstance( rect, cud.uniformUpdater, squareHairMaterial3 );
        shmit1 = squareHairMI1.getTransform();
        shmit1.setScale( 0.25f, 0.25f, 0.25f );
        shmit1.setTranslationZ( 2 );
        shmit2 = squareHairMI2.getTransform();
        shmit2.setScale( 0.2f, 0.2f, 0.2f );
        shmit2.setTranslationZ( 3 );
        shmit3 = squareHairMI3.getTransform();
        shmit3.setScale( 0.15f, 0.15f, 0.15f );
        shmit3.setTranslationZ( 4 );

        ball = new MeshInstance( ballMesh, (MeshInstance target) -> {
            target.getTransform().setTranslationZ( (float) Math.sin( getTime() ) );
            //trans.setTranslationZ( -1.5f + (float) Math.cos( t ) );
        }, cud.uniformUpdater, dilloMaterial );
        ball.getTransform().setTranslationX( -0.5f );

        MeshInstance leftInterfaceMI = new MeshInstance( rect, cud.uniformUpdater, interfaceLeftMaterial );
        m4 = leftInterfaceMI.getTransform();
        m4.setTranslationX( -0.5f );
        m4.setScale( 0.5f, 1, 0.5f );

        MeshInstance overlayMI = new MeshInstance( rect, cud.uniformUpdater, null );
        m4 = overlayMI.getTransform();
        //overlay.getTransform().setTranslationX( -2f );
        m4.setTranslationY( 1f );
        m4.setElement( Matrix4f.SCALE_X, 2 );
        m4.setElement( Matrix4f.SCALE_Y, 2 );
        //rightInterface.setUpdateUniformAction( uniformUpdater );

        meshes.add( ballMesh );
        meshes.add( rect );
        //meshes.add( rightInterface );
        mainMeshBatch.addNonAlphaBlendedMeshInstances( ball );
        mainMeshBatch.addAlphaBlendedMeshInstances();
        //mainMeshBatch.getAlphaBlendedMeshInstances().add( rightInterface );
        //mainMeshBatch.getNonAlphaBlendedMeshInstances().add( leftInterface );
        noiseMeshBatch.addAlphaBlendedMeshInstances( overlayMI );
        overlayMeshBatch.addAlphaBlendedMeshInstances( overlayMI );
        uiMeshBatch.addAlphaBlendedMeshInstances( leftInterfaceMI, squareHairMI1, squareHairMI2, squareHairMI3 );

        float aspectRatio = initialSettings.getAspectRatio();
        //gl.glPolygonMode( OpenGLUE.Constants.GL_FRONT_AND_BACK, OpenGLUE.Constants.GL_LINE ); // DEBUG
        gl.glCullFace( OpenGLUE.Constants.GL_BACK );
        gl.glEnable( OpenGLUE.Constants.GL_CULL_FACE );
        gl.glEnable( OpenGLUE.Constants.GL_DEPTH_TEST );
        //gl.glEnable( GL.GL_BLEND ); // per-mesh now
        gl.glBlendFunc( OpenGLUE.Constants.GL_SRC_ALPHA, OpenGLUE.Constants.GL_ONE_MINUS_SRC_ALPHA );
        //projectionMatrix.setToPerspective( 0.1f, 100f, 67, aspectRatio );
        float halfSizeX = 1f, halfSizeY = 1f, sizeX = 2 * halfSizeX, sizeY = 2 * halfSizeY;
        cud.projectionMatrix.setToOrthoWindow( -halfSizeX, -halfSizeY, sizeX, sizeY, 10, -10 );
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
                overlayUniSet = overlayMeshBatch.getPerRenderUniforms(),
                uiUniSet = uiMeshBatch.getPerRenderUniforms();
        for( Uniform u : cud.perRenderUniforms ) {
            mainUniSet.add( u );
            noiseUniSet.add( u );
            overlayUniSet.add( u );
            uiUniSet.add( u );
        }

        mainUniSet = mainMeshBatch.getPerMeshUniforms();
        noiseUniSet = noiseMeshBatch.getPerMeshUniforms();
        overlayUniSet = overlayMeshBatch.getPerMeshUniforms();
        uiUniSet = uiMeshBatch.getPerMeshUniforms();
        for( Uniform u : cud.perMeshUniforms ) {
            mainUniSet.add( u );
            noiseUniSet.add( u );
            overlayUniSet.add( u );
            uiUniSet.add( u );
        }

        mainMeshBatch.init( gl );
        noiseMeshBatch.init( gl );
        overlayMeshBatch.init( gl );
        uiMeshBatch.init( gl );

        /*
         Vector2i windowSize = initialSettings.windowSize;
         framebuffer = new Framebuffer( windowSize.getX(), windowSize.getY() );
         framebuffer.init( gl );
         framebufferMaterial = new Material(framebuffer.getTexture() );
         */
    }

    private void weightedAvgXY ( float newWeight, Matrix4f target, float newX, float newY ) {
        float //
                curX = target.getTranslationX(),
                curY = target.getTranslationY();
        target.setTranslationX( ( 1 - newWeight ) * curX + newWeight * newX );
        target.setTranslationY( ( 1 - newWeight ) * curY + newWeight * newY );
    }

    @Override
    public void render ( OpenGLUE gl ) {
        if ( debug ) {
            debugGLUE.setGlue( gl );
            gl = debugGLUE;
        }

        MouseGLUE mg = Main.wg.getMouseGLUE();
        int mouseX = mg.getX(), mouseY = mg.getY();
        float mouseRatioX = mg.getRatioX(), mouseRatioY = mg.getRatioY();

        float targetX = mouseRatioX * 2 - 1;
        float targetY = -( mouseRatioY * 2 - 1 );
        weightedAvgXY( 0.3f, shmit1, targetX, targetY );
        weightedAvgXY( 0.5f, shmit2, targetX, targetY );
        weightedAvgXY( 0.7f, shmit3, targetX, targetY );

        cud.mousePos.set( mouseX, mouseY );
        cud.lightDirUnit.setX( mouseRatioX * 2 - 1 );
        cud.lightDirUnit.setY( -( mouseRatioY * 2 - 1 ) );

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
        gl.glClear( ClearBufferMask.DepthBufferBit );
        uiMeshBatch.render( gl );
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
