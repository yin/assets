package com.github.yin.spaceassets.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.github.yin.spaceassets.game.generators.SphereGenerator;

public class SpaceAssetsGame implements ApplicationListener {
	ModelBatch batch;
	Model model;
	ModelInstance modelInstance;
	PerspectiveCamera camera;
	private double t;
	private Model model2;
	private ModelInstance modelInstance2;
	
	@Override
	public void create () {
		int divs = 15;
		float d = 10.0f;
		batch = new ModelBatch();
		Material material = new Material(ColorAttribute.createAmbient(1.0f, 1.0f, 1.0f, 1.0f));
		model2 = new ModelBuilder().createSphere(d, d, d, divs, divs, GL20.GL_TRIANGLES, material, Usage.Position | Usage.Normal);
		SphereGenerator sphereGenerator = new SphereGenerator(3, 3, 3);

 		ModelBuilder modelBuilder = new ModelBuilder();
 		modelBuilder.begin();
 		

		MeshPartBuilder meshBuilder = modelBuilder.part("mesh", GL20.GL_TRIANGLES, Usage.Position | Usage.ColorUnpacked, material);
		sphereGenerator.generate(meshBuilder, 10.0f, 10.0f, 10.0f);

 		model = modelBuilder.end();

		modelInstance = new ModelInstance(model);
		//modelInstance2 = new ModelInstance(model2);


 		camera = new PerspectiveCamera(67.0f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(10f, 10f, 10f);
        camera.lookAt(0,0,0);
        camera.near = 1f;
        camera.far = 300f;
 		camera.update();
	}

	@Override
	public void render () {
		t += 0.033;
		camera.position.set((float) (Math.cos(t) * 10), (float) (Math.sin(t) * 10), 10.0f);
        camera.lookAt(0,0,0);

		camera.update();
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		batch.begin(camera);
		batch.render(modelInstance);
		//batch.render(modelInstance2);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		model.dispose();
	}

	@Override
	public void resize(int width, int height) {
		camera.viewportWidth = width;
		camera.viewportHeight = height;
		camera.update(true);
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}
}
