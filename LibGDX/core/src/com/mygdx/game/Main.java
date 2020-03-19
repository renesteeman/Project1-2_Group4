package com.mygdx.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.loader.G3dModelLoader;
import com.badlogic.gdx.graphics.g3d.model.data.ModelData;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.g3d.utils.TextureProvider;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import Screen.*;

import java.util.ArrayList;
import java.util.Random;

//TODO How does this interact with the menu?
public class Main extends ApplicationAdapter implements InputProcessor, ApplicationListener, Screen {

	//Shared 2D stuff
	SpriteBatch batch2D;

	//Used to set what will be rendered
	UIState CurrentUIState = UIState.Only3D;

	//GamePlay
	GameUI gamePlayGame;
	Texture gamePlayBackButton;
	Texture gamePlayLine;
	BitmapFont gamePlayFont;
	private static final int gamePlay_BACK_BUTTON_SIZE = 80;
	private static final int gamePlay_OK_BUTTON_WIDTH = 90;
	private static final int gamePlay_OK_BUTTON_HEIGHT = 90;

	//GameUI
	public static final int gameUI_WINDOW_WIDTH = 750;
	public static final int gameUI_WINDOW_HEIGHT = 750;

	//HitWaterUI
	int hitWaterUIBackgroundWidth;
	private Stage hitWaterUIStage;
	private Skin hitWaterUISkin;
	double hitWaterUIDistanceFromStart;
	double hitWaterUIMaxDistanceFromStart;
	String hitWaterUIDistanceFromStartString;
	BitmapFont hitWaterUIFont;
	Sprite hitWaterUIWhiteBackground;
	TextButton hitWaterUIPositive;
	TextButton hitWaterUINegative;

	//WonScreen
	GameUI wonScreenGame;
	BitmapFont wonScreenFont;

	//NON-2D-UI
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
//	final int PRIMITIVE_SIZE = 3 * NUM_COMPONENTS;
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
	float[] terrainVertices = new float[MAX_VERTS * NUM_COMPONENTS];
	int terrainVertexIndex = 0;

	PerspectiveCamera camera;
	Viewport viewport;
	Environment environment;
	ModelBatch modelBatch;
	ModelBuilder modelBuilder;

	static Model ballModel;
	static ModelInstance ballInstance;

	static Model goalModel;
	static ModelInstance goalInstance;

	static boolean treesEnabled;
	static Model treeModel;
	static ArrayList<ModelInstance> treeInstances = new ArrayList<ModelInstance>();

	Mesh ground;
	ShaderProgram groundShader;

	CameraInputController cameraInputController;

