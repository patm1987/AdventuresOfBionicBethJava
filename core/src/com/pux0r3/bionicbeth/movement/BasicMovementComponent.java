package com.pux0r3.bionicbeth.movement;

import com.badlogic.ashley.core.Component;

/**
 * Created by pux19 on 1/12/2016.
 */
public class BasicMovementComponent implements Component {
	private boolean _moveLeft;
	private boolean _moveRight;
	private boolean _jump;

	// TODO: speed
	// TODO: acceleration (possibly with curve)
	// TODO: implement jumping
	// TODO: clear a jump flag when "on the ground"

	public void setMoveLeft(boolean moveLeft) {
		_moveLeft = moveLeft;
	}

	public boolean getMoveLeft() {
		return _moveLeft;
	}

	public void setMoveRight(boolean moveRight) {
		_moveRight = moveRight;
	}

	public boolean getMoveRight() {
		return _moveRight;
	}

	public void setJump(boolean jump) {
		_jump = jump;
	}

	public boolean getJump() {
		return _jump;
	}
}
