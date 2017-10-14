precision mediump float;
precision mediump int;
uniform mat4 modelViewMatrix; // optional
uniform mat4 projectionMatrix; // optional
attribute vec3 position;
attribute vec4 normal;
varying vec3 vPosition;
varying vec4 vNormal;

void main()	{
  vPosition = position;
  vNormal = normal;
  gl_Position = projectionMatrix * modelViewMatrix * vec4( position, 1.0 );
}
