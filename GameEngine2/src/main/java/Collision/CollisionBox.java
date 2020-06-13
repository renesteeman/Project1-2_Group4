package Collision;

import org.joml.Vector3f;

public class CollisionBox {
    private float left;
    private float front;
    private float back;
    private float right;
    private float bottom;
    private float top;

    public CollisionBox(float left, float front, float back, float right, float bottom, float top) {
        this.left = left;
        this.front = front;
        this.back = back;
        this.right = right;
        this.bottom = bottom;
        this.top = top;
    }

    //TODO make sure this works
    public boolean overlapsWithPointGivenMargin(Vector3f collisionBoxTranslation, Vector3f point, float margin){
        float leftTranslated = left + collisionBoxTranslation.x - margin;
        float rightTranslated = right + collisionBoxTranslation.x + margin;
        float backTranslated = back + collisionBoxTranslation.y - margin;
        float frontTranslated = front + collisionBoxTranslation.y + margin;
        float bottomTranslated = bottom + collisionBoxTranslation.z - margin;
        float topTranslated = top + collisionBoxTranslation.z + margin;

        if(point.x > leftTranslated && point.x < rightTranslated && point.z < topTranslated && point.z > bottomTranslated && point.y < backTranslated && point.y > frontTranslated){
            return true;
        } else {
            return false;
        }
    }

    public float getLeft() {
        return left;
    }

    public void setLeft(float left) {
        this.left = left;
    }

    public float getFront() {
        return front;
    }

    public void setFront(float front) {
        this.front = front;
    }

    public float getBack() {
        return back;
    }

    public void setBack(float back) {
        this.back = back;
    }

    public float getRight() {
        return right;
    }

    public void setRight(float right) {
        this.right = right;
    }

    public float getBottom() {
        return bottom;
    }

    public void setBottom(float bottom) {
        this.bottom = bottom;
    }

    public float getTop() {
        return top;
    }

    public void setTop(float top) {
        this.top = top;
    }


