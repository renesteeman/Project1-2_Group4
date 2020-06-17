package Physics;

public class VelocityVerletFlying implements PhysicsEngine{
    private double step = 1e-2; //TODO RANDOM VALUE, NEED TO ASSESS IT FURTHER ACCORDING TO THE INPUT
    private PuttingCourse course;
    private boolean passedFlag = false;

    public final double GRAVITY = 9.81; //TODO allow people to enter their preferred G value

    public VelocityVerletFlying(PuttingCourse course, double step){
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

        Vector3d currentPosition = shotInfo.getPosition3D();
        Vector3d currentVelocity = shotInfo.getVelocity3D();

        for (double timer = 0; timer < dtime; timer += step) {

            if (!isFlying(currentPosition)) {
                //Calculate the directional slope
                Vector2d normalizedVelocity = currentVelocity.getVector2D().getNormalized();
                Vector2d gradients = course.height.gradient(currentPosition.getVector2D());
                double directionalSlope = gradients.dotProduct(normalizedVelocity);

                //Break up velocity into components
                double totalVelocity = currentVelocity.length();
                double slopeAngle = Math.atan(directionalSlope);
                //TODO check if vertical and horizontal velocity are both completely correct
                //  because I suspect horizontal velocity is only correct when y = 0...
                double verticalVelocity = totalVelocity * Math.sin(slopeAngle);
                double horizontalScalar = totalVelocity * Math.cos(slopeAngle);
                Vector2d horizontalVelocity = currentVelocity.getVector2D().getNormalized().multiply(horizontalScalar);

                //Update 3D velocity vector
                currentVelocity = new Vector3d(horizontalVelocity.x, verticalVelocity, horizontalVelocity.y);
            }


            //STEP 1 - Calculate currentAcceleration
            Vector3d currentAcceleration = acceleration(currentPosition, currentVelocity);

            //STEP 2 - Calculate nextPosition
            Vector3d nextPosition = currentPosition.add(currentVelocity.multiply(step)).add(currentAcceleration.multiply(Math.pow(step, 2) / 2.0));
            nextPosition = checkOutOfBounds(nextPosition);

            //STEP 3 - Calculate intermediateVelocity
            Vector3d intermediateVelocity = currentVelocity.add(currentAcceleration.multiply(step / 2.0));
            intermediateVelocity = limitVelocity(intermediateVelocity);

            //STEP 4 - Calculate nextAcceleration
            Vector3d nextAcceleration = acceleration(nextPosition, intermediateVelocity);

            //STEP 5 - Calculate nextVelocity
            Vector3d nextVelocity = intermediateVelocity.add(nextAcceleration.multiply(step / 2.0));

            //STEP 6 - update variables
            currentPosition = checkOutOfBounds(nextPosition);
            currentVelocity = limitVelocity(nextVelocity);

            if (course.victoriousPosition3()) {
                passedFlag = true;
            }
        }

        shotInfo.setPosition3D(new Vector3d(currentPosition.x, currentPosition.y, currentPosition.z));
        shotInfo.setVelocity3D(new Vector3d(currentVelocity.x, currentVelocity.y, currentVelocity.z));
        return new ShotInfo(shotInfo);
    }

    /**
     * Checks whether the ball is flying
     * @return true if ball is in the air, false if ball is on the ground
     */
    private boolean isFlying(Vector3d position) {
        return (course.height.evaluate(position.getVector2D()) < position.y);
    }

    /**
     * Calculates the current acceleration given the position and velocity
     * @param position the current position of the ball
     * @param velocity the current velocity of the ball
     * @return the current acceleration
     */
    private Vector3d acceleration(Vector3d position, Vector3d velocity) {
        if (!isFlying(position)) {
            Vector2d gradients = course.height.gradient(position.getVector2D());
            double accelerationX = -GRAVITY * (gradients.x + course.getFriction() * velocity.x / velocity.length());
            double accelerationZ = -GRAVITY * (gradients.y + course.getFriction() * velocity.z / velocity.length());
            //TODO play around with y-acceleration, but keep in mind it should actually be 0.0?
            return new Vector3d(accelerationX, 0.0, accelerationZ);
        } else {
            return new Vector3d(0.0, -GRAVITY, 0.0);
        }
    }

