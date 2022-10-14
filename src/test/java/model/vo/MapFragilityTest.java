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

class MapFragilityTest {
    MapFragility mapFragilityExample;


    @BeforeEach
    void setUp() throws IOException {
        ElementLoader.getElementLoader().readGamePro();
        ElementLoader.getElementLoader().readImagePro();
        ElementLoader.getElementLoader().readCharactorsPro();
        ElementLoader.getElementLoader().readBubblePro();
        ElementLoader.getElementLoader().readSquarePro();
        Map<String, List<String>> gameInfoMap = ElementLoader.getElementLoader().getGameInfoMap();
        int i=1;
        int j=1;
        List<String> data = gameInfoMap.get("magicBox1");
        ImageIcon img = ElementLoader.getElementLoader().getImageMap().get("magicBox1");;
        int sx = 250;
        int sy = 500;
        int dx = 500;
        int dy = 250;
        int scaleX = 1000;
        int scaleY = 1000;
        mapFragilityExample= new MapFragility(i,j,img,sx,sy,dx,dy,scaleX,scaleY);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void createMapFragility() {
    }

    @Test
    void update() {
    }

    @Test
    void destroy() {
        mapFragilityExample.destroy();
        assertFalse(mapFragilityExample.isDestoried());
        assertTrue(mapFragilityExample.isAlive());
    }

    @Test
    void isDestoried() throws NoSuchFieldException, IllegalAccessException {
        Field field = mapFragilityExample.getClass().getDeclaredField("destoried");
        field.setAccessible(true);
        boolean result = (boolean) field.get(mapFragilityExample);
        assertEquals(result, mapFragilityExample.isDestoried());
    }

    @Test
    void setDestoried() {
        mapFragilityExample.setDestoried(true);
        assertTrue(mapFragilityExample.isDestoried());
    }
}