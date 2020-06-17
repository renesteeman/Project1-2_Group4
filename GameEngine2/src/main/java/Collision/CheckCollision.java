package Collision;

import Entities.*;
import Collision.CollisionBox;
import org.joml.Vector3f;

import java.util.ArrayList;

import static Collision.PreciseCollision.closestPointInTriangle;
import static Collision.PreciseCollision.isOverlapping;

public class CheckCollision {

    private static final float margin = 0.2f;

    public static void checkForCollision(ArrayList<Tree> trees, Goal goal, Ball ball){

        ArrayList<CollisionEntity> items = new ArrayList<>(trees);
        items.add(goal);

        for(CollisionEntity item : items){
            if(item.getCollisionBox().overlapsWithPointGivenMargin(item.getPosition(), ball.getPosition(), margin)){
                for(Face face : item.getCollisionModel().getFaces()){
                    Vector3f result = closestPointInTriangle(ball.getPosition(), face);
                    if(isOverlapping(result, ball)){
                        System.out.println("The collision is happening");
                    }
                }
            }
        }
    }

}
