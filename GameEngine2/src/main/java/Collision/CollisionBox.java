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
    public Vector3f closestPointInTriangle(Vector3f ball, Face face) {

        Vector3f result = new Vector3f(0,0,0);

        //Subtraction of different vectors
        Vector3f secondMinusFirst = face.getSecondVertex().sub(face.getFirstVertex());
        Vector3f thirdMinusFirst = face.getThirdVertex().sub(face.getFirstVertex());
        Vector3f ballMinusFirst = ball.sub(face.getFirstVertex());

        //Values for the multiplication of different vectors
        float secondMinusFirstTimesBallMinusFirst = secondMinusFirst.dot(ballMinusFirst);
        float thirdMinusFirstTimesBallMinusFirst = thirdMinusFirst.dot(ballMinusFirst);

        if (secondMinusFirstTimesBallMinusFirst <= 0.0f && thirdMinusFirstTimesBallMinusFirst <= 0.0f) {
            //return face.getFirstVertex();
            result = face.getFirstVertex();
            return result;
        }

        //Subtraction of vectors
        Vector3f ballMinusSideB = ball.sub(face.getSecondVertex());

        //Values for the multiplication of vectors
        float secondMinusFirstTimesBallMinusSecond = secondMinusFirst.dot(ballMinusSideB);
        float thirdMinusFirstTimesBallMinusSecond = thirdMinusFirst.dot(ballMinusSideB);

        //TODO what does this if check?
        if (secondMinusFirstTimesBallMinusSecond >= 0.0f && thirdMinusFirstTimesBallMinusSecond <= secondMinusFirstTimesBallMinusSecond) {
            //return face.getSecondVertex();
            result = face.getSecondVertex();
            return result;
        }

        //Result of ( (B-A)*(Ball-A)*(C-A)*(Ball-B)) - ( (B-A)*(Ball-B)*(C-A)*(Ball-A) )
        float vc = secondMinusFirstTimesBallMinusFirst * thirdMinusFirstTimesBallMinusSecond - secondMinusFirstTimesBallMinusSecond * thirdMinusFirstTimesBallMinusFirst;

        if (vc <= 0.0f && secondMinusFirstTimesBallMinusFirst >= 0.0f && secondMinusFirstTimesBallMinusSecond <= 0.0f) {
            //(B-A)*(Ball-A) / ((B-A)*(Ball-A)-(B-A)*(Ball-B))
            float v = secondMinusFirstTimesBallMinusFirst / (secondMinusFirstTimesBallMinusFirst - secondMinusFirstTimesBallMinusSecond);

            //TODO
            /*
            Vector3f vec1 = new Vector3f();
            Vector3f vec2 = new Vector3f();
            vec1.add(vec2);
            */

            //TODO
            //return Vector3f.add(face.getFirstVertex(), sideBMinusSideA.dot(v));
            result = face.getFirstVertex().add(sideBMinusSideA.dot(v));
            return result;
        }

        //Subtraction of vectors
        Vector3f ballMinusThird = ball.sub(face.getThirdVertex());

        //Multiplication of vectors
        float secondMinusFirstTimesBallMinusThird = secondMinusFirst.dot(ballMinusThird);
        float thirdMinusFirstTimesBallMinusThird = thirdMinusFirst.dot(ballMinusThird);

        if (thirdMinusFirstTimesBallMinusThird >= 0.0f && secondMinusFirstTimesBallMinusThird <= thirdMinusFirstTimesBallMinusThird) {
            //return face.getThirdVertex();
            result = face.getThirdVertex();
            return result;
        }

        //Result of ( (B-A)*(Ball-C)*(C-A)*(Ball-A)) - ( (B-A)*(Ball-A)*(C-A)*(Ball-C) )
        float vb = secondMinusFirstTimesBallMinusThird * thirdMinusFirstTimesBallMinusFirst - secondMinusFirstTimesBallMinusFirst * thirdMinusFirstTimesBallMinusThird;

        if (vb <= 0.0f && thirdMinusFirstTimesBallMinusFirst >= 0.0f && thirdMinusFirstTimesBallMinusThird <= 0.0f) {

            //Result of ((C-A)*(Ball-A)) /  ((C-A)*(Ball-A)-(C-A)*(Ball-C))
            float v = thirdMinusFirstTimesBallMinusFirst / (thirdMinusFirstTimesBallMinusFirst - thirdMinusFirstTimesBallMinusThird);

            // sideA + (ac * v)
            //TODO
            //return Vector3f.add(face.getFirstVertex(), sideCMinusSideA.dot(v));
            result = face.getFirstVertex().add(sideCMinusSideA.dot(v));
            return result;
        }

        //Result of ( (B-A)*(Ball-B)*(C-A)*(Ball-C)) - ( (B-A)*(Ball-C)*(C-A)*(Ball-B) )
        float va = secondMinusFirstTimesBallMinusSecond * thirdMinusFirstTimesBallMinusThird - secondMinusFirstTimesBallMinusThird * thirdMinusFirstTimesBallMinusSecond;

        if (va <= 0.0f && (thirdMinusFirstTimesBallMinusSecond - secondMinusFirstTimesBallMinusSecond) >= 0.0f && (secondMinusFirstTimesBallMinusThird - thirdMinusFirstTimesBallMinusThird) >= 0.0f) {

            //Result of ((C-A)*(Ball-B)-(B-A)*(Ball-B)) / ( ((C-A)*(Ball-B)-(B-A)*(Ball-B)) + ((B-A)*(Ball-C)-(C-A)*(Ball-C)) )
            float newResult = (thirdMinusFirstTimesBallMinusSecond - secondMinusFirstTimesBallMinusSecond) / ((thirdMinusFirstTimesBallMinusSecond - secondMinusFirstTimesBallMinusSecond) + (secondMinusFirstTimesBallMinusThird - thirdMinusFirstTimesBallMinusThird));

            // sideB + newResult * (sideC - sideB)
            //TODO
            //return Vector3f.add(face.getSecondVertex(), newResult.dot((face.getThirdVertex().sub(face.getSecondVertex()))));
            result = face.getSecondVertex().add(newResult.dot((face.getThirdVertex().sub(face.getSecondVertex()))));
            return result;
        }

        float denominator = 1.0f / (va + vb + vc);
        float normalVector = vb * denominator; //Normal vector used to be called vn
        float wn = vc * denominator; //Don't know how to rename this one

        // sideA + sideAMinusSideB * normalVector + ac * wn
        Vector3f secondMinusFirstTimesNormalVector = secondMinusFirst.dot(normalVector);
        Vector3f thirdMinusFirstTimesWN = thirdMinusFirst.dot(wn);

        // return result
        //TODO
        //return Vector3f.add(face.getFirstVertex(), Vector3f.add(secondMinusFirstTimesNormalVector, thirdMinusFirstTimesWN));

        secondMinusFirstTimesNormalVector.add(thirdMinusFirstTimesWN);

        result = face.getFirstVertex().add(secondMinusFirstTimesNormalVector);

       return result;
    }

    //Return if distance(closestPoint, ballLocation) < ballCollisionRadius
    public boolean isOverlapping(Vector3f result, Ball ball){

        if(){
            return true;
        }

        return false;
    }
}
