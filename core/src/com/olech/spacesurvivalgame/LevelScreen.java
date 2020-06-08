package com.olech.spacesurvivalgame;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.olech.spacesurvivalgame.gameactors.*;


public class LevelScreen extends BaseScreen
{
    private Spaceship spaceship;
    private boolean gameOver;

    public void initialize() {
        gameOver = false;
        BasicActor space = new BasicActor(0,0, mainStage);
        space.loadTexture( "background.png" );
        space.setSize(SpaceGame.GAME_WIDTH,SpaceGame.GAME_HEIGHT);
        BasicActor.setWorldBounds(space);

        new Asteroid(600,500, mainStage);
        new Asteroid(600,300, mainStage);
        new Asteroid(600,100, mainStage);
        new Asteroid(400,100, mainStage).setScale(2f);
        new Asteroid(200,100, mainStage);
        new Asteroid(200,300, mainStage);
        new Asteroid(200,500, mainStage);
        new Asteroid(400,500, mainStage);
    }
    public void update(float dt) {
        for ( BasicActor rockActor : BasicActor.getList(mainStage, "com.olech.spacesurvivalgame.gameactors.Asteroid") ) {
            if (rockActor.overlaps(spaceship)) {
                if (spaceship.shieldPower <= 0) {
                    ShipBoom boom = new ShipBoom(0,0, mainStage);
                    boom.centerAtActor(spaceship);
                    spaceship.remove();
                    spaceship.setPosition(-1000,-1000);
                    BasicActor messageLose = new BasicActor(0,0, uiStage);
                    messageLose.loadTexture("losemessage.png");
                    messageLose.centerAtPosition(uiStage.getWidth()/2,uiStage.getHeight()/2);
                    messageLose.setOpacity(0);
                    messageLose.addAction( Actions.fadeIn(1) );
                    gameOver = true;
                } else {
                    spaceship.shieldPower -= 34;
                    Explosion boom = new Explosion(0,0, mainStage);
                    boom.centerAtActor(rockActor);
                    spawnChilds(rockActor);
                    rockActor.remove();
                }
            }
            for ( BasicActor laserActor : BasicActor.getList(mainStage, "com.olech.spacesurvivalgame.gameactors.Laser") ) {
                if (laserActor.overlaps(rockActor)) {
                    Explosion boom = new Explosion(0,0, mainStage);
                    if(rockActor.getScaleX()<1)
                        boom.setScale(0.5f);
                        boom.centerAtActor(rockActor);
                    laserActor.remove();
                    spawnChilds(rockActor);
                    rockActor.remove();
                }
            }
        }

        if ( !gameOver && BasicActor.count(mainStage, "com.olech.spacesurvivalgame.gameactors.Asteroid") == 0 ) {
            BasicActor messageWin = new BasicActor(0,0, uiStage);
            messageWin.loadTexture("winmessage.png");
            messageWin.centerAtPosition(uiStage.getWidth()/2,uiStage.getHeight()/2);
            messageWin.setOpacity(0);
            messageWin.addAction( Actions.fadeIn(1) );
            gameOver = true;
        }

        //fast Meteorites
//        if( BasicActor.getList(mainStage,"com.olech.arcanoid.gameactors.Meteorite").size()==0) {
//            new Meteorite(MathUtils.random(800),MathUtils.random(600),mainStage);
//        }

    }

    @Override
    public boolean keyDown(int keycode) {
        if ( keycode == Input.Keys.X )
            spaceship.warp();
        if ( keycode == Input.Keys.SPACE )
            spaceship.shoot();
        if( keycode == Input.Keys.ENTER) {
            //need to remove everything from previous game
//            for ( BasicActor rockActor : BasicActor.getList(mainStage, "com.olech.spacesurvivalgame.gameactors.Asteroid") ) {
////                rockActor.remove();
////            }
////            uiStage.getActors().clear();
////            initialize();
            StartScreen startScreen = new StartScreen();
            SpaceGame.setActiveScreen(startScreen);
        }
        return false;
    }

    public void spawnChilds(BasicActor rockActor) {
        if(rockActor.getHeight() > 25 && rockActor.getScaleX()>=1f) {
            new Asteroid(rockActor.getX(), rockActor.getY(),mainStage).setScale(rockActor.getScaleX()/2);
            new Asteroid(rockActor.getX(), rockActor.getY(),mainStage).setScale(rockActor.getScaleX()/2);

        }
    }

    public void createShip(int shipNo) {
        spaceship = new Spaceship(400,300, mainStage, shipNo);
    }


}
