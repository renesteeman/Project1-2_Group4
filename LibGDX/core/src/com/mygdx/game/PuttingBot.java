package com.mygdx.game;

interface PuttingBot {
	//TODO why 2D?
	Vector2d shot_velocity(PuttingCourse course, Vector2d ball_position);
}