	@Override
	public void render (float delta) {
		//Gdx.gl20.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClearColor(.1f, .1f, .1f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT|GL20.GL_DEPTH_BUFFER_BIT);
		Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
		Gdx.gl.glDepthMask(true);

		//Create terrain
		createTerrain(0, 0);

		//this will render the remaining triangles
		flush();

		//Update camera movement
		cameraInputController.update();
		camera.update();

		//Show ball and goal
		modelBatch.begin(camera);
		modelBatch.render(ballInstance, environment);
		modelBatch.render(goalInstance, environment);

		for(ModelInstance treeInstance : treeInstances){
			modelBatch.render(treeInstance, environment);
		}

		modelBatch.end();

		//2D UI
		switch(CurrentUIState){
			case GamePlay:
				//GamePlay
				gamePlayBackButton = new Texture("back.png");
				gamePlayLine = new Texture("line.png");

				//GamePlay
				batch2D.begin();
				FreeTypeFontGenerator gamePlayGenerator = new FreeTypeFontGenerator(Gdx.files.internal("Georgia Italic.ttf"));

				FreeTypeFontGenerator.FreeTypeFontParameter gamePlayParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
				gamePlayParameter.size = 100;
				gamePlayParameter.characters = "Game";
				gamePlayFont = gamePlayGenerator.generateFont(gamePlayParameter);
				gamePlayFont.setColor(Color.FOREST);
				gamePlayGenerator.dispose();
				gamePlayFont.draw(batch2D, "Game", GameUI.gameUI_WINDOW_WIDTH/3, GameUI.gameUI_WINDOW_HEIGHT-100);

				if(Gdx.input.getX() > GameUI.gameUI_WINDOW_WIDTH/10 && Gdx.input.getX() < (GameUI.gameUI_WINDOW_WIDTH/10) + gamePlay_BACK_BUTTON_SIZE && GameUI.gameUI_WINDOW_HEIGHT - Gdx.input.getY() < gamePlay_BACK_BUTTON_SIZE + GameUI.gameUI_WINDOW_HEIGHT-140 && GameUI.gameUI_WINDOW_HEIGHT - Gdx.input.getY() > GameUI.gameUI_WINDOW_HEIGHT-140 ){
					batch2D.draw(gamePlayLine, (GameUI.gameUI_WINDOW_WIDTH)/10, (GameUI.gameUI_WINDOW_HEIGHT-220), gamePlay_BACK_BUTTON_SIZE, 150);
					if(Gdx.input.justTouched()){
						this.dispose();
//						this.setScreen(new StartMenu(gamePlayGame));
					}
				}

				batch2D.draw(gamePlayBackButton, GameUI.gameUI_WINDOW_WIDTH/10, GameUI.gameUI_WINDOW_HEIGHT-150, gamePlay_BACK_BUTTON_SIZE, gamePlay_BACK_BUTTON_SIZE);
				batch2D.end();
				break;
			case GameUI:
				//GameUI
				batch2D = new SpriteBatch();

				//GameUI
				super.render();
				break;

			case HitWaterUI:
				//HitWaterUI
				Ball.location = new Vector2d(0, 0, 0);
				PuttingCourse.start = new Vector2d(10, 1, 1);
				Vector2d ballLocation = Ball.location;
				Vector2d startPoint = PuttingCourse.start;
				hitWaterUIMaxDistanceFromStart = Vector2d.getDistance(ballLocation, startPoint);

				batch2D = new SpriteBatch();
				hitWaterUISkin = new Skin(Gdx.files.internal("uiskin.json"));
				hitWaterUIStage = new Stage(new ScreenViewport());

				hitWaterUIBackgroundWidth = Gdx.graphics.getWidth()/4;
				hitWaterUIWhiteBackground = new Sprite(new Texture(Gdx.files.internal("whiteBackground.png")));
				hitWaterUIWhiteBackground.setSize(hitWaterUIBackgroundWidth, Gdx.graphics.getHeight());
				hitWaterUIWhiteBackground.setPosition(Gdx.graphics.getWidth()-hitWaterUIBackgroundWidth,0);

				hitWaterUIFont = new BitmapFont(Gdx.files.internal("Arial.fnt"));
				hitWaterUIFont.getData().setScale(0.6f,0.6f);
				hitWaterUIDistanceFromStartString = "How far from the start do you want to set the ball?";

				hitWaterUIPositive = new TextButton(">", hitWaterUISkin, "default");
				hitWaterUIPositive.setWidth(25);
				hitWaterUIPositive.setHeight(25);
				hitWaterUIPositive.setPosition(Gdx.graphics.getWidth() * 18/20, Gdx.graphics.getHeight() * 3/5);

				hitWaterUINegative = new TextButton("<", hitWaterUISkin, "default");
				hitWaterUINegative.setWidth(25);
				hitWaterUINegative.setHeight(25);
				hitWaterUINegative.setPosition(Gdx.graphics.getWidth() * 17/20, Gdx.graphics.getHeight() * 3/5);

				final TextButton hitWaterUISetBallButton = new TextButton("Set", hitWaterUISkin, "default");
				hitWaterUISetBallButton.setWidth(100);
				hitWaterUISetBallButton.setHeight(30);
				hitWaterUISetBallButton.setPosition(Gdx.graphics.getWidth() * 8/10, Gdx.graphics.getHeight() * 6/20);

				hitWaterUIPositive.addListener(new ClickListener(){
			/*
			@Override
			public void clicked(InputEvent event, float x, float y) {
				rotation = rotation + 10;
				if(rotation >= 360){
					rotation = 0;
				}
			}
			 */

					@Override
					public boolean isPressed() {
						return super.isPressed();
					}
				});
				hitWaterUINegative.addListener(new ClickListener(){
			/*
			@Override
			public void clicked(InputEvent event, float x, float y) {
				rotation = rotation - 10;
				if(rotation < 0){
					rotation = 350;
				}
			}
			 */

					@Override
					public boolean isPressed() {
						return super.isPressed();
					}
				});

				hitWaterUIStage.addActor(hitWaterUIPositive);
				hitWaterUIStage.addActor(hitWaterUINegative);
				hitWaterUIStage.addActor(hitWaterUISetBallButton);

				InputMultiplexer hitWaterUIInputMultiplexer = new InputMultiplexer(hitWaterUIStage, this);
				Gdx.input.setInputProcessor(hitWaterUIInputMultiplexer);



				//HitWaterUI
				hitWaterUIStage.act(Gdx.graphics.getDeltaTime());

				if(hitWaterUIPositive.isPressed()){
					hitWaterUIDistanceFromStart += Gdx.graphics.getDeltaTime() *20;
					if(hitWaterUIDistanceFromStart >= hitWaterUIMaxDistanceFromStart){
						hitWaterUIDistanceFromStart = hitWaterUIMaxDistanceFromStart;
					}
				}

				if(hitWaterUINegative.isPressed()){
					hitWaterUIDistanceFromStart -= Gdx.graphics.getDeltaTime() *20;
					if(hitWaterUIDistanceFromStart < 0){
						hitWaterUIDistanceFromStart = 0;
					}
				}

				int distanceFromStartRounded = (int) hitWaterUIDistanceFromStart;

				batch2D.begin();
				hitWaterUIFont.setColor(Color.BLACK);
				hitWaterUIWhiteBackground.draw(batch2D);
				hitWaterUIFont.draw(batch2D, hitWaterUIDistanceFromStartString, Gdx.graphics.getWidth()-hitWaterUIBackgroundWidth,
						Gdx.graphics.getHeight() * 9/10, hitWaterUIBackgroundWidth, 1, true);
				hitWaterUIFont.draw(batch2D, String.valueOf(distanceFromStartRounded), Gdx.graphics.getWidth()-hitWaterUIBackgroundWidth,
						Gdx.graphics.getHeight() * 7/10, hitWaterUIBackgroundWidth, 1, true);
				batch2D.end();

				hitWaterUIStage.draw();
				break;

			case WonScreen:
				//WonScreen
				batch2D.begin();
				FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Georgia Italic.ttf"));

				FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
				parameter.size = 100;
				parameter.characters = "YOU WON!";
				wonScreenFont = generator.generateFont(parameter);
				wonScreenFont.setColor(Color.FOREST);
				generator.dispose();
				wonScreenFont.draw(batch2D, "YOU WON!", GameUI.gameUI_WINDOW_WIDTH/3-50, GameUI.gameUI_WINDOW_HEIGHT-300);

				batch2D.end();
				break;
		}
	}

