package com.pux0r3.bionicbeth.rendering;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

/**
 * Created by pux19 on 1/18/2016.
 */
public class MeshComponent implements Component {
	public ShaderProgram Shader;
	public Color Color;
	public Mesh Mesh;
}
