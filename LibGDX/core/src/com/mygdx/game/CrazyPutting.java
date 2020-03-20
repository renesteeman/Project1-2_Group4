package com.mygdx.game;

import java.util.*;
import java.lang.*;
import java.io.*;

public class CrazyPutting extends PuttingSimulator {
	public enum GameState {
		MENU,
		GAME_MOD1,
		GAME_MOD2
	};

	protected GameState state = GameState.MENU;

	protected String shotsFilename = "shots.txt";
	protected Scanner shotScn;

	protected Vector2d shotInput;

	public CrazyPutting() {
		//TODO Fix
		this.course = new PuttingCourse("C:\\Users\\steem\\Desktop\\Study\\Project 1-2\\LibGDX\\core\\src\\com\\mygdx\\game\\courses\\course0.txt");
		this.engine = new EulerSolver(course.get_height(), course);
		height = course.get_height();
		ball = new Ball(course.get_start_position(), new Vector2d()); // TO BE REPLACED
		goal = new Goal(course.get_flag_position()); // TO BE REPLACED
	}

	public void game() throws FileNotFoundException, InputMismatchException {
		//TODO fix this
		state = GameState.GAME_MOD2;

		if (state == GameState.GAME_MOD2) {
			File f = new File(shotsFilename);
			shotScn = new Scanner(f);
		}

		GameState initialGameState = state;
		shotInput = requestShotInput();
	}

	// TO BE CALLED
	void processShotRequest() {
		takeShot(shotInput);
		if (victory) {
			System.out.println("You won gg wp");
			return;
		}
		requestShotInput();
	}

	// TO BE OVERRIDDEN
	public Vector2d requestShotInput() {
		if (state == GameState.GAME_MOD2) {
			if (!shotScn.hasNextLine())
				return null;
			String[] curarray = shotScn.nextLine().split(" ");
			Vector2d ans = new Vector2d(new Double(curarray[0]), new Double(curarray[1]));
			return ans;
		}
		return null;
	}

	// TO BE OVERRIDDEN
	@Override
	public void requestGraphicsUpdate() {

	}

	// TO BE OVERRIDDEN
	@Override
	public void requestBallRepositioning() {

	}
}