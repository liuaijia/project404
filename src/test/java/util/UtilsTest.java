package util;
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


import static org.junit.jupiter.api.Assertions.*;


class UtilsTest {
    Utils utilsExample=new Utils();

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void between() {
        assertTrue(utilsExample.between(2,1,3));
        assertFalse(utilsExample.between(1,2,3));
        assertTrue(utilsExample.between(2,3,1));
        assertTrue(utilsExample.between(2,-1,3));
    }
}