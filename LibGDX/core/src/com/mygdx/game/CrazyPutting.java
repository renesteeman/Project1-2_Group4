package com.mygdx.game;

import java.util.*;
import java.lang.*;
import java.io.*;

public class CrazyPutting extends PuttingSimulator {
	public enum GameState {
		MENU,
		GAME_MOD1,
		GAME_MOD2
		// anything else?
	};

	protected GameState state = GameState.MENU;

	protected String shotsFilename = "shots.txt";
	protected Scanner shotsInp;

	public void game() throws FileNotFoundException, InputMismatchException {
		if (state == GameState.GAME_MOD2) {
			File f = new File(shotsFilename);
			shotsInp = new Scanner(f);
		}

		Vector2d shotInput = requestShotInput();
		while (shotInput != null && !victory) {
			take_shot(shotInput);
			shotInput = requestShotInput();
		}
	}

	// TO BE OVERRIDDEN
	public Vector2d requestShotInput() {
		if (state == GameState.GAME_MOD2) {
			if (!shotsInp.hasNextLine())
				return null;
			String[] curarray = shotsInp.nextLine().split(" ");
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