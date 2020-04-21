package game;

import java.util.ArrayList;
import java.util.Stack;
import java.awt.Graphics;
import java.awt.Color;

//TODO rename
public class Function extends Function2d {
    private String infix;
    private String postfix;
    public final int DL = -400, DR = 400;

    /**
     * Constructor of function. Infix is parsed into such a way every couple of elements (i.e., numbers, operators and functions (e.g., sin)) has spaces in between them.
     * @param infix equation in string form.
     */
    public Function(String infix) {
        this.infix = parseSpaces(infix);
        this.postfix = infixToPostfix(this.infix);
    } 

    @Override
    public void render(Graphics g) {
        g.setColor(Color.GREEN);
        for (int i = DL; i < DR; i++) {
            int val = (int)evaluate(new Vector2d(i, 0.0));
            g.fillOval(i + (-DL) - 3, 600 - val - 3, 6, 6);
        }
    }

    /**
     * @return equation in infix notation.
     */
    public String getInfix() { return this.infix; }

    /**
     * @return equation in postfix notation.
     */
    public String getPostfix() { return this.postfix; }

    /**
     * Transform function from infix notation to postfix notation.
     * @param infix is the equation we have to put in postfix notation.
     * @return equation in postfix notation.
     */
    public String infixToPostfix(String infix) {
        String[] split = infix.split(" ");

        StringBuilder sb = new StringBuilder();
        Stack<String> s = new Stack<>();

        int i = 0; //To keep track of how many tokens are used
        while(i < split.length) {
            String token = split[i];

            //If token is number or variable x,y or pi or e, then append to output with a space.
            if(isDouble(token) || token.equals("x") || token.equals("y") || token.equals("pi") || token.equals("e")) {
                sb.append(token).append(' ');
            }
            else if(isFunction(token)) { //Else if token is a function, e.g., sin, then push it onto the stack
                s.push(token);
            }
            else if(isOperator(token)) { //Else if token is an operator, then...
                // ... if the stack is not empty, then pop operators under certain conditions and at last push the token onto the stack.
                // Else token is immediately placed on stack.
                if(!s.isEmpty()) {
                    //System.out.println("Stack not empty...\nPrecedence: " + getPrecedence(token));
                    String peeked = s.peek();

                    //While( (at the top of the stack is a function (e.g., sin) OR the precedence of the element at the top of the stack is bigger than the token
                    // OR the precedence is equal (not including the power-sign "^") ) AND element at the top of the stack is not a left parenthesis
                    // AND the stack is not empty), then append operators to the output.
                    while ((isFunction(peeked)
                            || isGreaterPrecedence(token, peeked)
                            || isEqualPrecedence(token, peeked) )
                            && !peeked.equals("(")
                            && !s.isEmpty()) {
                        //System.out.println("pop element ...");
                        sb.append(s.pop()).append(' ');

                        //If stack is not empty, then update the value of peeked
                        if(!s.isEmpty()){
                            peeked = s.peek();
                        }
                    }
                    //System.out.println("Popped element(s) and stack size is: " + s.size() + "\n");
                }
                s.push(token);

            }
            else if(token.equals("(")) { //Else if token is left parenthesis, we push it onto the stack.
                //System.out.println("Pushing left parenthesis ...");
                s.push(token);
            }
            else if(token.equals(")")){ //Else if token is right parenthesis, then pop elements from stack until left parenthesis is on top.
                //System.out.println("Discarding left parenthesis ...");
                while (!s.peek().equals("(")) {
                    sb.append(s.pop()).append(' ');
                }
                s.pop();
            }
            i++;
        }

        //If there are no tokens left, the operator stack is completely popped and appended to the output
        while(!s.isEmpty()){
            sb.append(s.pop());
            //Only append a space if there is an element in the stack after one pop
            if(!s.isEmpty()){
                sb.append(' ');
            }
        }
        //System.out.println("\n" + sb.toString());
        return sb.toString();
    }

    /**
     * @param str a string of text
     * @return a positive integer if str is an operator, but -1 if it isn't an operator
     */
    private int getPrecedence(String str){
        if(str.equals("+") || str.equals("-")) {
            return 2;
        } else if(str.equals("*") || str.equals("/")) {
            return 3;
        } else if(str.equals("^")) {
            return 4;
        } else {
            return -1;
        }
    }

