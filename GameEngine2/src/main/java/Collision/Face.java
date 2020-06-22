package Collision;

import org.joml.Vector3f;

//A face is a group of three connected vertices
public class Face {
    final private Vector3f firstVertex;
    final private Vector3f secondVertex;
    final private Vector3f thirdVertex;

    public Face(Vector3f firstVertex, Vector3f secondVertex, Vector3f thirdVertex) {
        this.firstVertex = firstVertex;
        this.secondVertex = secondVertex;
        this.thirdVertex = thirdVertex;
    }

    public Vector3f getFirstVertex() {
        return firstVertex;
    }

    public Vector3f getSecondVertex() {
        return secondVertex;
    }

    public Vector3f getThirdVertex() {
        return thirdVertex;
    }
}
