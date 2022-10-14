package model.vo;
import model.loader.ElementLoader;
import model.manager.ElementManager;
import model.manager.GameMap;
import static model.manager.MoveTypeEnum.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static model.vo.Character.INIT_SPEED;
import static org.junit.jupiter.api.Assertions.*;

class CharacterTest{
    Character characterExample;

    @BeforeEach
    void setUp() throws IOException{
        ElementLoader.getElementLoader().readGamePro();
        ElementLoader.getElementLoader().readImagePro();
        ElementLoader.getElementLoader().readCharactorsPro();
        ElementLoader.getElementLoader().readBubblePro();
        ElementLoader.getElementLoader().readSquarePro();
        int i = 1;
        int j = 1;
        //System.out.println(GameMap.getBiasX()); 0
        //System.out.println(GameMap.getBiasX()); 0
        int x = j*MapSquare.PIXEL_X+ GameMap.getBiasX();
        int y = i*MapSquare.PIXEL_Y+GameMap.getBiasY();
        int w = MapSquare.PIXEL_X;
        int h = MapSquare.PIXEL_Y;
        characterExample=new Character(x,y,w,h);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void setHealthPoint() {
        characterExample.setHealthPoint(1);
        assertEquals(2,characterExample.getHeathPoint());
        characterExample.setHealthPoint(-2);
        assertFalse(characterExample.isAlive());
        characterExample.setHealthPoint(-2);
        assertTrue(characterExample.isDead());
    }

    @Test
    void changeSpeed() {
        int Orispeed=characterExample.getSpeed();
        characterExample.changeSpeed(2,1000);
        assertEquals(Orispeed*2,characterExample.getSpeed());
        characterExample.changeSpeed(0.5,1000);
        assertEquals(Orispeed,characterExample.getSpeed());
        characterExample.changeSpeed(-1,1000);
        assertEquals(-Orispeed,characterExample.getSpeed());
    }

    @Test
    void changeDirection() {
        int Orispeed=characterExample.getSpeed();
        characterExample.changeDirection(1000);
        assertEquals(-Orispeed,characterExample.getSpeed());
    }

    @Test
    void setOtherStop() {
    }

    @Test
    void setSpeedToInital() {
        characterExample.setSpeedToInital(1000,characterExample);
        assertEquals(INIT_SPEED,characterExample.getSpeed());
    }

    @Test
    void setUnstoppable() {
    }

    @Test
    void unstoppableChangeImg() {
    }

    @Test
    void bubbleAddPower() {
        characterExample.setBubblePower(2);
        characterExample.bubbleAddPower();
        assertEquals(3,characterExample.getBubblePower());
    }

    @Test
    void isDead() {
        assertEquals(characterExample.dead,characterExample.isDead());
    }

    @Test
    void setDead() {
        characterExample.setDead(characterExample.dead);
        assertFalse(characterExample.dead);
    }

    @Test
    void getMoveType(){
        assertEquals(characterExample.moveType,characterExample.getMoveType());
    }

    @Test
    void setMoveType() {
        characterExample.setMoveType(LEFT);
        assertEquals(LEFT,characterExample.getMoveType());
    }

    @Test
    void getSpeed() {
        assertEquals(characterExample.speed,characterExample.getSpeed());
    }

    @Test
    void setSpeed() {
        characterExample.setSpeed(8);
        assertEquals(8,characterExample.getSpeed());
    }

    @Test
    void getSpeedItemCount() {
        assertEquals(characterExample.speedItemCount,characterExample.getSpeedItemCount());
    }

    @Test
    void setSpeedItemCount() {
        characterExample.setSpeedItemCount(1);
        assertEquals(1,characterExample.getSpeedItemCount());
    }

    @Test
    void getBubblePower() {
        assertEquals(characterExample.bubblePower,characterExample.getBubblePower());
    }

    @Test
    void setBubblePower() {
        characterExample.setBubblePower(2);
        assertEquals(2,characterExample.getBubblePower());
    }

    @Test
    void getBubbleNum() {
        assertEquals(characterExample.bubbleNum,characterExample.getBubbleNum());
    }

    @Test
    void setBubbleNum() {
        characterExample.setBubbleNum(1);
        assertEquals(1,characterExample.getBubbleNum());
    }

    @Test
    void getBubbleLargest() {
        assertEquals(characterExample.bubbleLargest,characterExample.getBubbleLargest());
    }

    @Test
    void setBubbleLargest() {
        characterExample.setBubbleLargest(5);
        assertEquals(5,characterExample.getBubbleLargest());
    }

    @Test
    void getScore() {
        assertEquals(characterExample.score,characterExample.getScore());
    }

    @Test
    void setScore() {
        characterExample.setScore(10);
        assertEquals(10,characterExample.getScore());
    }

    @Test
    void getChangeDirectionCount() {
        assertEquals(characterExample.changeDirectionCount,characterExample.getChangeDirectionCount());
    }

    @Test
    void setChangeDirectionCount() {
        characterExample.setChangeDirectionCount(0);
        assertEquals(0,characterExample.getChangeDirectionCount());
    }

    @Test
    void getStopitemCount() {
        assertEquals(characterExample.stopitemCount ,characterExample.getStopitemCount());
    }

    @Test
    void setStopitemCount() {
        characterExample.setStopitemCount(1);
        assertEquals(1,characterExample.getStopitemCount());
    }

    @Test
    void getHeathPoint() {
        assertEquals(characterExample.heathPoint,characterExample.getHeathPoint());
    }

    @Test
    void isisUnstoppable() {
        assertEquals(characterExample.isUnstoppable,characterExample.isisUnstoppable());
    }

    @Test
    void setisUnstoppable() {
        characterExample.setisUnstoppable(true);
        assertTrue(characterExample.isUnstoppable);
    }
}