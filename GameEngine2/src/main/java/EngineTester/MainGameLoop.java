package EngineTester;

import Entities.Camera;
import Entities.Entity;
import Models.TexturedModel;
import RenderEngine.DisplayManager;
import RenderEngine.Loader;
import Models.RawModel;
import RenderEngine.OBJLoader;
import RenderEngine.Renderer;
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
        StaticShader shader = new StaticShader();
        Renderer renderer = new Renderer(shader);

        RawModel model = OBJLoader.loadObjModel("tree", loader);
        ModelTexture texture = new ModelTexture(loader.loadTexture("brick"));
        TexturedModel texturedModel = new TexturedModel(model, texture);

        Entity entity = new Entity(texturedModel, new Vector3f(0, 0, -50), 0, 0, 0, 1);

        Camera camera = new Camera();

        //Game loop
        while(!DisplayManager.closed()){
//            entity.increasePosition(0, 0, getDeltaTime() * -0.2f);
            entity.increaseRotation(getDeltaTime() * 0, getDeltaTime() * 50, 0);
            camera.move();

            renderer.prepare();
            shader.start();
            shader.loadViewMatrix(camera);

            renderer.render(entity, shader);

            shader.stop();

            DisplayManager.updateDisplay();
            DisplayManager.swapBuffers();
        }

        shader.cleanUp();
        loader.cleanUp();

    }
}
