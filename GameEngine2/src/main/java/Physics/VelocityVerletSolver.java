package Physics;

public class VelocityVerletSolver implements PhysicsEngine{
    private double step = 1e-2; //TODO RANDOM VALUE, NEED TO ASSESS IT FURTHER ACCORDING TO THE INPUT
    private PuttingCourse course;
    private boolean passedFlag = false;

    public final double __G = 9.81; //TODO allow people to enter their preferred G value

    public VelocityVerletSolver(PuttingCourse course){
        this.course = course;
    }

    public boolean passedFlag() {
        return false;
    }

    /**
     * Processes the shot using the Velocity Verlet Method.
     * This method updates the position and velocity in five steps:
     * 1. The current acceleration is calculated using the current position and velocity.
     * 2. The next position is calculated using the current position, velocity and acceleration.
     * 3. The intermediate velocity is calculated using the current velocity and acceleration.
     * 4. The next acceleration is calculated using the next position and intermediate velocity.
     * 5. The next velocity is calculated using the intermediate velocity and the next acceleration.
     * (Sources used:   http://www.physics.udel.edu/~bnikolic/teaching/phys660/numerical_ode/node5.html ;
     *                  https://www2.icp.uni-stuttgart.de/~icp/mediawiki/images/5/54/Skript_sim_methods_I.pdf )
     * @param dtime the interval over which we process the shot
     */
    @Override
    public void process(double dtime) {
        passedFlag = false;

        Vector2d currentPosition = this.course.ball.getPosition2();
        Vector2d currentVelocity = this.course.ball.getVelocity2D();

        for (double timer = 0; timer < dtime; timer += step) {
            Vector2d currentAcceleration = acceleration(currentPosition,currentVelocity);
            Vector2d nextPosition = currentPosition.add(currentVelocity.multiply(step)).add(currentAcceleration.multiply(step * step / 2.0));

            Vector2d intermediateVelocity = limitVelocity(currentVelocity.add(currentAcceleration.multiply(step / 2.0)));
            Vector2d nextAcceleration = acceleration(nextPosition,intermediateVelocity);
            Vector2d nextVelocity = intermediateVelocity.add(nextAcceleration.multiply(step / 2.0));

            currentPosition = nextPosition;
            currentVelocity = limitVelocity(nextVelocity);

            //TODO find better place to place this piece of code
            if (currentPosition.x < 0) currentPosition.x = 0;
            if (currentPosition.x > course.TERRAIN_SIZE) currentPosition.x = course.TERRAIN_SIZE;
            if (currentPosition.y < 0) currentPosition.y = 0;
            if (currentPosition.y > course.TERRAIN_SIZE) currentPosition.y = course.TERRAIN_SIZE;

            if (course.victoriousPosition3())
                passedFlag = true;
        }

        this.course.ball.setPosition(new Vector3d(currentPosition.x, course.height.evaluate(currentPosition), currentPosition.y));
        this.course.ball.setVelocity(new Vector3d(currentVelocity.x, 0, currentVelocity.y));
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
