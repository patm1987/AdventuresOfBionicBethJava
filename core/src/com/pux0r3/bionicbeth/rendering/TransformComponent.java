package com.pux0r3.bionicbeth.rendering;

import com.badlogic.ashley.core.Component;

/**
 * Created by pux19 on 12/19/2015.
 */
public class TransformComponent implements Component {
	private Transform _transform;

	public TransformComponent(Transform transform) {
		_transform = transform;
	}

	public Transform getTransform() {
		return _transform;
	}
}
