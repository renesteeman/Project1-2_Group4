package water;

import java.util.List;

import Entities.Camera;
import Models.RawModel;
import RenderEngine.Loader;
import Shaders.WaterShader;
import Toolbox.Maths;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class WaterRenderer {

	private RawModel quad;
	private WaterShader shader;
	private WaterFrameBuffers fbos;

	public WaterRenderer(Loader loader, WaterShader shader, Matrix4f projectionMatrix, WaterFrameBuffers fbos) {
		this.shader = shader;
		this.fbos = fbos;

		shader.start();
		shader.connectTextureUnits();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.stop();

		setUpVAO(loader);
	}

	public void render(List<WaterTile> water, Camera camera) {
		prepareRender(camera);

		for (WaterTile tile : water) {
			Matrix4f modelMatrix = Maths.createTransformationMatrix( new Vector3f(tile.getX(), tile.getHeight(), tile.getZ()), 0, 0, 0, tile.getSize());
			shader.loadModelMatrix(modelMatrix);
			GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, quad.getVertexCount());
		}

		unbind();
	}
	
	private void prepareRender(Camera camera){
		shader.start();
		shader.loadViewMatrix(camera);
		GL30.glBindVertexArray(quad.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, fbos.getReflectionTexture());
		GL13.glActiveTexture(GL13.GL_TEXTURE);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, fbos.getRefractionTexture());
	}
	
	private void unbind(){
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
		shader.stop();
	}

	private void setUpVAO(Loader loader) {
		//Just x and z vertex positions here, y is set to 0 in v.shader
		float[] vertices = { -1, -1, -1, 1, 1, -1, 1, -1, -1, 1, 1, 1 };
		quad = loader.loadToVAO(vertices, 2);
	}

}
