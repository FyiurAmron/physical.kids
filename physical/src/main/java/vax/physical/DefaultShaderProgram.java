package vax.physical;

import vax.openglue.ShaderProgram;

/**

 @author toor
 */
public class DefaultShaderProgram extends ShaderProgram {
    /*
     String vertexShaderSource = @"
     #version 140
     precision highp float;

     uniform mat4 projectionMatrix;
     uniform mat4 modelviewMatrix;
     uniform mat4 transform;
     uniform float time;
     uniform float random;

     in vec3 in_position;
     in vec3 in_normal;
     in vec2 in_uv;

     out vec3 normal;
     out vec2 uv;

     void main() {
     mat4 combined = modelviewMatrix * transform;
     normal = (transform * vec4(in_normal, 0)).xyz;
     uv = in_uv;

     gl_Position = projectionMatrix * combined * vec4(in_position, 1);
     //gl_Position.y += random; // earthquake!
     //gl_Position.y += 4 * random; // BIG earthquake!
     //gl_Position.y *= 0.5 + 0.5 * abs(sin(time+gl_Position.x+gl_Position.z)); // wavy!
     //gl_Position.y += 2 * abs(sin(time+gl_Position.x+gl_Position.z)); // wavy 2!
     }";
     */
    /*
     String fragmentShaderSource = @"
     #version 140
     precision highp float;

     uniform vec3 ambientColor;
     uniform vec3 lightColor;
     uniform vec3 lightDirUnit;
     uniform float time;
     uniform float random;

     uniform sampler2D textureSampler;

     in vec3 normal;
     in vec2 uv;
     out vec4 out_fragColor;

     void main() {
     //vec3 diffuseColor = vec3(uv,0); // UV debugging
     vec3 diffuseColor = texture(textureSampler, uv).rgb;
     //vec3 diffuseColor = texture(textureSampler, uv).rgb * vec3(uv,0) * abs(sin(time*2)); // Groovy Disco TM
     //vec3 shadedLightColor = lightColor;
     vec3 shadedLightColor = lightColor * clamp( dot( lightDirUnit, normal ), 0.0, 1.0 );
     out_fragColor = vec4( diffuseColor * clamp( shadedLightColor + ambientColor, 0.0, 1.0 ), 1.0 );
     }";
     */

    // TODO: move this to external resource
    public final static String vertexShaderSource = ""
            + "#version 140\n"
            + "precision highp float;\n"
            + "\n"
            + "uniform mat4 projectionMatrix;\n"
            + "uniform mat4 modelviewMatrix;\n"
            + "uniform mat4 transform;\n"
            + "uniform float time;\n"
            + "uniform float random;\n"
            + "\n"
            + "in vec3 in_position;\n"
            + "in vec3 in_normal;\n"
            + "in vec2 in_uv;\n"
            + "\n"
            + "out vec3 normal;\n"
            + "out vec2 uv;\n"
            + "\n"
            + "void main() {\n"
            + "    mat4 combined = modelviewMatrix * transform;\n"
            + "    normal = (transform * vec4(in_normal, 0)).xyz;\n"
            + "    uv = in_uv;\n"
            + "\n"
            + "    gl_Position = projectionMatrix * combined * vec4(in_position, 1);\n"
            + "    //gl_Position.y += random; // earthquake!\n"
            + "    //gl_Position.y += 4 * random; // BIG earthquake!\n"
            + "    //gl_Position.y *= 0.5 + 0.5 * abs(sin(time+gl_Position.x+gl_Position.z)); // wavy!\n"
            + "    //gl_Position.y += 2 * abs(sin(time+gl_Position.x+gl_Position.z)); // wavy 2!\n"
            + "}";

    public final static String fragmentShaderSource = ""
            + "#version 140\n"
            + "precision highp float;\n"
            + "\n"
            + "uniform vec3 ambientColor;\n"
            + "uniform vec3 lightColor;\n"
            + "uniform vec3 lightDirUnit;\n"
            + "uniform float time;\n"
            + "uniform float random;\n"
            + "\n"
            + "uniform sampler2D textureSampler;\n"
            + "\n"
            + "in vec3 normal;\n"
            + "in vec2 uv;\n"
            + "out vec4 out_fragColor;\n"
            + "\n"
            + "void main() {\n"
            + "    //vec3 diffuseColor = vec3(uv,0); // UV debugging\n"
            + "    vec3 diffuseColor = texture(textureSampler, uv).rgb;\n"
            + "    //vec3 diffuseColor = texture(textureSampler, uv).rgb * vec3(uv,0) * abs(sin(time*2)); // Groovy Disco TM\n"
            + "    //vec3 shadedLightColor = lightColor;\n"
            + "    vec3 shadedLightColor = lightColor * clamp( dot( lightDirUnit, normal ), 0.0, 1.0 );\n"
            + "    out_fragColor = vec4( diffuseColor * clamp( shadedLightColor + ambientColor, 0.0, 1.0 ), 1.0 );\n"
            + "}";

    public DefaultShaderProgram () {
        super( vertexShaderSource, fragmentShaderSource );
    }
}
