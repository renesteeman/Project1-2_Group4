package Terrain;

import Models.RawModel;
import RenderEngine.Loader;
import Textures.ModelTexture;
import Textures.TerrainTexture;
import Textures.TerrainTexturePack;
import Toolbox.Maths;
import javafx.css.Size;
import org.joml.Vector2f;
import org.joml.Vector3f;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import static org.lwjgl.nuklear.NkDrawList.VERTEX_COUNT;

//Currently only square terrains are supported
public class Terrain {

    private final int SIZE;
    private final int VERTICES_PER_SIZE_UNIT = 1;
    private final int VERTEX_COUNT;
    private static final float MAX_HEIGHT = 40;
    private static final float MAX_PIXEL_COLOR = 256 * 256 *256;

    private float x;
    private float z;
    private RawModel model;
    private TerrainTexturePack texturePack;

    //Keep track of the vertices that make up the terrain
    //[x][z]
    private TerrainVertex[][] terrainVertices;
    private float[][] heights;
    private Vector3f[][] normals;
    private float[][] textureCoords;
    //Dirt of sand, 0 or 1, [x][z], corresponds to terrainVertices
    private int[][] terrainTypes;

    public Terrain(int gridX, int gridZ, Loader loader, TerrainTexturePack texturePack, int size){
        this.texturePack = texturePack;
        this.SIZE = size;
        this.x = gridX * SIZE;
        this.z = gridZ * SIZE;
        this.model = generateTerrain(loader);
        this.VERTEX_COUNT = SIZE * VERTICES_PER_SIZE_UNIT;
    }

    public float getSIZE() {
        return SIZE;
    }

    public int getVertexCount() {
        return VERTEX_COUNT;
    }

    public float getX() {
        return x;
    }

    public float getZ() {
        return z;
    }

    public RawModel getModel() {
        return model;
    }

    public TerrainTexturePack getTexturePack() {
        return texturePack;
    }

    //TODO finish
    public void updateTerrainType(int type, Vector2f position, float radius){
        for(int z=0; z<VERTEX_COUNT;z++) {
            for(int x = 0; x < VERTEX_COUNT; x++) {
                //TODO For all vertices in radius
                updateVertex(x, z, type);
            }
        }
    }

    private void updateVertex(int x, int z, int type) {
        TerrainVertex terrainVertex = getTerrainVertex(x, z);
        terrainVertex.updateType(type);
    }

    public int getTerrainType(int x, int z){
        TerrainVertex terrainVertex = getTerrainVertex(x, z);
        return terrainVertex.getType();
    }

    public TerrainVertex getTerrainVertex(int x, int z){
        return terrainVertices[x][z];
    }

    private void createTerrainVertices() {
        this.terrainVertices = new TerrainVertex[VERTEX_COUNT][VERTEX_COUNT];
        for (int z = 0; z < VERTEX_COUNT; z++) {
            for (int x = 0; x < VERTEX_COUNT; x++) {
                terrainVertices[x][z] = new TerrainVertex(terrainTypes[x][z]);
            }
        }
    }

    //TODO remove heightMap and rewrite with new terrain system
    private RawModel generateTerrain(Loader loader){
        generateHeights();
        normals = NormalsGenerator.generateNormals(heights);
        generateTerrainTypes();
        
        float[] vertices = new float[count * 3];
        float[] textureCoords = new float[count*2];
        int[] indices = new int[6*(VERTEX_COUNT-1)*(VERTEX_COUNT-1)];
        int vertexPointer = 0;

        for(int z=0;z<VERTEX_COUNT;z++){
            for(int x=0;x<VERTEX_COUNT;x++){
                vertices[vertexPointer*3] = (float)x/((float)VERTEX_COUNT - 1) * SIZE;
                float height = getHeight(x, z);
                heights[x][z] = height;
                terrainTypes[vertexPointer] = getTerrainType(x, z, VERTEX_COUNT);
                vertices[vertexPointer*3+1] = height;
                vertices[vertexPointer*3+2] = (float) z /((float)VERTEX_COUNT - 1) * SIZE;
                textureCoords[vertexPointer*2] = (float)x/((float)VERTEX_COUNT - 1);
                textureCoords[vertexPointer*2+1] = (float) z /((float)VERTEX_COUNT - 1);
                vertexPointer++;
            }
        }
        int pointer = 0;
        for(int gz=0;gz<VERTEX_COUNT-1;gz++){
            for(int gx=0;gx<VERTEX_COUNT-1;gx++){
                int topLeft = (gz*VERTEX_COUNT)+gx;
                int topRight = topLeft + 1;
                int bottomLeft = ((gz+1)*VERTEX_COUNT)+gx;
                int bottomRight = bottomLeft + 1;
                indices[pointer++] = topLeft;
                indices[pointer++] = bottomLeft;
                indices[pointer++] = topRight;
                indices[pointer++] = topRight;
                indices[pointer++] = bottomLeft;
                indices[pointer++] = bottomRight;
            }
        }

        return loader.loadToVAOTerrain(vertices, textureCoords, normals, indices, terrainTypes);
    }

    private int addVertex(float data[], int pointer, TerrainVertex vertex){
        data[pointer++] = topLeft;
        data[pointer++] = bottomLeft;
        data[pointer++] = topRight;
        data[pointer++] = topRight;
        data[pointer++] = bottomLeft;
        data[pointer++] = bottomRight;
    }



    private void generateTerrainTypes(){
        int[][] types = new int[VERTEX_COUNT][VERTEX_COUNT];
        Arrays.fill(types, 0);
        generateTerrainTypes(types);
    }

    private void generateTerrainTypes(int[][] types){
        for(int z=0;z<VERTEX_COUNT;z++) {
            for (int x = 0; x < VERTEX_COUNT; x++) {
                terrainTypes[x][z] = types[x][z];
            }
        }
    }

    private void generateHeights(){
        for(int z=0;z<VERTEX_COUNT;z++) {
            for (int x = 0; x < VERTEX_COUNT; x++) {
                heights[x][z] = getHeight(x, z);
            }
        }
    }

    //TODO use actual function
    private float getHeight(int x, int z){
        float height = x*2+z;
        return height;
    }

}
