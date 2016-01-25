#version 140
precision highp float;

uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform mat4 modelMatrix;

uniform vec4 ambientColor;
uniform vec4 lightColor;
uniform float shininess;

uniform vec3 lightDirUnit;
uniform float time;
uniform float random;

uniform ivec2 mousePos;
uniform ivec2 viewportSize;
//in vec4 gl_FragCoord; // built-in

uniform sampler2D textureSampler;

in vec3 normal;
in vec2 uv;
in vec4 outPosition;
in vec3 raw_Position;

out vec4 out_fragColor;

void main() {
    //vec4 diffuseColor = vec4(0.8,0.6,0,1); // Gold Shader :P
    vec4 textureColor = texture(textureSampler, uv);
    textureColor = vec4(uv.x,uv.y,0,1);
    float nl = dot( lightDirUnit, normal );
    //mat4 viewInv = inverse( viewMatrix );
    //vec3 viewDir = normalize(vec3(viewInv[3] - modelMatrix * raw_Position));

    //vec4 diffuseColor = texture(textureSampler, uv) * vec4(uv,0,1) * abs(sin(time*2)); // Groovy Disco TM
    vec4 diffuseColor, specularColor;
    //if ( nl > 0 ) {
        vec3 viewDir = normalize(vec3(-viewMatrix[3] - modelMatrix * vec4(raw_Position,1)));
        diffuseColor = vec4( lightColor * clamp( nl, 0.0, 1.0 ) );
        specularColor = 0.5 * clamp( vec4(pow(max(0.0, dot(reflect(-lightDirUnit, normal), viewDir)), shininess)), 0.0, 1.0 );
/*
    } else {
        diffuseColor = vec4( 0,0,0,1 );
        specularColor = vec4( 0,0,0,1 );
    }
*/
/*
    vec4 mouseHighlight = vec4( sin( ( mousePos.x - gl_FragCoord.x ) / viewportSize.x ),
                                sin( ( mousePos.y - gl_FragCoord.y ) / viewportSize.y ), 0.0, 0.0 );
    mouseHighlight *= 0.5;
    mouseHighlight += 1;
*/
//out_fragColor = vec4(uv.x,uv.y,0,1);
//out_fragColor = textureColor;
    out_fragColor = /*mouseHighlight + */
       vec4( clamp( textureColor * clamp( diffuseColor + ambientColor, 0.0, 1.0 ) + specularColor, 0.0, 1.0 ) );
}

