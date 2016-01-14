package com.pux0r3.bionicbeth.movement;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Vector3;
import com.pux0r3.bionicbeth.physics.PhysicsComponent;

/**
 * Created by pux19 on 1/12/2016.
 */
public class MovementSystem extends EntitySystem {
	private ComponentMapper<PhysicsComponent> _physicsMapper;
	private ComponentMapper<BasicMovementComponent> _basicMovementMapper;
	private ImmutableArray<Entity> _entityList;

	public MovementSystem() {
		_physicsMapper = ComponentMapper.getFor(PhysicsComponent.class);
		_basicMovementMapper = ComponentMapper.getFor(BasicMovementComponent.class);
	}

	@Override
	public void addedToEngine(Engine engine) {
		_entityList = engine.getEntitiesFor(
				Family.all(PhysicsComponent.class, BasicMovementComponent.class).get());
	}

	@Override
	public void update(float deltaTime) {
		for (Entity e: _entityList) {
			PhysicsComponent physicsComponent = _physicsMapper.get(e);
			BasicMovementComponent movementComponent = _basicMovementMapper.get(e);

			Vector3 velocity = new Vector3();
			physicsComponent.getVelocity(velocity);
			if (movementComponent.getMoveLeft()) {
				velocity.x = -movementComponent.getSpeed();
			}
			else if (movementComponent.getMoveRight()) {
				velocity.x = 1.f;
			}

			physicsComponent.setVelocity(velocity);
		}
	}
}
