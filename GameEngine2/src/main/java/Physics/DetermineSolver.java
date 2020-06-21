package Physics;

import MainGame.MainGame;

//Determine which engine to use
public class DetermineSolver {
	public static PhysicsEngine getEulerSolver(PuttingCourse course, double step) {
		return new EulerSolver(course, step);
	}
	public static PhysicsEngine getVerletSolver(PuttingCourse course, double step) {
		return new VerletSolver(course, step);
	}	
	public static PhysicsEngine getVelocityVerletSolver(PuttingCourse course, double step) {
		return new VelocityVerletSolver(course, step);
	}
	public static PhysicsEngine getRungeKutta4Solver(PuttingCourse course, double step, MainGame game) {
		return new RungeKutta4Solver(course, step, game);
	}

	public static PhysicsEngine getVelocityVerletFlying(PuttingCourse course, double step) {
		return new VelocityVerletFlying(course, step);
	}
	public static PhysicsEngine getRungeKuttaFlying(PuttingCourse course, double step, MainGame game) {
		return new RungeKuttaFlying(course, step, game);
	}
}
