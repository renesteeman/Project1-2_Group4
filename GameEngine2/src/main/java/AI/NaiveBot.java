package AI;

import Physics.PuttingSimulator;
import Physics.Vector2d;
import Physics.Vector3d;

import java.util.ArrayList;
import java.util.List;

public class NaiveBot {
    public PuttingSimulator simulator;

    public Vector2d shot;
    public double angleStep, angleRange, velocityStep;
    public List<Vector2d> goodAnglesShots = new ArrayList<Vector2d>();
    public List<Vector2d> goodSpeed = new ArrayList();

    public NaiveBot(PuttingSimulator simulator, double angleStep, double angleRange, double velocityStep) {
        this.simulator = simulator;
        this.angleStep = angleStep;
        this.angleRange = angleRange;
        this.velocityStep = velocityStep;
    }

    public double getAngle(Vector2d v) {
        v = v.getNormalized();
        if (v.y < 0) 
            return Math.toDegrees(Math.acos(v.x));
        else 
            return 360 - Math.toDegrees(Math.acos(v.x));
    }

    public Vector2d rotatedVector(double angle, double length) {
        Vector2d res = new Vector2d(Math.cos(Math.toRadians(angle)), Math.sin(Math.toRadians(angle)));
        return res.multiply(length);
    }

    public Vector2d getStraightVelocity() {
        Vector2d velocity = Vector2d.subtract(simulator.course.getFlag(), simulator.getBallPosition2());
        velocity = velocity.getNormalized();
        velocity = velocity.multiply(simulator.course.getMaxVelocity());
        return velocity;
    }

    public void findGoodAngles() {
        Vector2d straightVelocity = getStraightVelocity();
        
        double angle = getAngle(straightVelocity);

        Vector3d currentBallPosition = simulator.getBallPosition3();
        for (double currentAngle = angle - angleRange / 2; currentAngle < angle + angleRange / 2; currentAngle += angleStep) {
            Vector2d shot = rotatedVector(currentAngle, straightVelocity.length()); 
            
            simulator.takeShot(shot);

            if (simulator.passedFlag()) { //is supposed to check if the ball had pass the flag, we still have to create that method
                goodAnglesShots.add(shot);
            }

            simulator.setBallPosition3(currentBallPosition);
        }
    }

    public Vector2d getRightSpeed() { //find the right speed to use to have a hole in one

        Vector3d currentBallPosition = simulator.getBallPosition3();
        double velocityMax = simulator.course.getMaxVelocity();
        for (int i = 0; i < goodAnglesShots.size(); i++) { //go through all elements in the list and test all the speeds for each
            for (int currentVelocity = 0; currentVelocity < velocityMax; currentVelocity += velocityStep) {
                Vector2d velocity = goodAnglesShots.get(i);
                velocity = velocity.getNormalized();
                velocity = velocity.multiply(currentVelocity);

                simulator.takeShot(velocity);

                if (simulator.course.victoriousPosition3()) {
                    //goodSpeed.add(shot); //In case we want to see if there are more than one
                    System.out.println("WON");
                    return shot;
                }

                simulator.setBallPosition3(currentBallPosition);
            }
        }

        System.out.println("No one-shot solution found");
        return null;
    }

    //@Override
    public Vector2d solve() {
        findGoodAngles();
        Vector2d ans = getRightSpeed();
        return ans;
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
