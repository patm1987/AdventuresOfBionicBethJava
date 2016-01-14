package com.pux0r3.bionicbeth.input;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.pux0r3.bionicbeth.movement.BasicMovementComponent;

/**
 * Created by pux19 on 1/13/2016.
 */
public class InputSystem extends EntitySystem {
	// TODO: joystick input
	// TODO: multiple input mappings (w and -> to right)
	// TODO: custom key input mapping (w or -> to right)
	// TODO: map input to movement

	private ComponentMapper<BasicMovementComponent> _movementMapper;
	private ComponentMapper<InputComponent> _inputMapper;
	private ImmutableArray<Entity> _entityList;
	private final IKeyChecker _keyChecker;

	public InputSystem(IKeyChecker keyChecker) {
		_keyChecker = keyChecker;
		_movementMapper = ComponentMapper.getFor(BasicMovementComponent.class);
		_inputMapper = ComponentMapper.getFor(InputComponent.class);
	}

	@Override
	public void update(float deltaTime) {
		for(Entity entity: _entityList) {
			BasicMovementComponent movementComponent = _movementMapper.get(entity);
			InputComponent inputComponent = _inputMapper.get(entity);
			movementComponent.setMoveLeft(_keyChecker.isKeyPressedInSet(inputComponent.getInputMapping().LeftKeys));
		}
	}

	@Override
	public void addedToEngine(Engine engine) {
		_entityList = engine.getEntitiesFor(
				Family.all(BasicMovementComponent.class, InputComponent.class).get()
		);
	}

	public static class InputMapping {
		public int[] LeftKeys;
		public int[] RightKeys;
		public int[] JumpKeys;
	}

	public interface IKeyChecker {
		boolean isKeyPressedInSet(int[] keySet);
	}
}
