package Toolbox;

import Entities.Camera;
import RenderEngine.DisplayManager;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class Maths {

    //3D transformationMatrix
    public static Matrix4f createTransformationMatrix(Vector3f translation, float rx, float ry, float rz, float scale){
        Matrix4f transformationMatrix = new Matrix4f();
        transformationMatrix.translate(translation);
        transformationMatrix.rotate((float) Math.toRadians(rx), 1.0f, 0.0f, 0.0f);
        transformationMatrix.rotate((float) Math.toRadians(ry), 0.0f, 1.0f, 0.0f);
        transformationMatrix.rotate((float) Math.toRadians(rz), 0.0f, 0.0f, 1.0f);
        transformationMatrix.scale(scale);

        return transformationMatrix;
    }

    public static Matrix4f createViewMatrix(Camera camera){
        Matrix4f viewMatrix = new Matrix4f();
        viewMatrix.rotate((float) Math.toRadians(camera.getPitch()), 1, 0, 0);
        viewMatrix.rotate((float) Math.toRadians(camera.getYaw()),0, 1, 0);
        Vector3f cameraPos = camera.getPosition();
        Vector3f negativeCameraPos = new Vector3f(-cameraPos.x,-cameraPos.y,-cameraPos.z);
        viewMatrix.translate(negativeCameraPos);

        return viewMatrix;
    }

    public static float barryCentric(Vector3f p1, Vector3f p2, Vector3f p3, Vector2f pos) {
        float det = (p2.z - p3.z) * (p1.x - p3.x) + (p3.x - p2.x) * (p1.z - p3.z);
        float l1 = ((p2.z - p3.z) * (pos.x - p3.x) + (p3.x - p2.x) * (pos.y - p3.z)) / det;
        float l2 = ((p3.z - p1.z) * (pos.x - p3.x) + (p1.x - p3.x) * (pos.y - p3.z)) / det;
        float l3 = 1.0f - l1 - l2;
        return l1 * p1.y + l2 * p2.y + l3 * p3.y;
    }

    //2D transformationMatrix
    public static Matrix4f createTransformationMatrix(Vector2f translation, Vector2f scale) {
        Matrix4f transformationMatrix = new Matrix4f();
        transformationMatrix.translate(new Vector3f(translation.x, translation.y,  1f));
        transformationMatrix.scale(new Vector3f(scale.x, scale.y, 1f));

        return transformationMatrix;
    }

    public static double screenCoordinateToPixelX(double screenCoordinate){
        return ((screenCoordinate+1)/2)*(DisplayManager.getWidth());
    }

<<<<<<< HEAD
    public static double getDistance(Vector3f pointOne, Vector3f pointTwo){
        return pointOne.distance(pointTwo);
    }

=======
    public static Vector3f minus(Vector3f a, Vector3f b){
        return new Vector3f(a.x - b.x, a.y-b.y, a.z-b.z);
    }

    public static Vector3f plus(Vector3f a, Vector3f b){
        return new Vector3f(a.x + b.x, a.y+b.y, a.z+b.z);
    }

    public static Vector3f multiply(Vector3f a, float b){
        return new Vector3f(a.x * b, a.y * b, a.z * b);
    }
>>>>>>> master
}
