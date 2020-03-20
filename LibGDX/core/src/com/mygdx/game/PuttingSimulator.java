package com.mygdx.game;

public class PuttingSimulator {
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

		//TODO link to UI
		ball = new Ball(course.get_start_position(), new Vector2d());
		goal = new Goal(course.get_flag_position());
	}

	public void takeShot(Vector2d initial_ball_velocity) {
		ball.setVelocity(initial_ball_velocity);

		Vector2d nullVector = new Vector2d();

		while (!ball.velocity.equals(nullVector)) {
			engine.process(ball.location, ball.velocity, DTIME);
			ball.updateLocation(engine.getLocation());
			ball.setVelocity(engine.getVelocity());

			try {
			    Thread.sleep(100);
			}
			catch(InterruptedException ex) {
			    Thread.currentThread().interrupt();
			}
			if (height.evaluate(ball.location) < 0) {
				requestBallRepositioning();
				break;
			}
		}

		if (victoriousPosition())
			victory = true;
	}

	public boolean victoriousPosition() {
		//TODO rewrite for 3D
		return (victory || ((Vector2d.substract(ball.location, goal.location)).len() <= course.get_hole_tolerance()));
	}
}