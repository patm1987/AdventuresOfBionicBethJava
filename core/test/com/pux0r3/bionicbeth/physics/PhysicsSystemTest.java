package com.pux0r3.bionicbeth.physics;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector3;
import com.pux0r3.bionicbeth.physics.collision.CollisionSphereComponent;
import com.pux0r3.bionicbeth.rendering.Transform;
import com.pux0r3.bionicbeth.rendering.TransformComponent;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by pux19 on 3/17/2016.
 */
public class PhysicsSystemTest {
	@Test
	public void testAppliesVelocity() throws Exception {
		Engine engine = new Engine();
		PhysicsSystem physicsSystem = new PhysicsSystem();
		engine.addSystem(physicsSystem);

		Entity entity = new Entity();
		TransformComponent transformComponent = new TransformComponent(new Transform());
		entity.add(transformComponent);
		PhysicsComponent physicsComponent = new PhysicsComponent();
		entity.add(physicsComponent);
		engine.addEntity(entity);

		physicsComponent.setVelocity(new Vector3(1.f, 1.f, 1.f));
		engine.update(1.f);

		Vector3 position = new Vector3();
		transformComponent.getTransform().getWorldPosition(position);
		Assert.assertEquals(new Vector3(1.f, 1.f, 1.f), position);
	}

	@Test
	public void testUpdatesVelocityFromAcceleration() throws Exception {
		Engine engine = new Engine();
		PhysicsSystem physicsSystem = new PhysicsSystem();
		engine.addSystem(physicsSystem);

		Entity entity = new Entity();
		TransformComponent transformComponent = new TransformComponent(new Transform());
		entity.add(transformComponent);
		PhysicsComponent physicsComponent = new PhysicsComponent();
		entity.add(physicsComponent);
		engine.addEntity(entity);

		physicsComponent.setAcceleration(new Vector3(1.f, 1.f, 1.f));
		engine.update(1.f);

		Vector3 velocity = new Vector3();
		physicsComponent.getVelocity(velocity);
		Assert.assertEquals(new Vector3(1.f, 1.f, 1.f), velocity);
	}


	// todo: test detect sphere - sphere overlap

	@Test
	public void testDetectsSphereSphereOverlap() throws Exception {
		Engine engine = new Engine();
		PhysicsSystem physicsSystem = new PhysicsSystem();
		engine.addSystem(physicsSystem);

		Entity entity0 = new Entity();
		TransformComponent transformComponent0 = new TransformComponent(new Transform());
		entity0.add(transformComponent0);
		PhysicsComponent physicsComponent0 = new PhysicsComponent();
		entity0.add(physicsComponent0);
		CollisionSphereComponent collisionSphereComponent0 = new CollisionSphereComponent();
		entity0.add(collisionSphereComponent0);

		Entity entity1 = new Entity();
		TransformComponent transformComponent1 = new TransformComponent(new Transform());
		entity0.add(transformComponent1);
		PhysicsComponent physicsComponent1 = new PhysicsComponent();
		entity0.add(physicsComponent1);
		CollisionSphereComponent collisionSphereComponent1 = new CollisionSphereComponent();
		entity0.add(collisionSphereComponent1);

		// Todo: set x position so not on top of each other
		// todo set sphere radius
		// todo: detect collision
	}


	// todo: test line - line overlap
	// todo: test sphere - line overlap
}