	public static void renderBall(double x, double y, double z){
		ModelLoader<?> modelLoader = new G3dModelLoader(new JsonReader());
		ModelData ballModelData = modelLoader.loadModelData(Gdx.files.internal("core/assets/golfBall.g3dj"));
		ballModel = new Model(ballModelData, new TextureProvider.FileTextureProvider());
		ballInstance = new ModelInstance(ballModel, (float) x, (float) y, (float) z);
	}

	public static void renderBall(Vector2d location){
		renderBall(location.x, location.y, location.z);
	}

	public static void renderGoal(double x, double y, double z){
		ModelLoader<?> modelLoader = new G3dModelLoader(new JsonReader());
		ModelData goalModelData = modelLoader.loadModelData(Gdx.files.internal("core/assets/flag.g3dj"));
		goalModel = new Model(goalModelData, new TextureProvider.FileTextureProvider());
		goalInstance = new ModelInstance(goalModel, (float) x, (float) y, (float) z);
	}

	public static void renderGoal(Vector2d location){
		renderGoal(location.x, location.y, location.z);
	}

	public static void enableTrees(){
		treesEnabled = true;
		ModelLoader<?> modelLoader = new G3dModelLoader(new JsonReader());
		ModelData treeModelData = modelLoader.loadModelData(Gdx.files.internal("core/assets/tree.g3dj"));
		treeModel = new Model(treeModelData, new TextureProvider.FileTextureProvider());
	}

