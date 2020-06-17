package Collision;

import Entities.Ball;
import Toolbox.Maths;
import org.joml.Vector3f;

public class PreciseCollision {

    static Vector3f closestPoint;

    // for triangle sideA-sideB-sideC represented by face.getVertex return a point closestPoint in triangle that is closest to ball
    public static Vector3f closestPointInTriangle(Vector3f ball, Face face) {

        closestPoint = new Vector3f(0,0,0);

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
    public static boolean isOverlapping(Vector3f closestPoint, Ball ball){
        if(ball.getCollisionRadiusScaled() > closestPoint.distance(ball.getPosition())){
            return true;
        }
        return false;
    }
}
