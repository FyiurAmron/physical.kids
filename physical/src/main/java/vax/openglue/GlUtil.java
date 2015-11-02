package vax.openglue;

import java.nio.ByteBuffer;

/**

 @author toor
 */
public class GlUtil {
    public static final int DEFAULT_LOG_BUFFER_SIZE = 4096;

    private ByteBuffer logBuffer;
    private final String[] singleStringArr = new String[1];
    private final int[] singleIntArr = new int[1];

    public ByteBuffer getLogBuffer () {
        return logBuffer;
    }

    public void setLogBuffer ( ByteBuffer logBuffer ) {
        this.logBuffer = logBuffer;
    }

    public String[] getSingleStringArray () {
        return singleStringArr;
    }

    public int[] getSingleIntArray () {
        return singleIntArr;
    }

    public String[] toArray ( String string ) {
        singleStringArr[0] = string;
        return singleStringArr;
    }

    public int[] toArray( int i ) {
        singleIntArr[0] = i;
        return singleIntArr;
    }

}
