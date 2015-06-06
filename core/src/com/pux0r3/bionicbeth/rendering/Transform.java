package com.pux0r3.bionicbeth.rendering;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Patrick on 2/15/2015.
 */
public class Transform {
	private Transform _parent = null;
	private ArrayList<Transform> _children = new ArrayList<>();
	private Matrix4 _localTransform = new Matrix4();

	public void setParent(Transform parent) {
		if (_parent != null) {
			_parent._children.remove(this);
		}
		_parent = parent;
		if (_parent != null) {
			_parent._children.add(this);
		}
	}

	public Transform getParent() {
		return _parent;
	}

	public Collection<Transform> getChildren() {
		return _children;
	}

	public Matrix4 getLocalTransform() {
		return _localTransform;
	}

	public void setLocalPosition(Vector3 localPosition) {
		_localTransform.setTranslation(localPosition);
	}

	public Matrix4 getWorldPosition() {
		if (_parent == null) {
			return _localTransform;
		}

		Matrix4 worldTransform = _parent.getWorldPosition().cpy();
		worldTransform.mul(_localTransform);
		return worldTransform;
	}
}