	public static void addTree(float x, float y, float z){
		ModelInstance tree = new ModelInstance(treeModel, x, y, z);
		treeInstances.add(tree);
	}

	public static void removeTree(int treeIndex){
		treeInstances.remove(treeIndex);
	}

	public static void removeTreeWithinRadius(float x, float y, float z, float radius){
		for(int i=0; i<treeInstances.size(); i++){
			ModelInstance cTree = treeInstances.get(i);
			Vector3 cTreeLocation = cTree.transform.getTranslation(new Vector3());

			//If the tree is within the given radius, remove it
			if(Math.abs(cTreeLocation.x-x) <= radius && Math.abs(cTreeLocation.y-y) <= radius && Math.abs(cTreeLocation.z-z) <= radius){
				treeInstances.remove(i);
			}
		}
	}

	public static void removeTreeAtLocation(float x, float y, float z){
		removeTreeWithinRadius(x, y, z, 0);
	}

	public Model convertMeshToModel(final String id, final Mesh mesh, Material material) {
		ModelBuilder builder = new ModelBuilder();
		builder.begin();
		builder.part(id, mesh, GL20.GL_TRIANGLES, material);
		return builder.end();
	}

	void createTerrain(float xOffset, float yOffset){
		//Go over chunks of terrain and create as many chunks as needed to create the terrain
		for(int x=0; x<terrainWidth/terrainStepSize; x++){
			for(int z=0; z<terrainLength/terrainStepSize; z++){
				float xCoordinate = x*terrainStepSize+xOffset;
				float zCoordinate = z*terrainStepSize+yOffset;
				drawGroundQuad(xCoordinate, zCoordinate);
			}
		}
	}

	static float getTerrainHeight(float x, float z){
		//TODO put the actual function here
		Random random = new Random((long) (x+z));
		random.nextFloat();
		return (random.nextFloat()*3-1);
//		return (float) (.2*x+.02*z-2);
	}

