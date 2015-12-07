package vax.physical;

import vax.openglue.*;
import vax.openglue.jogl.JoglWindowGLUE;
import vax.openglue.lwjgl.LwjglWindowGLUE;

/**

 @author toor
 */
public class Main {
    public static final String APP_NAME = "physical.kids";

    /**
     @param args the command line arguments
     */
    public static void main ( String[] args ) {
        // TODO code application logic here
        System.setProperty( "jogamp.common.utils.locks.Lock.timeout", "" + Integer.MAX_VALUE ); // so we're actually able to debug

        SceneManager sm = new vax.physical.SceneManager();
        /*
        WindowGLUE wg = new vax.openglue.jogl.JoglWindowGLUE( sm,
                new WindowGLUE.Settings( APP_NAME, 800, 600, 100, 100, false, false, false, true, false ) );
*/
        WindowGLUE wg = new vax.openglue.lwjgl.LwjglWindowGLUE( sm,
                new WindowGLUE.Settings( APP_NAME, 800, 600, 100, 100, false, false, false, true, false ) );

    }

}
