package com.mygdx.game;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/*
This class keeps track of everything having to do with the course/map
 */
public class PuttingCourse {
    Function2d height;
    double frictionCoefficient;
    Vector2d startLocation;
    Vector2d goalLocation;
    double goalRadius;
    double maxVelocity;

    Ball ball;
    Goal goal;
    ArrayList<Tree> trees = new ArrayList<>();

    public PuttingCourse(Function2d height, double frictionCoefficient, Vector2d startLocation, Vector2d goalLocation, double goalRadius, double maxVelocity) {
        this.height = height;
        this.frictionCoefficient = frictionCoefficient;
        this.startLocation = startLocation;
        this.goalLocation = goalLocation;
        this.goalRadius = goalRadius;
        this.maxVelocity = maxVelocity;

        //TODO is the use of new Vector2d() for the ball's velocity correct?
        this.ball = new Ball(startLocation, new Vector2d());
        this.goal = new Goal(goalLocation);
    }

    //Loads course from file
    //TODO add support for trees and sand
    public PuttingCourse(String path){

        try {
            File file = new File(path);
            Scanner inp = new Scanner(file);

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
            this.height = new Function(cur);

            String[] curarray = inp.nextLine().split(" ");
            String s1 = curarray[2].replace("(", "").replace(",", ""), s2 = curarray[3].replace(")", "");
            this.goalLocation = new Vector2d(Double.valueOf(s1), Double.valueOf(s2));

            curarray = inp.nextLine().split(" ");
            s1 = curarray[2].replace("(", "").replace(",", "");
            s2 = curarray[3].replace(")", "");
            this.startLocation = new Vector2d(Double.valueOf(s1), Double.valueOf(s2));

            curarray = inp.nextLine().split(" ");
            this.frictionCoefficient = Double.valueOf(curarray[2]);

            curarray = inp.nextLine().split(" ");
            this.maxVelocity = Double.valueOf(curarray[2]);

            curarray = inp.nextLine().split(" ");
            this.goalRadius = Double.valueOf(curarray[2]);

            //TODO is the use of new Vector2d() for the ball's velocity correct?
            this.ball = new Ball(startLocation, new Vector2d());
            this.goal = new Goal(goalLocation);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public void saveAsFile(String name){
        try {
            checkDirectory();
            File f = new File("courses\\" + name + ".txt");
            PrintWriter outp = new PrintWriter("courses\\" + name + ".txt", "UTF-8");
            outp.printf("height = %s\n", height.toString());
            outp.printf("flag = (%f, %f)\n", goalLocation.x, goalLocation.y);
            outp.printf("start = (%f, %f)\n", startLocation.x, startLocation.y);
            outp.printf("friction = %f\n", frictionCoefficient);
            outp.printf("vmax = %f\n", maxVelocity);
            outp.printf("tol = %f\n", goalRadius);
            outp.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    //TODO write function so it takes GameObjects into account AND update the save and load file functions accordingly
    /*public double getHeight(double x, double y, double z){
        return
    }*/

    public void checkDirectory() throws IOException {
        Path path = Paths.get("courses");
        if (!Files.exists(path))
            Files.createDirectory(path);
    }

    //Functions required by the API that we don't actually care about
    public void setHeight(Function2d height) {
        this.height = height;
    }

    public void setFlag(Vector2d flag) {
        this.goalLocation = flag;
    }

    public void setStart(Vector2d start) {
        this.startLocation = start;
    }

    public void setFriction(double friction) {
        this.frictionCoefficient = friction;
    }

    public void setMaxVelocity(double maxVelocity) {
        this.maxVelocity = maxVelocity;
    }

    public void setHoleRadius(double holeRadius) {
        this.goalRadius = holeRadius;
    }

    public Function2d get_height() {
        return height;
    }

    public Vector2d get_flag_position() {
        return goalLocation;
    }

    public Vector2d get_start_position() {
        return startLocation;
    }

    public double get_friction_coefficient() {
        return frictionCoefficient;
    }

    public double get_maximum_velocity() {
        return maxVelocity;
    }

    public double get_hole_tolerance() {
        return goalRadius;
    }

}
