package com.pux0r3.bionicbeth;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.pux0r3.bionicbeth.physics.PhysicsComponent;
import com.pux0r3.bionicbeth.physics.PhysicsSystem;
import com.pux0r3.bionicbeth.rendering.ImageComponent;
import com.pux0r3.bionicbeth.rendering.RenderingSystem;
import com.pux0r3.bionicbeth.rendering.Transform;
import com.pux0r3.bionicbeth.rendering.TransformComponent;

public class BionicBeth extends ApplicationAdapter {

	Engine _engine;
	RenderingSystem _renderingSystem;
	PhysicsSystem _physicsSystem;
	
	@Override
	public void create () {
		_engine = new Engine();

		_renderingSystem = new RenderingSystem(new Color(Color.BLUE));
		_engine.addSystem(_renderingSystem);

		_physicsSystem = new PhysicsSystem();
		_engine.addSystem(_physicsSystem);

		Texture bethImage = new Texture("Characters/Beth.png");

		Transform bethTransform = new Transform();
		Entity bethEntity = new Entity();
		bethEntity.add(new ImageComponent(bethImage));
		bethEntity.add(new TransformComponent(bethTransform));
		PhysicsComponent physicsComponent = new PhysicsComponent();
		physicsComponent.setVelocity(new Vector3(0.f, 10.f, 0.f));
		bethEntity.add(physicsComponent);
		_engine.addEntity(bethEntity);
	}

	@Override
	public void render () {
		float deltaTime = Gdx.graphics.getDeltaTime();
		_engine.update(deltaTime);
	}
}
