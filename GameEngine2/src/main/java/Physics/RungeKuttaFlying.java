package Physics;

import MainGame.MainGame;

public class RungeKuttaFlying implements PhysicsEngine{
    private double step = 1e-2;
    private PuttingCourse course;
    private MainGame game;

    private boolean wasFlying = false;

    public final double GRAVITY_EARTH = 9.81;
    private final double MASS_OF_BALL = course.ball.getMassOfBall();

    //Air friction coefficients for golf ball
    private final double DRAG_COEFFICIENT = 0.47; //BiNaS HAVO/VWO zesde editie
    private final double AIR_DENSITY = 1.205; //at 20 degrees celsius
    private final double DIAMETER_GOLFBALL = 4.5/100.0; //in meters, diameter >= 4.267cm
    private final double CROSS_SECTIONAL_AREA = Math.PI * Math.pow(DIAMETER_GOLFBALL/2.0, 2);
    //A constant term in the air friction formula can be calculated as follows:
    private final double DRAG_CONSTANT = 1.0/2.0 * DRAG_COEFFICIENT * AIR_DENSITY * CROSS_SECTIONAL_AREA; //0.00045037


    public RungeKuttaFlying(PuttingCourse course, double step, MainGame game){
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
        Vector3d currentPosition = shotInfo.getPosition3D(); //p1
        Vector3d currentVelocity = shotInfo.getVelocity3D(); //v1

        for(double timer = 0; timer < dtime; timer += step){
            if (!isFlying(currentPosition)) {
                System.out.println("I'm not flying :(");
                currentVelocity = redirectVelocity(currentPosition,currentVelocity);
                wasFlying = false;
            } else {
                System.out.println("I BELIEVE I CAN FLY!");
                wasFlying = true;
            }

            //STEP 1
            Vector3d currentAcceleration = acceleration(currentPosition, currentVelocity); //a1

            //STEP 2
            Vector3d intermediatePosition1 = checkOutOfBounds(currentPosition.add(currentVelocity.multiply(step / 2.0))); //p2 = p1 + v1 * 1/2 * step
            Vector3d intermediateVelocity1 = limitVelocity(currentVelocity.add(currentAcceleration.multiply(step / 2.0))); //v2 = v1 + a1 * 1/2 * step
            Vector3d intermediateAcceleration1 = acceleration(intermediatePosition1, intermediateVelocity1); //a2 = acceleration(p2,v2)

            //STEP 3
            Vector3d intermediatePosition2 = checkOutOfBounds(currentPosition.add(intermediateVelocity1.multiply(step / 2.0))); //p3 = p1 + v2 * 1/2 * step
            Vector3d intermediateVelocity2 = limitVelocity(currentVelocity.add(intermediateAcceleration1.multiply(step / 2.0))); //v3 = v1 + a2 * 1/2 * step
            Vector3d intermediateAcceleration2 = acceleration(intermediatePosition2, intermediateVelocity2); //a3 = acceleration(p3,v3)

            //STEP 4
            Vector3d endPosition = checkOutOfBounds(currentPosition.add(intermediateVelocity2.multiply(step))); //p4 = p1 + v3 * step
            Vector3d endVelocity = limitVelocity(currentVelocity.add(intermediateAcceleration2.multiply(step))); //v4 = v1 + a3 * step
            Vector3d endAcceleration = acceleration(endPosition, endVelocity); //a4 = acceleration(p4,v4)


            //positionStep = 1/6 * step * (v1 + 2*v2 + 2*v3 + v4);
            Vector3d positionStep = currentVelocity.add(intermediateVelocity1.multiply(2.0)).add(intermediateVelocity2.multiply(2.0)).add(endVelocity).multiply(step / 6.0);
            //velocityStep = 1/6 * step (a1 + 2*a2 + 2*a3 + a4)
            Vector3d velocityStep = currentAcceleration.add(intermediateAcceleration1.multiply(2.0)).add(intermediateAcceleration2.multiply(2.0)).add(endAcceleration).multiply(step / 6.0);

            //Calculate next position and velocity and update current position and velocity
            currentPosition = checkOutOfBounds(currentPosition.add(positionStep));
            currentVelocity = limitVelocity(currentVelocity.add(velocityStep));
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
        return position.y - course.height.evaluate(position.getVector2D()) > 1e-2;
    }

    /**
     * Redirects the velocity accordingly with respect to the directional slope
     * @param currentPosition the current position
     * @param currentVelocity the current velocity
     * @return the redirected velocity given the current position and velocity
     */
    private Vector3d redirectVelocity(Vector3d currentPosition, Vector3d currentVelocity) {
        //Calculate the directional slope
        Vector2d normalizedVelocity = currentVelocity.getVector2D().getNormalized();
        Vector2d gradients = course.height.gradient(currentPosition.getVector2D());
        double directionalSlope = gradients.dotProduct(normalizedVelocity);
        double slopeAngle = Math.atan(directionalSlope);

        //If ball was flying, then the ball bounces. Else velocity is redirected along the slope.
        if (wasFlying) {
            double ballGradient = currentVelocity.y / currentVelocity.getVector2D().length();
//            double angle;
//            if (Double.isInfinite((slopeAngle - ballGradient) / (1 - slopeAngle * ballGradient))) {
//                angle = Math.PI / 2.0;
//            } else {
//                angle = 1; //TODO change........
//            }

            //TODO check if directionalSlope3D is correct
            Vector3d directionalSlope3D = new Vector3d(normalizedVelocity.x,directionalSlope,normalizedVelocity.y);
            double dotProduct = directionalSlope3D.dotProduct(currentVelocity);
            double cos = dotProduct / (directionalSlope3D.length() * currentVelocity.length());
            double angle = Math.acos(cos); //value between 0.0 and PI

            double perpendicularSlope = -1/directionalSlope;
            double redirectionAngle = Math.atan(perpendicularSlope) + (90 - angle);

            Vector3d redirectedVelocity = limitVelocity(currentVelocity.multiply(Math.tan(redirectionAngle)));
            redirectedVelocity = redirectedVelocity.multiply(0.8f);
            redirectedVelocity = limitVelocity(redirectedVelocity);




//            if (angle > Math.PI) {
//                angle = 2 * Math.PI - angle;
//            }













            double redirectAngle;
            if (slopeAngle < 0) {
                redirectAngle = slopeAngle + angle;
            } else if (slopeAngle > 0) {
                redirectAngle = slopeAngle - angle;
            }
//            } else {
//                redirectAngle = -currentVelocity.y / currentVelocity.getVector2D().length();
//            }

//            //ballGradient = verticalVelocity / horizontalVelocity
//            double ballGradient = currentVelocity.y/Math.sqrt(Math.pow(currentVelocity.x,2)+Math.pow(currentVelocity.z,2));
//            double ballAngle = Math.atan(ballGradient);
//            double angle = Math.abs(slopeAngle - ballAngle);
//            if (angle > 90) {
//                angle = 180 - angle;
//            }
//            double redirectAngle = slopeAngle - angle;
//
//            return limitVelocity(currentVelocity.multiply(Math.tan(redirectAngle)));
//            return limitVelocity(currentVelocity.multiply(Math.tan(redirectAngle)).multiply(0.95));
            return redirectedVelocity;
        } else {
            //Break up velocity into components
            double totalVelocity = currentVelocity.length();
            double verticalVelocity = totalVelocity * Math.sin(slopeAngle);
            double horizontalVelocityScalar = totalVelocity * Math.cos(slopeAngle);
            Vector2d horizontalVelocity = currentVelocity.getVector2D().getNormalized().multiply(horizontalVelocityScalar);

            return limitVelocity(new Vector3d(horizontalVelocity.x, verticalVelocity, horizontalVelocity.y));
        }
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
            double accelerationX = -GRAVITY_EARTH * (gradients.x + course.getFriction() * velocity.x / velocity.length());
            double accelerationZ = -GRAVITY_EARTH * (gradients.y + course.getFriction() * velocity.z / velocity.length());
            return new Vector3d(accelerationX, 0.0, accelerationZ);
        } else {
            double accelerationX = -(DRAG_CONSTANT * velocity.x * velocity.length()) / MASS_OF_BALL;
            double accelerationY = -GRAVITY_EARTH - (DRAG_CONSTANT * velocity.y * velocity.length()) / MASS_OF_BALL;
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
