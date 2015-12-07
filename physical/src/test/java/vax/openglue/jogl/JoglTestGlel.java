package vax.openglue.jogl;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.Arrays;
import com.jogamp.opengl.*;
import static com.jogamp.opengl.GL.*;
import static com.jogamp.opengl.GL2ES2.GL_FRAGMENT_SHADER;
import static com.jogamp.opengl.GL2ES2.GL_VERTEX_SHADER;
import com.jogamp.opengl.math.FloatUtil;
import com.jogamp.opengl.math.Matrix4;
import com.jogamp.opengl.util.GLBuffers;
import com.jogamp.opengl.util.PMVMatrix;
import com.jogamp.opengl.util.glsl.ShaderCode;
import com.jogamp.opengl.util.glsl.ShaderProgram;
import vax.openglue.shader.Shader;
import vax.util.MiscUtils;

// TODO change to a proper unit test
/**

 @author toor
 */
public class JoglTestGlel implements GLEventListener {

    private int[] objects = new int[Semantic.Object.SIZE];
    // Position interleaved with colors (to be normalized).
    private float[] vertexData = new float[]{
        -1, -1, 0, 1, 0, 0,
        -1, 0, 0, 0, 1, 0,
        0, -1, 0, 0, 0, 1,
        -1, 0, 0, 0, 1, 0,
        0, -1, 0, 0, 0, 1,
        0, 0, 0, 0, 0, 1, };
    private short[] indexData = new short[]{
        0, 1, 2, 3, 4, 5
    };
    private int program;
    private int modelToClipMatrixUL, timeUL;
    private final String SHADERS_ROOT = Shader.getPath();
    /**
     Use pools, you don't want to create and let them cleaned by the garbage
     collector continuously in the display() method.
     */
    private final float[] scale = new float[16];
    private final float[] zRotazion = new float[16];
    private final float[] modelToClip = new float[16];
    private long start, now;
    FloatBuffer vertexBuffer = GLBuffers.newDirectFloatBuffer( vertexData );

    @Override
    public void init ( GLAutoDrawable drawable ) {
        System.out.println( "init" );

        GL4 gl4 = drawable.getGL().getGL4();

        initVbo( gl4 );
        initIbo( gl4 );
        initVao( gl4 );
        initProgram( gl4 );

        gl4.glEnable( GL4.GL_DEPTH_TEST );

        start = System.currentTimeMillis();
    }

    @Override
    public void display ( GLAutoDrawable drawable ) {
//        System.out.println("display");
        now = System.currentTimeMillis();
        float diff = (float) ( now - start ) / 1000;

        GL4 gl4 = drawable.getGL().getGL4();

        /**
         We set the clear color and depth (although depth is not necessary since
         it is 1 by default).
         */
        //gl4.glClearColor( 0.2f, 0.2f, 0.2f, 1f );
        //float fc = 0.5f * ( FloatUtil.sin( diff ) + 1 );
        float fc = 0.0f;
        gl4.glClearColor( fc, fc, fc, fc );
        //gl4.glClearDepthf( 1f );
        gl4.glClear( GL4.GL_COLOR_BUFFER_BIT | GL4.GL_DEPTH_BUFFER_BIT );

        gl4.glUseProgram( program );
        gl4.glBindVertexArray( objects[Semantic.Object.VAO] );

        Matrix4 m4proj = new Matrix4();
        Matrix4 m4view = new Matrix4();
        m4proj.makePerspective( 67.0f / 360 * 2 * FloatUtil.PI, 800f / 600f, 1, 1000 );
        //m4proj.makeOrtho( -10f, 10f, -10f, 10f, -10f, 10f );
        //m4view.translate( FloatUtil.sin(diff*2)*3, 0, -10f );
        float sc = 5f;
        m4view.scale( sc, sc, sc );
        m4view.translate( 1, 0, -4f );

        float f = FloatUtil.sin( diff ) - 2;
        //System.out.println( now );
/*
         ByteBuffer bb = gl4.glMapBuffer( GL4.GL_ARRAY_BUFFER, GL4.GL_WRITE_ONLY );
         bb.position( 4 );
         bb.putFloat( f );
         gl4.glUnmapBuffer( GL4.GL_ARRAY_BUFFER );
         */
        m4view.rotate( diff, 1, 0, 0 );
        m4view.scale( sc, sc, sc );
        m4proj.multMatrix( m4view );
        gl4.glUniform1f( timeUL, now % 1000000 );
        gl4.glUniformMatrix4fv( modelToClipMatrixUL, 1, false, m4proj.getMatrix(), 0 );

        gl4.glDrawElements( GL4.GL_TRIANGLES, indexData.length, GL4.GL_UNSIGNED_SHORT, 0 );

        checkError( gl4, "display" );
    }

