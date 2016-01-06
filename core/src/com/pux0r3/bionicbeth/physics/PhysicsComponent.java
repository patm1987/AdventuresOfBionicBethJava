package com.pux0r3.bionicbeth.physics;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by pux19 on 1/5/2016.
 */
public class PhysicsComponent implements Component {
	Vector3 _velocity = new Vector3();
	Vector3 _acceleration = new Vector3();

	public void getVelocity(Vector3 outVelocity) {
		outVelocity.set(_velocity);
	}

	public void setVelocity(Vector3 inVelocity) {
		_velocity.set(inVelocity);
	}

	public void getAcceleration(Vector3 outAcceleration) {
		outAcceleration.set(_acceleration);
	}

	public void setAcceleration(Vector3 inAcceleration) {
		_acceleration.set(inAcceleration);
	}
}
