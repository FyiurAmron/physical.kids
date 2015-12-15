package vax.openglue.lwjgl;

import java.nio.DoubleBuffer;
import java.nio.IntBuffer;
import org.lwjgl.glfw.GLFW;
import vax.openglue.BufferGLUE;
import vax.openglue.MouseGLUE;

/**

 @author toor
 */
public class LwjglMouseGLUE implements MouseGLUE {
    private final long glfwHandle;
    private final DoubleBuffer xPos, yPos;
    private final IntBuffer xSize, ySize;

    public LwjglMouseGLUE ( BufferGLUE bufferGLUE, long glfwHandle ) {
        this.glfwHandle = glfwHandle;
        xPos = bufferGLUE.createDoubleBuffer( 1 );
        yPos = bufferGLUE.createDoubleBuffer( 1 );
        xSize = bufferGLUE.createIntBuffer( 1 );
        ySize = bufferGLUE.createIntBuffer( 1 );
    }

    private void update () {
        GLFW.glfwGetCursorPos( glfwHandle, xPos, yPos );
        GLFW.glfwGetWindowSize( glfwHandle, xSize, ySize );
    }

    @Override
    public int getX () {
        update();
        return (int) xPos.get( 0 );
    }

    @Override
    public int getY () {
        update();
        return (int) yPos.get( 0 );
    }

    @Override
    public float getRatioX () {
        update();
        return ( (float) xPos.get( 0 ) ) / xSize.get( 0 );
    }

    @Override
    public float getRatioY () {
        update();
        return ( (float) yPos.get( 0 ) ) / ySize.get( 0 );
    }

    @Override
    public boolean isButtonDown ( int button ) {
        return GLFW.glfwGetMouseButton( glfwHandle, button ) == GLFW.GLFW_PRESS;
    }
}
