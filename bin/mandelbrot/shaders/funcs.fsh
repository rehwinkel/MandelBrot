#version 400 core

uniform vec2 screen;
uniform vec3 offset;

float rand(vec2 n){
	return fract(sin(dot(n, vec2(12.9898, 4.1414))) * 43758.5453);
}

vec3 hueToRgb(in float hue);

void main(void){
	vec2 fragment = gl_FragCoord.xy / screen;
	
	float brightx = fragment.x;
	float brighty = fragment.y;
	
	if(brightx > 0.5){
		brightx =  1 - brightx;
	}
	
	if(brighty > 0.5){
		brighty =  1 - brighty;
	}
	
	brightx *= 2;
	brighty *= 2;
	
	float col = rand(vec2(rand(fragment), rand(offset.xy)));
	
	gl_FragColor = vec4(vec3(col), 1.0);
}

vec3 hueToRgb(in float hue){
	float sixth = 1.0 / 6.0;
	
	if(hue <= sixth){
		return(vec3(1.0, hue / sixth, 0));
	}
	if(hue > sixth && hue <= 2 * sixth){
		return(vec3(1 - ((hue - sixth) / sixth), 1.0, 0));
	}
	if(hue > 2 * sixth && hue <= 3 * sixth){
		return(vec3(0, 1.0, (hue - 2 * sixth) / sixth));
	}
	if(hue > 3 * sixth && hue <= 4 * sixth){
		return(vec3(0, 1 - ((hue - 3 * sixth) / sixth), 1.0));
	}
	if(hue > 4 * sixth && hue <= 5 * sixth){
		return(vec3((hue - 4 * sixth) / sixth, 0, 1));
	}
	if(hue > 5 * sixth && hue <= 1){
		return(vec3(1, 0, 1 - ((hue - 5 * sixth) / sixth)));
	}
}