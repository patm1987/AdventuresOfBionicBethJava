
uniform mat4 modelView;
uniform mat4 projection;
uniform vec4 color;

attribute vec3 vertPosition;
varying vec4 vertColor;

void main(void) {
	gl_Position = projection * modelView * vec4(vertPosition, 1);
	vertColor = color;
}
