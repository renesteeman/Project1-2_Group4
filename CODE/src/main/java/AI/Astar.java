package AI;

import Physics.*;
import java.util.*;
import javafx.util.*;

public class Astar {
	public PuttingSimulator simulator; 

	public Vector2d error;

	public boolean[][] used, forbidden;
	public Vector2d[][] parent, step;

	public double stepDegree;
	public double stepVelocityLength;
	public int numberOfVelocitySteps;

	public boolean solved = false;
	public ArrayList<Vector2d> solution = new ArrayList<>();

	public double[][] g, h, f;

	public Vector2d endNode;
	public Vector2d goalNode;

	private int oneDimError = 1;

	private VisualizeGrid grid = new VisualizeGrid();

	public Astar(PuttingSimulator simulator, double stepDegree, double stepVelocityLength, int numberOfVelocitySteps) {

		this.simulator = simulator;
		this.stepDegree = stepDegree;
		this.stepVelocityLength = stepVelocityLength;
		this.numberOfVelocitySteps = numberOfVelocitySteps;

		oneDimError = (int)simulator.course.getHoleRadius();
		used = new boolean[(int)(simulator.course.DOMAIN_X / oneDimError)][(int)(simulator.course.DOMAIN_Y / oneDimError)];
		parent = new Vector2d[used.length][used[0].length];
		step = new Vector2d[used.length][used[0].length];
		forbidden = new boolean[used.length][used[0].length];

		grid.build(used.length, used[0].length, oneDimError);

		for (int i = 0; i < used.length; i++) {
			for (int j = 0; j < used[i].length; j++) {
				used[i][j] = false;
				parent[i][j] = new Vector2d(-1, -1);
				step[i][j] = new Vector2d(-1, -1);
				forbidden[i][j] = false;
			}
		}

		g = new double[used.length][used[0].length];
		h = new double[used.length][used[0].length];
		f = new double[used.length][used[0].length];

		goalNode = getNode(simulator.course.getFlag());

		for (int i = 0; i < used.length; i++) {
			for (int j = 0; j < used[i].length; j++) {
				g[i][j] = 100000;
				h[i][j] = Math.max(Math.abs(i - goalNode.x), Math.abs(j - goalNode.y));
				f[i][j] = g[i][j] + h[i][j];
			}
		}

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
		astar(getNode(start), start);

		System.out.println("solution found");

		if (solved) {
			for (Vector2d absCurrent = endNode; ; ) {
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
			}
			Collections.reverse(solution);
		}
	}

	public class PairComparator implements Comparator<Pair<Double, Vector2d> > {
		public int compare(Pair<Double, Vector2d> p1, Pair<Double, Vector2d> p2) {
			if (p1.getKey() < p2.getKey())
				return -1;
			else if (p1.getKey() > p2.getKey())
				return 1;
			return 0;
		}
	}

	public void astar(Vector2d start, Vector2d absStart) {
		PriorityQueue<Pair<Double, Vector2d> > queue = new PriorityQueue<>(used.length * used[0].length, new PairComparator()); 

		g[(int)start.x][(int)start.y] = 0;
		f[(int)start.x][(int)start.y] = h[(int)start.x][(int)start.y];
		queue.add(new Pair<Double, Vector2d>(f[(int)start.x][(int)start.y], absStart));
		used[(int)start.x][(int)start.y] = true;
		grid.setPushed((int)start.x, (int)start.y);

		for (int i = 0; i < f.length; i++) {
			for (int j = 0; j < f[i].length; j++)
				System.out.print(f[i][j] + " ");
			System.out.println();
		}

		int breakRule = 0;
		while (queue.size() > 0 && breakRule == 0) {
			//breakRule++;

			Vector2d absNode = queue.poll().getValue();
			Vector2d node = getNode(absNode);

			System.out.println("in the node: " + node + " " + f[(int)node.x][(int)node.y]);

			if (node.x == goalNode.x && node.y == goalNode.y) {
				solved = true;
				endNode = absNode;
				return;
			}

			//System.out.println("used " + node.x + " " + node.y);
			grid.setUsed((int)node.x, (int)node.y);

			for (int angle = -180; angle < 180 && !solved; angle += stepDegree) {
				double x = Math.cos(Math.toRadians(angle));
				double y = Math.sin(Math.toRadians(angle));

				for (int j = 0; j < numberOfVelocitySteps && !solved; j++) {
					double nx = x * stepVelocityLength * (j + 1);
					double ny = y * stepVelocityLength * (j + 1);

					Vector2d velocity = new Vector2d(nx, ny);

					simulator.setBallPosition2(absNode);
					
					simulator.takeShotWithoutError(velocity);

					//System.out.println("velocity: " + velocity);
					
					Vector2d nxtAbsPos = simulator.getBallPosition2();
					Vector2d nxtPos = getNode(nxtAbsPos);

					//System.out.println(nxtPos);

					if (((Math.abs(nxtPos.x - node.x) > 1) || (Math.abs(nxtPos.y - node.y)) > 1))
						continue;

					//System.out.println("to" + nxtPos);

					if (!inBounds(nxtPos))
						continue;

					//System.out.println(used.length + " " + used[0].length + " survived bounds" + nxtPos);

					double tentativeScore = g[(int)node.x][(int)node.y] + 1;
					//int tentativeScore = g[(int)node.x][(int)node.y] + () + ();
					
					/*for (int p1 = 0; p1 < used.length; p1++) { 
						for (int p2 = 0; p2 < used[p1].length; p2++)
							System.out.print(used[p1][p2] + " ");
						System.out.println();
					}*/

					if (forbidden[(int)nxtPos.x][(int)nxtPos.y])
						continue;

					if (used[(int)nxtPos.x][(int)nxtPos.y] && tentativeScore >= g[(int)nxtPos.x][(int)nxtPos.y])
						continue;	

					System.out.println(tentativeScore + " " + g[(int)nxtPos.x][(int)nxtPos.y]);
					if (!used[(int)nxtPos.x][(int)nxtPos.y] || tentativeScore < g[(int)nxtPos.x][(int)nxtPos.y]) {
						used[(int)nxtPos.x][(int)nxtPos.y] = true;

						queue.remove(new Pair<Double, Vector2d>(f[(int)nxtPos.x][(int)nxtPos.y], nxtAbsPos));

						parent[(int)nxtPos.x][(int)nxtPos.y] = absNode;
						step[(int)nxtPos.x][(int)nxtPos.y] = velocity;
						g[(int)nxtPos.x][(int)nxtPos.y] = tentativeScore;

						f[(int)nxtPos.x][(int)nxtPos.y] = g[(int)nxtPos.x][(int)nxtPos.y] + h[(int)nxtPos.x][(int)nxtPos.y];
						
						queue.add(new Pair<Double, Vector2d>(f[(int)nxtPos.x][(int)nxtPos.y], nxtAbsPos));
						grid.setPushed((int)nxtPos.x, (int)nxtPos.y);

						System.out.println("pushed" + nxtPos + " " + f[(int)nxtPos.x][(int)nxtPos.y] + " " + h[(int)nxtPos.x][(int)nxtPos.y]);

						if (nxtPos.x == goalNode.x && nxtPos.y == goalNode.y) {
							solved = true;
							endNode = nxtAbsPos;
							return;
						}
					}
				}
			}
		}
	}	

	public boolean inBounds(Vector2d v) {
		return 0 <= (int)v.x && (int)v.x < used.length && 0 <= (int)v.y && (int)v.y < used[0].length; 
	}
} 
