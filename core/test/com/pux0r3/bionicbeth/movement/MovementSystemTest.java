package com.pux0r3.bionicbeth.movement;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector3;
import com.pux0r3.bionicbeth.physics.PhysicsComponent;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by pux19 on 1/12/2016.
 */
public class MovementSystemTest {
	private Engine _engine;
	private PhysicsComponent _physicsComponent;
	private BasicMovementComponent _movementComponent;

	@Before
	public void setUp() throws Exception {
		_engine = new Engine();
		_engine.addSystem(new MovementSystem());

		Entity testEntity = new Entity();
		_physicsComponent = new PhysicsComponent();
		testEntity.add(_physicsComponent);
		_movementComponent = new BasicMovementComponent();
		testEntity.add(_movementComponent);
		_engine.addEntity(testEntity);
	}

	@Test
	public void testLeftMovesLeft() throws Exception {
		_movementComponent.setMoveLeft(true);
		_engine.update(1.f);

		Vector3 velocity = new Vector3();
		_physicsComponent.getVelocity(velocity);
		assertEquals(velocity.x, -1.f, Math.ulp(1.f));
	}

	@Test
	public void testLeftMovesRight() throws Exception {
		_movementComponent.setMoveRight(true);
		_engine.update(1.f);

		Vector3 velocity = new Vector3();
		_physicsComponent.getVelocity(velocity);
		assertEquals(velocity.x, 1.f, Math.ulp(1.f));
	}

	@Test
	public void testAppliesSpeedLeft() throws Exception {
		float speed = 5.f;

		_movementComponent.setSpeed(speed);

		_movementComponent.setMoveLeft(true);
		_engine.update(1.f);

		Vector3 velocity = new Vector3();
		_physicsComponent.getVelocity(velocity);
		assertEquals(velocity.x, -5.f, Math.ulp(1.f));
	}

	@Test
	public void testAppliesSpeedRight() throws Exception {
		float speed = 5.f;

		_movementComponent.setSpeed(speed);

		_movementComponent.setMoveRight(true);
		_engine.update(1.f);

		Vector3 velocity = new Vector3();
		_physicsComponent.getVelocity(velocity);
		assertEquals(velocity.x, 5.f, Math.ulp(1.f));
	}

	// TODO: test active breaking when not pressing left/right
}