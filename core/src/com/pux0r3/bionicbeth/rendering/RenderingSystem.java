package com.pux0r3.bionicbeth.rendering;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.signals.Listener;
import com.badlogic.ashley.signals.Signal;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Affine2;
import com.badlogic.gdx.math.Matrix4;
import com.pux0r3.bionicbeth.events.graphics.WindowResized;

/**
 * Created by pux19 on 12/19/2015.
 */
public class RenderingSystem extends EntitySystem {
	private final Color _backgroundColor;
	private ImmutableArray<Entity> _spriteEntityList;
	private ImmutableArray<Entity> _meshEntityList;

	private ComponentMapper<TransformComponent> _transformMapper;
	private ComponentMapper<ImageComponent> _imageMapper;
	private ComponentMapper<MeshComponent> _meshComponentMapper;
	private ComponentMapper<OrthographicCameraComponent> _orthographicCameraMapper;

	SpriteBatch _spriteBatch;
	private Entity _cameraEntity;
	private float _aspectRatio;

	public RenderingSystem(
			Color backgroundColor,
			Signal<WindowResized> windowResizedSignal,
			SpriteBatch spriteBatch) {
		super();

		_backgroundColor = backgroundColor;
		_transformMapper = ComponentMapper.getFor(TransformComponent.class);
		_imageMapper = ComponentMapper.getFor(ImageComponent.class);
		_meshComponentMapper = ComponentMapper.getFor(MeshComponent.class);
		_orthographicCameraMapper = ComponentMapper.getFor(OrthographicCameraComponent.class);
		_spriteBatch = spriteBatch;

		windowResizedSignal.add(new Listener<WindowResized>() {
			@Override
			public void receive(Signal<WindowResized> signal, WindowResized object) {
				handleWindowResized(object);
			}
		});
	}

	@Override
	public void addedToEngine(Engine engine) {
		_spriteEntityList = engine.getEntitiesFor(
				Family.all(TransformComponent.class, ImageComponent.class).get()
		);
		_meshEntityList = engine.getEntitiesFor(
				Family.all(TransformComponent.class, MeshComponent.class).get()
		);
	}

	@Override
	public void update(float deltaTime) {
		Gdx.gl.glClearColor(
				_backgroundColor.r,
				_backgroundColor.g,
				_backgroundColor.b,
				_backgroundColor.a);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// TODO: lazily perform this action
		Matrix4 viewMatrix = new Matrix4();
		getViewMatrix(viewMatrix);
		_spriteBatch.setTransformMatrix(viewMatrix);

		Matrix4 projectionMatrix = new Matrix4();
		getProjectionMatrix(projectionMatrix);
		_spriteBatch.setProjectionMatrix(projectionMatrix);

		for(Entity entity: _meshEntityList) {
			// TODO: sort by shader and render in big blocks!
			TransformComponent transform = _transformMapper.get(entity);
			MeshComponent meshComponent = _meshComponentMapper.get(entity);
			Matrix4 modelView = new Matrix4();
			transform.getTransform().getWorldTransform(modelView);
			modelView.mulLeft(viewMatrix);

			meshComponent.Shader.begin();
			meshComponent.Shader.setUniformMatrix("modelView", modelView);
			meshComponent.Shader.setUniformMatrix("projection", projectionMatrix);
			meshComponent.Shader.setUniform4fv(
					"color",
					new float[]{
							meshComponent.Color.r,
							meshComponent.Color.g,
							meshComponent.Color.b,
							meshComponent.Color.a
					},
					0,
					4);
			meshComponent.Mesh.render(meshComponent.Shader, GL20.GL_TRIANGLES);
			meshComponent.Shader.end();
		}

		_spriteBatch.begin();
		for (Entity entity: _spriteEntityList) {
			TransformComponent transform = _transformMapper.get(entity);
			ImageComponent image = _imageMapper.get(entity);

			// TODO: I'm inefficient
			Affine2 affineTransform = new Affine2();
			Matrix4 worldTransform = new Matrix4();
			transform.getTransform().getWorldTransform(worldTransform);
			affineTransform.set(worldTransform);
			_spriteBatch.draw(
					image.getTextureRegion(),
					image.getWidth(),
					image.getHeight(),
					affineTransform);
		}
		_spriteBatch.end();
	}

	private void handleWindowResized(WindowResized windowResized) {
		_aspectRatio = (float)windowResized.getWidth() / (float)windowResized.getHeight();
	}

	public void setCamera(Entity camera) {
		this._cameraEntity = camera;
	}

	public void getProjectionMatrix(Matrix4 outProjectionMatrix) {
		// TODO: I want to cache and regenerate this on demand

		OrthographicCameraComponent orthographicCamera = _orthographicCameraMapper.get(_cameraEntity);
		float height = 2.f * orthographicCamera.getHalfHeight();
		float width = _aspectRatio * height;

		outProjectionMatrix.setToOrtho(
				-width * .5f,
				width * .5f,
				-height * .5f,
				height * .5f,
				orthographicCamera.getNear(),
				orthographicCamera.getFar());
	}

	public void getViewMatrix(Matrix4 outGeneratedMatrix) {
		TransformComponent transformComponent = _transformMapper.get(_cameraEntity);
		if (transformComponent != null) {
			transformComponent.getTransform().getInverseWorldTransform(outGeneratedMatrix);
		}
		else {
			outGeneratedMatrix.set(new Matrix4());
		}
	}

	public MeshComponent makeBox(float radius) {
		ShaderProgram shader = new ShaderProgram(
				Gdx.files.internal("shaders/basic.vert"),
				Gdx.files.internal("shaders/basic.frag"));
		float[] vertexData = new float[] {
				// top
				-1.f, 1.f, -1.f, // 0
				1.f, 1.f, -1.f, // 1
				1.f, 1.f, 1.f, // 2
				-1.f, 1.f, 1.f, // 3

				// bottom
				-1.f, -1.f, -1.f, // 4
				1.f, -1.f, -1.f, // 5
				1.f, -1.f, 1.f, // 6
				-1.f, -1.f, 1.f // 7
		};
		for (int i = 0; i < vertexData.length; i++) {
			vertexData[i] *= radius;
		}
		short[] indexData = new short[] {
				// front
				0, 4, 1,
				1, 4, 5,

				// right
				1, 5, 2,
				2, 5, 6,

				// back
				2, 6, 3,
				3, 6, 7,

				// left
				3, 7, 0,
				0, 7, 4,

				// top
				3, 0, 2,
				2, 0, 1,

				// bottom
				4, 7, 5,
				5, 7, 6
		};

		Mesh mesh = new Mesh(
				true,
				vertexData.length,
				indexData.length,
				new VertexAttribute(VertexAttributes.Usage.Position, 3, "vertPosition"));
		mesh.setVertices(vertexData);
		mesh.setIndices(indexData);

		MeshComponent component = new MeshComponent();
		component.Shader = shader;
		component.Color = Color.WHITE;
		component.Mesh = mesh;
		return component;
	}
}
