package com.pux0r3.bionicbeth.physics.collision;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by pux19 on 3/17/2016.
 */
public class CollisionSphereComponent implements Component {
	private Vector3 _center;
	private float _radius;

	public void getCenter(Vector3 outCenter) {
		outCenter.set(_center);
	}

	public void setCenter(Vector3 center) {
		_center.set(center);
	}

	public float getRadius() {
		return _radius;
	}

	public void setRadius(float radius) {
		_radius = radius;
	}
}
