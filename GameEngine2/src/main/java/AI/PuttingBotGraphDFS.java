package AI;

import Physics.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Collections;

public class PuttingBotGraphDFS {
	public PuttingSimulator simulator; 

	public Vector2d error;

	public boolean[][] used;
	public Vector2d[][] parent, step;

	public double stepDegree;
	public double stepVelocityLength;
	public int numberOfVelocitySteps;

	public boolean solved = false;
	public ArrayList<Vector2d> solution = new ArrayList<>(),
	                           current = new ArrayList<>();

	public Vector2d endNode = new Vector2d();

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
		parent = new Vector2d[used.length][used[0].length];
		step = new Vector2d[used.length][used[0].length];

		for (int i = 0; i < used.length; i++) {
			for (int j = 0; j < used[i].length; j++) {
				used[i][j] = false;
				parent[i][j] = new Vector2d(-1, -1);
				step[i][j] = new Vector2d(-1, -1);
			}
		}
	}

	public Vector2d getNode(Vector2d coords) {
		return new Vector2d((int)(coords.x / error.x),
                            (int)(coords.y / error.y));
	}

	//@Override
	public void solve() {
		System.out.println("bot started");
		Vector2d start = simulator.course.getStart();
		bfs(getNode(start), start);

		if (solved) {
			for (Vector2d absCurrent = endNode; ; ) {
				Vector2d current = getNode(absCurrent);
				if (!inBounds(current)) {
					break;
				}

				Vector2d absAncestor = parent[(int)current.x][(int)current.y];
				Vector2d ancestor = getNode(absAncestor);
				if (inBounds(ancestor)) {
					solution.add(step[(int)current.x][(int)current.y]);
					absCurrent = absAncestor;
				} else
					break;
			}
			Collections.reverse(solution);
		}
	}

	public void bfs(Vector2d start, Vector2d absStart) {
		if (!inBounds(start) || used[(int)start.x][(int)start.y])
			return;
		used[(int)start.x][(int)start.y] = true;

		LinkedList<Vector2d> queue = new LinkedList<>();
		queue.add(absStart);
		used[(int)start.x][(int)start.y] = true;

		while (queue.size() != 0) {
			Vector2d absPos = queue.getFirst();
			queue.remove();

			System.out.println(absPos);

			for (int angle = -180; angle < 180 && !solved; angle += stepDegree) {
				double x = Math.cos(Math.toRadians(angle));
				double y = Math.sin(Math.toRadians(angle));

				for (int j = 0; j < numberOfVelocitySteps && !solved; j++) {
					double nx = x * stepVelocityLength * (j + 1);
					double ny = y * stepVelocityLength * (j + 1);

					Vector2d velocity = new Vector2d(nx, ny);

					simulator.setBallPosition2(absPos);
					
					simulator.takeShot(velocity);
					current.add(velocity);

					Vector2d nxtAbsPos = simulator.getBallPosition2();
					Vector2d nxtPos = getNode(nxtAbsPos);
					if (inBounds(nxtPos) && !used[(int)nxtPos.x][(int)nxtPos.y]) {
						used[(int)nxtPos.x][(int)nxtPos.y] = true;
						parent[(int)nxtPos.x][(int)nxtPos.y] = absPos;
						step[(int)nxtPos.x][(int)nxtPos.y] = velocity;
						queue.add(nxtAbsPos);

						System.out.println("to " + nxtAbsPos);

						if (simulator.course.victoriousPosition3()) {
							solved = true;
							endNode = nxtAbsPos;
							return;
						}
					}

					simulator.setBallPosition2(absPos);
				}
			}
		}
	}	

	public void dfs(Vector2d v, Vector2d absPos) {
		if (!inBounds(v) || used[(int)v.x][(int)v.y])
			return;

		System.out.println(v.x + " " + v.y);
		used[(int)v.x][(int)v.y] = true;

		if (simulator.course.victoriousPosition3()) {
			solved = true;
			solution = (ArrayList<Vector2d>)current.clone();
			return;
		}

		for (int angle = -180; angle < 180 && !solved; angle += stepDegree) {
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
