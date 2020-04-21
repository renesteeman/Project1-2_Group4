package Physics;

//Determine which engine to use
public class DetermineSolver {
	public static PhysicsEngine getEngine(PuttingCourse course) {
		return new EulerSolver(course);
	}
}
