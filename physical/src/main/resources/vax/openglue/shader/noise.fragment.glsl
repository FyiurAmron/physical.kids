#version 140
precision highp float;

uniform vec4 ambientColor;
uniform vec4 lightColor;
uniform vec3 lightDirUnit;
uniform float time;
uniform float random;
uniform ivec2 viewportSize;

uniform sampler2D textureSampler;

in vec3 normal;
in vec2 uv;
out vec4 out_fragColor;

highp float noise(float x, float y) {
    highp float a = 12.9898;
    highp float b = 78.233;
    highp float c = 43758.5453;
    highp float dt= dot(vec2(x,y) ,vec2(a,b));
    highp float sn= mod(dt,3.14);
    return fract(sin(sn) * c);
}

const float alphaRatio = 16;

void main() {
    float noiseLevel = noise( gl_FragCoord.x/viewportSize.x * time, gl_FragCoord.y/viewportSize.y );
    float alpha = alphaRatio * (random*4+1) * ( noise( gl_FragCoord.x/viewportSize.x , gl_FragCoord.y/viewportSize.y* time) - 1 ) + 1;
    //out_fragColor = vec4( noiseLevel, noiseLevel, noiseLevel, alpha);
    out_fragColor = vec4( noiseLevel * random, noiseLevel, noiseLevel * random, alpha);
}