    /**
     * Checks if the precedence of str1 is equal to the precedence of str2, but they may not be the power-sign "^"
     * @param str1 a string of text
     * @param str2 a string of text
     * @return true if the precedence of str1 is the same as str2 AND str1 is not the power-sign "^"
     */
    private boolean isEqualPrecedence(String str1, String str2) {
        return getPrecedence(str1) == getPrecedence(str2) && !str1.equals("^");
    }

    /**
     * Checks if the precedence of str2 is bigger than the precedence of str1
     * @param str1 a string of text
     * @param str2 another string of text
     * @return precedence of str1 < precedence of str2
     */
    private boolean isGreaterPrecedence(String str1, String str2) {
        return getPrecedence(str1) < getPrecedence(str2);
    }

    /**
     * @param str a string of text
     * @return return true if str is a function, e.g., sin, else return false
     */
    private boolean isFunction(String str) {
        return (str.contains("sin") || str.contains("cos") || str.contains("tan") || str.contains("log") || str.contains("ln"));
    }

    /**
     * @param str a string of text
     * @return returns true if string is parsable to a double, else return false.
     */
    private boolean isDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * @param str a string of text
     * @return true if str is an operator, else return false
     */
    private boolean isOperator(String str){
        return str.equals("+") || str.equals("-") || str.equals("*") || str.equals("/") || str.equals("^");
    }


    private String parseSpaces(String s) {
        ArrayList<Character> cur = new ArrayList<>();
        ArrayList<String> spl = new ArrayList<>();
        ArrayList<Integer> types = new ArrayList<>();
        int flag = 4;

        /*
            flag :

            0 - space (not pushed in spl)
            1 - number
            2 - function
            3 - variable
            4 - opening bracket
            5 - closing bracket
            6 - primitive operations
            7 - pi 
        */

        //System.out.println(s);

        for (int i = 0; i < s.length(); i++) {
            int current_flag = 2;
            if (('0' <= s.charAt(i) && s.charAt(i) <= '9') || (flag == 4 && s.charAt(i) == '-') || (s.charAt(i) == '.'))
                current_flag = 1;
            else if (s.charAt(i) == ' ')
                current_flag = 0;
            else if (s.charAt(i) == 'x' || s.charAt(i) == 'y' || s.charAt(i) == 'e')
                current_flag = 3;
            else if (s.charAt(i) == '(')
                current_flag = 4;
            else if (s.charAt(i) == ')')
                current_flag = 5;
            else if (s.charAt(i) == '+' || s.charAt(i) == '-' || s.charAt(i) == '*' || s.charAt(i) == '/' || s.charAt(i) == '^')
                current_flag = 6;
            else if (s.charAt(i) == 'p')
                current_flag = 7;

            if (current_flag == flag && s.charAt(i) == '-') {
                // usually we consider minus a part of the number, the only exception is [number-] construction
                if (cur.size() != 0) {
                    StringBuilder builder = new StringBuilder(cur.size());
                    for (int j = 0; j < cur.size(); j++)
                        builder.append(cur.get(j));
                    spl.add(builder.toString());
                    types.add(flag);
                }
                cur.clear();
                cur.add(s.charAt(i));
                flag = 6;
            } else if (current_flag == flag) {
                if (current_flag != 0)
                    cur.add(s.charAt(i));
            } else {
                if (cur.size() != 0) {
                    StringBuilder builder = new StringBuilder(cur.size());
                    for (int j = 0; j < cur.size(); j++)
                        builder.append(cur.get(j));
                    spl.add(builder.toString());
                    types.add(flag);
                }
                cur.clear();
                if (current_flag != 0)
                    cur.add(s.charAt(i));
                if (current_flag == 7) {
                    cur.add('i');
                    i++;
                }
                flag = current_flag;
            }
        }
        if (cur.size() != 0) {
            StringBuilder builder = new StringBuilder(cur.size());
            for (int j = 0; j < cur.size(); j++)
                builder.append(cur.get(j));
            spl.add(builder.toString());
            types.add(flag);
        }

        //System.out.println(types.toString());

        ArrayList<Boolean> updated = new ArrayList<>();
        for (int i = 0; i < spl.size() - 1; i++) {
            int p1 = types.get(i), p2 = types.get(i + 1);
            if (p1 == 6 || p2 == 6)
                updated.add(false);
            else if ((p1 == 1 && p2 == 5) || (p1 == 1 && p2 == 1))
                updated.add(false);
            else if (p1 == 1)
                updated.add(true);
            else if (p1 == 2)
                updated.add(false);
            else if (p1 == 3 && p2 == 5)
                updated.add(false);
            else if (p1 == 3)
                updated.add(true);
            else if (p1 == 4)
                updated.add(false);
            else if (p1 == 5)
                updated.add(true);
        }

        //System.out.println(updated.toString());

        String res = "";
        for (int i = 0; i < spl.size(); i++) {
            res = res + spl.get(i);
            if (i != spl.size() - 1)
                res = res + " ";
            if (i != spl.size() - 1 && updated.get(i))
                res = res + "* ";
        }
        return res;
    }

