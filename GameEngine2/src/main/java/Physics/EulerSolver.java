package Physics;

import Collision.CheckCollision;
import MainGame.MainGame;
import Water.WaterHit;
import org.joml.Vector3f;

public class EulerSolver implements PhysicsEngine {
	private double step = 1e-2;
	private PuttingCourse course;
	private MainGame game;

	public final double GRAVITY = 9.81;

	public EulerSolver(PuttingCourse course, double step, MainGame game) {
		this.course = course;
		this.step = step;
		this.game = game;
	}

	public void setPhysicsStep(double step) {
		this.step = step;
	}

	/**
	 * Processes the shot using Euler's Method. The position and velocity are simultaneously updated using the current
	 * position and current velocity. This happens as follows:
	 * 		nextPosition = currentPosition + currentVelocity * step;
	 * 		nextVelocity = currentVelocity + currentAcceleration * step;
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
		Vector2d currentPosition = shotInfo.getPosition2D();
		Vector2d currentVelocity = shotInfo.getVelocity2D();
		boolean hitWater = false;

		for (double timer = 0; timer < dtime; timer += step) {
			if (currentVelocity.length() == 0) {
				currentVelocity = currentVelocity.add(new Vector2d(1e-20,1e-20));
			}
			Vector2d currentAcceleration = acceleration(currentPosition,currentVelocity);

			//Calculate next position and next velocity
			Vector2d nextPosition = currentPosition.add(currentVelocity.multiply(step));
			Vector2d nextVelocity = currentVelocity.add(currentAcceleration.multiply(step));

			//update position and velocity
			currentPosition = checkOutOfBounds(nextPosition);
			currentVelocity = limitVelocity(nextVelocity);

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
			/*hitWater = WaterHit.hitWater(ballPosition);
			if(hitWater){
				WaterHit.showWaterHitUI(game, ballPosition);
				course.ball.setVelocity(new Vector3d(0, 0, 0));
				currentVelocity = new Vector2d(0, 0);
				timer = dtime;
			}*/
		}

		if(!hitWater) {
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
