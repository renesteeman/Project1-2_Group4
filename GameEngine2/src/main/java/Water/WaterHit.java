package Water;

import Entities.Ball;
import Terrain.Terrain;
import Toolbox.Maths;
import org.joml.Vector3f;

public class WaterHit {

    public boolean hitWater(Ball ball){
        if(ball.getPosition().y<0){
            return true;
        } else {
            return false;
        }
    }

    //Reset the ball along a line going from the starting point of the game to the location where the water was hit
    public void ballReset(Ball ball, Terrain terrain, Vector3f startLocation, Vector3f waterHitLocation, float distanceFromWaterHit){

        Vector3f differenceStartAndHit = waterHitLocation.min(startLocation);
        float differenceStartAndHitLength = differenceStartAndHit.lengthSquared();
        //hitLoc - distance * (start-hit)/norm2(start-hit)
        Vector3f newPositionIndependentOfTerrain = waterHitLocation.min(differenceStartAndHit.div(differenceStartAndHitLength).mul(distanceFromWaterHit));
    }
}
