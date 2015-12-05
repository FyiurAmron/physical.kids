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
    gl_Position.y *= 0.5 + 0.5 * abs(sin(time+gl_Position.x+gl_Position.z)); // wavy!
    //gl_Position.y *= 0.6 + 0.4 * abs(sin(time+gl_Position.x+gl_Position.z)); // wavy 1.1!
    //gl_Position.y += 2 * abs(sin(time+gl_Position.x+gl_Position.z)); // wavy 2!
}

