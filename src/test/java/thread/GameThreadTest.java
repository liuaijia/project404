//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package thread;

import java.io.IOException;
import java.util.List;
import main.GameController;
import model.loader.ElementLoader;
import model.manager.ElementManager;
import model.vo.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameThreadTest {
    private GameThread gameThread;

    GameThreadTest() {
    }

    @BeforeEach
    void setUp() throws IOException {
        ElementLoader.getElementLoader().readGamePro();
        ElementLoader.getElementLoader().readImagePro();
        ElementLoader.getElementLoader().readCharactorsPro();
        ElementLoader.getElementLoader().readBubblePro();
        ElementLoader.getElementLoader().readSquarePro();
        GameController.setTwoPlayer(true);
        ElementManager.getManager().loadMap();
        this.gameThread = new GameThread();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void playerBoom() {
        List<SuperElement> explodeList = ElementManager.getManager().getElementList("explode");
        explodeList.add(BubbleExplode.createExplode(120, 100, 3, 2));
        explodeList.add(BubbleExplode.createExplode(120, 680, 3, 2));
        explodeList.add(BubbleExplode.createExplode(890, 100, 1, 1));
        explodeList.add(BubbleExplode.createExplode(890, 680, 1, 0));
        int[] list = new int[]{0, 1};
        this.gameThread.npcBoom();
        List<SuperElement> playerList = ElementManager.getManager().getElementList("npc");

        for(int i = 0; i < playerList.size(); ++i) {
            Player player = (Player)playerList.get(i);
            Assertions.assertEquals(list[i], player.getHeathPoint());
        }

    }

    @Test
    void npcBoom() {
        List<SuperElement> explodeList = ElementManager.getManager().getElementList("explode");
        explodeList.add(BubbleExplode.createExplode(120, 100, 3, 2));
        explodeList.add(BubbleExplode.createExplode(120, 680, 3, 2));
        explodeList.add(BubbleExplode.createExplode(890, 100, 1, 1));
        explodeList.add(BubbleExplode.createExplode(890, 680, 1, 0));
        Boolean[] list = new Boolean[]{false, true};
        this.gameThread.npcBoom();
        List<SuperElement> npcList = ElementManager.getManager().getElementList("npc");

        for(int i = 0; i < npcList.size(); ++i) {
            Npc npc = (Npc)npcList.get(i);
            Assertions.assertEquals(list[i], npc.isDead());
        }

    }

    @Test
    void fragilityBoom() {
        List<SuperElement> explodeList = ElementManager.getManager().getElementList("explode");
        explodeList.add(BubbleExplode.createExplode(120, 100, 3, 2));
        explodeList.add(BubbleExplode.createExplode(120, 680, 3, 2));
        explodeList.add(BubbleExplode.createExplode(890, 100, 1, 1));
        explodeList.add(BubbleExplode.createExplode(890, 680, 1, 0));
        int[] list = new int[]{0, 4, 22, 29};
        this.gameThread.fragilityBoom();
        List<SuperElement> fragility = ElementManager.getManager().getElementList("fragility");

        for(int i = 0; i < list.length; ++i) {
            MapFragility mapFragility = (MapFragility)fragility.get(list[i]);
            Assertions.assertTrue(mapFragility.isDestoried());
        }

    }

    @Test
    void playerMagicBox() {
        List<SuperElement> list = ElementManager.getManager().getElementList("magicBox");
        list.add(MagicBox.createMagicBox(2,2));
        list.add(MagicBox.createMagicBox(8,1));
        list.add(MagicBox.createMagicBox(1,16));
        list.add(MagicBox.createMagicBox(8,16));
    }

    @Test
    void npcMagicBox() {
    }
}