    @Override
    public void reshape ( GLAutoDrawable drawable, int x, int y, int width, int height ) {
        System.out.println( "reshape" );
        GL4 gl4 = drawable.getGL().getGL4();
        /**
         Just the glViewport for this sample, normally here you update your
         projection matrix.
         */
        gl4.glViewport( x, y, width, height );
    }

    @Override
    public void dispose ( GLAutoDrawable drawable ) {
        System.out.println( "dispose" );

        GL4 gl4 = drawable.getGL().getGL4();

        gl4.glDeleteProgram( program );
        /**
         Clean VAO first in order to minimize problems. If you delete IBO first,
         VAO will still have the IBO id, this may lead to crashes.
         */
        gl4.glDeleteVertexArrays( 1, objects, objects[Semantic.Object.VAO] );

        gl4.glDeleteBuffers( 1, objects, Semantic.Object.VBO );

        gl4.glDeleteBuffers( 1, objects, Semantic.Object.IBO );
    }

    private void initVbo ( GL4 gl4 ) {

        gl4.glGenBuffers( 1, objects, Semantic.Object.VBO );
        gl4.glBindBuffer( GL4.GL_ARRAY_BUFFER, objects[Semantic.Object.VBO] );

        gl4.glBufferData( GL4.GL_ARRAY_BUFFER, vertexData.length * GLBuffers.SIZEOF_FLOAT, vertexBuffer, GL4.GL_STREAM_DRAW );

        checkError( gl4, "initVbo" );
    }

    private void initIbo ( GL4 gl4 ) {
        gl4.glGenBuffers( 1, objects, Semantic.Object.IBO );
        gl4.glBindBuffer( GL4.GL_ELEMENT_ARRAY_BUFFER, objects[Semantic.Object.IBO] );
        ShortBuffer indexBuffer = GLBuffers.newDirectShortBuffer( indexData );
        int size = indexData.length * GLBuffers.SIZEOF_SHORT;
        gl4.glBufferData( GL4.GL_ELEMENT_ARRAY_BUFFER, size, indexBuffer, GL4.GL_STATIC_DRAW );

        checkError( gl4, "initIbo" );
    }

    private void initVao ( GL4 gl4 ) {
        /**
         Let's create the VAO and save in it all the attributes properties.
         */
        gl4.glGenVertexArrays( 1, objects, Semantic.Object.VAO );
        gl4.glBindVertexArray( objects[Semantic.Object.VAO] );
        /**
         Ibo is part of the VAO, so we need to bind it and leave it bound.
         */
        gl4.glBindBuffer( GL4.GL_ELEMENT_ARRAY_BUFFER, objects[Semantic.Object.IBO] );
        /**
         VBO is not part of VAO, we need it to bind it only when we call
         glEnableVertexAttribArray and glVertexAttribPointer, so that VAO
         knows which VBO the attributes refer to, then we can unbind it.
         */
        gl4.glBindBuffer( GL4.GL_ARRAY_BUFFER, objects[Semantic.Object.VBO] );
        int stride = ( 3 + 3 ) * GLBuffers.SIZEOF_FLOAT;
        /**
         We draw in 2D on the xy plane, so we need just two
         coordinates for the position, it will be padded to vec4 as
         (x, y, 0, 1) in the vertex shader.
         */
        gl4.glEnableVertexAttribArray( Semantic.Attr.POSITION );
        gl4.glVertexAttribPointer( Semantic.Attr.POSITION, 3, GL4.GL_FLOAT,
                false, stride, 0 * GLBuffers.SIZEOF_FLOAT );
        /**
         Color needs three coordinates. We show the usage of normalization,
         where signed value get normalized [-1, 1] like in this case.
         unsigned will get normalized in the [0, 1] instead, but take
         in account java use always signed, although you can trick it.
         Vec3 color will be padded to (x, y, z, 1) in the fragment
         shader.
         */
        gl4.glEnableVertexAttribArray( Semantic.Attr.COLOR );
        gl4.glVertexAttribPointer( Semantic.Attr.COLOR, 3, GL4.GL_FLOAT,
                true, stride, 3 * GLBuffers.SIZEOF_FLOAT );

        checkError( gl4, "initVao" );
    }

