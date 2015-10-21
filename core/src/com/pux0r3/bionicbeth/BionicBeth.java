package com.pux0r3.bionicbeth;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BionicBeth extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;

	Engine m_engine;
	
	@Override
	public void create () {
		m_engine = new Engine();

		batch = new SpriteBatch();
		img = new Texture("Characters/Beth.png");
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		float deltaTime = Gdx.graphics.getDeltaTime();
		m_engine.update(deltaTime);

		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();
	}
}
