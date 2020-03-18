import java.util.ArrayList;
import java.util.Stack;

public class Function implements Function2d {
    private final String OPERATORS = "-+/*^"; //primitive operators
    private String infix;
    private String postfix;

    public Function(String infix) {
        this.infix = parseSpaces(infix);
        this.postfix = infixToPostfix(this.infix);
    }

    public String getInfix() {
        return this.infix;
    }

    public String getPostfix() {
        return this.postfix;
    }

    //Source used: https://rosettacode.org/wiki/Parsing/Shunting-yard_algorithm#Java
    public String infixToPostfix(String infix) {
        /* To find out the precedence, we take the index of the
           token in the ops string and divide by 2 (rounding down). 
           This will give us: 0, 0, 1, 1, 2 */

        StringBuilder sb = new StringBuilder();
        Stack<Integer> s = new Stack<>();

        for (String token : infix.split("\\s")) {
            if (token.isEmpty()) {
                continue;
            }
            char c = token.charAt(0);
            int idx = OPERATORS.indexOf(c);

            // check for operator
            if (idx != -1) {
                if (s.isEmpty()) {
                    s.push(idx);
                }
                else {
                    while (!s.isEmpty()) {
                        int prec2 = s.peek() / 2;
                        int prec1 = idx / 2;

                        if (prec2 > prec1 || (prec2 == prec1 && c != '^')) {
                            sb.append(OPERATORS.charAt(s.pop())).append(' ');
                        }
                        else {
                            break;
                        }
                    }
                    s.push(idx);
                }
            }
            else if (c == '(') {
                s.push(-2); // -2 stands for '('
            }
            else if (c == ')') {
                // until '(' on stack, pop operators.
                while (s.peek() != -2) {
                    sb.append(OPERATORS.charAt(s.pop())).append(' ');
                }
                s.pop();
            }
            else {
                sb.append(token).append(' ');
            }
        }
        while (!s.isEmpty()) {
            sb.append(OPERATORS.charAt(s.pop())).append(' ');
        }

        return sb.toString();
    }

    private String parseSpaces(String s) {
        ArrayList<Character> cur = new ArrayList<>();
        ArrayList<String> spl = new ArrayList<>();
        ArrayList<Integer> types = new ArrayList<>();
        int flag = 0;

        /*
            flag :

            0 - space (not pushed in spl)
            1 - number
            2 - function
            3 - variable
            4 - opening bracket
            5 - closing bracket
            6 - primitive operations
        */

        //System.out.println(s);

        for (int i = 0; i < s.length(); i++) {
            int current_flag = 2;
            if (('0' <= s.charAt(i) && s.charAt(i) <= '9') || (s.charAt(i) == '-') || (s.charAt(i) == '.'))
                current_flag = 1;
            else if (s.charAt(i) == ' ')
                current_flag = 0;
            else if (s.charAt(i) == 'x' || s.charAt(i) == 'y')
                current_flag = 3;
            else if (s.charAt(i) == '(')
                current_flag = 4;
            else if (s.charAt(i) == ')')
                current_flag = 5;
            else if (s.charAt(i) == '+' || s.charAt(i) == '-' || s.charAt(i) == '*' || s.charAt(i) == '/' || s.charAt(i) == '^')
                current_flag = 6;

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

    @Override
    public double evaluate(Vector2d p) {
        double xValue = p.x, yValue = p.y;
        String equation = this.postfix.replaceAll("x", Double.toString(xValue)).replaceAll("y", Double.toString(yValue));
        //System.out.println(equation);

        String[] split = equation.split("\\s");
        Stack<Double> s = new Stack<>();
        double sol = 0;
        int i = 0;

        while(i < split.length){
            //If first character of split[i] is primitive operator AND split[i] is 1 character long,
            // then it is a primitive operator. Thus, we can perform an action, e.g., multiplication.
            //Since equation is in postfix notation, the first place an operator can be is at i=2.
            if(OPERATORS.indexOf(split[i].charAt(0)) != -1 && split[i].length() == 1){
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

                //If i == split.length, the calculations are done.
                if(i == split.length) {
                    sol = s.pop();
                }
            }
            else {
                s.push(Double.parseDouble(split[i]));
                i++;
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
        Function example = new Function("3 + x * 2 / ( y - 5 ) ^ 2 ^ 3");
        System.out.printf("infix:   %s%n", example.getInfix());
        System.out.printf("postfix: %s%n", example.getPostfix());

        int x = 5; int y = 4;
        System.out.println(example.getInfix()+" with x = "+x+" and y = "+y+" is: " +  example.evaluate(new Vector2d(x, y)));
    }
}