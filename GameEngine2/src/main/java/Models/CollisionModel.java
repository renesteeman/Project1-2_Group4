package Models;

import OBJConverter.Vertex;
import org.joml.Vector3f;

import java.util.Arrays;

public class CollisionModel {
    private final TexturedModel texturedModel;
    private final Vector3f[] vertices;
    private final Vector3f[] normals;

    public CollisionModel(TexturedModel model, float[] vertexArray, float[] normalArray){
        this.texturedModel = model;
        this.vertices = floatArrayToPoints(vertexArray);
        this.normals = floatArrayToNormals(normalArray);
    }

    private Vector3f[] floatArrayToPoints(float[] vertexArray){
        Vector3f[] points = new Vector3f[vertexArray.length/3];

        //Vertices are stored as 3 values, split these up
        for(int i=0; i<vertexArray.length; i+=3){
            float vertX = vertexArray[i];
            float vertY = vertexArray[i+1];
            float vertZ = vertexArray[i+2];

            points[i/3] = new Vector3f(vertX, vertY, vertZ);
        }

        return points;
    }

    private Vector3f[] floatArrayToNormals(float[] normalArray){
        Vector3f[] normals = new Vector3f[normalArray.length/3];

        //Normals are stored as 3 values, split these up
        for(int i=0; i<normalArray.length; i+=3){
            float normX = normalArray[i];
            float normY = normalArray[i+1];
            float normZ = normalArray[i+2];

            normals[i/3] = new Vector3f(normX, normY, normZ);
        }

        return normals;
    }

    public TexturedModel getTexturedModel() {
        return texturedModel;
    }

    public Vector3f[] getVertices() {
        return vertices;
    }

    public Vector3f[] getNormals() {
        return normals;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CollisionModel that = (CollisionModel) o;
        return texturedModel.equals(that.texturedModel) &&
                Arrays.equals(vertices, that.vertices);
    }
}
