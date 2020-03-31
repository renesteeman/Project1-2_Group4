package RenderEngine;

import Entities.Camera;
import Entities.Entity;
import Entities.Light;
import Models.TexturedModel;
import Shaders.StaticShader;
import Shaders.TerrainShader;
import Skybox.SkyboxRenderer;
import Terrain.Terrain;
import org.joml.Matrix4f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MasterRenderer {
    private static final float FOV = 75;
    private static final float NEAR_PLANE = 0.1f;
    private static final float FAR_PLANE = 2000f;

    private StaticShader shader = new StaticShader();
    private EntityRenderer renderer;

    private TerrainRenderer terrainRenderer;
    private TerrainShader terrainShader = new TerrainShader();

    private Matrix4f projectionMatrix;

    private Map<TexturedModel, List<Entity>> entities = new HashMap<TexturedModel, List<Entity>>();
    private List<Terrain> terrains = new ArrayList<Terrain>();

    private SkyboxRenderer skyboxRenderer;

    public MasterRenderer(Loader loader){
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glCullFace(GL11.GL_BACK);
        createProjectionMatrix();
        renderer = new EntityRenderer(shader, projectionMatrix);
        terrainRenderer = new TerrainRenderer(terrainShader, projectionMatrix);
        skyboxRenderer = new SkyboxRenderer(loader, projectionMatrix);
    }

    public void renderScene(List<Entity> entities, Terrain terrain, Light light, Camera camera, Vector4f clipPlane){
        processTerrain(terrain);

        for (Entity entity : entities) {
            processEntity(entity);
        }

        render(light, camera, clipPlane);
    }

    public void render(Light sun, Camera camera, Vector4f clipPlane){
        prepare();

        //Render entities
        shader.start();
        shader.loadClipPlane(clipPlane);
        shader.loadLight(sun);
        shader.loadViewMatrix(camera);

        renderer.render(entities);

        shader.stop();

        //Render terrain
        terrainShader.start();
        terrainShader.loadClipPlane(clipPlane);
        terrainShader.loadLight(sun);
        terrainShader.loadViewMatrix(camera);

        terrainRenderer.render(terrains);

        terrainShader.stop();

        //Render skybox
        skyboxRenderer.render(camera);

        terrains.clear();
        entities.clear();
    }

    public void processTerrain(Terrain terrain){
        terrains.add(terrain);
    }

    public void processEntity(Entity entity){
        TexturedModel entityModel = entity.getModel();
        List<Entity> batch = entities.get(entityModel);
        if(batch!=null){
            batch.add(entity);
        } else {
            List<Entity> newBatch = new ArrayList<Entity>();
            newBatch.add(entity);
            entities.put(entityModel, newBatch);
        }
    }

    public void cleanUp(){
        shader.cleanUp();
        terrainShader.cleanUp();
    }

    public void prepare(){
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glClearColor(1, 0, 0, 1);
    }

    private void createProjectionMatrix(){
        float aspectRatio = (float) DisplayManager.getWidth() / (float) DisplayManager.getHeight();
        float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV/2f))) * aspectRatio);
        float x_scale = y_scale / aspectRatio;
        float frustum_length = FAR_PLANE - NEAR_PLANE;

        projectionMatrix = new Matrix4f();
        projectionMatrix.m00(x_scale);
        projectionMatrix.m11(y_scale);
        projectionMatrix.m22(-((FAR_PLANE + NEAR_PLANE) / frustum_length));
        projectionMatrix.m23(-1);
        projectionMatrix.m32(-((2 * NEAR_PLANE * FAR_PLANE) / frustum_length));
        projectionMatrix.m33(0);
    }

    public Matrix4f getProjectionMatrix() {
        return projectionMatrix;
    }
}


