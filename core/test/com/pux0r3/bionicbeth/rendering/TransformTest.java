package com.pux0r3.bionicbeth.rendering;

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
}