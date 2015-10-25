package vax.openglue.constants;

/**

 @author toor
 */
public interface OpenGlConstantWrapper {
    int getValue ();

    static int getValue ( OpenGlConstantWrapper ogcw, OpenGlConstantWrapper... ogcws ) {
        int ret = ogcw.getValue();
        for( OpenGlConstantWrapper ogcw2 : ogcws ) {
            ret |= ogcw2.getValue();
        }
        return ret;
    }
}
