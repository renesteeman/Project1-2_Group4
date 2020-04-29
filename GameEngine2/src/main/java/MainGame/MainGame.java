package MainGame;

import Physics.*;
import Entities.*;
import Models.TexturedModel;
import MouseHandler.MouseHandler;
import OBJConverter.ModelData;
import OBJConverter.OBJFileLoader;
import RenderEngine.*;
import Models.RawModel;
import Terrain.Terrain;
import Textures.ModelTexture;
import Textures.TerrainTexture;
import Textures.TerrainTexturePack;
import Toolbox.MousePicker;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL;

import org.apache.commons.lang3.StringUtils;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class MainGame extends CrazyPutting {

    //10 units in-engine = 1 meter
    public final int SCALE = GameStaticData.SCALE;
    public final int TERRAIN_SIZE = course.TERRAIN_SIZE; // 800

    public Loader loader = new Loader();
    public List<Entity> entities = new ArrayList<>();
    public Light light;
    public Terrain terrain;
    public Camera camera;
    public MasterRenderer masterRenderer;
    public MousePicker mousePicker;

    public Trees trees;

    //public Ball ball;
    //public Goal goal;

    public MainGame() {
        this.course = new PuttingCourse("./res/courses/course1.txt");
        this.engine = DetermineSolver.getEngine(course);

        DisplayManager.createDisplay();
        GL.createCapabilities();
    }

    public void addModels() {
        ModelData ballModelData = OBJFileLoader.loadOBJ("ball");
        RawModel ballModel = loader.loadToVAO(ballModelData.getVertices(), ballModelData.getTextureCoords(), ballModelData.getNormals(), ballModelData.getIndices());
        TexturedModel texturedBall = new TexturedModel(ballModel, new ModelTexture(loader.loadTexture("models/BallTexture")));

        ModelData goalModelData = OBJFileLoader.loadOBJ("goal");
        RawModel goalModel = loader.loadToVAO(goalModelData.getVertices(), goalModelData.getTextureCoords(), goalModelData.getNormals(), goalModelData.getIndices());
        TexturedModel texturedGoal = new TexturedModel(goalModel, new ModelTexture(loader.loadTexture("models/GoalTexture")));

        ModelData treeModelData = OBJFileLoader.loadOBJ("tree");
        RawModel treeModel = loader.loadToVAO(treeModelData.getVertices(), treeModelData.getTextureCoords(), treeModelData.getNormals(), treeModelData.getIndices());
        TexturedModel texturedTree = new TexturedModel(treeModel, new ModelTexture(loader.loadTexture("models/TreeTexture")));

        //Special arrayList just for trees
        trees = new Trees();
        //ball = new Ball(texturedBall, new Vector3f(25*SCALE, 2*SCALE, 25*SCALE), 0, 0, 0, 1);
        //goal = new Goal(texturedGoal, new Vector3f(25*SCALE, 2*SCALE, 26*SCALE), 0, 0, 0, 1);

        course.ball = new Ball(texturedBall, new Vector3f(25*SCALE, 2*SCALE, 25*SCALE), 0, 0, 0, 1);
        course.goal = new Goal(texturedGoal, new Vector3f(25*SCALE, 2*SCALE, 26*SCALE), 0, 0, 0, 1);
        Tree tree1 = new Tree(texturedTree, new Vector3f(25*SCALE, 2*SCALE, 27*SCALE), 0, 0, 0, 1);
        trees.add(tree1);
        entities.add(course.ball);
        entities.add(course.goal);

        //entities.add(ball);
        //entities.add(goal);

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

        //TODO remove
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

    public void initLight() {
        light = new Light(new Vector3f(20000,20000,2000), new Vector3f(1, 1, 1));
    }

    public void initCamera() {
        //Camera
        camera = new Camera(course.ball);
    }

    public void initRender() {
        masterRenderer = new MasterRenderer(loader);
    }

    public void initControls() {
        //MousePicker
        mousePicker = new MousePicker(camera, masterRenderer.getProjectionMatrix(), terrain);
    }

    public void runApp() {
        //Game loop
        while(!DisplayManager.closed()){
            //Handle mouse events
            MouseHandler.handleMouseEvents();
            camera.move(terrain);

            //Update mousePicker
            mousePicker.update();
            Vector3f terrainPoint = mousePicker.getCurrentTerrainPoint();

            //Render 3D elements
            masterRenderer.renderScene(entities, terrain, light, camera, new Vector4f(0, 0, 0, 0));

            DisplayManager.updateDisplay();
            DisplayManager.swapBuffers();
        }
    }

    @Override 
    public void requestGraphicsUpdate() {
        //Handle mouse events
        MouseHandler.handleMouseEvents();
        camera.move(terrain);

        //Update mousePicker
        mousePicker.update();
        Vector3f terrainPoint = mousePicker.getCurrentTerrainPoint();

        //Render 3D elements
        masterRenderer.renderScene(entities, terrain, light, camera, new Vector4f(0, 0, 0, 0));

        DisplayManager.updateDisplay();
        DisplayManager.swapBuffers();
    }

    public void cleanUp() {
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
        obj.addModels();
        obj.resetPositions();
        obj.addAxes();
        obj.addTerrain();
        obj.initLight();
        obj.initRender();
        obj.initCamera();
        obj.initControls();
        obj.setInteractiveMod(false);
        obj.requestGraphicsUpdate();
        //obj.runApp();
        
        /*for (int i = 0; i < 1e5; i++) {
            for (int j = 0; j < 1e5; j++) {
                System.out.println(i*1e5 + j);
            }
        }*/

        try
        {
            Thread.sleep(5000);
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }

        try {
            obj.game();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }

        //obj.requestGraphicsUpdate();
        obj.cleanUp();
    }
}
