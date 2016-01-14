package com.pux0r3.bionicbeth.input;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Input;
import com.pux0r3.bionicbeth.input.InputSystem.IKeyChecker;
import com.pux0r3.bionicbeth.movement.BasicMovementComponent;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by pux19 on 1/13/2016.
 */
public class InputMappingTest {
	private Engine _engine;
	private BasicMovementComponent _movementComponent;
	private InputComponent _inputComponent;

	@Before
	public void setUp() throws Exception {
		_engine = new Engine();

		Entity testEntity = new Entity();
		_movementComponent = new BasicMovementComponent();
		_inputComponent = new InputComponent();
		testEntity.add(_movementComponent);
		testEntity.add(_inputComponent);
		_engine.addEntity(testEntity);
	}

	@Test
	public void testLeftInputCausesMovement() throws Exception {
		final int[] expectedKeySet = new int[]{Input.Keys.LEFT};

		IKeyChecker keyChecker = new FakeKeyChecker(expectedKeySet);

		InputSystem inputSystem = new InputSystem(keyChecker);
		_engine.addSystem(inputSystem);

		InputSystem.InputMapping inputMapping = new InputSystem.InputMapping();
		inputMapping.LeftKeys = expectedKeySet;
		_inputComponent.setInputMapping(inputMapping);

		_engine.update(1.f);

		assertEquals(true, _movementComponent.getMoveLeft());
	}

	@Test
	public void testRightInputCausesMovement() throws Exception {
		final int[] expectedKeySet = new int[]{Input.Keys.RIGHT};

		IKeyChecker keyChecker = new FakeKeyChecker(expectedKeySet);

		InputSystem inputSystem = new InputSystem(keyChecker);
		_engine.addSystem(inputSystem);

		InputSystem.InputMapping inputMapping = new InputSystem.InputMapping();
		inputMapping.RightKeys = expectedKeySet;
		_inputComponent.setInputMapping(inputMapping);

		_engine.update(1.f);

		assertEquals(true, _movementComponent.getMoveRight());
	}

	@Test
	public void testJumpInputCausesJump() throws Exception {
		final int[] expectedKeySet = new int[]{Input.Keys.UP};

		IKeyChecker keyChecker = new FakeKeyChecker(expectedKeySet);

		InputSystem inputSystem = new InputSystem(keyChecker);
		_engine.addSystem(inputSystem);

		InputSystem.InputMapping inputMapping = new InputSystem.InputMapping();
		inputMapping.JumpKeys = expectedKeySet;
		_inputComponent.setInputMapping(inputMapping);

		_engine.update(1.f);

		assertEquals(true, _movementComponent.getJump());
	}

	public class FakeKeyChecker implements IKeyChecker {
		private final int[] _pressedKeys;

		public FakeKeyChecker(int[] pressedKeys) {
			_pressedKeys = pressedKeys;
		}

		@Override
		public boolean isKeyPressedInSet(int[] keySet) {
			if (_pressedKeys == null || keySet == null) {
				return false;
			}
			for(int pressedKey: _pressedKeys) {
				for (int key: keySet) {
					if (pressedKey == key) {
						return true;
					}
				}
			}
			return false;
		}
	}
}