/*
 * Fragment shader.
 */

#version 400

// Incoming interpolated (between vertices) color.
in vec3 interpolatedColor;
in vec3 outPosition;

// Outgoing final color.
out vec4 outputColor;

void main() {
    // We simply pad the interpolatedColor
    //outputColor = vec4(interpolatedColor, 1);
    outputColor = vec4(outPosition.z,outPosition.z,outPosition.z,1);
}
