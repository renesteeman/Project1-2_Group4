package Physics;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.util.Random;
import java.util.LinkedList;
import com.google.common.collect.TreeMultiset;

public class PuttingSimulator extends JPanel {
	public PuttingCourse course;
	public PhysicsEngine engine;

	private static final boolean USE_RANDOM_ERROR = true;
	private static final double ERROR_WEIGHT = 0.5e-1; //TODO Play with the weight/constant

	protected double DTIME = 1e-1;
	public boolean passedFlag = false;

	public PuttingSimulator() {
		course = new PuttingCourse("./res/courses/course0.txt");
		engine = DetermineSolver.getEulerSolver(course, 1e-2);
	}

	public PuttingSimulator(PuttingCourse course, PhysicsEngine engine) {
		this.course = course;
		this.engine = engine;
	}

	//TODO rename
	protected TreeMultiset<Double> sx, sz;
	protected LinkedList<Double> lsx, lsz;
	public boolean currentShotInProcess = false;

	protected boolean stopCondition() {
		if (sx.size() < 20)
			return false;
		double lx = sx.firstEntry().getElement(), rx = sx.lastEntry().getElement();
		double lz = sz.firstEntry().getElement(), rz = sz.lastEntry().getElement();
		//System.out.println(lx + " " + rx + " " + ly + " " + ry);
		return Math.abs(rx - lx) <= Vector2d.MAX_DIFFERENCE && Math.abs(rz - lz) <= Vector2d.MAX_DIFFERENCE;
	}

	public void takeShot(Vector2d initialBallVelocity) {
		System.out.println("shot taken");
		currentShotInProcess = true;
		sx = TreeMultiset.create();
		sz = TreeMultiset.create();
		lsx = new LinkedList();
		lsz = new LinkedList();
		course.ball.setVelocity(new Vector3d(initialBallVelocity.x, 0, initialBallVelocity.y));

		if (USE_RANDOM_ERROR) {
			randomErrorUpdate(initialBallVelocity);
		}

		passedFlag = false;
		while (!stopCondition()) {
			//System.out.println("hasnt stopped");
			engine.process(DTIME);
			passedFlag |= engine.passedFlag();

			//System.out.println("processed");

			//System.out.println(sx.size());

			if (sx.size() == 20) {
				sx.remove(lsx.remove());
				sz.remove(lsz.remove());
			}

			Vector3d curCoords = course.ball.getPosition3();
			lsx.add(curCoords.x);
			lsz.add(curCoords.z);
			sx.add(curCoords.x);
			sz.add(curCoords.z);

			//System.out.println(course.ball.getCoords3());
			//System.out.println(course.ball.getVelocity3());

			//System.out.println("requesting graphics update");
			requestGraphicsUpdate();
			//System.out.println("graphics updated");

//			try {
//			    Thread.sleep(10); /// 1000 * DTIME
//			}
//			catch(InterruptedException ex) {
//			    Thread.currentThread().interrupt();
//			}
			//if (height.evaluate(ball.getLocation()) < 0) {
			//	requestBallRepositioning();
			//	break;
			//}
		}
		currentShotInProcess = false;
	}

	/**
	 * Updates the initial position and velocity of the ball with a small random error
	 * @param initialBallVelocity the initial velocity
	 */
	private void randomErrorUpdate(Vector2d initialBallVelocity) {
		Vector2d updatedPosition = positionErrorUpdate();
		setBallPosition2(updatedPosition);

		Vector2d updatedVelocity = velocityErrorUpdate(initialBallVelocity);
		course.ball.setVelocity(new Vector3d(updatedVelocity.x, 0, updatedVelocity.y));
	}

	/**
	 * @return position with small random error added
	 */
	private Vector2d positionErrorUpdate() {
		Vector2d initialBallPosition = getBallPosition2();

		//Create random offset from initial position
		double randomAdditionX = initialBallPosition.x * ERROR_WEIGHT * randomMultiplier();
		double randomAdditionY = initialBallPosition.y * ERROR_WEIGHT * randomMultiplier();
		Vector2d randomPositionAddition = new Vector2d(randomAdditionX,randomAdditionY);

		//Add offset to initial position
		Vector2d randomErrorPosition = initialBallPosition.add(randomPositionAddition);

		//Set ball back on edge if it goes out of bounds.
		if (randomErrorPosition.x < 0) randomErrorPosition.x = 0;
		if (randomErrorPosition.x > course.TERRAIN_SIZE) randomErrorPosition.x = course.TERRAIN_SIZE;
		if (randomErrorPosition.y < 0) randomErrorPosition.y = 0;
		if (randomErrorPosition.y > course.TERRAIN_SIZE) randomErrorPosition.y = course.TERRAIN_SIZE;

		return randomErrorPosition;
	}

	/**
	 * The randomAdditionVelocity is absolutely smaller than the initialBallVelocity, which makes sure that the ball
	 * does not randomly move in the opposite direction.
	 * @param initialBallVelocity the initial velocity
	 * @return velocity with small random error added
	 */
	private Vector2d velocityErrorUpdate(Vector2d initialBallVelocity) {

		Vector2d randomVelocityAddition = initialBallVelocity.multiply(ERROR_WEIGHT).multiply(randomMultiplier());
		//Add the velocity with the random error
		Vector2d randomErrorVelocity = initialBallVelocity.add(randomVelocityAddition);

		//Downscale vector to maxVelocity if it is too long
		if (randomErrorVelocity.length() > course.maxVelocity) {
			randomErrorVelocity = randomErrorVelocity.multiply(course.maxVelocity).divide(randomErrorVelocity.length());
		}

		return randomErrorVelocity;
	}

	/**
	 * @return a (pseudo-)random number between -1 and 1
	 */
	private double randomMultiplier() {
		Random numberGenerator = new Random();
		double randomMultiplier = numberGenerator.nextDouble();
		if (numberGenerator.nextDouble() < 0.5) {
			randomMultiplier = -randomMultiplier;
		}
		return randomMultiplier;
	}


	@Override
	public void paintComponent(Graphics g) {

	}

	public boolean passedFlag() {
		return passedFlag;
	}

	// TO BE OVERRIDDEN
	public void requestGraphicsUpdate() {

	}

	public void setBallPosition2(Vector2d location) {
		course.ball.setPosition(new Vector3d(location.x, course.height.evaluate(location), location.y));
	}

	public Vector2d getBallPosition2() {
		return course.ball.getPosition2();
	}

	public void setBallPosition3(Vector3d location) {
		course.ball.setPosition(location);
	}

	public Vector3d getBallPosition3() {
		return course.ball.getPosition3();
	}
}
