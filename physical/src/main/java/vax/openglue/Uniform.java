package vax.openglue;

import vax.math.*;

/**

 @author toor
 */
public interface Uniform {
    void updateGL ( OpenGLUE gl, int uniformLocation );

    String getName ();

    String getPrefix ();

    String toShaderString ();

    static U2i from ( String name, Vector2i value ) {
        return new U2i( name, value );
    }

    static U1f from ( String name, Value1f value ) {
        return new U1f( name, value );
    }

    static U2f from ( String name, Vector2f value ) {
        return new U2f( name, value );
    }

    static U3f from ( String name, Vector3f value ) {
        return new U3f( name, value );
    }

    static U4f from ( String name, Vector4f value ) {
        return new U4f( name, value );
    }

    static UMatrix4f from ( String name, Matrix4f value ) {
        return new UMatrix4f( name, value );
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
        abstract public void updateGL ( OpenGLUE gl, int uniformLocation );

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

    class U2i extends AbstractUniform<Vector2i> {
        public U2i ( String name, Vector2i value ) {
            super( name, value );
        }

        @Override
        public void updateGL ( OpenGLUE gl, int uniformLocation ) {
            gl.glUniform2i( uniformLocation, gl.ueGetGLUtils().wrap( value.getData() ) );
        }

        @Override
        public String getPrefix () {
            return "ivec2";
        }
    }

    class U1f extends AbstractUniform<Value1f> {
        public U1f ( String name, Value1f value ) {
            super( name, value );
        }

        @Override
        public void updateGL ( OpenGLUE gl, int uniformLocation ) {
            gl.glUniform1f( uniformLocation, gl.ueGetGLUtils().wrap( value.getValue() ) );
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
        public void updateGL ( OpenGLUE gl, int uniformLocation ) {
            gl.glUniform2f( uniformLocation, gl.ueGetGLUtils().wrap( value.getData() ) );
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
        public void updateGL ( OpenGLUE gl, int uniformLocation ) {
            gl.glUniform3f( uniformLocation, gl.ueGetGLUtils().wrap( value.getData() ) );
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
        public void updateGL ( OpenGLUE gl, int uniformLocation ) {
            gl.glUniform4f( uniformLocation, gl.ueGetGLUtils().wrap( value.getData() ) );
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
        public void updateGL ( OpenGLUE gl, int uniformLocation ) {
            gl.glUniformMatrix4f( uniformLocation, gl.ueGetGLUtils().wrap( value.getData() ) );
        }

        @Override
        public String getPrefix () {
            return "mat4";
        }
    }

}
