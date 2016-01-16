package com.pux0r3.bionicbeth.rendering;

import com.badlogic.ashley.core.Component;

/**
 * Created by pux19 on 1/16/2016.
 */
public class OrthographicCameraComponent implements Component {
	private float _height;

	public float getHeight() {
		return _height;
	}

	public void setHeight(float height) {
		_height = height;
	}

	public void updateSize(int width, int height) {

	}
}
