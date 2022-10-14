package model.loader;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import javax.swing.*;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

class ElementLoaderTest {

    private static ElementLoader elementLoader=null,elementLoader2=null;

    @BeforeEach
    void setUp()  throws IOException {
        System.out.println("@BeforeEach，测试开始");

        ElementLoader.getElementLoader().readGamePro();
    }

    @AfterEach
    void tearDown() {
        System.out.println("@AfterEach，测试结束");
    }

    @Test
    void getElementLoader() {
        elementLoader=ElementLoader.getElementLoader();
        elementLoader2=ElementLoader.getElementLoader();
        Assert.assertEquals(true,elementLoader==elementLoader2);
    }

    @Test
    void readGamePro() throws IOException, NoSuchFieldException, IllegalAccessException {
        Map<String, List<String>> result=invokeMap("gameInfoMap");
        Assert.assertEquals(9,result.size());
    }

    @Test
    void readImagePro() throws IOException, NoSuchFieldException, IllegalAccessException {
        ElementLoader.getElementLoader().readGamePro();
        ElementLoader.getElementLoader().readImagePro();
        Map<String, List<String>> result=invokeMap("imageMap");
        Assert.assertEquals(38,result.size());
    }

    @Test
    void readCharactorsPro() throws IOException, NoSuchFieldException, IllegalAccessException {
        ElementLoader.getElementLoader().readCharactorsPro();
        Map<String, List<String>> result=invokeMap("gameInfoMap");
        Assert.assertEquals(15,result.size());
    }

    @ParameterizedTest
    @ValueSource(strings={"npcA","npcB","npcC"})
    void getNpcImageList(String npc) {
        List<ImageIcon> result=ElementLoader.getElementLoader().getNpcImageList(npc);
        Assert.assertEquals(4,result.size());
    }

    @Test
    void readBubblePro() throws IOException, NoSuchFieldException, IllegalAccessException {
        ElementLoader.getElementLoader().readBubblePro();
        Map<String, List<String>> result=invokeMap("gameInfoMap");
        Assert.assertEquals(10,result.size());
    }

    @Test
    void readSquarePro() throws IOException, NoSuchFieldException, IllegalAccessException {
        ElementLoader.getElementLoader().readSquarePro();
        Map<String, List<String>> result=invokeMap("squareTypeMap");
        Assert.assertEquals(23,result.size());
    }

    @ParameterizedTest
    @ValueSource(strings={"stage1Map","stage2Map","stage3Map"})
    void readMapPro(String mapProStr) throws IOException, NoSuchFieldException, IllegalAccessException {
        if(mapProStr=="stage1Map"){
            Assert.assertEquals(14,ElementLoader.getElementLoader().readMapPro(mapProStr).size());
        }else if(mapProStr=="stage2Map"){
            Assert.assertEquals(13,ElementLoader.getElementLoader().readMapPro(mapProStr).size());
        }else if(mapProStr=="stage3Map"){
            Assert.assertEquals(12,ElementLoader.getElementLoader().readMapPro(mapProStr).size());
        }
    }

    private Map<String, List<String>> invokeMap(String invoke)  throws NoSuchFieldException, IllegalAccessException{
        Field field = ElementLoader.getElementLoader().getClass().getDeclaredField(invoke);
        field.setAccessible(true);
        Map<String, List<String>> result = (Map<String, List<String>>) field.get(ElementLoader.getElementLoader());
        return result;
    }
}