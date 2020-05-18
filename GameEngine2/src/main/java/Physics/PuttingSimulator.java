package Physics;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.util.TreeSet;
import java.util.LinkedList;
import com.google.common.collect.TreeMultiset;

public class PuttingSimulator extends JPanel {
	public PuttingCourse course;
	public PhysicsEngine engine;
	
	protected double DTIME = 1e-2; // 100 FPS
	public boolean passedFlag = false;

	public PuttingSimulator() {
		course = new PuttingCourse("./res/courses/course0.txt");
		engine = DetermineSolver.getEngine(course);
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
		if (sx.size() < 300)
			return false;
		double lx = sx.firstEntry().getElement(), rx = sx.lastEntry().getElement();
		double lz = sz.firstEntry().getElement(), rz = sz.lastEntry().getElement();
		//System.out.println(lx + " " + rx + " " + ly + " " + ry);
		return Math.abs(rx - lx) <= Vector2d.MAX_DIFFERENCE && Math.abs(rz - lz) <= Vector2d.MAX_DIFFERENCE;
	}

	public void takeShot(Vector2d initialBallVelocity) {
		currentShotInProcess = true;
		sx = TreeMultiset.create();
		sz = TreeMultiset.create();
		lsx = new LinkedList();
		lsz = new LinkedList();
		course.ball.setVelocity(new Vector3d(initialBallVelocity.x, 0, initialBallVelocity.y));

		passedFlag = false;
		while (!stopCondition()) {
			engine.process(DTIME);
			passedFlag |= engine.passedFlag();

			//System.out.println(sx.size());

			if (sx.size() == 300) {
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

			requestGraphicsUpdate();

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
