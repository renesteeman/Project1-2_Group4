package Shaders;

import Entities.Camera;
import Entities.Light;
import Shaders.ShaderProgram;
import Toolbox.Maths;
import org.joml.Matrix4f;

public class WaterShader extends ShaderProgram {

	private final static String VERTEX_FILE = "src/main/java/Shaders/waterVertex.txt";
	private final static String FRAGMENT_FILE = "src/main/java/Shaders/waterFragment.txt";

	private int location_modelMatrix;
	private int location_viewMatrix;
	private int location_projectionMatrix;
	private int location_reflectionTexture;
	private int location_refractionTexture;
	private int location_DuDvMap;
	private int location_moveFactor;
	private int location_cameraPosition;
	private int location_normalMap;
	private int location_lightColor;
	private int location_lightPosition;

	public WaterShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void bindAttributes() {
		bindAttribute(0, "position");
	}

	@Override
	protected void getAllUniformLocations() {
		location_projectionMatrix = getUniformLocation("projectionMatrix");
		location_viewMatrix = getUniformLocation("viewMatrix");
		location_modelMatrix = getUniformLocation("modelMatrix");
		location_reflectionTexture = getUniformLocation("reflectionTexture");
		location_refractionTexture = getUniformLocation("refractionTexture");
		location_DuDvMap = getUniformLocation("DuDvMap");
		location_moveFactor = getUniformLocation("moveFactor");
		location_cameraPosition = getUniformLocation("cameraPosition");
		location_normalMap = getUniformLocation("normalMap");
		location_lightColor = getUniformLocation("lightColor");
		location_lightPosition = getUniformLocation("lightPosition");
	}

	public void connectTextureUnits(){
		super.loadInt(location_reflectionTexture, 0);
		super.loadInt(location_refractionTexture, 1);
		super.loadInt(location_DuDvMap, 2);
		super.loadInt(location_normalMap, 3);
	}

	public void loadLight(Light sun){
		super.loadVector(location_lightColor, sun.getColor());
		super.loadVector(location_lightPosition, sun.getPosition());
	}

	public void loadMoveFactor(float factor){
		super.loadFloat(location_moveFactor, factor);
	}

	public void loadProjectionMatrix(Matrix4f projection) {
		loadMatrix(location_projectionMatrix, projection);
	}
	
	public void loadViewMatrix(Camera camera){
		Matrix4f viewMatrix = Maths.createViewMatrix(camera);
		loadMatrix(location_viewMatrix, viewMatrix);
		super.loadVector(location_cameraPosition, camera.getPosition());
	}

	public void loadModelMatrix(Matrix4f modelMatrix){
		loadMatrix(location_modelMatrix, modelMatrix);
	}

}
