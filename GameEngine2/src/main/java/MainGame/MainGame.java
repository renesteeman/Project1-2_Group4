package MainGame;

import FontRendering.TextMaster;
import GUI.GUIRenderer;
import GUI.GUITexture;
import GUI.Menu.MainMenu;
import GUIElements.Buttons.AbstractButton;
import GUIElements.Buttons.InterfaceButton;
import GUIElements.Slider;
import GUIElements.UIElement;
import Physics.*;
import Entities.*;
import Models.TexturedModel;
import MouseHandler.MouseHandler;
import OBJConverter.ModelData;
import OBJConverter.OBJFileLoader;
import RenderEngine.*;
import Models.RawModel;
import Shaders.WaterShader;
import Terrain.Terrain;
import Textures.ModelTexture;
import Textures.TerrainTexture;
import Textures.TerrainTexturePack;
import Toolbox.MousePicker;
import Water.WaterFrameBuffers;
import Water.WaterTile;
import com.sun.tools.javac.Main;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.liquidengine.legui.input.Mouse;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL;

import org.apache.commons.lang3.StringUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

import java.lang.management.ManagementFactory;
import java.nio.DoubleBuffer;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.glfwGetCursorPos;

public class MainGame extends CrazyPutting {

    //10 units in-engine = 1 meter
    private final int SCALE = GameStaticData.SCALE;
    private final int TERRAIN_SIZE = course.TERRAIN_SIZE; // 800

    private Loader loader = new Loader();
    private List<Entity> entities = new ArrayList<>();
    private List<UIElement> GUIs = new ArrayList<>();
    private List<WaterTile> waters = new ArrayList<WaterTile>();

    private Light light;
    private Terrain terrain;
    private Camera camera;
    private MasterRenderer masterRenderer;
    private WaterFrameBuffers waterFrameBuffers;
    private GUIRenderer guiRenderer;
    private MousePicker mousePicker;
    private WaterTile mainWaterTile;

    private WaterShader waterShader;
    private WaterRenderer waterRenderer;

    private Trees trees;

    public MainGame() {
        this.course = new PuttingCourse("./res/courses/course1.txt");
        this.engine = DetermineSolver.getEngine(course);

        DisplayManager.createDisplay();
        GL.createCapabilities();
    }

    public void setUpModels() {
        ModelData ballModelData = OBJFileLoader.loadOBJ("ball");
        RawModel ballModel = loader.loadToVAO(ballModelData.getVertices(), ballModelData.getTextureCoords(), ballModelData.getNormals(), ballModelData.getIndices());
        TexturedModel texturedBall = new TexturedModel(ballModel, new ModelTexture(loader.loadTexture("models/BallTexture")));

        ModelData goalModelData = OBJFileLoader.loadOBJ("goal");
        RawModel goalModel = loader.loadToVAO(goalModelData.getVertices(), goalModelData.getTextureCoords(), goalModelData.getNormals(), goalModelData.getIndices());
        TexturedModel texturedGoal = new TexturedModel(goalModel, new ModelTexture(loader.loadTexture("models/GoalTexture")));

        //Special arrayList just for trees (still declared here since it shouldn't be null)
        trees = new Trees();

        course.ball = new Ball(texturedBall, new Vector3f(25*SCALE, 2*SCALE, 25*SCALE), 0, 0, 0, 1);
        course.goal = new Goal(texturedGoal, new Vector3f(25*SCALE, 2*SCALE, 26*SCALE), 0, 0, 0, 1);

        entities.add(course.ball);
        entities.add(course.goal);

        entities.addAll(trees);
    }

    public void resetPositions() {
        course.setDefaultPositions();
    }

