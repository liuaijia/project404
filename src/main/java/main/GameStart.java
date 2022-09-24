package main;

import java.io.IOException;

import frame.GameFrame;
import model.loader.ElementLoader;
import thread.GameMusicPlayer;


/**
 * 游戏启动入口
 * &#064;ClassName:  GameStart
 * &#064;Description:
 * &#064;CreateDate:  2019年4月8日 下午4:17:37
 */
public class GameStart {
	private static GameFrame gameFrame;

	//游戏启动入口
	public static void main(String[] args) {
		// 资源加载
		try {
			ElementLoader.getElementLoader().readGamePro();
			ElementLoader.getElementLoader().readImagePro();
			ElementLoader.getElementLoader().readCharactorsPro();
			ElementLoader.getElementLoader().readBubblePro();
			ElementLoader.getElementLoader().readSquarePro();
		} catch (IOException e) {
			System.out.println("资源加载失败");
			e.printStackTrace();
		}
		//初始化
		gameFrame = new GameFrame();
		//界面显示
		gameFrame.setVisible(true);
		//音乐播放
		GameMusicPlayer musicPlayer = new GameMusicPlayer();
		musicPlayer.start();
	}

	/**
	 * 界面切换
	 * @param panelName 界面名称
	 */
	public static void changeJPanel(String panelName){
		if(panelName == "game") {
			GameController.setGameRunning(true);
			gameFrame.addListener();
		} else {
			GameController.setGameRunning(false);
			gameFrame.removeListener();
		}
		gameFrame.changePanel(panelName);

		//强制刷新，否则监听无效
		gameFrame.setVisible(false);
		gameFrame.setVisible(true);
	}
	
	public static void startNewGame() {
		GameController.setGameRunning(true);
		gameFrame.startGame();
		changeJPanel("game");
	}

}