    /**
     * Checks if the position is out of bounds, i.e., the ball is off the course or under the course.
     * If so, then the ball is set at the particular bound
     * @param position the position
     * @return the (not-out-of-bounds) position
     */
    private Vector3d checkOutOfBounds(Vector3d position) {
        //Check for x
        if (position.x < 0) position.x = 0;
        if (position.x > course.TERRAIN_SIZE) position.x = course.TERRAIN_SIZE;

        //Check for y (= height)
        double courseHeight = course.height.evaluate(position.getVector2D());
        if (position.y < courseHeight) position.y = courseHeight;

        //Check for z
        if (position.z < 0) position.y = 0;
        if (position.z > course.TERRAIN_SIZE) position.z = course.TERRAIN_SIZE;

        return new Vector3d(position.x,position.y,position.z);
    }

    /**
     * Scale the velocity down to the maximum velocity if it is bigger than the maximum velocity
     * @param velocity
     * @return (scaled) velocity
     */
    private Vector3d limitVelocity(Vector3d velocity) {
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





    //TODO probably remove, was taken from VelocitVerletSolver class since it is unused
    //the nextPosition() and isFlying() methods are for flying balls.
    //TODO check these ^ when flying is implemented in process method...
    /**
     * Calculates the position of the ball when it is not flying yet.
     * @param currentPosition requires: ball is not flying
     * @param horizontalVelocity requires: ball is not flying
     * @return next position of flying or non-flying ball
     */
//    private Vector3d nextPosition(Vector2d currentPosition, Vector2d horizontalVelocity) {
//        // Step 1 - Calculate vertical velocity
//        Vector2d gradient = this.course.height.gradient(currentPosition);
//        double magnitudeOfSlope = Math.hypot(gradient.x,gradient.y);
//        double angleOfSlope = Math.atan(magnitudeOfSlope);
//
//        //Because velocity is given in x- and y-direction, use Math.tan() to get velocity in z-direction
//        double heightVelocity = Math.tan(angleOfSlope) * horizontalVelocity.length();
//
//        // Step 2 - Calculate next height
//        double currentHeight = this.course.height.evaluate(currentPosition);
//        double nextHeight = currentHeight + (heightVelocity * step) + (1.0/2.0 * -__G * Math.pow(step,2));
//
//        // Step 3 - Calculate the nextPosition
//        Vector2d currentAcceleration = acceleration(currentPosition, horizontalVelocity);
//        Vector2d nextPosition = currentPosition.add(horizontalVelocity.multiply(step)).add(currentAcceleration.multiply(Math.pow(step,2) / 2.0));
//
//
//        // Step 4 - Calculate nextHeight and check if ball is in air
//        //If nextHeight is beneath the surface of the course, then nextHeight = surface
//        if (nextHeight < course.height.evaluate(nextPosition)) {
//            nextHeight = course.height.evaluate(nextPosition);
//        }
//
//        return new Vector3d(nextPosition.x, nextHeight, nextPosition.y);
//    }

    /**
     * Scale the vertical velocity down to the maximum possible velocity in the height direction, such that
     *      verticalVelocity = sqrt(hypotenuse^2 - adjacent^2) = sqrt(maxVelocity^2 - horizontalVelocity.length()^2)
     * Only the vertical velocity is scaled down since the acceleration in the x- and y-direction is 0, meaning that
     * the speed in the horizontal direction is constant. (In case of gravitational force only)
     * @param verticalVelocity
     * @param horizontalVelocity
     * @return the (scaled) vertical velocity
     */
//    private double limitVelocity(double verticalVelocity, Vector2d horizontalVelocity) {
//        if (Math.hypot(verticalVelocity,horizontalVelocity.length()) > course.maxVelocity) {
//            return Math.sqrt(Math.pow(course.maxVelocity,2) - Math.pow(horizontalVelocity.length(),2));
//        }
//        return verticalVelocity;
//    }

    /**
     * Checks whether the ball is flying
     * @return true if ball is in the air, false if ball is on the ground
     */
//    private boolean isFlying(Vector3d position) {
//        Vector2d position2D = new Vector2d(position.x,position.z);
//        return position.y > course.height.evaluate(position2D);
//    }
}
