package com.pux0r3.bionicbeth.rendering;

import com.badlogic.ashley.core.Component;

/**
 * Created by pux19 on 1/16/2016.
 */
public class OrthographicCameraComponent implements Component {
	private float _halfHeight;
	private float _near;
	private float _far;

	public float getHalfHeight() {
		return _halfHeight;
	}

	public void setHalfHeight(float halfHeight) {
		_halfHeight = halfHeight;
	}

	public float getNear() {
		return _near;
	}

	public float getFar() {
		return _far;
	}

	public void setNear(float near) {
		_near = near;
	}

	public void setFar(float far) {
		_far = far;
	}
}
