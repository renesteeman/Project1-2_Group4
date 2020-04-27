package Physics;

import java.util.*;
import java.lang.*;
import java.io.*;

import java.awt.Graphics;

import javax.swing.JFrame;

public class CrazyPutting extends PuttingSimulator {
	protected String shotsFilename = "./res/shots/shots.txt";
	protected Scanner shotScn;

	protected Vector2d shotInput;

	public CrazyPutting() {
		course = new PuttingCourse("./res/courses/course0.txt");
		engine = DetermineSolver.getEngine(course);
	}

	@Override 
	public void paintComponent(Graphics g) {
		g.fillRect(0, 0, 800, 675);
		course.goal.render(g);
		course.ball.render(g);
		course.height.render(g);
	}

	public void game() throws FileNotFoundException, InputMismatchException {
		File f = new File(shotsFilename);
		shotScn = new Scanner(f);

		while (shotScn.hasNextLine()) {
			System.out.println("reading line");
			String[] curarray = shotScn.nextLine().split(" ");
			System.out.println(curarray[0] + " " + curarray[1]);
			shotInput = new Vector2d(curarray[0], curarray[1]);
			
			System.out.println("shot started");
			takeShot(shotInput);
			System.out.println("shot ended");
			
			if (victory) {
				System.out.println("You won gg wp");
				return;
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
}