package Toolbox;

import Entities.Camera;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Maths {

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

}
