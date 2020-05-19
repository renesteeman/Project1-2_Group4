package Physics;

//Determine which engine to use
public class DetermineSolver {
	public static PhysicsEngine getEulerSolver(PuttingCourse course) {
		return new EulerSolver(course);
	}
	public static PhysicsEngine getVerletSolver(PuttingCourse course) {
		return new VerletSolver(course);
	}	
	public static PhysicsEngine getVelocityVerletSolver(PuttingCourse course) {
		return new VelocityVerletSolver(course);
	}
	public static PhysicsEngine getRungeKutta4Solver(PuttingCourse course) {
		return new RungeKutta4Solver(course);
	}
}
