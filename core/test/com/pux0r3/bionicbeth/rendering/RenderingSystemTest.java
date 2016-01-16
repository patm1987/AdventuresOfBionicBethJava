package com.pux0r3.bionicbeth.rendering;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.signals.Signal;
import com.badlogic.gdx.graphics.Color;
import com.pux0r3.bionicbeth.events.graphics.WindowResized;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by pux19 on 1/16/2016.
 */
public class RenderingSystemTest {
	@Test
	public void testGeneratesOrthographicMatrix() throws Exception {
		Signal<WindowResized> windowResizedSignal = new Signal<>();

		Engine engine = new Engine();
		RenderingSystem renderingSystem = new RenderingSystem(Color.BLACK, windowResizedSignal);
		engine.addSystem(renderingSystem);

		Entity testEntity = new Entity();
		OrthographicCameraComponent orthographicCameraComponent = new OrthographicCameraComponent();
		orthographicCameraComponent.setHeight(1.f);
		testEntity.add(orthographicCameraComponent);

		engine.addEntity(testEntity);

		// TODO: test for orthographic transform
		assertTrue(false);
	}
}