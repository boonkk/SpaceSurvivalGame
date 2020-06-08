package com.olech.spacesurvivalgame;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Rectangle;
import com.olech.spacesurvivalgame.gameactors.BasicActor;
import com.olech.spacesurvivalgame.gameactors.PickFrame;


public class StartScreen extends BaseScreen {
    private int shipIndex = 0;
    private int noShips = 4;
    private PickFrame pickFrame;
    private BasicActor ship1;
    private BasicActor ship2;
    private BasicActor ship3;
    private BasicActor ship4;


    @Override
    public void initialize() {
        BasicActor space = new BasicActor(0,0, mainStage);
        space.loadTexture( "background.png" );
        space.setSize(SpaceGame.GAME_WIDTH,SpaceGame.GAME_HEIGHT);

        ship1 = new BasicActor(uiStage.getWidth()/2-150, uiStage.getHeight()/2-100, uiStage);
        ship1.loadTexture("ship1.png");

        pickFrame = new PickFrame(0,0,uiStage);
        pickFrame.centerAtActor(ship1);

        ship2 = new BasicActor(uiStage.getWidth()/2-50, uiStage.getHeight()/2-100, uiStage);
        ship2.loadTexture("ship2.png");

        ship3 = new BasicActor(uiStage.getWidth()/2+50,uiStage.getHeight()/2-100, uiStage);
        ship3.loadTexture("ship3.png");

        ship4 = new BasicActor(uiStage.getWidth()/2+150,uiStage.getHeight()/2-100, uiStage );
        ship4.loadTexture("ship4.png");
        BasicActor title = new BasicActor(0,0,mainStage);
        title.loadTexture("title.png");
        title.centerAtPosition(mainStage.getWidth()/2 ,mainStage.getHeight()/2+200);

    }

    @Override
    public void update(float dt) {

    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.LEFT) {
            shipIndex--;
            updateIndex();
        } else if(keycode == Input.Keys.RIGHT) {
            shipIndex++;
            updateIndex();
        }
        if(keycode == Input.Keys.ENTER) {
            LevelScreen levelScreen = new LevelScreen();
            levelScreen.createShip(shipIndex);
            SpaceGame.setActiveScreen(levelScreen);

        }

        return false;
    }

    private void updateIndex() {
        if(shipIndex > noShips-1)
            shipIndex-=noShips;
        if(shipIndex<0)
            shipIndex+=noShips;
        if(shipIndex == 0)
            pickFrame.centerAtActor(ship1);
        else if (shipIndex == 1)
            pickFrame.centerAtActor(ship2);
        else if(shipIndex == 2)
            pickFrame.centerAtActor(ship3);
        else if(shipIndex == 3)
            pickFrame.centerAtActor(ship4);

    }
}
