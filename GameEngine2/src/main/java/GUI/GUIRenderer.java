package GUI;

import Models.RawModel;
import RenderEngine.Loader;
import Toolbox.Maths;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.util.List;

public class GUIRenderer {

    private final RawModel quad;
    private GUIShader shader;

    public GUIRenderer(Loader loader){
        float[] positions = {-1, 1, -1, -1, 1, 1, 1, -1};
        quad = loader.loadToVAO(positions, 2);
        shader = new GUIShader();
    }

    public void render(List<GUITexture> GUIs){
        shader.start();

        GL30.glBindVertexArray(quad.getVaoID());
        GL20.glEnableVertexAttribArray(0);

        //Enable alpha blender / transparency
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        //Disable depth test / prevent UI from overlapping with transparent parts
        GL11.glDisable(GL11.GL_DEPTH_TEST);

        //Render
        for(GUITexture GUI : GUIs){
            GL13.glActiveTexture(GL13.GL_TEXTURE0);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, GUI.getTexture());
            Matrix4f transformationMatrix = Maths.createTransformationMatrix(GUI.getPosition(), GUI.getScale());
            shader.loadTransformation(transformationMatrix);
            GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, quad.getVertexCount());
        }

        //Disable transparency
        GL11.glDisable(GL11.GL_BLEND);

        //Enable depth test
        GL11.glEnable(GL11.GL_DEPTH_TEST);

        GL20.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);
        shader.stop();
    }

    public void cleanUp(){
        shader.cleanUp();
    }
}
