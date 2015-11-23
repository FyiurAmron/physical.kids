package vax.util;

import java.io.*;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

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

    public static int[] toArray ( IntBuffer fb ) {
        int[] arr = new int[fb.limit()];
        fb.get( arr );
        fb.rewind();
        return arr;
    }

    public static float[] toArray ( FloatBuffer fb ) {
        float[] arr = new float[fb.limit()];
        fb.get( arr );
        fb.rewind();
        return arr;
    }

    public static String readResource ( Class<?> pathClass, String filename ) {
        return readFile( pathClass.getResourceAsStream( filename ) );
    }

    /*
     public static String readFile ( String filename ) {
     //return new String(Files.readAllBytes(Paths.get(file)), encoding);
     return readFile( new File( filename ) );
     }

     public static String readFile ( File file ) {
     InputStream in = new FileInputStream( file );
     }
     */
    public static String readFile ( InputStream inputStream ) {
        try {
            //DataInputStream dis = new DataInputStream( inputStream );
            int avail = inputStream.available();
            byte[] buf = new byte[avail];
            inputStream.read( buf );
            avail = inputStream.available();
            if ( avail == 0 ) {
                return new String( buf );
            }
            int total = avail;
            ArrayList<byte[]> arrList = new ArrayList<>();
            arrList.add( buf );
            while( avail > 0 ) {
                buf = new byte[avail];
                total += avail;
                inputStream.read( buf );
                arrList.add( buf );
                avail = inputStream.available();
            }
            buf = new byte[total];
            int offset = 0;
            for( byte[] bs : arrList ) {
                System.arraycopy( bs, 0, buf, offset, bs.length );
                offset += bs.length;
            }
            return new String( buf );
        } catch (IOException ex) {
            throw new RuntimeException( ex );
        }
    }
}
