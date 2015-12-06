#version 140
precision highp float;

uniform vec4 ambientColor;
uniform vec4 lightColor;
uniform vec3 lightDirUnit;
uniform float time;
uniform float random;

uniform sampler2D textureSampler;

in vec3 normal;
in vec2 uv;
out vec4 out_fragColor;

void main() {
    //vec4 diffuseColor = vec4(0.8,0.6,0,1); // Gold Shader :P
    //vec4 diffuseColor = texture(textureSampler, uv);
    vec4 diffuseColor = texture(textureSampler, uv) * vec4(uv,0,1) * abs(sin(time*2)); // Groovy Disco TM
    vec4 shadedLightColor = vec4( lightColor * clamp( dot( lightDirUnit, normal ), 0.0, 1.0 ) );
    out_fragColor = vec4( diffuseColor * clamp( shadedLightColor + ambientColor, 0.0, 1.0 ) );
}
