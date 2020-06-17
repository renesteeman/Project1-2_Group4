package Collision;

import Entities.Ball;
import Toolbox.Maths;
import org.joml.Vector3f;

public class PreciseCollision {

    static Vector3f closestPoint;

    /**
     * Old Method
     *
    // for triangle sideA-sideB-sideC represented by face.getVertex return a point closestPoint in triangle that is closest to ball
    public static Vector3f closestPointInTriangle(Vector3f ball, Face face) {
        System.out.println("Face first: " + face.getFirstVertex().toString());
        System.out.println("Face second: " + face.getSecondVertex().toString());
        System.out.println("Face third: " + face.getThirdVertex().toString());

        //Subtraction of different vectors
        Vector3f secondMinusFirst = Maths.minus(face.getSecondVertex(), face.getFirstVertex());
        Vector3f thirdMinusFirst = Maths.minus(face.getThirdVertex(), face.getFirstVertex());
        Vector3f ballMinusFirst = Maths.minus(ball, face.getFirstVertex());

        //Values for the multiplication of different vectors
        float secondMinusFirstTimesBallMinusFirst = Maths.dotMultiplication(secondMinusFirst, ballMinusFirst);
        float thirdMinusFirstTimesBallMinusFirst = Maths.dotMultiplication(thirdMinusFirst, ballMinusFirst);

        if (secondMinusFirstTimesBallMinusFirst <= 0.0f && thirdMinusFirstTimesBallMinusFirst <= 0.0f) {
            return face.getFirstVertex();
        }

        Vector3f ballMinusSecond = Maths.minus(ball, face.getSecondVertex());

        float secondMinusFirstTimesBallMinusSecond = Maths.dotMultiplication(secondMinusFirst, ballMinusSecond);
        float thirdMinusFirstTimesBallMinusSecond = Maths.dotMultiplication(thirdMinusFirst, ballMinusSecond);

        if (secondMinusFirstTimesBallMinusSecond >= 0.0f && thirdMinusFirstTimesBallMinusSecond <= secondMinusFirstTimesBallMinusSecond) {
            return face.getSecondVertex();
        }

        //Result of ( (B-A)*(Ball-A)*(C-A)*(Ball-B)) - ( (B-A)*(Ball-B)*(C-A)*(Ball-A) )
        float vc = secondMinusFirstTimesBallMinusFirst * thirdMinusFirstTimesBallMinusSecond - secondMinusFirstTimesBallMinusSecond * thirdMinusFirstTimesBallMinusFirst;

        //TODO improve explanation
        if (vc <= 0.0f && secondMinusFirstTimesBallMinusFirst >= 0.0f && secondMinusFirstTimesBallMinusSecond <= 0.0f) {
            //(B-A)*(Ball-A) / ((B-A)*(Ball-A)-(B-A)*(Ball-B))
            float v = secondMinusFirstTimesBallMinusFirst / (secondMinusFirstTimesBallMinusFirst - secondMinusFirstTimesBallMinusSecond);

            return face.getFirstVertex().add(Maths.multiply(secondMinusFirst, v));
        }

        Vector3f ballMinusThird = Maths.minus(ball, face.getThirdVertex());

        //TODO check from here
        float secondMinusFirstTimesBallMinusThird = Maths.dotMultiplication(secondMinusFirst, ballMinusThird);
        float thirdMinusFirstTimesBallMinusThird = Maths.dotMultiplication(thirdMinusFirst,ballMinusThird);

        if (thirdMinusFirstTimesBallMinusThird >= 0.0f && secondMinusFirstTimesBallMinusThird <= thirdMinusFirstTimesBallMinusThird) {
            return face.getThirdVertex();
        }

        //Result of ( (B-A)*(Ball-C)*(C-A)*(Ball-A)) - ( (B-A)*(Ball-A)*(C-A)*(Ball-C) )
        float vb = secondMinusFirstTimesBallMinusThird * thirdMinusFirstTimesBallMinusFirst - secondMinusFirstTimesBallMinusFirst * thirdMinusFirstTimesBallMinusThird;

        if (vb <= 0.0f && thirdMinusFirstTimesBallMinusFirst >= 0.0f && thirdMinusFirstTimesBallMinusThird <= 0.0f) {

            //Result of ((C-A)*(Ball-A)) /  ((C-A)*(Ball-A)-(C-A)*(Ball-C))
            float v = thirdMinusFirstTimesBallMinusFirst / (thirdMinusFirstTimesBallMinusFirst - thirdMinusFirstTimesBallMinusThird);

            // sideA + (ac * v)
            return face.getFirstVertex().add(Maths.multiply(thirdMinusFirst, v));
        }

        //Result of ( (B-A)*(Ball-B)*(C-A)*(Ball-C)) - ( (B-A)*(Ball-C)*(C-A)*(Ball-B) )
        float va = secondMinusFirstTimesBallMinusSecond * thirdMinusFirstTimesBallMinusThird - secondMinusFirstTimesBallMinusThird * thirdMinusFirstTimesBallMinusSecond;

        if (va <= 0.0f && (thirdMinusFirstTimesBallMinusSecond - secondMinusFirstTimesBallMinusSecond) >= 0.0f && (secondMinusFirstTimesBallMinusThird - thirdMinusFirstTimesBallMinusThird) >= 0.0f) {
            //Result of ((C-A)*(Ball-B)-(B-A)*(Ball-B)) / ( ((C-A)*(Ball-B)-(B-A)*(Ball-B)) + ((B-A)*(Ball-C)-(C-A)*(Ball-C)) )
            float v = (thirdMinusFirstTimesBallMinusSecond - secondMinusFirstTimesBallMinusSecond) / ((thirdMinusFirstTimesBallMinusSecond - secondMinusFirstTimesBallMinusSecond) + (secondMinusFirstTimesBallMinusThird - thirdMinusFirstTimesBallMinusThird));

            // sideB + v * (sideC - sideB)
            return face.getSecondVertex().add(Maths.minus(face.getThirdVertex(), Maths.multiply(face.getSecondVertex(), v)));
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
     */

