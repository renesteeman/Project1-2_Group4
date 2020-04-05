public class PhysicsEngineDeterminator {
	public static PhysicsEngine getEngine(PuttingCourse course) {
		return new EulerSolver(course);
	}
}