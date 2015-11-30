package vax.physical;

import java.util.HashMap;

import vax.physics.*;
import vax.physics.BodyManager;
import vax.math.*;
import vax.openglue.*;
import vax.openglue.mesh.*;
import vax.openglue.jogl.JoglWindowGLUE;
import vax.util.Action;

/**


 */
public class PhysicalWindow {
    Vector3f basePosition = new Vector3f();
    //MouseState lastMouseState;
    //KeyboardState lastKeyboardState;

    BodyManager bodyManager = new BodyManager();

    public static final HashMap<String, Integer> uniformMap = new HashMap<>();
    public static final String APP_NAME = "Physical";
    static final int SIZE_X = 800, SIZE_Y = 600;

    static final float BALL_RADIUS = 1.0f, JUMP_HEIGHT = 10f, MIN_RADIUS = 10f, BASE_VIEW_HEIGHT = 10f, WORLD_MOVE_FRAME_DELTA = 0.5f;

    Action<Matrix4f> cameraOnFrame;

    SphereBody squirrelBody, turtleBody, dilloBody;
    PlaneBody[] planeBodies;

    WindowGLUE.Settings settings = new WindowGLUE.Settings( APP_NAME + " v0.1", SIZE_X, SIZE_Y );
    WindowGLUE window;
    SceneManager sm;

    public PhysicalWindow () {
        sm = new SceneManager( "new" /*, settings.windowSize */);
        window = new JoglWindowGLUE( sm, settings );
    }

