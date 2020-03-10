package com.gdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.gdx.game.GDX;

/* To use JDK 13 follow these steps:
1: Go to build.grade under desktop
2: Set sourceCompatibility = 1.13
3: Set the SDK in project structure equal to 13
4: Go to setttings -> build tools -> Graadle -> grade JVM
 */

public class DesktopLauncher {
	public static void main(String[] arg) {
		LwjglApplicationConfiguration config = new
				LwjglApplicationConfiguration();
		config.width = (int) Core.VIRTUAL_WIDTH;
		config.height = (int) Core.VIRTUAL_HEIGHT;
		new LwjglApplication(new Core(), config);
	}
}

