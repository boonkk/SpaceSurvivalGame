package com.olech.spacesurvivalgame.desktop;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.olech.spacesurvivalgame.SpaceGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Game myGame = new SpaceGame();
		LwjglApplication launcher = new LwjglApplication( myGame, "Space Survival Game", 800, 600 );
	}
}
