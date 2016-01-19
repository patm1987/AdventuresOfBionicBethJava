package com.pux0r3.bionicbeth.rendering;

import com.badlogic.gdx.backends.headless.HeadlessNativesLoader;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.pux0r3.bionicbeth.math.MathUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TransformTest {

	public static final float kEpsilon = 0.001f;

	@Before
	public void setUp() throws Exception {
		HeadlessNativesLoader.load();
	}

	@Test
	public void testCTor() {
		Transform t = new Transform();
		assert(t != null);
	}

	@Test
	public void testParent() throws Exception {
		Transform parent = new Transform();
		Transform child = new Transform();

		child.setParent(parent);
		Assert.assertEquals(parent, child.getParent());
	}

	@Test
	public void testChildren() throws Exception {
		Transform parent = new Transform();
		Transform child = new Transform();
		child.setParent(parent);

		Assert.assertTrue(parent.getChildren().contains(child));
	}

	@Test
	public void testOldParentDoesntKeepChild() throws Exception {
		Transform parent1 = new Transform();
		Transform parent2 = new Transform();
		Transform child = new Transform();

		child.setParent(parent1);
		child.setParent(parent2);

		Assert.assertFalse(parent1.getChildren().contains(child));
		Assert.assertTrue(parent2.getChildren().contains(child));
		Assert.assertEquals(parent2, child.getParent());
	}

	@Test
	public void testCanSetParentToNull() throws Exception {
		Transform parent = new Transform();
		Transform child = new Transform();

		child.setParent(parent);
		child.setParent(null);

		Assert.assertFalse(parent.getChildren().contains(child));
		Assert.assertEquals(null, child.getParent());
	}

	@Test
	public void testMatrixCreatedWithIdentity() throws Exception {
		Transform t = new Transform();
		Matrix4 localTransform = new Matrix4();
		t.getLocalTransform(localTransform);
		Assert.assertArrayEquals(localTransform.val, new Matrix4().val, kEpsilon);
	}

	@Test
	public void testCanSetLocalPosition() throws Exception {
		Vector3 localPosition = new Vector3(1f, 2f, 3f);
		Transform t = new Transform();

		t.setLocalPosition(localPosition);

		Vector3 storedTransform = new Vector3();
		Matrix4 localTransform = new Matrix4();
		t.getLocalTransform(localTransform);
		localTransform.getTranslation(storedTransform);

		Assert.assertEquals(localPosition, storedTransform);
	}

	@Test
	public void testGeneratesWorldPosition() throws Exception {
		Vector3 parentPosition = new Vector3(1f, 2f, 3f);
		Vector3 childPosition = new Vector3(3f, 2f, 1f);
		Vector3 worldPosition = parentPosition.cpy().add(childPosition);

		Transform parent = new Transform();
		Transform child = new Transform();

		parent.setLocalPosition(parentPosition);
		child.setLocalPosition(childPosition);

		child.setParent(parent);

		Vector3 storedWorldPosition = new Vector3();
		Matrix4 worldTransform = new Matrix4();
		child.getWorldTransform(worldTransform);
		worldTransform.getTranslation(storedWorldPosition);

		Assert.assertEquals(worldPosition, storedWorldPosition);
	}

	@Test
	public void testGeneratesInverseTransform() throws Exception {
		Vector3 position = new Vector3(1f, 2f, 3f);
		Vector3 inversePosition = new Vector3(-1f, -2f, -3f);
		Transform t = new Transform();
		t.setLocalPosition(position);

		Vector3 storedInversePosition = new Vector3();
		Matrix4 inverseTransform = new Matrix4();
		t.getInverseTransform(inverseTransform);
		inverseTransform.getTranslation(storedInversePosition);
		Assert.assertEquals(inversePosition, storedInversePosition);
	}

	@Test
	public void testGeneratesInverseWorldTransform() throws Exception {
		Vector3 parentPosition = new Vector3(1f, 2f, 3f);
		Vector3 childPosition = new Vector3(3f, 2f, 1f);
		Vector3 inversePosition = new Vector3(-4f, -4f, -4f);

		Transform parent = new Transform();
		Transform child = new Transform();
		child.setParent(parent);

		parent.setLocalPosition(parentPosition);
		child.setLocalPosition(childPosition);

		Vector3 storedInverseWorldPosition = new Vector3();
		Matrix4 inverseWorldTransform = new Matrix4();
		child.getInverseWorldTransform(inverseWorldTransform);
		inverseWorldTransform.getTranslation(storedInverseWorldPosition);
		Assert.assertEquals(inversePosition, storedInverseWorldPosition);
	}

	@Test
	public void testQuaternionIdentity() throws Exception {
		Quaternion expected = new Quaternion();
		Transform t = new Transform();

		Quaternion stored = new Quaternion();
		Matrix4 localTransform = new Matrix4();
		t.getLocalTransform(localTransform);
		localTransform.getRotation(stored);

		Assert.assertEquals(expected, stored);
	}

	@Test
	public void testSetQuaternion() throws Exception {
		Quaternion expected = new Quaternion(new Vector3(0f, 1f, 0f), 60);
		Transform t = new Transform();
		t.setLocalRotation(expected);

		Quaternion stored = new Quaternion();
		Matrix4 localTransform = new Matrix4();
		t.getLocalTransform(localTransform);
		localTransform.getRotation(stored);

		// equals with an epsilon
		Assert.assertTrue(MathUtils.EqualsEpsilon(expected, stored, kEpsilon));
	}

	@Test
	public void testStoresInverseQuaternion() throws Exception {
		Vector3 axis = new Vector3(0f, 1f, 0f);
		float angle = 45f;
		Quaternion quat = new Quaternion(axis, angle);
		Quaternion inv = new Quaternion(axis, -angle);

		Transform t = new Transform();
		t.setLocalRotation(quat);

		Quaternion stored = new Quaternion();
		Matrix4 inverseTransform = new Matrix4();
		t.getInverseTransform(inverseTransform);
		inverseTransform.getRotation(stored);

		Assert.assertTrue(MathUtils.EqualsEpsilon(inv, stored, kEpsilon));
	}

	@Test
	public void testBubblesTransformChanges() throws Exception {
		Vector3 parentAxis = new Vector3(0f, 1f, 0f);
		float parentAngle = 45f;
		Quaternion parentQuaternion = new Quaternion(parentAxis, parentAngle);

		Vector3 parentNewAxis = new Vector3(1f, 0f, 0f);
		float parentNewAngle = 15f;
		Quaternion parentNewQuaternion = new Quaternion(parentNewAxis, parentNewAngle);

		Vector3 childAxis = new Vector3(0f, 0f, 1f);
		float childAngle = 15f;
		Quaternion childRotation = new Quaternion(childAxis, childAngle);

		Vector3 parentPosition = new Vector3(1f, 2f, 3f);
		Vector3 parentNewPosition = new Vector3(3f, 2f, 1f);

		Vector3 childPosition = new Vector3(5f, 4f, 3f);

		Transform parent = new Transform();
		parent.setLocalPosition(parentPosition);
		parent.setLocalRotation(parentQuaternion);

		Transform child = new Transform();
		child.setLocalPosition(childPosition);
		child.setLocalRotation(childRotation);
		child.setParent(parent);

		Matrix4 parentTransform = new Matrix4(
				parentPosition,
				parentQuaternion,
				new Vector3(1f, 1f, 1f));
		Matrix4 parentNewTransform = new Matrix4(
				parentNewPosition,
				parentNewQuaternion,
				new Vector3(1f, 1f, 1f));
		Matrix4 childTransform = new Matrix4(
				childPosition,
				childRotation,
				new Vector3(1f, 1f, 1f));

		Matrix4 parentInvTransform = new Matrix4(
				parentPosition.cpy().scl(-1f),
				parentQuaternion.cpy().conjugate(),
				new Vector3(1f, 1f, 1f));
		Matrix4 parentInvNewTransform = new Matrix4(
				parentNewPosition.cpy().scl(-1f),
				parentNewQuaternion.cpy().conjugate(),
				new Vector3(1f, 1f, 1f));
		Matrix4 childInvTransform = new Matrix4(
				childPosition.cpy().scl(-1f),
				childRotation.cpy().conjugate(),
				new Vector3(1f, 1f, 1f));

		Matrix4 expectedWorld = parentTransform.cpy().mul(childTransform);
		Matrix4 expectedNewWorld = parentNewTransform.cpy().mul(childTransform);
		Matrix4 expectedInvWorld = childInvTransform.cpy().mul(parentInvTransform);
		Matrix4 expectedInvNewWorld = childInvTransform.cpy().mul(parentInvNewTransform);

		Matrix4 childWorldTransform = new Matrix4();
		child.getWorldTransform(childWorldTransform);
		Assert.assertTrue(approximatelyEqual(expectedWorld, childWorldTransform, kEpsilon));

		Matrix4 inverseWorldTransform = new Matrix4();
		child.getInverseWorldTransform(inverseWorldTransform);
		Assert.assertTrue(approximatelyEqual(expectedInvWorld, inverseWorldTransform, kEpsilon));

		parent.setLocalPosition(parentNewPosition);
		parent.setLocalRotation(parentNewQuaternion);

		child.getWorldTransform(childWorldTransform);
		Assert.assertTrue(approximatelyEqual(expectedNewWorld, childWorldTransform, kEpsilon));

		child.getInverseWorldTransform(inverseWorldTransform);
		Assert.assertTrue(approximatelyEqual(expectedInvNewWorld, inverseWorldTransform, kEpsilon));
	}

	@Test
	public void testBubblesUpParentChanges() throws Exception {
		Vector3 parent1Position = new Vector3(1f, 2f, 3f);
		Vector3 parent1Axis = new Vector3(1f, 0f, 0f);
		float parent1Angle = .3f;
		Quaternion parent1Rotation = new Quaternion(parent1Axis, parent1Angle);
		Transform parent1Transform = new Transform();
		parent1Transform.setLocalPosition(parent1Position);
		parent1Transform.setLocalRotation(parent1Rotation);

		Vector3 parent2Position = new Vector3(3f, 2f, 1f);
		Vector3 parent2Axis = new Vector3(0f, 1f, 0f);
		float parent2Angle = .5f;
		Quaternion parent2Rotation = new Quaternion(parent2Axis, parent2Angle);
		Transform parent2Transform = new Transform();
		parent2Transform.setLocalPosition(parent2Position);
		parent2Transform.setLocalRotation(parent2Rotation);

		Vector3 transformPosition = new Vector3(10f, 9f, .3f);
		Vector3 transformAxis = new Vector3(0f, 0f, 1f);
		float transformAngle = 3;
		Quaternion transformRotation = new Quaternion(transformAxis, transformAngle);
		Transform transformTransform = new Transform();
		transformTransform.setLocalPosition(transformPosition);
		transformTransform.setLocalRotation(transformRotation);

		Vector3 childPosition = new Vector3(7f, 10f, .1f);
		Vector3 childAxis = new Vector3(0, 0, 1f);
		float childAngle = 6.1f;
		Quaternion childRotation = new Quaternion(childAxis, childAngle);
		Transform childTransform = new Transform();
		childTransform.setLocalPosition(childPosition);
		childTransform.setLocalRotation(childRotation);

		Matrix4 transformAndChild = new Matrix4(
				transformPosition,
				transformRotation,
				new Vector3(1f, 1f, 1f));
		transformAndChild.mul(
				new Matrix4(childPosition, childRotation, new Vector3(1f, 1f, 1f)));

		Matrix4 transformStack1 = new Matrix4(
				parent1Position,
				parent1Rotation,
				new Vector3(1f, 1f, 1f));
		transformStack1.mul(transformAndChild);

		Matrix4 transformStack2 = new Matrix4(
				parent2Position,
				parent2Rotation,
				new Vector3(1f, 1f, 1f));
		transformStack2.mul(transformAndChild);

		Matrix4 childWorldTransform = new Matrix4();

		childTransform.setParent(transformTransform);
		transformTransform.setParent(parent1Transform);
		childTransform.getWorldTransform(childWorldTransform);
		Assert.assertTrue(
				approximatelyEqual(transformStack1, childWorldTransform, kEpsilon));

		transformTransform.setParent(parent2Transform);
		childTransform.getWorldTransform(childWorldTransform);
		Assert.assertTrue(
				approximatelyEqual(transformStack2, childWorldTransform, kEpsilon));
	}

	@Test
	public void testCanRetrieveLocalPosition() throws Exception {
		Vector3 testPosition = new Vector3(1.f, 2.f, 3.f);
		Vector3 outPosition = new Vector3();

		Transform transform = new Transform();
		transform.getLocalPosition(outPosition);
		Assert.assertEquals(outPosition, Vector3.Zero);

		transform.setLocalPosition(testPosition);
		transform.getLocalPosition(outPosition);
		Assert.assertEquals(outPosition, testPosition);
	}

	@Test
	public void testLocalPositionMatchesWorldPositionWithNoParent() throws Exception {
		Vector3 testPosition = new Vector3(10.f, 1.f, 7.f);
		Vector3 outLocalPosition = new Vector3();
		Vector3 outWorldPosition = new Vector3();

		Transform transform = new Transform();
		transform.getLocalPosition(outLocalPosition);
		transform.getWorldPosition(outWorldPosition);
		Assert.assertEquals(outLocalPosition, outWorldPosition);

		transform.setLocalPosition(testPosition);
		transform.getLocalPosition(outLocalPosition);
		transform.getWorldPosition(outWorldPosition);
		Assert.assertEquals(outLocalPosition, outWorldPosition);
	}

	@Test
	public void testGetWorldTransformHonorsTransformStack() throws Exception {
		Vector3 testPosition = new Vector3(-6.f, 1.f, 7.f);
		Vector3 outPosition = new Vector3();

		Transform root = new Transform();
		Transform child = new Transform();

		child.setParent(root);

		root.setLocalPosition(testPosition);
		child.getWorldPosition(outPosition);
		Assert.assertEquals(testPosition, outPosition);
	}

	@Test
	public void testCanSetWorldPosition() throws Exception {
		Transform root = new Transform();
		root.setLocalPosition(new Vector3(1.f, 1.f, 1.f));
		root.setLocalRotation(new Quaternion(new Vector3(1.f, 0.f, 0.f), 1.f));

		Transform child = new Transform();
		child.setWorldPosition(new Vector3(10.f, 1.f, 5.f));

		Vector3 testWorldPosition = new Vector3();
		child.getWorldPosition(testWorldPosition);
		Assert.assertEquals(new Vector3(10.f, 1.f, 5.f), testWorldPosition);
	}

	public boolean approximatelyEqual(Matrix4 mat0, Matrix4 mat1, float epsilon) {
		for(int i = 0; i < 16; i++) {
			float diff = Math.abs(mat0.getValues()[i] - mat1.getValues()[i]);
			if (diff > epsilon) {
				return false;
			}
		}
		return true;
	}
}