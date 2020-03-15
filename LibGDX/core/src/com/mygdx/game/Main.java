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

import java.util.Random;

public class Main extends ApplicationAdapter implements InputProcessor, ApplicationListener {
	//size in meter
	//1 must be evenly divisible by this step size or it must be an integer (.25, .5, 1, 5)
	final float terrainStepSize = 1;
	final int terrainWidth = 20;
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

		//Set ground shader and mesh
		groundShader = new ShaderProgram(Gdx.files.internal("shader/vertexshader.glsl").readString(), Gdx.files.internal("shader/fragmentshader.glsl").readString());

		ground = new Mesh(true, MAX_VERTS, 0,
				new VertexAttribute(VertexAttributes.Usage.Position, POSITION_COMPONENTS, "a_position"),
				new VertexAttribute(VertexAttributes.Usage.ColorPacked, 4, "a_color"));

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

		//TODO find a more optimal way
		//Create terrain
		createTerrain(0, 0);

		//this will render the remaining triangles
		flush();

		//Update camera movement
		cameraInputController.update();
		camera.update();

		//Show ball
		modelBatch.begin(camera);
		modelBatch.render(ballInstance, environment);
		modelBatch.end();
	}

	public Model convertMeshToModel(final String id, final Mesh mesh, Material material) {
		ModelBuilder builder = new ModelBuilder();
		builder.begin();
		builder.part(id, mesh, GL20.GL_TRIANGLES, material);
		return builder.end();
	}

	void createTerrain(float xOffset, float yOffset){

		//'Weird' quad test
//		verts[idx++] = 0;
//		verts[idx++] = 1;
//		verts[idx++] = 0;
//		verts[idx++] = Color.BLUE.toFloatBits();
//
//		verts[idx++] = 5;
//		verts[idx++] = 2;
//		verts[idx++] = 0;
//		verts[idx++] = Color.BLUE.toFloatBits();
//
//		verts[idx++] = 0;
//		verts[idx++] = 3;
//		verts[idx++] = 5;
//		verts[idx++] = Color.BLUE.toFloatBits();
//
//
//		verts[idx++] = 5;
//		verts[idx++] = 2;
//		verts[idx++] = 0;
//		verts[idx++] = Color.BLUE.toFloatBits();
//
//		verts[idx++] = 0;
//		verts[idx++] = 3;
//		verts[idx++] = 5;
//		verts[idx++] = Color.BLUE.toFloatBits();
//
//		verts[idx++] = 5;
//		verts[idx++] = 0;
//		verts[idx++] = 5;
//		verts[idx++] = Color.BLUE.toFloatBits();

		for(int x=0; x<terrainWidth/terrainStepSize; x++){
			for(int z=0; z<terrainLength/terrainStepSize; z++){
				float xCoordinate = x*terrainStepSize+xOffset;
				float zCoordinate = z*terrainStepSize+yOffset;
				drawGroundQuad(xCoordinate, zCoordinate);
			}
		}
	}

	float getTerrainHeight(float x, float z){
		//TODO put the actual function here
		Random random = new Random((long) (x+z));
		random.nextFloat();
		return (random.nextFloat()*3-1);
		//return (float) (.2*x+.02*z-2);
	}

	void drawGroundQuad(float x, float z) {
		//we don't want to hit any index out of bounds exception...
		//so we need to flush the batch if we can't store any more verts
		if (idx==verts.length-1)
			flush();

		//First triangle (bottom left, bottom right, top left)
		//bottom left vertex
		verts[idx++] = x;
		verts[idx++] = getTerrainHeight(x, z);
		verts[idx++] = z;
		if(getTerrainHeight(x, z) > 0){
			verts[idx++] = Color.GREEN.toFloatBits();
		} else {
			verts[idx++] = Color.BLUE.toFloatBits();
		}

		//bottom right vertex
		verts[idx++] = x + terrainStepSize;
		verts[idx++] = getTerrainHeight(x + terrainStepSize, z);
		verts[idx++] = z;
		if(getTerrainHeight(x + terrainStepSize, z) > 0){
			verts[idx++] = Color.GREEN.toFloatBits();
		} else {
			verts[idx++] = Color.BLUE.toFloatBits();
		}

		//Top left vertex
		verts[idx++] = x;
		verts[idx++] = getTerrainHeight(x, z + terrainStepSize);
		verts[idx++] = z + terrainStepSize;
		if(getTerrainHeight(x, z + terrainStepSize) > 0){
			verts[idx++] = Color.GREEN.toFloatBits();
		} else {
			verts[idx++] = Color.BLUE.toFloatBits();
		}

		//Second triangle (bottom right, top left, top right)
		//bottom right
		verts[idx++] = x + terrainStepSize;
		verts[idx++] = getTerrainHeight(x + terrainStepSize, z);
		verts[idx++] = z;
		if(getTerrainHeight(x + terrainStepSize, z) > 0){
			verts[idx++] = Color.GREEN.toFloatBits();
		} else {
			verts[idx++] = Color.BLUE.toFloatBits();
		}

		//top left vertex
		verts[idx++] = x;
		verts[idx++] = getTerrainHeight(x, z + terrainStepSize);
		verts[idx++] = z + terrainStepSize;
		if(getTerrainHeight(x, z + terrainStepSize) > 0){
			verts[idx++] = Color.GREEN.toFloatBits();
		} else {
			verts[idx++] = Color.BLUE.toFloatBits();
		}

		//top right vertex
		verts[idx++] = x + terrainStepSize;
		verts[idx++] = getTerrainHeight(x + terrainStepSize, z + terrainStepSize);
		verts[idx++] = z + terrainStepSize;
		if(getTerrainHeight(x + terrainStepSize, z + terrainStepSize) > 0){
			verts[idx++] = Color.GREEN.toFloatBits();
		} else {
			verts[idx++] = Color.BLUE.toFloatBits();
		}
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
}
