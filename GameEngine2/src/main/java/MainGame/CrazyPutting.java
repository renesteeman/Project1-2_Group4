package MainGame;

import FontMeshCreator.GUIText;
import Physics.*;
import org.joml.Vector2f;

import java.util.*;
import java.lang.*;
import java.io.*;

import java.awt.Graphics;

import javax.swing.JFrame;

public class CrazyPutting extends PuttingSimulator {
	protected boolean interactiveMod = true;

	protected String shotsFilename = "./res/shots/shots.txt";
	protected Scanner shotScn;

	protected Vector2d shotInput = new Vector2d();

	public CrazyPutting() {
		course = new PuttingCourse("./res/courses/course0.txt");
		engine = DetermineSolver.getEulerSolver(course, 1e-2);
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

	public static void main(String[] args) {
		
		JFrame frame = new JFrame("golf");
		frame.setSize(800, 675);
  		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		CrazyPutting obj = new CrazyPutting();

		frame.getContentPane().add(obj);
  		frame.setVisible(true);

		try {
			obj.game();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	//To overwrite
	public void showWinText(){

	}
}
