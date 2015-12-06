package vax.openglue.lwjgl;

import org.lwjgl.glfw.*;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryUtil;

import vax.openglue.EventListenerGL;
import vax.openglue.WindowGLUE;

/**

 @author toor
 */
public class LwjglWindowGLUE implements WindowGLUE {
    private final LwjglUe3 ue = new LwjglUe3();
    private boolean debug;

    private EventListenerGL cvel;
    private WindowGLUE.Settings settings;

    private GLFWErrorCallback errorCallback;
    private GLFWKeyCallback keyCallback;

    private long windowHandle;

    public LwjglWindowGLUE ( EventListenerGL cvel, WindowGLUE.Settings settings ) {
        this( cvel, settings, true );
    }

    public LwjglWindowGLUE ( EventListenerGL cvel, WindowGLUE.Settings settings, boolean debug ) {
        this.cvel = cvel;
        this.settings = settings;

        try {
            init();
            loop();

            // Release window and window callbacks
            GLFW.glfwDestroyWindow( windowHandle );
            keyCallback.release();
        } finally {
            // Terminate GLFW and release the GLFWErrorCallback
            GLFW.glfwTerminate();
            errorCallback.release();
            cvel.dispose( ue );
        }
    }

    private void init () {
        // Setup an error callback. The default implementation
        // will print the error message in System.err.

        errorCallback = GLFWErrorCallback.createPrint( System.err );
        GLFW.glfwSetErrorCallback( errorCallback );

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if ( GLFW.glfwInit() != GLFW.GLFW_TRUE ) {
            throw new IllegalStateException( "Unable to initialize GLFW" );
        }

        // Configure our windowHandle
        GLFW.glfwDefaultWindowHints(); // optional, the current windowHandle hints are already the default

        GLFW.glfwWindowHint( GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE ); // the windowHandle will stay hidden after creation

        GLFW.glfwWindowHint( GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE ); // the windowHandle will be resizable

        int width = settings.windowSize.getX(), height = settings.windowSize.getY();

        // Create the windowHandle
        windowHandle = GLFW.glfwCreateWindow( width, height, settings.title, MemoryUtil.NULL, MemoryUtil.NULL );

        if ( windowHandle == MemoryUtil.NULL ) {
            throw new RuntimeException( "Failed to create the GLFW window" );
        }

        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        keyCallback = new GLFWKeyCallback() {
            @Override
            public void invoke ( long window, int key, int scancode, int action, int mods ) {
                if ( key == GLFW.GLFW_KEY_ESCAPE && action == GLFW.GLFW_RELEASE ) {
                    GLFW.glfwSetWindowShouldClose( window, GLFW.GLFW_TRUE ); // We will detect this in our rendering loop
                }
            }
        };
        GLFW.glfwSetKeyCallback( windowHandle, keyCallback );

        // Get the resolution of the primary monitor
        GLFWVidMode vidmode = GLFW.glfwGetVideoMode( GLFW.glfwGetPrimaryMonitor() );
        // Center our windowHandle

        GLFW.glfwSetWindowPos( windowHandle, settings.windowPosition.getX(), settings.windowPosition.getY() );

        // Make the OpenGL context current
        GLFW.glfwMakeContextCurrent( windowHandle );

        // Enable v-sync
        GLFW.glfwSwapInterval( 1 );

        // Make the windowHandle visible
        GLFW.glfwShowWindow( windowHandle );

        GL.createCapabilities();

        cvel.init( ue );
    }

    private void loop () {
        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();

        // Run the rendering loop until the user has attempted to close
        // the windowHandle or has pressed the ESCAPE key.
        while( GLFW.glfwWindowShouldClose( windowHandle ) == GLFW.GLFW_FALSE ) {
            cvel.render( ue );

            GLFW.glfwSwapBuffers( windowHandle ); // swap the color buffers

            // Poll for windowHandle events. The key callback above will only be
            // invoked during this call.
            GLFW.glfwPollEvents();
        }
    }

    private void _applySettings ( WindowGLUE.Settings settings ) {
        /*
         glWindow.setSize( settings.windowSize.getX(), settings.windowSize.getY() );
         glWindow.setPosition( settings.windowPosition.getX(), settings.windowPosition.getY() );
         glWindow.setUndecorated( settings.undecorated );
         glWindow.setAlwaysOnTop( settings.alwaysOnTop );
         glWindow.setFullscreen( settings.fullscreen );
         glWindow.setPointerVisible( settings.mouseVisible );
         glWindow.confinePointer( settings.mouseConfined );
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