    private void initProgram ( GL4 gl4 ) {
        ShaderCode vertShader = ShaderCode.create( gl4, GL_VERTEX_SHADER, this.getClass(), SHADERS_ROOT, null, "vertex_1", "glsl", null, true );
        ShaderCode fragShader = ShaderCode.create( gl4, GL_FRAGMENT_SHADER, this.getClass(), SHADERS_ROOT, null, "fragment_1", "glsl", null, true );

        ShaderProgram shaderProgram = new ShaderProgram();
        shaderProgram.add( vertShader );
        shaderProgram.add( fragShader );

        shaderProgram.init( gl4 );

        program = shaderProgram.program();

        /**
         These links don't go into effect until you link the program. If you want
         to change index, you need to link the program again.
         */
        gl4.glBindAttribLocation( program, Semantic.Attr.POSITION, "position" );
        gl4.glBindAttribLocation( program, Semantic.Attr.COLOR, "color" );
        gl4.glBindFragDataLocation( program, Semantic.Frag.COLOR, "outputColor" );

        shaderProgram.link( gl4, System.out );

        modelToClipMatrixUL = gl4.glGetUniformLocation( program, "modelToClipMatrix" );
        timeUL = gl4.glGetUniformLocation( program, "time" );

        checkError( gl4, "initProgram" );
    }

    protected boolean checkError ( GL gl, String title ) {

        int error = gl.glGetError();
        if ( error != GL_NO_ERROR ) {
            String errorString;
            switch ( error ) {
                case GL_INVALID_ENUM:
                    errorString = "GL_INVALID_ENUM";
                    break;
                case GL_INVALID_VALUE:
                    errorString = "GL_INVALID_VALUE";
                    break;
                case GL_INVALID_OPERATION:
                    errorString = "GL_INVALID_OPERATION";
                    break;
                case GL_INVALID_FRAMEBUFFER_OPERATION:
                    errorString = "GL_INVALID_FRAMEBUFFER_OPERATION";
                    break;
                case GL_OUT_OF_MEMORY:
                    errorString = "GL_OUT_OF_MEMORY";
                    break;
                default:
                    errorString = "UNKNOWN";
                    break;
            }
            System.out.println( "OpenGL Error(" + errorString + "): " + title );
            throw new Error();
        }
        return error == GL_NO_ERROR;
    }

    public static class Semantic {

        public static class Attr {

            public static final int POSITION = 0;
            public static final int NORMAL = 1;
            public static final int COLOR = 3;
            public static final int TEXCOORD = 4;
            public static final int DRAW_ID = 5;
        }

        public static class Frag {

            public static final int COLOR = 0;
            public static final int RED = 0;
            public static final int GREEN = 1;
            public static final int BLUE = 2;
            public static final int ALPHA = 0;
        }

        public static class Uniform {

            public static final int MATERIAL = 0;
            public static final int TRANSFORM0 = 1;
            public static final int TRANSFORM1 = 2;
            public static final int INDIRECTION = 3;
            public static final int CONSTANT = 0;
            public static final int PER_FRAME = 1;
            public static final int PER_PASS = 2;
            public static final int LIGHT = 3;
            public static final int TEXTURE0 = 0;
        }

        public static class Object {

            public static final int VAO = 0;
            public static final int VBO = 1;
            public static final int IBO = 2;
            public static final int TEXTURE = 3;
            public static final int SAMPLER = 4;
            public static final int SIZE = 5;
        }
    }

}
