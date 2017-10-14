precision mediump float;
precision mediump int;
uniform float time;
uniform float offset;
uniform vec4 color;
varying vec3 vPosition;
varying vec4 vNormal;

void main()	{
  float d = 1.0-sqrt(vPosition.x*vPosition.x + vPosition.y*vPosition.y) * 0.01;
  float a = atan(vPosition.y, vPosition.x);
  d += sin(a*20.0) * 0.02;
  d += offset;

  vec4 mixed = vec4(color.xyz, color.w*d);
  gl_FragColor = mixed;
}
