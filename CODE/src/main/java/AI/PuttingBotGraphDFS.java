package AI;

import Physics.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Collections;

public class PuttingBotGraphDFS {
	public PuttingSimulator simulator; 

	private int oneDimError = 1;

	public boolean[][] used;
	public Vector2d[][] parent, step;

	public double stepDegree;
	public double stepVelocityLength;
	public int numberOfVelocitySteps;

	public boolean solved = false;
	public ArrayList<Vector2d> solution = new ArrayList<>();

	public Vector2d endNode = new Vector2d();
	public Vector2d goalNode = new Vector2d();

	private VisualizeGrid grid = new VisualizeGrid();

	public PuttingBotGraphDFS(PuttingSimulator simulator, double stepDegree, double stepVelocityLength, int numberOfVelocitySteps) {
		this.simulator = simulator;
		oneDimError = 10;//simulator.course.getHoleRadius();
		System.out.print("HOLE RADIUS ");
		System.out.println(oneDimError);
		this.stepDegree = stepDegree;
		this.stepVelocityLength = stepVelocityLength;
		this.numberOfVelocitySteps = numberOfVelocitySteps;

		used = new boolean[(int)(simulator.course.DOMAIN_X / oneDimError)][(int)(simulator.course.DOMAIN_Y / oneDimError)];
		parent = new Vector2d[used.length][used[0].length];
		step = new Vector2d[used.length][used[0].length];

		System.out.println(used.length + " " + used[0].length);

		for (int i = 0; i < used.length; i++) {
			for (int j = 0; j < used[i].length; j++) {
				used[i][j] = false;
				parent[i][j] = new Vector2d(-1, -1);
				step[i][j] = new Vector2d(-1, -1);
			}
		}

		goalNode = getNode(simulator.course.getFlag());

		grid.build(used.length, used[0].length, oneDimError);
		Vector2d ballPosition = getNode(simulator.getBallPosition2());
		grid.setBall((int)ballPosition.x, (int)ballPosition.y);
		Vector2d goalPosition = getNode(simulator.course.getFlag());
		grid.setGoal((int)goalPosition.x, (int)goalPosition.y);
	}

	public Vector2d getNode(Vector2d coords) {
		return new Vector2d((int)coords.x / oneDimError,
                            (int)coords.y / oneDimError);
	}

	//@Override
	public void solve() {
		System.out.println("bot started");
		Vector2d start = simulator.course.getStart();
		bfs(getNode(start), start);

		System.out.println("solution found, retrieving the path");
		if (solved) {
			int kek = 0;
			int counter = 0;
			for (Vector2d absCurrent = endNode; ; counter++) {
				Vector2d current = getNode(absCurrent);
				if (!inBounds(current)) {
					break;
				}

				System.out.println(current);

				Vector2d absAncestor = parent[(int)current.x][(int)current.y];
				
				if (absAncestor.x < 0)
					break;

				Vector2d ancestor = getNode(absAncestor);
				if (inBounds(ancestor)) {
					solution.add(step[(int)current.x][(int)current.y]);
					absCurrent = absAncestor;
				} else
					break;
				if (counter == 100) break;
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
		
		grid.setPushed((int)start.x, (int)start.y);

		while (queue.size() != 0) {
			Vector2d absPos = queue.getFirst();
			Vector2d pos = getNode(absPos);
			grid.setUsed((int)pos.x, (int)pos.y);
			queue.remove();

			if ((int)pos.x == goalNode.x && (int)pos.y == goalNode.y) {
				solved = true;
				endNode = absPos;
				return;
			}

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

					Vector2d nxtAbsPos = simulator.getBallPosition2();
					Vector2d nxtPos = getNode(nxtAbsPos);
					if (inBounds(nxtPos) && !used[(int)nxtPos.x][(int)nxtPos.y]) {
						used[(int)nxtPos.x][(int)nxtPos.y] = true;
						parent[(int)nxtPos.x][(int)nxtPos.y] = absPos;
						step[(int)nxtPos.x][(int)nxtPos.y] = velocity;
						queue.add(nxtAbsPos);
						grid.setPushed((int)nxtPos.x, (int)nxtPos.y);

						System.out.println("to " + nxtAbsPos);

						if ((int)goalNode.x == (int)nxtPos.x && (int)goalNode.y == (int)nxtPos.y) {
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

	public boolean inBounds(Vector2d v) {
		return 0 <= (int)v.x && (int)v.x < used.length && 0 <= (int)v.y && (int)v.y < used[0].length; 
	}
} 
