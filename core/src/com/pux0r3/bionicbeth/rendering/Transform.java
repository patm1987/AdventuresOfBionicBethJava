package com.pux0r3.bionicbeth.rendering;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Patrick on 2/15/2015.
 *
 * TODO: I should change the get transforms to behave like the get vectors
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

	public void getLocalTransform(Matrix4 outLocalTransform) {
		if (_localTransform == null) {
			_localTransform = new Matrix4(_position, _rotation, new Vector3(1f, 1f, 1f));
		}

		outLocalTransform.set(_localTransform);
	}

	/*!
	 * Gets the matrix that takes an object in parent space into local space
	 * \return the world to local transform represented by this matrix
	 */
	public void getInverseTransform(Matrix4 outInverseTransform) {
		if (_inverseLocalTransform == null) {
			_inverseLocalTransform = new Matrix4(
					new Vector3(-_position.x, -_position.y, -_position.z),
					_rotation.cpy().conjugate(),
					new Vector3(1f, 1f, 1f));
		}

		outInverseTransform.set(_inverseLocalTransform);
	}

	/*!
	 * Gets the matrix that represents an object in world space
	 * \return	the local to world transform represented by this matrix
	 */
	public void getWorldTransform(Matrix4 outWorldTransform) {
		if (_worldTransform == null) {
			_worldTransform = new Matrix4();
			if (_parent == null) {
				getLocalTransform(_worldTransform);
			} else {
				_parent.getWorldTransform(_worldTransform);
				Matrix4 localTransform = new Matrix4();
				getLocalTransform(localTransform);
				_worldTransform.mul(localTransform);
			}
		}

		outWorldTransform.set(_worldTransform);
	}

	public void getInverseWorldTransform(Matrix4 outInverseWorldTransform) {
		if (_inverseWorldTransform == null) {
			_inverseWorldTransform = new Matrix4();
			if (_parent == null) {
				getInverseTransform(_inverseWorldTransform);
			} else {
				getInverseTransform(_inverseWorldTransform);
				Matrix4 parentInverseWorldTransform = new Matrix4();
				_parent.getInverseWorldTransform(parentInverseWorldTransform);
				_inverseWorldTransform.mul(parentInverseWorldTransform);
			}
		}

		outInverseWorldTransform.set(_inverseWorldTransform);
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

	public void getLocalPosition(Vector3 outPosition) {
		outPosition.set(_position);
	}

	public void getWorldPosition(Vector3 outPosition) {
		Matrix4 worldTransform = new Matrix4();
		getWorldTransform(worldTransform);
		worldTransform.getTranslation(outPosition);
	}

	public void setWorldPosition(Vector3 worldPosition) {
		// TODO: I can avoid a bit of overhead if I just factor in local rotation/scale
		// rather than clearing everything
		setLocalPosition(Vector3.Zero);
		Matrix4 inverseWorldTransform = new Matrix4();
		getInverseWorldTransform(inverseWorldTransform);
		worldPosition.mul(inverseWorldTransform);
		setLocalPosition(worldPosition);
	}
}
