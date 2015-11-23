#version 400
precision highp float;

in vec3 interpolatedColor;
in vec3 outPosition;

uniform float time;

out vec4 outputColor;

#define A 12.9898
#define B 78.233
#define C 43758.5453

highp float rand(vec2 co) {
    return fract( sin( mod( dot( co.xy, vec2(A,B) ), 3.14 ) ) * C );
}

void main() {
    outputColor = vec4(interpolatedColor,1);
}
