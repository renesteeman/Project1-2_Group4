package AI;

import Physics.*;
///import java.Math.*;

import java.util.ArrayList;

// Complexity O(((360 / angleStep) * (maxVelocity / speedStep)) ^ maxMovesForSolution)
// So be careful, this is pretty demanding 

public class BruteForce {
    public PuttingSimulator simulator;
    public int maxMovesForSolution = 20; // usually for such an expanding
    public double angleStep, speedStep;

    public ArrayList<Vector2d> solution;
    public boolean solution_found = false;

    public BruteForce(PuttingSimulator simulator, int maxMovesForSolution, double angleStep, double speedStep) {
		this.simulator = simulator;
		this.maxMovesForSolution = maxMovesForSolution;
		this.angleStep = angleStep;
		this.speedStep = speedStep;
	}

    public void runRecursion(int counter, ArrayList<Vector2d> current) {
        if (simulator.course.victoriousPosition3()) {
        	solution = current;
        	solution_found = true;
        	return; 
        }

        if (counter > maxMovesForSolution) {
            return;
        } else {
        	Vector3d currentBallPosition = simulator.getBallPosition3();

            for (double initialAngle = 0.0; initialAngle < 360.0; initialAngle += angleStep) {
            	for (double initialSpeedModulo = 0.0; initialSpeedModulo < simulator.course.getMaxVelocity(); initialSpeedModulo += speedStep) {

            		Vector2d currentShot = new Vector2d(adjacent(initialSpeedModulo, initialAngle), oppositeSide(initialSpeedModulo, initialAngle));
            		current.add(currentShot);
            		simulator.takeShot(currentShot);
            		runRecursion(counter + 1, current);

            		if (solution_found) 
            			break;

            		current.remove(current.size() - 1);
            		simulator.setBallPosition3(currentBallPosition);
            	}

            	if (solution_found)
            		break;
            }
        }
    }

    @Override
    public void solve() {
        
    }

    private double oppositeSide (double power, double angle){
        return (Math.sin(Math.toRadians(angle))*power);
    }

    private double adjacent(double power, double angle){
        return (Math.cos(Math.toRadians(angle))*power);
    }
}