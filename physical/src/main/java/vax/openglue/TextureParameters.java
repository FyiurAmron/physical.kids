package vax.openglue;

import java.util.Collection;
import gnu.trove.map.hash.TIntObjectHashMap;
import static vax.openglue.OpenGL.Constants.*;

/**

 @author toor
 */
public class TextureParameters {
    private static final int[] ALLOWED_PARAMS = {
        GL_TEXTURE_MIN_FILTER,
        GL_TEXTURE_MAG_FILTER,
        /*
         GL_TEXTURE_MIN_LOD,
         GL_TEXTURE_MAX_LOD,
         GL_TEXTURE_LOD_BIAS,
         */
        GL_TEXTURE_WRAP_S,
        GL_TEXTURE_WRAP_T, /*
         GL_TEXTURE_WRAP_R,
         GL_TEXTURE_SWIZZLE_R,
         GL_TEXTURE_SWIZZLE_G,
         GL_TEXTURE_SWIZZLE_B,
         GL_TEXTURE_SWIZZLE_A,
         GL_DEPTH_STENCIL_TEXTURE_MODE,
         GL_TEXTURE_BASE_LEVEL,
         GL_TEXTURE_MAX_LEVEL,
         GL_TEXTURE_COMPARE_FUNC,
         GL_TEXTURE_COMPARE_MODE,
         GL_TEXTURE_PRIORITY,
         GL_DEPTH_TEXTURE_MODE,
         */
        GL_TEXTURE_MAX_ANISOTROPY_EXT // ubiquitous extension
    //GL_GENERATE_MIPMAP // use glGenerateMipmap(targetEnum)
    };

    public static final TextureParameters DEFAULT = new TextureParameters(
            new Param( GL_TEXTURE_MIN_FILTER, GL_LINEAR ),
            new Param( GL_TEXTURE_MAG_FILTER, GL_LINEAR ),
            new Param( GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE ),
            new Param( GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE )
    );

    private final TIntObjectHashMap<ParamValue<?>> params = new TIntObjectHashMap<>( ALLOWED_PARAMS.length );

    public TextureParameters () {
    }

    public TextureParameters ( Param param ) {
        _setParameters( param );
    }

    public TextureParameters ( Param param, Param... params ) {
        _setParameters( param, params );
    }

    public TextureParameters ( Collection<Param> params ) {
        _setParameters( params );
    }

    private void _setParameters ( Param param ) {
        params.put( param.paramEnum, param.paramValue );
    }

    private void _setParameters ( Param param, Param... params ) {
        _setParameters( param );
        for( Param param2 : params ) {
            this.params.put( param2.paramEnum, param2.paramValue );
        }
    }

    private void _setParameters ( Collection<Param> params ) {
        for( Param param : params ) {
            this.params.put( param.paramEnum, param.paramValue );
        }
    }

    public void setParameters ( Param param ) {
        _setParameters( param );
    }

    public void setParameters ( Param param, Param... params ) {
        _setParameters( param, params );
    }

    public void setParameters ( Collection<Param> params ) {
        _setParameters( params );
    }

    public void setParameter ( int paramEnum, int paramValue ) {
        ParamValue<?> param = params.get( paramEnum );
        if ( param instanceof IntParamValue ) {
            ( (IntParamValue) param ).param = paramValue;
        } else {
            params.put( paramEnum, new IntParamValue( paramValue ) );
        }
    }

    public void setParameter ( int paramEnum, float paramValue ) {
        ParamValue<?> param = params.get( paramEnum );
        if ( param instanceof FloatParamValue ) {
            ( (FloatParamValue) param ).param = paramValue;
        } else {
            params.put( paramEnum, new FloatParamValue( paramValue ) );
        }
    }

    public ParamValue<?> getParameter ( int paramEnum ) {
        return params.get( paramEnum );
    }

    public void update ( OpenGLUE gl, int targetEnum ) {
        params.forEachEntry( (int paramEnum, ParamValue<?> p) -> p.update( gl, targetEnum, paramEnum ) );
    }

    @Override
    public boolean equals ( Object obj ) {
        if ( obj == null ) {
            return false;
        }
        if ( obj == this ) {
            return true;
        }
        if ( !( obj instanceof TextureParameters ) ) {
            return false;
        }
        TextureParameters tp = (TextureParameters) obj;
        return params.equals( tp.params );
    }

    @Override
    public int hashCode () {
        return params.hashCode();
    }

    // inner class elements
    //
    /**
     POJO

     */
    public static class Param {
        public int paramEnum;
        public ParamValue<?> paramValue;

        public Param ( int paramEnum, int paramValue ) {
            this.paramEnum = paramEnum;
            this.paramValue = new IntParamValue( paramValue );
        }

        public Param ( int paramEnum, float paramValue ) {
            this.paramEnum = paramEnum;
            this.paramValue = new FloatParamValue( paramValue );
        }
    }

    public abstract static class ParamValue<T> extends Number {
        abstract public boolean update ( OpenGLUE gl, int targetEnum, int paramEnum );

        abstract public T getValue ();

        @Override
        public long longValue () {
            return intValue();
        }

        @Override
        public double doubleValue () {
            return floatValue();
        }
    }

    public static class IntParamValue extends ParamValue<Integer> {
        private int param;

        private IntParamValue ( int param ) {
            this.param = param;
        }

        @Override
        public Integer getValue () {
            return param;
        }

        @Override
        public int intValue () {
            return param;
        }

        @Override
        public float floatValue () {
            return param;
        }

        @Override
        public boolean update ( OpenGLUE gl, int targetEnum, int paramEnum ) {
            gl.glTexParameteri( targetEnum, paramEnum, param );
            //return ( gl.glGetError() != ErrorCode.NoError.getValue() );
            return true;
        }
    }

    public static class FloatParamValue extends ParamValue<Float> {
        private float param;

        private FloatParamValue ( float param ) {
            this.param = param;
        }

        @Override
        public Float getValue () {
            return param;
        }

        @Override
        public int intValue () {
            return (int) param;
        }

        @Override
        public float floatValue () {
            return param;
        }

        @Override
        public boolean update ( OpenGLUE gl, int targetEnum, int paramEnum ) {
            gl.glTexParameterf( targetEnum, paramEnum, param );
            //return ( gl.glGetError() != ErrorCode.NoError.getValue() );
            return true;
        }
    }
}
