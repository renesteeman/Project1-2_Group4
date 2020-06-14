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

    //TODO create a new method that returns a boolean telling you if the face is overlapping
    // (or really close) and use Face instead of sideA, B, C
    //Return if distance(closestPoint, ballLocation) < ballCollisionRadius

    // for triangle sideA-sideB-sideC return a point q in triangle that is closest to ball
    public boolean closestPtPointTriangle(Vector3f ball, Face face) {

        //Subtraction of different vectors
        //TODO change to right syntax
        Vector3f secondMinusFirst = face.getSecondVertex().sub(face.getFirstVertex());
        Vector3f thirdMinusFirst = face.getThirdVertex().sub(face.getFirstVertex());
        Vector3f ballMinusFirst = ball.sub(face.getFirstVertex());

        //Multiplication of different vectors
        float secondMinusFirstTimesBallMinusFirst = secondMinusFirst.dot(ballMinusFirst);
        float thirdMinusFirstTimesBallMinusFirst = thirdMinusFirst.dot(ballMinusFirst);

        if (secondMinusFirstTimesBallMinusFirst <= 0.0f && thirdMinusFirstTimesBallMinusFirst <= 0.0f) {
            //return face.getFirstVertex();
            return true;
        }

        //Subtraction of vectors
        Vector3f ballMinusSideB = ball.sub(face.getSecondVertex());

        //Multiplication of vectors
        float secondMinusFirstTimesBallMinusSecond = secondMinusFirst.dot(ballMinusSideB);
        float thirdMinusFirstTimesBallMinusSecond = thirdMinusFirst.dot(ballMinusSideB);

        //TODO what does this if check?
        if (secondMinusFirstTimesBallMinusSecond >= 0.0f && thirdMinusFirstTimesBallMinusSecond <= secondMinusFirstTimesBallMinusSecond) {
            //return face.getSecondVertex();
            return true;
        }

        //Result of ( (B-A)*(Ball-A)*(C-A)*(Ball-B)) - ( (B-A)*(Ball-B)*(C-A)*(Ball-A) )
        float thirdVector = secondMinusFirstTimesBallMinusFirst * thirdMinusFirstTimesBallMinusSecond - secondMinusFirstTimesBallMinusSecond * thirdMinusFirstTimesBallMinusFirst;

        if (thirdVector <= 0.0f && secondMinusFirstTimesBallMinusFirst >= 0.0f && secondMinusFirstTimesBallMinusSecond <= 0.0f) {
            float v = secondMinusFirstTimesBallMinusFirst / (secondMinusFirstTimesBallMinusFirst - secondMinusFirstTimesBallMinusSecond);

            //TODO
            return Vector3f.add(face.getFirstVertex(), Vector3f.multiply(sideBMinusSideA, v));
        }

        //Subtraction of vectors
        Vector3f ballMinusThird = ball.sub(face.getThirdVertex());

        //Multiplication of vectors
        float secondMinusFirstTimesBallMinusThird = secondMinusFirst.dot(ballMinusThird);
        float thirdMinusFirstTimesBallMinusThird = thirdMinusFirst.dot(ballMinusThird);

        if (thirdMinusFirstTimesBallMinusThird >= 0.0f && secondMinusFirstTimesBallMinusThird <= thirdMinusFirstTimesBallMinusThird) {
            //return face.getThirdVertex();
            return true;
        }

        //Result of ( (B-A)*(Ball-C)*(C-A)*(Ball-A)) - ( (B-A)*(Ball-A)*(C-A)*(Ball-C) )
        float secondVector = secondMinusFirstTimesBallMinusThird * thirdMinusFirstTimesBallMinusFirst - secondMinusFirstTimesBallMinusFirst * thirdMinusFirstTimesBallMinusThird;

        if (secondVector <= 0.0f && thirdMinusFirstTimesBallMinusFirst >= 0.0f && thirdMinusFirstTimesBallMinusThird <= 0.0f) {

            //Result of ((C-A)*(Ball-A)) /  ((C-A)*(Ball-A)-(C-A)*(Ball-C))
            float newResult = thirdMinusFirstTimesBallMinusFirst / (thirdMinusFirstTimesBallMinusFirst - thirdMinusFirstTimesBallMinusThird);

            // sideA + (ac * newResult)
            //TODO
            return Vector3f.add(face.getFirstVertex(), Vector3f.multiply(sideCMinusSideA, newResult));
        }

        //Result of ( (B-A)*(Ball-B)*(C-A)*(Ball-C)) - ( (B-A)*(Ball-C)*(C-A)*(Ball-B) )
        float firstVector = secondMinusFirstTimesBallMinusSecond * thirdMinusFirstTimesBallMinusThird - secondMinusFirstTimesBallMinusThird * thirdMinusFirstTimesBallMinusSecond;

        if (firstVector <= 0.0f && (thirdMinusFirstTimesBallMinusSecond - secondMinusFirstTimesBallMinusSecond) >= 0.0f && (secondMinusFirstTimesBallMinusThird - thirdMinusFirstTimesBallMinusThird) >= 0.0f) {

            //Result of ((C-A)*(Ball-B)-(B-A)*(Ball-B)) / ( ((C-A)*(Ball-B)-(B-A)*(Ball-B)) + ((B-A)*(Ball-C)-(C-A)*(Ball-C)) )
            float newResult = (thirdMinusFirstTimesBallMinusSecond - secondMinusFirstTimesBallMinusSecond) / ((thirdMinusFirstTimesBallMinusSecond - secondMinusFirstTimesBallMinusSecond) + (secondMinusFirstTimesBallMinusThird - thirdMinusFirstTimesBallMinusThird));

            // sideB + newResult * (sideC - sideB)
            //TODO
            return Vector3f.add(face.getSecondVertex(), Vector3f.multiply(Vector3f.subtract(face.getThirdVertex(), face.getSecondVertex()), newResult));
        }

        float denominator = 1.0f / (firstVector + secondVector + thirdVector);
        float normalVector = secondVector * denominator; //Normal vector used to be called vn
        float wn = thirdVector * denominator; //Don't know how to rename this one

        // sideA + sideAMinusSideB * normalVector + ac * wn
        Vector3f secondMinusFirstTimesNormalVector = secondMinusFirst.dot(normalVector);
        Vector3f thirdMinusFirstTimesWN = thirdMinusFirst.dot(wn);

        // return result
        return Vector3f.add(face.getFirstVertex(), Vector3f.add(secondMinusFirstTimesNormalVector, thirdMinusFirstTimesWN));
    }
}
