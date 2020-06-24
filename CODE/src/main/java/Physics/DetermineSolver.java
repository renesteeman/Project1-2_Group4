package Physics;

import MainGame.MainGame;

//Determine which engine to use
public class DetermineSolver {
	//Non-flying-ball engines
	public static PhysicsEngine getEulerSolver(boolean botMod, PuttingCourse course, double step, MainGame game) {
		return new EulerSolver(botMod, course, step, game);
	}
	public static PhysicsEngine getVelocityVerletSolver(boolean botMod, PuttingCourse course, double step, MainGame game) {
		return new VelocityVerletSolver(course, step, game);
	}
	public static PhysicsEngine getRungeKutta4Solver(boolean botMod, PuttingCourse course, double step, MainGame game) {
		return new RungeKutta4Solver(botMod, course, step, game);
	}

	//Flying-ball engines
	public static PhysicsEngine getVelocityVerletFlying(boolean botMod, PuttingCourse course, double step) {
		return new VelocityVerletFlying(course, step);
	}
	public static PhysicsEngine getRungeKuttaFlying(boolean botMod, PuttingCourse course, double step, MainGame game) {
		return new RungeKuttaFlying(course, step, game);
	}
}
