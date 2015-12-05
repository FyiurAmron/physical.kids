package vax.openglue;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

/**

 @author toor
 */
public class GLUtils {
    private ByteBuffer logBuffer;
    private IntBuffer logBufferSizeBuffer;
    private final String[] singleStringArr = new String[1];
    private final int[] singleIntArr = new int[1];

    public GLUtils () {
    }

    public ByteBuffer getLogBuffer () {
        return logBuffer;
    }

    public void setLogBuffer ( ByteBuffer logBuffer ) {
        this.logBuffer = logBuffer;
    }

    public IntBuffer getLogBufferSizeBuffer () {
        return logBufferSizeBuffer;
    }

    public void setLogBufferSizeBuffer ( IntBuffer logBufferSizeBuffer ) {
        this.logBufferSizeBuffer = logBufferSizeBuffer;
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

    public int[] toArray ( int i ) {
        singleIntArr[0] = i;
        return singleIntArr;
    }
}