    public void addAxes() {
        //Models and entities
        ModelData dragonModelData = OBJFileLoader.loadOBJ("dragon");
        RawModel dragonModel = loader.loadToVAO(dragonModelData.getVertices(), dragonModelData.getTextureCoords(), dragonModelData.getNormals(), dragonModelData.getIndices());
        TexturedModel texturedDragon = new TexturedModel(dragonModel, new ModelTexture(loader.loadTexture("textures/brick")));

        //Show X-axis
        for(int i=0; i<10; i++){
            TexturedModel XTexturedDragon = new TexturedModel(dragonModel, new ModelTexture(loader.loadTexture("textures/nice_sand")));
            Entity testDragonEntity = new Entity(XTexturedDragon, new Vector3f(i*5*SCALE, 5*SCALE, 0), 0, 0, 0, 1);
            entities.add(testDragonEntity);
        }

        //Show Z-axis
        for(int i=0; i<10; i++){
            TexturedModel ZTexturedDragon = new TexturedModel(dragonModel, new ModelTexture(loader.loadTexture("textures/nice_grass")));
            Entity testDragonEntity = new Entity(ZTexturedDragon, new Vector3f(0, 5*SCALE, 5*SCALE*i), 0, 0, 0, 1);
            entities.add(testDragonEntity);
        }
    }

    public void addTerrain() {
        //Terrain
        TerrainTexture grassTexture = new TerrainTexture(loader.loadTexture("textures/nice_grass"));
        TerrainTexture sandTexture = new TerrainTexture(loader.loadTexture("textures/nice_sand"));

        TerrainTexturePack terrainTexturePack = new TerrainTexturePack(grassTexture, sandTexture);

        //terrain = new Terrain(0, 0, loader, terrainTexturePack, TERRAIN_SIZE);
        terrain = new Terrain(0, 0, loader, course.height, terrainTexturePack, TERRAIN_SIZE);
    }

    public void addWater(){
        waterFrameBuffers = new WaterFrameBuffers();
        waterShader = new WaterShader();
        mainWaterTile = new WaterTile((float) (TERRAIN_SIZE/2.0), (float) (TERRAIN_SIZE/2.0), 0, TERRAIN_SIZE);
        waters.add(mainWaterTile);
    }

    public void addTrees(){
        ModelData treeModelData = OBJFileLoader.loadOBJ("tree");
        RawModel treeModel = loader.loadToVAO(treeModelData.getVertices(), treeModelData.getTextureCoords(), treeModelData.getNormals(), treeModelData.getIndices());
        TexturedModel texturedTree = new TexturedModel(treeModel, new ModelTexture(loader.loadTexture("models/TreeTexture")));

        Tree tree1 = new Tree(texturedTree, new Vector3f(25*SCALE, 2*SCALE, 27*SCALE), 0, 0, 0, 1);
        trees.add(tree1);
    }

    public void initLight() {
        light = new Light(new Vector3f(20000,20000,2000), new Vector3f(1, 1, 1));
    }

    public void initCamera() {
        //Camera
        camera = new Camera(course.ball);
    }

    public void initRenders() {
        masterRenderer = new MasterRenderer(loader);
        guiRenderer = new GUIRenderer(loader);
        waterRenderer = new WaterRenderer(loader, waterShader, masterRenderer.getProjectionMatrix(), waterFrameBuffers);
    }

    public void initControls() {
        //MousePicker
        mousePicker = new MousePicker(camera, masterRenderer.getProjectionMatrix(), terrain);
    }

    public void addUI(){
        AbstractButton testButton = new AbstractButton(loader, "textures/button", new Vector2f(0,0.5f), new Vector2f(0.2f, 0.2f)) {

            @Override
            public void onClick(InterfaceButton button) {
                MouseHandler.disable();
                System.out.println("Hello there");
            }

            @Override
            public void onStartHover(InterfaceButton button) {
                MouseHandler.disable();
                button.playHoverAnimation(0.092f);
                System.out.println("I am the Senate!");
            }

            @Override
            public void onStopHover(InterfaceButton button) {
                button.resetScale();
                System.out.println("General Kenobi");
                MouseHandler.enable();
            }

            @Override
            public void whileHovering(InterfaceButton button) {
                System.out.println("A suprise but I welcome one");
            }
        };

        Slider testSlider = new Slider(loader, "textures/sliderBar","textures/sliderKnob", new Vector2f(0,0), new Vector2f(0.2f, 0.2f)) {
            @Override
            public void onClick(InterfaceButton button) {
                MouseHandler.disable();
                getSliderTexture().setPosition(DisplayManager.getNormalizedMouseCoordinates());

                //System.out.println("Hello there");
            }

            @Override
            public void onStartHover(InterfaceButton button) {
                MouseHandler.disable();
                //button.playHoverAnimation(0.092f);
                //System.out.println("I am the Senate!");
            }

            @Override
            public void onStopHover(InterfaceButton button) {
                MouseHandler.enable();
                //button.resetScale();
                //System.out.println("General Kenobi");
            }

            @Override
            public void whileHovering(InterfaceButton button) {
                //System.out.println("A suprise but I welcome one");
            }
        };

        //GUIs.add(testButton);
//        GUIs.add(testSlider);
        GUIs.add(testButton);
        GUIs.add(testSlider);
//        testSlider.hide();
//        testSlider.show();
    }

