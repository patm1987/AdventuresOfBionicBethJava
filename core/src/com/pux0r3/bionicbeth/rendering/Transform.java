package com.pux0r3.bionicbeth.rendering;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Patrick on 2/15/2015.
 */
public class Transform {
	private Transform _parent = null;
	private ArrayList<Transform> _children = new ArrayList<>();

	private Vector3 _position = new Vector3();
	private Quaternion _rotation = new Quaternion();

	private Matrix4 _localTransform;
	private Matrix4 _inverseLocalTransform;

	private Matrix4 _worldTransform;
	private Matrix4 _inverseWorldTransform;

	public void setParent(Transform parent) {
		if (_parent != null) {
			_parent._children.remove(this);
		}
		_parent = parent;
		if (_parent != null) {
			_parent._children.add(this);
		}
		invalidateChildren();
	}

	public Transform getParent() {
		return _parent;
	}

	public Collection<Transform> getChildren() {
		return _children;
	}

	public Matrix4 getLocalTransform() {
		if (_localTransform == null) {
			_localTransform = new Matrix4(_position, _rotation, new Vector3(1f, 1f, 1f));
		}
		return _localTransform;
	}

	public Matrix4 getInverseTransform() {
		if (_inverseLocalTransform == null) {
			_inverseLocalTransform = new Matrix4(
					new Vector3(-_position.x, -_position.y, -_position.z),
					_rotation.cpy().conjugate(),
					new Vector3(1f, 1f, 1f));
		}
		return _inverseLocalTransform;
	}

	public Matrix4 getWorldTransform() {
		if (_worldTransform == null) {
			if (_parent == null) {
				_worldTransform = getLocalTransform().cpy();
			} else {
				_worldTransform = _parent.getWorldTransform().cpy();
				_worldTransform.mul(getLocalTransform());
			}
		}

		return _worldTransform;
	}

	public Matrix4 getInverseWorldTransform() {
		if (_inverseWorldTransform == null) {
			if (_parent == null) {
				_inverseWorldTransform = getInverseTransform().cpy();
			} else {
				_inverseWorldTransform = getInverseTransform().cpy();
				_inverseWorldTransform.mul(_parent.getInverseWorldTransform());
			}
		}
		return _inverseWorldTransform;
	}

	public void setLocalPosition(Vector3 localPosition) {
		_position = localPosition.cpy();
		invalidateChildren();
	}

	public void setLocalRotation(Quaternion localRotation) {
		_rotation = localRotation.cpy();
		invalidateChildren();
	}

	private void invalidateChildren() {
		boolean invalidated = false;

		if (_localTransform != null) {
			_localTransform = null;
			invalidated = true;
		}

		if (_inverseLocalTransform != null) {
			_inverseLocalTransform = null;
			invalidated = true;
		}

		if (_worldTransform != null) {
			_worldTransform = null;
			invalidated = true;
		}

		if (_inverseWorldTransform != null) {
			_inverseWorldTransform = null;
			invalidated = true;
		}

		if (invalidated) {
			for(Transform child: _children) {
				child.invalidateChildren();
			}
		}
	}
}
