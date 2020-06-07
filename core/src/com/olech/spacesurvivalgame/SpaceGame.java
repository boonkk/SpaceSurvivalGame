package com.olech.spacesurvivalgame;

public class SpaceGame extends BaseGame
{
    public void create()
    {
        super.create();
        setActiveScreen( new LevelScreen() );
    }
}
