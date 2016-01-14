package com.pux0r3.bionicbeth.input;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Input;
import com.pux0r3.bionicbeth.movement.BasicMovementComponent;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by pux19 on 1/13/2016.
 */
public class InputMappingTest {
	@Test
	public void testLeftInputCausesMovement() throws Exception {
		final int[] expectedKeySet = new int[]{Input.Keys.LEFT};
		InputSystem.IKeyChecker keyChecker = new InputSystem.IKeyChecker() {

			@Override
			public boolean isKeyPressedInSet(int[] keySet) {
				return keySet == expectedKeySet;
			}
		};

		Engine engine = new Engine();
		InputSystem inputSystem = new InputSystem(keyChecker);
		engine.addSystem(inputSystem);

		Entity testEntity = new Entity();
		BasicMovementComponent movementComponent = new BasicMovementComponent();
		InputComponent inputComponent = new InputComponent();
		InputSystem.InputMapping inputMapping = new InputSystem.InputMapping();
		inputMapping.LeftKeys = expectedKeySet;
		inputComponent.setInputMapping(inputMapping);
		testEntity.add(movementComponent);
		testEntity.add(inputComponent);

		engine.addEntity(testEntity);

		engine.update(1.f);

		assertEquals(true, movementComponent.getMoveLeft());
	}

	@Test
	public void testRightInputCausesMovement() throws Exception {
		final int[] expectedKeySet = new int[]{Input.Keys.RIGHT};
		InputSystem.IKeyChecker keyChecker = new InputSystem.IKeyChecker() {

			@Override
			public boolean isKeyPressedInSet(int[] keySet) {
				return keySet == expectedKeySet;
			}
		};

		Engine engine = new Engine();
		InputSystem inputSystem = new InputSystem(keyChecker);
		engine.addSystem(inputSystem);

		Entity testEntity = new Entity();
		BasicMovementComponent movementComponent = new BasicMovementComponent();
		InputComponent inputComponent = new InputComponent();
		InputSystem.InputMapping inputMapping = new InputSystem.InputMapping();
		inputMapping.RightKeys = expectedKeySet;
		inputComponent.setInputMapping(inputMapping);
		testEntity.add(movementComponent);
		testEntity.add(inputComponent);

		engine.addEntity(testEntity);

		engine.update(1.f);

		assertEquals(true, movementComponent.getMoveRight());
	}
}