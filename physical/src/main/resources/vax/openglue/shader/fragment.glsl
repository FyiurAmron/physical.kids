/*
 * Fragment shader.
 */

#version 400

in vec3 interpolatedColor;
in vec3 outPosition;

uniform float time;

out vec4 outputColor;

highp float rand(vec2 co) {
    highp float a = 12.9898;
    highp float b = 78.233;
    highp float c = 43758.5453;
    highp float dt= dot(co.xy ,vec2(a,b));
    highp float sn= mod(dt,3.14);
    return fract(sin(sn) * c);
}

highp float noise(vec2 co) {
    highp float a = 12.9898;
    highp float b = 78.233;
    highp float c = 43758.5453;
    highp float dt= dot(co.xy ,vec2(a,b));
    highp float sn= mod(dt,3.14);
    return fract(sin(sn) * c);
}


void main() {
    //outputColor = vec4(interpolatedColor, 1);
int itime;
float nois;
    nois = 0.5*(sin(outPosition.x*outPosition.y*time)+1); // magic swirls
//nois = noise(vec2(outPosition.x,0))*noise(vec2(0,outPosition.y)); // lines pattern
itime = int(time) % 1000; // least blinks, "dynamic"
//itime = int(time) % 100;
//itime = int(time) % 10; // blinks often, kind of "static"
//nois = noise(vec2(outPosition.x,itime))*noise(vec2(itime,outPosition.y))*0.5*(sin(time)+1); // lines noise

//nois = rand(outPosition.xy*itime); // swirly noise

    outputColor = vec4(0,0,nois,1);
}
