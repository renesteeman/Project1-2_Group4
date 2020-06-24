package AI;

import Physics.PuttingSimulator;
import Physics.Vector2d;
import Physics.Vector3d;

import java.util.ArrayList;

// Complexity O(((360 / angleStep) * (maxVelocity / speedStep)) ^ maxMovesForSolution)
// So be careful, this is pretty demanding 

public class BruteForce {
    public PuttingSimulator simulator;
    public int maxMovesForSolution = 20; // usually for such an expanding
    public double angleStep, speedStep;

    public ArrayList<Vector2d> solution = null,
                               current = new ArrayList<>();
    public boolean solution_found = false;

    public BruteForce(PuttingSimulator simulator, int maxMovesForSolution, double angleStep, double speedStep) {
        this.simulator = simulator;
        this.maxMovesForSolution = maxMovesForSolution;
        this.angleStep = angleStep;
        this.speedStep = speedStep;
    }

    public void solve() {
        solution_found = false;
        solution = null;
        current = new ArrayList<>();
        runRecursion(0);
    }

    public void runRecursion(int counter) {
        if (simulator.course.victoriousPosition3()) {
            solution = (ArrayList<Vector2d>)current.clone();
            solution_found = true;
            System.out.println("solution found");
            return; 
        }

        if (counter >= maxMovesForSolution) {
            return;
        } else {
            //System.out.printf("enter counter: %f\n", counter);

            Vector3d currentBallPosition = simulator.getBallPosition3();

            for (double initialAngle = 0.0; initialAngle < 360.0; initialAngle += angleStep) {

                //System.out.printf("current angle: %f out of 360\n", initialAngle);

                for (double initialSpeedModulo = 0.0; initialSpeedModulo < simulator.course.getMaxVelocity(); initialSpeedModulo += speedStep) {

                    //System.out.printf("current speed: %f out of %f\n", initialSpeedModulo, simulator.course.getMaxVelocity());

                    Vector2d currentShot = new Vector2d(adjacent(initialSpeedModulo, initialAngle), oppositeSide(initialSpeedModulo, initialAngle));
                    current.add(currentShot);

                    //System.out.println("shot started");
                    //System.out.println(currentShot);

                    System.out.println("shot");
                    simulator.takeShot(currentShot);

                    //System.out.println("shot finished");

                    runRecursion(counter + 1);

                    //System.out.printf("\033[F");
                    //System.out.printf("\033[F");
                    //System.out.printf("\033[F");

                    if (solution_found) 
                        break;

                    current.remove(current.size() - 1);
                    simulator.setBallPosition3(currentBallPosition);

                    //System.out.printf("\033[F");
                }

                if (solution_found)
                    break;

                //System.out.printf("\033[F");
            }

            //System.out.printf("\033[F");
        }
    }

    private double oppositeSide (double power, double angle){
        return (Math.sin(Math.toRadians(angle))*power);
    }

    private double adjacent(double power, double angle){
        return (Math.cos(Math.toRadians(angle))*power);
    }
}