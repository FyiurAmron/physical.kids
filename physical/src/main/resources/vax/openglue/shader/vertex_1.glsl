#version 400
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

in vec3 position;

uniform mat4 modelToClipMatrix;

out vec3 interpolatedColor;
out vec3 outPosition;

void main() {
    gl_Position = modelToClipMatrix * vec4(position, 1);
    outPosition = position;

    interpolatedColor = vec3(fract(position.x),fract(position.y),fract(position.z));
}
