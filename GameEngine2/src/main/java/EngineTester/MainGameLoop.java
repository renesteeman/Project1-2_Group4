package EngineTester;

import Entities.Camera;
import Entities.Entity;
import Entities.Light;
import Models.TexturedModel;
import OBJConverter.ModelData;
import OBJConverter.OBJFileLoader;
import RenderEngine.*;
import Models.RawModel;
import Terrain.Terrain;
import Textures.ModelTexture;
import Textures.TerrainTexture;
import Textures.TerrainTexturePack;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL;

import java.util.ArrayList;
import java.util.List;

import static RenderEngine.DisplayManager.getDeltaTime;

public class MainGameLoop {

    public static void main(String[] args){
        DisplayManager.createDisplay();
        GL.createCapabilities();
        Loader loader = new Loader();

        Light light = new Light(new Vector3f(20000,20000,2000), new Vector3f(1, 1, 1));

        //Models and entities
        ModelData modelData = OBJFileLoader.loadOBJ("dragon");
        RawModel dragonModel = loader.loadToVAO(modelData.getVertices(), modelData.getTextureCoords(), modelData.getNormals(), modelData.getIndices());
        TexturedModel texturedDragon = new TexturedModel(dragonModel, new ModelTexture(loader.loadTexture("brick")));

        List<Entity> entities = new ArrayList<Entity>();
        Entity entity1 = new Entity(texturedDragon, new Vector3f(0, 0, -50), 0, 0, 0, 1);
        entities.add(entity1);

        //Terrain
        TerrainTexture grassTexture = new TerrainTexture(loader.loadTexture("nice_grass"));
        TerrainTexture sandTexture = new TerrainTexture(loader.loadTexture("nice_sand"));
        //TODO remove
        TerrainTexture brickTexture = new TerrainTexture(loader.loadTexture("brick"));
        TerrainTexture blendMap = new TerrainTexture(loader.loadTexture(("blendMap")));

        TerrainTexturePack terrainTexturePack = new TerrainTexturePack(grassTexture, sandTexture, brickTexture);

        Terrain terrain = new Terrain(0, -1, loader, terrainTexturePack, blendMap);
        Terrain terrain2 = new Terrain(-1, -1, loader, terrainTexturePack, blendMap);

        Camera camera = new Camera(new Vector3f(0, 5, 0));

        MasterRenderer renderer = new MasterRenderer();

        //Game loop
        while(!DisplayManager.closed()){
//            entity.increasePosition(0, 0, getDeltaTime() * -0.2f);
            entity1.increaseRotation(getDeltaTime() * 0, getDeltaTime() * 50, 0);
            camera.move();

            renderer.processTerrain(terrain);
            renderer.processTerrain(terrain2);

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
