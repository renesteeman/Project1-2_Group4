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

        //Do a collision check for all items that have collision
        for(CollisionEntity item : items){

            //If the collision box overlaps, there might be an actual collision, if so, do a precise check
            if(item.getCollisionBox().overlapsWithPointGivenMargin(item.getPosition(), ballLocation, margin)){
                //Go trough all the faces of the model to get the distance of a projection of the ball location vector onto the face. If the distance is lower than the size of the ball, it collides
                for(Face face : item.getCollisionModel().getFaces()){
                    Vector3f result = PreciseCollision.closestPointTriangle(face, ballLocation, item.getPosition());
                    if(PreciseCollision.isOverlapping(result, ball)){
                        //The normal is used to determine how the ball should bounce off the model
                        Vector3f normal = PreciseCollision.getNormalFromFace(face);
                        return new Vector3d(normal);
                    }
                }
            }
        }

        return null;
    }

}
