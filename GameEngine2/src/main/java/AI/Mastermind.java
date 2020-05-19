package AI;

import Physics.*;
import MainGame.*;

import java.util.ArrayList;

public class Mastermind {
	PuttingSimulator simulator;
	ArrayList<Vector2d> solutionBruteForce = null, solutionNaive = null, solutionDFS = null;

	public Mastermind(boolean animated, String courseFileName, int solverFlag, double graphicsRate, double physicsStep) {
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
		/*if (flag1)
			result |= runBruteForce(); 
		if (flag2) 
			result |= runNaive();
		if (flag3)
			result |= runDFS();
		*/return result;
	}

	public boolean runBruteForce() {
		BruteForce bot = new BruteForce(simulator, 2, 1, 1);

		System.out.println("BruteForce initialized");
		System.out.println("starting search");

		bot.solve();

		solutionBruteForce = bot.solution;
		return (solutionBruteForce != null);
	}

	public boolean runNaive(double angleStep, double angleRange, double velocityStep) {
		NaiveBot bot = new NaiveBot(simulator, angleStep, angleRange, velocityStep);

		System.out.println("NaiveBot initialized");
		System.out.println("starting search");

		Vector2d ans = bot.solve();
		if (ans != null)
			solutionNaive.add(ans);
		return (solutionNaive != null);
	}

	public boolean runDFS(double stepDegree, double stepVelocityLength, double numberOfVelocitySteps) {
		PuttingBotGraphDFS bot = new PuttingBotGraphDFS(simulator, new Vector2d(1.0, 1.0), stepDegree, stepVelocityLength, (int)numberOfVelocitySteps);
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

	public void startNaiveBot(double angleStep, double angleRange, double velocityStep) {
		System.out.println("object of mastermind created");

		System.out.println("starting search...");

		boolean result = runNaive(angleStep, angleRange, velocityStep);
		
		System.out.println("search ended");

		if (!result) {
			System.out.println("no solution found");
		} else {
			System.out.println(getSolution());
		}	
	}

	public void startBFSBot(double stepDegree, double stepVelocityLength, double numberOfVelocitySteps) {
		System.out.println("object of mastermind created");

		System.out.println("starting search...");

		boolean result = runDFS(stepDegree, stepVelocityLength, (int)numberOfVelocitySteps);
		
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

		Mastermind obj = new Mastermind(true, "./res/courses/course1.txt", 2, 1e-1, 1e-2);
		boolean result = obj.findSolution(false, false, true);

		System.out.println("search ended");

		if (!result) {
			System.out.println("no solution found");
		} else {
			System.out.println(obj.getSolution());
		}	
	}
}