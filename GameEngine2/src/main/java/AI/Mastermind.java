package AI;

import Physics.*;
import MainGame.*;

import java.util.ArrayList;

public class Mastermind {
	PuttingSimulator simulator;
	ArrayList<Vector2d> solutionBruteForce = null, solutionNaive = null, solutionDFS = null;

	boolean botFlag = false;

	public Mastermind(boolean botFlag, boolean animated, String courseFileName, int solverFlag, double graphicsRate, double physicsStep) {
		if (animated) {
			MainGame obj = new MainGame(courseFileName, solverFlag, graphicsRate, physicsStep);
			obj.setUpModels();
			obj.resetPositions();
	        obj.addAxes();
	        obj.addTerrain();
	        obj.initLight();
	        obj.addWater();
	        obj.initRenders();
	        obj.initCamera();
	        obj.initControls();
	        obj.setInteractiveMod(false);

	        obj.requestGraphicsUpdate();

	        this.botFlag = botFlag;

	        /*try {
	            obj.game();
	        } catch (Exception e) {
	            e.printStackTrace();
	            System.exit(0);
	        }*/

	        //obj.runApp();

	        simulator = obj;
	    } else {
	    	// not supported yet
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
		NaiveBot bot = new NaiveBot(simulator, 10, 360, 10);

		System.out.println("NaiveBot initialized");
		System.out.println("starting search");

		Vector2d ans = bot.solve();
		if (ans != null)
			solutionNaive.add(ans);
		return (solutionNaive != null);
	}

	public boolean runDFS() {
		PuttingBotGraphDFS bot = new PuttingBotGraphDFS(simulator, new Vector2d(1.0, 1.0), 10, 5, 4);
		System.out.println("DFS bot initialized");
		System.out.println("starting search");

		bot.solve();
		if (bot.solved)
			solutionDFS = bot.solution;
		return (solutionDFS != null);
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

	public void start() {
		//obj.simulator.putDelay(10);

		System.out.println("object of mastermind created");

		System.out.println("starting search...");

		boolean result = false;
		if (botFlag)
			result = findSolution(false, true, false);
		else
			result = findSolution(false, false, true);

		System.out.println("search ended");

		if (!result) {
			System.out.println("no solution found");
		} else {
			System.out.println(getSolution());
		}
	}

	public static void main(String[] args) {
		System.out.println("object of mastermind created");

		System.out.println("starting search...");

		Mastermind obj = new Mastermind(false, true, "./res/courses/course1.txt", 2, 1e-1, 1e-2);
		boolean result = obj.findSolution(false, false, true);

		System.out.println("search ended");

		if (!result) {
			System.out.println("no solution found");
		} else {
			System.out.println(obj.getSolution());
		}	
	}
}