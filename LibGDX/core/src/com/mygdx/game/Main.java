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

import java.util.ArrayList;
import java.util.Random;

//TODO How does this interact with the menu?
public class Main extends ApplicationAdapter implements InputProcessor, ApplicationListener {

	//Used to set what will be rendered
	UIState CurrentUIState = UIState.StartMenu;

	//ChoiceModeScreen
	GameUI choiceModeScreenGame;
	BitmapFont choiceModeScreenTitle;
	Stage choiceModeScreenStage;
	TextButton choiceModeScreenHuman;
	TextButton choiceModeScreenBot;
	TextField choiceModeScreenPath;


	//CustomizedMenu
	GameUI customizedMenuGame;
	Texture customizedMenuBackButton;
	Texture customizedMenuLine;
	Texture customizedMenuOkButton;
	BitmapFont customizedMenuFont;
	BitmapFont customizedMenuVelocity;
	BitmapFont customizedMenuAcceleration;
	BitmapFont customizedMenuCoefficient;
	BitmapFont customizedMenuDistance;
	BitmapFont customizedMenuMass;
	BitmapFont customizedMenuHeight;
	BitmapFont customizedMenuHeight2;
	BitmapFont customizedMenuHeight3;
	Stage customizedMenuStage;
	TextField customizedMenuVel;
	TextField customizedMenuAcc;
	TextField customizedMenuMu;
	TextField customizedMenuDis;
	TextField customizedMenuMas;
	TextField customizedMenuHei;
	//Variables fot the answers
	BitmapFont customizedMenuVelocity1;
	private static final int customizedMenu_BACK_BUTTON_SIZE = 80;
	private static final int customizedMenu_OK_BUTTON_WIDTH = 90;
	private static final int customizedMenu_OK_BUTTON_HEIGHT = 90;


	//DefaultMenu
	GameUI defaultMenuGame;
	Texture defaultMenuBackButton;
	Texture defaultMenuLine;
	Texture defaultMenuOkButton;
	BitmapFont defaultMenuFont;
	BitmapFont defaultMenuVelocity;
	BitmapFont defaultMenuAcceleration;
	BitmapFont defaultMenuCoefficient;
	BitmapFont defaultMenuDistance;
	BitmapFont defaultMenuMass;
	BitmapFont defaultMenuHeight;
	//Variables that are used in the game
	BitmapFont defaultMenuVelocity1;
	BitmapFont defaultMenuAcceleration1;
	BitmapFont defaultMenuCoefficient1;
	BitmapFont defaultMenuDistance1;
	BitmapFont defaultMenuMass1;
	BitmapFont defaultMenuHeight1;
	private static final int defaultMenu_BACK_BUTTON_SIZE = 80;
	private static final int defaultMenu_OK_BUTTON_WIDTH = 90;
	private static final int defaultMenu_OK_BUTTON_HEIGHT = 90;

	//GamePlay
	GameUI gamePlayGame;
	Texture gamePlayBackButton;
	Texture gamePlayLine;
	BitmapFont gamePlayFont;
	private static final int gamePlay_BACK_BUTTON_SIZE = 80;
	private static final int gamePlay_OK_BUTTON_WIDTH = 90;
	private static final int gamePlay_OK_BUTTON_HEIGHT = 90;

	//GameUI
	public SpriteBatch gameUIBatch;
	public static final int gameUI_WINDOW_WIDTH = 750;
	public static final int gameUI_WINDOW_HEIGHT = 750;

	//HelpMenu
	GameUI helpMenuHelp;
	Texture helpMenuHelpButton;
	Texture helpMenuBackButton;
	Texture helpMenuOkButton;
	Texture helpMenuLine;
	Texture helpMenuLeftButton;
	Texture helpMenuRightButton;
	Texture helpMenuPlusButton;
	Texture helpMenuMinusButton;
	Texture helpMenuShootButton;
	BitmapFont helpMenuFont;
	BitmapFont helpMenuLeftRule;
	BitmapFont helpMenuRightRule;
	BitmapFont helpMenuPlusRule;
	BitmapFont helpMenuMinusRule;
	BitmapFont helpMenuShootRule;
	private static final int helpMenu_HELP_BUTTON_SIZE = 100;
	private static final int helpMenu_BACK_BUTTON_SIZE = 80;
	private static final int helpMenu_OK_BUTTON_WIDTH = 90;
	private static final int helpMenu_OK_BUTTON_HEIGHT = 90;
	private static final int helpMenu_LEFT_BUTTON_WIDTH = 70;
	private static final int helpMenu_LEFT_BUTTON_HEIGHT = 70;
	private static final int helpMenu_RIGHT_BUTTON_WIDTH = 70;
	private static final int helpMenu_RIGHT_BUTTON_HEIGHT = 70;
	private static final int helpMenu_PLUS_BUTTON_SIZE = 70;
	private static final int helpMenu_MINUS_BUTTON_SIZE = 70;
	private static final int helpMenu_SHOOT_BUTTON_SIZE = 70;

	//HitWaterUI
	int hitWaterUIBackgroundWidth;
	SpriteBatch hitWaterUIBatch;
	private Stage hitWaterUIStage;
	private Skin hitWaterUISkin;
	double hitWaterUIDistanceFromStart;
	double hitWaterUIMaxDistanceFromStart;
	String hitWaterUIDistanceFromStartString;
	BitmapFont hitWaterUIFont;
	Sprite hitWaterUIWhiteBackground;
	TextButton hitWaterUIPositive;
	TextButton hitWaterUINegative;
	//MainMenuScreen
	GameUI mainMenuScreenGame;
	SettingsMenu mainMenuScreenTest;
	Texture mainMenuScreenExitButton;
	Texture mainMenuScreenStartButton;
	Texture mainMenuScreenSettingsButton;
	Texture mainMenuScreenLine;
	Texture mainMenuScreenGolf;
	BitmapFont mainMenuScreenFont;
	BitmapFont mainMenuScreenFont1;
	private static final int mainMenuScreen_START_BUTTON_WIDTH = 400;
	private static final int mainMenuScreen_START_BUTTON_HEIGHT = 400;
	private static final int mainMenuScreen_EXIT_BUTTON_WIDTH = 100;
	private static final int mainMenuScreen_EXIT_BUTTON_HEIGHT = 100;
	private static final int mainMenuScreen_SETTINGS_BUTTON_SIZE = 80;
	private static final int mainMenuScreen_GOLF_IMAGE_WIDTH = 200;
	private static final int mainMenuScreen_GOLF_IMAGE_HEIGHT = 200;

	//SettingsMenu
	GameUI settingsMenuSettings;
	Texture settingsMenuSettingsButton;
	Texture settingsMenuBackButton;
	Texture settingsMenuOkButton;
	Texture settingsMenuLine;
	BitmapFont settingsMenuFont;
	private static final int settingsMenu_SETTINGS_BUTTON_SIZE = 80;
	private static final int settingsMenu_BACK_BUTTON_SIZE = 80;
	private static final int settingsMenu_OK_BUTTON_WIDTH = 90;
	private static final int settingsMenu_OK_BUTTON_HEIGHT = 90;

