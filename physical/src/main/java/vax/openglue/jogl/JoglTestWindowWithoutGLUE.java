package vax.openglue.jogl;

import com.jogamp.nativewindow.WindowClosingProtocol;
import com.jogamp.newt.Display;
import com.jogamp.newt.NewtFactory;
import com.jogamp.newt.Screen;
import com.jogamp.newt.event.*;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.util.Animator;
import vax.math.Vector2i;

/**

 @author gbarbieri
 */
public class JoglTestWindowWithoutGLUE {
    private int screenIdx = 0;
    private Vector2i windowSize = new Vector2i( 800, 600 );
    private Vector2i windowPosition = new Vector2i( 50, 50 );
    private boolean undecorated = false;
    private boolean alwaysOnTop = false;
    private boolean fullscreen = false;
    private boolean mouseVisible = true;
    private boolean mouseConfined = false;

    private final GLWindow glWindow;
    private final Animator animator;
    private Boolean running;

    public JoglTestWindowWithoutGLUE () {
        Display display = NewtFactory.createDisplay( null );
        Screen screen = NewtFactory.createScreen( display, screenIdx );
        GLProfile glProfile = GLProfile.get( GLProfile.GL4 );
        GLCapabilities glCapabilities = new GLCapabilities( glProfile );
        glWindow = GLWindow.create( screen, glCapabilities );

        glWindow.setSize( windowSize.getX(), windowSize.getY() );
        glWindow.setPosition( windowPosition.getX(), windowPosition.getY() );
        glWindow.setUndecorated( undecorated );
        glWindow.setAlwaysOnTop( alwaysOnTop );
        glWindow.setFullscreen( fullscreen );
        glWindow.setPointerVisible( mouseVisible );
        glWindow.confinePointer( mouseConfined );
        glWindow.setVisible( true );

        glWindow.addGLEventListener( new JoglTestGlel() );
        glWindow.addGLEventListener( new GLEventListener() {
            @Override
            public void init ( GLAutoDrawable drawable ) {
            }

            @Override
            public void display ( GLAutoDrawable drawable ) {
                glWindow.setTitle( "JoglTest @ " + animator.getLastFPS() + " FPS" );
            }

            @Override
            public void reshape ( GLAutoDrawable drawable, int x, int y, int width, int height ) {
            }

            @Override
            public void dispose ( GLAutoDrawable drawable ) {
                animator.remove( drawable );
            }
        } );
        glWindow.addKeyListener( new KeyAdapter() {
            @Override
            public void keyPressed ( KeyEvent e ) {
                if ( e.getKeyCode() == KeyEvent.VK_ESCAPE ) {
                    running = false;
                }
            }
        } );
        running = true;

        animator = new Animator(  glWindow );
        animator.setUpdateFPSFrames( 100, null );
        animator.start();
        glWindow.setDefaultCloseOperation( WindowClosingProtocol.WindowClosingMode.DO_NOTHING_ON_CLOSE );
        glWindow.addWindowListener( new WindowAdapter() {
            @Override
            public void windowDestroyNotify ( WindowEvent e ) {
                running = false;
            }
        } );
        while( running ) {
            // do something - main game loop, etc.
            Thread.yield();
            //Thread.sleep( 1 );
        }
        animator.stop();
        glWindow.destroy();
    }
}
