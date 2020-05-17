package AI;

import Physics.*;
import MainGame.*;

import java.util.ArrayList;

public class Mastermind {
	PuttingSimulator simulator;
	ArrayList<Vector2d> solutionBruteForce = null, solutionNaive = null, solutionDFS = null;

	public Mastermind(boolean animated) {
		if (animated) {
			MainGame obj = new MainGame();
			obj.setUpModels();
			obj.resetPositions();
	        obj.addAxes();
	        obj.addTerrain();
	        obj.initLight();
	        obj.addWater();
	        obj.initRenders();
	        obj.initCamera();
	        obj.initControls();

	        //obj.runApp();

	        simulator = obj;
	    } else {
	    	PuttingCourse course = new PuttingCourse("./res/courses/course1.txt");
			EulerSolver engine = (EulerSolver)DetermineSolver.getEngine(course);
			simulator = new PuttingSimulator(course, engine);
	    }
	}

	public boolean findSolution(boolean flag1, boolean flag2, boolean flag3) {
		boolean result = false;
		if (flag1)
			result |= runBruteForce(); 
		if (flag2) 
			result |= runNaive();
		if (flag3)
			result |= runDFS();
		return result;
	}

	public boolean runBruteForce() {
		BruteForce bot = new BruteForce(simulator, 2, 1, 1);

		System.out.println("BruteForce initialized");
		System.out.println("starting search");

		bot.solve();

		solutionBruteForce = bot.solution;
		return (solutionBruteForce != null);
	}

	public boolean runNaive() {
		NaiveBot bot = new NaiveBot(simulator, 10, 360, 1);

		System.out.println("NaiveBot initialized");
		System.out.println("starting search");

		Vector2d ans = bot.solve();
		if (ans != null)
			solutionNaive.add(ans);
		return (solutionNaive != null);
	}

	public boolean runDFS() {
		//PuttingBotGraphDFS bot = new PuttingBotGraphDFS(simulator, new Vector2d(1.0, 1.0), 90, 1, 20, );
		return false;
	}

	public ArrayList<Vector2d> getSolution() {
		if (solutionBruteForce != null)
			return solutionBruteForce;
		if (solutionNaive != null)
			return solutionNaive;
		if (solutionDFS != null)
			return solutionDFS;
		return null;
	}

	public static void main(String[] args) {
		Mastermind obj = new Mastermind(true);
		
		//obj.simulator.putDelay(10);

		System.out.println("object of mastermind created");

		System.out.println("starting search...");

		boolean result = obj.findSolution(true, false, false);

		System.out.println("search ended");

		if (!result) {
			System.out.println("no solution found");
		} else {
			System.out.println(obj.getSolution());
		}
	}
}