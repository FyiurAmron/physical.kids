package vax.util;

/**

 @author toor
 */
public class MiscUtils {
    private MiscUtils () {
        throw new UnsupportedOperationException();
    }

    public static boolean isNullOrEmpty ( String s ) {
        return s == null || s.isEmpty();
    }
}
