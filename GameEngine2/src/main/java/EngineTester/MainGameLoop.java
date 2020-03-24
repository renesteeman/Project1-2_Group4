package EngineTester;

import RenderEngine.DisplayManager;
import RenderEngine.Loader;
import RenderEngine.RawModel;
import RenderEngine.Renderer;
import Shaders.StaticShader;
import org.lwjgl.opengl.GL;

public class MainGameLoop {

    public static void main(String[] args){
        DisplayManager.createDisplay();
        GL.createCapabilities();

        Loader loader = new Loader();
        Renderer renderer = new Renderer();
        StaticShader shader = new StaticShader();

        float[] vertices = {
                -0.5f, 0.5f, 0f,
                -0.5f, -0.5f, 0f,
                0.5f, -0.5f, 0f,
                0.5f, 0.5f, 0f
        };

        int[] indices = {
                0, 1, 3,
                3, 1, 2
        };

        RawModel model = loader.loadToVAO(vertices, indices);

        //Game loop
        while(!DisplayManager.closed()){
            renderer.prepare();

            shader.start();

            renderer.render(model);

            shader.stop();

            DisplayManager.updateDisplay();
            DisplayManager.swapBuffers();
        }

        shader.cleanUp();
        loader.cleanUp();

    }
}
