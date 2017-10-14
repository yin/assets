precision mediump float;
precision mediump int;
uniform mat4 modelViewMatrix; // optional
uniform mat4 projectionMatrix; // optional
uniform vec3 center;
uniform vec2 size;
attribute vec3 position; // vertex, not sprite center
varying vec3 vPosition;
varying vec4 vNormal;

void main() {
  vPosition = position;
  vec3 camera_f = vec3(
    modelViewMatrix[0][2],
    modelViewMatrix[1][2],
    modelViewMatrix[2][2]);
  vec3 camera_r = vec3(
    modelViewMatrix[0][0],
    modelViewMatrix[1][0],
    modelViewMatrix[2][0]);
  vec3 camera_u = vec3(
    modelViewMatrix[0][1],
    modelViewMatrix[1][1],
    modelViewMatrix[2][1]);
  vNormal = vec4(camera_f, 1.0);
  vec3 vertexPosition = center
      + camera_r * position.x * size.x
      + camera_u * position.y * size.y;
  gl_Position = projectionMatrix * modelViewMatrix * vec4( vertexPosition, 1.0 );
}
