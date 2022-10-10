package thread;

import com.sun.xml.internal.ws.util.StringUtils;
import frame.GameFrame;
import frame.OverJPanel;
import main.GameStart;
import model.manager.ElementManager;
import model.vo.*;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;


/**
 * 游戏线程控制
 *
 */
public class GameThread extends Thread{
	private boolean running; //表示当前关卡是否在进行
	private boolean over = false; //表示游戏是否结束，结束返回开始菜单
	private static int sleepTime = 20; //runGame刷新时间
	//倒计时变量
	private static int allTime = 600*1000; //10分钟



	@Override
	public void run() {
		while(!over) {
			running = true;//当前关卡正在进行
			//加载元素
			loadElement();
			//显示人物，流程，自动化
			runGame();
			//结束当前关
			overGame(over);
		}
		GameStart.changeJPanel("over");
	}

	//加载元素
	public void loadElement() {
		ElementManager.getManager().loadMap();//加载地图及其元素
	}

	/**
	 * 关卡结束
	 * 如果over为真则游戏失败返回界面，否则进入下一关
	 * @param over
	 */
	public void overGame(Boolean over) {
		ElementManager.getManager().overGame(over);
	}

	//显示人物，游戏流程，自动化
	public void runGame() {
		allTime = 600*1000;
		while(running) {
			Map<String, List<SuperElement>> map = ElementManager.getManager().getMap();
			Set<String> set = map.keySet();
			for(String key:set) {
				List<SuperElement> list = map.get(key);
				for(int i=list.size()-1; i>=0; i--) {
					list.get(i).update();
					if(!list.get(i).isAlive())
						list.remove(i);
				}
			}

			//添加游戏的流程控制linkGame()?

			//玩家与炸弹碰撞死亡
			playerBoom();
			//可破坏物与炸弹碰撞
			fragilityBoom();
			//电脑与炸弹碰撞死亡
			npcBoom();
			//电脑与道具碰撞效果，暂时不开启
			//npcMagicBox();
			//玩家与道具碰撞效果
			playerMagicBox();
			//检测是否玩家全部死亡
			defeat();

			//控制runGame进程
			allTime = allTime - sleepTime;
			try {
				sleep(20);
			} catch (InterruptedException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}

	public void defeat() {
		boolean allDead = true;
		int surviveP = 0;
		int winner = 2;//0为玩家1，1为玩家2，2为电脑获胜
		List<SuperElement> playerList = ElementManager.getManager().getElementList("player");
		List<SuperElement> npcList = ElementManager.getManager().getElementList("npc");
		for(SuperElement se:playerList) {
			if(!((Player)se).isDead()) {
				surviveP++;
			}
		}
		for(SuperElement npc:npcList) {
			if(!((Npc)npc).isDead()) {
				allDead = false;
			}
		}

		//请在玩家失败或胜利后，记录分数等，并录入数据库
		//玩家失败
		if(surviveP==0||(allTime<=0 && !allDead)) {
			running = false;
			over = true;
			OverJPanel.getResult().setText("defeated");
			int score1 = ((Player)playerList.get(0)).score;
			OverJPanel.getScoreBoard().setText(String.valueOf(score1));
			//System.out.println("log");
			uploadScore(0,score1,"分数上传成功");
		}
		//玩家胜利
		if(allDead&&surviveP==1) {
			running = false;
			over = true;
			for(SuperElement se:playerList) {
				if(!((Player)se).isDead()) {
					surviveP++;
					winner = ((Player)se).getPlayerNum();
				}
			}
			OverJPanel.getResult().setText("player "+(winner+1)+" win");
			int score1 = ((Player)playerList.get(0)).score;
			OverJPanel.getScoreBoard().setText("score: "+ score1);
			uploadScore(1,score1,"分数和胜利次数上传成功");
		}
		
		//时间到，两个玩家都活着
		if(allTime<=0&&surviveP==2&&allDead) {
			running = false;
			over = true;
			int score1 = ((Player)playerList.get(0)).score;
			int score2 = ((Player)playerList.get(0)).score;
			if(score1==score2) {
				OverJPanel.getResult().setText("defeated");
			}
			else if(score1>score2)
			{
				OverJPanel.getResult().setText("player 1 win");
				OverJPanel.getScoreBoard().setText("score: "+String.valueOf(score1));
			}
			else {
				OverJPanel.getResult().setText("player 2 win");
				OverJPanel.getScoreBoard().setText("score: "+String.valueOf(score2));
			}
		}
	}

	//玩家与炸弹碰撞判断
	public void playerBoom() {
		List<SuperElement> playerList = ElementManager.getManager().getElementList("player");
		List<SuperElement> explodeList = ElementManager.getManager().getElementList("explode");
		for(int i=0; i<playerList.size(); i++) {
			for(int j=0; j<explodeList.size(); j++) {
				if(explodeList.get(j).crash(playerList.get(i))){
					Player player = (Player) playerList.get(i);
					player.setHealthPoint(-1);//����ֵ-1
				}
			}
		}
		
	}
	//npc与炸弹碰撞判断
	public void npcBoom() {
		List<SuperElement> playerList = ElementManager.getManager().getElementList("player");
		List<SuperElement> npcList = ElementManager.getManager().getElementList("npc");
		List<SuperElement> explodeList = ElementManager.getManager().getElementList("explode");
		for(int i=0; i<npcList.size(); i++) {
			for(int j=0; j<explodeList.size(); j++) {
				if(explodeList.get(j).crash(npcList.get(i))){
					Npc npc = (Npc) npcList.get(i);
					npc.setDead(true);
					npc.setX(-100);
					npc.setY(-100);
					BubbleExplode e = (BubbleExplode)explodeList.get(j);
					if(e.getPlayerNum()<2)//Ŀǰֻ����ҼƷ�
						((Player)playerList.get(e.getPlayerNum())).setScore(((Player)playerList.get(e.getPlayerNum())).getScore()+50);
				}
			}
		}
	}

	//障碍物与炸弹碰撞判断
	public void fragilityBoom() {
		List<SuperElement> playerList = ElementManager.getManager().getElementList("player");
		List<SuperElement> explodes = ElementManager.getManager().getElementList("explode");
		List<SuperElement> fragility = ElementManager.getManager().getElementList("fragility");
		for(int i=0; i<fragility.size(); i++) {
			for(int j=0; j<explodes.size(); j++) {
				if(explodes.get(j).crash(fragility.get(i))) {
					MapFragility mapFragility = (MapFragility)fragility.get(i);
					mapFragility.setDestoried(true);
					BubbleExplode e = (BubbleExplode)explodes.get(j);
					if(e.getPlayerNum()<2)//Ŀǰֻ����ҼƷ�
						((Player)playerList.get(e.getPlayerNum())).setScore(((Player)playerList.get(e.getPlayerNum())).getScore()+10);
				}
			}
		}
	}

	//玩家与道具碰撞判断
	public void playerMagicBox() {
		List<SuperElement> playerList = ElementManager.getManager().getElementList("player");
		List<SuperElement> magicBoxList = ElementManager.getManager().getElementList("magicBox");
		for(int i=0; i<playerList.size(); i++) {
			for(int j=magicBoxList.size()-1; j>=0; j--) {
				if(magicBoxList.get(j).crash(playerList.get(i))){
					MagicBox magicBox = (MagicBox) magicBoxList.get(j);
					magicBox.setCharacterIndex(i);//˭�Է���
					magicBox.setEaten(true);//���鱻��
					((Player)playerList.get(i)).setScore(((Player)playerList.get(i)).getScore()+30);
				}
				
			}
		}
	}

	//npc与道具碰撞判断
	public void npcMagicBox() {
		List<SuperElement> npcList = ElementManager.getManager().getElementList("npc");
		List<SuperElement> magicBoxList = ElementManager.getManager().getElementList("magicBox");
		for(int i=0; i<npcList.size(); i++) {
			for(int j=magicBoxList.size()-1; j>=0; j--) {
				if(magicBoxList.get(j).crash(npcList.get(i))){
					MagicBox magicBox = (MagicBox) magicBoxList.get(j);
					magicBox.setCharacterIndex(i+2);//˭�Է���
					magicBox.setEaten(true);//���鱻��
				}
			}
		}
	}

	//runGame调用，加入拓展
	public void linkGame() {}


	public static int getAllTime() {
		return allTime;
	}

	public void uploadScore(int num, int score, String msg){
		if (GameFrame.uID!=null){
			OkHttpClient client = new OkHttpClient();
			RequestBody requestBody = new FormBody.Builder()
					.add("username", GameFrame.uID)
					.add("num", String.valueOf(num))
					.add("grade", String.valueOf(score))
					.build();
			Request request = new Request.Builder()
					.url("http://localhost:8002/upload")
					.post(requestBody)
					.build();
			client.newCall(request).enqueue(new Callback() {
				@Override
				public void onFailure(@NotNull Call call, @NotNull IOException e) {
					JOptionPane.showMessageDialog(null, "network error");
				}

				@Override
				public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
					String responseData = Objects.requireNonNull(response.body()).string();
					if ("1".equals(responseData)){
						JOptionPane.showMessageDialog(null, msg);
					}
					if ("0".equals(responseData)){
						JOptionPane.showMessageDialog(null, "上传失败");
					}
				}
			});
		}
	}

}
