#version 400 core

uniform vec2 screen;

void main(void){
	vec2 fragment = gl_FragCoord.xy / screen;
	
	vec3 color = vec3(cos(fragment.x), sin(length(fragment)), tan(fragment.y));
	
	gl_FragColor = vec4(color, 1.0);
}