    /**
     * Calculate solution of equation at the location of Vector2d with it's x- and y-value
     * @param p 2D location vector with x- and y-value
     * @return solution of equation
     */
    @Override
    public double evaluate(Vector2d p) {
        double xValue = p.x, yValue = p.y;
        String equation = this.postfix.replaceAll("x", Double.toString(xValue)).replaceAll("y", Double.toString(yValue));
        //System.out.println(equation);

        String[] split = equation.split("\\s");
        Stack<Double> s = new Stack<>();

        double sol = 0;
        int i = 0;

        //Go through the entire list (i.e., split[])
        while(i < split.length){
            //If split[i] is an operator, then pop two numbers from the stack, do a calculation
            // with the popped numbers and operator, and then push it back onto the stack.
            if(isOperator(split[i])){
                double e1 = s.pop();
                double e2 = s.pop();
                double e3;

                if(split[i].equals("+")) {
                    e3 = e2+e1;
                } else if(split[i].equals("-")) {
                    e3 = e2-e1;
                } else if(split[i].equals("*")) {
                    e3 = e2*e1;
                } else if(split[i].equals("/")) {
                    e3 = e2/e1;
                } else {
                    e3 = Math.pow(e2, e1);
                }
                s.push(e3);
                i++;
            }
            else if(isFunction(split[i])){ //Else if split[i] is a function, then we only pop one number.
                double e1 = s.pop();
                double e2;

                if(split[i].equals("sin")) {
                    e2 = Math.sin(e1);
                } else if(split[i].equals("cos")) {
                    e2 = Math.cos(e1);
                } else if(split[i].equals("tan")) {
                    e2 = Math.tan(e1);
                } else if(split[i].equals("log")) {
                    e2 = Math.log10(e1);
                } else { //else split[i].equals("ln")
                    e2 = Math.log(e1);
                }
                s.push(e2);
                i++;
            }
            else {
                if(split[i].equals("e")){
                    s.push(Math.E);
                } else if(split[i].equals("pi")) {
                    s.push(Math.PI);
                } else if(isDouble(split[i])) {
                    s.push(Double.parseDouble(split[i]));
                }
                i++;
            }

            //If i == split.length, the calculations are done.
            if(i == split.length) {
                sol = s.pop();
            }
        }
        return sol;
    }

    public final double DELTA_VALUE = 1e-9; // NEED TO PLAY WITH THIS VALUE
    public final double REVERSE_DELTA_VALUE = 1e9;

    @Override
    public Vector2d gradient(Vector2d p) {
        double val1 = evaluate(new Vector2d(p.x + DELTA_VALUE, p.y));
        double val2 = evaluate(new Vector2d(p.x, p.y + DELTA_VALUE));
        double val3 = evaluate(p);

        double p1 = (val1 - val3) * REVERSE_DELTA_VALUE;
        double p2 = (val2 - val3) * REVERSE_DELTA_VALUE;
        return new Vector2d(p1, p2);
    }

    @Override
    public String toString() {
        return infix;
    }

    public static void main(String[] args) {
        //String function = "-1*pi + 3pi + x * 2 / (y - 5)^2 ^ 3";
        String function = "cos(pi) + 2";
        Function example = new Function(function);

        System.out.printf("function:%s%n", function);
        System.out.printf("infix:   %s%n", example.getInfix());
        System.out.printf("postfix: %s%n", example.getPostfix());

        int x = 5; int y = 4;
        //System.out.println(example.getInfix()+" with x = "+x+" and y = "+y+" is: " +  example.evaluate(new Vector2d(x, y)));
        System.out.println(example.evaluate(new Vector2d(x, y)));
        System.out.println(example.gradient(new Vector2d(x, y)).x);
        System.out.println(example.gradient(new Vector2d(x, y)).y); 
    }
}
