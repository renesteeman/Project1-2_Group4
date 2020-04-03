package Terrain;

import Models.RawModel;
import RenderEngine.Loader;
import Textures.TerrainTexturePack;
import Toolbox.Maths;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.util.Arrays;

public class Terrain {

    //Every terrain uses the same SIZE and VERTEX_COUNT (SIZE is final in MainGameLoop)
    private final int SIZE;
    private final int VERTEX_COUNT = 512;
    private final float DISTANCE_PER_VERTEX;
    private static final float MAX_PIXEL_COLOR = 256 * 256 *256;

    private float xStart;
    private float zStart;
    private RawModel model;
    private TerrainTexturePack texturePack;

    private float[][] heights;
    private float[] normals;
    private float[] vertices;
    private float[] textureCoords;
    private int[] indices;

    private int[] terrainTypes;

    public Terrain(int gridX, int gridZ, Loader loader, TerrainTexturePack texturePack, int size){
        this.texturePack = texturePack;
        this.SIZE = size;
        this.DISTANCE_PER_VERTEX = (float) SIZE/ (float) VERTEX_COUNT;
        this.xStart = gridX * SIZE;
        this.zStart = gridZ * SIZE;
        this.model = generateTerrain(loader);
    }

    public float getSIZE() {
        return SIZE;
    }

    public int getVertexCount() {
        return VERTEX_COUNT;
    }

    public float getXStart() {
        return xStart;
    }

    public float getZStart() {
        return zStart;
    }

    public RawModel getModel() {
        return model;
    }

    public TerrainTexturePack getTexturePack() {
        return texturePack;
    }

    private RawModel generateTerrain(Loader loader){
        heights = getHeights();
        Vector3f[][] normalVectors = NormalsGenerator.generateNormals(heights);
        normals = normalsToFloatArray(normalVectors);
        vertices = getVertices();
        textureCoords = getTextureCoords();
        indices = getIndices();

        terrainTypes = getTerrainTypes();

        return loader.loadToVAO(vertices, textureCoords, normals, indices, terrainTypes);
    }

    public float getHeightOfTerrain(float worldX, float worldZ){
        float terrainX = worldX - this.xStart;
        float terrainZ = worldZ - this.zStart;
        float gridSquareSize = SIZE / ((float) heights.length - 1);
        int gridX = (int) Math.floor(terrainX / gridSquareSize);
        int gridZ = (int) Math.floor(terrainZ / gridSquareSize);

        if(gridX >= heights.length-1 || gridZ >= heights.length-1 || gridX <0 || gridZ < 0){
            return 0;
        }

        float xCoord = (terrainX % gridSquareSize)/gridSquareSize;
        float zCoord = (terrainZ % gridSquareSize)/gridSquareSize;

        float answer;
        if (xCoord <= (1-zCoord)) {
            answer = Maths.barryCentric(new Vector3f(0, heights[gridX][gridZ], 0), new Vector3f(1,
                            heights[gridX + 1][gridZ], 0), new Vector3f(0,
                            heights[gridX][gridZ + 1], 1), new Vector2f(xCoord, zCoord));
        } else {
            answer = Maths.barryCentric(new Vector3f(1, heights[gridX + 1][gridZ], 0), new Vector3f(1,
                            heights[gridX + 1][gridZ + 1], 1), new Vector3f(0,
                            heights[gridX][gridZ + 1], 1), new Vector2f(xCoord, zCoord));
        }

        return answer;
    }

    //TODO update to actual function
    public float getHeight(float x, float z){

        float add=0;
//        if(x>100&&x<150&&z>50&&z<150){
//            add = (float) (-5);
//        }

        return (float) (2*Math.sin(x) + 2*Math.sin(z) + add);
    }

    private float[] normalsToFloatArray(Vector3f[][] normalVectors){
        float[] normals = new float[VERTEX_COUNT * VERTEX_COUNT * 3];
        int vertexPointer = 0;

        for(int i=0; i<VERTEX_COUNT; i++) {
            for (int j=0; j<VERTEX_COUNT; j++) {
                Vector3f normal = normalVectors[i][j];
                normals[vertexPointer*3] = normal.x;
                normals[vertexPointer*3+1] = normal.y;
                normals[vertexPointer*3+2] = normal.z;
                vertexPointer++;
            }
        }

        return normals;
    }

    private float[][] getHeights(){
        float[][] heights = new float[VERTEX_COUNT][VERTEX_COUNT];

        for(int i=0; i<VERTEX_COUNT; i++) {
            for (int j=0; j<VERTEX_COUNT; j++) {
                float x = j*DISTANCE_PER_VERTEX;
                float z = i*DISTANCE_PER_VERTEX;
                float height = getHeight(x, z);

                heights[j][i] = height;
            }
        }

        return heights;
    }

    private float[] getVertices(){
        int vertexPointer = 0;
        float[] vertices = new float[VERTEX_COUNT * VERTEX_COUNT * 3];

        for(int i=0; i<VERTEX_COUNT;i++) {
            for (int j=0; j<VERTEX_COUNT; j++) {
                vertices[vertexPointer*3] = (float)j/((float)VERTEX_COUNT - 1) * SIZE;
                vertices[vertexPointer*3+1] = heights[j][i];
                vertices[vertexPointer*3+2] = (float)i/((float)VERTEX_COUNT - 1) * SIZE;
                vertexPointer++;
            }
        }

        return vertices;
    }

    private float[] getTextureCoords(){
        int vertexPointer = 0;
        float[] textureCoords = new float[VERTEX_COUNT * VERTEX_COUNT*2];

        for(int i=0; i<VERTEX_COUNT;i++) {
            for (int j=0; j<VERTEX_COUNT; j++) {
                textureCoords[vertexPointer*2] = (float)j/((float)VERTEX_COUNT - 1);
                textureCoords[vertexPointer*2+1] = (float)i/((float)VERTEX_COUNT - 1);
                vertexPointer++;
            }
        }

        return textureCoords;
    }

    private int[] getIndices(){
        int vertexPointer = 0;
        int[] indices = new int[6*(VERTEX_COUNT-1)*(VERTEX_COUNT-1)];

        for(int gz=0;gz<VERTEX_COUNT-1;gz++){
            for(int gx=0;gx<VERTEX_COUNT-1;gx++){
                int topLeft = (gz*VERTEX_COUNT)+gx;
                int topRight = topLeft + 1;
                int bottomLeft = ((gz+1)*VERTEX_COUNT)+gx;
                int bottomRight = bottomLeft + 1;

                indices[vertexPointer++] = topLeft;
                indices[vertexPointer++] = bottomLeft;
                indices[vertexPointer++] = topRight;
                indices[vertexPointer++] = topRight;
                indices[vertexPointer++] = bottomLeft;
                indices[vertexPointer++] = bottomRight;
            }
        }

        return indices;
    }

    private int[] getTerrainTypes(){
        int vertexPointer = 0;
        int[] terrainTypes = new int[VERTEX_COUNT*VERTEX_COUNT];

        for(int i=0; i<VERTEX_COUNT;i++) {
            for (int j=0; j<VERTEX_COUNT; j++) {
                float x = i * DISTANCE_PER_VERTEX;
                float z = j * DISTANCE_PER_VERTEX;
                int terrainType = getTerrainType(x, z);

                terrainTypes[vertexPointer] = terrainType;
                vertexPointer++;
            }
        }

        return terrainTypes;
    }

    //TODO load in actual values or set all to 0
    private int getTerrainType(float x, float z){
        if(x+z>50 && x+z<100){
            return 1;
        } else {
            return 0;
        }
    }
}
