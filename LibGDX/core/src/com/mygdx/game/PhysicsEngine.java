package com.mygdx.game;

interface PhysicsEngine {
	void process(Vector2d p, Vector2d v, double dtime);
	void set_step_size(double h);
	double getStepSize();
}