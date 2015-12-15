package vax.openglue;

/**
 The values returned are in screen co-ords, with (0,0) in upper-left corner of the window.

 @author toor
 */
public interface MouseGLUE {
    int getX ();

    int getY ();

    float getRatioX ();

    float getRatioY ();

    /*
     @return usually the wheel position
     */
    //float getZ ();
    boolean isButtonDown ( int button );
}
