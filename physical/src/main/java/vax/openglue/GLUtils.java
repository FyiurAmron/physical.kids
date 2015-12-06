package vax.openglue;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**

 @author toor
 */
public class GLUtils {
    private ByteBuffer logBuffer;
    private IntBuffer tempIntBuffer;
    private FloatBuffer tempFloatBuffer;
    private final String[] singleStringArr = new String[1];

    public GLUtils () {
    }

    public ByteBuffer getLogBuffer () {
        return logBuffer;
    }

    public void setLogBuffer ( ByteBuffer logBuffer ) {
        this.logBuffer = logBuffer;
    }

    public IntBuffer getTempIntBuffer () {
        return tempIntBuffer;
    }

    /**
     rewinds the buffer and then sets the limit

     @param limit
     @return
     */
    public IntBuffer getTempIntBuffer ( int limit ) {
        tempIntBuffer.rewind();
        tempIntBuffer.limit( limit );
        return tempIntBuffer;
    }

    public void setTempIntBuffer ( IntBuffer tempIntBuffer ) {
        this.tempIntBuffer = tempIntBuffer;
    }

    public FloatBuffer getTempFloatBuffer () {
        return tempFloatBuffer;
    }

    public FloatBuffer getTempFloatBuffer ( int limit ) {
        tempFloatBuffer.rewind();
        tempFloatBuffer.limit( limit );
        return tempFloatBuffer;
    }

    public void setTempFloatBuffer ( FloatBuffer tempFloatBuffer ) {
        this.tempFloatBuffer = tempFloatBuffer;
    }

    public String[] getSingleStringArray () {
        return singleStringArr;
    }

    public IntBuffer wrap ( int singleInt ) {
        tempIntBuffer.rewind();
        tempIntBuffer.limit( 1 );
        tempIntBuffer.put( singleInt );
        tempIntBuffer.rewind(); // limit already set
        return tempIntBuffer;
    }

    public IntBuffer wrap ( int... intArr ) {
        tempIntBuffer.rewind();
        tempIntBuffer.limit( intArr.length );
        tempIntBuffer.put( intArr );
        tempIntBuffer.rewind(); // limit already set
        return tempIntBuffer;
    }

    public FloatBuffer wrap ( float singleFloat ) {
        tempFloatBuffer.rewind();
        tempFloatBuffer.limit( 1 );
        tempFloatBuffer.put( singleFloat );
        tempFloatBuffer.rewind(); // limit already set
        return tempFloatBuffer;
    }

    public FloatBuffer wrap ( float... floatArr ) {
        tempFloatBuffer.rewind();
        tempFloatBuffer.limit( floatArr.length );
        tempFloatBuffer.put( floatArr );
        tempFloatBuffer.rewind(); // limit already set
        return tempFloatBuffer;
    }

    public String[] toArray ( String string ) {
        singleStringArr[0] = string;
        return singleStringArr;
    }
}