	//StartMenu
	GameUI startMenuStart;
	Texture startMenuBackButton;
	Texture startMenuOkButton;
	Texture startMenuLine;
	BitmapFont startMenuFont;
	BitmapFont startMenuFont1;
	BitmapFont startMenuChoice;
	BitmapFont startMenuCustomize;
	BitmapFont startMenuOwnPath;
	Stage startMenuStage;
	TextField startMenuPath;
	private static final int startMenu_BACK_BUTTON_SIZE = 80;
	private static final int startMenu_OK_BUTTON_WIDTH = 90;
	private static final int startMenu_OK_BUTTON_HEIGHT = 90;

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
	public void create () {
		//Use 1080p
		Gdx.graphics.setWindowedMode(1920, 1080);

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



		//2D UI creation

		//ChoiceModeScreen
		choiceModeScreenStage = new Stage();
		Gdx.input.setInputProcessor(choiceModeScreenStage);
		Skin choiceModeSkin = new Skin(Gdx.files.internal("skins/uiskin.json"));

		choiceModeScreenHuman = new TextButton("Play as a human", choiceModeSkin);
		choiceModeScreenHuman.setPosition(GameUI.gameUI_WINDOW_WIDTH/6, GameUI.gameUI_WINDOW_HEIGHT - 300);
		choiceModeScreenHuman.setSize(200, 50);
		choiceModeScreenHuman.setColor(Color.FOREST);

		choiceModeScreenHuman.addListener(new ClickListener(){
			@Override
			public void touchUp(InputEvent e, float x, float y, int point, int button){
				game.setScreen(new GamePlay(game));
			}
		});

		choiceModeScreenBot = new TextButton("Play using your own file", choiceModeSkin);
		choiceModeScreenBot.setPosition(GameUI.gameUI_WINDOW_WIDTH/6, GameUI.gameUI_WINDOW_HEIGHT - 400);
		choiceModeScreenBot.setSize(200, 50);
		choiceModeScreenBot.setColor(Color.FOREST);

		choiceModeScreenBot.addListener(new ClickListener(){
			@Override
			public void touchUp(InputEvent e, float x, float y, int point, int button){
				game.setScreen(new GamePlay(game));
			}
		});

		choiceModeScreenPath = new TextField("", choiceModeSkin);
		choiceModeScreenPath.setPosition((GameUI.gameUI_WINDOW_WIDTH/6) + choiceModeScreenBot.getWidth() + 50, GameUI.gameUI_WINDOW_HEIGHT-400);
		choiceModeScreenPath.setSize(200, 50);
		choiceModeScreenPath.setColor(Color.FOREST);
		choiceModeScreenPath.setText("Your path:");

		choiceModeScreenStage.addActor(choiceModeScreenHuman);
		choiceModeScreenStage.addActor(choiceModeScreenBot);
		choiceModeScreenStage.addActor(choiceModeScreenPath);


		//CustomizedMenu
		customizedMenuBackButton = new Texture("back.png");
		customizedMenuLine = new Texture("line.png");
		customizedMenuOkButton = new Texture("ok.png");

		customizedMenuStage = new Stage();
		Gdx.input.setInputProcessor(customizedMenuStage);
		Skin customizedMenuSkin = new Skin(Gdx.files.internal("skins/uiskin.json"));

		//Different TextFields
		//Velocity
		customizedMenuVel = new TextField("", customizedMenuSkin);
		customizedMenuVel.setPosition((GameUI.gameUI_WINDOW_WIDTH/3) - 40, GameUI.gameUI_WINDOW_HEIGHT-317);
		customizedMenuVel.setSize(200, 20);
		customizedMenuVel.setColor(Color.FOREST);
		//Acceleration
		customizedMenuAcc = new TextField("", customizedMenuSkin);
		customizedMenuAcc.setPosition((GameUI.gameUI_WINDOW_WIDTH/3) - 40, GameUI.gameUI_WINDOW_HEIGHT-367);
		customizedMenuAcc.setSize(200, 20);
		customizedMenuAcc.setColor(Color.FOREST);
		//Mu
		customizedMenuMu = new TextField("", customizedMenuSkin);
		customizedMenuMu.setPosition((GameUI.gameUI_WINDOW_WIDTH/3) + 40, GameUI.gameUI_WINDOW_HEIGHT-417);
		customizedMenuMu.setSize(200, 20);
		customizedMenuMu.setColor(Color.FOREST);
		//Distance
		customizedMenuDis = new TextField("", customizedMenuSkin);
		customizedMenuDis.setPosition((GameUI.gameUI_WINDOW_WIDTH/3) + 40, GameUI.gameUI_WINDOW_HEIGHT-467);
		customizedMenuDis.setSize(200, 20);
		customizedMenuDis.setColor(Color.FOREST);
		//Mass
		customizedMenuMas = new TextField("", customizedMenuSkin);
		customizedMenuMas.setPosition((GameUI.gameUI_WINDOW_WIDTH/3) - 40, GameUI.gameUI_WINDOW_HEIGHT-517);
		customizedMenuMas.setSize(200, 20);
		customizedMenuMas.setColor(Color.FOREST);
		//Height Equation
		customizedMenuHei = new TextField("", customizedMenuSkin);
		customizedMenuHei.setPosition((GameUI.gameUI_WINDOW_WIDTH/3) + 40, GameUI.gameUI_WINDOW_HEIGHT-567);
		customizedMenuHei.setSize(200, 20);
		customizedMenuHei.setColor(Color.FOREST);

		customizedMenuStage.addActor(customizedMenuVel);
		customizedMenuStage.addActor(customizedMenuAcc);
		customizedMenuStage.addActor(customizedMenuMu);
		customizedMenuStage.addActor(customizedMenuDis);
		customizedMenuStage.addActor(customizedMenuMas);
		customizedMenuStage.addActor(customizedMenuHei);


		//DefaultMenu
		defaultMenuBackButton = new Texture("back.png");
		defaultMenuLine = new Texture("line.png");
		defaultMenuOkButton =new Texture(("ok.png"));


		//GamePlay
		gamePlayBackButton = new Texture("back.png");
		gamePlayLine = new Texture("line.png");


		//GameUI
		gameUIBatch = new SpriteBatch();


		//HelpMenu
		helpMenuHelpButton = new Texture(("help.png"));
		helpMenuBackButton = new Texture("back.png");
		helpMenuOkButton = new Texture("ok.png");
		helpMenuLeftButton = new Texture(("left.png"));
		helpMenuRightButton = new Texture("right.png");
		helpMenuPlusButton = new Texture("plus.png");
		helpMenuMinusButton = new Texture(("minus.png"));
		helpMenuShootButton = new Texture("shoot.png");
		helpMenuLine = new Texture("line.png");


		//HitWaterUI
		Ball.location = new Vector2d(0, 0, 0);
		PuttingCourse.start = new Vector2d(10, 1, 1);
		Vector2d ballLocation = Ball.location;
		Vector2d startPoint = PuttingCourse.start;
		hitWaterUIMaxDistanceFromStart = Vector2d.getDistance(ballLocation, startPoint);

		hitWaterUIBatch = new SpriteBatch();
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


		//MainMenuScreen
		mainMenuScreenStartButton = new Texture("start.png");
		mainMenuScreenExitButton = new Texture("exit.png");
		mainMenuScreenSettingsButton = new Texture(("settings.png"));
		mainMenuScreenLine = new Texture("line.png");
		mainMenuScreenGolf = new Texture("golf.png");


		//SettingsMenu
		settingsMenuSettingsButton = new Texture(("settings.png"));
		settingsMenuBackButton = new Texture("back.png");
		settingsMenuOkButton = new Texture("ok.png");
		settingsMenuLine = new Texture("line.png");


		//StartMenu
		startMenuBackButton = new Texture("back.png");
		startMenuOkButton = new Texture("ok.png");
		startMenuLine = new Texture("line.png");

		//TextField for the path input
		startMenuStage = new Stage();
		Gdx.input.setInputProcessor(startMenuStage);
		Skin startMenuSkin = new Skin(Gdx.files.internal("skins/uiskin.json"));

		startMenuPath = new TextField("", startMenuSkin);
		startMenuPath.setPosition((GameUI.gameUI_WINDOW_WIDTH/2)+60, GameUI.gameUI_WINDOW_HEIGHT-470);
		startMenuPath.setSize(200, 20);
		startMenuPath.setColor(Color.FOREST);
		startMenuPath.setText("Your path:");
		startMenuStage.addActor(startMenuPath);


		//WonScreen
		//nothing inside
	}

