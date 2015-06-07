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
		Assert.assertArrayEquals(t.getLocalTransform().val, new Matrix4().val, kEpsilon);
	}

	@Test
	public void testCanSetLocalPosition() throws Exception {
		Vector3 localPosition = new Vector3(1f, 2f, 3f);
		Transform t = new Transform();

		t.setLocalPosition(localPosition);

		Vector3 storedTransform = new Vector3();
		t.getLocalTransform().getTranslation(storedTransform);

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
		child.getWorldTransform().getTranslation(storedWorldPosition);

		Assert.assertEquals(worldPosition, storedWorldPosition);
	}

	@Test
	public void testGeneratesInverseTransform() throws Exception {
		Vector3 position = new Vector3(1f, 2f, 3f);
		Vector3 inversePosition = new Vector3(-1f, -2f, -3f);
		Transform t = new Transform();
		t.setLocalPosition(position);

		Vector3 storedInversePosition = new Vector3();
		t.getInverseTransform().getTranslation(storedInversePosition);
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
		child.getInverseWorldTransform().getTranslation(storedInverseWorldPosition);
		Assert.assertEquals(inversePosition, storedInverseWorldPosition);
	}

	@Test
	public void testQuaternionIdentity() throws Exception {
		Quaternion expected = new Quaternion();
		Transform t = new Transform();

		Quaternion stored = new Quaternion();
		t.getLocalTransform().getRotation(stored);

		Assert.assertEquals(expected, stored);
	}

	@Test
	public void testSetQuaternion() throws Exception {
		Quaternion expected = new Quaternion(new Vector3(0f, 1f, 0f), 60);
		Transform t = new Transform();
		t.setLocalRotation(expected);

		Quaternion stored = new Quaternion();
		t.getLocalTransform().getRotation(stored);

		// equals with an epsilon
		Assert.assertTrue(MathUtils.EqualsEpsilon(expected, stored, kEpsilon));
	}

	// todo: test bubble down invalidation
}