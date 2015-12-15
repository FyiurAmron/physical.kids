package vax.physical;

import vax.openglue.*;

/**

 @author toor
 */
public class Main {
    public static final String APP_NAME = "physical.kids";

    public static WindowGLUE wg;

    /**
     @param args the command line arguments
     */
    public static void main ( String[] args ) {
        // TODO code application logic here
        System.setProperty( "jogamp.common.utils.locks.Lock.timeout", "" + Integer.MAX_VALUE ); // so we're actually able to debug

        WindowGLUE.Settings settings = new WindowGLUE.Settings( APP_NAME, 800, 600, 100, 100, false, false, false, true, false );

        SceneManager sm = new vax.physical.SceneManager(settings);

        //wg = new vax.openglue.jogl.JoglWindowGLUE( sm, settings );
        wg = new vax.openglue.lwjgl.LwjglWindowGLUE( sm, settings );
        wg.start();
    }
}
