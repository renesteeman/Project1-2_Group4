package com.mygdx.game;

public class PuttingSimulator {
	//TODO why 2D?
	public Function2d height;
	public PuttingCourse course;
	public PhysicsEngine engine;
	public Ball ball;
	public Goal goal;

	protected double DTIME = 1e-2; // 100 FPS
	protected boolean victory = false;

	public PuttingSimulator() {}

	public PuttingSimulator(PuttingCourse course, PhysicsEngine engine) {
		this.course = course;
		this.engine = engine;
		height = course.get_height();
//		ball = new Ball(course.get_start_position(), new Vector2d()); // TO BE REPLACED
//		goal = new Goal(course.get_flag_position()); // TO BE REPLACED
	}

	public void set_ball_position(Vector2d location) {
		ball.updateLocation(location);
	}

	public Vector2d get_ball_position() {
		return ball.getLocation();
	}

	public void take_shot(Vector2d initial_ball_velocity) {
		ball.setVelocity(initial_ball_velocity);

		Vector2d nullVector = new Vector2d();
		while (!ball.getVelocity().equals(nullVector)) {
			engine.process(ball.getLocation(), ball.getVelocity(), DTIME);
			requestGraphicsUpdate();
			if (height.evaluate(ball.getLocation()) < 0) {
				requestBallRepositioning();
				break;
			}
		}

		if (victoriousPosition())
			victory = true;
	}

	public boolean victoriousPosition() {
		return (victory || ((Vector2d.substract(ball.getLocation(), goal.getLocation())).len() <= course.get_hole_tolerance()));
	}

	// TO BE OVERRIDDEN
	public void requestGraphicsUpdate() {

	}

	// TO BE OVERRIDDEN
	public void requestBallRepositioning() {

	}
}