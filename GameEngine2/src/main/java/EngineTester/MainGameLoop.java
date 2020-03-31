package EngineTester;

import Entities.Ball;
import Entities.Camera;
import Entities.Entity;
import Entities.Light;
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
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import Water.WaterFrameBuffers;
import RenderEngine.WaterRenderer;
import Shaders.WaterShader;
import Water.WaterTile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainGameLoop {

    public static void main(String[] args){
        DisplayManager.createDisplay();
        GL.createCapabilities();
        Loader loader = new Loader();
        TextMaster.init(loader);

        FontType font = new FontType(loader.loadTexture("/font/tahoma"), new File("res/font/tahoma.fnt"));
        GUIText text = new GUIText("This is a test text!", 1, font, new Vector2f(0, 0), 1f, true);

        Light light = new Light(new Vector3f(20000,20000,2000), new Vector3f(1, 1, 1));

        //Models and entities
        ModelData dragonModelData = OBJFileLoader.loadOBJ("dragon");
        RawModel dragonModel = loader.loadToVAO(dragonModelData.getVertices(), dragonModelData.getTextureCoords(), dragonModelData.getNormals(), dragonModelData.getIndices());
        TexturedModel texturedDragon = new TexturedModel(dragonModel, new ModelTexture(loader.loadTexture("textures/brick")));

        ModelData ballModelData = OBJFileLoader.loadOBJ("ball");
        RawModel ballModel = loader.loadToVAO(ballModelData.getVertices(), ballModelData.getTextureCoords(), ballModelData.getNormals(), ballModelData.getIndices());
        TexturedModel texturedBall = new TexturedModel(ballModel, new ModelTexture(loader.loadTexture("textures/brick")));

        List<Entity> entities = new ArrayList<Entity>();
        Entity dragonEntity = new Entity(texturedDragon, new Vector3f(0, 0, -50), 0, 0, 0, 1);
        Ball ball = new Ball(texturedBall, new Vector3f(250, 20, -250), 0, 0, 0, 1);
        entities.add(dragonEntity);
        entities.add(ball);

        //Terrain
        TerrainTexture grassTexture = new TerrainTexture(loader.loadTexture("textures/nice_grass"));
        TerrainTexture sandTexture = new TerrainTexture(loader.loadTexture("textures/nice_sand"));

        TerrainTexturePack terrainTexturePack = new TerrainTexturePack(grassTexture, sandTexture);

        Terrain terrain = new Terrain(0, -1, loader, terrainTexturePack, "textures/heightmap");

        //GUI
        List<GUITexture> GUIs = new ArrayList<GUITexture>();
        GUITexture GUI = new GUITexture(loader.loadTexture("textures/UI_meme"), new Vector2f(0.5f, 0.5f), new Vector2f(0.25f, 0.25f));
        GUIs.add(GUI);

        GUIRenderer guiRenderer = new GUIRenderer(loader);

        //Camera
        Camera camera = new Camera(ball, new Vector3f(0, 5, 0));

        //3D renderer
        MasterRenderer masterRenderer = new MasterRenderer(loader);

        //MousePicker
        MousePicker mousePicker = new MousePicker(camera, masterRenderer.getProjectionMatrix(), terrain);

        //Water
        WaterFrameBuffers waterFrameBuffers = new WaterFrameBuffers();
        WaterShader waterShader = new WaterShader();
        WaterRenderer waterRenderer = new WaterRenderer(loader, waterShader, masterRenderer.getProjectionMatrix(), waterFrameBuffers);
        List<WaterTile> waters = new ArrayList<WaterTile>();
        WaterTile mainWaterTile = new WaterTile(250, -250, 0, 250);
        waters.add(mainWaterTile);

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

            //Update mousePicker
            mousePicker.update();
            Vector3f terrainPoint = mousePicker.getCurrentTerrainPoint();

            //Handle object movement
//            entity.increasePosition(0, 0, getDeltaTime() * -0.2f);
//            dragonEntity.increaseRotation(getDeltaTime() * 0, getDeltaTime() * 50, 0);




            //Move object(s) based on pointer
            if(terrainPoint != null){
//                System.out.println(terrainPoint);
                dragonEntity.setPosition(terrainPoint);
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
}