    public void init ( OpenGLUE gl ) {
        Texture angrySquirrelTextureData, angryTurtleTextureData, angryDilloTextureData;
        Texture[] worldTextureDatas;
        Mesh squirrelMesh, turtleMesh, dilloMesh;
        RectangleMesh[] worldMeshes;
        /*
         cameraOnFrame = (Matrix4f mvMatrix) -> {
         float radius = 40, y = 20, timeRatio = 0.5f;
         Vector3f pos = new Vector3f(
         (float) Math.sin( time.getValue() * timeRatio ) * radius,
         y,
         (float) Math.cos( time.getValue() * timeRatio ) * radius );
         mvMatrix.set( Matrix4f.lookAt( pos,
         new Vector3f( 0, y * 0.5f * ( 1.5f + (float) Math.sin( time.getValue() * timeRatio ) ), 0 ),
         new Vector3f( 0, 1, 0 ) ) );
         };
         cameraOnFrame.exec( modelviewMatrix );
         cameraOnFrame = null; // enable/disable
         */

        //texture = new texture( "E:\\drop\\logo-dark.jpg" );
        angrySquirrelTextureData = new Texture( "gfx/angry-squirrel.png" );
        angryTurtleTextureData = new Texture( "gfx/angry-turtle.png" );
        angryDilloTextureData = new Texture( "gfx/angry-armadillo.png" );

        worldTextureDatas = new Texture[]{
            new Texture( "gfx/drzewka-1.png" ),
            new Texture( "gfx/drzewka-3.png" ),
            new Texture( "gfx/sky.png" ),
            new Texture( "gfx/grass.png" ),
            new Texture( "gfx/drzewka-2.png" ),
            new Texture( "gfx/drzewka-4.png" )
        };

        squirrelMesh = new SphereMesh( BALL_RADIUS, 20, 20, true );
        squirrelMesh.texture = angrySquirrelTextureData;
        /*
         squirrelMesh.UpdateAction = ( Mesh model ) -> {
         Matrix4f transform = model.Transform;
         transform.setScaleAndRotation( Matrix4.CreateRotationZ( time.Value ) );
         //transform.setTranslationY( BALL_RADIUS + JUMP_HEIGHT * 0.5f * (1 + (float) Math.Sin( time.Value )) ); // hover
         //transform.setTranslationY( BALL_RADIUS + JUMP_HEIGHT * (float) Math.Abs( Math.Sin( time.Value ) ) ); // hover
         };
         squirrelMesh.UpdateAction( squirrelMesh );
         */
        //sm.writeOBJ();
        sm.addMesh( squirrelMesh );

        turtleMesh = new SphereMesh( BALL_RADIUS * 5, 10, 10, true );
        turtleMesh.texture = angryTurtleTextureData;
        /*
         turtleMesh.UpdateAction = ( Mesh model ) -> {
         Matrix4f transform = model.Transform;
         transform.setScaleAndRotation( Matrix4.CreateRotationX( -time.Value ) );
         };
         turtleMesh.UpdateAction( turtleMesh );
         */
        sm.addMesh( turtleMesh );

        dilloMesh = new SphereMesh( BALL_RADIUS * 2, 20, 20, true );
        dilloMesh.texture = angryDilloTextureData;
        /*
         dilloMesh.UpdateAction = ( Mesh model ) -> {
         Matrix4f transform = model.Transform;
         transform.setScaleAndRotation( Matrix4.CreateRotationX( -time.Value ) );
         };
         dilloMesh.UpdateAction( dilloMesh );
         */
        sm.addMesh( dilloMesh );

        float boxX = 100, boxY = 50, boxZ = 100, shiftX = 0.5f * boxX, shiftY = 0.5f * boxY, shiftZ = 0.5f * boxZ;
        worldMeshes = new RectangleMesh[]{
            new RectangleMesh( Vector3f.OZ, boxX, boxY ),
            new RectangleMesh( Vector3f.OZ, boxX, -boxY ),
            new RectangleMesh( Vector3f.OY, boxX, boxZ ),
            new RectangleMesh( Vector3f.OY, -boxX, boxZ ),
            new RectangleMesh( Vector3f.OZ, -boxX, boxY ),
            new RectangleMesh( Vector3f.OZ, boxX, boxY )
        };
        for( int i = worldMeshes.length - 1; i >= 0; i-- ) {
            Mesh m = worldMeshes[i];
            m.texture = worldTextureDatas[i];
            sm.addMesh( m );
        }
        Matrix4f trans;
        trans = worldMeshes[0].getTransform();
        trans.setToZero();
        trans.getData()[2] = 1;
        trans.getData()[5] = 1;
        trans.getData()[8] = 1;
        trans.getData()[15] = 1;
        trans.setTranslation( shiftX, shiftY, 0 );
        trans = worldMeshes[1].getTransform();
        trans.setToZero();
        trans.getData()[2] = -1;
        trans.getData()[5] = -1;
        trans.getData()[8] = -1;
        trans.getData()[15] = 1;
        worldMeshes[1].getTransform().setTranslation( -shiftX, shiftY, 0 );
        worldMeshes[2].getTransform().setTranslation( 0, boxY, 0 );
        worldMeshes[3].getTransform().setTranslation( 0, 0, 0 );
        worldMeshes[4].getTransform().setTranslation( 0, shiftY, shiftZ );
        worldMeshes[5].getTransform().setTranslation( 0, shiftY, -shiftZ );

        //bodyManager.Gravity.setZero();
        /* SphereBody */
        squirrelBody = new SphereBody( 1, BALL_RADIUS );
        squirrelBody.getTransform().setTranslation( 40, 40, 40 );
        //squirrelBody.setRotationSpeed( 1f );
        /* SphereBody */
        turtleBody = new SphereBody( 25, BALL_RADIUS * 5 );
        turtleBody.getTransform().setTranslation( 0, 20, 0 );
        //turtleBody.Velocity.Y = 5;
        //turtleBody.setRotationSpeed( 1f );
        /* SphereBody */
        dilloBody = new SphereBody( 25, BALL_RADIUS * 2 );
        //dilloBody.Transform.setTranslation( -20, 100, -30 );
        dilloBody.getTransform().setTranslation( 0, 30, 0 );
        //dilloBody.Velocity.Y = -5;
        //dilloBody.setRotationSpeed( 1f );
        Vector3f fixPoint = new Vector3f( 0, 42, 9 );
        /*
         // spring - vertical harmonic oscillator
         dilloBody.Forces.Add( ( Body obj ) -> {
         Vector3f disp = obj.Transform.getDisplacement( fixPoint );
         float k = 20.0f;
         disp.scale( k );
         obj.applyForce( disp );
         } );
         */

        // springy pendulum - 3D harmonic oscillator
        dilloBody.getForces().add( (Body obj) -> {
            Vector3f disp = obj.getTransform().getDisplacement( fixPoint );
            float k = 10.0f, l = 15.0f;
            disp.scale( k * ( disp.calcLength() - l ) );
            obj.applyForce( disp );
        } );

        planeBodies = new PlaneBody[]{
            new PlaneBody( new Plane3f( new Vector3f( 0, 1, 0 ), 0 ) ),
            new PlaneBody( new Plane3f( new Vector3f( 0, -1, 0 ), boxY ) ),
            new PlaneBody( new Plane3f( new Vector3f( 1, 0, 0 ), shiftX ) ),
            new PlaneBody( new Plane3f( new Vector3f( -1, 0, 0 ), shiftX ) ),
            new PlaneBody( new Plane3f( new Vector3f( 0, 0, 1 ), shiftZ ) ),
            new PlaneBody( new Plane3f( new Vector3f( 0, 0, -1 ), shiftZ ) )
        };

        for( PlaneBody pb : planeBodies ) {
            pb.setFriction( 0.1f );
            bodyManager.addBody( pb );
        }
        bodyManager.addBody( squirrelBody, squirrelMesh );
        bodyManager.addBody( turtleBody, turtleMesh );
        bodyManager.addBody( dilloBody, dilloMesh );

        sm.init( gl );
    }

