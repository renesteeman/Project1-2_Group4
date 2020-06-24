package Physics;

public interface PhysicsEngine {
	ShotInfo process(double dtime, ShotInfo shotInfo);
	void setStepSize(double h);
	double getStepSize();
	void setPhysicsStep(double step);
}
