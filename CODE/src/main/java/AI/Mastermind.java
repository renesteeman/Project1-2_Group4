package AI;

import Physics.*;
import MainGame.*;

import java.util.ArrayList;

public class Mastermind {
	PuttingSimulator simulator;
	ArrayList<Vector2d> solutionBruteForce = null, solutionNaive = null, solutionDFS = null, solutionAstar = null;

	public Mastermind(String courseFileName, int solverFlag, double graphicsRate, double physicsStep) {
		MainGame obj = new MainGame(true, courseFileName, solverFlag, graphicsRate, physicsStep);
		obj.setUpModels();
		obj.resetPositions();
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
        simulator.animated = false;
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

	public void destroyDisplay() {
		simulator.destroyDisplay();
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
		System.out.print("VECTOR2d. MAX DIFFERENCE    ");
		System.out.println(Vector2d.MAX_DIFFERENCE);
		PuttingBotGraphDFS bot = new PuttingBotGraphDFS(simulator, stepDegree, stepVelocityLength, (int)numberOfVelocitySteps);
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
		if (solutionAstar != null)
			return solutionAstar;
		return null;
	}

	public boolean runAstar(double stepDegree, double stepVelocityLength, double numberOfVelocitySteps) {
		Astar bot = new Astar(simulator, stepDegree, stepVelocityLength, (int)numberOfVelocitySteps);
		System.out.println("Astar bot initialized");
		System.out.println("starting search");

		bot.solve();
		if (bot.solved)
			solutionAstar = bot.solution;
		return (solutionAstar != null);
	}

	public void buildPath() {
		ArrayList<Vector2d> shots = getSolution();

		if (shots == null)
			return;

		simulator.setGraphicsUpdateRate(1);
		simulator.engine.setPhysicsStep(1e-1);

		simulator.animated = true;
		simulator.course.setDefaultPositions();

		System.out.println("taking shots...");

		for (int i = 0; i < shots.size(); i++) {
			Vector2d current_shot = shots.get(i);
			System.out.println(current_shot);
			simulator.takeShotWithoutError(current_shot);
		}
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

	public void startAstarBot(double stepDegree, double stepVelocityLength, double numberOfVelocitySteps) {
		System.out.println("object of mastermind created");

		System.out.println("starting search...");

		boolean result = runAstar(stepDegree, stepVelocityLength, (int)numberOfVelocitySteps);
		
		System.out.println("search ended");

		if (!result) {
			System.out.println("no solution found");
		} else {
			System.out.println(getSolution());
		}	
	}	
}

