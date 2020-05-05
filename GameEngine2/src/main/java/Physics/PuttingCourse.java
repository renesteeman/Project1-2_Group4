package Physics;

import Entities.*;

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
    public Function2d height;
    //TODO convert 2D to 3D while loading and than ONLY use 3D
    public Vector2d startLocation2, goalLocation2;
    public Vector3d startLocation3, goalLocation3;
    public double frictionCoefficient, goalRadius, maxVelocity;

    public Ball ball;
    public Goal goal;
    public ArrayList<Tree> trees = new ArrayList<>();

    public final int TERRAIN_SIZE = 800;
    public final int DOMAIN_X = 800;
    public final int DOMAIN_Y = 800;

    public static void main(String[] args) {
        PuttingCourse course = new PuttingCourse("./res/courses/course0.txt");
        System.out.println(course);

        course.saveAsFile("course1");
    }

    public PuttingCourse(Function2d height, double frictionCoefficient, Vector2d startLocation, Vector2d goalLocation, double goalRadius, double maxVelocity) {
        this.height = height;
        this.frictionCoefficient = frictionCoefficient;
        this.startLocation2 = startLocation;
        this.goalLocation2 = goalLocation;
        this.goalRadius = goalRadius;
        this.maxVelocity = maxVelocity;

        //TODO is the use of new Vector2d() for the ball's velocity correct? 
        //YES, because initial velocity is always zero

        ball = new Ball(new Vector3d(startLocation2.x, height.evaluate(startLocation2), startLocation2.y), new Vector3d());
        goal = new Goal(new Vector3d(goalLocation2.x, height.evaluate(goalLocation2), goalLocation2.y));
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
            this.height = FunctionDeterminator.getFunction(cur);

            String[] curarray = inp.nextLine().split(" ");
            String s1 = curarray[2].replace("(", "").replace(",", ""), s2 = curarray[3].replace(")", "");
            this.goalLocation2 = new Vector2d(s1, s2);

            curarray = inp.nextLine().split(" ");
            s1 = curarray[2].replace("(", "").replace(",", "");
            s2 = curarray[3].replace(")", "");
            this.startLocation2 = new Vector2d(s1, s2);

            curarray = inp.nextLine().split(" ");
            this.frictionCoefficient = Double.parseDouble(curarray[2]);

            curarray = inp.nextLine().split(" ");
            this.maxVelocity = Double.parseDouble(curarray[2]);

            curarray = inp.nextLine().split(" ");
            this.goalRadius = Double.parseDouble(curarray[2]);

            //TODO is the use of new Vector2d() for the ball's velocity correct?
            ball = new Ball(new Vector3d(startLocation2.x, height.evaluate(startLocation2), startLocation2.y), new Vector3d());
            goal = new Goal(new Vector3d(goalLocation2.x, height.evaluate(goalLocation2), goalLocation2.y));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    
    public void setDefaultPositions() {
        ball.setPosition(new Vector3d(startLocation2.x, height.evaluate(startLocation2), startLocation2.y));
        goal.setPosition(new Vector3d(goalLocation2.x, height.evaluate(goalLocation2), goalLocation2.y)); 
    }

    public void saveAsFile(String name){
        try {
            checkDirectory();
            PrintWriter outp = new PrintWriter("./res/courses/" + name + ".txt", "UTF-8");
            outp.printf("height = %s\n", height.toString());
            outp.printf("flag = (%f, %f)\n", goalLocation2.x, goalLocation2.y);
            outp.printf("start = (%f, %f)\n", startLocation2.x, startLocation2.y);
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

    public boolean victoriousPosition2() {
        return ((Vector2d.subtract(ball.getPosition2(), goal.getPosition2())).length() <= getHoleRadius());
    }

    public boolean victoriousPosition3() {
        return ((Vector3d.subtract(ball.getPosition3(), goal.getPosition3())).length() <= getHoleRadius());
    }

    //Functions required by the API that we don't actually care about
    public void setHeight(Function2d height) {
        this.height = height;
    }

    public void setFlag(Vector2d flag) {
        this.goalLocation2 = flag;
    }

    public void setStart(Vector2d start) {
        this.startLocation2 = start;
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

    public Function2d getHeight() {
        return height;
    }

    public Vector2d getFlag() {
        return goalLocation2;
    }

    public Vector2d getStart() {
        return startLocation2;
    }

    public double getFriction() {
        return frictionCoefficient;
    }

    public double getMaxVelocity() {
        return maxVelocity;
    }

    public double getHoleRadius() {
        return goalRadius;
    }

    @Override 
    public String toString() {
        return String.format("(%s %f %s %s %f %f)", height, frictionCoefficient, startLocation2, goalLocation2, goalRadius, maxVelocity); 
    }
}
