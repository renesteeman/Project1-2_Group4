package game;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.util.TreeSet;
import java.util.LinkedList;

public class PuttingSimulator extends JPanel {
	public PuttingCourse course;
	public PhysicsEngine engine;
	
	protected double DTIME = 1e-2; // 100 FPS
	protected boolean victory = false;

	public PuttingSimulator() {}

	public PuttingSimulator(PuttingCourse course, PhysicsEngine engine) {
		this.course = course;
		this.engine = engine;
	}

	public void setBallPosition2(Vector2d location) {
		course.ball.setPosition(new Vector3d(location.x, 0, location.y));
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

	protected TreeSet<Double> sx = new TreeSet<>(), sz = new TreeSet<>();
	protected LinkedList<Double> lsx = new LinkedList<>(), lsz = new LinkedList<>();

	protected boolean stopCondition() {
		if (sx.size() < 300)
			return false;
		double lx = sx.first(), rx = sx.last();
		double lz = sz.first(), rz = sz.last();
		//System.out.println(lx + " " + rx + " " + ly + " " + ry);
		return Math.abs(rx - lx) <= Vector2d.EPS && Math.abs(rz - lz) <= Vector2d.EPS;
	}

	public void takeShot(Vector2d initialBallVelocity) {
		course.ball.setVelocity(new Vector3d(initialBallVelocity.x, 0., initialBallVelocity.y));

		while (!stopCondition()) {
			engine.process(DTIME);

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
			try {
			    Thread.sleep(10); /// 1000 * DTIME
			}
			catch(InterruptedException ex) {
			    Thread.currentThread().interrupt();
			}
			//if (height.evaluate(ball.getLocation()) < 0) {
			//	requestBallRepositioning();
			//	break;
			//}
		}

		//if (victoriousPosition3())
		//	victory = true;
	}

	@Override
	public void paintComponent(Graphics g) {

	}

	public boolean victoriousPosition2() {
		return (victory || ((Vector2d.substract(course.ball.getPosition2(), course.goal.getPosition2())).len() <= course.getHoleRadius()));
	}

	public boolean victoriousPosition3() {
		return (victory || ((Vector3d.substract(course.ball.getPosition3(), course.goal.getPosition3())).len() <= course.getHoleRadius()));
	}

	// TO BE OVERRIDDEN
	public void requestGraphicsUpdate() {

	}
}
