package com.pux0r3.bionicbeth.rendering;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.signals.Signal;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.pux0r3.bionicbeth.events.graphics.WindowResized;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by pux19 on 1/16/2016.
 */
public class RenderingSystemTest {
	Signal<WindowResized> _windowResizedSignal;
	private RenderingSystem _renderingSystem;

	@Before
	public void setUp() throws Exception {
		// signal for alerting listeners that the window resized
		_windowResizedSignal = new Signal<>();

		// engine with a rendering subsystem
		_renderingSystem = new RenderingSystem(Color.BLACK, _windowResizedSignal, null);
	}

	@Test
	public void testGeneratesOrthographicMatrix() throws Exception {
		final float halfHeight = 1.f;
		final float height = 2 * halfHeight;
		final int windowWidth = 640;
		final int windowHeight = 480;
		final float aspect = (float)windowWidth / (float)windowHeight;
		final float width = height * aspect;
		final float near = -1.f;
		final float far = 1.f;

		// entity with an orthographic camera
		Entity testEntity = new Entity();
		OrthographicCameraComponent orthographicCameraComponent = new OrthographicCameraComponent();
		orthographicCameraComponent.setHalfHeight(1.f);
		orthographicCameraComponent.setNear(near);
		orthographicCameraComponent.setFar(far);
		testEntity.add(orthographicCameraComponent);

		// expected orthographic transform
		// NOTE: column major
		Matrix4 expectedOrthographicProjectionMatrix = new Matrix4(new float[]{
				2.f / width, 0.f, 0.f, 0.f,
				0.f, 2.f / height, 0.f, 0.f,
				0.f, 0.f, -2.f / (far - near), 0.f,
				0.f, 0.f, - (far + near) / (far - near), 1.f
		});

		// tell the rendering system what we're using to render
		_renderingSystem.setCamera(testEntity);

		// best case for success is the resize signal firing now, future tests will have to push it back
		// NOTE: should be 4::3, but doesn't really matter
		_windowResizedSignal.dispatch(new WindowResized(windowWidth, windowHeight));

		// debating whether or not I should wait an update
		// I figure that I should do lazy generation of the projection matrix, so it shouldn't be necessary
		Matrix4 generatedOrthographicProjectionMatrix = new Matrix4();
		_renderingSystem.getProjectionMatrix(generatedOrthographicProjectionMatrix);

		assertMatricesEqual(
				expectedOrthographicProjectionMatrix,
				generatedOrthographicProjectionMatrix,
				Math.ulp(1.f));
	}

	@Test
	public void testGetsViewMatrixFromTransform() throws Exception {
		Transform transform = new Transform();
		transform.setLocalPosition(new Vector3(1.f, 2.f, 3.f));
		transform.setLocalRotation(new Quaternion(new Vector3(0.f, 1.f, 0.f), 2.f));

		Entity testEntity = new Entity();
		testEntity.add(new OrthographicCameraComponent());
		testEntity.add(new TransformComponent(transform));

		_renderingSystem.setCamera(testEntity);

		Matrix4 expectedViewMatrix = new Matrix4();
		transform.getInverseWorldTransform(expectedViewMatrix);

		Matrix4 generatedViewMatrix = new Matrix4();
		_renderingSystem.getViewMatrix(generatedViewMatrix);

		assertMatricesEqual(expectedViewMatrix, generatedViewMatrix, Math.ulp(10.f));
	}

	@Test
	public void testViewUsesOriginIfNoTransformSpecifiedOnCamera() throws Exception {
		Entity testEntity = new Entity();
		testEntity.add(new OrthographicCameraComponent());

		_renderingSystem.setCamera(testEntity);

		Matrix4 expectedViewMatrix = new Matrix4(new float[]{
				1.f, 0.f, 0.f, 0.f,
				0.f, 1.f, 0.f, 0.f,
				0.f, 0.f, 1.f, 0.f,
				0.f, 0.f, 0.f, 1.f
		});

		Matrix4 generatedMatrix = new Matrix4();
		_renderingSystem.getViewMatrix(generatedMatrix);

		assertMatricesEqual(expectedViewMatrix, generatedMatrix, Math.ulp(10.f));
	}

	// TODO: make rendering system cranky if added entity doesn't have a camera
	// TODO: camera object changes after window size signal fires
	// TODO: singal fires BEFORE the rendering system registers for the window size signal event

	public static void assertMatricesEqual(Matrix4 expected, Matrix4 generated, float epsilon) {
		float[] expectedValues = expected.getValues();
		float[] generatedValues = generated.getValues();

		for (int i = 0; i < 16; i++) {
			assertEquals(expectedValues[i], generatedValues[i], epsilon);
		}
	}
}