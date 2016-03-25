package com.pux0r3.bionicbeth.physics.collision;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by pux19 on 3/17/2016.
 */
public class CollisionLineComponent implements Component {
	private Vector3 _startPosition;
	private Vector3 _endPosition;

	public void getStartPosition(Vector3 outPostition) {
		outPostition.set(_startPosition);
	}

	public void setStartPosition(Vector3 startPosition) {
		_startPosition.set(startPosition);
	}

	public void getEndPosition(Vector3 outPosition) {
		outPosition.set(_endPosition);
	}

	public void setEndPosition(Vector3 endPosition) {
		_endPosition.set(endPosition);
	}
}
