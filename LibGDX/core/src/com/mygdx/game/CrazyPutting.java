package com.mygdx.game;

import java.util.*;
import java.lang.*;
import java.io.*;


//TODO redo and make it clearer what this file does
public class CrazyPutting extends PuttingSimulator {
	public enum GameState {
		MENU,
		GAME_MOD1,
		GAME_MOD2
	};

	protected GameState state = GameState.MENU;

	//protected String shotsFilename = "C:\\Users\\steem\\Desktop\\Study\\Project 1-2\\LibGDX\\core\\src\\com\\mygdx\\game\\shots.txt";
	protected String shotsFilename = "shots.txt";
	protected Scanner shotScn;

	protected Vector2d shotInput;

	public CrazyPutting() {
		//this.course = new PuttingCourse("C:\\Users\\steem\\Desktop\\Study\\Project 1-2\\LibGDX\\core\\src\\com\\mygdx\\game\\courses\\course0.txt");
		this.course = new PuttingCourse("courses\\course0.txt");
		this.engine = new EulerSolver(course.get_height(), course);
		height = course.get_height();
		ball = new Ball(course.get_start_position(), new Vector2d()); // TO BE REPLACED
		goal = new Goal(course.get_flag_position()); // TO BE REPLACED
	}

	//TODO rewrite
	public void game() throws FileNotFoundException, InputMismatchException {
		//TODO fix this
		state = GameState.GAME_MOD2;

		if (state == GameState.GAME_MOD2) {
			File f = new File(shotsFilename);
			shotScn = new Scanner(f);
		}

		GameState initialGameState = state;

		while (state == GameState.GAME_MOD2 && shotScn.hasNextLine()) {
			String[] curarray = shotScn.nextLine().split(" ");
			System.out.println(curarray[0] + " " + curarray[1]);
			shotInput = new Vector2d(new Double(curarray[0]), new Double(curarray[1]));
			System.out.println("shot started");
			take_shot(shotInput);
			System.out.println("shot ended");
			if (victory) {
				System.out.println("You won gg wp");
				return;
			}
		}
	}

	//TODO rewrite or remove
	// TO BE CALLED
	void processShotRequest() {
		//takeShot(shotInput);
		if (victory) {
			System.out.println("You won gg wp");
			return;
		}
		requestShotInput();
	}

	//TODO rewrite or remove
	// TO BE OVERRIDDEN
	public Vector2d requestShotInput() {
		if (state == GameState.GAME_MOD2) {
			if (!shotScn.hasNextLine())
				return null;
			String[] curarray = shotScn.nextLine().split(" ");
			System.out.println(curarray[0] + " " + curarray[1]);
			Vector2d ans = new Vector2d(new Double(curarray[0]), new Double(curarray[1]));
			return ans;
		}
		return null;
	}

	//TODO rewrite or remove
	// TO BE OVERRIDDEN
	public void requestBallRepositioning() {

	}

	public static void main(String[] args) {
		CrazyPutting obj = new CrazyPutting();
		try {
			obj.game();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
}