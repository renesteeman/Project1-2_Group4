package EngineTester;

import Entities.*;
import GUI.GUIRenderer;
import GUI.GUITexture;
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
import FontMeshCreator.FontType;
import FontMeshCreator.GUIText;
import FontRendering.TextMaster;
import Toolbox.MousePicker;
import Water.WaterTile;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import RenderEngine.WaterRenderer;
import Shaders.WaterShader;
import Water.WaterFrameBuffers;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;

public class MainGameLoop {

    //10 units in-engine = 1 meter
    static public final int SCALE = 10;
    static final int TERRAIN_SIZE = 80*SCALE;

    //TMP move into a separate directory
    static final boolean editMode = true;
    //0 = place items, 1 = remove items
    static int interactionType = -1;
    static Vector3f terrainPoint;
    static final float REMOVE_DISTANCE = 5;

    static Loader loader = new Loader();
    static Trees trees = new Trees();
    static TexturedModel texturedTree;

    public static void main(String[] args){
        DisplayManager.createDisplay();
        GL.createCapabilities();
        TextMaster.init(loader);

        FontType font = new FontType(loader.loadTexture("/font/tahoma"), new File("res/font/tahoma.fnt"));
        GUIText text = new GUIText("This is a test text!", 1, font, new Vector2f(0, 0), 1f, true);

        Light light = new Light(new Vector3f(20000,20000,2000), new Vector3f(1, 1, 1));

        //Terrain
        TerrainTexture grassTexture = new TerrainTexture(loader.loadTexture("textures/nice_grass"));
        TerrainTexture sandTexture = new TerrainTexture(loader.loadTexture("textures/nice_sand"));

        TerrainTexturePack terrainTexturePack = new TerrainTexturePack(grassTexture, sandTexture);

        Terrain terrain = new Terrain(0, 0, loader, terrainTexturePack, TERRAIN_SIZE);

        //Models and entities
        ModelData dragonModelData = OBJFileLoader.loadOBJ("dragon");
        RawModel dragonModel = loader.loadToVAO(dragonModelData.getVertices(), dragonModelData.getTextureCoords(), dragonModelData.getNormals(), dragonModelData.getIndices());
        TexturedModel texturedDragon = new TexturedModel(dragonModel, new ModelTexture(loader.loadTexture("textures/brick")));

        ModelData ballModelData = OBJFileLoader.loadOBJ("Ball");
        RawModel ballModel = loader.loadToVAO(ballModelData.getVertices(), ballModelData.getTextureCoords(), ballModelData.getNormals(), ballModelData.getIndices());
        TexturedModel texturedBall = new TexturedModel(ballModel, new ModelTexture(loader.loadTexture("models/BallTexture")));

        ModelData goalModelData = OBJFileLoader.loadOBJ("Goal");
        RawModel goalModel = loader.loadToVAO(goalModelData.getVertices(), goalModelData.getTextureCoords(), goalModelData.getNormals(), goalModelData.getIndices());
        TexturedModel texturedGoal = new TexturedModel(goalModel, new ModelTexture(loader.loadTexture("models/GoalTexture")));

        ModelData treeModelData = OBJFileLoader.loadOBJ("Tree");
        RawModel treeModel = loader.loadToVAO(treeModelData.getVertices(), treeModelData.getTextureCoords(), treeModelData.getNormals(), treeModelData.getIndices());
        texturedTree = new TexturedModel(treeModel, new ModelTexture(loader.loadTexture("models/TreeTexture")));

        List<Entity> entities = new ArrayList<Entity>();
        //Special arrayList just for trees
        Entity dragonEntity = new Entity(texturedDragon, new Vector3f(0, 0, -5*SCALE), 0, 0, 0, 1);
        Ball ball = new Ball(texturedBall, new Vector3f(25*SCALE, 2*SCALE, 25*SCALE), 0, 0, 0, 1);
        Goal goal = new Goal(texturedGoal, new Vector3f(25*SCALE, 2*SCALE, 26*SCALE), 0, 0, 0, 1);
        Tree tree1 = new Tree(texturedTree, new Vector3f(25*SCALE, 2*SCALE, 27*SCALE), 0, 0, 0, 1);
        trees.add(tree1);
        entities.add(dragonEntity);
        entities.add(ball);
        entities.add(goal);
        entities.addAll(trees);

        //TODO remove
        //Show X-axis
        TexturedModel XtexturedDragon = new TexturedModel(dragonModel, new ModelTexture(loader.loadTexture("textures/nice_sand")));
        for(int i=0; i<10; i++){
            float x = i*5*SCALE;
            float z = 0;
            float y = 5*SCALE;

            Entity testDragonEntity = new Entity(XtexturedDragon, new Vector3f(x, y, z), 0, 0, 0, 1);
            entities.add(testDragonEntity);
        }

        float tmpY = terrain.getHeight(200, 200);
//        System.out.println("HEIGHT " + tmpY + " SHOULD EQUAL -4.73");
        Entity specificTestEntity = new Entity(texturedGoal, new Vector3f(200, tmpY, 20), 0, 0, 0, 1);
        entities.add(specificTestEntity);
        Entity specificTestEntity2 = new Entity(texturedGoal, new Vector3f(200, 0, 20), 0, 0, 0, 1);
        entities.add(specificTestEntity2);

        TexturedModel XtexturedDragonMEME = new TexturedModel(dragonModel, new ModelTexture(loader.loadTexture("textures/UI_meme")));
        for(int i=0; i<10; i++){
            float x = i*5*SCALE;
            float z = 200;
            float y = terrain.getHeight(x, z);

            Entity testDragonEntity = new Entity(XtexturedDragonMEME, new Vector3f(x, y, z), 0, 0, 0, 1);
            entities.add(testDragonEntity);
        }

        //Show Z-axis
        TexturedModel ZtexturedDragon = new TexturedModel(dragonModel, new ModelTexture(loader.loadTexture("textures/nice_grass")));
        for(int i=0; i<10; i++){
            Entity testDragonEntity = new Entity(ZtexturedDragon, new Vector3f(0, 5*SCALE, 5*SCALE*i), 0, 0, 0, 1);
            entities.add(testDragonEntity);
        }

        //GUI
        List<GUITexture> GUIs = new ArrayList<GUITexture>();
        GUITexture GUI = new GUITexture(loader.loadTexture("textures/UI_meme"), new Vector2f(0.5f, 0.5f), new Vector2f(0.25f, 0.25f));
        GUIs.add(GUI);

        GUIRenderer guiRenderer = new GUIRenderer(loader);

        //Camera
        Camera camera = new Camera(ball);

        //3D renderer
        MasterRenderer masterRenderer = new MasterRenderer(loader);

        //MousePicker
        MousePicker mousePicker = new MousePicker(camera, masterRenderer.getProjectionMatrix(), terrain);

        //Water
        WaterFrameBuffers waterFrameBuffers = new WaterFrameBuffers();
        WaterShader waterShader = new WaterShader();
        WaterRenderer waterRenderer = new WaterRenderer(loader, waterShader, masterRenderer.getProjectionMatrix(), waterFrameBuffers);
        List<WaterTile> waters = new ArrayList<WaterTile>();
        WaterTile mainWaterTile = new WaterTile((float) (TERRAIN_SIZE/2.0), (float) (TERRAIN_SIZE/2.0), 0, TERRAIN_SIZE);
        waters.add(mainWaterTile);

        if(editMode){
            //Handle events related to editing
            GLFW.glfwSetKeyCallback(DisplayManager.getWindow(), (handle, key, scancode, action, mods) -> {
                if (key == GLFW_KEY_1) {
                    System.out.println("1 pressed");
                    interactionType = 1;
                } else if(key == GLFW_KEY_2){
                    System.out.println("2 pressed");
                    interactionType = 2;
                }
            });

            GLFW.glfwSetMouseButtonCallback(DisplayManager.getWindow(), (window, button, action, mods) -> {
                if(button==GLFW_MOUSE_BUTTON_LEFT){
                    System.out.println("MOUSE_LEFT");
                    handleEditAction();
                }
            });
        }

        //Game loop
        while(!DisplayManager.closed()){
            // This line is critical for LWJGL's interoperation with GLFW's
            // OpenGL context, or any context that is managed externally.
            // LWJGL detects the context that is current in the current thread,
            // creates the GLCapabilities instance and makes the OpenGL
            // bindings available for use.
            GL.createCapabilities();

            //Handle mouse events
            MouseHandler.handleMouseEvents();
            camera.move(terrain);

            if(editMode){
                //Update mousePicker
                mousePicker.update();
                terrainPoint = mousePicker.getCurrentTerrainPoint();

                //Add new trees to scene
                entities.removeAll(trees);
                entities.addAll(trees);
            }

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

            //2D Rendering / UI
            guiRenderer.render(GUIs);

            TextMaster.render();

            DisplayManager.updateDisplay();
            DisplayManager.swapBuffers();
        }

        waterFrameBuffers.cleanUp();
        waterShader.cleanUp();
        TextMaster.cleanUp();
        guiRenderer.cleanUp();
        masterRenderer.cleanUp();
        loader.cleanUp();
    }

    public static void handleEditAction(){
        if(terrainPoint!=null){
            if(interactionType==1){
                //Place mode
                //terrainPoint is the point on the terrain that the user clicked on
                System.out.println("ADD TREE");
                Tree treeToAdd = new Tree(texturedTree, new Vector3f(terrainPoint), 0, 0, 0, 1);
                trees.add(treeToAdd);
                System.out.println(Arrays.toString(trees.toArray()));
            } else if(interactionType==2){
                //Remove mode within remove distance
                trees.removeIf(tree -> tree.getPosition().distance(terrainPoint) < REMOVE_DISTANCE);
            }
        }
    }
}
