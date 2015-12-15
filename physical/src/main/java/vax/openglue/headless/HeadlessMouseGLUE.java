package vax.openglue.headless;

import vax.openglue.MouseGLUE;

/**
 Obviously a no-op implementation.

 @author toor
 */
public class HeadlessMouseGLUE implements MouseGLUE {
    @Override
    public int getX () {
        return 0;
    }

    @Override
    public int getY () {
        return 0;
    }

    @Override
    public float getRatioX () {
        return 0;
    }

    @Override
    public float getRatioY () {
        return 0;
    }

    /*
     @Override
     public float getZ () {
     return 0;
     }
     */

    @Override
    public boolean isButtonDown ( int button ) {
        return false;
    }

}
