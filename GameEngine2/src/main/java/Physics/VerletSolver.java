package Physics;

public class VerletSolver implements PhysicsEngine {
    private double step = 1e-2; //TODO RANDOM VALUE, NEED TO ASSESS IT FURTHER ACCORDING TO THE INPUT
    private PuttingCourse course;

    public final double GRAVITY = 9.81; //TODO allow people to enter their preferred G value

    public VerletSolver(PuttingCourse course, double step) {
        this.course = course;
        this.step = step;
    }

    /**
     * Processes the shot using the Verlet Method.
     *
     * The method initially calculates the previous position as follows:
     *      previousPosition = currentPosition - currentVelocity * step + 1/2 * currentAcceleration * step^2
     * Then, in a loop, the next position and the currentVelocity are calculated as follows:
     *      nextPosition = 2 * currentPosition - previousPosition + currentAcceleration * step^2
     *      currentVelocity = (nextPosition + previousPosition) / (2 * step)
     * After which, in the same loop, the previousPosition and currentPosition are updated:
     *      previousPosition = currentPosition
     *      currentPosition = nextPosition
     *
     * The velocity is always one step behind, because it is calculated using the Centered Difference formula.
     * To get the current velocity in the end, one extra step is performed.
     * In this step only the velocity is updated and not the position.
     *
     * Sources used:    http://www.physics.udel.edu/~bnikolic/teaching/phys660/numerical_ode/node5.html ;
     *                  https://www2.icp.uni-stuttgart.de/~icp/mediawiki/images/5/54/Skript_sim_methods_I.pdf .
     *
     * @param dtime the interval over which the shot is processed
     * @param shotInfo contains info about current position and current velocity
     * @return info about the latest calculated position and velocity
     */
    @Override
    public ShotInfo process(double dtime, ShotInfo shotInfo) {
        Vector2d currentPosition = shotInfo.getPosition2D();
        Vector2d currentVelocity = shotInfo.getVelocity2D();

        Vector2d previousPosition = currentPosition.subtract(currentVelocity.multiply(step)).add(acceleration(currentPosition,currentVelocity).multiply((step*step)/2.0));
        Vector2d nextPosition;

        for(double timer = 0; timer < dtime; timer += step) {
            nextPosition = currentPosition.multiply(2.0).subtract(previousPosition).add(acceleration(currentPosition,currentVelocity).multiply(step * step));

            currentVelocity = limitVelocity(nextPosition.subtract(previousPosition).divide(2.0 * step));

            previousPosition = checkOutOfBounds(currentPosition);
            currentPosition = checkOutOfBounds(nextPosition);
        }
        nextPosition = currentPosition.multiply(2.0).subtract(previousPosition).add(acceleration(currentPosition,currentVelocity).multiply(step*step));
        currentVelocity = limitVelocity(nextPosition.subtract(previousPosition).divide(2.0));

        shotInfo.setPosition3D(new Vector3d(currentPosition.x, course.height.evaluate(currentPosition), currentPosition.y));
        shotInfo.setVelocity3D(new Vector3d(currentVelocity.x, 0, currentVelocity.y));
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
        double accelerationX =  -GRAVITY * (gradient.x + course.getFriction() * velocity.x / velocity.length());
        double accelerationY =  -GRAVITY * (gradient.y + course.getFriction() * velocity.y / velocity.length());
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
