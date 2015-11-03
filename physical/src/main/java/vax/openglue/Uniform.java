package vax.openglue;

import vax.math.*;

public class Uniform {
    public static U1f from ( String name, Value1f value ) {
        return new U1f( name, value );
    }

    public static U2f from ( String name, Vector2f value ) {
        return new U2f( name, value );
    }

    public static U3f from ( String name, Vector3f value ) {
        return new U3f( name, value );
    }

    public static U4f from ( String name, Vector4f value ) {
        return new U4f( name, value );
    }

    public static UMatrix4f from ( String name, Matrix4f value ) {
        return new UMatrix4f( name, value );
    }

    private Uniform () {
        throw new UnsupportedOperationException();
    }

    protected interface IUniform {
        void updateGl ( OpenGlUe gl, int uniformLocation );

        String getName ();

        String getPrefix ();
    }

    public static abstract class UBase<T> implements IUniform {
        protected T value;
        protected String name;

        public UBase ( String name, T value ) {
            this.value = value;
            this.name = name;
        }

        @Override
        public String getName () {
            return name;
        }

        @Override
        abstract public void updateGl ( OpenGlUe gl, int uniformLocation );

        @Override
        abstract public String getPrefix ();

        @Override
        public String toString () {
            return getPrefix() + " " + getName() + "[" + value + "]";
        }

        public String toShaderString () {
            return "uniform " + getPrefix() + " " + getName() + ";\n";
        }
    }

    public static class U1f extends UBase<Value1f> {
        public U1f ( String name, Value1f value ) {
            super( name, value );
        }

        @Override
        public void updateGl ( OpenGlUe gl, int uniformLocation ) {
            gl.glUniform1f( uniformLocation, value.getValue() );
        }

        @Override
        public String getPrefix () {
            return "float";
        }
    }

    public static class U2f extends UBase<Vector2f> {
        public U2f ( String name, Vector2f value ) {
            super( name, value );
        }

        @Override
        public void updateGl ( OpenGlUe gl, int uniformLocation ) {
            gl.glUniform2fv( uniformLocation,value.getData() );
        }

        @Override
        public String getPrefix () {
            return "vec2";
        }
    }

    public static class U3f extends UBase<Vector3f> {
        public U3f ( String name, Vector3f value ) {
            super( name, value );
        }

        @Override
        public void updateGl ( OpenGlUe gl, int uniformLocation ) {
            gl.glUniform3fv( uniformLocation, value.getData() );
        }

        @Override
        public String getPrefix () {
            return "vec3";
        }
    }

    public static class U4f extends UBase<Vector4f> {
        public U4f ( String name, Vector4f value ) {
            super( name, value );
        }

        @Override
        public void updateGl ( OpenGlUe gl, int uniformLocation ) {
            gl.glUniform4fv( uniformLocation, value.getData() );
        }

        @Override
        public String getPrefix () {
            return "vec4";
        }
    }

    public static class UMatrix4f extends UBase<Matrix4f> {
        public UMatrix4f ( String name, Matrix4f value ) {
            super( name, value );
        }

        @Override
        public void updateGl ( OpenGlUe gl, int uniformLocation ) {
            gl.glUniformMatrix4fv( uniformLocation, value.getData() );
        }

        @Override
        public String getPrefix () {
            return "mat4";
        }
    }
}
