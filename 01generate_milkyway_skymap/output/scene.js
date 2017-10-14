// create and set up the scene, etc
var width = window.innerWidth;
var height = window.innerHeight;
var scene = new THREE.Scene();
var camera = new THREE.PerspectiveCamera(35, width / height, 1, 150000);
var renderer = new THREE.WebGLRenderer({
  antialias: true
});
var time = 0;
var ORIGIN = new THREE.Vector3();

// urls of the images,
// one per half axis
var urls = [
  'pos-x.jpg',
  'neg-x.jpg',
  'pos-y.jpg',
  'neg-y.jpg',
  'pos-z.jpg',
  'neg-z.jpg'
];

var distance = 400;
var star_temp = 3700;
var star_radius = 60.0;
var star_color = kelvinToRGB(star_temp)
star_color[3] = 0.5;
var corona_radius = 250.0;

// wrap it up into the object that we need
var cubemap = (new THREE.CubeTextureLoader()).load(urls);

function initScene() {
  // set the format, likely RGB
  // unless you've gone crazy
  cubemap.format = THREE.RGBFormat;

  scene.background = cubemap;

  var ambient = new THREE.AmbientLight(0xffffff);
  scene.add(ambient);

  var pointLight = new THREE.PointLight(0xffffff, 2);
  scene.add(pointLight);

  scene.add(star);
  scene.add(corona);
  scene.add(camera);

  renderer.setSize(width, height);
  document.body.appendChild(renderer.domElement);

  function animate() {
    var time = performance.now();
    var star = scene.children[2];
    var corona = scene.children[3];

    star.material.uniforms.time.value = time * 0.005;

    camera.position.x = Math.sin(time * 0.0001) * distance;
    camera.position.z = Math.cos(time * 0.0001) * distance;
    camera.lookAt(ORIGIN);

    renderer.render(scene, camera);
    requestAnimationFrame(animate);
  }

  function updateDistance(d) {
    distance = d;
  }

  function updateTemperature(temp) {
    star_temp = temp;
    star_color = kelvinToRGB(star_temp)
    var star = scene.children[2];
    var corona = scene.children[3];
    star.material.uniforms.color.value = Array.from(star_color);
    star.material.uniforms.color.needsUpdate = true;
    star_color[3] = 0.5;
    corona.material.uniforms.color.value = star_color;
    corona.material.uniforms.color.needsUpdate = true
    console.log(star_color)
  }
  requestAnimationFrame(animate);
}
