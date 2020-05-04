package Physics;

/**
 * The RungeKutta4Solver class updates position and velocity of a ball after every time-step.
 *
 * The position and velocity can be updated by the process()-method.
 * This is done by the Classical Fourth-Order Runge-Kutta method in a second-order system.
 */
public class RungeKutta4Solver implements PhysicsEngine{
    private double step = 1e-4; //TODO RANDOM VALUE, NEED TO ASSESS IT FURTHER ACCORDING TO THE INPUT
    private PuttingCourse course;

    public final double __G = 9.81; //TODO allow people to enter their preferred G value


    public RungeKutta4Solver(PuttingCourse course){
        this.course = course;
    }

    @Override
    public boolean passedFlag() {
        return false;
    }

    /**
     * Processes the shot of a ball over time using the Classical 4th-order Runge-Kutta method
     *      p = position;   k is the derivative of p
     *      v = velocity;   l is the derivative of v
     *
     * Source used: https://cg.informatik.uni-freiburg.de/course_notes/sim_02_particles.pdf (page/slide 29)
     * @param dtime TODO add description
     */
    @Override
    public void process(double dtime) {
        Vector2d p = this.course.ball.getPosition2();
        Vector2d v = this.course.ball.getVelocity2D();


        //TODO make code shorter, e.g. k1 = new Vector2d(v.x,v.y)
        //TODO Maybe update for loop to while loop, using the hole or e.g. v<0.1 as stopping condition?
        //Formulas: lx = x" = -g * h,x(x,y) - mu * g * x'/sqrt(x'^2 + y'^2) = -g * (h,x(x,y) + mu * x'/sqrt(x'^2 + y'^2)
        //          ly = y" = -g * h,y(x,y) - mu * g * y'/sqrt(x'^2 + y'^2) = -g * (h,y(x,y) + mu * y'/sqrt(x'^2 + y'^2)
        for(double timer = 0; timer < dtime; timer += step){
            //FIRST STEP
            Vector2d gradient1 = course.height.gradient(p); //Gradient at step 1

            double k1x = v.x; //p' = f(t,x); velocity in x-direction
            double k1y = v.y; //p' = f(t,y); velocity in y-direction
            Vector2d k1 = new Vector2d(k1x, k1y); //p' = velocity vector

            double l1x = -__G * (gradient1.x + course.getFriction() * k1.x / k1.length()); //v' = g(t,x); acceleration in x-direction
            double l1y = -__G * (gradient1.y + course.getFriction() * k1.y / k1.length()); //v' = g(t,y); acceleration in y-direction
            Vector2d l1 = new Vector2d(l1x, l1y); //v' = acceleration vector


            //SECOND STEP
            double p2x = p.x + k1.x / 2.0 * step;
            double p2y = p.y + k1.y / 2.0 * step;
            Vector2d p2 = new Vector2d(p2x, p2y); //Intermediate position vector after step 1 to calculate gradient
            Vector2d gradient2 = course.height.gradient(p2);

            double k2x = v.x + l1.x / 2.0 * step;
            double k2y = v.y + l1.y / 2.0 * step;
            Vector2d k2 = new Vector2d(k2x, k2y);

            double l2x = -__G * (gradient2.x + course.getFriction() * k2.x / k2.length());
            double l2y = -__G * (gradient2.y + course.getFriction() * k2.y / k2.length());
            Vector2d l2 = new Vector2d(l2x, l2y);


            //THIRD STEP
            double p3x = p.x + k2.x / 2.0 * step;
            double p3y = p.y + k2.y / 2.0 * step;
            Vector2d p3 = new Vector2d(p3x, p3y); //Intermediate position vector after step 2 to calculate gradient
            Vector2d gradient3 = course.height.gradient(p3);

            double k3x = v.x + l2.x / 2.0 * step;
            double k3y = v.y + l2.y / 2.0 * step;
            Vector2d k3 = new Vector2d(k3x, k3y);

            double l3x = -__G * (gradient3.x + course.getFriction() * k3.x / k3.length());
            double l3y = -__G * (gradient3.y + course.getFriction() * k3.y / k3.length());
            Vector2d l3 = new Vector2d(l3x, l3y);


            //FOURTH STEP
            double p4x = p.x + k3.x * step;
            double p4y = p.y + k3.y * step;
            Vector2d p4 = new Vector2d(p4x, p4y); //Intermediate position vector after step 3 to calculate gradient
            Vector2d gradient4 = course.height.gradient(p4);

            double k4x = v.x + l3.x * step;
            double k4y = v.y + l3.y * step;
            Vector2d k4 = new Vector2d(k4x, k4y);

            double l4x = -__G * (gradient4.x + course.getFriction() * k4.x / k4.length());
            double l4y = -__G * (gradient4.y + course.getFriction() * k4.y / k4.length());
            Vector2d l4 = new Vector2d(l4x, l4y);


            //CALCULATING THE NEXT POSITION AND VELOCITY
            //       k = (k1 +      2 * k2         +      2 * k3         +  k4)    *    (step/6.0);
            Vector2d k = k1.add(k2.multiply(2.0)).add(k3.multiply(2.0)).add(k4).multiply(step/6.0);
            Vector2d l = l1.add(l2.multiply(2.0)).add(l3.multiply(2.0)).add(l4).multiply(step/6.0);
            p = p.add(k);
            v = v.add(l);
        }

        //UPDATE POSITION ON THE COURSE
        this.course.ball.setPosition(new Vector3d(p.x, course.height.evaluate(p), p.y));
        this.course.ball.setVelocity(new Vector3d(v.x, 0, v.y));
    }


    @Override
    public void setStepSize(double h) {
        this.step = h;
    }

    @Override
    public double getStepSize() {
        return this.step;
    }
}
