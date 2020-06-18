package Collision;

import Entities.*;
import Collision.CollisionBox;
import org.joml.Vector3f;

import java.util.ArrayList;

import static Collision.PreciseCollision.isOverlapping;
import static Collision.PreciseCollision.closestPointTriangle;

public class CheckCollision {

    private static final float margin = 0.2f;

    public static void checkForCollision(ArrayList<Tree> trees, Goal goal, Ball ball){

        ArrayList<CollisionEntity> items = new ArrayList<>(trees);
        items.add(goal);

        for(CollisionEntity item : items){
            //System.out.println("Item position: " + item.getPosition().toString() + " Ball position: " + ball.getPosition().toString());

            if(item.getCollisionBox().overlapsWithPointGivenMargin(item.getPosition(), ball.getPosition(), margin)){
                for(Face face : item.getCollisionModel().getFaces()){
                    Vector3f result = closestPointTriangle(face, ball.getPosition(), item.getPosition());
                    System.out.println("result: " + result.toString());
                    if(isOverlapping(result, ball)){
                        System.out.println("The collision is happening");
                    }
                }
            }
        }
    }

}
