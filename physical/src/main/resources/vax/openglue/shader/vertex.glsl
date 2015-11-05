/*
 * Vertex shader.
 */
#version 400

in vec3 position;
in vec3 color;

uniform mat4 modelToClipMatrix;

out vec3 interpolatedColor;
out vec3 outPosition;

void main() {
    gl_Position = modelToClipMatrix * vec4(position, 1);
    outPosition = position;

    interpolatedColor = color;
}
