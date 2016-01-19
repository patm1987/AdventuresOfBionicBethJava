package com.pux0r3.bionicbeth;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.signals.Signal;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.pux0r3.bionicbeth.events.graphics.WindowResized;
import com.pux0r3.bionicbeth.input.GdxKeyChecker;
import com.pux0r3.bionicbeth.input.InputComponent;
import com.pux0r3.bionicbeth.input.InputSystem;
import com.pux0r3.bionicbeth.movement.BasicMovementComponent;
import com.pux0r3.bionicbeth.movement.MovementSystem;
import com.pux0r3.bionicbeth.physics.PhysicsComponent;
import com.pux0r3.bionicbeth.physics.PhysicsSystem;
import com.pux0r3.bionicbeth.rendering.*;

public class BionicBeth extends ApplicationAdapter {

	Engine _engine;
	RenderingSystem _renderingSystem;
	PhysicsSystem _physicsSystem;

	Signal<WindowResized> _windowResizedSignal;

	@Override
	public void create () {
		_windowResizedSignal = new Signal<>();

		_engine = new Engine();

		_renderingSystem = new RenderingSystem(
				new Color(Color.BLUE),
				_windowResizedSignal,
				new SpriteBatch());
		_engine.addSystem(_renderingSystem);

		_physicsSystem = new PhysicsSystem();
		_engine.addSystem(_physicsSystem);

		_engine.addSystem(new MovementSystem());
		_engine.addSystem(new InputSystem(new GdxKeyChecker()));

		Texture bethImage = new Texture("Characters/Beth.png");

		// TODO: need a scenegraph
		Transform bethTransform = new Transform();
		Entity bethEntity = new Entity();
		bethEntity.add(new ImageComponent(bethImage));
		bethEntity.add(new TransformComponent(bethTransform));
		bethEntity.add(new PhysicsComponent());

		BasicMovementComponent movementComponent = new BasicMovementComponent();
		movementComponent.setSpeed(20.f);
		bethEntity.add(movementComponent);

		InputSystem.InputMapping inputMapping = new InputSystem.InputMapping();
		inputMapping.LeftKeys = new int[] {Input.Keys.LEFT, Input.Keys.A};
		inputMapping.RightKeys = new int[] {Input.Keys.RIGHT, Input.Keys.D};
		inputMapping.JumpKeys = new int[] {Input.Keys.SPACE};
		InputComponent inputComponent = new InputComponent();
		inputComponent.setInputMapping(inputMapping);
		bethEntity.add(inputComponent);

		_engine.addEntity(bethEntity);

		Transform cameraTransform = new Transform();
		OrthographicCameraComponent cameraComponent = new OrthographicCameraComponent();
		cameraComponent.setNear(-10.f);
		cameraComponent.setFar(10.f);
		cameraComponent.setHalfHeight(320.f);
		Entity cameraEntity = new Entity();
		cameraEntity.add(new TransformComponent(cameraTransform));
		cameraEntity.add(cameraComponent);
		_engine.addEntity(cameraEntity);
		_renderingSystem.setCamera(cameraEntity);

		Transform boxTransform = new Transform();
		boxTransform.setLocalPosition(new Vector3(0.f, -10.f, 0.f));
		Entity boxEntity = new Entity();
		boxEntity.add(new TransformComponent(boxTransform));
		boxEntity.add(_renderingSystem.makeBox(10.f));
		_engine.addEntity(boxEntity);
	}

	@Override
	public void render () {
		float deltaTime = Gdx.graphics.getDeltaTime();
		_engine.update(deltaTime);
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);

		_windowResizedSignal.dispatch(new WindowResized(width, height));
	}
}
