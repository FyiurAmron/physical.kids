#version 140
precision highp float;

uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform mat4 modelMatrix;

uniform float time;
uniform float random;

in vec3 in_position;
in vec3 in_normal;
in vec2 in_uv;

out vec3 normal;
out vec2 uv;
out vec4 outPosition;
out vec3 raw_Position;

void main() {
    mat4 combined = projectionMatrix * viewMatrix;
    normal = (modelMatrix * vec4(in_normal, 0)).xyz;
    uv = in_uv;

    raw_Position = in_position;
    outPosition = combined * modelMatrix * vec4(in_position, 1);
    gl_Position = outPosition;
}

