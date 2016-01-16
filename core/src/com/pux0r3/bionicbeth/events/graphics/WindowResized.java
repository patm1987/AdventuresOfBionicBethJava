package com.pux0r3.bionicbeth.events.graphics;

/**
 * Created by pux19 on 1/16/2016.
 */
public class WindowResized {
	private final int _width;
	private final int _height;

	public WindowResized(int width, int height) {
		_width = width;
		_height = height;
	}

	public int getWidth() {
		return _width;
	}

	public int getHeight() {
		return _height;
	}
}
