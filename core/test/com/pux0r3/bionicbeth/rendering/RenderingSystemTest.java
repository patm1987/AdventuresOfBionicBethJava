package com.pux0r3.bionicbeth.rendering;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.signals.Signal;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Matrix4;
import com.pux0r3.bionicbeth.events.graphics.WindowResized;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by pux19 on 1/16/2016.
 */
public class RenderingSystemTest {
	@Test
	public void testGeneratesOrthographicMatrix() throws Exception {
		final float halfHeight = 1.f;
		final float height = 2 * halfHeight;
		final float aspect = 4.f / 3.f; // 4::3 aspect ratio
		final float width = height * aspect;
		final float near = -1.f;
		final float far = 1.f;

		// signal for alerting listeners that the window resized
		Signal<WindowResized> windowResizedSignal = new Signal<>();

		// engine with a rendering subsystem
		Engine engine = new Engine();
		RenderingSystem renderingSystem = new RenderingSystem(Color.BLACK, windowResizedSignal);
		engine.addSystem(renderingSystem);

		// entity with an orthographic camera
		Entity testEntity = new Entity();
		OrthographicCameraComponent orthographicCameraComponent = new OrthographicCameraComponent();
		orthographicCameraComponent.setHalfHeight(1.f);
		testEntity.add(orthographicCameraComponent);
		engine.addEntity(testEntity);

		// expected orthographic transform
		// NOTE: column major
		Matrix4 expectedOrthographicProjectionMatrix = new Matrix4(new float[]{
				2.f / width, 0.f, 0.f, 0.f,
				0.f, 2.f / height, 0.f, 0.f,
				0.f, 0.f, -2.f / (far - near), 0.f,
				0.f, 0.f, - (far + near) / (far - near), 1.f
		});

		// tell the rendering system what we're using to render
		renderingSystem.setCamera(testEntity);

		// debating whether or not I should wait an update
		// I figure that I should do lazy generation of the projection matrix, so it shouldn't be necessary
		Matrix4 generatedOrthographicProjectionMatrix = new Matrix4();
		renderingSystem.getProjectionMatrix(generatedOrthographicProjectionMatrix);

		// TODO: test for orthographic transform
		assertEquals(expectedOrthographicProjectionMatrix, generatedOrthographicProjectionMatrix);
	}

	// TODO: make rendering system cranky if added entity doesn't have a camera
	// TODO: if the camera has a position, check that the proper view transform is generated
}