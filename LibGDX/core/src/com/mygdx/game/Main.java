package com.mygdx.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class Main extends ApplicationAdapter implements InputProcessor, ApplicationListener {
	//size in meter
	//1 must be evenly divisible by this step size or it must be an integer (.25, .5, 1, 5)
	final float terrainStepSize = 1;
	final int terrainWidth = 15;
	final int terrainLength = 15;

	//Position attribute - (x, y, z)
	final int POSITION_COMPONENTS = 3;
	//Color attribute - (r, g, b, a), but using Packed
	final int COLOR_COMPONENTS = 1;
	//Total number of components for all attributes
	final int NUM_COMPONENTS = POSITION_COMPONENTS + COLOR_COMPONENTS;
	//The "size" (total number of floats) for a single triangle
	final int PRIMITIVE_SIZE = 3 * NUM_COMPONENTS;
	//The maximum number of triangles our mesh will hold
	//Size of the terrain / stepSize = the amount of squares, *2=the amount of triangles
	final int MAX_TRIS = (int)((terrainLength * terrainWidth)/terrainStepSize)*2;
	//The maximum number of vertices our mesh will hold
	final int MAX_VERTS = MAX_TRIS * 3;

	//The array which holds all the data, interleaved like so:
	//    x, y, z
	//    x, y, z
	//    x, y, z
	//    ... etc ...
	float[] verts = new float[MAX_VERTS * NUM_COMPONENTS];

	int idx = 0;

	PerspectiveCamera camera;
	ModelBatch modelBatch;
	ModelBuilder modelBuilder;
	Model ball;
	ModelInstance ballInstance;
	Environment environment;

	Mesh ground;
	ShaderProgram groundShader;

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




		groundShader = new ShaderProgram(Gdx.files.internal("shader/vertexshader.glsl").readString(), Gdx.files.internal("shader/fragmentshader.glsl").readString());




//		//Add the vertices
//		for(int x=0; x<terrainWidth/terrainStepSize; x++){
//			for(int y=0; y<terrainLength/terrainStepSize; y++){
//				float xCoordinate = x;
//				float yCoordinate = y;
//				//TODO link to height function
//				float zCoordinate = (float) (Math.random()*3)-1;
//
//
//			}
//		}


		ground = new Mesh(true, MAX_VERTS, 0,
				new VertexAttribute(VertexAttributes.Usage.Position, POSITION_COMPONENTS, "a_position"),
				new VertexAttribute(VertexAttributes.Usage.ColorPacked, 4, "a_color"));





//		ground = new Mesh(true, 3, 3, new VertexAttribute(VertexAttributes.Usage.Position, 3, "a_Position"));
//		short[] indices = {0, 1, 2};
//		float[] vertices = {0f, 0f, 0f,
//							1f, 0f, 0f,
//							1f, 0f, 1f};
//
//		ground.setVertices(vertices);
//		ground.setIndices(indices);









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
		//Gdx.gl20.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClearColor(.1f, .1f, .1f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT|GL20.GL_DEPTH_BUFFER_BIT);
		Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
		Gdx.gl.glDepthMask(true);

		//TODO
		//modelBatch.render(terrainInstance, environment);*/

//		groundShader.begin();
//		groundShader.setUniformMatrix("matViewProj", camera.combined);
//		ground.render(groundShader, GL20.GL_TRIANGLES);
//		groundShader.end();

		//push a few triangles to the batch
		drawTriangle(0, 0, 1,40, 20, Color.RED);
		drawTriangle(0, 0, 4, 70, 40, Color.BLUE);

		//this will render the above triangles to GL, using Mesh
		flush();

		//Update camera movement
		cameraInputController.update();
		camera.update();

		modelBatch.begin(camera);
		modelBatch.render(ballInstance, environment);
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

	//Based of https://github.com/mattdesl/lwjgl-basics/wiki/LibGDX-Meshes-Lesson-1
	void drawTriangle(float x, float y, float z, float width, float length, Color color) {
		float colorBits = color.toFloatBits();

		//we don't want to hit any index out of bounds exception...
		//so we need to flush the batch if we can't store any more verts
		if (idx==verts.length)
			flush();

		//now we push the vertex data into our array
		//we are assuming (0, 0) is lower left, and Y is up

		//bottom left vertex
		verts[idx++] = x;
		//TODO link to height function
		verts[idx++] = y;
		verts[idx++] = z;
		verts[idx++] = colorBits;

		//top left vertex
		verts[idx++] = x;
		//TODO link to height function
		verts[idx++] = y;
		verts[idx++] = z + length;
		verts[idx++] = colorBits;

		//bottom right vertex
		verts[idx++] = x + width;
		//TODO link to height function
		verts[idx++] = y;
		verts[idx++] = z;
		verts[idx++] = colorBits;
	}

	//Based of https://github.com/mattdesl/lwjgl-basics/wiki/LibGDX-Meshes-Lesson-1
	void flush() {
		//if we've already flushed
		if (idx==0)
			return;

		//sends our vertex data to the mesh
		ground.setVertices(verts);

		//enable blending, for alpha
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

		//number of vertices we need to render
		int vertexCount = (idx/NUM_COMPONENTS);

		//start the shader before setting any uniforms
		groundShader.begin();
		groundShader.setUniformMatrix("u_projTrans", camera.combined);
		ground.render(groundShader, GL20.GL_TRIANGLES, 0, vertexCount);
		groundShader.end();

		//reset index to zero
		idx = 0;
	}

}
