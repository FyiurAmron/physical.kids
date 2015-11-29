package vax.openglue.jogl;

import com.jogamp.newt.NewtFactory;
import com.jogamp.newt.Screen;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.*;
import com.jogamp.opengl.util.Animator;
import vax.openglue.CanvasGLUE;
import vax.openglue.WindowGLUE;

/**

 @author toor
 */
public class JoglWindowGLUE implements WindowGLUE {
    public final int screenIdx = 0; // ?
    private final GLWindow glWindow;
    private final Animator animator;
    private final JoglUe3 ue = new JoglUe3();
    public boolean debug;

    public JoglWindowGLUE ( CanvasGLUE.EventListener cvel, WindowGLUE.Settings settings ) {
        this( cvel, settings, new GLCapabilities( GLProfile.get( GLProfile.GL3 ) ), true );
    }

    public JoglWindowGLUE ( CanvasGLUE.EventListener cvel, WindowGLUE.Settings settings, GLCapabilities glCapabilities, boolean debug ) {
        this.debug = debug;
        Screen screen = NewtFactory.createScreen( NewtFactory.createDisplay( null ), screenIdx );
        glWindow = GLWindow.create( screen, glCapabilities );

        _applySettings( settings );
        glWindow.setVisible( true );

        glWindow.addGLEventListener( new GLEventListener() {
            @Override
            public void init ( GLAutoDrawable drawable ) {
                cvel.init( ue.setGl( drawable ) );
            }

            @Override
            public void display ( GLAutoDrawable drawable ) {
                cvel.display( ue.setGl( drawable ) );
                if ( debug ) {
                    glWindow.setTitle( settings.title + " @ " + animator.getLastFPS() + " FPS" );
                }
            }

            @Override
            public void reshape ( GLAutoDrawable drawable, int x, int y, int width, int height ) {
                cvel.reshape( ue.setGl( drawable ), x, y, width, height );
            }

            @Override
            public void dispose ( GLAutoDrawable drawable ) {
                cvel.dispose( ue );
                //animator.remove( drawable );
                animator.stop();
            }
        } );
        // TODO
        // maybe extend the key handler code below with a custom GLUE?
        /*
         glWindow.addKeyListener( new KeyAdapter() {
         @Override
         public void keyPressed ( KeyEvent e ) {
         if ( e.getKeyCode() == KeyEvent.VK_ESCAPE ) {
         running = false;
         }
         }
         } );
         */
        // ditto with WindowListener
        // end TODO

        animator = new Animator( glWindow );
        animator.setUpdateFPSFrames( 100, null );
        animator.start();
    }

    private void _applySettings ( WindowGLUE.Settings settings ) {
        glWindow.setSize( settings.windowSize.getX(), settings.windowSize.getY() );
        glWindow.setPosition( settings.windowPosition.getX(), settings.windowPosition.getY() );
        glWindow.setUndecorated( settings.undecorated );
        glWindow.setAlwaysOnTop( settings.alwaysOnTop );
        glWindow.setFullscreen( settings.fullscreen );
        glWindow.setPointerVisible( settings.mouseVisible );
        glWindow.confinePointer( settings.mouseConfined );
    }

    public void applySettings ( WindowGLUE.Settings settings ) {
        _applySettings( settings );
    }

    public GLWindow getGlWindow () {
        return glWindow;
    }

    public Animator getAnimator () {
        return animator;
    }

    @Override
    public JoglUe3 getOpenGLUE () {
        return ue;
    }

    @Override
    public void dispose () {
        animator.stop();
        glWindow.destroy();
    }
}
