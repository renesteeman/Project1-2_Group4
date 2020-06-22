package Physics;

import Collision.CheckCollision;
import MainGame.MainGame;
import Water.WaterHit;
import org.joml.Vector3f;

public class RungeKutta4Solver implements PhysicsEngine{
    private double step = 1e-2;
    private PuttingCourse course;
    private MainGame game;

    public final double GRAVITY = 9.81;

    public RungeKutta4Solver(PuttingCourse course, double step, MainGame game){
        this.course = course;
        this.step = step;
        this.game = game;
    }

    /**
     * Processes the shot using the Classical 4th-order Runge-Kutta Method. This method takes four samples of the
     * velocity and acceleration at different time-points in a time-step. To get the next position and velocity, a
     * weighted average of these four samples is calculated and multiplied with a time-step, which is added to the
     * current position.
     *
     * The formulas to calculate these samples are:
     *      k1 = v(t)                               k3 = v(t) + l2 * 1/2 * Δt
     *      l1 = a(p(t), v(t))                      l3 = a(p(t) + k2 * 1/2 * Δt, k3)
     *      k2 = v(t) + l1 * 1/2 * Δt               k4 = v(t) + l3 * Δt
     *      l2 = a(p(t) + k1 * 1/2 * Δt, k2)        l4 = a(p(t) + k2 * Δt, k4)
     * (where p = position, v = velocity, a = acceleration, t = time,
     *      and Δt = step size = change in time from current position)
     *
     * And to calculate the next position and next velocity with these samples, the formulas are:
     *      p(t+Δt) = p(t) + 1/6 * Δt * (k1 + 2*k2 + 2*k3 + k4)
     *      v(t+Δt) = v(t) + 1/6 * Δt * (l1 + 2*l2 + 2*l3 + l4)
     *
     * Source used: https://cg.informatik.uni-freiburg.de/course_notes/sim_02_particles.pdf (page/slide 29)
     *
     * After the position and velocity are updated, there is checked if there is any collision with an object, or that
     * the ball hit with the water. If there is any collision detected, then these events will be handled accordingly.
     *
     * @param dtime the interval over which the shot is processed
     * @param shotInfo contains info about current position and current velocity
     * @return info about the latest calculated position and velocity
     */
    @Override
    public ShotInfo process(double dtime, ShotInfo shotInfo) {
        Vector2d currentPosition = shotInfo.getPosition2D(); //p1
        Vector2d currentVelocity = shotInfo.getVelocity2D(); //v1
        boolean hitWater = false;

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

            //Check for collisions and react accordingly
            Vector3f ballPosition = new Vector3f((float) currentPosition.x, (float) course.height.evaluate(currentPosition), (float) currentPosition.y);
            Vector3d collisionNormal = CheckCollision.checkForCollision(game.getTrees().getTrees(), course.goal, course.ball, ballPosition);
            if(collisionNormal!=null){
                System.out.println("YEE");

                double A = (currentVelocity.dotProduct(collisionNormal.getVector2D()))/(currentVelocity.length()*collisionNormal.getVector2D().length());
                double angle = Math.acos(A);
                currentVelocity = currentVelocity.rotate(angle);
            }

            //Check for water 'collision'
            hitWater = WaterHit.hitWater(ballPosition);
            if(hitWater){
                WaterHit.showWaterHitUI(game, ballPosition);
                course.ball.setVelocity(new Vector3d(0, 0, 0));
                currentVelocity = new Vector2d(0, 0);
                timer = dtime;
            }
        }

        if(!hitWater){
            shotInfo.setPosition3D(new Vector3d(currentPosition.x, course.height.evaluate(currentPosition), currentPosition.y));
            shotInfo.setVelocity3D(new Vector3d(currentVelocity.x, 0, currentVelocity.y));
        }

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
        return (new Vector2d(accelerationX,accelerationY)).multiply(1./3.);
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
