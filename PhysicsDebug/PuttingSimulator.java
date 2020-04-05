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
		course.ball.setCoords2(location);
	}

	public Vector2d getBallPosition2() {
		return course.ball.getCoords2();
	}

	public void setBallPosition3(Vector3d location) {
		course.ball.setCoords3(location);
	}

	public Vector3d getBallPosition3() {
		return course.ball.getCoords3();
	}

	protected TreeSet<Double> sx = new TreeSet<>(), sy = new TreeSet<>();
	protected LinkedList<Double> lsx = new LinkedList<>(), lsy = new LinkedList<>();

	protected boolean stopCondition() {
		if (sx.size() < 300)
			return false;
		double lx = sx.first(), rx = sx.last();
		double ly = sy.first(), ry = sy.last();
		//System.out.println(lx + " " + rx + " " + ly + " " + ry);
		return Math.abs(rx - lx) <= Vector2d.EPS && Math.abs(ry - ly) <= Vector2d.EPS;
	}

	public void takeShot(Vector2d initialBallVelocity) {
		course.ball.setVelocity2(initialBallVelocity);
		course.ball.setVelocity3(new Vector3d(initialBallVelocity, 0.));

		Vector3d nullVector = new Vector3d();
		
		while (!stopCondition()) {
			engine.process(DTIME);

			System.out.println(sx.size());

			if (sx.size() == 300) {
				sx.remove(lsx.remove());
				sy.remove(lsy.remove());
			}

			Vector3d curCoords = course.ball.getCoords3();
			lsx.add(curCoords.x);
			lsy.add(curCoords.y);
			sx.add(curCoords.x);
			sy.add(curCoords.y);

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
		return (victory || ((Vector2d.substract(course.ball.getCoords2(), course.goal.getCoords2())).len() <= course.getHoleRadius()));
	}

	public boolean victoriousPosition3() {
		return (victory || ((Vector3d.substract(course.ball.getCoords3(), course.goal.getCoords3())).len() <= course.getHoleRadius()));
	}

	// TO BE OVERRIDDEN
	public void requestGraphicsUpdate() {

	}
}