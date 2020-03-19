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

	public void game() throws FileNotFoundException, InputMismatchException {
		if (state == GameState.GAME_MOD2) {
			File f = new File(shotsFilename);
			shotScn = new Scanner(f);
		}

		GameState initialGameState = state;
		shotInput = requestShotInput();
	}

	// TO BE CALLED
	void processShotRequest() {
		take_shot(shotInput);
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
	public void requestBallRepositioning() {

	}
}