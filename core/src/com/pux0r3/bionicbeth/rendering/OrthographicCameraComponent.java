package com.pux0r3.bionicbeth.rendering;

import com.badlogic.ashley.core.Component;

/**
 * Created by pux19 on 1/16/2016.
 */
public class OrthographicCameraComponent implements Component {
	private float _halfHeight;

	public float getHalfHeight() {
		return _halfHeight;
	}

	public void setHalfHeight(float halfHeight) {
		_halfHeight = halfHeight;
	}

	public void updateSize(int width, int height) {

	}
}
