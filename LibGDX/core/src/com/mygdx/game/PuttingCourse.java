package com.mygdx.game;

import com.mygdx.game.Function2d;

import java.util.*;
import java.lang.*;
import java.io.*;
import java.nio.file.*;

public class PuttingCourse implements Drawable {
	public Function2d height;
	//TODO why 2D?
	public Vector2d flag, start;
	public double friction, maxVelocity, holeRadius;

	public PuttingCourse(Function2d height, Vector2d flag, Vector2d start) {
		this.height = height;
		this.flag = flag;
		this.start = start;
	}

	public PuttingCourse(Function2d height, Vector2d flag, Vector2d start, double friction, double maxVelocity, double holeRadius) {
		this.height = height;
		this.flag = flag;
		this.start = start;
		this.friction = friction;
		this.maxVelocity = maxVelocity;
		this.holeRadius = holeRadius;
	}

	public PuttingCourse(String path) throws FileNotFoundException, InputMismatchException {
		File f = new File(path);
		Scanner inp = new Scanner(f);

		String cur = inp.nextLine();
		int id = -1, cnt = 0;
		for (int i = 0; i < cur.length(); i++) {
			if (cur.charAt(i) == ' ')
				cnt++;
			if (cnt == 2) {
				id = i;
				break;
			}
		}
		if (id == -1)
			throw new InputMismatchException();
		cur = cur.substring(id);
		height = new Function(cur);

		String[] curarray = inp.nextLine().split(" ");
		String s1 = curarray[2].replace("(", "").replace(",", ""), s2 = curarray[3].replace(")", "");
		flag = new Vector2d(new Double(s1), new Double(s2));

		curarray = inp.nextLine().split(" ");
		s1 = curarray[2].replace("(", "").replace(",", "");
		s2 = curarray[3].replace(")", "");
		start = new Vector2d(new Double(s1), new Double(s2));

		curarray = inp.nextLine().split(" ");
		friction = new Double(curarray[2]);

		curarray = inp.nextLine().split(" ");
		maxVelocity = new Double(curarray[2]);

		curarray = inp.nextLine().split(" ");
		holeRadius = new Double(curarray[2]);
	}

	public void writeCourse(String name) throws FileNotFoundException, UnsupportedEncodingException {
		PrintWriter outp = new PrintWriter("/courses/" + name + ".txt");
		outp.printf("height = %s\n", height.toString());
		outp.printf("flag = (%d, %d)\n", flag.x, flag.y);
		outp.printf("start = (%d, %d)\n", start.x, start.y);
		outp.printf("friction = %d\n", friction);
		outp.printf("vmax = %d\n", maxVelocity);
		outp.printf("tol = %d\n", holeRadius);		
		outp.close();	
	}

	public void checkDirectory() throws IOException {
        Path path = Paths.get("/courses");
        if (!Files.exists(path))
            Files.createDirectory(path);
	}

	public void setHeight(Function2d height) {
		this.height = height;
	}

	public void setFlag(Vector2d flag) {
		this.flag = flag;
	}

	public void setStart(Vector2d start) {
		this.start = start;
	}

	public void setFriction(double friction) {
		this.friction = friction;
	}

	public void setMaxVelocity(double maxVelocity) {
		this.maxVelocity = maxVelocity;
	}

	public void setHoleRadius(double holeRadius) {
		this.holeRadius = holeRadius;
	}
	
	public Function2d get_height() {
		return height;
	}
	public Vector2d get_flag_position() {
		return flag;
	}
	public Vector2d get_start_position() {
		return start;
	}
	public double get_friction_coefficient() {
		return friction;
	}
	public double get_maximum_velocity() {
		return maxVelocity;
	}
	public double get_hole_tolerance() {
		return holeRadius;
	}

	//TO BE OVERRIDDEN
	public void draw() {
		
	}
}