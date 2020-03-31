package Terrain;

import RenderEngine.Loader;
import org.joml.Vector3f;

public class TerrainLoader {
    protected static void loadTerrain(Terrain terrain, int gridX, int gridZ, float[][] heights, Vector3f[][] normals) {
        int vao = Loader.createVAO();
        int[] indices = GridGenerator.generateGridIndexBuffer(Terrain.VERTEX_COUNT, (gridX+gridZ)%2==0);
        Loader.createIndicesVBO(vao, indices);
        loadPositionNormalBuffer(vao, heights, normals, gridX, gridZ);
        int colourVbo = Loader.createEmptyInterleavedVBO(vao, Terrain.VERTEX_COUNT * Terrain.VERTEX_COUNT, 2, 3, 4);
        terrain.setVboData(vao, colourVbo, indices.length);
    }

    private static void loadPositionNormalBuffer(int vao, float[][] heights, Vector3f[][] normalsGrid, int gridX, int gridZ) {
        float[] positions = generatePositionData(heights, gridX, gridZ);
        float[] normals = generateNormalData(normalsGrid);
        float[] interleavedData = Loader.interleaveFloatData(positions.length / 3, positions, normals);
        Loader.storeInterleavedDataInVAO(vao, interleavedData, 3, 3);
    }

    private static float[] generatePositionData(float[][] heights, int xStart, int zStart) {
        float[] positions = new float[heights.length * heights.length * 3];
        float squareSize = Terrain.SIZE / (Terrain.VERTEX_COUNT - 1);
        int pointer = 0;
        for (int z = 0; z < heights.length; z++) {
            for (int x = 0; x < heights.length; x++) {
                positions[pointer++] = xStart + x * squareSize;
                positions[pointer++] = heights[z][x];
                positions[pointer++] = zStart + z * squareSize;
            }
        }
        return positions;
    }

    private static float[] generateNormalData(Vector3f[][] normalsGrid) {
        float[] normals = new float[normalsGrid.length * normalsGrid.length * 3];
        int pointer = 0;
        for (int z = 0; z < normalsGrid.length; z++) {
            for (int x = 0; x < normalsGrid.length; x++) {
                Vector3f normal = normalsGrid[z][x];
                normals[pointer++] = normal.x;
                normals[pointer++] = normal.y;
                normals[pointer++] = normal.z;
            }
        }
        return normals;
    }
}
