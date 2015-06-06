package com.pux0r3.bionicbeth.rendering;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import org.junit.Assert;
import org.junit.Test;

public class TransformTest {
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
		Assert.assertArrayEquals(t.getLocalTransform().val, new Matrix4().val, 0.001f);
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
		Vector3 worldPosition = parentPosition.add(childPosition);

		Transform parent = new Transform();
		Transform child = new Transform();

		parent.setLocalPosition(parentPosition);
		child.setLocalPosition(childPosition);

		child.setParent(parent);

		Vector3 storedWorldPosition = new Vector3();
		child.getWorldPosition().getTranslation(storedWorldPosition);

		Assert.assertEquals(worldPosition, storedWorldPosition);
	}
}