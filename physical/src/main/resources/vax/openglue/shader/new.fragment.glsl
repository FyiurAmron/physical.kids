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
    //vec3 diffuseColor = texture(textureSampler, uv).rgb;
    //vec3 diffuseColor = texture(textureSampler, uv).rgb * vec3(uv,0) * abs(sin(time*2)); // Groovy Disco TM
    vec3 diffuseColor = vec3(uv,0) * abs(sin(time*2)); // Groovy Disco 2 TM
    //vec3 shadedLightColor = lightColor;
    vec3 shadedLightColor = lightColor * clamp( dot( lightDirUnit, normal ), 0.0, 1.0 );
    out_fragColor = vec4( diffuseColor * clamp( shadedLightColor + ambientColor, 0.0, 1.0 ), 1.0 );
}

