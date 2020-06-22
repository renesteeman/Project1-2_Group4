package Water;

import Entities.Ball;
import Entities.IndicationBall;
import MainGame.MainGame;
import Terrain.Terrain;
import Toolbox.Maths;
import com.sun.tools.javac.Main;
import org.joml.Vector3f;

//Everything to do with the ball landing/hitting the water
public class WaterHit {

    public static boolean hitWater(Vector3f position){
        if(position.y<0){
            return true;
        } else {
            return false;
        }
    }

    //Reset the ball along a line going from the starting point of the game to the location where the water was hit
    public static void ballReset(Ball ball, Terrain terrain, Vector3f startLocation, Vector3f waterHitLocation, float distanceFromWaterHit){
        Vector3f differenceStartAndHit = Maths.minus(waterHitLocation, startLocation);
        Vector3f newPositionIndependentOfTerrain = Maths.minus(waterHitLocation, Maths.multiply(differenceStartAndHit, distanceFromWaterHit));
        Vector3f newPositionOnTerrain = new Vector3f(newPositionIndependentOfTerrain.x, (float) terrain.getHeightFromFunction(newPositionIndependentOfTerrain.x, newPositionIndependentOfTerrain.z), newPositionIndependentOfTerrain.z);

        ball.setPosition(newPositionOnTerrain);
    }

    //Put a phantom ball along a line going from the starting point of the game to the location where the water was hit
    public static void updateIndicationBall(IndicationBall ball, Terrain terrain, Vector3f startLocation, Vector3f waterHitLocation, float distanceFromWaterHit){
        Vector3f differenceStartAndHit = Maths.minus(waterHitLocation, startLocation);
        Vector3f newPositionIndependentOfTerrain = Maths.minus(waterHitLocation, Maths.multiply(differenceStartAndHit, distanceFromWaterHit));
        Vector3f newPositionOnTerrain = new Vector3f(newPositionIndependentOfTerrain.x, (float) terrain.getHeightFromFunction(newPositionIndependentOfTerrain.x, newPositionIndependentOfTerrain.z), newPositionIndependentOfTerrain.z);

        ball.setPosition(newPositionOnTerrain);
    }

    public static void showWaterHitUI(MainGame mainGame, Vector3f waterHitLocation){
        mainGame.createWaterHitUI(waterHitLocation);
        mainGame.getPlayerUiGroup().hide();
        mainGame.getWaterHitUI().show();
        mainGame.setHitWater(true);
    }

    public static void hideWaterHitUI(MainGame mainGame){
        mainGame.getWaterHitUI().hide();
        mainGame.getPlayerUiGroup().show();
        mainGame.setHitWater(false);
    }

}
