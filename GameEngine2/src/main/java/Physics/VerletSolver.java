package Physics;

public class VerletSolver implements PhysicsEngine {
    private double step = 1e-2; //TODO RANDOM VALUE, NEED TO ASSESS IT FURTHER ACCORDING TO THE INPUT
    private PuttingCourse course;
    private boolean passedFlag = false;

    public final double __G = 9.81; //TODO allow people to enter their preferred G value

    public VerletSolver(PuttingCourse course, double step) {
        this.course = course;
        this.step = step;
    }

    @Override
    public boolean passedFlag() {
        return passedFlag;
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
     * @param dtime the interval over which we process the shot
     */
    @Override
    public void process(double dtime) {
        passedFlag = false;

        Vector2d currentPosition = this.course.ball.getPosition2();
        Vector2d currentVelocity = this.course.ball.getVelocity2D();

        Vector2d previousPosition = currentPosition.subtract(currentVelocity.multiply(step)).add(acceleration(currentPosition,currentVelocity).multiply((step*step)/2.0));
        Vector2d nextPosition;

        for(double timer = 0; timer < dtime; timer += step) {
            nextPosition = currentPosition.multiply(2.0).subtract(previousPosition).add(acceleration(currentPosition,currentVelocity).multiply(step * step));

            currentVelocity = limitVelocity(nextPosition.subtract(previousPosition).divide(2.0 * step));

            previousPosition = checkOutOfBounds(currentPosition);
            currentPosition = checkOutOfBounds(nextPosition);

            if (course.victoriousPosition3())
                passedFlag = true;
        }

        nextPosition = currentPosition.multiply(2.0).subtract(previousPosition).add(acceleration(currentPosition,currentVelocity).multiply(step*step));
        currentVelocity = limitVelocity(nextPosition.subtract(previousPosition).divide(2.0));

        this.course.ball.setPosition(new Vector3d(currentPosition.x, course.height.evaluate(currentPosition), currentPosition.y));
        this.course.ball.setVelocity(new Vector3d(currentVelocity.x, 0, currentVelocity.y));
    }

    /**
     * Checks if the position is out of bounds, if so, then the ball is set at the particular bound
     * @param position the position
     * @return the (not-out-of-bounds) position
     */
    private Vector2d checkOutOfBounds(Vector2d position) {
        //Check for x
        if (position.x < 0) {
            position.x = 0;
        } else if (position.x > course.TERRAIN_SIZE) {
            position.x = course.TERRAIN_SIZE;
        }
        //Check for y
        if (position.y < 0) {
            position.y = 0;
        } else if (position.y > course.TERRAIN_SIZE) {
            position.y = course.TERRAIN_SIZE;
        }
        return position;
    }

    /**
     * Scale the velocity down to the maximum velocity if it is bigger than the maximum
     * @param velocity the velocity vector
     * @return the (scaled) velocity vector
     */
    private Vector2d limitVelocity(Vector2d velocity) {
        if (velocity.length() > course.maxVelocity) {
            double scalingFactor = course.maxVelocity / velocity.length();
            velocity = velocity.multiply(scalingFactor);
        }
        return velocity;
    }

    /**
     * Calculates the current acceleration given the position and velocity
     * @param position the current position of the ball
     * @param velocity the current velocity of the ball
     * @return the current acceleration
     */
    private Vector2d acceleration(Vector2d position, Vector2d velocity) {
        Vector2d gradient = this.course.height.gradient(position);
        double accelerationX =  -__G * (gradient.x + this.course.getFriction() * velocity.x / velocity.length());
        double accelerationY =  -__G * (gradient.y + this.course.getFriction() * velocity.y / velocity.length());
        return new Vector2d(accelerationX,accelerationY);
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