	@Override
	public void render () {
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
			case ChoiceModeScreen:
				//ChoiceModeScreen
				choiceModeScreenGame.gameUIBatch.begin();
				FreeTypeFontGenerator choiceModeScreenGenerator = new FreeTypeFontGenerator(Gdx.files.internal("Georgia Italic.ttf"));

				FreeTypeFontGenerator.FreeTypeFontParameter choiceModeScreenParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
				choiceModeScreenParameter.size = 70;
				choiceModeScreenParameter.characters = "Choose Your Game Mode";
				choiceModeScreenTitle = choiceModeScreenGenerator.generateFont(choiceModeScreenParameter);
				choiceModeScreenTitle.setColor(Color.FOREST);
				choiceModeScreenGenerator.dispose();
				choiceModeScreenTitle.draw(choiceModeScreenGame.gameUIBatch, "Choose Your Game Mode", GameUI.gameUI_WINDOW_WIDTH/4-50, GameUI.gameUI_WINDOW_HEIGHT-100);

				choiceModeScreenGame.gameUIBatch.end();
				choiceModeScreenStage.draw();
				break;

			case CustomizedMenu:
				//CustomizedMenu
				customizedMenuGame.gameUIBatch.begin();

				FreeTypeFontGenerator customizedMenuGenerator = new FreeTypeFontGenerator(Gdx.files.internal("Georgia Italic.ttf"));
				FreeTypeFontGenerator customizedMenuWritingStyle = new FreeTypeFontGenerator(Gdx.files.internal("Courier New.ttf"));

				//Different texts
				FreeTypeFontGenerator.FreeTypeFontParameter customizedMenuParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
				FreeTypeFontGenerator.FreeTypeFontParameter customizedMenuVelo = new FreeTypeFontGenerator.FreeTypeFontParameter();
				FreeTypeFontGenerator.FreeTypeFontParameter customizedMenuAcc = new FreeTypeFontGenerator.FreeTypeFontParameter();
				FreeTypeFontGenerator.FreeTypeFontParameter customizedMenuMu = new FreeTypeFontGenerator.FreeTypeFontParameter();
				FreeTypeFontGenerator.FreeTypeFontParameter customizedMenuDist = new FreeTypeFontGenerator.FreeTypeFontParameter();
				FreeTypeFontGenerator.FreeTypeFontParameter customizedMenuWeight = new FreeTypeFontGenerator.FreeTypeFontParameter();
				FreeTypeFontGenerator.FreeTypeFontParameter customizedMenuHauteur = new FreeTypeFontGenerator.FreeTypeFontParameter();
				FreeTypeFontGenerator.FreeTypeFontParameter customizedMenuHauteur2 = new FreeTypeFontGenerator.FreeTypeFontParameter();
				FreeTypeFontGenerator.FreeTypeFontParameter customizedMenuHauteur3 = new FreeTypeFontGenerator.FreeTypeFontParameter();

				customizedMenuParameter.size = 60;
				customizedMenuVelo.size = 20;
				customizedMenuAcc.size = 20;
				customizedMenuMu.size = 20;
				customizedMenuDist.size = 20;
				customizedMenuWeight.size = 20;
				customizedMenuHauteur.size = 20;
				customizedMenuHauteur2.size = 20;
				customizedMenuHauteur3.size = 20;

				customizedMenuParameter.characters = "customized Menu";
				customizedMenuVelo.characters = "Initial Velocity: ";
				customizedMenuAcc.characters = "Acceleration: ";
				customizedMenuMu.characters = "Coefficient of friction: ";
				customizedMenuDist.characters = "Distance from the Hole: ";
				customizedMenuWeight.characters = "Mass of the ball: ";
				customizedMenuHauteur.characters = "Equation of the height: ";
				customizedMenuHauteur2.characters = "Write the equation in that form please: ";
				customizedMenuHauteur3.characters = "-0.01*x + 0.003*x^2 + 0.04 * y";

				customizedMenuFont = customizedMenuGenerator.generateFont(customizedMenuParameter);
				customizedMenuVelocity = customizedMenuWritingStyle.generateFont(customizedMenuVelo);
				customizedMenuAcceleration = customizedMenuWritingStyle.generateFont(customizedMenuAcc);
				customizedMenuCoefficient = customizedMenuWritingStyle.generateFont(customizedMenuMu);
				customizedMenuDistance = customizedMenuWritingStyle.generateFont(customizedMenuDist);
				customizedMenuMass = customizedMenuWritingStyle.generateFont(customizedMenuWeight);
				customizedMenuHeight = customizedMenuWritingStyle.generateFont(customizedMenuHauteur);
				customizedMenuHeight2 = customizedMenuWritingStyle.generateFont(customizedMenuHauteur2);
				customizedMenuHeight3 = customizedMenuWritingStyle.generateFont(customizedMenuHauteur3);

				customizedMenuFont.setColor(Color.FOREST);
				customizedMenuVelocity.setColor(Color.FOREST);
				customizedMenuAcceleration.setColor(Color.FOREST);
				customizedMenuCoefficient.setColor(Color.FOREST);
				customizedMenuDistance.setColor(Color.FOREST);
				customizedMenuMass.setColor(Color.FOREST);
				customizedMenuHeight.setColor(Color.FOREST);
				customizedMenuHeight2.setColor(Color.FOREST);
				customizedMenuHeight3.setColor(Color.FOREST);

				customizedMenuGenerator.dispose();

				customizedMenuFont.draw(customizedMenuGame.gameUIBatch, "customized Menu", GameUI.gameUI_WINDOW_WIDTH/3, GameUI.gameUI_WINDOW_HEIGHT-100);
				customizedMenuVelocity.draw(customizedMenuGame.gameUIBatch, "Initial Velocity: ", GameUI.gameUI_WINDOW_WIDTH/8, GameUI.gameUI_WINDOW_HEIGHT-300);
				customizedMenuAcceleration.draw(customizedMenuGame.gameUIBatch, "Acceleration: ", GameUI.gameUI_WINDOW_WIDTH/8, GameUI.gameUI_WINDOW_HEIGHT-350);
				customizedMenuCoefficient.draw(customizedMenuGame.gameUIBatch, "Coefficient of friction: ", GameUI.gameUI_WINDOW_WIDTH/8, GameUI.gameUI_WINDOW_HEIGHT-400);
				customizedMenuDistance.draw(customizedMenuGame.gameUIBatch, "Distance from the Hole: ", GameUI.gameUI_WINDOW_WIDTH/8, GameUI.gameUI_WINDOW_HEIGHT-450);
				customizedMenuMass.draw(customizedMenuGame.gameUIBatch, "Mass of the ball: ", GameUI.gameUI_WINDOW_WIDTH/8, GameUI.gameUI_WINDOW_HEIGHT-500);
				customizedMenuHeight.draw(customizedMenuGame.gameUIBatch, "Equation of the height: ", GameUI.gameUI_WINDOW_WIDTH/8, GameUI.gameUI_WINDOW_HEIGHT-550);
				customizedMenuHeight2.draw(customizedMenuGame.gameUIBatch, "Write the equation in that form please:", GameUI.gameUI_WINDOW_WIDTH/8, GameUI.gameUI_WINDOW_HEIGHT-600);
				customizedMenuHeight3.draw(customizedMenuGame.gameUIBatch, "-0.01*x + 0.003*x^2 + 0.04 * y", GameUI.gameUI_WINDOW_WIDTH/2, GameUI.gameUI_WINDOW_HEIGHT-600);

				//Drawing the line under the Back Button
				if(Gdx.input.getX() > GameUI.gameUI_WINDOW_WIDTH/10 && Gdx.input.getX() < (GameUI.gameUI_WINDOW_WIDTH/10) + customizedMenu_BACK_BUTTON_SIZE && GameUI.gameUI_WINDOW_HEIGHT - Gdx.input.getY() < customizedMenu_BACK_BUTTON_SIZE + GameUI.gameUI_WINDOW_HEIGHT-140 && GameUI.gameUI_WINDOW_HEIGHT - Gdx.input.getY() > GameUI.gameUI_WINDOW_HEIGHT-140 ){
					customizedMenuGame.gameUIBatch.draw(customizedMenuLine, (GameUI.gameUI_WINDOW_WIDTH)/10, (GameUI.gameUI_WINDOW_HEIGHT-220), customizedMenu_BACK_BUTTON_SIZE, 150);
					if(Gdx.input.justTouched()){
						this.dispose();
						customizedMenuGame.setScreen(new StartMenu(customizedMenuGame));
					}
				}

				//Drawing the line under the Ok button
				if(Gdx.input.getX() > GameUI.gameUI_WINDOW_WIDTH-150 && Gdx.input.getX() < (GameUI.gameUI_WINDOW_WIDTH-150) + customizedMenu_OK_BUTTON_WIDTH && GameUI.gameUI_WINDOW_HEIGHT - Gdx.input.getY() < customizedMenu_OK_BUTTON_HEIGHT + GameUI.gameUI_WINDOW_HEIGHT/10 && GameUI.gameUI_WINDOW_HEIGHT - Gdx.input.getY() > GameUI.gameUI_WINDOW_HEIGHT/10 ){
					customizedMenuGame.gameUIBatch.draw(customizedMenuLine, GameUI.gameUI_WINDOW_WIDTH-150, (GameUI.gameUI_WINDOW_HEIGHT/10)-60, customizedMenu_OK_BUTTON_WIDTH, 150);
					if(Gdx.input.justTouched()){
						this.dispose();
						customizedMenuGame.setScreen(new ChoiceModeScreen(customizedMenuGame));
//                System.out.println("Velocity: " + getcustomizedMenuVel());
//                System.out.println("Acceleration: " + getcustomizedMenuAcc());
//                System.out.println("Coefficient: " + getcustomizedMenuMu());
//                System.out.println("Distance: " + getcustomizedMenuDis());
//                System.out.println("Mass: " + getcustomizedMenuMas());
//                System.out.println("Height Equation: " + getcustomizedMenuHei());
					}
				}

				//Drawing the different buttons
				customizedMenuGame.gameUIBatch.draw(customizedMenuBackButton, GameUI.gameUI_WINDOW_WIDTH/10, GameUI.gameUI_WINDOW_HEIGHT-150, customizedMenu_BACK_BUTTON_SIZE, customizedMenu_BACK_BUTTON_SIZE);
				customizedMenuGame.gameUIBatch.draw(customizedMenuOkButton, GameUI.gameUI_WINDOW_WIDTH-150, GameUI.gameUI_WINDOW_HEIGHT/10, customizedMenu_OK_BUTTON_WIDTH, customizedMenu_OK_BUTTON_HEIGHT);

				//Drawing the line under the different texts
				//And get the input for each value when they're clicked

				FreeTypeFontGenerator.FreeTypeFontParameter customizedMenuVelo2 = new FreeTypeFontGenerator.FreeTypeFontParameter();
				customizedMenuVelo2.size = 100;
				customizedMenuVelo2.characters = " ";
				customizedMenuVelocity1 = customizedMenuWritingStyle.generateFont(customizedMenuVelo2);
				customizedMenuVelocity1.setColor(Color.FOREST);
				customizedMenuWritingStyle.dispose();
				customizedMenuVelocity1.draw(customizedMenuGame.gameUIBatch, " ", GameUI.gameUI_WINDOW_WIDTH/6, GameUI.gameUI_WINDOW_HEIGHT-380);

				//Velocity
				if(Gdx.input.getX() > GameUI.gameUI_WINDOW_WIDTH/8 && Gdx.input.getX() < (GameUI.gameUI_WINDOW_WIDTH/8) + 200 && GameUI.gameUI_WINDOW_HEIGHT - Gdx.input.getY() <  GameUI.gameUI_WINDOW_HEIGHT-300 && GameUI.gameUI_WINDOW_HEIGHT - Gdx.input.getY() > GameUI.gameUI_WINDOW_HEIGHT-330 ){
					customizedMenuGame.gameUIBatch.draw(customizedMenuLine, GameUI.gameUI_WINDOW_WIDTH/8, (GameUI.gameUI_WINDOW_HEIGHT)-380, 200, 150);
				}
				//Acceleration
				if(Gdx.input.getX() > GameUI.gameUI_WINDOW_WIDTH/8 && Gdx.input.getX() < (GameUI.gameUI_WINDOW_WIDTH/8) + 200 && GameUI.gameUI_WINDOW_HEIGHT - Gdx.input.getY() <  GameUI.gameUI_WINDOW_HEIGHT-350 && GameUI.gameUI_WINDOW_HEIGHT - Gdx.input.getY() > GameUI.gameUI_WINDOW_HEIGHT-380 ){
					customizedMenuGame.gameUIBatch.draw(customizedMenuLine, GameUI.gameUI_WINDOW_WIDTH/8, (GameUI.gameUI_WINDOW_HEIGHT)-430, 150, 150);
				}
				//Mu
				if(Gdx.input.getX() > GameUI.gameUI_WINDOW_WIDTH/8 && Gdx.input.getX() < (GameUI.gameUI_WINDOW_WIDTH/8) + 200 && GameUI.gameUI_WINDOW_HEIGHT - Gdx.input.getY() <  GameUI.gameUI_WINDOW_HEIGHT-400 && GameUI.gameUI_WINDOW_HEIGHT - Gdx.input.getY() > GameUI.gameUI_WINDOW_HEIGHT-430 ){
					customizedMenuGame.gameUIBatch.draw(customizedMenuLine, GameUI.gameUI_WINDOW_WIDTH/8, (GameUI.gameUI_WINDOW_HEIGHT)-480, 290, 150);
				}
				//Distance
				if(Gdx.input.getX() > GameUI.gameUI_WINDOW_WIDTH/8 && Gdx.input.getX() < (GameUI.gameUI_WINDOW_WIDTH/8) + 200 && GameUI.gameUI_WINDOW_HEIGHT - Gdx.input.getY() <  GameUI.gameUI_WINDOW_HEIGHT-450 && GameUI.gameUI_WINDOW_HEIGHT - Gdx.input.getY() > GameUI.gameUI_WINDOW_HEIGHT-480 ){
					customizedMenuGame.gameUIBatch.draw(customizedMenuLine, GameUI.gameUI_WINDOW_WIDTH/8, (GameUI.gameUI_WINDOW_HEIGHT)-530, 290, 150);
				}
				//Mass
				if(Gdx.input.getX() > GameUI.gameUI_WINDOW_WIDTH/8 && Gdx.input.getX() < (GameUI.gameUI_WINDOW_WIDTH/8) + 200 && GameUI.gameUI_WINDOW_HEIGHT - Gdx.input.getY() <  GameUI.gameUI_WINDOW_HEIGHT-500 && GameUI.gameUI_WINDOW_HEIGHT - Gdx.input.getY() > GameUI.gameUI_WINDOW_HEIGHT-530 ){
					customizedMenuGame.gameUIBatch.draw(customizedMenuLine, GameUI.gameUI_WINDOW_WIDTH/8, (GameUI.gameUI_WINDOW_HEIGHT)-580, 200, 150);
				}
				//Height equation
				if(Gdx.input.getX() > GameUI.gameUI_WINDOW_WIDTH/8 && Gdx.input.getX() < (GameUI.gameUI_WINDOW_WIDTH/8) + 200 && GameUI.gameUI_WINDOW_HEIGHT - Gdx.input.getY() <  GameUI.gameUI_WINDOW_HEIGHT-550 && GameUI.gameUI_WINDOW_HEIGHT - Gdx.input.getY() > GameUI.gameUI_WINDOW_HEIGHT-580 ){
					customizedMenuGame.gameUIBatch.draw(customizedMenuLine, GameUI.gameUI_WINDOW_WIDTH/8, (GameUI.gameUI_WINDOW_HEIGHT)-630, 290, 150);
				}
				customizedMenuGame.gameUIBatch.end();

				customizedMenuStage.draw();
				break;
			case DefaultMenu:
				//DefaultMenu
				defaultMenuGame.gameUIBatch.begin();

				FreeTypeFontGenerator defaultMenuGenerator = new FreeTypeFontGenerator(Gdx.files.internal("Georgia Italic.ttf"));
				FreeTypeFontGenerator defaultMenuWritingStyle = new FreeTypeFontGenerator(Gdx.files.internal("Courier New.ttf"));

				FreeTypeFontGenerator.FreeTypeFontParameter defaultMenuParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
				FreeTypeFontGenerator.FreeTypeFontParameter defaultMenuVelo = new FreeTypeFontGenerator.FreeTypeFontParameter();
				FreeTypeFontGenerator.FreeTypeFontParameter defaultMenuAcc = new FreeTypeFontGenerator.FreeTypeFontParameter();
				FreeTypeFontGenerator.FreeTypeFontParameter defaultMenuMu = new FreeTypeFontGenerator.FreeTypeFontParameter();
				FreeTypeFontGenerator.FreeTypeFontParameter defaultMenuDist = new FreeTypeFontGenerator.FreeTypeFontParameter();
				FreeTypeFontGenerator.FreeTypeFontParameter defaultMenuWeight = new FreeTypeFontGenerator.FreeTypeFontParameter();
				FreeTypeFontGenerator.FreeTypeFontParameter defaultMenuHauteur = new FreeTypeFontGenerator.FreeTypeFontParameter();

				defaultMenuParameter.size = 60;
				defaultMenuVelo.size = 20;
				defaultMenuAcc.size = 20;
				defaultMenuMu.size = 20;
				defaultMenuDist.size = 20;
				defaultMenuWeight.size = 20;
				defaultMenuHauteur.size = 20;

				defaultMenuParameter.characters = "Default Menu";
				defaultMenuVelo.characters = "Initial Velocity: ";
				defaultMenuAcc.characters = "Acceleration: ";
				defaultMenuMu.characters = "Coefficient of friction: ";
				defaultMenuDist.characters = "Distance from the Hole: ";
				defaultMenuWeight.characters = "Mass of the ball: ";
				defaultMenuHauteur.characters = "Equation of the height: ";

				defaultMenuFont = defaultMenuGenerator.generateFont(defaultMenuParameter);
				defaultMenuVelocity = defaultMenuWritingStyle.generateFont(defaultMenuVelo);
				defaultMenuAcceleration = defaultMenuWritingStyle.generateFont(defaultMenuAcc);
				defaultMenuCoefficient = defaultMenuWritingStyle.generateFont(defaultMenuMu);
				defaultMenuDistance = defaultMenuWritingStyle.generateFont(defaultMenuDist);
				defaultMenuMass = defaultMenuWritingStyle.generateFont(defaultMenuWeight);
				defaultMenuHeight = defaultMenuWritingStyle.generateFont(defaultMenuHauteur);

				defaultMenuFont.setColor(Color.FOREST);
				defaultMenuVelocity.setColor(Color.FOREST);
				defaultMenuAcceleration.setColor(Color.FOREST);
				defaultMenuCoefficient.setColor(Color.FOREST);
				defaultMenuDistance.setColor(Color.FOREST);
				defaultMenuMass.setColor(Color.FOREST);
				defaultMenuHeight.setColor(Color.FOREST);

				defaultMenuGenerator.dispose();

				defaultMenuFont.draw(defaultMenuGame.gameUIBatch, "Default Menu", GameUI.gameUI_WINDOW_WIDTH/3, GameUI.gameUI_WINDOW_HEIGHT-100);
				defaultMenuVelocity.draw(defaultMenuGame.gameUIBatch, "Initial Velocity: ", GameUI.gameUI_WINDOW_WIDTH/8, GameUI.gameUI_WINDOW_HEIGHT-300);
				defaultMenuAcceleration.draw(defaultMenuGame.gameUIBatch, "Acceleration: ", GameUI.gameUI_WINDOW_WIDTH/8, GameUI.gameUI_WINDOW_HEIGHT-350);
				defaultMenuCoefficient.draw(defaultMenuGame.gameUIBatch, "Coefficient of friction: ", GameUI.gameUI_WINDOW_WIDTH/8, GameUI.gameUI_WINDOW_HEIGHT-400);
				defaultMenuDistance.draw(defaultMenuGame.gameUIBatch, "Distance from the Hole: ", GameUI.gameUI_WINDOW_WIDTH/8, GameUI.gameUI_WINDOW_HEIGHT-450);
				defaultMenuMass.draw(defaultMenuGame.gameUIBatch, "Mass of the ball: ", GameUI.gameUI_WINDOW_WIDTH/8, GameUI.gameUI_WINDOW_HEIGHT-500);
				defaultMenuHeight.draw(defaultMenuGame.gameUIBatch, "Equation of the height: ", GameUI.gameUI_WINDOW_WIDTH/8, GameUI.gameUI_WINDOW_HEIGHT-550);

				//Default Input
				FreeTypeFontGenerator.FreeTypeFontParameter defaultMenuVelo1 = new FreeTypeFontGenerator.FreeTypeFontParameter();
				FreeTypeFontGenerator.FreeTypeFontParameter defaultMenuAcc1 = new FreeTypeFontGenerator.FreeTypeFontParameter();
				FreeTypeFontGenerator.FreeTypeFontParameter defaultMenuMu1 = new FreeTypeFontGenerator.FreeTypeFontParameter();
				FreeTypeFontGenerator.FreeTypeFontParameter defaultMenuDist1 = new FreeTypeFontGenerator.FreeTypeFontParameter();
				FreeTypeFontGenerator.FreeTypeFontParameter defaultMenuWeight1 = new FreeTypeFontGenerator.FreeTypeFontParameter();
				FreeTypeFontGenerator.FreeTypeFontParameter defaultMenuHauteur1 = new FreeTypeFontGenerator.FreeTypeFontParameter();

				defaultMenuVelo1.size = 20;
				defaultMenuAcc1.size = 20;
				defaultMenuMu1.size = 20;
				defaultMenuDist1.size = 20;
				defaultMenuWeight1.size = 20;
				defaultMenuHauteur1.size = 20;

				defaultMenuVelo1.characters = "0 m/s";
				defaultMenuAcc1.characters = "9.81 m/s^2";
				defaultMenuMu1.characters = "0.131";
				defaultMenuDist1.characters = "0.02 m";
				defaultMenuWeight1.characters = "45.93 g";
				defaultMenuHauteur1.characters = "-0.01*x + 0.003*x^2 + 0.04 * y";

				defaultMenuVelocity1 = defaultMenuWritingStyle.generateFont(defaultMenuVelo1);
				defaultMenuAcceleration1 = defaultMenuWritingStyle.generateFont(defaultMenuAcc1);
				defaultMenuCoefficient1 = defaultMenuWritingStyle.generateFont(defaultMenuMu1);
				defaultMenuDistance1 = defaultMenuWritingStyle.generateFont(defaultMenuDist1);
				defaultMenuMass1 = defaultMenuWritingStyle.generateFont(defaultMenuWeight1);
				defaultMenuHeight1 = defaultMenuWritingStyle.generateFont(defaultMenuHauteur1);

				defaultMenuVelocity1.setColor(Color.FOREST);
				defaultMenuAcceleration1.setColor(Color.FOREST);
				defaultMenuCoefficient1.setColor(Color.FOREST);
				defaultMenuDistance1.setColor(Color.FOREST);
				defaultMenuMass1.setColor(Color.FOREST);
				defaultMenuHeight1.setColor(Color.FOREST);

				defaultMenuVelocity1.draw(defaultMenuGame.gameUIBatch, "0 m/s", GameUI.gameUI_WINDOW_WIDTH/3 + 50, GameUI.gameUI_WINDOW_HEIGHT-300);
				defaultMenuAcceleration1.draw(defaultMenuGame.gameUIBatch, "9.81 m/s^2", GameUI.gameUI_WINDOW_WIDTH/3+50, GameUI.gameUI_WINDOW_HEIGHT-350);
				defaultMenuCoefficient1.draw(defaultMenuGame.gameUIBatch, "0.131", (GameUI.gameUI_WINDOW_WIDTH/3) + 50, GameUI.gameUI_WINDOW_HEIGHT-400);
				defaultMenuDistance1.draw(defaultMenuGame.gameUIBatch, "0.02 m", (GameUI.gameUI_WINDOW_WIDTH/3) + 50, GameUI.gameUI_WINDOW_HEIGHT-450);
				defaultMenuMass1.draw(defaultMenuGame.gameUIBatch, "45.93 g", (GameUI.gameUI_WINDOW_WIDTH/3) + 50, GameUI.gameUI_WINDOW_HEIGHT-500);
				defaultMenuHeight1.draw(defaultMenuGame.gameUIBatch, "-0.01*x + 0.003*x^2 + 0.04 * y", (GameUI.gameUI_WINDOW_WIDTH/3)+50, GameUI.gameUI_WINDOW_HEIGHT-550);

				//Draw the line under the Back Button
				if(Gdx.input.getX() > GameUI.gameUI_WINDOW_WIDTH/10 && Gdx.input.getX() < (GameUI.gameUI_WINDOW_WIDTH/10) + defaultMenu_BACK_BUTTON_SIZE && GameUI.gameUI_WINDOW_HEIGHT - Gdx.input.getY() < defaultMenu_BACK_BUTTON_SIZE + GameUI.gameUI_WINDOW_HEIGHT-140 && GameUI.gameUI_WINDOW_HEIGHT - Gdx.input.getY() > GameUI.gameUI_WINDOW_HEIGHT-140 ){
					defaultMenuGame.gameUIBatch.draw(defaultMenuLine, (GameUI.gameUI_WINDOW_WIDTH)/10, (GameUI.gameUI_WINDOW_HEIGHT-220), defaultMenu_BACK_BUTTON_SIZE, 150);
					if(Gdx.input.justTouched()){
						this.dispose();
						defaultMenuGame.setScreen(new StartMenu(defaultMenuGame));
					}
				}
				//Draw the line under the Ok button
				if(Gdx.input.getX() > GameUI.gameUI_WINDOW_WIDTH-150 && Gdx.input.getX() < (GameUI.gameUI_WINDOW_WIDTH-150) + defaultMenu_OK_BUTTON_WIDTH && GameUI.gameUI_WINDOW_HEIGHT - Gdx.input.getY() < defaultMenu_OK_BUTTON_HEIGHT + GameUI.gameUI_WINDOW_HEIGHT/10 && GameUI.gameUI_WINDOW_HEIGHT - Gdx.input.getY() > GameUI.gameUI_WINDOW_HEIGHT/10 ){
					defaultMenuGame.gameUIBatch.draw(defaultMenuLine, GameUI.gameUI_WINDOW_WIDTH-150, (GameUI.gameUI_WINDOW_HEIGHT/10)-60, defaultMenu_OK_BUTTON_WIDTH, 150);
					if(Gdx.input.justTouched()){
						this.dispose();
						defaultMenuGame.setScreen(new ChoiceModeScreen(defaultMenuGame));
					}
				}

				//Draw the buttons
				defaultMenuGame.gameUIBatch.draw(defaultMenuBackButton, GameUI.gameUI_WINDOW_WIDTH/10, GameUI.gameUI_WINDOW_HEIGHT-150, defaultMenu_BACK_BUTTON_SIZE, defaultMenu_BACK_BUTTON_SIZE);
				defaultMenuGame.gameUIBatch.draw(defaultMenuOkButton, GameUI.gameUI_WINDOW_WIDTH-150, GameUI.gameUI_WINDOW_HEIGHT/10, defaultMenu_OK_BUTTON_WIDTH, defaultMenu_OK_BUTTON_HEIGHT);

				defaultMenuGame.gameUIBatch.end();
				break;
			case GamePlay:
				//GamePlay
				gamePlayGame.gameUIBatch.begin();
				FreeTypeFontGenerator gamePlayGenerator = new FreeTypeFontGenerator(Gdx.files.internal("Georgia Italic.ttf"));

				FreeTypeFontGenerator.FreeTypeFontParameter gamePlayParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
				gamePlayParameter.size = 100;
				gamePlayParameter.characters = "Game";
				gamePlayFont = gamePlayGenerator.generateFont(gamePlayParameter);
				gamePlayFont.setColor(Color.FOREST);
				gamePlayGenerator.dispose();
				gamePlayFont.draw(gamePlayGame.gameUIBatch, "Game", GameUI.gameUI_WINDOW_WIDTH/3, GameUI.gameUI_WINDOW_HEIGHT-100);

				if(Gdx.input.getX() > GameUI.gameUI_WINDOW_WIDTH/10 && Gdx.input.getX() < (GameUI.gameUI_WINDOW_WIDTH/10) + gamePlay_BACK_BUTTON_SIZE && GameUI.gameUI_WINDOW_HEIGHT - Gdx.input.getY() < gamePlay_BACK_BUTTON_SIZE + GameUI.gameUI_WINDOW_HEIGHT-140 && GameUI.gameUI_WINDOW_HEIGHT - Gdx.input.getY() > GameUI.gameUI_WINDOW_HEIGHT-140 ){
					gamePlayGame.gameUIBatch.draw(gamePlayLine, (GameUI.gameUI_WINDOW_WIDTH)/10, (GameUI.gameUI_WINDOW_HEIGHT-220), gamePlay_BACK_BUTTON_SIZE, 150);
					if(Gdx.input.justTouched()){
						this.dispose();
						gamePlayGame.setScreen(new StartMenu(gamePlayGame));
					}
				}

				gamePlayGame.gameUIBatch.draw(gamePlayBackButton, GameUI.gameUI_WINDOW_WIDTH/10, GameUI.gameUI_WINDOW_HEIGHT-150, gamePlay_BACK_BUTTON_SIZE, gamePlay_BACK_BUTTON_SIZE);
				gamePlayGame.gameUIBatch.end();
				break;
			case GameUI:
				//GameUI
				super.render();
				break;
			case HelpMenu:
				//HelpMenu
				helpMenuHelp.gameUIBatch.begin();

				FreeTypeFontGenerator helpMenuGenerator = new FreeTypeFontGenerator(Gdx.files.internal("Georgia Italic.ttf"));
				FreeTypeFontGenerator.FreeTypeFontParameter helpMenuParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
				helpMenuParameter.size = 50;
				helpMenuParameter.characters = "Command";
				helpMenuFont = helpMenuGenerator.generateFont(helpMenuParameter);
				helpMenuFont.setColor(Color.FOREST);
				helpMenuGenerator.dispose();
				helpMenuFont.draw(helpMenuHelp.gameUIBatch, "Command", GameUI.gameUI_WINDOW_WIDTH-750, GameUI.gameUI_WINDOW_HEIGHT-100);

				if(Gdx.input.getX() > GameUI.gameUI_WINDOW_WIDTH-150 && Gdx.input.getX() < (GameUI.gameUI_WINDOW_WIDTH-150) + helpMenu_OK_BUTTON_WIDTH && GameUI.gameUI_WINDOW_HEIGHT - Gdx.input.getY() < helpMenu_OK_BUTTON_HEIGHT + GameUI.gameUI_WINDOW_HEIGHT/10 && GameUI.gameUI_WINDOW_HEIGHT - Gdx.input.getY() > GameUI.gameUI_WINDOW_HEIGHT/10 ){
					helpMenuHelp.gameUIBatch.draw(helpMenuLine, GameUI.gameUI_WINDOW_WIDTH-150, (GameUI.gameUI_WINDOW_HEIGHT/10)-60, helpMenu_OK_BUTTON_WIDTH, 150);
					if(Gdx.input.justTouched()){
						this.dispose();
						helpMenuHelp.setScreen(new MainMenuScreen(helpMenuHelp));
					}
				}
				if(Gdx.input.getX() > GameUI.gameUI_WINDOW_WIDTH/10 && Gdx.input.getX() < (GameUI.gameUI_WINDOW_WIDTH/10) + helpMenu_BACK_BUTTON_SIZE && GameUI.gameUI_WINDOW_HEIGHT - Gdx.input.getY() < helpMenu_BACK_BUTTON_SIZE + GameUI.gameUI_WINDOW_HEIGHT-140 && GameUI.gameUI_WINDOW_HEIGHT - Gdx.input.getY() > GameUI.gameUI_WINDOW_HEIGHT-140 ){
					helpMenuHelp.gameUIBatch.draw(helpMenuLine, (GameUI.gameUI_WINDOW_WIDTH)/10, (GameUI.gameUI_WINDOW_HEIGHT-220), helpMenu_BACK_BUTTON_SIZE, 150);
					if(Gdx.input.justTouched()){
						this.dispose();
						helpMenuHelp.setScreen(new MainMenuScreen(helpMenuHelp));
					}
				}

				helpMenuHelp.gameUIBatch.draw(helpMenuHelpButton, GameUI.gameUI_WINDOW_WIDTH-200, GameUI.gameUI_WINDOW_HEIGHT - 150, helpMenu_HELP_BUTTON_SIZE, helpMenu_HELP_BUTTON_SIZE);
				helpMenuHelp.gameUIBatch.draw(helpMenuOkButton, GameUI.gameUI_WINDOW_WIDTH-150, GameUI.gameUI_WINDOW_HEIGHT/10, helpMenu_OK_BUTTON_WIDTH, helpMenu_OK_BUTTON_HEIGHT);
				helpMenuHelp.gameUIBatch.draw(helpMenuBackButton, GameUI.gameUI_WINDOW_WIDTH/10, GameUI.gameUI_WINDOW_HEIGHT-150, helpMenu_BACK_BUTTON_SIZE, helpMenu_BACK_BUTTON_SIZE);
				helpMenuHelp.gameUIBatch.draw(helpMenuLeftButton, GameUI.gameUI_WINDOW_WIDTH/4, GameUI.gameUI_WINDOW_HEIGHT-300, helpMenu_LEFT_BUTTON_WIDTH, helpMenu_LEFT_BUTTON_HEIGHT);
				helpMenuHelp.gameUIBatch.draw(helpMenuRightButton, GameUI.gameUI_WINDOW_WIDTH/4, GameUI.gameUI_WINDOW_HEIGHT - 400, helpMenu_RIGHT_BUTTON_WIDTH, helpMenu_RIGHT_BUTTON_HEIGHT);
				helpMenuHelp.gameUIBatch.draw(helpMenuPlusButton, GameUI.gameUI_WINDOW_WIDTH/4, GameUI.gameUI_WINDOW_HEIGHT-500, helpMenu_PLUS_BUTTON_SIZE, helpMenu_PLUS_BUTTON_SIZE);
				helpMenuHelp.gameUIBatch.draw(helpMenuMinusButton, GameUI.gameUI_WINDOW_WIDTH/4, GameUI.gameUI_WINDOW_HEIGHT-600, helpMenu_MINUS_BUTTON_SIZE, helpMenu_MINUS_BUTTON_SIZE);
				helpMenuHelp.gameUIBatch.draw(helpMenuShootButton, GameUI.gameUI_WINDOW_WIDTH/4, GameUI.gameUI_WINDOW_HEIGHT-700, helpMenu_SHOOT_BUTTON_SIZE, helpMenu_SHOOT_BUTTON_SIZE);

				//write the different rules
				FreeTypeFontGenerator helpMenuWritingStyle = new FreeTypeFontGenerator(Gdx.files.internal("Courier New.ttf"));

				FreeTypeFontGenerator.FreeTypeFontParameter helpMenuLeftR = new FreeTypeFontGenerator.FreeTypeFontParameter();
				FreeTypeFontGenerator.FreeTypeFontParameter helpMenuRightR = new FreeTypeFontGenerator.FreeTypeFontParameter();
				FreeTypeFontGenerator.FreeTypeFontParameter helpMenuPlusR = new FreeTypeFontGenerator.FreeTypeFontParameter();
				FreeTypeFontGenerator.FreeTypeFontParameter helpMenuMinusR = new FreeTypeFontGenerator.FreeTypeFontParameter();
				FreeTypeFontGenerator.FreeTypeFontParameter helpMenuShootR = new FreeTypeFontGenerator.FreeTypeFontParameter();

				helpMenuLeftR.size = 30;
				helpMenuRightR.size = 30;
				helpMenuMinusR.size = 30;
				helpMenuPlusR.size = 30;
				helpMenuShootR.size = 30;

				helpMenuLeftR.characters = "Change direction to the left";
				helpMenuRightR.characters = "Change direction to the right";
				helpMenuPlusR.characters = "Increases the power of the shoot";
				helpMenuMinusR.characters = "Decreases the power of the shoot";
				helpMenuShootR.characters = "Shoot the ball";

				helpMenuLeftRule = helpMenuWritingStyle.generateFont(helpMenuLeftR);
				helpMenuRightRule = helpMenuWritingStyle.generateFont(helpMenuRightR);
				helpMenuPlusRule = helpMenuWritingStyle.generateFont(helpMenuPlusR);
				helpMenuMinusRule = helpMenuWritingStyle.generateFont(helpMenuMinusR);
				helpMenuShootRule = helpMenuWritingStyle.generateFont(helpMenuShootR);

				helpMenuLeftRule.setColor(Color.FOREST);
				helpMenuRightRule.setColor(Color.FOREST);
				helpMenuPlusRule.setColor(Color.FOREST);
				helpMenuMinusRule.setColor(Color.FOREST);
				helpMenuShootRule.setColor(Color.FOREST);

				helpMenuWritingStyle.dispose();
				helpMenuLeftRule.draw(helpMenuHelp.gameUIBatch, "Change direction to the left", GameUI.gameUI_WINDOW_WIDTH/3, GameUI.gameUI_WINDOW_HEIGHT-250);
				helpMenuRightRule.draw(helpMenuHelp.gameUIBatch, "Change direction to the right", GameUI.gameUI_WINDOW_WIDTH/3, GameUI.gameUI_WINDOW_HEIGHT-350);
				helpMenuPlusRule.draw(helpMenuHelp.gameUIBatch, "Increases the power of the shoot", GameUI.gameUI_WINDOW_WIDTH/3, GameUI.gameUI_WINDOW_HEIGHT-450);
				helpMenuMinusRule.draw(helpMenuHelp.gameUIBatch, "Decreases the power of the shoot", GameUI.gameUI_WINDOW_WIDTH/3, GameUI.gameUI_WINDOW_HEIGHT-550);
				helpMenuShootRule.draw(helpMenuHelp.gameUIBatch, "Shoot the ball", GameUI.gameUI_WINDOW_WIDTH/3, GameUI.gameUI_WINDOW_HEIGHT-650);

				helpMenuHelp.gameUIBatch.end();
				break;

			case HitWaterUI:
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

				hitWaterUIBatch.begin();
				hitWaterUIFont.setColor(Color.BLACK);
				hitWaterUIWhiteBackground.draw(hitWaterUIBatch);
				hitWaterUIFont.draw(hitWaterUIBatch, hitWaterUIDistanceFromStartString, Gdx.graphics.getWidth()-hitWaterUIBackgroundWidth,
						Gdx.graphics.getHeight() * 9/10, hitWaterUIBackgroundWidth, 1, true);
				hitWaterUIFont.draw(hitWaterUIBatch, String.valueOf(distanceFromStartRounded), Gdx.graphics.getWidth()-hitWaterUIBackgroundWidth,
						Gdx.graphics.getHeight() * 7/10, hitWaterUIBackgroundWidth, 1, true);
				hitWaterUIBatch.end();

				hitWaterUIStage.draw();
				break;

			case MainMenuScreen:
				//MainMenuScreen
				FreeTypeFontGenerator mainMenuScreenGenerator = new FreeTypeFontGenerator(Gdx.files.internal("Georgia Italic.ttf"));

				FreeTypeFontGenerator.FreeTypeFontParameter mainMenuScreenParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
				FreeTypeFontGenerator.FreeTypeFontParameter mainMenuScreenGroup4 = new FreeTypeFontGenerator.FreeTypeFontParameter();
				mainMenuScreenParameter.size = 90;
				mainMenuScreenParameter.characters = "Mini Golf";
				mainMenuScreenGroup4.characters = "Group 4";
				mainMenuScreenGroup4.size = 30;

				mainMenuScreenFont = mainMenuScreenGenerator.generateFont(mainMenuScreenParameter);
				mainMenuScreenFont1 = mainMenuScreenGenerator.generateFont(mainMenuScreenGroup4);
				mainMenuScreenFont.setColor(Color.FOREST);
				mainMenuScreenFont1.setColor(Color.FOREST);
				mainMenuScreenGenerator.dispose();

				mainMenuScreenGame.gameUIBatch.begin();
				mainMenuScreenFont.draw(mainMenuScreenGame.gameUIBatch, " Mini Golf", GameUI.gameUI_WINDOW_WIDTH/5, GameUI.gameUI_WINDOW_HEIGHT-100);
				mainMenuScreenFont1.draw(mainMenuScreenGame.gameUIBatch, "Group 4", GameUI.gameUI_WINDOW_WIDTH / 10, GameUI.gameUI_WINDOW_HEIGHT - 50);

				if(Gdx.input.getX() > GameUI.gameUI_WINDOW_WIDTH/4 && Gdx.input.getX() < GameUI.gameUI_WINDOW_WIDTH/5 + mainMenuScreen_START_BUTTON_WIDTH && GameUI.gameUI_WINDOW_HEIGHT - Gdx.input.getY() < mainMenuScreen_START_BUTTON_HEIGHT + (GameUI.gameUI_WINDOW_HEIGHT/7)-150 && GameUI.gameUI_WINDOW_HEIGHT - Gdx.input.getY() > (GameUI.gameUI_WINDOW_HEIGHT/7)+80){
					mainMenuScreenGame.gameUIBatch.draw(mainMenuScreenLine, (GameUI.gameUI_WINDOW_WIDTH/4) +50, (GameUI.gameUI_WINDOW_HEIGHT/7)+80, mainMenuScreen_START_BUTTON_WIDTH-120, 150);
					if(Gdx.input.justTouched()){
						this.dispose();
						mainMenuScreenGame.setScreen(new StartMenu(mainMenuScreenGame));
					}
				}
				if(Gdx.input.getX() > GameUI.gameUI_WINDOW_WIDTH/10 && Gdx.input.getX() < GameUI.gameUI_WINDOW_WIDTH/10 + mainMenuScreen_EXIT_BUTTON_WIDTH && GameUI.gameUI_WINDOW_HEIGHT - Gdx.input.getY() < mainMenuScreen_EXIT_BUTTON_HEIGHT + GameUI.gameUI_WINDOW_HEIGHT/10 && GameUI.gameUI_WINDOW_HEIGHT - Gdx.input.getY() > GameUI.gameUI_WINDOW_HEIGHT/10){
					mainMenuScreenGame.gameUIBatch.draw(mainMenuScreenLine, (GameUI.gameUI_WINDOW_WIDTH/10), (GameUI.gameUI_WINDOW_HEIGHT/10)-50, mainMenuScreen_EXIT_BUTTON_WIDTH, 150);
					if(Gdx.input.justTouched()){
						Gdx.app.exit();
					}
				}
				if(Gdx.input.getX() > GameUI.gameUI_WINDOW_WIDTH-150 && Gdx.input.getX() < GameUI.gameUI_WINDOW_WIDTH-150 + mainMenuScreen_SETTINGS_BUTTON_SIZE && GameUI.gameUI_WINDOW_HEIGHT - Gdx.input.getY() < mainMenuScreen_SETTINGS_BUTTON_SIZE + GameUI.gameUI_WINDOW_HEIGHT/10 && GameUI.gameUI_WINDOW_HEIGHT - Gdx.input.getY() > GameUI.gameUI_WINDOW_HEIGHT/10){
					mainMenuScreenGame.gameUIBatch.draw(mainMenuScreenLine, (GameUI.gameUI_WINDOW_WIDTH)-150, (GameUI.gameUI_WINDOW_HEIGHT/10) - 55, mainMenuScreen_SETTINGS_BUTTON_SIZE, 150);
					if(Gdx.input.justTouched()){
						this.dispose();
						mainMenuScreenGame.setScreen(new SettingsMenu(mainMenuScreenGame));
					}
				}

				mainMenuScreenGame.gameUIBatch.draw(mainMenuScreenStartButton, GameUI.gameUI_WINDOW_WIDTH/4, GameUI.gameUI_WINDOW_HEIGHT/8, mainMenuScreen_START_BUTTON_WIDTH, mainMenuScreen_START_BUTTON_HEIGHT);
				mainMenuScreenGame.gameUIBatch.draw(mainMenuScreenExitButton, GameUI.gameUI_WINDOW_WIDTH/10, GameUI.gameUI_WINDOW_HEIGHT/10, mainMenuScreen_EXIT_BUTTON_WIDTH, mainMenuScreen_EXIT_BUTTON_HEIGHT);
				mainMenuScreenGame.gameUIBatch.draw(mainMenuScreenSettingsButton, GameUI.gameUI_WINDOW_WIDTH - 150, GameUI.gameUI_WINDOW_HEIGHT / 10, mainMenuScreen_SETTINGS_BUTTON_SIZE, mainMenuScreen_SETTINGS_BUTTON_SIZE);
				mainMenuScreenGame.gameUIBatch.draw(mainMenuScreenGolf, GameUI.gameUI_WINDOW_WIDTH-480, GameUI.gameUI_WINDOW_HEIGHT/2, mainMenuScreen_GOLF_IMAGE_WIDTH, mainMenuScreen_GOLF_IMAGE_HEIGHT);
				mainMenuScreenGame.gameUIBatch.end();

			case SettingsMenu:
				//SettingsMenu
				settingsMenuSettings.gameUIBatch.begin();

				FreeTypeFontGenerator settingsMenuGenerator = new FreeTypeFontGenerator(Gdx.files.internal("Georgia Italic.ttf"));
				FreeTypeFontGenerator.FreeTypeFontParameter settingsMenuParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
				settingsMenuParameter.size = 50;
				settingsMenuParameter.characters = "Settings";
				settingsMenuFont = settingsMenuGenerator.generateFont(settingsMenuParameter);
				settingsMenuFont.setColor(Color.FOREST);
				settingsMenuGenerator.dispose();
				settingsMenuFont.draw(settingsMenuSettings.gameUIBatch, "Settings", GameUI.gameUI_WINDOW_WIDTH/3, GameUI.gameUI_WINDOW_HEIGHT-100);

				if(Gdx.input.getX() > GameUI.gameUI_WINDOW_WIDTH-150 && Gdx.input.getX() < (GameUI.gameUI_WINDOW_WIDTH-150) + settingsMenu_OK_BUTTON_WIDTH && GameUI.gameUI_WINDOW_HEIGHT - Gdx.input.getY() < settingsMenu_OK_BUTTON_HEIGHT + GameUI.gameUI_WINDOW_HEIGHT/10 && GameUI.gameUI_WINDOW_HEIGHT - Gdx.input.getY() > GameUI.gameUI_WINDOW_HEIGHT/10 ){
					settingsMenuSettings.gameUIBatch.draw(settingsMenuLine, GameUI.gameUI_WINDOW_WIDTH-150, (GameUI.gameUI_WINDOW_HEIGHT/10)-60, settingsMenu_OK_BUTTON_WIDTH, 150);
					if(Gdx.input.justTouched()){
						this.dispose();
						settingsMenuSettings.setScreen(new MainMenuScreen(settingsMenuSettings));
					}
				}
				if(Gdx.input.getX() > GameUI.gameUI_WINDOW_WIDTH/10 && Gdx.input.getX() < (GameUI.gameUI_WINDOW_WIDTH/10) + settingsMenu_BACK_BUTTON_SIZE && GameUI.gameUI_WINDOW_HEIGHT - Gdx.input.getY() < settingsMenu_BACK_BUTTON_SIZE + GameUI.gameUI_WINDOW_HEIGHT-140 && GameUI.gameUI_WINDOW_HEIGHT - Gdx.input.getY() > GameUI.gameUI_WINDOW_HEIGHT-140 ){
					settingsMenuSettings.gameUIBatch.draw(settingsMenuLine, (GameUI.gameUI_WINDOW_WIDTH)/10, (GameUI.gameUI_WINDOW_HEIGHT-220), settingsMenu_BACK_BUTTON_SIZE, 150);
					if(Gdx.input.justTouched()){
						this.dispose();
						settingsMenuSettings.setScreen(new MainMenuScreen(settingsMenuSettings));
					}
				}

				settingsMenuSettings.gameUIBatch.draw(settingsMenuSettingsButton, GameUI.gameUI_WINDOW_WIDTH*2/3, GameUI.gameUI_WINDOW_HEIGHT - 150, settingsMenu_SETTINGS_BUTTON_SIZE, settingsMenu_SETTINGS_BUTTON_SIZE);
				settingsMenuSettings.gameUIBatch.draw(settingsMenuOkButton, GameUI.gameUI_WINDOW_WIDTH-150, GameUI.gameUI_WINDOW_HEIGHT/10, settingsMenu_OK_BUTTON_WIDTH, settingsMenu_OK_BUTTON_HEIGHT);
				settingsMenuSettings.gameUIBatch.draw(settingsMenuBackButton, GameUI.gameUI_WINDOW_WIDTH/10, GameUI.gameUI_WINDOW_HEIGHT-150, settingsMenu_BACK_BUTTON_SIZE, settingsMenu_BACK_BUTTON_SIZE);

				settingsMenuSettings.gameUIBatch.end();
				break;

			case StartMenu:
				//StartMenu
				startMenuStart.gameUIBatch.begin();
				FreeTypeFontGenerator startMenuGenerator = new FreeTypeFontGenerator(Gdx.files.internal("Georgia Italic.ttf"));
				FreeTypeFontGenerator startMenuWritingStyle = new FreeTypeFontGenerator(Gdx.files.internal("Courier New.ttf"));

				FreeTypeFontGenerator.FreeTypeFontParameter startMenuParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
				FreeTypeFontGenerator.FreeTypeFontParameter startMenuMakeChoice = new FreeTypeFontGenerator.FreeTypeFontParameter();
				FreeTypeFontGenerator.FreeTypeFontParameter startMenuBasic = new FreeTypeFontGenerator.FreeTypeFontParameter();
				FreeTypeFontGenerator.FreeTypeFontParameter startMenuCusto = new FreeTypeFontGenerator.FreeTypeFontParameter();
				FreeTypeFontGenerator.FreeTypeFontParameter startMenuOwn = new FreeTypeFontGenerator.FreeTypeFontParameter();

				startMenuParameter.size = 90;
				startMenuMakeChoice.size = 40;
				startMenuBasic.size = 30;
				startMenuCusto.size = 30;
				startMenuOwn.size = 30;

				startMenuParameter.characters = "Start";
				startMenuMakeChoice.characters = "Which set of parameters do you want to use?";
				startMenuBasic.characters = "Default";
				startMenuCusto.characters = "Customized";
				startMenuOwn.characters = "Path to your startMenuOwn parameters:";

				startMenuFont = startMenuGenerator.generateFont(startMenuParameter);
				startMenuChoice = startMenuWritingStyle.generateFont(startMenuMakeChoice);
				startMenuFont1 = startMenuWritingStyle.generateFont(startMenuBasic);
				startMenuCustomize = startMenuWritingStyle.generateFont(startMenuCusto);
				startMenuOwnPath = startMenuWritingStyle.generateFont(startMenuOwn);

				startMenuFont.setColor(Color.FOREST);
				startMenuChoice.setColor(Color.FOREST);
				startMenuFont1.setColor(Color.FOREST);
				startMenuCustomize.setColor(Color.FOREST);
				startMenuOwnPath.setColor(Color.FOREST);

				startMenuGenerator.dispose();

				startMenuFont.draw(startMenuStart.gameUIBatch, "Start", GameUI.gameUI_WINDOW_WIDTH-750, GameUI.gameUI_WINDOW_HEIGHT-100);
				startMenuChoice.draw(startMenuStart.gameUIBatch, "Which set of parameters do you want to use?", GameUI.gameUI_WINDOW_WIDTH/7, GameUI.gameUI_WINDOW_HEIGHT-200);
				startMenuFont1.draw(startMenuStart.gameUIBatch, "Default", GameUI.gameUI_WINDOW_WIDTH/7, GameUI.gameUI_WINDOW_HEIGHT-350);
				startMenuCustomize.draw(startMenuStart.gameUIBatch, "Customized", GameUI.gameUI_WINDOW_WIDTH/7, GameUI.gameUI_WINDOW_HEIGHT-400);
				startMenuOwnPath.draw(startMenuStart.gameUIBatch, "Path to your startMenuOwn parameters:", GameUI.gameUI_WINDOW_WIDTH/7, GameUI.gameUI_WINDOW_HEIGHT-450);

				//Menu choice
				if(Gdx.input.getX() > GameUI.gameUI_WINDOW_WIDTH/7 && Gdx.input.getX() < (GameUI.gameUI_WINDOW_WIDTH/7) + 200 && GameUI.gameUI_WINDOW_HEIGHT - Gdx.input.getY() <  GameUI.gameUI_WINDOW_HEIGHT-350 && GameUI.gameUI_WINDOW_HEIGHT - Gdx.input.getY() > GameUI.gameUI_WINDOW_HEIGHT-400 ){
					startMenuStart.gameUIBatch.draw(startMenuLine, (GameUI.gameUI_WINDOW_WIDTH)/7, (GameUI.gameUI_WINDOW_HEIGHT-435), 120, 150);
					if(Gdx.input.justTouched()){
						startMenuStart.setScreen(new DefaultMenu(startMenuStart));
					}
				}

				if(Gdx.input.getX() > GameUI.gameUI_WINDOW_WIDTH/7 && Gdx.input.getX() < (GameUI.gameUI_WINDOW_WIDTH/7) + 200 && GameUI.gameUI_WINDOW_HEIGHT - Gdx.input.getY() <  GameUI.gameUI_WINDOW_HEIGHT-400 && GameUI.gameUI_WINDOW_HEIGHT - Gdx.input.getY() > GameUI.gameUI_WINDOW_HEIGHT-450 ){
					startMenuStart.gameUIBatch.draw(startMenuLine, (GameUI.gameUI_WINDOW_WIDTH)/7, (GameUI.gameUI_WINDOW_HEIGHT-485), 180, 150);
					if(Gdx.input.justTouched()){
						startMenuStart.setScreen(new CustomizedMenu(startMenuStart));
					}
				}

				if(Gdx.input.getX() > GameUI.gameUI_WINDOW_WIDTH/10 && Gdx.input.getX() < (GameUI.gameUI_WINDOW_WIDTH/10) + startMenu_BACK_BUTTON_SIZE && GameUI.gameUI_WINDOW_HEIGHT - Gdx.input.getY() < startMenu_BACK_BUTTON_SIZE + GameUI.gameUI_WINDOW_HEIGHT-140 && GameUI.gameUI_WINDOW_HEIGHT - Gdx.input.getY() > GameUI.gameUI_WINDOW_HEIGHT-140 ){
					startMenuStart.gameUIBatch.draw(startMenuLine, (GameUI.gameUI_WINDOW_WIDTH)/10, (GameUI.gameUI_WINDOW_HEIGHT-220), startMenu_BACK_BUTTON_SIZE, 150);
					if(Gdx.input.justTouched()){
						this.dispose();
						startMenuStart.setScreen(new MainMenuScreen(startMenuStart));
					}
				}

				//start.batch.draw(okButton, GameUI.WINDOW_WIDTH-150, GameUI.WINDOW_HEIGHT/10, OK_BUTTON_WIDTH, OK_BUTTON_HEIGHT);
				startMenuStart.gameUIBatch.draw(startMenuBackButton, GameUI.gameUI_WINDOW_WIDTH/10, GameUI.gameUI_WINDOW_HEIGHT-150, startMenu_BACK_BUTTON_SIZE, startMenu_BACK_BUTTON_SIZE);
				startMenuStart.gameUIBatch.end();
				startMenuStage.draw();
				break;

			case WonScreen:
				//WonScreen
				wonScreenGame.gameUIBatch.begin();
				FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Georgia Italic.ttf"));

				FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
				parameter.size = 100;
				parameter.characters = "YOU WON!";
				wonScreenFont = generator.generateFont(parameter);
				wonScreenFont.setColor(Color.FOREST);
				generator.dispose();
				wonScreenFont.draw(wonScreenGame.gameUIBatch, "YOU WON!", GameUI.gameUI_WINDOW_WIDTH/3-50, GameUI.gameUI_WINDOW_HEIGHT-300);

				wonScreenGame.gameUIBatch.end();
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

	public void resize(int width, int height) {
		viewport.update(width, height);
		camera.update();
	}
}
