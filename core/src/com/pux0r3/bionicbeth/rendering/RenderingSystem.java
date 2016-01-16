package com.pux0r3.bionicbeth.rendering;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.signals.Listener;
import com.badlogic.ashley.signals.Signal;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Affine2;
import com.pux0r3.bionicbeth.events.graphics.WindowResized;

/**
 * Created by pux19 on 12/19/2015.
 */
public class RenderingSystem extends EntitySystem {
	private final Color _backgroundColor;
	private ImmutableArray<Entity> _entityList;

	private ComponentMapper<TransformComponent> _transformMapper;
	private ComponentMapper<ImageComponent> _imageMapper;

	private ComponentMapper<OrthographicCameraComponent> _orthographicCameraMapper;

	SpriteBatch _spriteBatch;

	public RenderingSystem(Color backgroundColor, Signal<WindowResized> windowResizedSignal) {
		super();

		_backgroundColor = backgroundColor;
		_transformMapper = ComponentMapper.getFor(TransformComponent.class);
		_imageMapper = ComponentMapper.getFor(ImageComponent.class);
		_orthographicCameraMapper = ComponentMapper.getFor(OrthographicCameraComponent.class);
		_spriteBatch = new SpriteBatch();

		windowResizedSignal.add(new Listener<WindowResized>() {
			@Override
			public void receive(Signal<WindowResized> signal, WindowResized object) {
				handleWindowResized(object);
			}
		});
	}

	@Override
	public void addedToEngine(Engine engine) {
		_entityList = engine.getEntitiesFor(
				Family.all(TransformComponent.class, ImageComponent.class).get()
		);
	}

	@Override
	public void update(float deltaTime) {
		Gdx.gl.glClearColor(
				_backgroundColor.r,
				_backgroundColor.g,
				_backgroundColor.b,
				_backgroundColor.a);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		_spriteBatch.begin();
		for (Entity entity: _entityList) {
			TransformComponent transform = _transformMapper.get(entity);
			ImageComponent image = _imageMapper.get(entity);

			// TODO: I'm inefficient
			Affine2 affineTransform = new Affine2();
			affineTransform.set(transform.getTransform().getWorldTransform());
			_spriteBatch.draw(
					image.getTextureRegion(),
					image.getWidth(),
					image.getHeight(),
					affineTransform);
		}
		_spriteBatch.end();
	}

	private void handleWindowResized(WindowResized windowResized) {
//		generateMatrix(windowResized.getWidth(), windowResized.getHeight());
	}
}
