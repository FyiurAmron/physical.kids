package vax.openglue.constants;

/**

 @author toor
 */
public interface OpenGlConstantWrapper {
    int getGlConstant ();

    static int getGlConstant ( OpenGlConstantWrapper ogcw, OpenGlConstantWrapper... ogcws ) {
        int ret = ogcw.getGlConstant();
        for( OpenGlConstantWrapper ogcw2 : ogcws ) {
            ret |= ogcw2.getGlConstant();
        }
        return ret;
    }
}
