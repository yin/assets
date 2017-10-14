function Star(temp_k, radius_sol) {
  this.temperature = temp_k;
  this.radius = radius_sol;
  this.corona = new Corona(this);
  var starMaterial = new THREE.RawShaderMaterial({
    uniforms: {
      time: {
        value: 1.0
      },
      color: {
        value: star_color
      }
    },
    vertexShader: document.getElementById('vs_star_surface').textContent,
    fragmentShader: document.getElementById('fs_star_surface').textContent,
  });

  THREE.Mesh.call(this,
    new THREE.SphereGeometry(this.radius, 20, 20),
    starMaterial);

  this.corona = new Corona(this);
  this.add(this.corona);

  this.setTemperature = function(temp) {
    this.temperature = temp;
    this.color = kelvinToRBG();
  }
}

function Corona(star) {
  var coronaMaterial = new THREE.RawShaderMaterial({
    uniforms: {
      time: {
        value: 1.0
      },
      offset: {
        value: star_radius / corona_radius
      },
      size: {
        value: [1.0, 1.0]
      },
      center: {
        value: star.position.toArray()
      },
      color: {
        value: star_color
      },
    },
    vertexShader: document.getElementById('vs_billboard').textContent,
    fragmentShader: document.getElementById('fs_billboard_hallo').textContent,
    side: THREE.DoubleSide,
    transparent: true
  });

  THREE.Mesh.call(this,
    new THREE.PlaneGeometry(this.radius, this.radius),
    coronaMaterial
  );

  this.parent = star;
  this.radius = 5 * star.radius;
  this.offset = star.radius;
}

function kelvinToRGB(temp, out) {
  if (!Array.isArray(out)) {
    out = [0, 0, 0]
  }

  temp = temp / 100
  var red, blue, green

  if (temp <= 66) {
    red = 255
  } else {
    red = temp - 60
    red = 329.698727466 * Math.pow(red, -0.1332047592)
    if (red < 0) {
      red = 0
    }
    if (red > 255) {
      red = 255
    }
  }

  if (temp <= 66) {
    green = temp
    green = 99.4708025861 * Math.log(green) - 161.1195681661
    if (green < 0) {
      green = 0
    }
    if (green > 255) {
      green = 255
    }
  } else {
    green = temp - 60
    green = 288.1221695283 * Math.pow(green, -0.0755148492)
    if (green < 0) {
      green = 0
    }
    if (green > 255) {
      green = 255
    }
  }

  if (temp >= 66) {
    blue = 255
  } else {
    if (temp <= 19) {
      blue = 0
    } else {
      blue = temp - 10
      blue = 138.5177312231 * Math.log(blue) - 305.0447927307
      if (blue < 0) {
        blue = 0
      }
      if (blue > 255) {
        blue = 255
      }
    }
  }

  out[0] = red / 256.0 * ((star_temp - 800.0) / 2000.0);
  out[1] = green / 256.0 * ((star_temp - 800.0) / 2000.0);
  out[2] = blue / 256.0 * ((star_temp - 800.0) / 2000.0);
  out[3] = 1.0;
  return out
}
