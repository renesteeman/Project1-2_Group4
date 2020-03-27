package EngineTester;

import Entities.Ball;
import Entities.Camera;
import Entities.Entity;
import Entities.Light;
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
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL;

import java.nio.DoubleBuffer;
import java.util.ArrayList;
import java.util.List;

import static RenderEngine.DisplayManager.getDeltaTime;
import static org.lwjgl.glfw.GLFW.*;

public class MainGameLoop {

    public static void main(String[] args){
        DisplayManager.createDisplay();
        GL.createCapabilities();
        Loader loader = new Loader();

        Light light = new Light(new Vector3f(20000,20000,2000), new Vector3f(1, 1, 1));

        //Models and entities
        ModelData dragonModelData = OBJFileLoader.loadOBJ("dragon");
        RawModel dragonModel = loader.loadToVAO(dragonModelData.getVertices(), dragonModelData.getTextureCoords(), dragonModelData.getNormals(), dragonModelData.getIndices());
        TexturedModel texturedDragon = new TexturedModel(dragonModel, new ModelTexture(loader.loadTexture("brick")));

        ModelData ballModelData = OBJFileLoader.loadOBJ("ball");
        RawModel ballModel = loader.loadToVAO(ballModelData.getVertices(), ballModelData.getTextureCoords(), ballModelData.getNormals(), ballModelData.getIndices());
        TexturedModel texturedBall = new TexturedModel(ballModel, new ModelTexture(loader.loadTexture("brick")));

        List<Entity> entities = new ArrayList<Entity>();
        Entity entity1 = new Entity(texturedDragon, new Vector3f(0, 0, -50), 0, 0, 0, 1);
        Ball ball = new Ball(texturedBall, new Vector3f(0, 5, -10), 0, 0, 0, 1);
//        entities.add(entity1);
        entities.add(ball);

        //Terrain
        TerrainTexture grassTexture = new TerrainTexture(loader.loadTexture("nice_grass"));
        TerrainTexture sandTexture = new TerrainTexture(loader.loadTexture("nice_sand"));
        //TODO remove
        TerrainTexture brickTexture = new TerrainTexture(loader.loadTexture("brick"));
        TerrainTexture blendMap = new TerrainTexture(loader.loadTexture(("blendMap")));

        TerrainTexturePack terrainTexturePack = new TerrainTexturePack(grassTexture, sandTexture, brickTexture);

        Terrain terrain = new Terrain(0, -1, loader, terrainTexturePack, blendMap, "heightmap");
        Terrain terrain2 = new Terrain(-1, -1, loader, terrainTexturePack, blendMap, "heightmap");

        //Camera
        Camera camera = new Camera(ball, new Vector3f(0, 5, 0));

        MasterRenderer renderer = new MasterRenderer();

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

            //Handle object movement
//            entity.increasePosition(0, 0, getDeltaTime() * -0.2f);
//            entity1.increaseRotation(getDeltaTime() * 0, getDeltaTime() * 50, 0);
            camera.move();

            //Handle terrain
            renderer.processTerrain(terrain);
            renderer.processTerrain(terrain2);

            //Render objects
            for(Entity entity : entities){
                renderer.processEntity(entity);
            }

            renderer.render(light, camera);

            DisplayManager.updateDisplay();
            DisplayManager.swapBuffers();
        }

        renderer.cleanUp();
        loader.cleanUp();
    }
}
