package com.pux0r3.bionicbeth.rendering;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by pux19 on 12/19/2015.
 */
public class ImageComponent implements Component {
	private Texture _texture;
	private TextureRegion _textureRegion;

	public ImageComponent(Texture texture) {
		_texture = texture;
		_textureRegion = new TextureRegion(texture);
	}

	public float getWidth() {
		return _textureRegion.getRegionWidth();
	}

	public float getHeight() {
		return _textureRegion.getRegionHeight();
	}

	public TextureRegion getTextureRegion() {
		return _textureRegion;
	}
}
