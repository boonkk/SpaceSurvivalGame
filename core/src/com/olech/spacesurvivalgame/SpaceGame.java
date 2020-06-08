package com.olech.spacesurvivalgame;

public class SpaceGame extends BaseGame
{
    public static final int GAME_WIDTH = 1024;
    public static final int GAME_HEIGHT = 768;
    public void create()
    {
        super.create();
        //setActiveScreen( new LevelScreen() );
        setActiveScreen( new StartScreen() );
    }
}
