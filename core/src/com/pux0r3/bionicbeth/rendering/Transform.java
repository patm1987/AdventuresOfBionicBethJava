package com.pux0r3.bionicbeth.rendering;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Patrick on 2/15/2015.
 */
public class Transform {
	private Transform _parent;
	private ArrayList<Transform> _children = new ArrayList<>();

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
}
