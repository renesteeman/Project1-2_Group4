package Collision;

import Entities.*;
import Collision.CollisionBox;
import Physics.Vector3d;
import Toolbox.Maths;
import org.joml.Vector3f;

import java.util.ArrayList;

import static Collision.PreciseCollision.isOverlapping;
import static Collision.PreciseCollision.closestPointTriangle;

public class CheckCollision {

    private static final float margin = 0.2f;

    public static Vector3d checkForCollision(ArrayList<Tree> trees, Goal goal, Ball ball, Vector3f ballLocation){

        ArrayList<CollisionEntity> items = new ArrayList<>(trees);
        items.add(goal);

        for(CollisionEntity item : items){
            //System.out.println("Item position: " + item.getPosition().toString() + " Ball position: " + ball.getPosition().toString());

            if(item.getCollisionBox().overlapsWithPointGivenMargin(item.getPosition(), ballLocation, margin)){
                for(Face face : item.getCollisionModel().getFaces()){
                    Vector3f result = PreciseCollision.closestPointTriangle(face, ballLocation, item.getPosition());
                    if(PreciseCollision.isOverlapping(result, ball)){
                        System.out.println("The collision is happening");
                        Vector3f normal = PreciseCollision.getNormalFromFace(face);
                        System.out.println("Normal: " + normal.toString());
                        return new Vector3d(normal);
                    }
                }
            }
        }

        return null;
    }

}
