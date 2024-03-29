package vax.openglue.jogl;

import com.jogamp.newt.NewtFactory;
import com.jogamp.newt.Screen;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.*;
import com.jogamp.opengl.util.Animator;

import vax.openglue.EventListenerGL;
import vax.openglue.MouseGLUE;
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
    private JoglMouseGLUE mouseGLUE;

    public JoglWindowGLUE ( EventListenerGL cvel, WindowGLUE.Settings settings ) {
        this( cvel, settings, new GLCapabilities( GLProfile.get( GLProfile.GL3 ) ), true );
    }

    public JoglWindowGLUE ( EventListenerGL cvel, WindowGLUE.Settings settings, GLCapabilities glCapabilities, boolean debug ) {
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
                cvel.render( ue.setGl( drawable ) );
                if ( debug ) {
                    //glWindow.setTitle( settings.title + " @ " + animator.getLastFPS() + " FPS" );
                    // disabled due to https://jogamp.org/bugzilla/show_bug.cgi?id=1098
                }
            }

            @Override
            public void reshape ( GLAutoDrawable drawable, int x, int y, int width, int height ) {
                cvel.resize( ue.setGl( drawable ), x, y, width, height );
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
        // end TO_DO

        mouseGLUE = new JoglMouseGLUE( glWindow );

        glWindow.addMouseListener( mouseGLUE.getJoglMouseListener() );

        animator = new Animator( glWindow );
        animator.setUpdateFPSFrames( 100, null );
    }

    @Override
    public void start () {
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
    public MouseGLUE getMouseGLUE () {
        return mouseGLUE;
    }

    public boolean isDebug () {
        return debug;
    }

    public void setDebug ( boolean debug ) {
        this.debug = debug;
    }

    @Override
    public void dispose () {
        animator.stop();
        glWindow.destroy();
    }
}
