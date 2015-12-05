package vax.openglue;

import vax.math.*;

/**

 @author toor
 */
public interface Uniform {
    void updateGl ( OpenGLUE gl, int uniformLocation );

    String getName ();

    String getPrefix ();

    String toShaderString ();

    static U1f from ( String name, Value1f value ) {
        return new AbstractUniform.U1f( name, value );
    }

    static U2f from ( String name, Vector2f value ) {
        return new AbstractUniform.U2f( name, value );
    }

    static U3f from ( String name, Vector3f value ) {
        return new AbstractUniform.U3f( name, value );
    }

    static U4f from ( String name, Vector4f value ) {
        return new AbstractUniform.U4f( name, value );
    }

    static UMatrix4f from ( String name, Matrix4f value ) {
        return new AbstractUniform.UMatrix4f( name, value );
    }

    abstract class AbstractUniform<T> implements Uniform {
        protected T value;
        protected String name;

        public AbstractUniform ( String name, T value ) {
            this.value = value;
            this.name = name;
        }

        @Override
        public String getName () {
            return name;
        }

        @Override
        abstract public void updateGl ( OpenGLUE gl, int uniformLocation );

        @Override
        abstract public String getPrefix ();

        @Override
        public String toString () {
            return getPrefix() + " " + getName() + "( " + value + " )";
        }

        @Override
        public String toShaderString () {
            return "uniform " + getPrefix() + " " + getName() + ";\n";
        }
    }

    class U1f extends AbstractUniform<Value1f> {
        public U1f ( String name, Value1f value ) {
            super( name, value );
        }

        @Override
        public void updateGl ( OpenGLUE gl, int uniformLocation ) {
            gl.glUniform1f( uniformLocation, value.getValue() );
        }

        @Override
        public String getPrefix () {
            return "float";
        }
    }

    class U2f extends AbstractUniform<Vector2f> {
        public U2f ( String name, Vector2f value ) {
            super( name, value );
        }

        @Override
        public void updateGl ( OpenGLUE gl, int uniformLocation ) {
            gl.glUniform2fv( uniformLocation, value.getData() );
        }

        @Override
        public String getPrefix () {
            return "vec2";
        }
    }

    class U3f extends AbstractUniform<Vector3f> {
        public U3f ( String name, Vector3f value ) {
            super( name, value );
        }

        @Override
        public void updateGl ( OpenGLUE gl, int uniformLocation ) {
            gl.glUniform3fv( uniformLocation, value.getData() );
        }

        @Override
        public String getPrefix () {
            return "vec3";
        }
    }

    class U4f extends AbstractUniform<Vector4f> {
        public U4f ( String name, Vector4f value ) {
            super( name, value );
        }

        @Override
        public void updateGl ( OpenGLUE gl, int uniformLocation ) {
            gl.glUniform4fv( uniformLocation, value.getData() );
        }

        @Override
        public String getPrefix () {
            return "vec4";
        }
    }

    class UMatrix4f extends AbstractUniform<Matrix4f> {
        public UMatrix4f ( String name, Matrix4f value ) {
            super( name, value );
        }

        @Override
        public void updateGl ( OpenGLUE gl, int uniformLocation ) {
            gl.glUniformMatrix4fv( uniformLocation, value.getData() );
        }

        @Override
        public String getPrefix () {
            return "mat4";
        }
    }

}
