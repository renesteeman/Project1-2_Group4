package Physics;

public class VelocityVerletFlying implements PhysicsEngine{
    private double step = 1e-2;
    private PuttingCourse course;

    public final double GRAVITY = 9.81;
    private final double MASS_OF_BALL = course.ball.getMassOfBall();

    //Air friction coefficients for golf ball
    private final double DRAG_COEFFICIENT = 0.47; //BiNaS HAVO/VWO zesde editie
    private final double AIR_DENSITY = 1.2041; //at 20 degrees celsius
    private final double DIAMETER_GOLFBALL = 4.5/100.0; //in meters, diameter >= 4.267cm
    private final double CROSS_SECTIONAL_AREA = Math.PI * Math.pow(DIAMETER_GOLFBALL/2.0, 2);
    //A constant term in the air friction formula can be calculated as follows:
    private final double DRAG_CONSTANT = 1.0/2.0 * DRAG_COEFFICIENT * AIR_DENSITY * CROSS_SECTIONAL_AREA; //0.001800136


    public VelocityVerletFlying(PuttingCourse course, double step){
        this.course = course;
        this.step = step;
    }
    
    public void setPhysicsStep(double step) {
        this.step = step;
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
     *
     * @param dtime the interval over which the shot is processed
     * @param shotInfo contains info about current position and current velocity
     * @return info about the latest calculated position and velocity
     */
    @Override
    public ShotInfo process(double dtime, ShotInfo shotInfo) {
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
                double verticalVelocity = totalVelocity * Math.sin(slopeAngle);
                double horizontalVelocityScalar = totalVelocity * Math.cos(slopeAngle);
                Vector2d horizontalVelocity = currentVelocity.getVector2D().getNormalized().multiply(horizontalVelocityScalar);

                //Update 3D velocity vector
                currentVelocity = limitVelocity(new Vector3d(horizontalVelocity.x, verticalVelocity, horizontalVelocity.y));
            } else {

            }

            Vector3d currentAcceleration = acceleration(currentPosition, currentVelocity); //STEP 1

            Vector3d nextPosition = currentPosition.add(currentVelocity.multiply(step)).add(currentAcceleration.multiply(Math.pow(step, 2) / 2.0)); //STEP 2
            nextPosition = checkOutOfBounds(nextPosition);

            Vector3d intermediateVelocity = currentVelocity.add(currentAcceleration.multiply(step / 2.0)); //STEP 3
            intermediateVelocity = limitVelocity(intermediateVelocity);

            Vector3d nextAcceleration = acceleration(nextPosition, intermediateVelocity); //STEP 4
            Vector3d nextVelocity = intermediateVelocity.add(nextAcceleration.multiply(step / 2.0)); //STEP 5

            currentPosition = checkOutOfBounds(nextPosition);
            currentVelocity = limitVelocity(nextVelocity);
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
        return course.height.evaluate(position.getVector2D()) < position.y;
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
            return new Vector3d(accelerationX, 0.0, accelerationZ);
        } else {
            double accelerationX = -(DRAG_CONSTANT * velocity.x * velocity.length()) / MASS_OF_BALL;
            double accelerationY = -GRAVITY - (DRAG_CONSTANT * velocity.y * velocity.length()) / MASS_OF_BALL;
            double accelerationZ = -(DRAG_CONSTANT * velocity.z * velocity.length()) / MASS_OF_BALL;
            return new Vector3d(accelerationX, accelerationY, accelerationZ);
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
}
