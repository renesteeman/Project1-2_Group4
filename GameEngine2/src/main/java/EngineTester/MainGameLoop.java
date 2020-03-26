package EngineTester;

import Entities.Camera;
import Entities.Entity;
import Entities.Light;
import Models.TexturedModel;
import RenderEngine.*;
import Models.RawModel;
import Shaders.StaticShader;
import Textures.ModelTexture;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL;

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

        Entity entity = new Entity(texturedModel, new Vector3f(0, 0, -50), 0, 0, 0, 1);
        Light light = new Light(new Vector3f(0, 0, -20), new Vector3f(1, 1, 1));

        Camera camera = new Camera();

        //Game loop
        MasterRenderer renderer = new MasterRenderer();
        while(!DisplayManager.closed()){
//            entity.increasePosition(0, 0, getDeltaTime() * -0.2f);
            entity.increaseRotation(getDeltaTime() * 0, getDeltaTime() * 50, 0);
            camera.move();

            renderer.processEntity(entity);

            renderer.render(light, camera);

            DisplayManager.updateDisplay();
            DisplayManager.swapBuffers();
        }

        renderer.cleanUp();
        loader.cleanUp();
    }
}
