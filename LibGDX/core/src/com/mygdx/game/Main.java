package com.mygdx.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.MeshBuilder;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Main extends ApplicationAdapter implements InputProcessor, ApplicationListener {
	PerspectiveCamera camera;
	ModelBatch modelBatch;
	ModelBuilder modelBuilder;
	Model ball;
	ModelInstance ballInstance;
	Mesh terrainMesh;
	Model terrainModel;
	ModelInstance terrainInstance;
	Environment environment;

	Mesh mesh;
	ShaderProgram shader;

	CameraInputController cameraInputController;

	@Override
	public void create () {
		//Needed to process input
		Gdx.input.setInputProcessor(this);

		//Create camera
		camera = new PerspectiveCamera(75, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		//Set initial position and orientation
		camera.position.set(10f, 10f, 10f);
		camera.lookAt(0f, 0f, 0f);

		//Clipping distances
		camera.near = 0.1f;
		camera.far = 300f;

		//TMP model
		modelBatch = new ModelBatch();
		modelBuilder = new ModelBuilder();
		ball = modelBuilder.createBox(
				2f, 2f, 2f,
				new Material(ColorAttribute.createDiffuse(Color.BLUE)),
				VertexAttributes.Usage.Position|VertexAttributes.Usage.Normal);

		//Load model
		ballInstance = new ModelInstance(ball, 0, 0, 0);


		//Terrain test
		/*
		MeshBuilder meshBuilder = new MeshBuilder();
		meshBuilder.begin(VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal, GL20.GL_TRIANGLES);

		terrainMesh = new Mesh(true, 4, 6,
				new VertexAttribute(VertexAttributes.Usage.Position, 3,"attr_Position"),
				new VertexAttribute(VertexAttributes.Usage.TextureCoordinates, 2, "attr_texCoords"));
		terrainMesh.setVertices(new float[] {
				-1024f, -1024f, 0, 0, 1,
				1024f, -1024f, 0, 1, 1,
				1024f,  1024f, 0, 1, 0,
				-1024f,  1024f, 0, 0, 0
		});
		terrainMesh.setIndices(new short[] { 0, 1, 2, 2, 3, 0 });

		terrainMesh = meshBuilder.end();

		Material material = new Material(ColorAttribute.createDiffuse(Color.BLUE));
		terrainModel = convertMeshToModel("ground", terrainMesh, material);
		terrainInstance = new ModelInstance(terrainModel, 0, 0, 0);*/
		mesh = new Mesh(true, 3, 3, new VertexAttribute(VertexAttributes.Usage.Position, 3, "a_Position"));
		short[] indices = {0, 1, 2};
		float[] vertices = {0f, 0f, 0f,
							1f, 0f, 0f,
							1f, 0f, 1f};
		mesh.setVertices(vertices);
		mesh.setIndices(indices);









		//Set camera controller
		cameraInputController = new CameraInputController(camera);
		Gdx.input.setInputProcessor(cameraInputController);

		//Set lightning
		environment = new Environment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 1, 1, 1, 1f));
		environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));
	}

	@Override
	public void render () {
		Gdx.gl20.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClearColor(.1f, .1f, .1f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT|GL20.GL_DEPTH_BUFFER_BIT);

		//Update camera movement
		cameraInputController.update();

		camera.update();
		modelBatch.begin(camera);
		modelBatch.render(ballInstance, environment);

		//TODO
		//modelBatch.render(terrainInstance, environment);*/

		modelBatch.end();
	}

	@Override
	public void dispose () {
		ball.dispose();
	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

	public Model convertMeshToModel(final String id, final Mesh mesh, Material material) {
		ModelBuilder builder = new ModelBuilder();
		builder.begin();
		builder.part(id, mesh, GL20.GL_TRIANGLES, material);
		return builder.end();
	}

}
