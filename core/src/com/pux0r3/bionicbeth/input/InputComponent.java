package com.pux0r3.bionicbeth.input;

import com.badlogic.ashley.core.Component;

/**
 * Created by pux19 on 1/13/2016.
 */
public class InputComponent implements Component {
	private InputSystem.InputMapping _inputMapping;

	public InputSystem.InputMapping getInputMapping() {
		return _inputMapping;
	}

	public void setInputMapping(InputSystem.InputMapping inputMapping) {
		_inputMapping = inputMapping;
	}
}
