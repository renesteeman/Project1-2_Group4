package EngineTester;

import Entities.Camera;
import Entities.Entity;
import Entities.Light;
import Models.TexturedModel;
import RenderEngine.*;
import Models.RawModel;
import Shaders.StaticShader;
import Terrain.Terrain;
import Textures.ModelTexture;
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

        RawModel model = OBJLoader.loadObjModel("dragon", loader);
//        ModelTexture texture = new ModelTexture(loader.loadTexture("brick"));
        TexturedModel texturedModel = new TexturedModel(model, new ModelTexture(loader.loadTexture("brick")));
        ModelTexture texture = texturedModel.getTexture();
        texture.setShineDamper(10);
        texture.setReflectivity(1);

        List<Entity> entities = new ArrayList<Entity>();
        Entity entity1 = new Entity(texturedModel, new Vector3f(0, 0, -50), 0, 0, 0, 1);
        entities.add(entity1);

        Light light = new Light(new Vector3f(20000,20000,2000), new Vector3f(1, 1, 1));

        Terrain terrain = new Terrain(0, -1, loader, new ModelTexture(loader.loadTexture("grass")));
        Terrain terrain2 = new Terrain(-1, -1, loader, new ModelTexture(loader.loadTexture("grass")));

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