    @Override
    //Update screen
    public void requestGraphicsUpdate() {
        //Handle mouse events
        MouseHandler.handleMouseEvents();
        camera.move(terrain);

        //Update mousePicker
        mousePicker.update();
        Vector3f terrainPoint = mousePicker.getCurrentTerrainPoint();

        //Render water part 1
        GL11.glEnable(GL30.GL_CLIP_DISTANCE0);

        //water reflection
        waterFrameBuffers.bindReflectionFrameBuffer();
        float distance = 2*(camera.getPosition().y - mainWaterTile.getHeight());
        camera.setPreventTerrainClipping(false);
        camera.getPosition().y -= distance;
        camera.invertPitch();

        masterRenderer.renderScene(entities, terrain, light, camera, new Vector4f(0, 1, 0, -mainWaterTile.getHeight()+0.2f));

        camera.getPosition().y += distance;
        camera.invertPitch();
        camera.setPreventTerrainClipping(true);
        waterFrameBuffers.unbindCurrentFrameBuffer();

        //water refraction
        waterFrameBuffers.bindRefractionFrameBuffer();
        masterRenderer.renderScene(entities, terrain, light, camera, new Vector4f(0, -1, 0, mainWaterTile.getHeight()+0.2f));
        waterFrameBuffers.unbindCurrentFrameBuffer();

        //Render 3D elements
        masterRenderer.renderScene(entities, terrain, light, camera, new Vector4f(0, 0, 0, 0));

        //Render water part 2
        waterRenderer.render(waters, camera, light);

        //Render 2D elements
        guiRenderer.render(GUIs);
        for(UIElement element : GUIs){
            element.update();
        }

        DisplayManager.updateDisplay();
        DisplayManager.swapBuffers();
    }

    public void cleanUp() {
        waterFrameBuffers.cleanUp();
        waterShader.cleanUp();
        TextMaster.cleanUp();
        guiRenderer.cleanUp();
        masterRenderer.cleanUp();
        loader.cleanUp();
    }

    @Override 
    protected boolean collectShotData() {
        System.out.println("enter your shot data(the velocity vector):");
        Scanner shotScanner = new Scanner(System.in);
        String[] arguments = shotScanner.nextLine().split(" ");
        System.out.println("your shot is read");
        if (arguments.length == 1 && arguments[0].equals("stop")) {
            System.out.println("stop condition is recognized");
            return false;
        }
        if (arguments.length == 2 && StringUtils.isNumeric(arguments[0]) && StringUtils.isNumeric(arguments[1])) {
            shotInput = new Vector2d(arguments[0], arguments[1]);
            System.out.println("input is recognized as valid");
            return true;
        }
        System.out.println("invalid shot input, try again");
        return collectShotData();
    }

    public static void main(String[] args) {
        MainGame obj = new MainGame();
        obj.setUpModels();
        obj.resetPositions();
        obj.addAxes();
        obj.addTerrain();
        obj.initLight();
        obj.addWater();
        obj.initRenders();
        obj.initCamera();
        obj.initControls();
        obj.setInteractiveMod(false);
        obj.addUI();
        obj.requestGraphicsUpdate();

        //MainMenu.createMenu();
        //obj.runApp();
        
        /*for (int i = 0; i < 1e5; i++) {
            for (int j = 0; j < 1e5; j++) {
                System.out.println(i*1e5 + j);
            }
        }*/

        /*try
        {
            Thread.sleep(5000);
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }*/

        //TODO add comments
        try {
            obj.game();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }

        System.out.println(obj.passedFlag());

        //obj.requestGraphicsUpdate();
        obj.cleanUp();
    }
}
