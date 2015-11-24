package vax.physical;

import vax.openglue.WindowGLUE;
import vax.openglue.mesh.SphereMesh;

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
        //new vax.openglue.jogl.JoglTestWindowWithoutGLUE();
        SceneManager sm = new vax.physical.SceneManager( "new" );
        sm.addMesh( new SphereMesh( 1, 6, 6, true ) );
        new vax.openglue.jogl.JoglWindowGLUE( sm, new WindowGLUE.Settings( APP_NAME, 800, 600, 100, 100, false, false, false, true, false ) );
    }

}
