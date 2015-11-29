package vax.physical;

import vax.openglue.*;
import vax.openglue.jogl.JoglWindowGLUE;

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
        SceneManager sm = new vax.physical.SceneManager( "new" );
        JoglWindowGLUE jwg = new vax.openglue.jogl.JoglWindowGLUE( sm,
                new WindowGLUE.Settings( APP_NAME, 800, 600, 100, 100, false, false, false, true, false ) );
    }

}
