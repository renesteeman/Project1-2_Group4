package com.mygdx.game;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.InputMismatchException;
import java.util.Scanner;

//TODO rewrite hole file and make clear what it does
public class InputOutputModule {
    private Function2d height;
    private Vector2d start;
    private Vector2d flag;
    private double friction, maxVelocity, holeRadius;

    public InputOutputModule(String path) {
        try {
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
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public void writeCourse(String name) {
        try {
            checkDirectory();
            File f = new File("courses\\" + name + ".txt");
            PrintWriter outp = new PrintWriter("courses\\" + name + ".txt", "UTF-8");
            outp.printf("height = %s\n", height.toString());
            outp.printf("flag = (%f, %f)\n", flag.x, flag.y);
            outp.printf("start = (%f, %f)\n", start.x, start.y);
            outp.printf("friction = %f\n", friction);
            outp.printf("vmax = %f\n", maxVelocity);
            outp.printf("tol = %f\n", holeRadius);
            outp.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public void checkDirectory() throws IOException {
        Path path = Paths.get("courses");
        if (!Files.exists(path))
            Files.createDirectory(path);
    }

}