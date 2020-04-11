public class PuttingBotGraphDFS {
	public PuttingSimulator simulator; //TODO default constructor

	public Vector2d error;//

	public boolean[][] used;

	public final double stepDegree;
	public final double stepVelocityLength;
	public final int numberOfVelocitySteps;

	public Vector2d goal;

	public boolean solved = false;

	public PuttingBotGraphDFS(PuttingSimulator simulator, Vector2d error) {
		this.simulator = simulator;
		this.error = error;
		init();
	}

	public void inti() {
		used = new boolean[simulator.course.getDomainX() / error.x][simulator.course.getDomainY() / error.y];
	}

	public Vector2d getNode(Vector2d coords) {
		double min_coordx = simulator.course.getMinimumX(),
		       min_coordy = simulator.course.getMinimumY();
		Vector2d node = new Vector2d((coords.x - min_coordx) / error.x,
			                         (coords.y - min_coordy) / error.y);
		return node;
	}

	public void solve() {
		Vector2d start = simulator.course.getStart();
		dfs(getNode(start), start);
	}

	public void dfs(Vecto2d v, Vector2d absPos) {
		if (!inBounds(v) || !used[v.x][v.y])
			return;

		if (v.x == goal.x && v.y == goal.y) {
			solved = true;
			return;
		}

		for (int i = 0; i < 180 / stepDegree && !solved; i++) {
			double x = 1 * Math.cos(Math.toRadians(i * stepDegree));
			double y = 1 * Math.sin(Math.toRatians(i * stepDegree));

			for (int j = 0; j < numberOfVelocitySteps && !solved; i++) {
				double nx = x * stepVelocityLength * (j + 1);
				double ny = y * stepVelocityLength * (j + 1);

				Vector2d velocity = new Vector2d(nx, ny);

				simulator.setBallPosition2(new Vector2d(absPos));
				simulator.setBallPosition3(new Vector3d(absPos));
				simulator.takeShot(absPos);
				nxtPos = simulator.getBallPosition2();
				dfs(getNode(nxtPos), nxtPos);
				simulator.setBallPosition2(new Vector2d(absPos));
				simulator.setBallPosition3(new Vector3d(absPos));
			}
		}
	}

	public boolean inBounds(Vecto2d v) {
		return 0 <= (int)v.x && (int)v.x <= user.length && 0 <= (int)v.y && (int)v.y <= used[0].length; 
	}
} 