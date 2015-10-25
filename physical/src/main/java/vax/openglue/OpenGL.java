package vax.openglue;

/**

 @author toor
 */
public interface OpenGL {

    void glEnableVertexAttribArray ( int attribNr );

    int glGenVertexArray ();

    void glBindVertexArray ( int vaoHandle );

    Integer glGetUniformLocation ( int shaderProgramHandle, String name );

    void glUniform1 ( int uniformLocation, float value );

    void glUniformMatrix4 ( int uniformLocation, int i, boolean b, float[] data );

    int glGenTexture ();

    void glUniform2 ( int uniformLocation, int i, float[] data );

    void glUniform3 ( int uniformLocation, int i, float[] data );

    void glUniform4 ( int uniformLocation, int i, float[] data );

    void glEnable ( int glParam );

    void glDisable ( int glParam );

    void glClear ( int i );

    public void glCompileShader ( int vertexShaderHandle );

    public boolean glGetShaderInfoLog ( int vertexShaderHandle );

    public int glCreateShader ( int shaderType );

    public int glCreateProgram ();

    public void glAttachShader ( int shaderProgramHandle, int vertexShaderHandle );

    public void glBindAttribLocation ( int shaderProgramHandle, int i, String attrib );

    public void glLinkProgram ( int shaderProgramHandle );

    public boolean glGetProgramInfoLog ( int shaderProgramHandle );

    public void glUseProgram ( int shaderProgramHandle );

    public void glBindTexture ( int GL_TEXTURE_2D, int handle );

    public int glGenBuffer ();

    public void glBindBuffer ( int GL_ARRAY_BUFFER, int handle );

    public void glVertexAttribPointer ( int attribNr, int size, int GL_FLOAT, int GL_TRUE, int i, int i0 );

    public void glShaderSource ( int shaderHandle, String source );

    public int glGetError ();
}
