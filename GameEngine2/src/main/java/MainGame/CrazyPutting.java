package MainGame;

import Physics.DetermineSolver;
import Physics.PuttingCourse;
import Physics.PuttingSimulator;
import Physics.Vector2d;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class CrazyPutting extends PuttingSimulator {
	protected boolean interactiveMod = true;

	protected String shotsFilename = "./res/shots/shots.txt";
	protected Scanner shotScn;

	protected Vector2d shotInput = new Vector2d();

	public CrazyPutting() {
		course = new PuttingCourse("./res/courses/course0.txt");
	}

	public void setInteractiveMod(boolean newMod) {
		interactiveMod = newMod;
	}

	@Override 
	public void paintComponent(Graphics g) {
		g.fillRect(0, 0, 800, 675);
		course.goal.render(g);
		course.ball.render(g);
		course.height.render(g);
	}

	public void game() throws FileNotFoundException, InputMismatchException {
		if (interactiveMod) {
			gameInteractiveMod();
		} else {
			gameTextMod();
		}
	}

	public void gameInteractiveMod() {
		while (collectShotData()) {
			System.out.println("shot started");
			takeShot(shotInput);
			System.out.println("shot ended");
			
			if (course.victoriousPosition3()) {
				System.out.println("You won");
				showWinText();
				break;
			}
		}
		System.out.println("the end");
	}

	//To be overridden in mainGame
	protected boolean collectShotData() {
		return true;
	}

	public void gameTextMod() throws FileNotFoundException, InputMismatchException {
		System.out.println("entered text mod");

		File f = new File(shotsFilename);
		shotScn = new Scanner(f);

		while (shotScn.hasNextLine()) {
			System.out.println("reading line");
			String[] cArray = shotScn.nextLine().split(" ");
			System.out.println(cArray[0] + " " + cArray[1]);
			shotInput = new Vector2d(cArray[0], cArray[1]);
			
			System.out.println("shot started");
			takeShot(shotInput);
			System.out.println("shot ended");
			
			if (course.victoriousPosition3()) {
				System.out.println("You won gg wp");
				break;
			}
		}
		System.out.println("the end");
	}

	@Override
	public void requestGraphicsUpdate() {
		repaint();
	}

	//To overwrite
	public void showWinText(){

	}
}
