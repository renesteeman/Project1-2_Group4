package Terrain;

import Physics.Function2d;
import Physics.Vector2d;
import Models.RawModel;
import RenderEngine.Loader;
import Textures.TerrainTexturePack;
import Toolbox.Maths;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.Arrays;

public class Terrain {

    //Every terrain uses the same SIZE and VERTEX_COUNT (SIZE is final in MainGameLoop)
    private final int SIZE;
    private final int VERTEX_COUNT = 512;
    private final float DISTANCE_PER_VERTEX;
    private static final float MAX_PIXEL_COLOR = 256 * 256 * 256;

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

    private Function2d heightFunction;

    /*
    //TESTING ONLY
    public Terrain(int gridX, int gridZ, Loader loader, TerrainTexturePack texturePack, int size){
        this.texturePack = texturePack;
        this.SIZE = size;
        this.DISTANCE_PER_VERTEX = (float) SIZE/ (float) VERTEX_COUNT;
        this.xStart = gridX * SIZE;
        this.zStart = gridZ * SIZE;
        this.model = generateTerrain(loader);
    }*/

    public Terrain(int gridX, int gridZ, Loader loader, Function2d function, TerrainTexturePack texturePack, int size){
        this.texturePack = texturePack;
        this.SIZE = size;
        this.DISTANCE_PER_VERTEX = (float) SIZE/ (float) VERTEX_COUNT;
        this.xStart = gridX * SIZE;
        this.zStart = gridZ * SIZE;
        this.model = generateTerrainFunctionBased(loader, function);
        this.heightFunction = function;
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

    /*
    //TODO can this be removed?
    private RawModel generateTerrain(Loader loader){
        heights = getHeightsGeneration();
        Vector3f[][] normalVectors = NormalsGenerator.generateNormals(heights);
        normals = normalsToFloatArray(normalVectors);
        vertices = getVerticesGeneration();
        textureCoords = getTextureCoordsGeneration();
        indices = getIndicesGeneration();
        terrainTypes = getTerrainTypesGeneration();

        return loader.loadToVAO(vertices, textureCoords, normals, indices, terrainTypes);
    }*/

    private RawModel generateTerrainFunctionBased(Loader loader, Function2d function) {
        heights = getHeightsGenerationFunctionBased(function);
        Vector3f[][] normalVectors = NormalsGenerator.generateNormals(heights);
        normals = normalsToFloatArray(normalVectors);
        vertices = getVerticesGeneration();
        textureCoords = getTextureCoordsGeneration();
        indices = getIndicesGeneration();
        terrainTypes = getTerrainTypesGeneration();

        return loader.loadToVAO(vertices, textureCoords, normals, indices, terrainTypes);   
    }

    public void updateTerrain(Loader loader){
        this.model = loader.loadToVAO(vertices, textureCoords, normals, indices, terrainTypes);
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

    private float[][] getHeightsGenerationFunctionBased(Function2d function) {
        float[][] heights = new float[VERTEX_COUNT][VERTEX_COUNT];
        
        for (int i = 0; i < VERTEX_COUNT; i++) {
            for (int j = 0; j < VERTEX_COUNT; j++) {
                float x = j * DISTANCE_PER_VERTEX;
                float z = i * DISTANCE_PER_VERTEX;
                double height = function.evaluate(new Vector2d(x, z));
                
                heights[j][i] = (float)height;
            }
        }

        return heights;
    }

    private float[] getVerticesGeneration(){
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

    private float[] getTextureCoordsGeneration(){
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

    private int[] getIndicesGeneration(){
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

    private int[] getTerrainTypesGeneration(){
        int vertexPointer = 0;
        int[] terrainTypes = new int[VERTEX_COUNT*VERTEX_COUNT];

        for(int i=0; i<VERTEX_COUNT;i++) {
            for (int j=0; j<VERTEX_COUNT; j++) {
                float x = i * DISTANCE_PER_VERTEX;
                float z = j * DISTANCE_PER_VERTEX;
                int terrainType = getCreateTerrainType(x, z);

                terrainTypes[vertexPointer] = terrainType;
                vertexPointer++;
            }
        }

        return terrainTypes;
    }

    private int getCreateTerrainType(float x, float z){
        //Always return grass 0
        return 0;
    }

    public void setTerrainTypeWithinDiameter(float x, float z, int type, float diameter){
        //Take the terrain starting points into account before determining any indexes
        Vector2f terrainCoordinates = coordinateToTerrainCoordinates(x, z);
        float terrainCoordinateZ = terrainCoordinates.y;

        //Get the center of the circle
        int centerX = (int) terrainCoordinates.x;
        int centerZ = (int) terrainCoordinates.y;

        //Get the edges of the square within the terrain has to be updated (optimization)
        int leftX = (int) (terrainCoordinates.x-diameter/2);
        int rightX = (int) (terrainCoordinates.x+diameter/2);
        int topZ = (int) (terrainCoordinateZ+diameter/2);
        int bottomZ = (int) (terrainCoordinateZ-diameter/2);

        //Prevent going over the edge (and crashing)
        if(leftX<0) leftX=0;
        if(rightX<0) rightX=0;
        if(topZ<0) topZ=0;
        if(bottomZ<0) bottomZ=0;
        if(centerX<0) centerX=0;
        if(centerZ<0) centerZ=0;

        //Calculate relevant edges
        int leftXTerrainCoordinate = (int) (leftX/DISTANCE_PER_VERTEX);
        int rightXTerrainCoordinate = (int) (rightX/DISTANCE_PER_VERTEX);
        int topZTerrainCoordinate = (int) (topZ/DISTANCE_PER_VERTEX);
        int bottomZTerrainCoordinate = (int) (bottomZ/DISTANCE_PER_VERTEX);

        //Go trough the square and update
        for(int i=leftXTerrainCoordinate; i<rightXTerrainCoordinate; i++){
            for(int j=bottomZTerrainCoordinate; j<topZTerrainCoordinate; j++){
                //Only update values within given diameter (make the brush circular instead of square)
                int xPos = (int) (i*DISTANCE_PER_VERTEX);
                int zPos = (int) (j*DISTANCE_PER_VERTEX);

                if(distance(xPos, zPos, centerX, centerZ) < diameter/2){
                    //Should be updated
                    updateTerrainType2D(i, j, type);
                }
            }
        }
    }

    private Vector2f coordinateToTerrainCoordinates(float x, float z){
        Vector2f result = new Vector2f();
        result.x = x-xStart;
        result.y = z-zStart;

        return result;
    }

    private float distance(int i, int j, float heightAtPosition, float x, float z, float y){
        float dX = x-i;
        float dZ = z-j;
        float dY = y-heightAtPosition;

        return (float) Math.sqrt(Math.pow(dX, 2) + Math.pow(dZ, 2) + Math.pow(dY, 2));
    }

    private float distance(int i, int j, float x, float z){
        float dX = x-i;
        float dZ = z-j;

        return (float) Math.sqrt(Math.pow(dX, 2) + Math.pow(dZ, 2));
    }

    private void updateTerrainType2D(int x, int z, int type){
        //Convert 2D coordinates to array index
        //z*VERTEX_COUT = the amount of indices for the rows that are fully filled, x gives the amount of indices for the
        //last row that is partially filled
        int index = z*VERTEX_COUNT + x;

        if(index<terrainTypes.length){
            terrainTypes[index] = type;
        } else {
            System.out.println("ERROR: The terrainType is updated for an index that doesn't exist.");
        }
    }

    public int getTerrainTypeAtTerrainPoint(float x, float z) {
        //Take the terrain starting points into account before determining the index
        Vector2f terrainCoordinates = coordinateToTerrainCoordinates(x, z);
        int xCord = (int) terrainCoordinates.x;
        int zCord = (int) terrainCoordinates.y;
        if(xCord<0) xCord=0;
        if(zCord<0) zCord=0;

        int index = zCord*VERTEX_COUNT + xCord;

        if(index>terrainTypes.length) return 0;

        return terrainTypes[index];
    }

    public double getHeightFromFunction(float x, float z){
        return heightFunction.evaluate(new Vector2d(x, z));
    }

    public String getTerrainInfoAsString(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(":HEIGHTS:");
        stringBuilder.append(Arrays.deepToString(heights));
        stringBuilder.append(":VERTICES:");
        stringBuilder.append(Arrays.toString(vertices));
        stringBuilder.append(":TEXTURE_COORDS:");
        stringBuilder.append(Arrays.toString(textureCoords));
        stringBuilder.append(":INDICES:");
        stringBuilder.append(Arrays.toString(indices));
        stringBuilder.append(":TERRAIN_TYPES:");
        stringBuilder.append(Arrays.toString(terrainTypes));

        return stringBuilder.toString();
    }

    public void loadFromString(String terrainInfo){
        String[] parts = terrainInfo.split(":");
        String heightsString = parts[2];
        String verticesString = parts[4];
        String textureCoordsString = parts[6];
        String indicesString = parts[8];
        String terrainTypesString = parts[10];

        //Load in the actual values
        this.heights = getHeightsArrayFromFileData(heightsString);
        Vector3f[][] normalVectors = NormalsGenerator.generateNormals(heights);
        this.normals = normalsToFloatArray(normalVectors);
        this.vertices = getFloatArrayFromFileData(verticesString);
        this.textureCoords = getFloatArrayFromFileData(textureCoordsString);
        this.indices = getIntArrayFromFileData(indicesString);
        this.terrainTypes = getIntArrayFromFileData(terrainTypesString);
    }

    private int[] getIntArrayFromFileData(String inputString){
        ArrayList<Integer> valuesArrayList = new ArrayList<Integer>();

        //Remove whitespace
        inputString = inputString.replaceAll("\\s+","");

        String[] valueArray = inputString.split(",");
        //Remove [ sign for the first value
        valueArray[0] = valueArray[0].substring(1);
        //Remove ] sign for the last value
        valueArray[valueArray.length-1] = valueArray[valueArray.length-1].substring(0, valueArray[valueArray.length-1].length()-1);

        //Convert from string to int
        for(String value : valueArray){
            valuesArrayList.add(Integer.parseInt(value));
        }

        int[] intArray = new int[valuesArrayList.size()];

        //Go from ArrayList to array
        for(int i=0; i<valuesArrayList.size(); i++){
            intArray[i] = valuesArrayList.get(i);
        }

        return intArray;
    }

    private float[] getFloatArrayFromFileData(String inputString){
        ArrayList<Float> valuesArrayList = new ArrayList<Float>();

        //Remove whitespace
        inputString = inputString.replaceAll("\\s+","");

        String[] valueArray = inputString.split(",");
        //Remove [ sign for the first value
        valueArray[0] = valueArray[0].substring(1);
        //Remove ] sign for the last value
        valueArray[valueArray.length-1] = valueArray[valueArray.length-1].substring(0, valueArray[valueArray.length-1].length()-1);

        //Convert from string to int
        for(String value : valueArray){
            valuesArrayList.add(Float.parseFloat(value));
        }

        float[] floatArray = new float[valuesArrayList.size()];

        //Go from ArrayList to array
        for(int i=0; i<valuesArrayList.size(); i++){
            floatArray[i] = valuesArrayList.get(i);
        }

        return floatArray;
    }

    private float[][] getHeightsArrayFromFileData(String heights){
        ArrayList<ArrayList<Float>> heightValuesFloatListList = new ArrayList<>();

        //Remove whitespace
        heights = heights.replaceAll("\\s+","");

        String[] heightsArray = heights.split("\\[");

        for(String heightArray : heightsArray){
            //Skip the first two values which are empty
            if(heightArray.length()==0) continue;

            //Remove the last two signs, for non-end values this means ], and for the last one it means ]] gets removed
            heightArray = heightArray.substring(0, heightArray.length()-2);
            String[] heightValues = heightArray.split(",");

            //Go from one big string to ArrayList<Float>
            ArrayList<Float> heightValuesFloatList = new ArrayList<Float>();
            for(String heightValue : heightValues){
                heightValuesFloatList.add(Float.parseFloat(heightValue));
            }
            heightValuesFloatListList.add(heightValuesFloatList);
        }

        float[][] floatHeights = new float[heightValuesFloatListList.size()][heightValuesFloatListList.get(0).size()];

        //Go from ArrayList to array
        for(int i=0; i<floatHeights.length; i++){
            ArrayList<Float> heightValuesFloatList = heightValuesFloatListList.get(i);

            Object[] heightValuesObjectArray = heightValuesFloatList.toArray();
            float[] heightValuesFloatArray = new float[heightValuesObjectArray.length];

            for(int j=0; j<heightValuesObjectArray.length-1; j++){
                heightValuesFloatArray[j] = (float) heightValuesObjectArray[j];
            }

            floatHeights[i] = heightValuesFloatArray;
        }

        return floatHeights;
    }
}
