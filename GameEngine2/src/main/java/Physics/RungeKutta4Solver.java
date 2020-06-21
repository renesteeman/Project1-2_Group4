package Physics;

import Collision.CheckCollision;
import MainGame.MainGame;

/**
 * The RungeKutta4Solver class updates position and velocity of a ball after every time-step.
 *
 * The position and velocity can be updated by the process()-method.
 * This is done by the Classical Fourth-Order Runge-Kutta method in a second-order system.
 */
public class RungeKutta4Solver implements PhysicsEngine{
    private double step = 1e-2; //TODO RANDOM VALUE, NEED TO ASSESS IT FURTHER ACCORDING TO THE INPUT
    private PuttingCourse course;
    private MainGame game;

    public final double GRAVITY_EARTH = 9.81; //TODO allow people to enter their preferred G value

    public RungeKutta4Solver(PuttingCourse course, double step, MainGame game){
        this.course = course;
        this.step = step;
        this.game = game;
    }

    /**
     * Processes the shot  using the Classical 4th-order Runge-Kutta Method.
     * TODO add better description here
     * Source used: https://cg.informatik.uni-freiburg.de/course_notes/sim_02_particles.pdf (page/slide 29)
     *
     * @param dtime the interval over which the shot is processed
     * @param shotInfo contains info about current position and current velocity
     * @return info about the latest calculated position and velocity
     */
    @Override
    public ShotInfo process(double dtime, ShotInfo shotInfo) {
        Vector2d currentPosition = shotInfo.getPosition2D(); //p1
        Vector2d currentVelocity = shotInfo.getVelocity2D(); //v1

        for(double timer = 0; timer < dtime; timer += step){
            //STEP 1
            Vector2d currentAcceleration = acceleration(currentPosition, currentVelocity); //a1

            //STEP 2
            Vector2d intermediatePosition1 = currentPosition.add(currentVelocity.multiply(step / 2.0)); //p2 = p1 + v1 * 1/2 * step
            Vector2d intermediateVelocity1 = currentVelocity.add(currentAcceleration.multiply(step / 2.0)); //v2 = v1 + a1 * 1/2 * step
            Vector2d intermediateAcceleration1 = acceleration(intermediatePosition1, intermediateVelocity1); //a2 = acceleration(p2,v2)

            //STEP 3
            Vector2d intermediatePosition2 = currentPosition.add(intermediateVelocity1.multiply(step / 2.0)); //p3 = p1 + v2 * 1/2 * step
            Vector2d intermediateVelocity2 = currentVelocity.add(intermediateAcceleration1.multiply(step / 2.0)); //v3 = v1 + a2 * 1/2 * step
            Vector2d intermediateAcceleration2 = acceleration(intermediatePosition2, intermediateVelocity2); //a3 = acceleration(p3,v3)

            //STEP 4
            Vector2d endPosition = currentPosition.add(intermediateVelocity2.multiply(step)); //p4 = p1 + v3 * step
            Vector2d endVelocity = currentVelocity.add(intermediateAcceleration2.multiply(step)); //v4 = v1 + a3 * step
            Vector2d endAcceleration = acceleration(endPosition, endVelocity); //a4 = acceleration(p4,v4)


            //positionStep = 1/6 * step * (v1 + 2*v2 + 2*v3 + v4);
            Vector2d positionStep = currentVelocity.add(intermediateVelocity1.multiply(2.0)).add(intermediateVelocity2.multiply(2.0)).add(endVelocity).multiply(step / 6.0);
            //velocityStep = 1/6 * step (a1 + 2*a2 + 2*a3 + a4)
            Vector2d velocityStep = currentAcceleration.add(intermediateAcceleration1.multiply(2.0)).add(intermediateAcceleration2.multiply(2.0)).add(endAcceleration).multiply(step / 6.0);

            //Calculate next position and velocity and update current position and velocity
            currentPosition = checkOutOfBounds(currentPosition.add(positionStep));
            currentVelocity = limitVelocity(currentVelocity.add(velocityStep));
        }

        shotInfo.setPosition3D(new Vector3d(currentPosition.x, course.height.evaluate(currentPosition), currentPosition.y));
        shotInfo.setVelocity3D(new Vector3d(currentVelocity.x, 0, currentVelocity.y));

        CheckCollision.checkForCollision(game.getTrees().getTrees(), course.goal, course.ball);

        return new ShotInfo(shotInfo);
    }

    /**
     * Calculates the current acceleration given the position and velocity
     * @param position the current position of the ball
     * @param velocity the current velocity of the ball
     * @return the current acceleration
     */
    private Vector2d acceleration(Vector2d position, Vector2d velocity) {
        Vector2d gradient = course.height.gradient(position);
        double accelerationX =  -GRAVITY_EARTH * (gradient.x + course.getFriction() * velocity.x / velocity.length());
        double accelerationY =  -GRAVITY_EARTH * (gradient.y + course.getFriction() * velocity.y / velocity.length());
        return new Vector2d(accelerationX,accelerationY);
    }

    /**
     * Checks if the position is out of bounds, if so, then the ball is set at the particular bound
     * @param position
     * @return the (not-out-of-bounds) position
     */
    private Vector2d checkOutOfBounds(Vector2d position) {
        //Check for x
        if (position.x < 0) position.x = 0;
        if (position.x > course.TERRAIN_SIZE) position.x = course.TERRAIN_SIZE;
        //Check for y
        if (position.y < 0) position.y = 0;
        if (position.y > course.TERRAIN_SIZE) position.y = course.TERRAIN_SIZE;

        return new Vector2d(position.x,position.y);
    }

    /**
     * Scale the velocity down to the maximum velocity if it is bigger than the maximum
     * @param velocity
     * @return the (scaled) velocity
     */
    private Vector2d limitVelocity(Vector2d velocity) {
        double currentVelocity = velocity.length();
        if (course.maxVelocity < currentVelocity) {
            return velocity.divide(currentVelocity).multiply(course.maxVelocity);
        }
        return velocity;
    }

    @Override
    public void setStepSize(double h) {
        this.step = h;
    }

    @Override
    public double getStepSize() {
        return this.step;
    }
}
