package com.pux0r3.bionicbeth.movement;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector3;
import com.pux0r3.bionicbeth.physics.PhysicsComponent;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by pux19 on 1/12/2016.
 */
public class MovementSystemTest {
	@Test
	public void testLeftMovesLeft() throws Exception {
		Engine engine = new Engine();
		MovementSystem movementSystem = new MovementSystem();
		engine.addSystem(movementSystem);

		Entity testEntity = new Entity();
		PhysicsComponent physicsComponent = new PhysicsComponent();
		BasicMovementComponent movementComponent = new BasicMovementComponent();
		testEntity.add(physicsComponent);
		testEntity.add(movementComponent);
		engine.addEntity(testEntity);

		movementComponent.setMoveLeft(true);
		engine.update(1.f);

		Vector3 velocity = new Vector3();
		physicsComponent.getVelocity(velocity);
		assertEquals(velocity.x, 1.f, Math.ulp(1.f));
	}

	@Test
	public void testLeftMovesRight() throws Exception {
		Engine engine = new Engine();
		MovementSystem movementSystem = new MovementSystem();
		engine.addSystem(movementSystem);

		Entity testEntity = new Entity();
		PhysicsComponent physicsComponent = new PhysicsComponent();
		BasicMovementComponent movementComponent = new BasicMovementComponent();
		testEntity.add(physicsComponent);
		testEntity.add(movementComponent);
		engine.addEntity(testEntity);

		movementComponent.setMoveRight(true);
		engine.update(1.f);

		Vector3 velocity = new Vector3();
		physicsComponent.getVelocity(velocity);
		assertEquals(velocity.x, -1.f, Math.ulp(1.f));
	}
}