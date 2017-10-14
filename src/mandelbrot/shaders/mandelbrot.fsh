#version 400 core

in vec3 color;

out vec4 out_Color;

uniform vec2 screen;
uniform vec3 offset;
uniform vec3 julia;

vec3 hueToRgb(in float hue);

void main(void){
	vec2 fragment = gl_FragCoord.xy / screen;
	
	double scale = offset.z;
	
	int max = 500;
	
	double a = fragment.x * (2 * scale) - scale;
	double b = fragment.y * (2 * scale) - scale;
	dvec2 c;

    c.x = a + offset.x;
    c.y = b + offset.y;

    int i;
    dvec2 z = c;
    
    for(i = 0; i < max; i++) {
    	double x;
    	double y;
   		if(julia.z > 0){
        	x = (z.x * z.x - z.y * z.y) + c.x;
        	y = (z.y * z.x + z.x * z.y) + c.y;
    	}else{
        	x = (z.x * z.x - z.y * z.y) + julia.x;
        	y = (z.y * z.x + z.x * z.y) + julia.y;
    	}

        if((x * x + y * y) > 4.0) break;
        z.x = x;
        z.y = y;
    }
    
    float bright = float(i) / max;
    
    if(bright > 0.5){
    	bright = 1 - bright;
    }
    
	vec3 color = hueToRgb(1 - bright);
	
    gl_FragColor = vec4(color * bright, 1.0);
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