    public Vector3f closestPointTriangle(Face face, Ball p){

        Vector3f ab = Maths.minus(face.getSecondVertex(), face.getFirstVertex());
        Vector3f ac = Maths.minus(face.getThirdVertex(), face.getFirstVertex());
        Vector3f bc = Maths.minus(face.getThirdVertex(), face.getSecondVertex());

        //Compute parametric position s for projection P' of P on AB (P is the ball)
        // P' = A + s*AB, s = snom/(snom/sdenom)
        float snom = Maths.dotMultiplication(Maths.minus(p.getPosition(), face.getFirstVertex()), ab);
        float sdenom = Maths.dotMultiplication(Maths.minus(p.getPosition(), face.getFirstVertex()), Maths.minus(face.getFirstVertex(), face.getSecondVertex()));

        //Compute parametric position t for projection P' of p on AC
        //P' = A + t*AC, s = tnom / (tnom+tdenom)
        float tnom = Maths.dotMultiplication(Maths.minus(p.getPosition(), face.getFirstVertex()), ac);
        float tdenom =  Maths.dotMultiplication(Maths.minus(p.getPosition(), face.getThirdVertex()), Maths.minus(face.getFirstVertex(), face.getThirdVertex()));

        if(snom <= 0.0f && tnom <= 0.0f){
            return face.getFirstVertex();
            //Vertex region early out
        }

        //Compute parametric position u for projection p' of p on BC
        //P' = B + u*BC, u = unom/(unom+udenom)
        float unom = Maths.dotMultiplication(Maths.minus(p.getPosition(), face.getSecondVertex()), bc);
        float undenom = Maths.dotMultiplication(Maths.minus(p.getPosition(), face.getThirdVertex()), Maths.minus(face.getSecondVertex(), face.getThirdVertex()));


        if(sdenom <= 0.0f && unom <= 0.0f)
            return face.getSecondVertex();
        if(tdenom <= 0.0f && undenom <= 0.0f)
            return face.getThirdVertex();


        // P is outside (or on) AB if the triples scalar product [N PA PB] <= 0
        Vector3f n = Maths.crossProduct(Maths.minus(face.getSecondVertex(), face.getFirstVertex()), Maths.minus(face.getThirdVertex(), face.getFirstVertex()));
        float vc = Maths.dotMultiplication(n, Maths.crossProduct(Maths.minus(face.getFirstVertex(), p.getPosition()), Maths.minus(face.getSecondVertex(), p.getPosition())));

        //If P is outside AB and within feature region of AB return projection of P onto AB
        if(vc <= 0.0f && snom >= 0.0f && sdenom >= 0.0f) {
            return Maths.plus(face.getFirstVertex(), Maths.multiply( ab,snom / (snom + sdenom)));
        }

        //P is outside (or on) BC if the triple scalar product [N PB PC <=0
        float va = Maths.dotMultiplication(n, Maths.crossProduct(Maths.minus(face.getSecondVertex(), p.getPosition()), Maths.minus(face.getThirdVertex(), p.getPosition())));
        //If P is outside BC and within feature region of BC return projection of P onto BC
        if(va<=0.0f && unom >= 0.0f && undenom >= 0.0f){
            return Maths.plus(face.getSecondVertex() ,Maths.multiply( bc,unom/(unom + undenom)));
        }

        //P is outside (or on) CA if the triple scalar product [N PC PA <=0
        float vb = Maths.dotMultiplication(n, Maths.crossProduct(Maths.minus(face.getThirdVertex(), p.getPosition()), Maths.minus(face.getFirstVertex(), p.getPosition())));
        //If P is outside CA and within feature region of CA return projection of P onto CA
        if(vb <=0.0f && tnom >= 0.0f && tdenom >= 0.0f){
            return Maths.plus(face.getFirstVertex() , Maths.multiply(ac, tnom/(tnom + tdenom)));
        }

        //P must project inside face region. Compute Q using barycentric coordinates
        float u = va / (va + vb + vc);
        float v = vb / (va + vb + vc);
        float w = 1.0f - u - v; // = vc / (va + vb + vc)

        // had to add 3 multiplication of vectors so used a temp variable since we can only do two by two

        Vector3f temp = Maths.plus(Maths.multiply(face.getFirstVertex(), u), Maths.multiply(face.getSecondVertex(), v));
        return Maths.plus(temp, Maths.multiply(face.getThirdVertex(), w));


    }

    //Return if distance(closestPoint, ballLocation) < ballCollisionRadius
    public static boolean isOverlapping(Vector3f closestPoint, Ball ball){
        System.out.println("Distance: " + closestPoint.distance(ball.getPosition()));
        return closestPoint.distance(ball.getPosition()) < ball.getCollisionRadiusScaled();
    }
}
