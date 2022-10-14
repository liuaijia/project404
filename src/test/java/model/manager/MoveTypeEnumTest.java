package model.manager;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MoveTypeEnumTest {

    @BeforeEach
    void setUp() {
        System.out.println("@BeforeEach，测试开始");
    }

    @AfterEach
    void tearDown() {
        System.out.println("@AfterEach，测试结束");
    }

    @Test
    void testLEFT(){
        assertEquals(true,MoveTypeEnum.LEFT==MoveTypeEnum.codeToMoveType(37));
        assertEquals(true,MoveTypeEnum.LEFT==MoveTypeEnum.codeToMoveType(65));
    }

    @Test
    void testTOP(){
        assertEquals(true,MoveTypeEnum.TOP==MoveTypeEnum.codeToMoveType(38));
        assertEquals(true,MoveTypeEnum.TOP==MoveTypeEnum.codeToMoveType(87));
    }

    @Test
    void testRIGHT(){
        assertEquals(true,MoveTypeEnum.RIGHT==MoveTypeEnum.codeToMoveType(39));
        assertEquals(true,MoveTypeEnum.RIGHT==MoveTypeEnum.codeToMoveType(68));
    }

    @Test
    void testDOWN(){
        assertEquals(true,MoveTypeEnum.DOWN==MoveTypeEnum.codeToMoveType(40));
        assertEquals(true,MoveTypeEnum.DOWN==MoveTypeEnum.codeToMoveType(83));
    }

    @Test
    void testSTOP(){
        assertEquals(true,MoveTypeEnum.STOP==MoveTypeEnum.codeToMoveType(0));
    }

}