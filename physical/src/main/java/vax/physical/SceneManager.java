package vax.physical;

import java.util.ArrayList;
import java.util.HashSet;

import vax.math.*;
import vax.openglue.*;
import vax.openglue.constants.ClearBufferMask;
import vax.openglue.mesh.*;
import vax.physical.resource.Resource;
import vax.physics.BodyManager;
import vax.physics.PlaneBody;
import vax.physics.SphereBody;

/**

 @author toor
 */
public class SceneManager implements EventListenerGL {
    private final ArrayList<Mesh> meshes = new ArrayList<>();
    private final ArrayList<Texture> textures = new ArrayList<>();

    private final static float BALL_RADIUS = 1.0f, JUMP_HEIGHT = 10f, MIN_RADIUS = 10f, BASE_VIEW_HEIGHT = 10f, WORLD_MOVE_FRAME_DELTA = 0.5f;

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
    private SphereBody squirrelBody, turtleBody, dilloBody;

    private final DebugGLUE debugGLUE = new DebugGLUE();

    // misc junk
    private Texture dilloTex;
    private Material dilloMaterial;
    private Mesh ball;
    private Framebuffer framebuffer;
    private Material framebufferMaterial;
    private final WindowGLUE.Settings initialSettings;
    private final BodyManager bodyManager = new BodyManager();
    
    private Vector3f cameraPos;

