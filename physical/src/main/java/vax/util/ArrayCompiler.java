package vax.util;

/**

 @author poponuro
 */
abstract public class ArrayCompiler {
    protected int pos, len;

    protected ArrayCompiler ( int len ) {
        this.len = len;
    }

    public int getPos () {
        return pos;
    }

    protected void checkPos () {
        if ( pos < 0 || pos >= len ) {
            throw new IndexOutOfBoundsException( "" + pos );
        }
    }

    public void setPos ( int pos ) {
        this.pos = pos;
        checkPos();
    }

    public void movePos ( int offset ) {
        pos += offset;
        checkPos();
    }

    public static class Bytes extends ArrayCompiler {
        protected byte[] arr;
        protected boolean littleEndian;

        public Bytes ( int len ) {
            super( len );
            arr = new byte[len];
        }

        public void setEndianess ( boolean isLittleEndian ) {
            littleEndian = isLittleEndian;
        }

        public void put ( byte b ) {
            arr[pos] = b;
            pos++;
        }

        public void put ( byte b, byte... bs ) {
            arr[pos] = b;
            pos++;
            put( bs );
        }

        public void put ( byte[] v ) {
//      if ( pos + v.length > len )
//        throw new IllegalStateException( "array buffer overflow; v.length == " + v.length + " ; pos == " + pos + " ; len == " + len );
            System.arraycopy( v, 0, arr, pos, v.length );
            pos += v.length;
        }

        public void put ( long l ) {
            put( l, 8 );
        }

        public void put ( int i ) {
            put( i, 4 );
        }

        public void put ( short s ) {
            put( s, 2 );
        }

        public void put ( float f ) {
            put( Float.floatToIntBits( f ) );
        }

        public void put ( double d ) {
            put( Double.doubleToLongBits( d ) );
        }

        public void put ( long l, int bytes ) { // bytes - significant bytes (counted from right)
            int ppos = littleEndian ? pos + bytes : pos;
            for( int i = 0; i < bytes; i++, l >>= 8 ) {
                arr[ppos] = (byte) l; // cast implies & 0xFF
                if ( littleEndian ) {
                    ppos--;
                } else {
                    ppos++;
                }
            }
            pos += bytes;
        }

        public byte[] get_array () {
            return arr;
        }

        public byte[] compile () {
            if ( pos != len ) {
                throw new IllegalStateException( "the array hasn't been filled with data yet; pos == " + pos + " ; len == " + len );
            }
            return arr;
        }

        public byte[] compileTruncate () {
            if ( pos == len ) {
                return compile();
            }
            byte[] ret = new byte[pos];
            System.arraycopy( arr, 0, ret, 0, pos );
            return ret;
        }
    }

    public static class Floats extends ArrayCompiler {
        protected float[] arr;

        public Floats ( int len ) {
            super( len );
            arr = new float[len];
        }

        public void put ( float f ) {
            arr[pos] = f;
            pos++;
        }

        public void put ( float f, float... fs ) {
            arr[pos] = f;
            pos++;
            put( fs );
        }

        public void put ( float[] fs ) {
//      if ( pos + fs.length > len )
//        throw new IllegalStateException( "array buffer overflow; fs.length == " + fs.length + " ; pos == " + pos + " ; len == " + len );
            System.arraycopy( fs, 0, arr, pos, fs.length );
            pos += fs.length;
        }

        public float[] get_array () {
            return arr;
        }

        public float[] compile () {
            if ( pos != len ) {
                throw new IllegalStateException( "the array hasn't been filled with data yet; pos == " + pos + " ; len == " + len );
            }
            return arr;
        }

        public float[] compileTruncate () {
            if ( pos == len ) {
                return compile();
            }
            float[] ret = new float[pos];
            System.arraycopy( arr, 0, ret, 0, pos );
            return ret;
        }
    }
}