    /*
     @Override
     protected void onUpdateFrame () {
     KeyboardState currentKeyboardState = OpenTK.Input.Keyboard.GetState();
     if ( currentKeyboardState[Key.Escape] ) {
     System.exit( 0 );
     }

     MouseState currentMouseState = OpenTK.Input.Mouse.GetState();
     int deltaX, deltaY, deltaZ;
     if ( currentMouseState != lastMouseState ) {
     deltaX = currentMouseState.X - lastMouseState.X;
     deltaY = currentMouseState.Y - lastMouseState.Y;
     deltaZ = currentMouseState.Wheel - lastMouseState.Wheel;
     lastMouseState = currentMouseState;
     } else {
     deltaX = 0;
     deltaY = 0;
     deltaZ = 0;
     }

     float xFactor = (float) Math.Sin( -2 * Math.PI / SIZE_X * currentMouseState.X ),
     zFactor = (float) Math.Cos( -2 * Math.PI / SIZE_X * currentMouseState.X );

     if ( currentKeyboardState[Key.W] ) {
     basePosition.X -= WORLD_MOVE_FRAME_DELTA * xFactor;
     basePosition.Z -= WORLD_MOVE_FRAME_DELTA * zFactor;
     }
     if ( currentKeyboardState[Key.S] ) {
     basePosition.X += WORLD_MOVE_FRAME_DELTA * xFactor;
     basePosition.Z += WORLD_MOVE_FRAME_DELTA * zFactor;
     }
     if ( currentKeyboardState[Key.A] ) {
     basePosition.X -= WORLD_MOVE_FRAME_DELTA * zFactor;
     basePosition.Z += WORLD_MOVE_FRAME_DELTA * xFactor;
     }
     if ( currentKeyboardState[Key.D] ) {
     basePosition.X += WORLD_MOVE_FRAME_DELTA * zFactor;
     basePosition.Z -= WORLD_MOVE_FRAME_DELTA * xFactor;
     }
     if ( currentKeyboardState[Key.LShift] ) {
     basePosition.Y += WORLD_MOVE_FRAME_DELTA;
     }
     if ( currentKeyboardState[Key.LControl] ) {
     basePosition.Y -= WORLD_MOVE_FRAME_DELTA;
     }

     if ( currentKeyboardState[Key.G] && !lastKeyboardState[Key.G] ) {
     bodyManager.Gravity.setToOpposite();
     }
     if ( currentKeyboardState[Key.R] && !lastKeyboardState[Key.R] ) {
     bodyManager.Gravity.rotate( 1 );
     }
     if ( currentKeyboardState[Key.BackSlash] && !lastKeyboardState[Key.BackSlash] ) {
     squirrelBody.setRestitution( 1 - squirrelBody.getRestitution() );
     dilloBody.setRestitution( 1 - dilloBody.getRestitution() );
     turtleBody.setRestitution( 1 - turtleBody.getRestitution() );
     }

     if ( currentKeyboardState[Key.Number1] ) {
     //squirrelBody.applyImpulse( Vector3f.getRandom( -10, 10 ) );
     squirrelBody.getVelocity().add( Vector3f.getRandom( -10, 10 ) );
     }
     if ( currentKeyboardState[Key.Number2] ) {
     //dilloBody.applyImpulse( Vector3f.getRandom( -10, 10 ) );
     dilloBody.getVelocity().add( Vector3f.getRandom( -10, 10 ) );
     }
     if ( currentKeyboardState[Key.Number3] ) {
     //turtleBody.applyImpulse( Vector3f.getRandom( -10, 10 ) );
     turtleBody.getVelocity().add( Vector3f.getRandom( -10, 10 ) );
     }

     if ( currentKeyboardState[Key.Enter] ) {
     squirrelBody.getTransform().setTranslation( 45, BALL_RADIUS, 45 );
     squirrelBody.getVelocity().set( -15, 0, -15 );
     }

     lastKeyboardState = currentKeyboardState;

     time.setValue( ( DateTime.Now.Ticks % ( 100L * 1000 * 1000 * 1000 ) ) / 1E7f );
     random.setValue( MathUtils.nextFloat() );
     //Console.WriteLine( time[0] );

     if ( cameraOnFrame != null ) {
     cameraOnFrame( modelviewMatrix );
     } else {
     float radius = currentMouseState.Wheel + MIN_RADIUS;

     Vector3f pos = new Vector3f(
     basePosition.X + xFactor * radius,
     basePosition.Y + BASE_VIEW_HEIGHT,
     basePosition.Z + zFactor * radius
     );
     //Console.WriteLine( pos );

     modelviewMatrix.set( Matrix4f.lookAt( pos,
     new Vector3f( basePosition.X, BASE_VIEW_HEIGHT + basePosition.Y - radius * currentMouseState.Y / SIZE_Y, basePosition.Z ),
     new Vector3f( 0, 1, 0 ) ) );

     //modelviewMatrix.setTranslation( deltaX, deltaY, deltaZ );
     }

     for( Mesh m : meshes ) {
     m.update();
     }

     if ( !currentKeyboardState.IsKeyDown( Key.LAlt ) ) {
     float TIME_THRESHOLD = 1f / 60f, stepTime = (float) e.getTime();
     while( stepTime > TIME_THRESHOLD ) {
     bodyManager.update( TIME_THRESHOLD );
     stepTime -= TIME_THRESHOLD;
     }
     bodyManager.update( stepTime );
     }


     //ambientColor.Y = (float) Math.Sin( time.Value );
     //Console.WriteLine( ambientColor.Y );
     }
     */
}
