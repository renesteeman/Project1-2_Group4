package Collision;

import Entities.Ball;
import Toolbox.Maths;
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

    // for triangle sideA-sideB-sideC represented by face.getVertex return a point closestPoint in triangle that is closest to ball
    public Vector3f closestPointInTriangle(Vector3f ball, Face face) {

        Vector3f closestPoint = new Vector3f(0,0,0);

        //Subtraction of different vectors
        Vector3f secondMinusFirst = Maths.minus(face.getSecondVertex(), face.getFirstVertex());
        Vector3f thirdMinusFirst = Maths.minus(face.getThirdVertex(), face.getFirstVertex());
        Vector3f ballMinusFirst = Maths.minus(ball, face.getFirstVertex());

        //Values for the multiplication of different vectors
        float secondMinusFirstTimesBallMinusFirst = Maths.dotMultiplication(secondMinusFirst, ballMinusFirst);
        float thirdMinusFirstTimesBallMinusFirst = Maths.dotMultiplication(thirdMinusFirst, ballMinusFirst);

        if (secondMinusFirstTimesBallMinusFirst <= 0.0f && thirdMinusFirstTimesBallMinusFirst <= 0.0f) {
            //return face.getFirstVertex();
            closestPoint = face.getFirstVertex();
            return closestPoint;
        }

        Vector3f ballMinusSecond = Maths.minus(ball, face.getSecondVertex());

        float secondMinusFirstTimesBallMinusSecond = Maths.dotMultiplication(secondMinusFirst, ballMinusSecond);
        float thirdMinusFirstTimesBallMinusSecond = Maths.dotMultiplication(thirdMinusFirst, ballMinusSecond);

        //TODO what does this if check?
        if (secondMinusFirstTimesBallMinusSecond >= 0.0f && thirdMinusFirstTimesBallMinusSecond <= secondMinusFirstTimesBallMinusSecond) {
            //return face.getSecondVertex();
            closestPoint = face.getSecondVertex();
            return closestPoint;
        }

        //Result of ( (B-A)*(Ball-A)*(C-A)*(Ball-B)) - ( (B-A)*(Ball-B)*(C-A)*(Ball-A) )
        float vc = secondMinusFirstTimesBallMinusFirst * thirdMinusFirstTimesBallMinusSecond - secondMinusFirstTimesBallMinusSecond * thirdMinusFirstTimesBallMinusFirst;

        if (vc <= 0.0f && secondMinusFirstTimesBallMinusFirst >= 0.0f && secondMinusFirstTimesBallMinusSecond <= 0.0f) {
            //(B-A)*(Ball-A) / ((B-A)*(Ball-A)-(B-A)*(Ball-B))
            float v = secondMinusFirstTimesBallMinusFirst / (secondMinusFirstTimesBallMinusFirst - secondMinusFirstTimesBallMinusSecond);

            //return Vector3f.add(face.getFirstVertex(), sideBMinusSideA.dot(v));
            closestPoint = face.getFirstVertex().add(Maths.multiply(secondMinusFirst, v));
            return closestPoint;
        }

        Vector3f ballMinusThird = Maths.minus(ball, face.getThirdVertex());

        float secondMinusFirstTimesBallMinusThird = Maths.dotMultiplication(secondMinusFirst, ballMinusThird);
        float thirdMinusFirstTimesBallMinusThird = Maths.dotMultiplication(thirdMinusFirst,ballMinusThird);

        if (thirdMinusFirstTimesBallMinusThird >= 0.0f && secondMinusFirstTimesBallMinusThird <= thirdMinusFirstTimesBallMinusThird) {
            //return face.getThirdVertex();
            closestPoint = face.getThirdVertex();
            return closestPoint;
        }

        //Result of ( (B-A)*(Ball-C)*(C-A)*(Ball-A)) - ( (B-A)*(Ball-A)*(C-A)*(Ball-C) )
        float vb = secondMinusFirstTimesBallMinusThird * thirdMinusFirstTimesBallMinusFirst - secondMinusFirstTimesBallMinusFirst * thirdMinusFirstTimesBallMinusThird;

        if (vb <= 0.0f && thirdMinusFirstTimesBallMinusFirst >= 0.0f && thirdMinusFirstTimesBallMinusThird <= 0.0f) {

            //Result of ((C-A)*(Ball-A)) /  ((C-A)*(Ball-A)-(C-A)*(Ball-C))
            float v = thirdMinusFirstTimesBallMinusFirst / (thirdMinusFirstTimesBallMinusFirst - thirdMinusFirstTimesBallMinusThird);

            // sideA + (ac * v)
            //return Vector3f.add(face.getFirstVertex(), sideCMinusSideA.dot(v));
            closestPoint = face.getFirstVertex().add(Maths.multiply(thirdMinusFirst, v));
            return closestPoint;
        }

        //Result of ( (B-A)*(Ball-B)*(C-A)*(Ball-C)) - ( (B-A)*(Ball-C)*(C-A)*(Ball-B) )
        float va = secondMinusFirstTimesBallMinusSecond * thirdMinusFirstTimesBallMinusThird - secondMinusFirstTimesBallMinusThird * thirdMinusFirstTimesBallMinusSecond;

        if (va <= 0.0f && (thirdMinusFirstTimesBallMinusSecond - secondMinusFirstTimesBallMinusSecond) >= 0.0f && (secondMinusFirstTimesBallMinusThird - thirdMinusFirstTimesBallMinusThird) >= 0.0f) {
            //Result of ((C-A)*(Ball-B)-(B-A)*(Ball-B)) / ( ((C-A)*(Ball-B)-(B-A)*(Ball-B)) + ((B-A)*(Ball-C)-(C-A)*(Ball-C)) )
            float v = (thirdMinusFirstTimesBallMinusSecond - secondMinusFirstTimesBallMinusSecond) / ((thirdMinusFirstTimesBallMinusSecond - secondMinusFirstTimesBallMinusSecond) + (secondMinusFirstTimesBallMinusThird - thirdMinusFirstTimesBallMinusThird));

            // sideB + v * (sideC - sideB)
            //return Vector3f.add(face.getSecondVertex(), v.dot((face.getThirdVertex().sub(face.getSecondVertex()))));
            closestPoint = face.getSecondVertex().add(Maths.minus(face.getThirdVertex(), Maths.multiply(face.getSecondVertex(), v)));
            return closestPoint;
        }

        float denominator = 1.0f / (va + vb + vc);
        float normalVector = vb * denominator; //Normal vector used to be called vn
        float wn = vc * denominator; //Don't know how to rename this one

        // sideA + sideAMinusSideB * normalVector + ac * wn
        Vector3f secondMinusFirstTimesNormalVector = Maths.multiply(secondMinusFirst, normalVector);
        Vector3f thirdMinusFirstTimesWN = Maths.multiply(thirdMinusFirst, wn);

        //return Vector3f.add(face.getFirstVertex(), Vector3f.add(secondMinusFirstTimesNormalVector, thirdMinusFirstTimesWN));
        secondMinusFirstTimesNormalVector.add(thirdMinusFirstTimesWN);
        closestPoint = face.getFirstVertex().add(secondMinusFirstTimesNormalVector);

       return closestPoint;
    }

    //Return if distance(closestPoint, ballLocation) < ballCollisionRadius
    public boolean isOverlapping(Vector3f closestPoint, Ball ball){
        if(ball.getCollisionRadiusScaled() > closestPoint.distance(ball.getPosition())){
            return true;
        }
        return false;
    }
}