    public SceneManager ( WindowGLUE.Settings initialSettings ) {
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
        //noiseMeshBatch = new MeshBatch( "noise", cds );
        //overlayMeshBatch = new MeshBatch( "overlay", cds );

        TextureData<?> dilloTD = imageGlue.readTextureData( "angry-armadillo.png", Resource.class );
        TextureData<?> squirrelTD = imageGlue.readTextureData( "angry-squirrel.png", Resource.class );
        TextureData<?> turtleTD = imageGlue.readTextureData( "angry-turtle.png", Resource.class );
        //TextureData<?> leftInterfaceTD = imageGlue.readTextureData( "interface.png", Resource.class );
        TextureData<?> grassTD = imageGlue.readTextureData( "grass.png", Resource.class );
        TextureData<?> drzewka1TD = imageGlue.readTextureData( "drzewka-1.png", Resource.class );
        TextureData<?> drzewka2TD = imageGlue.readTextureData( "drzewka-2.png", Resource.class );
        TextureData<?> drzewka3TD = imageGlue.readTextureData( "drzewka-3.png", Resource.class );
        TextureData<?> drzewka4TD = imageGlue.readTextureData( "drzewka-4.png", Resource.class );
        TextureData<?> skyTD = imageGlue.readTextureData( "sky.png", Resource.class );

        Texture dilloTex = new Texture( dilloTD, TextureParameters.TRILINEAR_CLAMP, debug );
        Texture squirrelTex = new Texture( squirrelTD, TextureParameters.TRILINEAR_CLAMP, debug );
        Texture turtleTex = new Texture( turtleTD, TextureParameters.TRILINEAR_CLAMP, debug );

        Texture grassTex = new Texture( grassTD, TextureParameters.TRILINEAR_CLAMP, debug );
        Texture drzewka1Tex = new Texture( drzewka1TD, TextureParameters.TRILINEAR_CLAMP, debug );
        Texture drzewka2Tex = new Texture( drzewka2TD, TextureParameters.TRILINEAR_CLAMP, debug );
        Texture drzewka3Tex = new Texture( drzewka3TD, TextureParameters.TRILINEAR_CLAMP, debug );
        Texture drzewka4Tex = new Texture( drzewka4TD, TextureParameters.TRILINEAR_CLAMP, debug );
        Texture skyTex = new Texture( skyTD, TextureParameters.TRILINEAR_CLAMP, debug );

        Texture[] worldTextures = new Texture[]{
            drzewka1Tex,
            drzewka3Tex,
            skyTex,
            grassTex,
            drzewka2Tex,
            drzewka4Tex, };

        float boxX = 100, boxY = 50, boxZ = 100, shiftX = 0.5f * boxX, shiftY = 0.5f * boxY, shiftZ = 0.5f * boxZ;
        RectangleMesh[] worldMeshes = new RectangleMesh[]{
            new RectangleMesh( Vector3f.OZ, boxX, boxY ),
            new RectangleMesh( Vector3f.OZ, boxX, -boxY ),
            new RectangleMesh( Vector3f.OY, boxX, boxZ ),
            new RectangleMesh( Vector3f.OY, -boxX, boxZ ),
            new RectangleMesh( Vector3f.OZ, -boxX, boxY ),
            new RectangleMesh( Vector3f.OZ, boxX, boxY )
        };

        for( int i = worldMeshes.length - 1; i >= 0; i-- ) {
            Mesh m = worldMeshes[i];
            m.setMaterial( new Material( worldTextures[i] ) );
            meshes.add( m );
        }
        Matrix4f trans;
        trans = worldMeshes[0].getTransform();
        trans.setToZero();
        trans.setValue( 1, 2 );
        trans.setValue( 1, 5 );
        trans.setValue( 1, 8 );
        trans.setValue( 1, 15 );
        trans.setTranslation( shiftX, shiftY, 0 );
        trans = worldMeshes[1].getTransform();
        trans.setToZero();
        trans.setValue( -1, 2 );
        trans.setValue( -1, 5 );
        trans.setValue( -1, 8 );
        trans.setValue( 1, 15 );
        worldMeshes[1].getTransform().setTranslation( -shiftX, shiftY, 0 );
        worldMeshes[2].getTransform().setTranslation( 0, boxY, 0 );
        worldMeshes[3].getTransform().setTranslation( 0, 0, 0 );
        worldMeshes[4].getTransform().setTranslation( 0, shiftY, shiftZ );
        worldMeshes[5].getTransform().setTranslation( 0, shiftY, -shiftZ );
        Mesh squirrelMesh, turtleMesh, dilloMesh;

        squirrelMesh = new SphereMesh( BALL_RADIUS, 20, 20, true );
        squirrelMesh.setMaterial( new Material( squirrelTex ) );
        meshes.add( squirrelMesh );

        turtleMesh = new SphereMesh( BALL_RADIUS * 5, 10, 10, true );
        turtleMesh.setMaterial( new Material( turtleTex ) );
        meshes.add( turtleMesh );
        dilloMesh = new SphereMesh( BALL_RADIUS * 2, 20, 20, true );
        dilloMesh.setMaterial( new Material( dilloTex ) );
        meshes.add( dilloMesh );

        RectangleMesh overlay = new RectangleMesh( -2f, -2f, RectangleMesh.RECT_VT_PROTO_2 );
        //overlay.getTransform().setTranslationX( -2f );
        overlay.getTransform().setTranslationY( 1f );

        Texture[] ts = new Texture[]{
            dilloTex, turtleTex, squirrelTex, skyTex, grassTex, drzewka1Tex, drzewka2Tex, drzewka3Tex, drzewka4Tex
        };

        for( Texture tex : ts ) {
            tex.init( gl );
        }

        for( Mesh m : meshes ) {
            mainMeshBatch.getNonAlphaBlendedMeshes().add( m );
            m.setUpdateAction( (Mesh target) -> {
                Matrix4f trans1 = target.getTransform();

                Material mat = target.getMaterial();
                if ( mat != null ) {
                    shininess.setValue( mat.shininess );
                }
                modelMatrix.set( trans1 );
            } );
        }

        float aspectRatio = initialSettings.getAspectRatio();
        //gl.glPolygonMode( OpenGLUE.Constants.GL_FRONT_AND_BACK, OpenGLUE.Constants.GL_LINE ); // DEBUG
        gl.glCullFace( OpenGLUE.Constants.GL_BACK );
        gl.glEnable( OpenGLUE.Constants.GL_CULL_FACE );
        gl.glEnable( OpenGLUE.Constants.GL_DEPTH_TEST );
        //gl.glEnable( GL.GL_BLEND ); // per-mesh now
        gl.glBlendFunc( OpenGLUE.Constants.GL_SRC_ALPHA, OpenGLUE.Constants.GL_ONE_MINUS_SRC_ALPHA );
        projectionMatrix.setToPerspective( 0.1f, 1000f, 67, aspectRatio );
        float halfSizeX = 1f, halfSizeY = 1f, sizeX = 2 * halfSizeX, sizeY = 2 * halfSizeY;
        //projectionMatrix.setToOrthoWindow( -halfSizeX, -halfSizeY, sizeX, sizeY, -10, 10 );
        //projectionMatrix.setToIdentity();
        viewMatrix.setToIdentity();
        //modelviewMatrix.scaleZ( -1f );
        //modelviewMatrix.scaleY( -1f );
        cameraPos = new Vector3f( -20f, -10f, -40f );
        viewMatrix.setTranslation( cameraPos );
        
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
                mainUniSet = mainMeshBatch.getPerRenderUniforms();
        for( Uniform u : perRenderUniforms ) {
            mainUniSet.add( u );
        }

        squirrelBody = new SphereBody( 1, BALL_RADIUS );
        squirrelBody.getTransform().setTranslation( 40, 40, 40 );
        /* SphereBody */
        turtleBody = new SphereBody( 25, BALL_RADIUS * 5 );
        turtleBody.getTransform().setTranslation( 0, 20, 0 );
        //turtleBody.Velocity.Y = 5;
        /* SphereBody */
        dilloBody = new SphereBody( 25, BALL_RADIUS * 2 );
        //dilloBody.Transform.setTranslation( -20, 100, -30 );
        dilloBody.getTransform().setTranslation( 0, 30, 0 );
        //dilloBody.Velocity.Y = -5;
        Vector3f fixPoint = new Vector3f( 0, 42, 9 );

        mainUniSet = mainMeshBatch.getPerMeshUniforms();
        for( Uniform u : perMeshUniforms ) {
            mainUniSet.add( u );
        }

        PlaneBody[] planeBodies = new PlaneBody[]{
            new PlaneBody( new Plane3f( new Vector3f( 0, 1, 0 ), 0 ) ),
            new PlaneBody( new Plane3f( new Vector3f( 0, -1, 0 ), boxY ) ),
            new PlaneBody( new Plane3f( new Vector3f( 1, 0, 0 ), shiftX ) ),
            new PlaneBody( new Plane3f( new Vector3f( -1, 0, 0 ), shiftX ) ),
            new PlaneBody( new Plane3f( new Vector3f( 0, 0, 1 ), shiftZ ) ),
            new PlaneBody( new Plane3f( new Vector3f( 0, 0, -1 ), shiftZ ) )
        };
        for( PlaneBody pb : planeBodies ) {
            pb.setFriction( 0.1f );
            pb.setRestitution( 0.8f );
            bodyManager.addBody( pb );
        }
        bodyManager.addBody( squirrelBody, squirrelMesh );
        bodyManager.addBody( turtleBody, turtleMesh );
        bodyManager.addBody( dilloBody, dilloMesh );
        mainMeshBatch.init( gl );
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

        if ( true ) {
            float TIME_THRESHOLD = 1f / 60f;
            float stepTime = TIME_THRESHOLD;
            /*float stepTime = time.getValue();
            while( stepTime > TIME_THRESHOLD ) {
                bodyManager.update( TIME_THRESHOLD );
                stepTime -= TIME_THRESHOLD;
            }*/
            bodyManager.update( stepTime );
        }

        //viewMatrix.setTranslationZ( viewMatrix.getTranslationZ() - 0.1f );
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
        //overlayMeshBatch.render( gl );
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
