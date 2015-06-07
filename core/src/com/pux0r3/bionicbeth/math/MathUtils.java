package com.pux0r3.bionicbeth.math;

import com.badlogic.gdx.math.Quaternion;

/**
 * Created by Patrick on 6/7/2015.
 */
public class MathUtils {
	/**
	 * Compare two quaternions using an epsilon
	 *
	 * @param q0      the first quaternion to compare
	 * @param q1      the second quaternion to compre
	 * @param epsilon some epsilon value (threshold) under which the quaternions are considered equal
	 * @return true if the quaternions are within some epsilon of each other
	 */
	public static boolean EqualsEpsilon(Quaternion q0, Quaternion q1, float epsilon) {
		// technically I should take the difference of the quaternions amd find the length of the resulting
		// quaternion. I think I should be good just ensuring that every component is within epsilon
		if ((Math.abs(q1.x - q0.x) < epsilon)
				&& (Math.abs(q1.y - q0.y) < epsilon)
				&& (Math.abs(q1.z - q0.z) < epsilon)
				&& (Math.abs(q1.w - q0.w) < epsilon)) {
			return true;
		} else {
			// Note: quaternions are logically equal if q0 == q1 OR q0 == -q1
			return (Math.abs(q1.x + q0.x) < epsilon)
					&& (Math.abs(q1.y + q0.y) < epsilon)
					&& (Math.abs(q1.z + q0.z) < epsilon)
					&& (Math.abs(q1.w + q0.w) < epsilon);
		}
	}
}
