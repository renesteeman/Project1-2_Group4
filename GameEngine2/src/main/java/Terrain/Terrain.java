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

    private float[][] heights;
    //Dirt of sand, 0 or 1, [x][z], corresponds to terrainVertices
    private int[][] terrainTypes;

    //Keep track of the vertices that make up the terrain
    //[x][z]
    private TerrainVertex[][] terrainVertices;

    public Terrain(int gridX, int gridZ, Loader loader, TerrainTexturePack texturePack, String heightMap, int size){
        this.texturePack = texturePack;
        this.SIZE = size;
        this.x = gridX * SIZE;
        this.z = gridZ * SIZE;
        this.model = generateTerrain(loader, heightMap);
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
    private RawModel generateTerrain(Loader loader, String heightMap){
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File("res/" + heightMap + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        int VERTEX_COUNT = image.getHeight();
        heights = new float[VERTEX_COUNT][VERTEX_COUNT];
        terrainTypes = new int[VERTEX_COUNT*VERTEX_COUNT];

        int count = VERTEX_COUNT * VERTEX_COUNT;
        float[] vertices = new float[count * 3];
        float[] normals = new float[count * 3];
        float[] textureCoords = new float[count*2];
        int[] indices = new int[6*(VERTEX_COUNT-1)*(VERTEX_COUNT-1)];
        int vertexPointer = 0;

        for(int z=0;z<VERTEX_COUNT;z++){
            for(int x=0;x<VERTEX_COUNT;x++){
                vertices[vertexPointer*3] = (float)x/((float)VERTEX_COUNT - 1) * SIZE;
                float height = getHeight(x, z, image);
                heights[x][z] = height;
                terrainTypes[vertexPointer] = getTerrainType(x, z, VERTEX_COUNT);
                vertices[vertexPointer*3+1] = height;
                vertices[vertexPointer*3+2] = (float) z /((float)VERTEX_COUNT - 1) * SIZE;
                Vector3f normal = calculateNormal(x, z, image);
                normals[vertexPointer*3] = normal.x;
                normals[vertexPointer*3+1] = normal.y;
                normals[vertexPointer*3+2] = normal.z;
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

    public float getHeightOfTerrain(float worldX, float worldZ){
        float terrainX = worldX - this.x;
        float terrainZ = worldZ - this.z;
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

    private float getHeight(int x, int z, BufferedImage image){
        if(x<0 || x>=image.getHeight() || z<0 || z>= image.getHeight()){
            return 0;
        }

        float height = image.getRGB(x, z);
        //convert to range -1, 1
        height += MAX_PIXEL_COLOR/2f;
        height /= MAX_PIXEL_COLOR/2;

        height *= MAX_HEIGHT;
        return height;
    }

    private Vector3f calculateNormal(int x, int z, BufferedImage image){
        float heightL = getHeight(x-1, z, image);
        float heightR = getHeight(x+1, z, image);
        float heightD = getHeight(x, z-1, image);
        float heightU = getHeight(x, z+1, image);
        Vector3f normal = new Vector3f(heightL-heightR, 2f, heightD - heightU);
        normal.normalize();

        return normal;
    }
}