    // for triangle sideA-sideB-sideC return a point q in triangle that is closest to ball
    public static Vector3f closestPtPointTriangle(Vector3f ball, Vector3f sideA, Vector3f sideB, Vector3f sideC) {

        //Subtraction of different vectors
        Vector3f sideBMinusSideA = Vector3f.subtract(sideB, sideA);
        Vector3f sideCMinusSideA = Vector3f.subtract(sideC, sideA);
        Vector3f ballMinusSideA = Vector3f.subtract(ball, sideA);

        //Multiplication of different vectors
        float sideBMinusSideATimesBallMinusSideA = Vector3f.Dot(sideBMinusSideA, ballMinusSideA);
        float sideCMinusSideATimesBallMinusSideA = Vector3f.Dot(sideCMinusSideA, ballMinusSideA);

        if (sideBMinusSideATimesBallMinusSideA <= 0.0f && sideCMinusSideATimesBallMinusSideA <= 0.0f) {
            return sideA;
        }

        //Subtraction of vectors
        Vector3f ballMinusSideB = Vector3f.subtract(ball, sideB);

        //Multiplication of vectors
        float sideBMinusSideATimesBallMinusSideB = Vector3f.Dot(sideBMinusSideA, ballMinusSideB);
        float sideCMinusSideATimesBallMinusSideB = Vector3f.Dot(sideCMinusSideA, ballMinusSideB);

        if (sideBMinusSideATimesBallMinusSideB >= 0.0f && sideCMinusSideATimesBallMinusSideB <= sideBMinusSideATimesBallMinusSideB) {
            return sideB;
        }

        //Result of ( (B-A)*(Ball-A)*(C-A)*(Ball-B)) - ( (B-A)*(Ball-B)*(C-A)*(Ball-A) )
        float vectorC = sideBMinusSideATimesBallMinusSideA * sideCMinusSideATimesBallMinusSideB - sideBMinusSideATimesBallMinusSideB * sideCMinusSideATimesBallMinusSideA;

        if (vectorC <= 0.0f && sideBMinusSideATimesBallMinusSideA >= 0.0f && sideBMinusSideATimesBallMinusSideB <= 0.0f) {
            float v = sideBMinusSideATimesBallMinusSideA / (sideBMinusSideATimesBallMinusSideA - sideBMinusSideATimesBallMinusSideB);

            return Vector3f.add(sideA, Vector3f.multiply(sideBMinusSideA, v));
        }

        //Subtraction of vectors
        Vector3f ballMinusSideC = Vector3f.subtract(ball, sideC);

        //Multiplication of vectors
        float sideBMinusSideATimesBallMinusSideC = Vector3f.Dot(sideBMinusSideA, ballMinusSideC);
        float sideCMinusSideATimesBallMinusSideC = Vector3f.Dot(sideCMinusSideA, ballMinusSideC);

        if (sideCMinusSideATimesBallMinusSideC >= 0.0f && sideBMinusSideATimesBallMinusSideC <= sideCMinusSideATimesBallMinusSideC) {
            return sideC;
        }

        //Result of ( (B-A)*(Ball-C)*(C-A)*(Ball-A)) - ( (B-A)*(Ball-A)*(C-A)*(Ball-C) )
        float vectorB = sideBMinusSideATimesBallMinusSideC * sideCMinusSideATimesBallMinusSideA - sideBMinusSideATimesBallMinusSideA * sideCMinusSideATimesBallMinusSideC;

        if (vectorB <= 0.0f && sideCMinusSideATimesBallMinusSideA >= 0.0f && sideCMinusSideATimesBallMinusSideC <= 0.0f) {

            //Result of ((C-A)*(Ball-A)) /  ((C-A)*(Ball-A)-(C-A)*(Ball-C))
            float newResult = sideCMinusSideATimesBallMinusSideA / (sideCMinusSideATimesBallMinusSideA - sideCMinusSideATimesBallMinusSideC);

            // sideA + (ac * newResult)
            return Vector3f.add(sideA, Vector3f.multiply(sideCMinusSideA, newResult));
        }

        //Result of ( (B-A)*(Ball-B)*(C-A)*(Ball-C)) - ( (B-A)*(Ball-C)*(C-A)*(Ball-B) )
        float vectorA = sideBMinusSideATimesBallMinusSideB * sideCMinusSideATimesBallMinusSideC - sideBMinusSideATimesBallMinusSideC * sideCMinusSideATimesBallMinusSideB;

        if (vectorA <= 0.0f && (sideCMinusSideATimesBallMinusSideB - sideBMinusSideATimesBallMinusSideB) >= 0.0f && (sideBMinusSideATimesBallMinusSideC - sideCMinusSideATimesBallMinusSideC) >= 0.0f) {

            //Result of ((C-A)*(Ball-B)-(B-A)*(Ball-B)) / ( ((C-A)*(Ball-B)-(B-A)*(Ball-B)) + ((B-A)*(Ball-C)-(C-A)*(Ball-C)) )
            float newResult = (sideCMinusSideATimesBallMinusSideB - sideBMinusSideATimesBallMinusSideB) / ((sideCMinusSideATimesBallMinusSideB - sideBMinusSideATimesBallMinusSideB) + (sideBMinusSideATimesBallMinusSideC - sideCMinusSideATimesBallMinusSideC));

            // sideB + newResult * (sideC - sideB)
            return Vector3f.add(sideB, Vector3f.multiply(Vector3f.subtract(sideC, sideB), newResult));
        }

        float denominator = 1.0f / (vectorA + vectorB + vectorC);
        float normalVector = vectorB * denominator; //Normal vector used to be called vn
        float wn = vectorC * denominator; //Don't know how to rename this one

        // sideA + sideAminusSideB * normalVector + ac * wn
        Vector3 sideBMinusSideATimesNormalVector = Vector3f.multiply(sideBMinusSideA, normalVector);
        Vector3 sideCMinusSideATimesWN = Vector3f.multiply(sideCMinusSideA, wn);

        // return result
        return Vector3f.add(sideA, Vector3f.add(sideBMinusSideATimesNormalVector, sideCMinusSideATimesWN));
    }
}
