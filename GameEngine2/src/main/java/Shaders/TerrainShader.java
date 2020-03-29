package Shaders;

import Entities.Camera;
import Entities.Light;
import Toolbox.Maths;
import org.joml.Matrix4f;
import org.joml.Vector4f;

public class TerrainShader extends ShaderProgram {

    private static final String VERTEX_FILE = "./src/main/java/Shaders/terrainVertexShader.txt";
    private static final String FRAGMENT_FILE = "./src/main/java/Shaders/terrainFragmentShader.txt";

    private int location_transformationMatrix;
    private int location_viewMatrix;
    private int location_projectionMatrix;
    private int location_lightPosition;
    private int location_lightColor;
    private int location_shineDamper;
    private int location_reflectivity;
    private int location_grassTexture;
    private int location_sandTexture;
    //TODO remove
    private int location_brickTexture;
    private int location_blendMap;
    //
    private int location_plane;

    public TerrainShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    @Override
    protected void getAllUniformLocations() {
        location_transformationMatrix = super.getUniformLocation("transformationMatrix");
        location_projectionMatrix = super.getUniformLocation("projectionMatrix");
        location_viewMatrix = super.getUniformLocation("viewMatrix");
        location_lightPosition = super.getUniformLocation("lightPosition");
        location_lightColor = super.getUniformLocation("lightColor");
        location_shineDamper = super.getUniformLocation("shineDamper");
        location_reflectivity = super.getUniformLocation("reflectivity");
        location_grassTexture = super.getUniformLocation("grassTexture");
        location_sandTexture = super.getUniformLocation("sandTexture");
        location_brickTexture = super.getUniformLocation("brickTexture");
        location_blendMap = super.getUniformLocation("blendMap");
        location_plane = super.getUniformLocation("plane");
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "textureCoords");
        super.bindAttribute(2, "normal");
    }

    public void connectTextureUnits(){
        super.loadInt(location_grassTexture, 0);
        super.loadInt(location_sandTexture, 1);
        //TODO remove
        super.loadInt(location_brickTexture, 2);
        super.loadInt(location_blendMap, 3);
    }

    public void loadShineVariables(float damper, float reflectivity){
        super.loadFloat(location_shineDamper, damper);
        super.loadFloat(location_reflectivity, reflectivity);
    }

    public void loadLight(Light light){
        super.loadVector(location_lightPosition, light.getPosition());
        super.loadVector(location_lightColor, light.getColor());
    }

    public void loadClipPlane(Vector4f plane){
        super.loadVector(location_plane, plane);
    }

    public void loadTransformationMatrix(Matrix4f transformation){
        super.loadMatrix(location_transformationMatrix, transformation);
    }

    public void loadProjectionMatrix(Matrix4f projection){
        super.loadMatrix(location_projectionMatrix, projection);
    }

    public void loadViewMatrix(Camera camera){
        Matrix4f viewMatrix = Maths.createViewMatrix(camera);
        super.loadMatrix(location_viewMatrix, viewMatrix);
    }

}
