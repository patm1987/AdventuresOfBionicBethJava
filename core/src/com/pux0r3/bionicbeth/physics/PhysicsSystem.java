package com.pux0r3.bionicbeth.physics;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector3;
import com.pux0r3.bionicbeth.rendering.TransformComponent;

/**
 * Created by pux19 on 1/5/2016.
 */
public class PhysicsSystem extends EntitySystem {
	private ComponentMapper<TransformComponent> _transformMapper;
	private ComponentMapper<PhysicsComponent> _physicsMapper;
	private ImmutableArray<Entity> _entityList;

	public PhysicsSystem() {
		super();

		_transformMapper = ComponentMapper.getFor(TransformComponent.class);
		_physicsMapper = ComponentMapper.getFor(PhysicsComponent.class);
	}

	@Override
	public void addedToEngine(Engine engine) {
		_entityList = engine.getEntitiesFor(
				Family.all(TransformComponent.class, PhysicsComponent.class).get());
	}

	@Override
	public void update(float deltaTime) {
		for (Entity entity: _entityList) {
			TransformComponent transformComponent = _transformMapper.get(entity);
			PhysicsComponent physicsComponent = _physicsMapper.get(entity);

			Vector3 position = new Vector3();
			Vector3 velocity = new Vector3();
			Vector3 acceleration = new Vector3();

			physicsComponent.getVelocity(velocity);
			physicsComponent.getAcceleration(acceleration);
			acceleration.scl(deltaTime);
			velocity.add(acceleration);

			transformComponent.getTransform().getWorldPosition(position);
			Vector3 pdt = new Vector3(velocity);
			pdt.scl(deltaTime);
			position.add(pdt);

			transformComponent.getTransform().setWorldPosition(position);
			physicsComponent.setVelocity(velocity);
			physicsComponent.setAcceleration(Vector3.Zero);
		}
	}
}
