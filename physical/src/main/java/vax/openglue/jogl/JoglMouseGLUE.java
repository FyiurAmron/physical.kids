package vax.openglue.jogl;

import com.jogamp.newt.event.MouseEvent;
import com.jogamp.newt.event.MouseListener;
import com.jogamp.newt.opengl.GLWindow;
import vax.openglue.MouseGLUE;

/**

 @author toor
 */
public class JoglMouseGLUE implements MouseGLUE {
    private int posX, posY, sizeX, sizeY;
    private final boolean[] buttons = new boolean[MouseEvent.BUTTON_COUNT]; // arbitrary max size

    private final MouseListener mouseListener = new MouseListener() {
        @Override
        public void mouseClicked ( MouseEvent e ) {
            updateButtonStates( e );
        }

        @Override
        public void mouseEntered ( MouseEvent e ) {
        }

        @Override
        public void mouseExited ( MouseEvent e ) {
        }

        @Override
        public void mousePressed ( MouseEvent e ) {
            updateButtonStates( e );
        }

        @Override
        public void mouseReleased ( MouseEvent e ) {
            updateButtonStates( e );
        }

        @Override
        public void mouseMoved ( MouseEvent e ) {
            updateCursorPos( e );
        }

        @Override
        public void mouseDragged ( MouseEvent e ) {
        }

        @Override
        public void mouseWheelMoved ( MouseEvent e ) {
        }
    };

    private final GLWindow glWindow;

    public JoglMouseGLUE ( GLWindow gLWindow ) {
        this.glWindow = gLWindow;
    }

    @Override
    public int getX () {
        return posX;
    }

    @Override
    public int getY () {
        return posY;
    }

    @Override
    public float getRatioX () {
        return ( (float) getX() ) / sizeX;
    }

    @Override
    public float getRatioY () {
        return ( (float) getY() ) / sizeY;
    }

    private void updateButtonStates ( MouseEvent e ) {
        for( int i = MouseEvent.BUTTON_COUNT - 1; i >= 0; i-- ) {
            buttons[i] = e.isButtonDown( i );
        }
    }

    private void updateCursorPos ( MouseEvent e ) {
        posX = e.getX();
        posY = e.getY();
        sizeX = glWindow.getWidth();
        sizeY = glWindow.getHeight();
    }

    @Override
    public boolean isButtonDown ( int button ) {
        return buttons[button];
    }

    public MouseListener getJoglMouseListener () {
        return mouseListener;
    }

}
