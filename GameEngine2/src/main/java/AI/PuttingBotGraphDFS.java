package AI;

import Physics.*;
import java.util.ArrayList;

public class PuttingBotGraphDFS {
	public PuttingSimulator simulator; 

	public Vector2d error;//

	public boolean[][] used;

	public double stepDegree;
	public double stepVelocityLength;
	public int numberOfVelocitySteps;

	public boolean solved = false;
	public ArrayList<Vector2d> solution = new ArrayList<>(),
	                           current = new ArrayList<>();

	public PuttingBotGraphDFS(PuttingSimulator simulator, Vector2d error, double stepDegree, double stepVelocityLength, int numberOfVelocitySteps) {
		this.simulator = simulator;
		this.error = error;
		this.stepDegree = stepDegree;
		this.stepVelocityLength = stepVelocityLength;
		this.numberOfVelocitySteps = numberOfVelocitySteps;
		init();
	}

	public void init() {
		used = new boolean[(int)(simulator.course.DOMAIN_X / error.x)][(int)(simulator.course.DOMAIN_Y / error.y)];
	}

	public Vector2d getNode(Vector2d coords) {
		return new Vector2d((int)(coords.x / error.x),
                            (int)(coords.y / error.y));
	}

	//@Override
	public void solve() {
		//Vector2d start = simulator.course.getStart();
		//dfs(getNode(start), start);
	}

	public void dfs(Vector2d v, Vector2d absPos) {
		if (!inBounds(v) || !used[(int)v.x][(int)v.y])
			return;

		if (simulator.course.victoriousPosition3()) {
			solved = true;
			solution = (ArrayList<Vector2d>)current.clone();
			return;
		}

		for (int angle = 0; angle < 360 && !solved; angle += stepDegree) {
			double x = Math.cos(Math.toRadians(angle));
			double y = Math.sin(Math.toRadians(angle));

			for (int j = 0; j < numberOfVelocitySteps && !solved; j++) {
				double nx = x * stepVelocityLength * (j + 1);
				double ny = y * stepVelocityLength * (j + 1);

				Vector2d velocity = new Vector2d(nx, ny);

				simulator.setBallPosition2(absPos);
				
				simulator.takeShot(velocity);
				current.add(velocity);

				Vector2d nxtPos = simulator.getBallPosition2();
				dfs(getNode(nxtPos), nxtPos);

				simulator.setBallPosition2(absPos);
				current.remove(current.size() - 1);
			}
		}
	}

	public boolean inBounds(Vector2d v) {
		return 0 <= (int)v.x && (int)v.x <= used.length && 0 <= (int)v.y && (int)v.y <= used[0].length; 
	}
} 