	void drawGroundQuad(float x, float z) {
		//we don't want to hit any index out of bounds exception...
		//so we need to flush the batch if we can't store any more verts
		//4=amounts of indexes used per vertex; 3=amount of vertices per triangle; 2=amount of triangles
		if (terrainVertexIndex == terrainVertices.length-(4*3*2))
			flush();

		//First triangle (bottom left, bottom right, top left)
		//bottom left vertex
		terrainVertices[terrainVertexIndex++] = x;
		terrainVertices[terrainVertexIndex++] = getTerrainHeight(x, z);
		terrainVertices[terrainVertexIndex++] = z;
		if(getTerrainHeight(x, z) > 0){
			terrainVertices[terrainVertexIndex++] = Color.GREEN.toFloatBits();
		} else {
			terrainVertices[terrainVertexIndex++] = Color.BLUE.toFloatBits();
		}

		//bottom right vertex
		terrainVertices[terrainVertexIndex++] = x + terrainStepSize;
		terrainVertices[terrainVertexIndex++] = getTerrainHeight(x + terrainStepSize, z);
		terrainVertices[terrainVertexIndex++] = z;
		if(getTerrainHeight(x + terrainStepSize, z) > 0){
			terrainVertices[terrainVertexIndex++] = Color.GREEN.toFloatBits();
		} else {
			terrainVertices[terrainVertexIndex++] = Color.BLUE.toFloatBits();
		}

		//Top left vertex
		terrainVertices[terrainVertexIndex++] = x;
		terrainVertices[terrainVertexIndex++] = getTerrainHeight(x, z + terrainStepSize);
		terrainVertices[terrainVertexIndex++] = z + terrainStepSize;
		if(getTerrainHeight(x, z + terrainStepSize) > 0){
			terrainVertices[terrainVertexIndex++] = Color.GREEN.toFloatBits();
		} else {
			terrainVertices[terrainVertexIndex++] = Color.BLUE.toFloatBits();
		}

		//Second triangle (bottom right, top left, top right)
		//bottom right
		terrainVertices[terrainVertexIndex++] = x + terrainStepSize;
		terrainVertices[terrainVertexIndex++] = getTerrainHeight(x + terrainStepSize, z);
		terrainVertices[terrainVertexIndex++] = z;
		if(getTerrainHeight(x + terrainStepSize, z) > 0){
			terrainVertices[terrainVertexIndex++] = Color.GREEN.toFloatBits();
		} else {
			terrainVertices[terrainVertexIndex++] = Color.BLUE.toFloatBits();
		}

		//top left vertex
		terrainVertices[terrainVertexIndex++] = x;
		terrainVertices[terrainVertexIndex++] = getTerrainHeight(x, z + terrainStepSize);
		terrainVertices[terrainVertexIndex++] = z + terrainStepSize;
		if(getTerrainHeight(x, z + terrainStepSize) > 0){
			terrainVertices[terrainVertexIndex++] = Color.GREEN.toFloatBits();
		} else {
			terrainVertices[terrainVertexIndex++] = Color.BLUE.toFloatBits();
		}

		//top right vertex
		terrainVertices[terrainVertexIndex++] = x + terrainStepSize;
		terrainVertices[terrainVertexIndex++] = getTerrainHeight(x + terrainStepSize, z + terrainStepSize);
		terrainVertices[terrainVertexIndex++] = z + terrainStepSize;
		if(getTerrainHeight(x + terrainStepSize, z + terrainStepSize) > 0){
			terrainVertices[terrainVertexIndex++] = Color.GREEN.toFloatBits();
		} else {
			terrainVertices[terrainVertexIndex++] = Color.BLUE.toFloatBits();
		}
	}

	//Based of https://github.com/mattdesl/lwjgl-basics/wiki/LibGDX-Meshes-Lesson-1
	void flush() {
		//if we've already flushed
		if (terrainVertexIndex ==0)
			return;

		//sends our vertex data to the mesh
		ground.setVertices(terrainVertices);

		//number of vertices we need to render
		int vertexCount = (terrainVertexIndex /NUM_COMPONENTS);

		//start the shader before setting any uniforms
		groundShader.begin();
		groundShader.setUniformMatrix("u_projTrans", camera.combined);
		ground.render(groundShader, GL20.GL_TRIANGLES, 0, vertexCount);
		groundShader.end();

		//reset index to zero
		terrainVertexIndex = 0;
	}

	@Override
	public void dispose () {
		modelBatch.dispose();
		ballModel.dispose();
		goalModel.dispose();
		if(treesEnabled){
			treeModel.dispose();
		}
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

	@Override
	public void show() {
//		//Use 1080p
//		Gdx.graphics.setWindowedMode(1920, 1080);

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

		//Needed for window resizing
		viewport = new FitViewport(800, 480, camera);

		//Model setup
		modelBatch = new ModelBatch();
		modelBuilder = new ModelBuilder();

		//NOTE: when updating the 3D model, export it as fbx, than convert it to g3dj .\fbx-conv-win32 -f -o G3DJ NAME.fbx, than set opacity to 1 for all the materials
		//TODO only call these functions from a general game class
		renderBall(0, getTerrainHeight(0, 0), 0);
		renderGoal(0, getTerrainHeight(0, 0),0);

		//Test for the trees
//		enableTrees();
//		addTree(5, getTerrainHeight(5, 5), 5);
//		addTree(15, getTerrainHeight(15, 15), 15);
//		removeTreeWithinRadius(5, getTerrainHeight(5, 5), 5, 1);

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
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, .5f, .5f, .5f, 1f));
		environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));
	}

//	public void resize(int width, int height) {
//		viewport.update(width, height);
//		camera.update();
//	}

	@Override
	public void hide() {

	}
}
