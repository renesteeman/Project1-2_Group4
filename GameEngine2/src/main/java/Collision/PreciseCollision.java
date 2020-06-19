package Collision;

import Entities.Ball;
import Toolbox.Maths;
import org.joml.Vector3f;

public class PreciseCollision {

    //This is very heavily inspired by Real-Time Collision Detection by Christer Ericson (page 139)
    public static Vector3f closestPointTriangle(Face face, Vector3f ball, Vector3f entityOffset){
        Face offsettenFace = new Face(Maths.plus(face.getFirstVertex(), entityOffset), Maths.plus(face.getSecondVertex(), entityOffset), Maths.plus(face.getThirdVertex(), entityOffset));

        Vector3f ab = Maths.minus(offsettenFace.getSecondVertex(), offsettenFace.getFirstVertex());
        Vector3f ac = Maths.minus(offsettenFace.getThirdVertex(), offsettenFace.getFirstVertex());
        Vector3f bc = Maths.minus(offsettenFace.getThirdVertex(), offsettenFace.getSecondVertex());

        //Compute parametric position s for projection P' of P on AB (P is the ball)
        // P' = A + s*AB, s = snom/(snom/sdenom)
        float snom = Maths.dotMultiplication(Maths.minus(ball, offsettenFace.getFirstVertex()), ab);
        float sdenom = Maths.dotMultiplication(Maths.minus(ball, offsettenFace.getFirstVertex()), Maths.minus(offsettenFace.getFirstVertex(), offsettenFace.getSecondVertex()));

        //Compute parametric position t for projection P' of p on AC
        //P' = A + t*AC, s = tnom / (tnom+tdenom)
        float tnom = Maths.dotMultiplication(Maths.minus(ball, offsettenFace.getFirstVertex()), ac);
        float tdenom =  Maths.dotMultiplication(Maths.minus(ball, offsettenFace.getThirdVertex()), Maths.minus(offsettenFace.getFirstVertex(), offsettenFace.getThirdVertex()));

        if(snom <= 0.0f && tnom <= 0.0f){
            return offsettenFace.getFirstVertex();
            //Vertex region early out
        }

        //Compute parametric position u for projection p' of p on BC
        //P' = B + u*BC, u = unom/(unom+udenom)
        float unom = Maths.dotMultiplication(Maths.minus(ball, offsettenFace.getSecondVertex()), bc);
        float undenom = Maths.dotMultiplication(Maths.minus(ball, offsettenFace.getThirdVertex()), Maths.minus(offsettenFace.getSecondVertex(), offsettenFace.getThirdVertex()));

        if(sdenom <= 0.0f && unom <= 0.0f)
            return offsettenFace.getSecondVertex();
        if(tdenom <= 0.0f && undenom <= 0.0f)
            return offsettenFace.getThirdVertex();

        // P is outside (or on) AB if the triples scalar product [N PA PB] <= 0
        Vector3f n = Maths.crossProduct(Maths.minus(offsettenFace.getSecondVertex(), offsettenFace.getFirstVertex()), Maths.minus(offsettenFace.getThirdVertex(), offsettenFace.getFirstVertex()));
        float vc = Maths.dotMultiplication(n, Maths.crossProduct(Maths.minus(offsettenFace.getFirstVertex(), ball), Maths.minus(offsettenFace.getSecondVertex(), ball)));

        //If P is outside AB and within feature region of AB return projection of P onto AB
        if(vc <= 0.0f && snom >= 0.0f && sdenom >= 0.0f) {
            return Maths.plus(offsettenFace.getFirstVertex(), Maths.multiply( ab,snom / (snom + sdenom)));
        }

        //P is outside (or on) BC if the triple scalar product [N PB PC <=0
        float va = Maths.dotMultiplication(n, Maths.crossProduct(Maths.minus(offsettenFace.getSecondVertex(), ball), Maths.minus(offsettenFace.getThirdVertex(), ball)));
        //If P is outside BC and within feature region of BC return projection of P onto BC
        if(va<=0.0f && unom >= 0.0f && undenom >= 0.0f){
            return Maths.plus(offsettenFace.getSecondVertex() ,Maths.multiply( bc,unom/(unom + undenom)));
        }

        //P is outside (or on) CA if the triple scalar product [N PC PA <=0
        float vb = Maths.dotMultiplication(n, Maths.crossProduct(Maths.minus(offsettenFace.getThirdVertex(), ball), Maths.minus(offsettenFace.getFirstVertex(), ball)));
        //If P is outside CA and within feature region of CA return projection of P onto CA
        if(vb <=0.0f && tnom >= 0.0f && tdenom >= 0.0f){
            return Maths.plus(offsettenFace.getFirstVertex() , Maths.multiply(ac, tnom/(tnom + tdenom)));
        }

        //P must project inside offsettenFace region. Compute Q using barycentric coordinates
        float u = va / (va + vb + vc);
        float v = vb / (va + vb + vc);
        float w = 1.0f - u - v; // = vc / (va + vb + vc)

        // had to add 3 multiplication of vectors so used a temp variable since we can only do two by two
        Vector3f temp = Maths.plus(Maths.multiply(offsettenFace.getFirstVertex(), u), Maths.multiply(offsettenFace.getSecondVertex(), v));
        return Maths.plus(temp, Maths.multiply(offsettenFace.getThirdVertex(), w));
    }

    //Return if distance(closestPoint, ballLocation) < ballCollisionRadius
    public static boolean isOverlapping(Vector3f closestPoint, Ball ball){
        return closestPoint.distance(ball.getPosition()) < ball.getCollisionRadiusScaled();
    }

    //Based on https://www.khronos.org/opengl/wiki/Calculating_a_Surface_Normal
    public static Vector3f getNormalFromFace(Face face){
        Vector3f U = Maths.minus(face.getSecondVertex(), face.getFirstVertex());
        Vector3f V = Maths.minus(face.getThirdVertex(), face.getFirstVertex());

        Vector3f normal = new Vector3f();
        normal.x = U.y * V.z - U.z * V.y;
        normal.y = U.z * V.x - U.x * V.z;
        normal.z = U.x * V.y - U.y * V.x;

        return normal;
    }
}
