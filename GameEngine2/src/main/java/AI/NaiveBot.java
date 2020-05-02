package AI;

import Physics.PuttingCourse;
import Physics.PuttingSimulator;
import Physics.Vector2d;
import java.util.ArrayList;
import java.util.List;

public class NaiveBot {
    PuttingSimulator simulator;
    PuttingCourse obj;
    Vector2d shot;
    public double velocityMax;
    List<Vector2d> goodAnglesShots = new ArrayList<Vector2d>();
    List<Vector2d> goodSpeed = new ArrayList();
    public double distance;

    public NaiveBot(PuttingSimulator simulator){
        this.simulator = simulator;
    }

    public double getDistance() {
        distance = Math.sqrt(((obj.getFlag().get_x() - obj.getStart().get_x()) * (obj.getFlag().get_x() - obj.getStart().get_x())) + ((obj.getFlag().get_y() - obj.getStart().get_y()) * (obj.getFlag().get_y() - obj.getStart().get_y())));
        return distance;
    }

    private double oppositeSide (double power, double angle){
        return (Math.sin(Math.toRadians(angle))*power);
    }

    private double adjacent(double power, double angle){
        return (Math.cos(Math.toRadians(angle))*power);
    }

    private Vector2d getPositionForFlag(Vector2d flag, Vector2d ball, double distance){
        return new Vector2d( ((flag.get_x() - ball.get_x()) / (getDistance())) * velocityMax, ((flag.get_y() - ball.get_y()) / (getDistance())) * velocityMax);
    }

    public void goodAngles(){
        Vector2d interval = getPositionForFlag(obj.getFlag(), obj.getStart(), getDistance());
        double angle = Math.toDegrees(Math.asin(velocityMax/interval.get_y()));
        for(double i = angle - 30; i<angle+30; i = i+0.25){
            shot = new Vector2d(adjacent(velocityMax, i),oppositeSide(velocityMax, i)); //Shot is the hypotenuse composed of the x and y vectors which correspond to the opposite and the adjacent sides
            simulator.takeShot(shot);
            if(simulator.victoriousPosition3()) { //is supposed to check if the ball had pass the flag, we still have to create that method
                goodAnglesShots.add(shot);
            }
        }
    }

    public Vector2d rightSpeed(){ //find the right speed to use to have a hole in one
        goodAngles();
        for(int i = 0; i<goodAnglesShots.size(); i++) { //go through all elements in the list and test all the speeds for each
            for(int j = 0; j<velocityMax ; j++){
                Vector2d vect = (Vector2d) goodAnglesShots.get(i);
                double angle = Math.asin(velocityMax/vect.get_y()); //is in radian
                shot = new Vector2d(adjacent(j, Math.toDegrees(angle)),oppositeSide(j, Math.toDegrees(angle)));
                if(simulator.victoriousPosition3()){
                    //goodSpeed.add(shot); //In case we want to see if there are more than one
                    return shot;
                }
            }
        }
        System.out.println("There is nbo hole in one possible with this algorithm.");
        return null;
    }

    /**
     * Call the method take shot from the PuttingSimulator class, update the position afterwards as the new initial position
     * Will do this action 360 times so we check for every angle of the shot is doable
     * Check how far from the flag we are after that shot is made
     * Check if the shot made the ball go in the radius of the accepted win, and if yes keep that action in reward.
     * Get all the shots that were successful, and find the good speed for them
     *
     * old method for angles
     * //    public void goodAngles(){ //finds all the angles that are possible to do the hole in one and add those actions to the list
     * //        for(int i = 0; i<360; i++){
     * //            shot = new Vector2d(adjacent(velocityMax, i),oppositeSide(velocityMax, i)); //Shot is the hypotenuse composed of the x and y vectors which correspond to the opposite and the adjacent sides
     * //            simulator.takeShot(shot);
     * //            if(simulator.passedFlag){
     * //                goodAnglesShots.add(shot);
     * //            }
     * //        }
     * //    }
     */

}
