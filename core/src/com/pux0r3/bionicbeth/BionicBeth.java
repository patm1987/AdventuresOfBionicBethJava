package com.pux0r3.bionicbeth;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.pux0r3.bionicbeth.input.GdxKeyChecker;
import com.pux0r3.bionicbeth.input.InputComponent;
import com.pux0r3.bionicbeth.input.InputSystem;
import com.pux0r3.bionicbeth.movement.BasicMovementComponent;
import com.pux0r3.bionicbeth.movement.MovementSystem;
import com.pux0r3.bionicbeth.physics.PhysicsComponent;
import com.pux0r3.bionicbeth.physics.PhysicsSystem;
import com.pux0r3.bionicbeth.rendering.ImageComponent;
import com.pux0r3.bionicbeth.rendering.RenderingSystem;
import com.pux0r3.bionicbeth.rendering.Transform;
import com.pux0r3.bionicbeth.rendering.TransformComponent;
import com.sun.corba.se.impl.oa.poa.ActiveObjectMap;

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

		_engine.addSystem(new MovementSystem());
		_engine.addSystem(new InputSystem(new GdxKeyChecker()));

		Texture bethImage = new Texture("Characters/Beth.png");

		Transform bethTransform = new Transform();
		Entity bethEntity = new Entity();
		bethEntity.add(new ImageComponent(bethImage));
		bethEntity.add(new TransformComponent(bethTransform));
		bethEntity.add(new PhysicsComponent());
		bethEntity.add(new BasicMovementComponent());

		InputSystem.InputMapping inputMapping = new InputSystem.InputMapping();
		inputMapping.LeftKeys = new int[] {Input.Keys.LEFT, Input.Keys.A};
		inputMapping.RightKeys = new int[] {Input.Keys.RIGHT, Input.Keys.D};
		inputMapping.JumpKeys = new int[] {Input.Keys.SPACE};
		InputComponent inputComponent = new InputComponent();
		inputComponent.setInputMapping(inputMapping);
		bethEntity.add(inputComponent);

		_engine.addEntity(bethEntity);
	}

	@Override
	public void render () {
		float deltaTime = Gdx.graphics.getDeltaTime();
		_engine.update(deltaTime);
	}
}
