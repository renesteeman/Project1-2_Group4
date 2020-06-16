package Physics;

import org.joml.Vector3f;

public class VelocityVerletSolver implements PhysicsEngine{
    private double step = 1e-2; //TODO RANDOM VALUE, NEED TO ASSESS IT FURTHER ACCORDING TO THE INPUT
    private PuttingCourse course;
    private boolean passedFlag = false;

    public final double __G = 9.81; //TODO allow people to enter their preferred G value

    public VelocityVerletSolver(PuttingCourse course, double step){
        this.course = course;
        this.step = step;
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
    public ShotInfo process(double dtime, ShotInfo shotInfo) {
        passedFlag = false;

        Vector2d currentPosition = shotInfo.getPosition2D();
        Vector2d currentVelocity = shotInfo.getVelocity2D();

        for (double timer = 0; timer < dtime; timer += step) {
            Vector2d currentAcceleration = acceleration(currentPosition, currentVelocity);
            Vector2d nextPosition = currentPosition.add(currentVelocity.multiply(step)).add(currentAcceleration.multiply(Math.pow(step, 2) / 2.0));

            Vector2d intermediateVelocity = limitVelocity(currentVelocity.add(currentAcceleration.multiply(step / 2.0)));
            Vector2d nextAcceleration = acceleration(nextPosition, intermediateVelocity);
            Vector2d nextVelocity = intermediateVelocity.add(nextAcceleration.multiply(step / 2.0));

            currentPosition = checkOutOfBounds(nextPosition);
            currentVelocity = limitVelocity(nextVelocity);

            if (course.victoriousPosition3())
                passedFlag = true;
        }

        shotInfo.setPosition3D(new Vector3d(currentPosition.x, course.height.evaluate(currentPosition), currentPosition.y));
        shotInfo.setVelocity3D(new Vector3d(currentVelocity.x, 0, currentVelocity.y));
        return new ShotInfo(shotInfo);
    }

    /**
     * Calculates the position of the ball when it is not flying yet.
     * @param currentPosition requires: ball is not flying
     * @param horizontalVelocity requires: ball is not flying
     * @return next position of flying or non-flying ball
     */
    private Vector3d nextPosition(Vector2d currentPosition, Vector2d horizontalVelocity) {
        // Step 1 - Calculate vertical velocity
        Vector2d gradient = this.course.height.gradient(currentPosition);
        double magnitudeOfSlope = Math.hypot(gradient.x,gradient.y);
        double angleOfSlope = Math.atan(magnitudeOfSlope);

        //Because velocity is given in x- and y-direction, use Math.tan() to get velocity in z-direction
        double heightVelocity = Math.tan(angleOfSlope) * horizontalVelocity.length();

        // Step 2 - Calculate next height
        double currentHeight = this.course.height.evaluate(currentPosition);
        double nextHeight = currentHeight + (heightVelocity * step) + (1.0/2.0 * -__G * Math.pow(step,2));

        // Step 3 - Calculate the nextPosition
        Vector2d currentAcceleration = acceleration(currentPosition, horizontalVelocity);
        Vector2d nextPosition = currentPosition.add(horizontalVelocity.multiply(step)).add(currentAcceleration.multiply(Math.pow(step,2) / 2.0));


        // Step 4 - Calculate nextHeight and check if ball is in air
        //If nextHeight is beneath the surface of the course, then nextHeight = surface
        if (nextHeight < course.height.evaluate(nextPosition)) {
            nextHeight = course.height.evaluate(nextPosition);
        }

        return new Vector3d(nextPosition.x, nextHeight, nextPosition.y);
    }

    /**
     * Checks whether the ball is flying
     * @return true if ball is in the air, false if ball is on the ground
     */
    private boolean isFlying(Vector3d position) {
        Vector2d position2D = new Vector2d(position.x,position.z);
        return position.y > course.height.evaluate(position2D);
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
     * Scale the vertical velocity down to the maximum possible velocity in the height direction, such that
     *      verticalVelocity = sqrt(hypotenuse^2 - adjacent^2) = sqrt(maxVelocity^2 - horizontalVelocity.length()^2)
     * Only the vertical velocity is scaled down since the acceleration in the x- and y-direction is 0, meaning that
     * the speed in the horizontal direction is constant. (In case of gravitational force only)
     * @param verticalVelocity
     * @param horizontalVelocity
     * @return the (scaled) vertical velocity
     */
    private double limitVelocity(double verticalVelocity, Vector2d horizontalVelocity) {
        if (Math.hypot(verticalVelocity,horizontalVelocity.length()) > course.maxVelocity) {
            return Math.sqrt(Math.pow(course.maxVelocity,2) - Math.pow(horizontalVelocity.length(),2));
        }
        return verticalVelocity;
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
