package com.pux0r3.bionicbeth.input;

import com.badlogic.gdx.Gdx;

/**
 * Created by pux19 on 1/13/2016.
 */
public class GdxKeyChecker implements InputSystem.IKeyChecker {
	@Override
	public boolean isKeyPressedInSet(int[] keySet) {
		for (int key : keySet) {
			if (Gdx.input.isKeyPressed(key)) {
				return true;
			}
		}
		return false;
	}
}
