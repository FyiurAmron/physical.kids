package vax.openglue.lwjgl;

import org.lwjgl.glfw.*;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryUtil;

import vax.math.Vector2i;
import vax.openglue.EventListenerGL;
import vax.openglue.WindowGLUE;

/**

 @author toor
 */
public class LwjglWindowGLUE implements WindowGLUE {
    private final LwjglUe3 ue = new LwjglUe3();
    private boolean debug;

    private EventListenerGL cvel;
    private WindowGLUE.Settings initialSettings;

    private GLFWErrorCallback errorCallback;
    private GLFWKeyCallback keyCallback;

    private long windowHandle;

    public LwjglWindowGLUE ( EventListenerGL cvel, WindowGLUE.Settings settings ) {
        this( cvel, settings, true );
    }

    public LwjglWindowGLUE ( EventListenerGL cvel, WindowGLUE.Settings settings, boolean debug ) {
        this.cvel = cvel;
        this.initialSettings = settings;

        try {
            init();
            loop();

            GLFW.glfwDestroyWindow( windowHandle );
            keyCallback.release();
        } finally {
            GLFW.glfwTerminate();
            errorCallback.release();
            cvel.dispose( ue );
        }
    }

    private void init () {
        errorCallback = GLFWErrorCallback.createPrint( System.err );
        GLFW.glfwSetErrorCallback( errorCallback );

        if ( GLFW.glfwInit() != GLFW.GLFW_TRUE ) {
            throw new IllegalStateException( "unable to initialize GLFW" );
        }

        GLFW.glfwDefaultWindowHints(); // optional, the current windowHandle hints are already the default
        windowHint( GLFW.GLFW_VISIBLE, false );
        windowHint( GLFW.GLFW_RESIZABLE, true );

        int width = initialSettings.windowSize.getX(), height = initialSettings.windowSize.getY();
        long monitor;
        if ( initialSettings.fullscreen ) {
            monitor = GLFW.glfwGetPrimaryMonitor();
            GLFWVidMode mode = glfwGetVideoMode( monitor );
            GLFW.glfwWindowHint( GLFW.GLFW_RED_BITS, mode.redBits() );
            GLFW.glfwWindowHint( GLFW.GLFW_GREEN_BITS, mode.greenBits() );
            GLFW.glfwWindowHint( GLFW.GLFW_BLUE_BITS, mode.blueBits() );
            GLFW.glfwWindowHint( GLFW.GLFW_REFRESH_RATE, mode.refreshRate() );
        } else {
            monitor = MemoryUtil.NULL;
        }

        windowHandle = GLFW.glfwCreateWindow( width, height, initialSettings.title, monitor, MemoryUtil.NULL );
        if ( windowHandle == MemoryUtil.NULL ) {
            throw new RuntimeException( "Failed to create the GLFW window" );
        }

        _applySettings( initialSettings );

        keyCallback = new GLFWKeyCallback() {
            @Override
            public void invoke ( long window, int key, int scancode, int action, int mods ) {
                if ( key == GLFW.GLFW_KEY_ESCAPE && action == GLFW.GLFW_RELEASE ) {
                    GLFW.glfwSetWindowShouldClose( window, GLFW.GLFW_TRUE ); // We will detect this in our rendering loop
                }
            }
        };
        GLFW.glfwSetKeyCallback( windowHandle, keyCallback );

        GLFW.glfwMakeContextCurrent( windowHandle ); // make the OpenGL context current
        GLFW.glfwSwapInterval( 1 ); // enable v-sync
        GLFW.glfwShowWindow( windowHandle ); // make the windowHandle visible

        GL.createCapabilities();

        cvel.init( ue );
    }

    private void loop () {
        GL.createCapabilities();

        while( GLFW.glfwWindowShouldClose( windowHandle ) == GLFW.GLFW_FALSE ) {
            cvel.render( ue );
            GLFW.glfwSwapBuffers( windowHandle ); // with GLFW.glfwSwapInterval(1) this call blocks until VSYNC
            GLFW.glfwPollEvents(); // key callback will only be invoked during this call
        }
    }

    private void windowHint ( int hintEnum, boolean value ) {
        GLFW.glfwWindowHint( hintEnum, value ? GLFW.GLFW_TRUE : GLFW.GLFW_FALSE );
    }

    private void _applySettings ( WindowGLUE.Settings settings ) {
        windowHint( GLFW.GLFW_DECORATED, !settings.undecorated );
        windowHint( GLFW.GLFW_FLOATING, settings.alwaysOnTop );
        if ( settings.mouseConfined && settings.mouseVisible ) {
            throw new UnsupportedOperationException( "confined & visible hardware cursor unsupported by LWJGL" );
        }
        GLFW.glfwSetInputMode( windowHandle, GLFW.GLFW_CURSOR,
                settings.mouseConfined ? GLFW.GLFW_CURSOR_DISABLED
                        : ( settings.mouseVisible ? GLFW.GLFW_CURSOR_NORMAL : GLFW.GLFW_CURSOR_HIDDEN ) );
        Vector2i dim = settings.windowPosition;
        GLFW.glfwSetWindowPos( windowHandle, dim.getX(), dim.getY() );
        dim = settings.windowSize;
        GLFW.glfwSetWindowSize( windowHandle, dim.getX(), dim.getY() );
        GLFW.glfwSetWindowTitle( windowHandle, settings.title );

        /*
         glWindow.setFullscreen( settings.fullscreen );
         */
    }

    public void applySettings ( WindowGLUE.Settings settings ) {
        _applySettings( settings );
    }

    @Override
    public LwjglUe3 getOpenGLUE () {
        return ue;
    }

    @Override
    public void dispose () {
    }
}
