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

class MagicBoxTest {

    MagicBox magicBoxExample;

    @BeforeEach
    void setUp() throws IOException{
        ElementLoader.getElementLoader().readGamePro();
        ElementLoader.getElementLoader().readImagePro();
        ElementLoader.getElementLoader().readCharactorsPro();
        ElementLoader.getElementLoader().readBubblePro();
        ElementLoader.getElementLoader().readSquarePro();
        Map<String, List<String>> gameInfoMap = ElementLoader.getElementLoader().getGameInfoMap();
        magicBoxExample=MagicBox.createMagicBox(1,1);
    }

    @AfterEach
    void tearDown() {
    }


    @Test
    void chanceSelect() {
    }

    @Test
    void createMagicBox() {
    }

    @Test
    void crash() {
    }

    @Test
    void update() {
    }

    @Test
    void updateImage() {
    }

    @Test
    void destroy() {
        magicBoxExample.destroy();
        assertFalse(magicBoxExample.isEaten());
        assertTrue(magicBoxExample.isAlive());
    }

    @Test
    void isEaten() throws NoSuchFieldException, IllegalAccessException {
        Field field = magicBoxExample.getClass().getDeclaredField("eaten");
        field.setAccessible(true);
        boolean result = (boolean) field.get(magicBoxExample);
        assertEquals(result, magicBoxExample.isEaten());
    }

    @Test
    void setEaten() {
        magicBoxExample.setEaten(true);
        assertTrue(magicBoxExample.isEaten());
    }

    @Test
    void getCharacterIndex() throws NoSuchFieldException, IllegalAccessException {
        Field field = magicBoxExample.getClass().getDeclaredField("characterIndex");
        field.setAccessible(true);
        int result = (int) field.get(magicBoxExample);
        assertEquals(result, magicBoxExample.getCharacterIndex());
    }

    @Test
    void setCharacterIndex() {
        magicBoxExample.setCharacterIndex(3);
        assertEquals(3,magicBoxExample.getCharacterIndex());
    }
}