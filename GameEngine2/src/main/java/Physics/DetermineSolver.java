package Physics;

import MainGame.MainGame;

//Determine which engine to use
public class DetermineSolver {
	//Non-flying-ball engines
	public static PhysicsEngine getEulerSolver(PuttingCourse course, double step, MainGame game) {
		return new EulerSolver(course, step, game);
	}
	public static PhysicsEngine getVelocityVerletSolver(PuttingCourse course, double step, MainGame game) {
		return new VelocityVerletSolver(course, step, game);
	}
	public static PhysicsEngine getRungeKutta4Solver(PuttingCourse course, double step, MainGame game) {
		return new RungeKutta4Solver(course, step, game);
	}

	//Flying-ball engines
	public static PhysicsEngine getVelocityVerletFlying(PuttingCourse course, double step) {
		return new VelocityVerletFlying(course, step);
	}
	public static PhysicsEngine getRungeKuttaFlying(PuttingCourse course, double step, MainGame game) {
		return new RungeKuttaFlying(course, step, game);
	}
}
