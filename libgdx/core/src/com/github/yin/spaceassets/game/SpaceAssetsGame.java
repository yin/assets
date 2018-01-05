package com.github.yin.spaceassets.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;

public class SpaceAssetsGame extends ApplicationAdapter {
	ModelBatch batch;
	Model model;
	ModelInstance modelInstance;
	PerspectiveCamera camera;
	
	@Override
	public void create () {
		int divs = 15;
		float d = 10.0f;
		batch = new ModelBatch();
		Material material = new Material(ColorAttribute.createAmbient(1.0f, 1.0f, 1.0f, 1.0f));
		model = new ModelBuilder().createSphere(d, d, d, divs, divs, GL20.GL_TRIANGLES, material, Usage.Position | Usage.Normal);
 		modelInstance = new ModelInstance(model);
 		camera = new PerspectiveCamera(67.0f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(10f, 10f, 10f);
        camera.lookAt(0,0,0);
        camera.near = 1f;
        camera.far = 300f;
 		camera.update();
	}

	@Override
	public void render () {
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		batch.begin(camera);
		batch.render(modelInstance);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		model.dispose();
	}
}
